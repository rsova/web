package hapi

import org.springframework.stereotype.Component;

import ca.uhn.hl7v2.DefaultHapiContext
import ca.uhn.hl7v2.HapiContext
import ca.uhn.hl7v2.model.AbstractMessage
import ca.uhn.hl7v2.parser.Parser

@Component
class ContextGenerator {

	public String outAsEr7(AbstractMessage hl7Msg){
		HapiContext context = new DefaultHapiContext();
		Parser parser = context.getPipeParser();
		String encodedMessage = parser.encode(hl7Msg);
		System.out.println("Printing ER7 Encoded Message:");
		System.out.println(encodedMessage);
		return encodedMessage
	}
}
