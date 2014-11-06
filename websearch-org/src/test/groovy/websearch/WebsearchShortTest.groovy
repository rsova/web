package websearch;

import org.custommonkey.xmlunit.*
import org.junit.*

import websearch.rpc.helper.XmlRpcHelper
import websearch.xml.helper.IgnoreNamedElementsDifferenceListener

//WhatPage	 		1	Page of data to return
//WhatNumber		The maximum number of MLS numbers that can be passed is 200.	200	Number of listings to return //Max number is 200
//WhatMLS	 		None	//Comma delimited list of mls numbers.
//WhatPropType		Array: Residential, Vacant, Land, Condominium, Commercial, Business, Time Interval, Multi-Dwelling Res	//If no value is passed, the default is all property types. If WhatCondo is passed, the default is Condominium. If WhatOhana is passed, the default is Residential.
//WhatAddressFull	None	Any part of an address
//WhatWater	 		All  	Array: OceanFront,BeachFront, Across Street from Ocean
//WhatOhana	 		All  	Array: Single Family, Single Family w/Att Ohana, SF w/Det Ohana or Cottage
//WhatCondo	 		All  	Array: See Condo List
//WhatStartPrice	No		Lower Limit	Price (Integer)
//WhatEndPrice	 	No 		Upper Limit	Price (Integer)

//WhatView	 		All		Array: Ocean, Mountain, Mountain/Ocean, Golf Course, Garden View
//WhatDistrict	 	All 	Array: See District List
//WhatLandTenure	All		Array: Fee Simple, Leasehold ,Leasehold-FA
//WhatAgent			All		Array: See Agent List (Use agent ID number)

//WhatOffice	 	All		Array: See Office List (Use Office ID Number)
//WhatStartBed	 	No 		Lower Limit	Integer
//WhatEndBed	 	No 		Upper Limit	Integer
//WhatStartBath	 	No 		Lower Limit	Integer
//WhatEndBath	 	No 		Upper Limit	Integer

//WhatStartIntArea	No 		Lower Limit	Integer (Square Feet)
//WhatEndIntArea	No 		Upper Limit	Integer (Square Feet)
//WhatStartExtArea	No 		Lower Limit	Integer (Square Feet)
//WhatEndExtArea	No 		Upper Limit	Integer (Square Feet)
//WhatDiv	 		Any?		Array: ?
//WhatZoneAny	 	Any?		Array: ?
//WhatSec	 		Any?		Array: ?
//WhatPlat	 		Any?		Array: ?
//WhatPar	 		Any?		Array: ?
//WhatStartDate	 	Any		List Date in the format: Year-Month-Day (xxxx-xx-xx)
//WhatEndDate	 	Any?		List Date in the format: Year-Month-Day (xxxx-xx-xx)
//WhatSortType1	 	MLS #	You may sort by any field returned.
//WhatSortDirection1|Ascending	Direction to sort by, ASC or DESC
//WhatSortType2	 	None	You may include a secondary sort using any field returned.
//WhatSortDirection2|None	Direction for secondary sort, ASC or DESC
//ExAgency	 		Yes?MAKES NO DIFF(RS)		Include exclusive agency listings in the query.
//These values represent two corners of a rectangle. Any listings who's Latitude and Longitude fall within these values are returned.
//WhatLat1	 		None	
//WhatLon1	 		None
//WhatLat2	 		None?MAKES NO DIFF(RS)
//WhatLon2	 		None?MAKES NO DIFF(RS)
//MeFirst	 		N	If set to Y, the Agent or Office listing will be returned first.

//final static def METHOD ='<methodCall><methodName>short</methodName><params><param><value><struct><member><name>dbid</name><value><string>dbid1139259934</string></value></member><member><name>WhatPropType</name><value><array><data><value><string>Residential</string></value></data></array></value></member></struct></value></param></params></methodCall>';
class WebsearchShortTest extends XMLTestCase {
	final static def WS='http://websearch.ramidx.com/smartframe/ramxml.php'
	final static def METHOD ='short'
	static final String SRC_TEST_RESOURCES = 'src/test/resources/'
	static final String TIME_TO_RUN_DOUBLE = '/methodResponse[1]/params[1]/param[1]/value[1]/array[1]/data[1]/value[4]/double[1]/text()[1]'
	static final String DBID = 'dbid1139259934'//dbid1142571627
	//un-comment this to skip xml compare
	final boolean SKIP_CMP
	
