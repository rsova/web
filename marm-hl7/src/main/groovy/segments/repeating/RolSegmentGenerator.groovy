package segments.repeating

import org.springframework.beans.factory.annotation.Value

import segments.ISegmentGenerator
import types.TypeAwareFieldGenerator
import ca.uhn.hl7v2.model.AbstractMessage
import ca.uhn.hl7v2.model.AbstractSegment
import ca.uhn.hl7v2.model.primitive.ID
import ca.uhn.hl7v2.model.v24.datatype.CE
import ca.uhn.hl7v2.model.v24.datatype.EI
import ca.uhn.hl7v2.model.v24.datatype.TS
import ca.uhn.hl7v2.model.v24.datatype.XAD
import ca.uhn.hl7v2.model.v24.datatype.XCN
import ca.uhn.hl7v2.model.v24.datatype.XTN
import ca.uhn.hl7v2.model.v24.segment.ROL


class RolSegmentGenerator implements ISegmentGenerator {
	static final Random random = new Random()
	@Value('''#{'${person.names.first}'.split(',')}''') List<String> firstNames
	@Value('''#{'${person.names.last}'.split(',')}''') List<String> lastNames
	
	@Value('''#{'${addres.streetNames}'.split(',')}''') List<String> streetNames
	@Value('''#{'${addres.cities}'.split(',')}''') List<String> cities
	@Value('''#{'${addres.states}'.split(',')}''') List<String> states
	@Value('''#{'${addres.zips}'.split(',')}''') List<String> zips
	@Value('''#{'${address.countries}'.split(',')}''') List<String> countries
	@Value('''#{'${phones}'.split(',')}''') List<String> phones

	TypeAwareFieldGenerator fieldGenerator = new TypeAwareFieldGenerator()
	
//	public AbstractMessage generate(AbstractMessage message, List details) {
//		Map map = [:]
//		String v = message.getVersion()
//		//TODO: Properly initiate the parser, inject?
//		ProfileParser pp = new ProfileParser("","")
//		//pp.getCodeTable(
//	//	for(int i; i<2;i++){
//			ROL rol = message.getROL(0)
//			//1 Role Instance ID, EI, R
//			rol.getRol1_RoleInstanceID().getEntityIdentifier().setValue(Math.abs(random.nextInt() % 20 ).toString())
//			
//			//2 Action Code, ID, R
//			((ID)rol.getTypedField(2,0)).setValue(getCodedValue(pp, details.get(1)?.codetable))
//
//			//3 Role-ROL, datatype:CE, required:R
//			map = getCodedMap(pp, details.get(2)?.codetable)
//			((CE)rol.getTypedField(3,0)).getIdentifier().setValue(getCodedValue(pp, details.get(1)?.codetable))
//			((CE)rol.getTypedField(3,0)).getAlternateText().setValue(map.description)
//
//			//4 Role Person, datatype:XCN, required:R
//			XCN xcn = ((XCN)rol.getTypedField(4,0))
//			handlePersonXCN(xcn)
//			//5 Role Begin Date/Time, datatype:TS, required:O
//			if(random.nextBoolean()){
//				use(TimeCategory) {
//					((TS)rol.getTypedField(5,0)).getTimeOfAnEvent().setValue(new Date() - Math.abs(random.nextInt() % 52).toInteger().week)
//				}
//			}
//			//6 Role End Date/Time, datatype:TS, required:O
//			if(random.nextBoolean()){
//				use(TimeCategory) {
//					((TS)rol.getTypedField(6,0)).getTimeOfAnEvent().setValue(new Date() + Math.abs(random.nextInt() % 52).toInteger().week)
//				}
//			}
//			
//			//7 Role Duration, datatype:CE, required:O
//			if(random.nextBoolean()){
//				((CE)rol.getTypedField(7,0)).getAlternateIdentifier().setValue(Math.abs(random.nextInt() % 100).toString())
//			}
//
//			//8 Role Action Reason datatype:CE, required:O
//			if(random.nextBoolean()){
//				((CE)rol.getTypedField(8,0)).getAlternateIdentifier().setValue("Not Documented")
//			}
//
//			//9 Provider Type, datatype:CE, required:O
//			if(random.nextBoolean()){
//				((CE)rol.getTypedField(9,0)).getAlternateIdentifier().setValue("Not Documented")
//			}
//
//			//10 Organization Unit Type - ROL, datatype:CE, required:O
//			//if(random.nextBoolean()){
//			if(true){
//				map = getCodedMap(pp, details.get(9)?.codetable)
//				((CE)rol.getTypedField(10,0)).getIdentifier().setValue(map.value)
//				((CE)rol.getTypedField(10,0)).getAlternateText().setValue(map.description)
//			}
//
//			//11 Office/Home Address, datatype:XAD, required:O
//			if(random.nextBoolean()){
//				handleXAD((XAD)rol.getTypedField(11,0))
//			}
//
//			//12 Phone, datatype:XTN, required:O
//			if(random.nextBoolean()){
//				((XTN)rol.getTypedField(12,0)).get9999999X99999CAnyText().setValue(phones.getAt(Math.abs(random.nextInt()%phones.size())))
//			}
//
//			
//	//	}
////		pv1.getSetIDPV1().value = "1"
////		pv1.patientClass.value = 'I'
////		pv1.assignedPatientLocation.building.value = Math.abs(random.nextInt() % 20 + 1)
////		pv1.assignedPatientLocation.pointOfCare.value = "200"
////		pv1.assignedPatientLocation.bed.value = Math.abs(random.nextInt() % 10 + 1)
////		pv1.assignedPatientLocation.room.value = Math.abs(random.nextInt() % 2000 + 1)
////
////		XCN xcn = pv1.getAdmittingDoctor(0)
////		xcn.familyName.surname.value = lastNames.getAt(Math.abs(random.nextInt()%lastNames.size()));
////		xcn.givenName.value = firstNames.getAt(Math.abs(random.nextInt()%firstNames.size()));
////		xcn.secondAndFurtherGivenNamesOrInitialsThereof.value ="J"
////
////		//coded values from user defined tables
////		pv1.hospitalService.value = "SUR"
////		pv1.admitSource.value = "ADM"
////		pv1.getAmbulatoryStatus(0).value = "AO"
//		return message
//	}
	
