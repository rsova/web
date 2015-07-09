package types

import org.springframework.beans.factory.annotation.Value

import ca.uhn.hl7v2.model.primitive.ID
import ca.uhn.hl7v2.model.v24.datatype.CE
import ca.uhn.hl7v2.model.v24.datatype.EI
import ca.uhn.hl7v2.model.v24.datatype.TS
import ca.uhn.hl7v2.model.v24.datatype.XAD
import ca.uhn.hl7v2.model.v24.datatype.XCN
import ca.uhn.hl7v2.model.v24.datatype.XTN
import ensemble.profiles.ProfileParser
import groovy.time.Duration
import groovy.time.TimeCategory

class TypeAwareFieldGenerator {
	static final Random random = new Random()
	
	@Value('''#{'${person.names.first}'.split(',')}''') List<String> firstNames
	@Value('''#{'${person.names.last}'.split(',')}''') List<String> lastNames
	@Value('''#{'${addres.streetNames}'.split(',')}''') List<String> streetNames
	@Value('''#{'${addres.cities}'.split(',')}''') List<String> cities
	@Value('''#{'${addres.states}'.split(',')}''') List<String> states
	@Value('''#{'${addres.zips}'.split(',')}''') List<String> zips
	@Value('''#{'${address.countries}'.split(',')}''') List<String> countries
	@Value('''#{'${phones}'.split(',')}''') List<String> phones

	ProfileParser pp = new ProfileParser("","")
	
	public String getCodedValue(ProfileParser pp, String codeTblName) {
		List codes = pp.getCodeTable(codeTblName)
		int idx  = Math.abs(random.nextInt() % codes.size())
		String code = codes.get(idx).value
		return code
	}
	
	public Map getCodedMap(ProfileParser pp, String codeTblName) {
		List codes = pp.getCodeTable(codeTblName)
		int idx  = Math.abs(random.nextInt() % codes.size())
		return codes.get(idx)
	}
	
	// Generate HL7 EI - entity identifier
	public EI ei(LinkedHashMap map){
//	public EI ei(EI ei, boolean isReq = true){
		//check if the field is optional and randomly generate it of skip
		if((map.required == 'O')&&(random.nextBoolean()))return null
		
		EI ei =(EI)map.fld
		ei.getEntityIdentifier().setValue(Math.abs(random.nextInt() % 20 ).toString())
		// ei.getNamespaceID().setValue("")
		// ei.getUniversalID().setValue("")
		// ei.getUniversalIDType().setValue("")
		return (EI) map.fld
	}
	
	// Generate HL7 ID, usually using value from code table
	//public ID generateId(ID id, String code = null, boolean isReq = true){
	public ID id(LinkedHashMap map){
		if((map.required == 'O')&&(random.nextBoolean()))return null
		def alphas = ("A".."Z")
		String val = (map.codetable != null)? getCodedValue(pp, map.codetable): Math.abs(random.nextInt() % 200).toString()
		((ID) map.fld).setValue(val)
		return (ID) map.fld
	}
	
	// Generate HL7 CE
//	public CE generateCe(CE ce, String code = null, boolean isReq = true){
	public CE ce(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if((map.required == 'O')&&(random.nextBoolean()))return null
		
		CE ce = (CE) map?.fld
		if(map.codetable != null){
			Map codes = getCodedMap(pp, map.codetable)
			//identifier (ST) (ST)
			ce.getIdentifier().setValue(codes.codetable)
			//text (ST)
			ce.getText().setValue(codes.description)
			// name of coding system (IS)
			// alternate identifier (ST) (ST)
			// alternate text (ST)
			//name of alternate coding system (IS)
		}else{
			ce.getIdentifier().setValue(Math.abs(random.nextInt() % 200).toString())
		}
		return ce
	}
	
