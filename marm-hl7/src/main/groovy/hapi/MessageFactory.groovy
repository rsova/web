package hapi;

//import ca.uhn.hl7v2.model.v24.message.ADT_A01;


import java.util.List;

import hapi.adt.a01.AdtMessageGenerator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import segments.MagicSegmentGenerator
import service.generator.SmartSegmentGeneratorService
import service.picker.SmartSegmentPickerService
import ca.uhn.hl7v2.model.AbstractMessage
import ca.uhn.hl7v2.model.Structure
import ca.uhn.hl7v2.model.v24.segment.MSH
import ensemble.profiles.ProfileParser

@Component
public class MessageFactory {
	public static Set set = []
	@Autowired
	AdtMessageGenerator adtMessageGenerator
	
	@Autowired
	MagicSegmentGenerator magicGenerator
	
	@Autowired
	SmartSegmentPickerService segmentPicker
	
	public AbstractMessage generate(String version, String message, List segmentList){
		AbstractMessage hl7Msg = adtMessageGenerator.genreateAdtA01Msg()
		return hl7Msg
	}
	
	public AbstractMessage generate1(String version, String message){
		//
		AbstractMessage hl7Msg = init(version, message)
		ProfileParser parser = new ProfileParser(version, message)
//		0: [[~PD1~], ~PD1~]
//		1: [[~{~ROL~}~], ~{~ROL~}~]
//		2: [[~{~NK1~}~], ~{~NK1~}~]
//		3: [[~PV2~], ~PV2~]		
		Map segmentsMap = parser.getSegments()	
					
		// Get list of non required segments randomly selected for this build
		List segments = segmentPicker.init(segmentsMap).pickSegments()
		
		for (segment in segments) {
			List attributes = parser.getSegmentStructure(segment)
			hl7Msg = magicGenerator.generate(hl7Msg, segment, attributes)			
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
