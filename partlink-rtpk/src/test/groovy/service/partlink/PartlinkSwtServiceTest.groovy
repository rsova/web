package service.partlink;

import static org.junit.Assert.*;

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hp.hpl.jena.query.*
import com.hp.hpl.jena.rdf.model.*

import groovyx.gpars.*
import groovy.time.*

import java.util.concurrent.Future

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import service.partlink.sparql.Sparql
import service.web.WebLookupService
import service.web.parser.WebContentParser
import utils.*

class PartlinkSwtServiceTest {
	static PartlinkSwtService service

	@BeforeClass
	public static void setUp(){
		service = new PartlinkSwtService('http://api.xsb.com/sparql/query')
	}

	@AfterClass
	public static void cleanUp(){
		service = null
	}
	//@Test
	public void test1() {
		def sparql =
				'''
		SELECT * 
		WHERE{
				?logNiin log:hasProductNIIN prod:NIIN010077987 .   
				?logNiin log:hasReferenceNumber ?refNum .
				?refNum log:hasCage ?cage .  
				?cage log:hasCageCode ?cageCode     
		}
		'''
		println service.execute(sparql)
	}

	//@Test
	public void test2() {
		def sparql =
				'''
		SELECT * 
		WHERE{
		?cageCode log:hasCageCode "64616";
		#log:hasCAO ?cao;
#		log:hasADP ?adp;                                 
		<http://xsb.com/swiss/logistics#isWomanOwned> ?wo.                                 

#?person ab:homeTel "(229) 276-5135" ;
#          ab:firstName ?first ; 
#          ab:lastName  ?last . 
		}
		'''
		service.execute(sparql)
	}
	//@Test
	public void test3() {
		//def sparql ='SELECT * WHERE{<http://xsb.com/swiss/logistics#CageStatus_A>" ?a ?b.}'
		//def sparql ='SELECT * WHERE{<http://www.w3.org/2006/vcard/ns#Voice> ?a ?b.}'
		def sparql ='SELECT * WHERE{<http://xsb.com/swiss/logistics#isWomanOwned> ?a ?b.}'
		service.execute(sparql)
	}

	@Test
	public void testCamelCaseToLabel() {
		def camel = 'cageCode'
		println camel.each {it = it.toUpperCase()}
		println StringUtil.splitCamelCase(camel);
	}

	@Test
	public void testSplit() {
		LinkedHashMap map = [name:"Gromit", likes:"cheese", id:1234, part:'345', description:'Screw']
		Set set = map.keySet()
		println set.toArray()[0..2]
		def o = map.subMap(['name', 'likes'])
		map = map - o
		println 'map : ' +  map
		println o
		
		println o.remove('bogus')
		println o
		
		println o.remove('name')
		println o
		println "________"
		def list = [[name:"Gromit", likes:"cheese", id:1234, part:'345', description:'Screw']]
		println list.first()
		
		map = [
			[name:'Clark', city:'London'],
			[name:'Sharma', city:'London'],
			[name:'Maradona', city:'LA'],
			[name:'Zhang', city:'HK'],
			[name:'Ali', city: 'HK'],
			[name:'Liu', city:'HK'],
		].groupBy{ it.city }
		
		//			 == [
		//			London: [ [name:'Clark', city:'London'],
		//					  [name:'Sharma', city:'London'] ],
		//			LA: [ [name:'Maradona', city:'LA'] ],
		//			HK: [ [name:'Zhang', city:'HK'],
		//				  [name:'Ali', city: 'HK'],
		//				  [name:'Liu', city:'HK'] ],
		//		  ]
		println map
	}