	def curl = []
		
//	@Before
	void setUp(){
		curl = ['curl',
			'-H', 'Cookie: WebSearchID=r2so30lip7g5rp8ibonnej9ha2, D_SID=74.123.217.146:3rK08PPRKVcCOWmibSyxVs9T5+e0ovpplUhtdFEsY+4; D_PID=3119DF0B-3C06-308A-88B4-6118E4B86D16; D_IID=0B90D182-B361-3247-BBB8-F4E25374A8DD; D_UID=2DF8F0F0-54E8-3AC8-9B9F-566FA9407FDA; D_HID=D/r38/ISyXBwlPemp21MktzpUBkVEfdJwsLQeFu/JaM',
			'-H','Content-Type:text/xml',
			'-H', 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0',
			//'data',
		   WS]
		super.setUp()
	}
	
	//@After
	void tearDown() {
		curl = null;
		super.tearDown();
	}
	
	
	void test_lat1_lon1_sortdirection1_number() {
		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatLat1':'20.0','WhatLon1':'-156.0','WhatSortType1':'ListPrice','WhatSortDirection1':'ASC','WhatNumber':'5']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)

	}
	
	void test_startdate_enddate_sortype1_sortdirection1_sortype2_sortdirection2_number() {

		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID, 'WhatStartDate':'2014-08-10','WhatSortType1':'ListPrice','WhatSortDirection1':'ASC','WhatSortType2':'MLS','WhatSortDirection2':'DESC','WhatNumber':'5']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)

	}

	void test_startintarea_endintarea_startextarea_endextarea_number() {
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatStartIntArea':'800','WhatEndIntArea':'2000','WhatStartExtArea':'200','WhatEndExtArea':'1000', 'WhatNumber':'2']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}
	
	void test_condo_agent_startbed_endbed_startbath_endbath_number() {
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatCondo':['Pacific Shores'],'WhatAgent':['12496'],'WhatStartBed':'1','WhatEndBed':'2', 'WhatStartBath':'1','WhatEndBath':'2', 'WhatNumber':'2']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}

	void test_ohana_endprice_number() {
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatOhana':['Single Family'],'WhatEndPrice':'6700000','WhatNumber':'2']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}
	
	void test_view_district_tenure_endprice_number() {
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatView':['Ocean',' Mountain'],'WhatDistrict':['Wailea/Makena'],'WhatLandTenure':['Fee Simple', 'Leasehold'],'WhatEndPrice':'700000','WhatNumber':'2']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}
	//WhatView	 		All		Array: Ocean, Mountain, Mountain/Ocean, Golf Course, Garden View
	//WhatDistrict	 	All 	Array: See District List
	//WhatLandTenure	All		Array: Fee Simple, Leasehold ,Leasehold-FA
	
	void test_water_startprice_number() {
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatWater':['BeachFront'],'WhatStartPrice':'3700000','WhatNumber':'2']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}

	void test_type_addressfull() {
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':['Condominium'],'WhatAddressFull':'Bay DR']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}
	
	void test_msl() {		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID, 'WhatMLS':['361066', '361300']]))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}

	void test_type_page_number() {
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':['Commercial'], 'WhatPage':'1', 'WhatNumber':'2']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}
	
	void test_type_number() {
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':['Multi-Dwelling Res'], 'WhatNumber':'2']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}

	private compareXml(String actual, String control = null) {
		//create control file with the name similar to the test
		def test = getName().replaceFirst('test_','')
		//check if xml compare is enabled
		if(this.hasProperty( 'SKIP_CMP' )){println '~~~~> Skipped XML check for similarity';return}
		println '------> Checking XML for similarity'
		saveToFileIfNotExist(SRC_TEST_RESOURCES + "$METHOD/$test"+".xml", actual)
		String expectedXml = new File(SRC_TEST_RESOURCES + "$METHOD/$test"+".xml").getText()
		DifferenceListener listener = new IgnoreNamedElementsDifferenceListener(TIME_TO_RUN_DOUBLE);
		Diff diff = new Diff(expectedXml, actual);
		diff.overrideDifferenceListener(listener);
		assert diff.similar()
		assert diff.identical()
	}

	private void saveToFileIfNotExist(String fileName, String response) {
		if(! new File(fileName).exists()){
			saveToFile(fileName, response)
			fail("Control File Created, re-run the test")
		}
	}

	private saveToFile(String fileName, String response) {
		def w = new File(fileName).newWriter()
		w<<response
		w.close()
	}
	
}
