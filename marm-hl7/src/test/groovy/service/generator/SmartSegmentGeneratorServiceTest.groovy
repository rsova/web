package service.generator;

import static org.junit.Assert.*

import org.junit.Test

import segments.PidSegmentGenerator
import service.ContextGenerator;
import ca.uhn.hl7v2.model.AbstractMessage

public class SmartSegmentGeneratorServiceTest {

	//@Test
	public void testGenerateAdt01() {
		def segments = ["MSH"," EVN"," PID"," [~{~ROL~}~]"," [~{~NK1~}~]"," PV1"," [~PV2~]"," [~{~DB1~}~]"," [~{~AL1~}~]"," [~{~DG1~}~]"," [~{~PR1~10~}~]"," [~UB1~]"]
		
		AbstractMessage message = new SmartSegmentGeneratorService().generateMessage("2", "ADT_A01", segments)
		//message = new PidSegmentGenerator().generate(message)
		println new ContextGenerator().outAsEr7(message) 
		
	}
	
	@Test
	public void testGetSegmentDetails(){
		SmartSegmentGeneratorService ssgs =  new SmartSegmentGeneratorService()
		Map m = ssgs.getSegmentDetails("MSH")
		assertEquals("MSH", m.name)
		assertFalse(m.reps)
		
		m = ssgs.getSegmentDetails("[~{~ROL~}~]")
		assertEquals("ROL", m.name)
		assertTrue(m.reps)
		
		m = ssgs.getSegmentDetails("[~PV2~]")
		assertEquals("PV2", m.name)
		assertFalse(m.reps)
		
	}

}