	@Test
	public void testLookup() {
		def obj
		def start = new Date()
		//obj =  service.lookupSupplierByNiinOrg('010077987')//1
		obj =  service.lookupSupplierByNiin('016033650')//1
		use ( TimeCategory ) {println "time to run : " + (new Date() - start)}
		println new GsonBuilder().setPrettyPrinting().create().toJson(obj)
		println "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
		//		obj =  service.lookupSupplierByNiin('015127231')//1
		//		println new GsonBuilder().setPrettyPrinting().create().toJson(obj)
		//		println "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
		//		obj =  service.lookupSupplierByNiin('015303893')//1
		//		println new GsonBuilder().setPrettyPrinting().create().toJson(obj)
		//		println "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
		//		obj= service.lookupSupplierByNiin('016033650')//2
		//		println new GsonBuilder().setPrettyPrinting().create().toJson(obj)
	}

	//@Test
	public void testSuppliersWithAddress1() {
		def sparql =
				'''
#	SELECT DISTINCT ?cageName ?cageCode ?cao ?adp ?wo, ?streetAddress, ?locality, ?region, ?countryName, ?postalCode, ?pnone
#	SELECT DISTINCT ?name (CONCAT(?streetAddress,', ', ?locality,', ',?region,', ', ?countryName,', ', ?postalCode) as ?address) (GROUP_CONCAT(?ph) as ?phone) ?cao ?adp ?isWomanOwned 
	SELECT DISTINCT ?name ?cageCode ?address (GROUP_CONCAT(?ph) as ?phone) ?cao ?adp ?isWomanOwned  
	WHERE{
	#?cageCode log:hasCageCode "64616";
	#?logNiin log:hasProductNIIN prod:NIIN010077987 .   
	?logNiin log:hasProductNIIN ?prodNIIN .   
	?logNiin log:hasReferenceNumber ?refNum .
	?refNum log:hasCage ?cage .  
	?cage log:hasCageCode ?cageCode;   
	#?cageCode log:hasCageCode ?cageId;
	log:hasCageName ?name;
	#		log:hasCageStatus ?cageStatus;
	#		log:hasBusinessSizeCode ?businessSizeCode;
	vcard:hasAddress ?addrs;
	vcard:hasTelephone ?telephone;
	log:hasCAO ?cao;
	log:hasADP ?adp;                                 
	log:isWomanOwned ?isWomanOwned.                                 
	
	?addrs vcard:street-address ?streetAddress.
	?addrs vcard:locality ?locality.
	?addrs vcard:region ?region.
	?addrs vcard:country-name ?countryName.
	?addrs vcard:postal-code ?postalCode.
	
	#?telephone rdf:type vcard:Voice.
	?telephone vcard:hasValue ?ph.

	BIND (CONCAT(?streetAddress,', ', ?locality,', ',?region,', ', ?countryName,', ', ?postalCode) as ?address).
	
	} GROUP BY ?name ?cageCode ?address ?cao ?adp ?isWomanOwned 
	#} GROUP BY ?name ?cageCode ?cao ?adp ?isWomanOwned ?streetAddress ?locality ?region ?countryName ?postalCode
'''
		println service.execute(sparql)
	}

	//@Test
	void test4() {
		def json =
				'''
   {
    "cageCode": "0SRE6",
    "address": "8101 WALLACE RD, EDEN PRAIRIE, MN, UNITED STATES, 55344-2114",
    "phone": "9529149789 9529149866",
    "cao": "S2401A",
    "adp": "HQ0339",
    "isWomanOwned": "N"
  }
'''
		Gson gson=new Gson();
		Map<String,String> map=new HashMap<String,String>();
		map=(Map<String,String>) gson.fromJson(json, map.getClass());
		println map

		Map leafs = service.generateLeafs(map)
		//println leafs
		println new GsonBuilder().setPrettyPrinting().create().toJson(leafs)


	}

