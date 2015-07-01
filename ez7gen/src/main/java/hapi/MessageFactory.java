package hapi;

import java.io.IOException;
import java.util.List;

import hapi.adt.a01.AdtMessageGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.AbstractMessage;

@Component
public class MessageFactory {
	@Autowired
	AdtMessageGenerator adtMessageGenerator;
	
	public AbstractMessage generate(String version, String message, List segmentList) throws HL7Exception, IOException{
		AbstractMessage hl7Msg = adtMessageGenerator.genreateAdtA01Msg();
		return hl7Msg;
	}
}
