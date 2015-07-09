package service.picker

import java.util.List;
import java.util.Random;

import ensemble.profiles.ProfileParser;

class SmartSegmentPickerService {
	private List segments
	private String profile
	
	//load 50 percent of optional segments
	static final loadFactor = 0.5
	static final Random random = new Random()
	
	public SmartSegmentPickerService( String profile, List segments) {
		this.segments = segments;
		this.profile = profile;
	}
	
	public SmartSegmentPickerService(Map segmentsMap) {
		this.segments = segmentsMap.segments;
		this.profile = segmentsMap.profile;
	}
	
	public List getSegmentsToBuild(){
		List pList = profile.split("~")
			
		List nonRequitedSegments = getSegmentCandidates()		
		//ListIterator<String> iter = pList.listIterator()
		List segmentsToKeep = []
		//while(iter.hasNext()){
		for(String seg in pList){
			
			//String seg = iter.next()
			if(seg.isNumber()) {
				Integer segNbmr = seg.toInteger()
				if(nonRequitedSegments.contains(segNbmr)){
					def n = handleSegment(segNbmr)
					segmentsToKeep.add(n)
				}
				
			}else{ segmentsToKeep.add(seg) }
		}		
		return segmentsToKeep
	}
	
	protected handleSegment(int idx){
		def n = segments.get(idx).getAt(0)
		//TODO: Check if this is group and undo the group
		return n
	}
	
	protected List getSegmentCandidates(){		
		int max = getLoadCandidatesCount(segments.size())
		Set randoms = []
		while(randoms.size() < max){
			randoms.add(random.nextInt(segments.size()).toInteger())
		}
		return randoms.sort() as List
	}

	protected int getLoadCandidatesCount(int total) {
		//round it up
		return Math.ceil(total*loadFactor).toInteger()
	}
	

}