	@Test
	void testGenerateClientFeedOld() {
		def json =
				//'''
				//  {
				//    "niin": "016033650",
				//    "price": "154.26",
				//    "assignmentDate": "2012-03-01",
				//    "name": "DEFENSE MEDICAL STANDARDIZATION",
				//    "cageCode": "64616",
				//    "address": "1423 SULTON DR, FREDERICK, MD, UNITED STATES, 21702-5013",
				//    "phone": "301-619-2001",
				//    "cao": "S2101A",
				//    "adp": "HQ0338",
				//    "isWomanOwned": "N"
				//  }
				//'''
				'''
[
  {
    "niin": "016033650",
    "price": "154.26",
    "assignmentDate": "2012-03-01",
    "name": "DEFENSE MEDICAL STANDARDIZATION",
    "cageCode": "64616",
    "address": "1423 SULTON DR, FREDERICK, MD, UNITED STATES, 21702-5013",
    "phone": "301-619-2001",
    "cao": "S2101A",
    "adp": "HQ0338",
    "isWomanOwned": "N"
  },
  {
    "niin": "016033650",
    "price": "154.26",
    "assignmentDate": "2012-03-01",
    "name": "KEY SURGICAL, INC.",
    "cageCode": "0SRE6",
    "address": "8101 WALLACE RD, EDEN PRAIRIE, MN, UNITED STATES, 55344-2114",
    "phone": "9529149789 9529149866",
    "cao": "S2401A",
    "adp": "HQ0339",
    "isWomanOwned": "N"
  }
]'''
		def prods = []
		prods= new Gson().fromJson(json, prods.getClass());
		println prods

		Map clientJson = service.generateClientFeed(prods)
		//println leafs
		println new GsonBuilder().setPrettyPrinting().create().toJson(clientJson)


	}
	
	@Test
	void testGenerateClientFeed() {
		def json =
'''
[
  {
    "prodName": "BRUSH SET,CLEANING,MEDICAL INSTRUMENT",
    "price": "154.26",
    "assignmentDate": "2012-03-01",
    "suppliers": [
      {
        "name": "KEY SURGICAL, INC.",
        "address": "8101 WALLACE RD, EDEN PRAIRIE, MN, UNITED STATES, 55344-2114",
        "cageCode": "0SRE6"
      },
      {
        "name": "DEFENSE MEDICAL STANDARDIZATION",
        "address": "1423 SULTON DR, FREDERICK, MD, UNITED STATES, 21702-5013",
        "cageCode": "64616"
      },
      {
        "name": "DEFENSE LOGISTICS AGENCY",
        "address": "700 ROBBINS AVE, PHILADELPHIA, PA, UNITED STATES, 19111",
        "cageCode": "89875"
      }
    ]
  }
]'''
		def prods = []
		prods= new Gson().fromJson(json, prods.getClass());
		println prods.get(0)
		
		Map clientJson = service.generateClientFeed(prods.get(0))
		//println leafs
		println new GsonBuilder().setPrettyPrinting().create().toJson(clientJson)
		
		
	}
	
	
	//@Test
	void test6() {
		GParsExecutorsPool.withPool {
			Collection<Future> result = [1, 2, 3, 4, 5].collectParallel{it * 10}
			assert new HashSet([10, 20, 30, 40, 50]) == new HashSet((Collection)result*.get())
		}

	}

