package service.web;

import static org.junit.Assert.*;

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.junit.Test;

class UspsShippingLookupServiceTest {
	def json = '''
{
prodName: "BRUSH SET,CLEANING,MEDICAL INSTRUMENT",
NIIN: "016033650",
price: "154.26",
assignmentDate: "2012-03-01",
suppliers: [
{
name: "KEY SURGICAL, INC.",
address: "8101 WALLACE RD, EDEN PRAIRIE, MN",
country: "UNITED STATES",
postalCode: "55344-2114",
cageCode: "0SRE6"
},
{
name: "DEFENSE MEDICAL STANDARDIZATION",
address: "1423 SULTON DR, FREDERICK, MD",
country: "UNITED STATES",
postalCode: "21702-5013",
cageCode: "64616"
},
{
name: "DEFENSE LOGISTICS AGENCY",
address: "700 ROBBINS AVE, PHILADELPHIA, PA",
country: "UNITED STATES",
postalCode: "19111",
cageCode: "89875"
},
{
name: "C.P. FRANCE",
address: "1775 RTE (NATION.106) DE NIMES, SAINT HILAIRE DE BRETHMAS, ",
country: "FRANCE",
postalCode: "30560",
cageCode: "FAE78"
}
]
}
'''

	@Test
	public void testLookup() {
		File cfg = new File("src/ratpack/config/Config.groovy")
		def config = new ConfigSlurper().parse(cfg.text).app
		UspsShippingLookupService service = new UspsShippingLookupService(config.usps.user,config.usps.base, config.usps.xmlTemplate)
		//Gson gson=new Gson();
		Map<String,String> map=new HashMap<String,String>();
		map=(Map<String,String>) new Gson().fromJson(json, map.getClass());
		def sups =  map.'suppliers'
		def ranges = []
		for(Map sup in sups){
			Set range = []
			if( sup?.country?.equals("UNITED STATES")){
				def zip=sup?.postalCode
				if(zip){
					def zipFrom =  zip.substring(0, 5)
					def adviceMap = service.lookupPackageServiceStandard(zipFrom, '96753')
					if(adviceMap){
						sup.putAll(adviceMap)
					}
				}
			}
//			ranges.add("Usps standard:" + (range.size()==1)?range.getAt(0):range.min()<<'-'<<range.max())
		}
 	//map.each 
	println new GsonBuilder().setPrettyPrinting().create().toJson(map)
	}
	
	@Test
	public void testRange(){
		def x =['13', '15', '15', '5']
		assertEquals('5', x.max())
		x =[13, 15, 15, 5]
		assertEquals(15, x.max())
	}

}
