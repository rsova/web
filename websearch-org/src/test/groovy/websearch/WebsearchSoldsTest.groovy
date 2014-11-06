package websearch;

import static org.junit.Assert.*

import org.custommonkey.xmlunit.*
import org.junit.*

import websearch.rpc.helper.XmlRpcHelper
import websearch.xml.helper.IgnoreNamedElementsDifferenceListener

//WhatPropType			Array: Residential, Vacant, Land, Condominium, Commercial, Business, Time Interval, Multi-Dwelling Res	//If no value is passed, the default is all property types. If WhatCondo is passed, the default is Condominium. If WhatOhana is passed, the default is Residential.
//WhatWater	 		All  	Array: OceanFront,BeachFront, Across Street from Ocean
//WhatOhana	 		All  	Array: Single Family, Single Family w/Att Ohana, SF w/Det Ohana or Cottage
//WhatCondo	 		All  	Array: See Condo List
//WhatStartPrice	No		Lower Limit	Price (Integer)
//WhatEndPrice	 	No 		Upper Limit	Price (Integer)
//WhatView	 		All		Array: Ocean, Mountain, Mountain/Ocean, Golf Course, Garden View
//WhatDistrict	 	All 	Array: See District List
//WhatLandTenure    All		Array:Fee Simple, Leasehold, Leasehold-FA
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
//WhatDiv			Any?	Array:?
//WhatZone		 	Any?	Array: ?
//----WhatZoneAny in Short
//WhatSec	 		Any?	Array: ?
//WhatPlat	 		Any?	Array: ?
//WhatPar	 		Any?	Array: ?

class WebsearchSoldsTest extends XMLTestCase {
	final static def WS='http://websearch.ramidx.com/smartframe/ramxml.php'
	final static def METHOD ='solds'
	static final String SRC_TEST_RESOURCES = './src/test/resources/'

	static final String TIME_TO_RUN_DOUBLE = '/methodResponse[1]/params[1]/param[1]/value[1]/array[1]/data[1]/value[2]/double[1]/text()[1]'
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
		super.setUp();
	}
	
	//@After
	void tearDown() {
		curl = null;
		super.tearDown();
	}
	
	void test_type_water_startprice_endprice() {
		def test = getName().replaceFirst('test_','')
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID, 'WhatPropType':'Residential', 'WhatWater':['OceanFront'],'WhatStartPrice':'700000', 'WhatEndPrice':'10000000']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}

	void test_type_startprice_endprice() {
		def test = getName().replaceFirst('test_','')
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID, 'WhatPropType':'Residential', 'WhatStartPrice':'650000', 'WhatEndPrice':'750000']))
		def actual = curl.execute().text
		println actual
	}

	void test_type_district_startintarea_endintarea_startextarea_endextarea_number() {
		def test = getName().replaceFirst('test_','')
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':'Residential', 'WhatDistrict':['Kahului'],'WhatStartIntArea':'1200','WhatEndIntArea':'1800','WhatStartExtArea':'1000','WhatEndExtArea':'7000']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}
	
	void test_condo_startbed_endbed_startbath_endbath_number() {
		def test = getName().replaceFirst('test_','')
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatCondo':['Pacific Shores'],'WhatStartBed':'1','WhatEndBed':'2', 'WhatStartBath':'1','WhatEndBath':'2']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}

	void test_view_ohana_endprice_number() {
		def test = getName().replaceFirst('test_','')
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatView':['Mountain/Ocean'],'WhatOhana':['Single Family w/Att Ohana'], 'WhatEndPrice':'1700000']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}
	
	void test_type_landtenure() {
		def test = getName().replaceFirst('test_','')
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':'Residential','WhatLandTenure':['Leasehold']]))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}
	
	void test_type_district_startprice_water() {
		def test = getName().replaceFirst('test_','')
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':'Condominium', 'WhatDistrict':['Kihei'],'WhatStartPrice':'500000','WhatWater':['OceanFront','BeachFront']]))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}
	
	void test_type_agent() {
		def test = getName().replaceFirst('test_','')
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':'Condominium','WhatAgent':['10202','10475']]))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}
	
	void test_type_agent_startbed() {
		def test = getName().replaceFirst('test_','')
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':'Condominium','WhatAgent':['10202','10475'],'WhatStartBed':'2']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}
	
	void test_type_office() {
		def test = getName().replaceFirst('test_','')
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':'Commercial','WhatOffice':['2987','8956','8700']]))
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
