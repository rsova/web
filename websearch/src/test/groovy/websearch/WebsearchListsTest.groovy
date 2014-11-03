package websearch;

import static org.junit.Assert.*

import org.custommonkey.xmlunit.*
import org.junit.*

import websearch.rpc.helper.XmlRpcHelper
import websearch.xml.helper.IgnoreNamedElementsDifferenceListener

class WebsearchListsTest extends XMLTestCase {
	final static def WS='http://websearch.ramidx.com/smartframe/ramxml.php'
    //final static def METHOD ='<methodCall><methodName>lists</methodName><params><param><value><struct><member><name>dbid</name><value><string>dbid1139259934</string></value></member><member><name>getlist</name><value><string>$TOKEN</string></value></member></struct></value></param></params></methodCall>';

	static final String SRC_TEST_RESOURCES_LISTS = './src/test/resources/lists/'
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

	public void testCondo() throws Exception{
		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest('lists',['dbid':'dbid1139259934','getlist':'condo']))
		def actual = curl.execute().text
		
		println actual
		String expectedXml = getClass().getClassLoader().getResourceAsStream('lists/condo.xml').getText()
		
		DifferenceListener listener = new IgnoreNamedElementsDifferenceListener('/methodResponse[1]/params[1]/param[1]/value[1]/array[1]/data[1]/value[2]/double[1]/text()[1]');
		Diff diff = new Diff(expectedXml, actual);
		diff.overrideDifferenceListener(listener);
		assert diff.similar()
		assert diff.identical()

		//saveToFile(SRC_TEST_RESOURCES_LISTS+'condo.xml', actual)
		
	}
	
	public void testDistrict() throws Exception{
		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest('lists',['dbid':'dbid1139259934','getlist':'district']))		
		def actual = curl.execute().text		
		println actual
		String expectedXml = getClass().getClassLoader().getResourceAsStream('lists/district.xml').getText()
		
		DifferenceListener listener = new IgnoreNamedElementsDifferenceListener('/methodResponse[1]/params[1]/param[1]/value[1]/array[1]/data[1]/value[2]/double[1]/text()[1]');
		Diff diff = new Diff(expectedXml, actual);
		diff.overrideDifferenceListener(listener);
		assert diff.similar()
		assert diff.identical()

		
	//	saveToFile(SRC_TEST_RESOURCES_LISTS+'district.xml', actual)		
	}
	
	public void testAgent() throws Exception{
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest('lists',['dbid':'dbid1139259934','getlist':'agent']))		
		def actual = curl.execute().text		
		println actual
		String expectedXml = getClass().getClassLoader().getResourceAsStream('lists/agent.xml').getText()
		
		DifferenceListener listener = new IgnoreNamedElementsDifferenceListener('/methodResponse[1]/params[1]/param[1]/value[1]/array[1]/data[1]/value[2]/double[1]/text()[1]');
		Diff diff = new Diff(expectedXml, actual);
		diff.overrideDifferenceListener(listener);
		assert diff.similar()
		assert diff.identical()
		
		//saveToFile(SRC_TEST_RESOURCES_LISTS+'agent.xml', actual)		
	}
	
	public void testOffice() throws Exception{
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest('lists',['dbid':'dbid1139259934','getlist':'office']))		
		def actual = curl.execute().text		
		println actual
		String expectedXml = getClass().getClassLoader().getResourceAsStream('lists/office.xml').getText()
		
		DifferenceListener listener = new IgnoreNamedElementsDifferenceListener('/methodResponse[1]/params[1]/param[1]/value[1]/array[1]/data[1]/value[2]/double[1]/text()[1]');
		Diff diff = new Diff(expectedXml, actual);
		diff.overrideDifferenceListener(listener);
		assert diff.similar()
		assert diff.identical()
		
		//saveToFile(SRC_TEST_RESOURCES_LISTS+'office.xml', actual)
		
	}
	
//		public void testCondo1() {
//			String myControlXML = "<location><street-address>22 any street</street-address><postcode>XY00 99Z</postcode></location>";
//			String myTestXML = "<location><street-address>20 east cheap</street-address><postcode>EC3M 1EB</postcode></location>";
//			DifferenceListener myDifferenceListener = new IgnoreTextAndAttributeValuesDifferenceListener();
//			Diff myDiff = new Diff(myControlXML, myTestXML);
//			myDiff.overrideDifferenceListener(myDifferenceListener);
//			assertTrue("test XML matches control skeleton XML " + myDiff, myDiff.similar());
//		}
	
	

	private saveToFile(String fileName, String response) {
		def w = new File(fileName).newWriter()
		w<<response
		w.close()
	}
	
}
