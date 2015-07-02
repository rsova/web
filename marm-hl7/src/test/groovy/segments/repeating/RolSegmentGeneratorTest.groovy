package segments.repeating;

import static org.junit.Assert.*
import hapi.ContextGenerator

import org.junit.Test

import ca.uhn.hl7v2.model.v24.message.ADT_A01

class RolSegmentGeneratorTest {

	@Test
	public void testGenerate() {
		RolSegmentGenerator segment = new RolSegmentGenerator()
		ADT_A01 hl7Msg = new ADT_A01();
		hl7Msg.initQuickstart("ADT", "A01", "P")
		
		hl7Msg = segment.generate(hl7Msg,[:])
		println new ContextGenerator().outAsEr7(hl7Msg)

	}

}
