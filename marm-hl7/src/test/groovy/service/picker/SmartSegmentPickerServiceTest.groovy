package service.picker;

import static org.junit.Assert.*

import java.util.regex.Matcher

import org.junit.Test

import ensemble.profiles.ProfileParser

class SmartSegmentPickerServiceTest {

	@Test
	public void testPickSegments() {
		
		Map segmentsList = new ProfileParser("2", "ADT_A01").getSegments();
		List segments = new SmartSegmentPickerService(segmentsList.profile, segmentsList.segments).pickSegments();
		assertNotNull(segments)
		println segments
 	}
	
	@Test
	public void testGetSegmentsToBuild() {
		Map segmentsList = new ProfileParser("2", "ADT_A01").getSegments();
		List segments = new SmartSegmentPickerService(segmentsList.profile, segmentsList.segments).getSegmentsToBuild();
		assertNotNull(segments)
		println segments
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