	public AbstractMessage generate1(AbstractMessage message, List details, boolean isRepeating) {
				
		ROL rol = message.getROL(0)
		
		//1 Role Instance ID, EI, R
		fieldGenerator.ei(((EI)rol.getTypedField(1,0)))
		
		//2 Action Code, ID, R
		fieldGenerator.id((ID)rol.getTypedField(2,0), details.get(1)?.codetable)
		
		//3 Role-ROL, datatype:CE, required:R
		fieldGenerator.ce((CE)rol.getTypedField(3,0), details.get(2)?.codetable)
				
		//4 Role Person, datatype:XCN, required:R
		fieldGenerator.xcn(((XCN)rol.getTypedField(4,0)))
		
		//5 Role Begin Date/Time, datatype:TS, required:O
		fieldGenerator.ts((TS)rol.getTypedField(5, 0), 52, false, random.nextBoolean()) // range of 52 weeks in the past
		
		//6 Role End Date/Time, datatype:TS, required:O
		fieldGenerator.ts((TS)rol.getTypedField(6, 0), 12, false,  random.nextBoolean()) // range of 12 weeks in the future
		
		//7 Role Duration, datatype:CE, required:O
		fieldGenerator.ce((CE)rol.getTypedField(7,0), null, random.nextBoolean())
		
		//8 Role Action Reason datatype:CE, required:O
		fieldGenerator.ce((CE)rol.getTypedField(8,0), null,  random.nextBoolean())
		
		//9 Provider Type, datatype:CE, required:O
		fieldGenerator.ce((CE)rol.getTypedField(9,0), null, random.nextBoolean())
		
		//10 Organization Unit Type - ROL, datatype:CE, required:O
		fieldGenerator.ce((CE)rol.getTypedField(10,0), details.get(9)?.codetable, random.nextBoolean())
				
		//11 Office/Home Address, datatype:XAD, required:O
		fieldGenerator.xad((XAD)rol.getTypedField(11,0), random.nextBoolean())
		
		//12 Phone, datatype:XTN, required:O
		fieldGenerator.xtn((XTN)rol.getTypedField(12,0), random.nextBoolean())
		
		return message
	}
	
	public AbstractMessage generate(AbstractMessage message, String segment, List details) {

		int totalReps = (false)? Math.abs(random.nextInt() % 5 + 1):1
		for(int i=0;i<totalReps;i++){
			ROL rol = message.getROL(i)
			generateSegment(details, rol)
		}

		return message
	}
			
	// Generate a Segment based on list of attributes for each field
	// Method uses reflection to call particular type of field implementation
	public void generateSegment(List details, AbstractSegment rol) {
		
		for(int i=0; i<details.size(); i++){
			def attributes = details.get(i)
			
			//Get field by position and add it to the map, which will be sent to generator
			def fld = rol.getTypedField(attributes?.piece?.toInteger(), 0)
			Map map = ['fld':fld]
			map.putAll(attributes)
			
			//Derive the method name from the data type, and call generator method via 'reflection'
			def methodName = attributes.datatype?.toLowerCase()
			//fieldGenerator."${methodName}"(map)
			try{
				fieldGenerator."${methodName}"(map)
			}catch (Exception e){
				println e.message
			}
		}
	}
		

}
