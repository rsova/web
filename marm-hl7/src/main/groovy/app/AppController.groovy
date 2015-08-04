package app

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

import service.ContextGenerator
import service.MessageFactory
import ensemble.profiles.ProfileParser

@RestController
@ComponentScan(["hapi", "service","segments"])
//@ComponentScan(["com.my.package.first","com.my.package.second"])
public class AppController {
	@Autowired
	MessageFactory messageFactory

	@Autowired
	ContextGenerator contextGenerator
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
	public Map generate1( @PathVariable String version, @PathVariable String message){
		String response = 'success'
		String msg = ''

		try {
			System.err.println("Generate version: " + version +" message: " + message )
			
			
			def hl7Msg = messageFactory.generate(version, message)
			msg = contextGenerator.outAsEr7(hl7Msg)
			msg = msg.replace('\r','\n' )

		} catch (Exception e) {
			println e.message
			response = 'error'
			msg = "Err, Somethng went the wrong way... Version: $version , Message: $message"
		}

		return [ "$response": true, message: msg ]

	}

}
