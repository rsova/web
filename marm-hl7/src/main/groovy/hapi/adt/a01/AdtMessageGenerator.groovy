package hapi.adt.a01
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
	
	@Value('${pid.name.first}')
	String firstName
	@Value('${pid.name.last}')
	String lastName
	@Value('${pid.id}')
	String pidId
	
	static final Random random = new Random()
	
//	public String genreateAdtA01(){
//		
//		// Generate ADT message
//		ADT_A01 adt = generateAdt();
//
//		// Populate the MSH Segment
//		generateMsh(adt);
//		
//		// Populate the PID Segment
//		generatePid(adt)
//
//		// Now, let's encode the message and look at the output
//		HapiContext context = new DefaultHapiContext();
//		Parser parser = context.getPipeParser();
//		String encodedMessage = parser.encode(adt);
//		System.out.println("Printing ER7 Encoded Message:");
//		System.out.println(encodedMessage);
//
//		return encodedMessage
//	}
	
	public  genreateAdtA01Msg(){
		
		// Generate ADT message
		ADT_A01 adt = generateAdt();
		
		// Populate the MSH Segment
		generateMsh(adt);
		
		// Populate the PID Segment
		generatePid(adt)
		
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
		pid.getPatientName(0).familyName.surname.value = firstName;
		pid.getPatientName(0).givenName.value = lastName;
		//pid.getPatientIdentifierList(0).ID.value = pidId
		pid.getPatientIdentifierList(0).ID.value = Math.abs(random.nextInt() % 6000 + 1)

	}
	
	private generatePv1(ADT_A01 adt) {
		PV1 pv1 = adt.PV1
		//PV1|1|I|2000^2012^01||||004777^GOOD^SIDNEY^J.|||SUR||||ADM|A0|
		//IS i = new IS()
		pv1.getSetIDPV1().value = "1"
		pv1.patientClass.value = 'I'
		pv1.assignedPatientLocation.building.value = "400"
		pv1.assignedPatientLocation.pointOfCare.value = "200"
		pv1.assignedPatientLocation.bed.value = "2"
		pv1.assignedPatientLocation.room.value = "2012"
		
		XCN xcn = pv1.getAdmittingDoctor(0)
		xcn.familyName.surname.value = "Sidney"
		xcn.givenName.value = "Good"
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
