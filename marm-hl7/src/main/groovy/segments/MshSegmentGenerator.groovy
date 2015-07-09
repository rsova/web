package segments

import java.util.List;

import ca.uhn.hl7v2.model.AbstractMessage
import ca.uhn.hl7v2.model.v24.segment.MSH

class MshSegmentGenerator implements ISegmentGenerator {
	static final Random random = new Random()
	
	public AbstractMessage generate(AbstractMessage message, String segment, List details) {
		MSH mshSegment = message.getMSH();
		mshSegment.getSendingApplication().getNamespaceID().setValue("MARMSendingSystem");
		mshSegment.getSequenceNumber().setValue("123")
		return message
	}

}