	@Test
	void testExecute1() {
def sparql = '''
 SELECT DISTINCT * WHERE {
		#<http://xsb.com/swiss/logistics#REFNUM08108150PAR%2FFL%2FR_3_1> log:hasCage ?cage
		log:REFNUM08108150PAR%2FFL%2FR_3_1 log:hasCage ?cage.
 		?cage log:hasCageCode ?cageCode;   
 		log:hasCageName ?name;
 		#vcard:hasAddress ?addr;
 		#vcard:hasTelephone ?telephone;
	   }
	   LIMIT 10
 '''
		println service.execute(sparql)

	}
	@Test
	void testExecute2() {
def sparql = '''
select  *
	where{
	#?NIIN prod:nationalItemId "001186248". #null
    #?NIIN prod:nationalItemId "010077987". #log:REFNUM08108150PAR%2FFL%2FR_3_1, log:REFNUM08805150PAR%2FFL%2FR_3_2
	?NIIN prod:nationalItemId "016033650".
	#?NIIN prod:nationalItemId ?id.
	?NIIN rdfs:subClassOf ?prod.
	?prod rdfs:label ?prodName.
	?logNiin log:hasProductNIIN ?NIIN . 
	?logNiin rdfs:label ?id .
	?logNiin log:hasUnitPrice ?price.
	?logNiin log:assignmentDate ?assignmentDate.

    optional {?logNiin log:hasReferenceNumber ?refNum.
    ?refNum log:hasCage ?cage .  
    ?cage log:hasCageCode ?cageCode.
	?cage vcard:hasAddress ?addr.
	#?cage vcard:hasTelephone ?telephone.
}


} limit 10'''
		println service.execute(sparql)


	}
	@Test
	void testExecute3() {
		//		[[NIIN:http://xsb.com/swiss/product#NIIN016033650, prod:http://xsb.com/swiss/product#39211:BRUSH_SET_CLEANING_MEDICAL_INSTRUMENT, prodName:BRUSH SET,CLEANING,MEDICAL INSTRUMENT, logNiin:http://xsb.com/swiss/logistics#NIIN016033650, id:016033650, price:154.26, assignmentDate:2012-03-01, refNum:http://xsb.com/swiss/logistics#REFNUM0SRE6BR-4499_3_2, cage:http://xsb.com/swiss/logistics#CAGE0SRE6, cageCode:0SRE6, addr:39f9d74c:1494ab520d9:-7fff], [NIIN:http://xsb.com/swiss/product#NIIN016033650, prod:http://xsb.com/swiss/product#39211:BRUSH_SET_CLEANING_MEDICAL_INSTRUMENT, prodName:BRUSH SET,CLEANING,MEDICAL INSTRUMENT, logNiin:http://xsb.com/swiss/logistics#NIIN016033650, id:016033650, price:154.26, assignmentDate:2012-03-01, refNum:http://xsb.com/swiss/logistics#REFNUM646166545NCM127090_5_1, cage:http://xsb.com/swiss/logistics#CAGE64616, cageCode:64616, addr:39f9d74c:1494ab520d9:-7ffe], [NIIN:http://xsb.com/swiss/product#NIIN016033650, prod:http://xsb.com/swiss/product#39211:BRUSH_SET_CLEANING_MEDICAL_INSTRUMENT, prodName:BRUSH SET,CLEANING,MEDICAL INSTRUMENT, logNiin:http://xsb.com/swiss/logistics#NIIN016033650, id:016033650, price:154.26, assignmentDate:2012-03-01, refNum:http://xsb.com/swiss/logistics#REFNUM8987531402_5_1, cage:http://xsb.com/swiss/logistics#CAGE89875, cageCode:89875, addr:39f9d74c:1494ab520d9:-7ffd]]

		def sparql = '''
		select distinct ?name ?address 
		where{
		?cage log:hasCageCode ?cageCodeEl.
		#FILTER(?cageCodeEl IN ("0SRE6","64616","89875"))
		#FILTER(?cageCodeEl IN ("0SRE6","FAE78"))
		#FILTER(?cageCodeEl IN ("08805")).
		FILTER(?cageCodeEl IN ('89875', '64616', '67032', 'Z04E6', '65442', 'FAE78')).
        ?cage log:hasCageName ?name.

		#?cage log:hasCageCode "FAE78".
 
		?cage vcard:hasAddress ?addr.
		?addr vcard:street-address ?streetAddress.
		?addr vcard:locality ?locality.
		optional {?addr vcard:region ?reg}.
		?addr vcard:country-name ?countryName.
		?addr vcard:postal-code ?postalCode.

		#?cage vcard:hasTelephone ?telephone.
		bind ( COALESCE(?reg, "") As ?region)
		bind (concat(?streetAddress,', ', ?locality,', ',?region,', ', ?countryName,', ', ?postalCode) as ?address).	

		}  limit 10'''
		def start = new Date()
		println service.execute(sparql)
		use ( TimeCategory ) {println "time to run : " + (new Date() - start)}
		//[[name:DEFENSE MEDICAL STANDARDIZATION, address:1423 SULTON DR, FREDERICK, MD, UNITED STATES, 21702-5013], [name:PELICAN PRODUCTS, INC., address:23215 EARLY AVE, TORRANCE, CA, UNITED STATES, 90505-4002], [name:GENERAL DYNAMICS C4 SYSTEMS, INC., address:400 JOHN QUINCY ADAMS RD, TAUNTON, MA, UNITED STATES, 02780-1036], [name:PELICAN PRODUCTS PTY LTD, address:SUITE 2.33 WEST WING PLATINUM BLDG 4 ILYA AVE, ERINA, NS, AUSTRALIA, 2250], [name:DEFENSE LOGISTICS AGENCY, address:700 ROBBINS AVE, PHILADELPHIA, PA, UNITED STATES, 19111], [name:C.P. FRANCE, address:1775 RTE (NATION.106) DE NIMES, SAINT HILAIRE DE BRETHMAS, , FRANCE, 30560]]

	}

