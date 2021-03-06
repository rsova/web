package app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import hapi.ContextGenerator;
import hapi.MessageFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ca.uhn.hl7v2.model.AbstractMessage;
import ensemble.profiles.ProfileParser;

@RestController
@ComponentScan("hapi")
public class AppController {
	@Autowired
	MessageFactory messageFactory;

	@Autowired
	ContextGenerator contextGenerator;
	//AdtMessageGenerator adtMessageGenerator

	@RequestMapping("/resource")
	public Map<String,Object> home() {
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello World");
		return model;
	}
	
	@RequestMapping(value="/generate/{version}/{message}")
	@ResponseBody
	public Map generate( @PathVariable String version, @PathVariable String message){
		String response = "success";
		String msg = "";
		
		try {
			System.err.println("Generate version: " + version +" message: " + message );
			
			List segmentList = new ProfileParser(version, message).getCoreSegments();

			AbstractMessage hl7Msg = messageFactory.generate(version, message, segmentList);
			msg = contextGenerator.outAsEr7(hl7Msg);
			msg = msg.replace('\r','\n' );

		} catch (Exception e) {
		    System.out.println(e.getMessage());
			response = "error";
			msg = "Err, Somethng went the wrong way... Version: " + version + "Message: " + message;
		}
		return (Map) ((Map<String, Object>) new HashMap().put(response, Boolean.TRUE)).put(message, msg);
		//return [ response: true, message: msg ]
		
	}

}
