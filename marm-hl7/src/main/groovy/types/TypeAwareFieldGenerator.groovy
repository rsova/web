package types

import org.springframework.beans.factory.annotation.Value

import ca.uhn.hl7v2.model.primitive.DT
import ca.uhn.hl7v2.model.primitive.ID
import ca.uhn.hl7v2.model.primitive.IS
import ca.uhn.hl7v2.model.v24.datatype.CE
import ca.uhn.hl7v2.model.v24.datatype.CP
import ca.uhn.hl7v2.model.v24.datatype.CX
import ca.uhn.hl7v2.model.v24.datatype.DLD
import ca.uhn.hl7v2.model.v24.datatype.DLN
import ca.uhn.hl7v2.model.v24.datatype.DR
import ca.uhn.hl7v2.model.v24.datatype.EI
import ca.uhn.hl7v2.model.v24.datatype.FC
import ca.uhn.hl7v2.model.v24.datatype.FN
import ca.uhn.hl7v2.model.v24.datatype.HD
import ca.uhn.hl7v2.model.v24.datatype.JCC
import ca.uhn.hl7v2.model.v24.datatype.MO
import ca.uhn.hl7v2.model.v24.datatype.MSG
import ca.uhn.hl7v2.model.v24.datatype.NM
import ca.uhn.hl7v2.model.v24.datatype.OCD
import ca.uhn.hl7v2.model.v24.datatype.OSP
import ca.uhn.hl7v2.model.v24.datatype.PL
import ca.uhn.hl7v2.model.v24.datatype.PT
import ca.uhn.hl7v2.model.v24.datatype.SI
import ca.uhn.hl7v2.model.v24.datatype.ST
import ca.uhn.hl7v2.model.v24.datatype.TN
import ca.uhn.hl7v2.model.v24.datatype.TS
import ca.uhn.hl7v2.model.v24.datatype.UVC
import ca.uhn.hl7v2.model.v24.datatype.VID
import ca.uhn.hl7v2.model.v24.datatype.XAD
import ca.uhn.hl7v2.model.v24.datatype.XCN
import ca.uhn.hl7v2.model.v24.datatype.XON
import ca.uhn.hl7v2.model.v24.datatype.XPN
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
	
	private static final MONEY_FORMAT_INDICATORS = ["Balance", "Charges", "Adjustments","Income","Amount","Money"]
	private static final String RANGE_INDICATOR = "..."
	

	ProfileParser pp = new ProfileParser("","")
	/**
	 * Value of coded table returned as as single entry
	 * @param pp
	 * @param codeTblName
	 * @return
	 */
	protected String getCodedValue(ProfileParser pp, Map attributes) {
		List codes = pp.getCodeTable(attributes.codetable)
		def code = applyRules(codes, attributes)
		return code
	}

	/**
	 * Values and Description from code table returned as a map. 
	 * @param pp
	 * @param codeTblName
	 * @return
	 */
	protected Map getCodedMap(ProfileParser pp, Map attributes) {
		List codes = pp.getCodeTable(attributes.codetable)
		//Apply rules to find a value
		String val = applyRules(codes, attributes)
		//Match a value with description
		String discription = (val.empty)?'':codes.get(codes.findIndexOf{it.value == val}).description
		Map map = ['value':val,'descripton':discription]
		return map
	}
	
	/**
	 * Handle range values specified by '...' sequence, including empty range
	 * @param code
	 * @return
	 */
	private String applyRules(List codes, Map attributes) {
		int idx  = Math.abs(random.nextInt() % codes.size())
		String code = codes.get(idx).value
		
		if(code.contains(RANGE_INDICATOR)){ //Handle range values
			def range = code.tokenize(RANGE_INDICATOR)
			code = (range.size() > 0)?range?.get(0)?.trim():''
		}else{ //Handle length
			int codeLen = code.length()
			int maxLen = (attributes.max_length?.toInteger()!=null)?attributes.max_length?.toInteger():codeLen
			if(codeLen > maxLen){
				codes = codes.findAll{ it.value.size() <= maxLen }
				idx  = Math.abs(random.nextInt() % codes.size())
			    code = codes.get(idx).value
			}
		}
		
		return code
	}

	
	
	/**
	 * Returns randomly generated Id of required length, of single digit id
	 * @param len
	 * @return id as string
	 */
	protected String generateLengthBoundId(int maxlen){
		return generateLengthBoundId(maxlen, Math.abs(random.nextInt()).toString())
	}
	
	protected String generateLengthBoundId(int maxlen, String str){
		def idx = maxlen

		if(maxlen > str.length()){
			str = str.padRight(maxlen,'0')
			idx = str.length()
		}
		return str[0..idx-1]
	}

	/**
	 * If field is returns true, if field are Not Supported, Withdrawn Fields  or Backward Compatible (X,W,B) returns false.
	 * Conditional (C) ? 
	 * for Optional field (O) makes random choice to build it or skip
	 * @param map
	 * @return true or false
	 */
	protected boolean isAutogenerate(Map map) {
		if(map.required in ['X','W','B']) return false
		if(map.required =='R') return true
		if(map.required == 'O') return random.nextBoolean()
		//return true
	}

	// Generate HL7 CE (coded element) data type
	public void ce(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		CE ce = (CE) map?.fld
		if(map.codetable != null){
			Map codes = getCodedMap(pp, map)
			//identifier (ST) (ST)
			ce.getIdentifier().setValue(codes.value)
			//text (ST)
			ce.getText().setValue(codes.description)
			// name of coding system (IS)
			// alternate identifier (ST) (ST)
			// alternate text (ST)
			//name of alternate coding system (IS)
		}else{
			ce.getIdentifier().setValue(Math.abs(random.nextInt() % 200).toString())
		}
		println ce
	}
	
	//Generate HL7 CP (composite price) data type.
	public void cp(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
				
		CP cp = (CP) map.fld
		//price (MO) 
		mo(["fld":cp.getComponent(0), "required":"R"])
		//price type (ID)
		//from value (NM)
		//to value (NM)
		//range units (CE)
		//range type (ID)
	}
	
	//Generate HL7 CX (extended composite ID with check digit) data type.
	public void cx(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
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
	

	//Generate HL7 DLD (discharge location) data type. This type consists of the following components:
	public void dld(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		DLD dld = (DLD) map.fld
		//discharge location (ID)
		id(["fld":dld.getDischargeLocation(), required:"R"])
		//effective date (TS)
		ts(["fld":dld.getEffectiveDate(), required:"R"])
		println dld
	}

	//Generate HHL7 DLN (driver's license number) data type
	public void dln(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		DLN dln = (DLN) map.fld
		//Driver´s License Number (ST)
		dln.getDriverSLicenseNumber().setValue(String.format("%07d", (Math.abs(random.nextInt()))))// 7 Numeric, as for some states in real life
		//Issuing State, province, country (IS)
		//is(["fld":dln.getIssuingStateProvinceCountry(), "required":"R","codetable":map.codetable])
		dln.getIssuingStateProvinceCountry().setValue(allStates.get(Math.abs(random.nextInt()%allStates.size())))
		
		//expiration date (DT)
		dt(["fld":dln.getExpirationDate(),"description":"End", "required":"R"])
	}
	
	//Generates HL7 DR (date/time range) data type. 
	public void dr(Map map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		DR dr = (DR) map.fld
		//range start date/time (TS)
		ts(["fld":dr.getComponent(0), "required":"R"])		
		//range end date/time (TS)
		ts(["fld":dr.getComponent(1),"description":"End", "required":"R"])
	}
	
	// Generates an HL7 DT (date) datatype.
	public void dt(Map map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}

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
	
	// Generate HL7 EI - entity identifier
	public void ei(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		EI ei =(EI)map.fld
		ei.getEntityIdentifier().setValue(Math.abs(random.nextInt() % 20 ).toString())
		// ei.getNamespaceID().setValue("")
		// ei.getUniversalID().setValue("")
		// ei.getUniversalIDType().setValue("")
		//return (EI) map.fld
	}

	// Generate HL7 ID, usually using value from code table
	public void id(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		String val = (map.codetable != null)? getCodedValue(pp, map): Math.abs(random.nextInt() % 200).toString()
		((ID) map.fld).setValue(val)
	}

	//Generates HL7 IS (namespace id) data type
	public void is(Map map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		String val = (map.codetable != null)? getCodedValue(pp, map): Math.abs(random.nextInt() % 200).toString()
		((IS) map.fld).setValue(val)
	}
	
	//Generates HL7 FC (financial class) data type.
	public void fc(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		FC fc = (FC) map.fld
		
		//Financial Class (IS)
		is(["fld":fc.getComponent(0), "required":"R", "codetable":map.codetable])
		//Effective Date (TS) (TS)
		ts(["fld":fc.getComponent(1), "required":"R"])	
	}
	
	//Generates an HL7 FN (familiy name) data type. 
	public void fn(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		FN fc = (FN) map.fld
				
		//surname (ST)
		fc.surname.setValue(lastNames.getAt(Math.abs(random.nextInt()%lastNames.size())));
		//own surname prefix (ST)
		//own surname (ST)
		//surname prefix from partner/spouse (ST)
		//surname from partner/spouse (ST)
	}
	
	//Generates HL7 HD (hierarchic designator) data type
	public void hd(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		HD hd = (HD) map.fld
		//namespace ID (IS)
		is(["fld":hd.getComponent(0), "required":"R", "codetable":map.codetable])
		//universal ID (ST)
		//universal ID type (ID)
	}
	
	//Generates HL7 JCC (job code/class) data type.
	public void jcc(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		JCC jcc = (JCC) map.fld
		//job code (IS)
		is(["fld":jcc.getComponent(0), "required":"R", "codetable":map.codetable])
		//job class (IS)
		is(["fld":jcc.getComponent(1), "required":"R"])
		
	}
	
	//Generates HL7 MSG (Message Type) data type.
	public void msg(LinkedHashMap map){
		//message type (ID)
		//trigger event (ID)
		//message structure (ID)
		
		//Message type set while initializing MSH segment, do nothing.
		return
	}
	
	//Generates an HL7 MO (money) data type. 
	public void mo(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		MO mo = (MO) map.fld
		//quantity (NM)
		nm("fld":mo.getComponent(0), "description":"Money", "required":"R")
		//denomination (ID)
		mo.getComponent(1).setValue("USD")
	}
	
	//Generates an HL7 NM (numeric) data type. A NM contains a single String value.
	public void nm(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		NM nm = (NM) map.fld
		//money
		//if(map.description?.contains("Amount") || map.description?.contains("Money")){//check for specific numeric format Amount
		
		//if(map.description!=null && null!= moneyFormatIndicator.find(map?.description?.contains(it))){//check for specific numeric for money
		if(null!= MONEY_FORMAT_INDICATORS.find{map?.description?.contains(it)}){//check for specific numeric for money
			nm.setValue(String.format("%.2f",(Math.abs(random.nextDouble())) *1000)) //under $1,000
		}else {//quantity (NM)
			nm.setValue(Math.abs(random.nextInt() % 20).toString())
		}
	}

	//Generates an HL7 OCD (occurence) data type.
	//The code and associated date defining a significant event relating to a bill that may affect payer processing
	public void ocd(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		OCD ocd = (OCD) map.fld
		//occurrence code (IS)
		is(["fld":ocd.getComponent(0), "required":"R", "codetable":map.codetable])
		//occurrence date (DT)
		dt(["fld":ocd.getComponent(1), "required":"R"])	
		println ocd	
	}

	//Generate an HL7 OSP (occurence span) data type.
	public void osp(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		OSP ocp = (OSP) map.fld
		//occurrence span code (CE)
		ce(["fld":ocp.getComponent(0), "required":"R", "codetable":map.codetable])
		//occurrence span start date (DT)
		dt(["fld":ocp.getComponent(1), "required":"R"])
		//occurrence span stop date (DT)
		dt(["fld":ocp.getComponent(2), "description":"End","required":"R"])
	}
	
	//Generate an HL7 PL (person location) data type.
	public void pl(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		PL pl = (PL) map.fld
		//point of care (IS)
		is(["fld":pl.getComponent(0), "required":"R"])
		//room (IS)
		is(["fld":pl.getComponent(1), "required":"R"])
		//bed (IS)
		is(["fld":pl.getComponent(2), "required":"R"])
		//facility (HD) (HD)
		hd(["fld":pl.getComponent(3), "required":"R"])
		//location status (IS)
		//person location type (IS)
		//building (IS)
		is(["fld":pl.getComponent(6), "required":"R"])
		//floor (IS)
		//Location description (ST)
	}
	
	//Generate HL7 S PT (processing type) data type.
	public void pt(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		//map.maxlen
		PT pt = (PT) map.fld
		Map mp = map.clone()
		mp.fld = pt.getComponent(0)
		mp.required = "R"
		id(mp)
		//processing ID (ID)
		//processing mode (ID)
	}

	//Generate HL7 SI (sequence ID) data type. A SI contains a single String value.
	public void si(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
	    
		SI pt = (SI) map.fld
		pt.setValue(generateLengthBoundId((map.max_length)?map.max_length.toInteger():1))
	}

	//Generate HL7 ST (string data) data type. A ST contains a single String value.
	public void st(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		ST st = (ST) map.fld
		//this one returns empty string if field has not been set
//		def x  =  st.getValueOrEmpty()//number,-id, code
//		if(x.isEmpty()){
			String val = map.description
			println val
			//[Continuation Pointer, SSN Number - Patient, Birth Place, Strain, Patient Valuables Location, Observation Sub-Id, References Range, User Defined Access Checks, Allergy Reaction Code, Guarantor SSN, Job Title, UB-82 Locator 2, UB-82 Locator 9, UB-82 Locator 27, UB-82 Locator 45, Covered Days (7), Non-Covered Days (8), UB92 Locator 56 (State), UB92 Locator 57 (National)]
			
			if(val?.contains("SSN")){//if SSN
				//val = "606121126"
				val = generateLengthBoundId(9)
			}else{
				int len = (map.max_length)?map.max_length.toInteger():1
				val = generateLengthBoundId((len>5)?5:len) // numeric id up to 5 TODO: string ids?
				
//			else if(map.max_length < 4 || val.contains("Id")|| val.contains("Days")|| val.contains("Code") || val.contains("Number") ){
//				val = (Math.abs(random.nextInt()%100))// up to 3 spaces
//			}else{//use description
//				//if description does not fit use number
//				val = (val.length() > map.max_length.toInteger())?(Math.abs(random.nextInt()%100)):val
			}
			st.setValue(val)
//		}
	}
	
	//Generate HL7 TS (time stamp), within number of weeks in the future or past
	public void ts(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}

		TS ts = (TS) map.fld
		//for Time Stamp one way to figure out if event is in the future of in the past to look for key words in description
		boolean isFutureEvent = map?.description?.contains('End') //so 'Role End Date/Time'
		int seed = 52 //seed bounds duration of time to 52 weeks, a year baby...
		use(TimeCategory) {
			Duration duration = Math.abs(random.nextInt() % seed).toInteger().week
			Date evnt = (isFutureEvent)?new Date() + duration:new Date() - duration
			//time of an event (TSComponentOne)
			ts.getTimeOfAnEvent().setValue(evnt.format("YYYYMMDDHHSS.SSS"))
			//degree of precision (ST)
		}
		println ts
	}
	
	//Generate an HL7 TN (telephone number) data type. A TN contains a single String value.
	public void tn(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		TN tn = (TN) map.fld
		tn.setValue(phones.getAt(Math.abs(random.nextInt()%phones.size())))
	}
	
	//Generate an HL7 UVC (Value code and amount) data type.
	public void uvc(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}

		UVC uvc = ((UVC)map.fld)
		//value code (IS)
		is(["fld":uvc.getComponent(0), "required":"R", "codetable":map.codetable])
		//value amount (NM)
		nm(["fld":uvc.getComponent(1), "required":"R"])
	}
	
	//Generate an HL7 VID (version identifier) data type.
	public void vid(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		VID vid = ((VID)map.fld)
//		version ID (ID)
		id(["fld":vid.getComponent(0), "required":"R", "codetable":map.codetable])
		
//		internationalization code (CE)
//		international version ID (CE)
		
	}

	//Generate HL7 XAD (extended address)
	public void xad(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
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
	}
	
	// Generate HL7 XCN (extended composite ID number and name for persons)
	public void xcn(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		XCN xcn = (XCN) map.fld
		// ID number (ST) (ST)
		xcn.getIDNumber().setValue(Math.abs(random.nextInt() % 300).toString())
		// family name (FN)
		fn(['fld': xcn.getComponent(1),'required':'R'])
		//xcn.familyName.surname.value = lastNames.getAt(Math.abs(random.nextInt()%lastNames.size()));
		// given name (ST)
		//xcn.givenName.value = firstNames.getAt(Math.abs(random.nextInt()%firstNames.size()));
		xcn.getComponent(2).setValue(firstNames.getAt(Math.abs(random.nextInt()%firstNames.size())))
		// second and further given names or initials thereof (ST)
		//xcn.secondAndFurtherGivenNamesOrInitialsThereof.value ="J"
		xcn.getComponent(3).setValue("ABCDEFGHIJKLMNOPQRSTUVWXYZ".getAt(Math.abs(random.nextInt()%26)))
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
		//println xcn
	}
	
	//Generate an HL7 XON (extended composite name and identification number for organizations) data type.
	public void xon(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		XON xtn = (XON) map.fld
		
		//organization name (ST)
		st(["fld":xtn.getComponent(0), "required":"R", "codetable":map.codetable])
		//organization name type code (IS)
		//ID number (NM) (NM)
		nm(["fld":xtn.getComponent(2), "required":"R"])
		//check digit (NM) (ST)
		//code identifying the check digit scheme employed (ID)
		//assigning authority (HD)
		//identifier type code (IS) (IS)
		//assigning facility ID (HD)
		//Name Representation code (ID)
	}
	
	//Generate an HL7 XPN (extended person name) data type. This type consists of the following components:
	public void xpn(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		XPN xpn = (XPN)map.fld
		//family name (FN)
		fn(['fld': xpn.getComponent(0),'required':'R'])
		//given name (ST)
		//xpn.givenName.setValue(firstNames.getAt(Math.abs(random.nextInt()%firstNames.size())));
		xpn.getComponent(1).setValue(firstNames.getAt(Math.abs(random.nextInt()%firstNames.size())))
		//second and further given names or initials thereof (ST)
		//xpn.secondAndFurtherGivenNamesOrInitialsThereof.setValue("ABCDEFGHIJKLMNOPQRSTUVWXYZ".getAt(Math.abs(random.nextInt()%26)))
		xpn.getComponent(2).setValue("ABCDEFGHIJKLMNOPQRSTUVWXYZ".getAt(Math.abs(random.nextInt()%26)))
		//suffix (e.g., JR or III) (ST)
		//prefix (e.g., DR) (ST)
		//degree (e.g., MD) (IS)
		//name type code (ID)
		println xpn
	}
	
	//Generate HL7 XTN (extended telecommunication number)
	public void xtn(LinkedHashMap map){
		//check if the field is optional and randomly generate it of skip
		if(!isAutogenerate(map)){return}
		
		XTN xtn = (XTN) map.fld
		//[(999)] 999-9999 [X99999][C any text] (TN)
		tn(["fld":xtn.getComponent(0), 'required':'R'])
		//xtn.get9999999X99999CAnyText().setValue(phones.getAt(Math.abs(random.nextInt()%phones.size())))
		// telecommunication use code (ID)
		// telecommunication equipment type (ID) (ID)
		// Email address (ST)
		// Country Code (NM)
		// Area/city code (NM)
		// Phone number (NM)
		// Extension (NM)
		// any text (ST)
	}
		
	//[cp, cx, dld, dln, *dr, dt, fc, hd, jcc, msg, nm, ocd, osp, pl, pt, si, st, uvc, vid, xon, xpn]
	
	
			
}
