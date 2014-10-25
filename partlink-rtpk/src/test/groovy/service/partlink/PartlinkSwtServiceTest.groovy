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

	
	//@Test
	public void testSplit() {
		def map = [name:"Gromit", likes:"cheese", id:1234, part:'345', description:'Screw']
		Set set = map.keySet()
		println set.toArray()[0..2]
		def o = map.subMap(['name', 'likes'])
		map = map - o
		println map
		println o
		
		map = [
			[name:'Clark', city:'London'], [name:'Sharma', city:'London'],
			[name:'Maradona', city:'LA'], [name:'Zhang', city:'HK'],
			[name:'Ali', city: 'HK'], [name:'Liu', city:'HK'],
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
		obj =  service.lookupSupplierByNiin('010077987')//1
		use ( TimeCategory ) {
		println "time to run : " + (new Date() - start)
		}
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
	
	@Test
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
	void test5() {
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
	//@Test
	void test6() {
		GParsExecutorsPool.withPool {
			Collection<Future> result = [1, 2, 3, 4, 5].collectParallel{it * 10}
			assert new HashSet([10, 20, 30, 40, 50]) == new HashSet((Collection)result*.get())
		}
	   
	}
}
