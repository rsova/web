package websearch
//@Grapes(
//	@Grab(group='org.apache.xmlrpc', module='xmlrpc-client', version='3.1.3')
//)
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.parser.XmlRpcRequestParser
import org.apache.xmlrpc.parser.XmlRpcResponseParser;


println 'hello'
// Location of the RAM service...
//$site        = 'websearch.ramidx.com';        // The url of the XML server
//$location    = '/ramxml.php';    // The script to talk to
//$method        = 'lists';                    // The remote method to invoke
////$params = '<methodCall><methodName>lists</methodName><params><param><value><struct><member><name>dbid</name><value><string>dbid1139259934</string></value></member><member><name>getlist</name><value><string>district</string></value></member></struct></value></param></params></methodCall>';
//// We will encode all of our search params into one associative array:
//$List = 'agent';
//$paramfields = array(
//// Stuff the programmer can define:
//'dbid'        =>    'dbid1113086003',
//'getlist'     =>    $List);

XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
//config.setServerURL(new URL("http://127.0.0.1:8080/xmlrpc"));
config.setServerURL(new URL("http://websearch.ramidx.com/smartform/ramxml.php"));
config.setUserAgent("User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")
//config.setBasicUserName('ramaui')
//config.setBasicPassword('demo')
XmlRpcClient client = new XmlRpcClient();
client.setConfig(config);
//def xml = '<methodCall><methodName>lists</methodName><params><param><value><struct><member><name>dbid</name><value><string>dbid1139259934</string></value></member><member><name>getlist</name><value><string>district</string></value></member></struct></value></param></params></methodCall>';
//("lists",{ "dbid" =>"dbid1113086003", "getlist"=> "district"})
//Object[] params = new Object[]{new Integer(33), new Integer(9)};
def result = client.execute('lists', ['dbid':'dbid1113086003', 'getlist':'district']);
XmlRpcRequest req = new XmlRpcRequestParser()
result = client.execute()
println result



//import groovy.net.xmlrpc.*
//http://websearch.ramidx.com/ramxml.php/?list=condo&dbid=dbid1139259934
//def c = new XMLRPCServerProxy("http://docs.codehaus.org/rpc/xmlrpc")
//def token = c.confluence1.login("your_username","your_password")
//// print all the code snippets from the Groovy Home page
//def page = c.confluence1.getPage(token, "Groovy", "Home")
//def incode = false
//def separator = '////////////////////////////////////'
//page.content.split('\n').each{
//	if (it =~ /\{code\}/) {
//		incode = !incode
//		if (incode) println separator
//		return
//	}
//	if (incode) print it
//}
//println separator

//import java.net.ServerSocket

//def server = new XMLRPCServer()
//$params = '<methodCall><methodName>lists</methodName><params><param><value><struct><member><name>dbid</name><value><string>dbid1139259934</string></value></member><member><name>getlist</name><value><string>district</string></value></member></struct></value></param></params></methodCall>';
