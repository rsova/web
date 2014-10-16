package service.partlink;

import static org.junit.Assert.*;

import com.hp.hpl.jena.query.*
import com.hp.hpl.jena.rdf.model.*

//import com.hp.hpl.jena.query.ResultSet
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

	@Test
	public void testLookup() {
		println service.lookupSupplierByNiin('010077987')//1
		//println service.lookupSupplierByNiin('016033650')//2
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
}
