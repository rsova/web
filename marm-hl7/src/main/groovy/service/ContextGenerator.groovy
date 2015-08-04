package service

import org.springframework.stereotype.Component

import ca.uhn.hl7v2.DefaultHapiContext
import ca.uhn.hl7v2.HL7Exception
import ca.uhn.hl7v2.HapiContext
import ca.uhn.hl7v2.model.AbstractMessage
import ca.uhn.hl7v2.parser.Parser
import ca.uhn.hl7v2.validation.impl.ValidationContextFactory

@Component
class ContextGenerator {

	public String outAsEr7(AbstractMessage hl7Msg, boolean validate=false){
		HapiContext context = new DefaultHapiContext();
		Parser parser = context.getPipeParser();
		String encodedMessage = null

		if(validate)// set validator
			context.setValidationContext(ValidationContextFactory.defaultValidation());
		
		try {
			encodedMessage = parser.encode(hl7Msg);
			//System.out.println("Successfully parsed valid message");
		} catch (HL7Exception e) {
			// This shouldn't happen!
			System.out.println("Something went wrong!");
			System.exit(-1);
		}

		return encodedMessage
	}
}
