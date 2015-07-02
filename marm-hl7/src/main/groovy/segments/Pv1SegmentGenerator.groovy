package segments

import org.springframework.beans.factory.annotation.Value

import ca.uhn.hl7v2.model.AbstractMessage
import ca.uhn.hl7v2.model.v24.datatype.XCN
import ca.uhn.hl7v2.model.v24.segment.PV1

class Pv1SegmentGenerator implements ISegmentGenerator {
	static final Random random = new Random()
	@Value('''#{'${person.names.first}'.split(',')}''') List<String> firstNames
	@Value('''#{'${person.names.last}'.split(',')}''') List<String> lastNames

	public AbstractMessage generate(AbstractMessage message, Map details) {
		PV1 pv1 = message.PV1
		pv1.getSetIDPV1().value = "1"
		pv1.patientClass.value = 'I'
		pv1.assignedPatientLocation.building.value = Math.abs(random.nextInt() % 20 + 1)
		pv1.assignedPatientLocation.pointOfCare.value = "200"
		pv1.assignedPatientLocation.bed.value = Math.abs(random.nextInt() % 10 + 1)
		pv1.assignedPatientLocation.room.value = Math.abs(random.nextInt() % 2000 + 1)

		XCN xcn = pv1.getAdmittingDoctor(0)
		xcn.familyName.surname.value = lastNames.getAt(Math.abs(random.nextInt()%lastNames.size()));
		xcn.givenName.value = firstNames.getAt(Math.abs(random.nextInt()%firstNames.size()));
		xcn.secondAndFurtherGivenNamesOrInitialsThereof.value ="J"

		//coded values from user defined tables
		pv1.hospitalService.value = "SUR"
		pv1.admitSource.value = "ADM"
		pv1.getAmbulatoryStatus(0).value = "AO"
		return message
	}

}