	@Test
	void testExecute4() {
	def sparql =
		'''
		select  distinct ?name ?address
		where{
		?NIIN prod:nationalItemId "016033650".
		?logNiin log:hasProductNIIN ?NIIN .	
		?logNiin log:hasReferenceNumber ?refNum.
		
		?refNum log:hasCage ?cage .  
		?cage log:hasCageCode ?cageCode.
		?cage log:hasCageName ?name.
		?cage vcard:hasAddress ?addr.
		?addr vcard:street-address ?streetAddress.
		?addr vcard:locality ?locality.
		optional {?addr vcard:region ?region}.
		?addr vcard:country-name ?countryName.
		?addr vcard:postal-code ?postalCode.
		
		#?cage vcard:hasTelephone ?telephone.
		bind (concat(?streetAddress,', ', ?locality,', ',?region,', ', ?countryName,', ', ?postalCode) as ?address).	
		}#limit 10'''
					
		def start = new Date()
		println service.execute(sparql)
		use ( TimeCategory ) {println "time to run : " + (new Date() - start)}
	}
	
	@Test
	void testExecute5() {
		def sparql = '''
        SELECT DISTINCT ?name ?address ?cageCode  WHERE {
		#<http://xsb.com/swiss/logistics#REFNUM08108150PAR%2FFL%2FR_3_1> log:hasCage ?cage.
		#<http://xsb.com/swiss/logistics#REFNUM0SRE6BR-4499_3_2> log:hasCage ?cage.
		#log:REFNUM08108150PAR%2FFL%2FR_3_1 log:hasCage ?cage.
		?iri log:hasCage ?cage.
 		?cage log:hasCageCode ?cageCode;   
 		log:hasCageName ?name;
 		vcard:hasAddress ?addr.

		?addr vcard:street-address ?streetAddress.
		?addr vcard:locality ?locality.
		optional {?addr vcard:region ?reg}.
		?addr vcard:country-name ?countryName.
		?addr vcard:postal-code ?postalCode.

#		vcard:hasTelephone ?telephone.
#		optional{?telephone vcard:hasValue ?ph}.
#		bind ( COALESCE(?ph, "") As ?phone)

		bind ( COALESCE(?reg, "") As ?region)
		bind (concat(?streetAddress,', ', ?locality,', ',?region,', ', ?countryName,', ', ?postalCode) as ?address).
	   }
	   LIMIT 1
 '''
	   
		def products = service.execute(Sparql.NIIC_TO_CAGE_REF, [var:[id:"016033650"]])//null
		def rnl = products*.refNum
		println rnl
		//def refNum = products.get(0).refNum
 		//def refNum = 'http://xsb.com/swiss/logistics#REFNUM0SRE6BR-4499_3_2'
		println '________'
		def all = []
		for (refNum in rnl) {
		println refNum
		//refNum java.net.URLDecoder.decode(refNum)
		//println refNum
		try {
			def a = service.execute(sparql, ['uri':['iri':refNum]])//null			
			all.add(a)
		} catch (Exception e) {
			e.printStackTrace()
		}
//		def a = service.execute(sparql, [var:['iri':'log:REFNUM0SRE6BR-4499_3_2']])//null
		//def a = service.execute(sparql)//null
		}
		println '________'
		println all
	}
	
