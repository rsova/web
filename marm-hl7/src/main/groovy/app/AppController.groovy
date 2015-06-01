package app

import hapi.ContextGenerator
import hapi.MessageFactory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

import ensemble.profiles.ProfileParser

@RestController
@ComponentScan("hapi")
public class AppController {
	@Autowired
	MessageFactory messageFactory
	
	@Autowired
	ContextGenerator contextGenerator
	//AdtMessageGenerator adtMessageGenerator
	
    @RequestMapping("/")
    public String index() {
        return "HL7 Test Generator is up and running";
    }		
	
	@RequestMapping(value="/generate/{version}/{message}")
	@ResponseBody	
	public Map generate( @PathVariable String version, @PathVariable String message){
	
		System.out.println("Generate version: " + version +" message: " + message )
		List segmentList = new ProfileParser(version, message).coreSegments
		
	    def hl7Msg = messageFactory.generate(version, message, segmentList)
		return ['success':true, message: contextGenerator.outAsEr7(hl7Msg)]
	}	

}
