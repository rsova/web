package hapi.adt.a01
//import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

import ca.uhn.hl7v2.model.v24.datatype.XCN
import ca.uhn.hl7v2.model.v24.message.ADT_A01
import ca.uhn.hl7v2.model.v24.segment.MSH
import ca.uhn.hl7v2.model.v24.segment.PID
import ca.uhn.hl7v2.model.v24.segment.PV1

@Component
@Configuration
class AdtMessageGenerator {
	static final Random random = new Random()
	
//	@Value('${pid.name.first}')
//	String firstName
//	@Value('${pid.name.last}')
//	String lastName
	@Value('${pid.id}')
	String pidId
	
	//@Value("#{T(java.util.Arrays)#{'${person.names.last}'.split(',')}}")	List<String> firstNames;
	@Value('''#{'${person.names.first}'.split(',')}''') List<String> firstNames
	@Value('''#{'${person.names.last}'.split(',')}''') List<String> lastNames
	
	
	public  genreateAdtA01Msg(){
		
		// Generate ADT message
		ADT_A01 adt = generateAdt();
		
		// Populate the MSH Segment
		generateMsh(adt);
		
		// Populate the PID Segment
		generatePid(adt)
		
		// Poputate PV1 Segment
		generatePv1(adt)
		
		return adt
	}

	private ADT_A01 generateAdt() {
		ADT_A01 adt = new ADT_A01();
		adt.initQuickstart("ADT", "A01", "P")
		return adt
	}

	private generatePid(ADT_A01 adt) {
		PID pid = adt.PID
		//pid.getPatientName(0).familyName.surname.value = firstName;
//		int sz = fNames.split(',').length
		int idx = Math.abs(random.nextInt() % firstNames.size() + 1)		
		pid.getPatientName(0).familyName.surname.value = lastNames.getAt(idx);
		pid.getPatientName(0).givenName.value = firstNames.getAt(idx);
		
		//pid.getPatientIdentifierList(0).ID.value = pidId
		pid.getPatientIdentifierList(0).ID.value = Math.abs(random.nextInt() % 6000 + 1)

	}
	
	private generatePv1(ADT_A01 adt) {
		PV1 pv1 = adt.PV1
		//PV1|1|I|2000^2012^01||||004777^GOOD^SIDNEY^J.|||SUR||||ADM|A0|
		//IS i = new IS()
		pv1.getSetIDPV1().value = "1"
		pv1.patientClass.value = 'I'
		pv1.assignedPatientLocation.building.value = Math.abs(random.nextInt() % 20 + 1)
		pv1.assignedPatientLocation.pointOfCare.value = "200"
		pv1.assignedPatientLocation.bed.value = Math.abs(random.nextInt() % 10 + 1)
		pv1.assignedPatientLocation.room.value = Math.abs(random.nextInt() % 2000 + 1)
		
		XCN xcn = pv1.getAdmittingDoctor(0)
		xcn.familyName.surname.value = lastNames.getAt(Math.abs(random.nextInt()%lastNames.size() + 1));
		xcn.givenName.value = firstNames.getAt(Math.abs(random.nextInt()%firstNames.size() + 1));
		xcn.secondAndFurtherGivenNamesOrInitialsThereof.value ="J"
		
		//coded values from user defined tables
		pv1.hospitalService.value = "SUR"
		pv1.admitSource.value = "ADM"		
		pv1.getAmbulatoryStatus(0).value = 'AO'
	}

	private generateMsh(ADT_A01 adt) {
		MSH mshSegment = adt.getMSH();
		mshSegment.getSendingApplication().getNamespaceID().setValue("MARMSendingSystem");
		mshSegment.getSequenceNumber().setValue("123")
	}
}
