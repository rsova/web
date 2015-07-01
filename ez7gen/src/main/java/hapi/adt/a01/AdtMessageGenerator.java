package hapi.adt.a01;
//import org.springframework.beans.factory.annotation.Value
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v24.datatype.XCN;
import ca.uhn.hl7v2.model.v24.message.ADT_A01;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.model.v24.segment.PID;
import ca.uhn.hl7v2.model.v24.segment.PV1;

@Component
@Configuration
public class AdtMessageGenerator {
	static final Random random = new Random();
	
//	@Value('${pid.name.first}')
//	String firstName
//	@Value('${pid.name.last}')
//	String lastName
//	@Value('${pid.id}')
//	String pidId
	
	//@Value("#{T(java.util.Arrays)#{'${person.names.last}'.split(',')}}")	List<String> firstNames;
//	@Value('''#{'${person.names.first}'.split(',')}''') List<String> firstNames
//	@Value('''#{'${person.names.last}'.split(',')}''') List<String> lastNames
	@Value("#{'${person.names.first}'.split(',')}") List<String> firstNames;
	@Value("#{'${person.names.last}'.split(',')}") List<String> lastNames;
	
	
	public  ADT_A01 genreateAdtA01Msg() throws HL7Exception, IOException{
		
		// Generate ADT message
		ADT_A01 adt = generateAdt();
		
		// Populate the MSH Segment
		generateMsh(adt);
		
		// Populate the PID Segment
		generatePid(adt);
		
		// Poputate PV1 Segment
		generatePv1(adt);
		
		return adt;
	}

	private ADT_A01 generateAdt() throws HL7Exception, IOException {
		ADT_A01 adt = new ADT_A01();
		adt.initQuickstart("ADT", "A01", "P");
		return adt;
	}

	private PID generatePid(ADT_A01 adt) throws DataTypeException {
		PID pid = adt.getPID();
		//pid.getPatientName(0).familyName.surname.value = firstName;
//		int sz = fNames.split(',').length
		int idx = Math.abs(random.nextInt() % firstNames.size() + 1);		
		pid.getPatientName(0).getFamilyName().getSurname().setValue(lastNames.get(idx));
		pid.getPatientName(0).getGivenName().setValue(firstNames.get(idx));
		
		//pid.getPatientIdentifierList(0).ID.value = pidId
		pid.getPatientIdentifierList(0).getID().setValue( String.valueOf(Math.abs(random.nextInt() % 6000 + 1)));
		return pid;

	}
	
	private PV1 generatePv1(ADT_A01 adt) throws DataTypeException {
		PV1 pv1 = adt.getPV1();
		//PV1|1|I|2000^2012^01||||004777^GOOD^SIDNEY^J.|||SUR||||ADM|A0|
		//IS i = new IS()
		pv1.getSetIDPV1().setValue("1");
		pv1.getPatientClass().setValue("I");
		pv1.getAssignedPatientLocation().getBuilding().setValue(String.valueOf(Math.abs(random.nextInt() % 20 + 1)));
		pv1.getAssignedPatientLocation().getPointOfCare().setValue("200");
		pv1.getAssignedPatientLocation().getBed().setValue(String.valueOf(Math.abs(random.nextInt() % 10 + 1)));
		pv1.getAssignedPatientLocation().getRoom().setValue(String.valueOf(Math.abs(random.nextInt() % 2000 + 1)));
		
		XCN xcn = pv1.getAdmittingDoctor(0);
		xcn.getFamilyName().getSurname().setValue( lastNames.get(Math.abs(random.nextInt()%lastNames.size() + 1)));
		xcn.getGivenName().setValue(firstNames.get(Math.abs(random.nextInt()%firstNames.size() + 1)));
		xcn.getSecondAndFurtherGivenNamesOrInitialsThereof().setValue("J");
		
		//coded values from user defined tables
		pv1.getHospitalService().setValue("SUR");
		pv1.getAdmitSource().setValue("ADM");		
		pv1.getAmbulatoryStatus(0).setValue("AO");
		return pv1;
	}

	private MSH generateMsh(ADT_A01 adt) throws DataTypeException {
		MSH mshSegment = adt.getMSH();
		mshSegment.getSendingApplication().getNamespaceID().setValue("MARMSendingSystem");
		mshSegment.getSequenceNumber().setValue("123");
		return mshSegment;
	}
}