	// Generate HL7 XCN (extended composite ID number and name for persons)
//	public XCN generateXcn(XCN xcn, boolean isReq = true){
	public XCN xcn(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if((map.required == 'O')&&(random.nextBoolean()))return null
		
		XCN xcn = (XCN) map.fld
		// ID number (ST) (ST)
		xcn.getIDNumber().setValue(Math.abs(random.nextInt() % 300).toString())
		// family name (FN)
		xcn.familyName.surname.value = lastNames.getAt(Math.abs(random.nextInt()%lastNames.size()));
		// given name (ST)
		xcn.givenName.value = firstNames.getAt(Math.abs(random.nextInt()%firstNames.size()));
		// second and further given names or initials thereof (ST)
		xcn.secondAndFurtherGivenNamesOrInitialsThereof.value ="J"
		// suffix (e.g., JR or III) (ST)
		// prefix (e.g., DR) (ST)
		// degree (e.g., MD) (IS)
		// source table (IS)
		// assigning authority (HD)
		// name type code (ID)
		// identifier check digit (ST)
		// code identifying the check digit scheme employed (ID)
		// identifier type code (IS) (IS)
		// assigning facility (HD)
		// Name Representation code (ID)
		// name context (CE)
		// name validity range (DR)
		// name assembly order (ID)
		return xcn
	}
	
	//Generate HL7 TS (time stamp), within number of weeks in the future or past
//	public TS generateTs(TS ts, Integer seed, boolean isFutureEvent, boolean isReq = true){
	public TS ts(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if((map.required == 'O')&&(random.nextBoolean()))return null
		
		TS ts = (TS) map.fld
		//for Time Stamp one way to figure out if event is in the future of in the past to look for key words in description
		boolean isFutureEvent = map.description?.contains(' End ') //so 'Role End Date/Time' 
		int seed = 52 //seed bounds duration of time to 52 weeks, a year baby...
		use(TimeCategory) {
			Duration duration = Math.abs(random.nextInt() % seed).toInteger().week
			Date evnt = (isFutureEvent)?new Date() + duration:new Date() - duration
			//time of an event (TSComponentOne)
			ts.getTimeOfAnEvent().setValue(evnt)
			//degree of precision (ST)
		}
		return ts
	}
	
	//Generate HL7 XAD (extended address)
//	public XAD generateXad(XAD xad, boolean isReq = true){
	public XAD xad(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if((map.required == 'O')&&(random.nextBoolean()))return null
		
		XAD xad = (XAD) map.fld
		int idx = Math.abs(random.nextInt()%streetNames.size())
		//street address (SAD) (SAD)
		xad.getStreetAddress().getStreetName().setValue(streetNames.getAt(idx))
		//other designation (ST)
		//city (ST)
		xad.getCity().setValue(cities.getAt(idx))
		//state or province (ST)
		xad.getStateOrProvince().setValue(states.getAt(idx))
		//zip or postal code (ST)
		xad.getZipOrPostalCode().setValue(zips.getAt(idx))
		//country (ID)
		xad.getCountry().setValue(countries.getAt(idx))
		//address type (ID)
		//other geographic designation (ST)
		//county/parish code (IS)
		//census tract (IS)
		//address representation code (ID)
		//address validity range (DR)
		return xad
	}

	//Generate HL7 XTN (extended telecommunication number)
//	public XTN generateXtn(XTN xtn, boolean isReq = true){
	public XTN xtn(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if((map.required == 'O')&&(random.nextBoolean()))return null
		XTN xtn = (XTN) map.fld
		//[(999)] 999-9999 [X99999][C any text] (TN)
		xtn.get9999999X99999CAnyText().setValue(phones.getAt(Math.abs(random.nextInt()%phones.size())))
		// telecommunication use code (ID)
		// telecommunication equipment type (ID) (ID)
		// Email address (ST)
		// Country Code (NM)
		// Area/city code (NM)
		// Phone number (NM)
		// Extension (NM)
		// any text (ST)
		return xtn
	}
	
}
