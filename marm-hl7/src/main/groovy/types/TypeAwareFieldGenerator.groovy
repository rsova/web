package types

import org.springframework.beans.factory.annotation.Value

import ca.uhn.hl7v2.model.AbstractPrimitive
import ca.uhn.hl7v2.model.primitive.DT
import ca.uhn.hl7v2.model.primitive.ID
import ca.uhn.hl7v2.model.primitive.IS
import ca.uhn.hl7v2.model.v24.datatype.CE
import ca.uhn.hl7v2.model.v24.datatype.CP
import ca.uhn.hl7v2.model.v24.datatype.CX
import ca.uhn.hl7v2.model.v24.datatype.DLD
import ca.uhn.hl7v2.model.v24.datatype.DLN
import ca.uhn.hl7v2.model.v24.datatype.EI
import ca.uhn.hl7v2.model.v24.datatype.ST
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
	@Value('''#{'${states}'.split(',')}''') List<String> allStates

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
	public void id(LinkedHashMap map){
		if((map.required == 'O')&&(random.nextBoolean())){return}
		//def alphas = ("A".."Z")
		String val = (map.codetable != null)? getCodedValue(pp, map.codetable): Math.abs(random.nextInt() % 200).toString()
		((ID) map.fld).setValue(val)
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
		boolean isFutureEvent = map.description?.contains('End') //so 'Role End Date/Time' 
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
	
	//[cp, cx, dld,
	// dln, dr, dt, fc, hd, jcc, msg, nm, ocd, osp, pl, pt, si, st, uvc, vid, xon, xpn]
	
	//Generate HL7 CP (composite price) data type.
	public void cp(LinkedHashMap map){
		if((map.required == 'O')&&(random.nextBoolean())){return}
		
		CP cp = (CP) map.fld
		//price (MO)
		cp.getPrice().getQuantity().value = String.format("%.2f", (Math.abs(random.nextDouble())) *1000)  //under $1,000
		cp.getPrice().getDenomination().value = "USD"
		//price type (ID)
		//from value (NM)
		//to value (NM)
		//range units (CE)
		//range type (ID)
	}
	
	//Generate HL7 CX (extended composite ID with check digit) data type. 
	public void cx(LinkedHashMap map){
		if((map.required == 'O')&&(random.nextBoolean())){return}
		
		CX cx = (CX) map.fld
		//ID (ST)
		st(["fld":cx.getID(), description:"Number", required:"R"])
		println cx.getID()
		//check digit (ST) (ST)
		//code identifying the check digit scheme employed (ID)
		//assigning authority (HD)
		//identifier type code (ID) (ID)
		//assigning facility (HD)
		//effective date (DT) (DT)
		//expiration date (DT)
	}
	
	//Generate HHL7 DLN (driver's license number) data type
	public void dln(LinkedHashMap map){
		//if((map.required == 'O')&&(random.nextBoolean())){return}
		
		DLN dln = (DLN) map.fld
		//Driver´s License Number (ST)
		dln.getDriverSLicenseNumber().setValue(String.format("%07d", (Math.abs(random.nextInt()))))// 7 Numeric, as for some states in real life
		//Issuing State, province, country (IS)
		dln.getIssuingStateProvinceCountry().setValue(allStates.get(Math.abs(random.nextInt()%allStates.size())))
		
		//expiration date (DT)
		dt(["fld":dln.getExpirationDate(),"description":"End", "required":"R"])
		
		println dln
	}
	
	// Generates an HL7 DT (date) datatype.
	public void dt(Map map){
		boolean isFutureEvent = map.description?.contains('End') //so 'Role End Date/Time'
		int seed = 52 //seed bounds duration of time to 52 weeks, a year baby...
		use(TimeCategory) {
			Duration duration = Math.abs(random.nextInt() % seed).toInteger().week
			Date evnt = (isFutureEvent)?new Date() + duration:new Date() - duration
			//YYYY[MM[DD]]
			//String v = evnt.format( 'YYYYMMDD')
			String v = evnt.format( 'YYYYMMdd')
			((DT)map.fld).setValue(v)
		}
	}
	
	public void is(Map map){
		//if((map.required == 'O')&&(random.nextBoolean())){return}
		String val = (map.codetable != null)? getCodedValue(pp, map.codetable): Math.abs(random.nextInt() % 200).toString()
		((IS) map.fld).setValue(val)
	}
	
	//Generate HL7 DLD (discharge location) data type. This type consists of the following components:
	public void dld(LinkedHashMap map){
		if((map.required == 'O')&&(random.nextBoolean())){return}
		
		DLD dld = (DLD) map.fld
		//discharge location (ID)
		id(["fld":dld.getDischargeLocation(), required:"R"])
		//effective date (TS)
		ts(["fld":dld.getEffectiveDate(), required:"R"])
		println dld
	}
	
	//Generate HL7 ST (string data) data type. A ST contains a single String value.
	public void st(LinkedHashMap map){
		if((map.required == 'O')&&(random.nextBoolean())){return}
		
		ST st = (ST) map.fld
		//this one returns empty string if field has not been set
		def x  =  st.getValueOrEmpty()//number,-id, code
		if(x.isEmpty()){
			String val = map.description
			//[Continuation Pointer, SSN Number - Patient, Birth Place, Strain, Patient Valuables Location, Observation Sub-Id, References Range, User Defined Access Checks, Allergy Reaction Code, Guarantor SSN, Job Title, UB-82 Locator 2, UB-82 Locator 9, UB-82 Locator 27, UB-82 Locator 45, Covered Days (7), Non-Covered Days (8), UB92 Locator 56 (State), UB92 Locator 57 (National)]
			
			if(val.contains("SSN")){//if SSN
				val = "606121126"
			}else if(map.max_length < 4 || val.contains("Id")|| val.contains("Days")|| val.contains("Code") || val.contains("Number") ){
				val = (Math.abs(random.nextInt()%100))// up to 3 spaces
			}else{//use description
			    //if description does not fit use number
				val = (val.length() > map.max_length.toInteger())?(Math.abs(random.nextInt()%100)):val
			}
			st.setValue(val)
		}
	}
		
}
