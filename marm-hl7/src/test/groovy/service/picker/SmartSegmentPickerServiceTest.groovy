package service.picker;

import static org.junit.Assert.*;
import ensemble.profiles.ProfileParser

import org.junit.Test;

import service.picker.SmartSegmentPickerService;

class SmartSegmentPickerServiceTest {

	@Test
	public void testGetSegmentsToBuild() {
		Map segmentsList = new ProfileParser("2", "ADT_A01").getSegments();
		List segments = new SmartSegmentPickerService(segmentsList.profile, segmentsList.collection).getSegmentsToBuild();
		assertNotNull(segments)
		println segments
		assertEquals(12, segments.size());
	}
	
	@Test
	void testGetLoadCandidatesCount() {
		SmartSegmentPickerService ssps = new SmartSegmentPickerService("",[])
//		println ssps.getLoadCandidatesCount(10)
//		println ssps.getLoadCandidatesCount(11)
		assertEquals(5, ssps.getLoadCandidatesCount(10))
		assertEquals(6, ssps.getLoadCandidatesCount(11))
		assertEquals(6, ssps.getLoadCandidatesCount(12))
		
	}
	
	@Test
	void testGetSegmentCandidates() {
		SmartSegmentPickerService ssps = new SmartSegmentPickerService("",0..18)
		println ssps.getSegmentCandidates()
		println ssps.getSegmentCandidates()
		println ssps.getSegmentCandidates()
		println ssps.getSegmentCandidates()
	}

}
