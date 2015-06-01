package hapi;

import hapi.adt.a01.AdtMessageGenerator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component;

import ca.uhn.hl7v2.model.AbstractMessage

@Component
public class MessageFactory {
	@Autowired
	AdtMessageGenerator adtMessageGenerator
	
	public AbstractMessage generate(String version, String message, List segmentList){
		AbstractMessage hl7Msg = adtMessageGenerator.genreateAdtA01Msg()
		return hl7Msg
	}
}
