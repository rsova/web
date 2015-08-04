package segments;

import static org.junit.Assert.*

import org.junit.Test

import service.ContextGenerator;
import ca.uhn.hl7v2.model.v24.message.ADT_A01

class PidSegmentGeneratorTest {

	@Test
	public void testGenerate() {
		
		PidSegmentGenerator segment = new PidSegmentGenerator()
		segment.firstNames = ['Bob','Bill']
		segment.lastNames = ['Smith','Lee']

		ADT_A01 hl7Msg = new ADT_A01();
		hl7Msg.initQuickstart("ADT", "A01", "P")
		
		hl7Msg = segment.generate(hl7Msg,null,[])
		println new ContextGenerator().outAsEr7(hl7Msg)
	}

}
