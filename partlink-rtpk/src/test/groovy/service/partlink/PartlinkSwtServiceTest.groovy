package service.partlink;

import static org.junit.Assert.*
import groovy.time.*
import groovyx.gpars.*

import java.util.concurrent.Future

import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test

import service.partlink.sparql.Sparql
import utils.*

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hp.hpl.jena.query.*
import com.hp.hpl.jena.rdf.model.*

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
	public void testNiinsToAll_TimeOut() {
		def sparql = Sparql.NIIC_TO_SUPPS.replace("?niin",'"016033650"')
		def map = service.execute(Sparql.NIIC_TO_SUPPS)
		println map
	}

	@Test
	public void testNiinsToCages() {
		def sparql = '''
		SELECT * 
		WHERE{
			?logNiin log:hasProductNIIN prod:NIIN010077987 .   
			?logNiin log:hasReferenceNumber ?refNum .
			?refNum log:hasCage ?cage .  
			?cage log:hasCageCode ?cageCode     
		}
		'''
		def map = service.execute(sparql)
		assertEquals([], map*.'cageCode' - ['08108', '08108', '08805'])
	}

	@Test
	public void testCamelCaseToLabel() {
		def camel = 'cageCode'
		println camel.each {it = it.toUpperCase()}
		println StringUtil.splitCamelCase(camel);
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


	@Test
	void test4() {

	def json = '''
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

	//@Test
	void test6() {
		GParsExecutorsPool.withPool {
			Collection<Future> result = [1, 2, 3, 4, 5].collectParallel{it * 10}
			assert new HashSet([10, 20, 30, 40, 50]) == new HashSet((Collection)result*.get())
		}

	}

}