	@Test
	void testExecute6() {
//<http://xsb.com/swiss/product#NIIN012769436> | <http://xsb.com/swiss/product#13656:NEBULIZER_MEDICINAL>                                     |
		
		//def products = service.execute(Sparql.NIIC_TO_CAGE_REF, [var:[id:"016033650"]])//null
		def niin;
		niin = "012769436"
		def products = service.execute(Sparql.NIIC_TO_CAGE_REF, [var:[id:niin]])//null
		def rnl = products*.refNum
		println rnl
		//def refNum = products.get(0).refNum
		//def refNum = 'http://xsb.com/swiss/logistics#REFNUM0SRE6BR-4499_3_2'
		println '________'
		def all = []
		for (refNum in rnl) {
			//println refNum
			try {
				def a = service.execute(Sparql.GAGE_DETAILS_BY_REF, ['uri':['iri':refNum]])//null			
				all.add(a)
			} catch (Exception e) {
				e.printStackTrace()
			}
		}
		if(all.isEmpty()){
			def uri = 'http://www.logisticsinformationservice.dla.mil/WebFlis/pub/pub_search.aspx'
			def magicNbr = '&__VIEWSTATE=%2FwEPDwUJNTMzMDY2Nzk3D2QWAgICD2QWJAIID2QWAgIBDzwrAAsAZAIJD2QWAgIDDzwrAAsAZAIKD2QWAgIBDzwrAAsAZAILD2QWAgIBDzwrAAsAZAIMD2QWAgIBDzwrAAsAZAIND2QWAgIBDzwrAAsAZAIOD2QWAgIDDzwrAAsAZAIPD2QWAgIBDzwrAAsAZAIQD2QWAgIDDzwrAAsAZAIRD2QWBAIDDzwrAAsAZAIFDzwrAAsAZAISD2QWAgIBDzwrAAsAZAITD2QWAgIBDzwrAAsAZAIUD2QWAgIJDzwrAAsAZAIVD2QWAgIFDzwrAAsAZAIWD2QWAgIHDzwrAAsAZAIXD2QWAgIFDzwrAAsAZAIYD2QWAgIFDzwrAAsAZAIZD2QWAgIFDzwrAAsAZBgBBR5fX0NvbnRyb2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WCQURY2hrRXhwYW5kZWRTZWFyY2gFB2Noa0NhZ2UFAkMxBQJDMwUCQzYFAkM0BQJDNwUCQzkFA0MxNCThyg84CZcp1hAIXCkU7WWhv6pn'+
			'&__EVENTVALIDATION=%2FwEWEQK196%2FRAgLDkqnvDQLHkL%2B1AgK%2BsdPcCAK54Yr1AQLIhLeDDAKE3r2bCAK%2B61kC3v3%2B%2FgsC4a%2BaDQLd7%2BbtDALd797tDALd79LtDALd79rtDALd787tDALd74buDALj04CPBJ5oI514%2BmUpSMjvGSLrGeZpCy%2Be'+
			"&btnNIIN=Go"+
			"&C1=on"+
			"&C6=on"
	
			def scraper = new WebLookupService(uri,magicNbr)
			scraper.parser = new WebContentParser();
			def cages = scraper.lookup([niin])*.'CAGE CD'
			println 'cages: ' + cages
			def sparql = '''
			select distinct ?name ?address 
			where{
			?cage log:hasCageCode ?cageCodeEl.
			FILTER(?cageCodeEl IN ('2L201','1Y857')).
	        ?cage log:hasCageName ?name.
	
			#?cage log:hasCageCode "FAE78".
	 
			?cage vcard:hasAddress ?addr.
			?addr vcard:street-address ?streetAddress.
			?addr vcard:locality ?locality.
			optional {?addr vcard:region ?reg}.
			?addr vcard:country-name ?countryName.
			?addr vcard:postal-code ?postalCode.
	
			#?cage vcard:hasTelephone ?telephone.
			bind ( COALESCE(?reg, "") As ?region)
			bind (concat(?streetAddress,', ', ?locality,', ',?region,', ', ?countryName,', ', ?postalCode) as ?address).	
	
			}  limit 10'''
			println '+++++++++'
			println service.execute(sparql)

		}
		println '________'
		println all
	}
	
