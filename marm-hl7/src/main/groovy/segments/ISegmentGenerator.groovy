package segments

import ca.uhn.hl7v2.model.AbstractMessage;

interface ISegmentGenerator {
	public AbstractMessage generate(AbstractMessage message, String segment, List attributes);
}
