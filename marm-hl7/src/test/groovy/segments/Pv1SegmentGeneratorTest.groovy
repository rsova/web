package segments;

import static org.junit.Assert.*
import hapi.ContextGenerator

import org.junit.Test

import ca.uhn.hl7v2.model.v24.message.ADT_A01

class Pv1SegmentGeneratorTest {

	@Test
	public void testGenerate() {
		
		Pv1SegmentGenerator psg = new Pv1SegmentGenerator()
		psg.firstNames = ['Bob','Bill']
		psg.lastNames = ['Do','Lee']
		
		ADT_A01 hl7Msg = new ADT_A01();
		hl7Msg.initQuickstart("ADT", "A01", "P")
		hl7Msg = psg.generate(hl7Msg,null, [])
		println new ContextGenerator().outAsEr7(hl7Msg)
	}

}