	@Test
	void testExecute7() {
		def niin;
		niin = "012769436"
		def uri = 'http://www.logisticsinformationservice.dla.mil/WebFlis/pub/pub_search.aspx'
				def magicNbr = '&__VIEWSTATE=%2FwEPDwUJNTMzMDY2Nzk3D2QWAgICD2QWJAIID2QWAgIBDzwrAAsAZAIJD2QWAgIDDzwrAAsAZAIKD2QWAgIBDzwrAAsAZAILD2QWAgIBDzwrAAsAZAIMD2QWAgIBDzwrAAsAZAIND2QWAgIBDzwrAAsAZAIOD2QWAgIDDzwrAAsAZAIPD2QWAgIBDzwrAAsAZAIQD2QWAgIDDzwrAAsAZAIRD2QWBAIDDzwrAAsAZAIFDzwrAAsAZAISD2QWAgIBDzwrAAsAZAITD2QWAgIBDzwrAAsAZAIUD2QWAgIJDzwrAAsAZAIVD2QWAgIFDzwrAAsAZAIWD2QWAgIHDzwrAAsAZAIXD2QWAgIFDzwrAAsAZAIYD2QWAgIFDzwrAAsAZAIZD2QWAgIFDzwrAAsAZBgBBR5fX0NvbnRyb2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WCQURY2hrRXhwYW5kZWRTZWFyY2gFB2Noa0NhZ2UFAkMxBQJDMwUCQzYFAkM0BQJDNwUCQzkFA0MxNCThyg84CZcp1hAIXCkU7WWhv6pn'+
				'&__EVENTVALIDATION=%2FwEWEQK196%2FRAgLDkqnvDQLHkL%2B1AgK%2BsdPcCAK54Yr1AQLIhLeDDAKE3r2bCAK%2B61kC3v3%2B%2FgsC4a%2BaDQLd7%2BbtDALd797tDALd79LtDALd79rtDALd787tDALd74buDALj04CPBJ5oI514%2BmUpSMjvGSLrGeZpCy%2Be'+
				"&btnNIIN=Go"+
				"&C1=on"+
				"&C6=on"
					
		def scraper = new WebLookupService(uri,magicNbr)
		scraper.parser = new WebContentParser();
		
		def cages = scraper.lookup([niin])*.'CAGE CD'
		println 'cages: ' + cages
		
		def sparql = '''
		select distinct ?name ?address 
		where{
		?cage log:hasCageCode ?cageCodeEl.
		FILTER(?cageCodeEl IN ($cageCodeEl)).
		?cage log:hasCageName ?name.
		
		#?cage log:hasCageCode "FAE78".
		
		?cage vcard:hasAddress ?addr.
		?addr vcard:street-address ?streetAddress.
		?addr vcard:locality ?locality.
		optional {?addr vcard:region ?reg}.
		?addr vcard:country-name ?countryName.
		?addr vcard:postal-code ?postalCode.
		
		#?cage vcard:hasTelephone ?telephone.
		bind ( COALESCE(?reg, "") As ?region)
		bind (concat(?streetAddress,', ', ?locality,', ',?region,', ', ?countryName,', ', ?postalCode) as ?address).	
		
		}  limit 10'''
		
		println '+++++++++'
		def tokens = cages.collect{ "'$it'" }.join(',')
		Map params =  ['filter': ['$cageCodeEl':tokens]]		
		println service.execute(sparql,params)		
		println '~~~~~~~~~'
		params =  ['filter': ['$CCTOKENS':tokens]]
		println service.execute(Sparql.GAGE_DETAILS_BY_CODE,params)		
		println '________'
		
		//println all
	}
}
