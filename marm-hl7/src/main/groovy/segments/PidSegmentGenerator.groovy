package segments

import org.springframework.beans.factory.annotation.Value

import ca.uhn.hl7v2.model.AbstractMessage
import ca.uhn.hl7v2.model.v24.segment.PID

class PidSegmentGenerator implements ISegmentGenerator {
	static final Random random = new Random()
	
	@Value('''#{'${person.names.first}'.split(',')}''') List<String> firstNames
	@Value('''#{'${person.names.last}'.split(',')}''') List<String> lastNames

	public AbstractMessage generate(AbstractMessage message, Map details) {
		PID pid = message.PID
		//pid.getPatientName(0).familyName.surname.value = firstName;
		int idx = Math.abs(random.nextInt() % firstNames.size())
		pid.getPatientName(0).familyName.surname.value = lastNames.getAt(idx);
		pid.getPatientName(0).givenName.value = firstNames.getAt(idx);
		
		//pid.getPatientIdentifierList(0).ID.value = pidId
		pid.getPatientIdentifierList(0).ID.value = Math.abs(random.nextInt() % 6000 + 1)

		return message
	}


}
