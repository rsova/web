
package service.generator

import java.util.regex.Matcher

import segments.ISegmentGenerator
import segments.PidSegmentGenerator
import ca.uhn.hl7v2.model.AbstractMessage
import ca.uhn.hl7v2.model.v24.message.ADT_A01
import ensemble.profiles.ProfileParser

class SmartSegmentGeneratorService {
	static final Map generatorMap = [PID: new PidSegmentGenerator()]
	
	public  AbstractMessage generateMessage(String version, String type, List segments){
		
		AbstractMessage message = generateHeader(type)
		for (segment in segments) {
			Map segmentDetails = getSegmentDetails(segment)
			//message = ((ISegmentGenerator) generatorMap.get(segmentDetails.name))?.generate(message, segmentDetails)
			ISegmentGenerator generator	 =  generatorMap.get(segmentDetails.name)
			if(generator != null){
				message = generator.generate(message, segmentDetails)
			}
		}
		
		return message
	}
	
	protected Map getSegmentDetails (Object segmentDefinition){
		Map details = [:]
		
		if(segmentDefinition instanceof String){//single segment
			//check if this segment repeats
			boolean isReps = segmentDefinition.contains(ProfileParser.LBRS)
			
			//remove control characters
			Matcher matcher = segmentDefinition =~ /(~|\[|\])|\{|\}/
			String name = matcher.replaceAll("").trim();
			details = ["name": name, "reps":isReps]
		}
		else{
			//handle group
		}
		return details
	}
	
	private AbstractMessage generateHeader(String type) {
		AbstractMessage message;

		switch(type){
			case "ADT_A01":
				message = new ADT_A01();
				message.initQuickstart("ADT", "A01", "P");
				break;
			default:
				break;
		}
		return message
	}

}
