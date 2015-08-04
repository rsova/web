package service;

//import ca.uhn.hl7v2.model.v24.message.ADT_A01;


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

import segments.MagicSegmentGenerator
import service.picker.SmartSegmentPickerService
import ca.uhn.hl7v2.model.AbstractMessage
import ca.uhn.hl7v2.model.v24.segment.MSH
import ensemble.profiles.ProfileParser

@Component
public class MessageFactory {
	public static Set set = []
//	@Autowired
//	AdtMessageGenerator adtMessageGenerator
	
	@Autowired
	MagicSegmentGenerator magicSegmentGenerator
	
	@Autowired
	SmartSegmentPickerService smartSegmentPickerService
	
//	public AbstractMessage generate(String version, String message, List segmentList){
//		AbstractMessage hl7Msg = adtMessageGenerator.genreateAdtA01Msg()
//		return hl7Msg
//	}
	
	public AbstractMessage generate(String version, String message){
		ProfileParser parser = new ProfileParser(version, message)
		
		AbstractMessage hl7Msg = init(version, message)
		Map segmentsMap = parser.getSegments()	
					
		// Get list of non required segments randomly selected for this build
		List segments = smartSegmentPickerService.init(segmentsMap).pickSegments()
		
		for (segment in segments) {
			List attributes = parser.getSegmentStructure(segment)
			hl7Msg = magicSegmentGenerator.generate(hl7Msg, segment, attributes)			
		}
		println MessageFactory.set.sort()
		return hl7Msg
	}
	
	protected AbstractMessage init(String version, String message){
		//AbstractMessage hl7Msg = new ADT_A01();
		//AbstractMessage hl7Msg = this.getClass().classLoader.loadClass( "ca.uhn.hl7v2.model.v24.message."+message, true, false )?.newInstance()		
		AbstractMessage hl7Msg = this.getClass().classLoader.loadClass("ca.uhn.hl7v2.model.v24.message."+ message, true)?.newInstance()		
		hl7Msg.initQuickstart("ADT", "A01", "P")
		MSH msh = hl7Msg.get('MSH')
		//MSH-3: Sending Application (HD) optional
		msh.getSendingApplication().getNamespaceID().setValue("Sending App")
		//MSH-4: Sending Facility (HD) optional
		msh.getSendingFacility().getNamespaceID().setValue("Sending Facility")
		//MSH-5: Receiving Application (HD) optional
		msh.getReceivingApplication().getNamespaceID().setValue("MARM")
		//MSH-6: Receiving Facility (HD) optional
		msh.getReceivingFacility().getNamespaceID().setValue("HL7 Generator")
		
		return hl7Msg
	}
}
