package websearch;

import org.custommonkey.xmlunit.*
import org.junit.*

import websearch.rpc.helper.XmlRpcHelper
import websearch.xml.helper.IgnoreNamedElementsDifferenceListener

//	WhatOffice	 Array Office ID Numbers
//	WhatAgent	 Array Agent ID Numbers
//	WhatType	 Array Residential Vacant Land Condominium
//	WhatDistrict Array Use district

class WebsearchShortTest extends XMLTestCase {
	final static def WS='http://websearch.ramidx.com/smartframe/ramxml.php'
    //final static def METHOD ='<methodCall><methodName>short</methodName><params><param><value><struct><member><name>dbid</name><value><string>dbid1139259934</string></value></member><member><name>WhatPropType</name><value><array><data><value><string>Residential</string></value></data></array></value></member></struct></value></param></params></methodCall>';
	final static def METHOD ='short'
	static final String SRC_TEST_RESOURCES = 'src/test/resources/'
	def curl = []
		
//	@Before
	void setUp(){
		curl = ['curl',
			'-H', 'Cookie: WebSearchID=r2so30lip7g5rp8ibonnej9ha2, D_SID=74.123.217.146:3rK08PPRKVcCOWmibSyxVs9T5+e0ovpplUhtdFEsY+4; D_PID=3119DF0B-3C06-308A-88B4-6118E4B86D16; D_IID=0B90D182-B361-3247-BBB8-F4E25374A8DD; D_UID=2DF8F0F0-54E8-3AC8-9B9F-566FA9407FDA; D_HID=D/r38/ISyXBwlPemp21MktzpUBkVEfdJwsLQeFu/JaM',
			'-H','Content-Type:text/xml',
			'-H', 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0',
			//'data',
		   WS]
	}
	
	//@After
	void tearDown() {
		curl = null;
	}
	
	void testAllCondominium() {
		def test = getName()
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':'dbid1139259934','WhatPropType':['Condominimum']]))
		def actual = curl.execute().text
		
		println actual
		String expectedXml = getClass().getClassLoader().getResourceAsStream("$METHOD/$test.xml").getText()
		
		DifferenceListener listener = new IgnoreNamedElementsDifferenceListener('/methodResponse[1]/params[1]/param[1]/value[1]/array[1]/data[1]/value[4]/double[1]/text()[1]');
		Diff diff = new Diff(expectedXml, actual);
		diff.overrideDifferenceListener(listener);
		assert diff.similar()
		assert diff.identical()

		saveToFile(SRC_TEST_RESOURCES + "$METHOD/$test.xml", actual)

	}
//	void testAllLand() {
//		
//	}
	
	public void testAllVacant() throws Exception{
		def test = getName()
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':'dbid1139259934','WhatPropType':['Vacant']]))
		def actual = curl.execute().text
		
		println actual
		String expectedXml = getClass().getClassLoader().getResourceAsStream("$METHOD/$test.xml").getText()
		
		DifferenceListener listener = new IgnoreNamedElementsDifferenceListener('/methodResponse[1]/params[1]/param[1]/value[1]/array[1]/data[1]/value[4]/double[1]/text()[1]');
		Diff diff = new Diff(expectedXml, actual);
		diff.overrideDifferenceListener(listener);
		assert diff.similar()
		assert diff.identical()

		saveToFile(SRC_TEST_RESOURCES + "$METHOD/$test.xml", actual)
	}
	
	public void testAllResidential() throws Exception{
		def test = getName()
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':'dbid1139259934','WhatPropType':['Residential']]))
		def actual = curl.execute().text
		
		println actual
		String expectedXml = getClass().getClassLoader().getResourceAsStream("$METHOD/$test.xml").getText()
		
		DifferenceListener listener = new IgnoreNamedElementsDifferenceListener('/methodResponse[1]/params[1]/param[1]/value[1]/array[1]/data[1]/value[4]/double[1]/text()[1]');
		Diff diff = new Diff(expectedXml, actual);
		diff.overrideDifferenceListener(listener);
		assert diff.similar()
		assert diff.identical()

		saveToFile(SRC_TEST_RESOURCES + "$METHOD/$test.xml", actual)
	}


	

	private saveToFile(String fileName, String response) {
		def w = new File(fileName).newWriter()
		w<<response
		w.close()
	}
	
}
