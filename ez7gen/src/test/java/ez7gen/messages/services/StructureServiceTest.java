package ez7gen.messages.services;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class StructureServiceTest {
//	No brackets around it - Required
//	[] - Optional
//	{ } - Repeating
//	[{ }] - Optional Repeating
	
	//@Test
	public void test() {
		//<MessageStructure name='ADT_A01'
		String definition="MSH~EVN~PID~[~PD1~]~[~{~ROL~}~]~[~{~NK1~}~]~PV1~[~PV2~]~[~{~ROL~}~]~[~{~DB1~}~]~[~{~OBX~}~]~[~{~AL1~}~]~[~{~DG1~}~]~[~DRG~]~[~{~PR1~[~{~ROL~}~]~}~]~[~{~GT1~}~]~[~{~IN1~[~IN2~]~[~{~IN3~}~]~[~{~ROL~}~]~}~]~[~ACC~]~[~UB1~]~[~UB2~]~[~PDA~]";
//		MSH
//		~EVN
//		~PID
//		~[~PD1~]
//		~[~{~ROL~}~]
//		~[~{~NK1~}~]
//		~PV1
//		~[~PV2~]
//		~[~{~ROL~}~]
//		~[~{~DB1~}~]
//		~[~{~OBX~}~]
//		~[~{~AL1~}~]
//		~[~{~DG1~}~]
//		~[~DRG~]
//		~[~{~PR1~[~{~ROL~}~]~}~]
//		~[~{~GT1~}~]
//		~[~{~IN1~[~IN2~]~[~{~IN3~}~]~[~{~ROL~}~]~}~]
//		~[~ACC~]
//		~[~UB1~]
//		~[~UB2~]
//		~[~PDA~]
		
	//	[r:MSH]
	//  [o:PD1]
    //~[~{~PR1~[~{~ROL~}~]~}~]
	//  [ork:[or:PR1, or:ROL]]	
	//~[~{~IN1~[~IN2~]~[~{~IN3~}~]~[~{~ROL~}~]~}~]
	// ork[r:IN1,o:IN2, or:IN3, or:ROL]

		StructureService s = new StructureService();
		List structs = s.parse();
	}
	
	@Test
	public void testParseAll() throws Exception {
		StructureService s = new StructureService("MSH~EVN~PID~[~PD1~]~[~{~ROL~}~]");
		List<Map<String, Object>> structs = s.parse();
		for(Map map: structs){
			System.out.println("values : " + map.values() + "keys : " + map.keySet() );
		}
	}
	
	@Test
	public void testGetNextSegment() throws Exception {
		StructureService s = new StructureService("MSH~EVN~PID~[~PD1~]");
		Integer idx = 0;
		System.out.println("values : " + s.getNextSegment().values() + " idx : " + idx);
		//assertEquals("MSH", s.getNextSegment().values());
		
	}
	
//	@Test
//	public void isGroupTest() throws Exception {
//		StructureService s = new StructureService();
//		assertFalse(s.isGroup("{","}")); //
//		assertTrue(s.isGroup("}","{"));
//		assertFalse(s.isGroup("[","]"));
//		assertTrue(s.isGroup("]","["));
//		assertFalse(s.isGroup("{","["));
//		assertFalse(s.isGroup("[","}"));
//		assertFalse(s.isGroup("[",null));
//	}

	@Test
	public void handleConditionalSegmentsOptionalTest() throws Exception {
		StructureService s = new StructureService("[~PD1~]");
		Map map = s.handleConditionalSegmetns();
		System.out.println("values : " + map.values() + "keys : " + map.keySet());

	}
	@Test
	public void handleConditionalSegmentsOptionalRepeatingTest() throws Exception {
		StructureService s = new StructureService("[~{~ROL~}~]");
		Map map = s.handleConditionalSegmetns();
		System.out.println("values : " + map.values() + "keys : " + map.keySet());
		
	}
}
