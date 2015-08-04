package segments.repeating;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import service.ContextGenerator;
import ca.uhn.hl7v2.model.v24.message.ADT_A01
import ensemble.profiles.ProfileParser

class RolSegmentGeneratorTest {
	RolSegmentGenerator segment = new RolSegmentGenerator()
	ADT_A01 hl7Msg = new ADT_A01();
	List attributes;

	@Before
	void setup() {
		hl7Msg.initQuickstart("ADT", "A01", "P")
		
		segment.fieldGenerator.firstNames = ['Bob','Bill']
		segment.fieldGenerator.lastNames = ['Smith','Lee']
		segment.fieldGenerator.streetNames=["504 Honey Elk Wynd","7608 Silent Glen","7741 Foggy Pond Jetty","7061 Iron Blossom Ridge","5777 Fallen Panda Expressway"]
		segment.fieldGenerator.cities=["Oatmeal","Owl","Dollar Settlement","Saint-Quentin","Ragtown"]
		segment.fieldGenerator.states=["TN","MS","TN","MS","GA"]
		segment.fieldGenerator.zips=["37166-9572","38889-6760","37326-6978","39377-7406","39833-8563"]
		segment.fieldGenerator.countries=["US","US","US","US","US"]
		segment.fieldGenerator.phones=["(931) 872-7634","(601) 110-8688","(731) 538-7434","(662) 120-6474","(470) 446-7282"]
		
		ProfileParser pp = new ProfileParser("2.4","ADT_A01")
		 attributes = pp.getSegmentStructure("ROL")
	}
	
	//@Test
	void testGenerate() {
		hl7Msg = segment.generate(hl7Msg, attributes, true)
		println new ContextGenerator().outAsEr7(hl7Msg)

	}
	
	@Test
	void testGenerateByName() {
		hl7Msg = segment.generate(hl7Msg, "[~{~ROL~}~]" , attributes)
		println new ContextGenerator().outAsEr7(hl7Msg)
	}
	
	//@Test
	void test1() {
		def a = "[~{~ROL~}~]"
		boolean isRep = a.contains("~{")
		println isRep
		println a.replaceAll("~|\\[|\\]|\\{|\\}","")

	}
	
	

}
