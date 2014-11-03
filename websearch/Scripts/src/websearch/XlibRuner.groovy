package websearch
import groovy.net.xmlrpc.*

    username = 'ramaui' //the user ramaui:demo1
    pwd = 'demo1'      //the password of the user
    dbname = 'dbid1113086003'    //the database

    // Get the uid
    XMLRPCServerProxy serverProxy = new XMLRPCServerProxy('http://websearch.ramidx.com/ramxml.php')
   // uid = serverProxy.setBasicAuth(username, pwd)
	def xml = '<methodCall><methodName>lists</methodName><params><param><value><struct><member><name>dbid</name><value><string>dbid1139259934</string></value></member><member><name>getlist</name><value><string>district</string></value></member></struct></value></param></params></methodCall>';
	def data = serverProxy.doRpcCall(xml.bytes)
	println data
    //replace localhost with the address of the server
    //sock = xmlrpclib.ServerProxy('http://localhost:8069/xmlrpc/object')

//    partner = {
//       'name': 'Fabien Pinckaers',
//       'lang': 'fr_FR',
//    }
//
//    partner_id = sock.execute('res.partner', 'create', partner)
//	def paramfields = [
//		// Stuff the programmer can define:
//		//'method':'lists',
//		'dbid' : 'dbid1113086003',
//		'getlist': 'condo'];
//	def resp = serverProxy.execute('/ramxml.php','lists', paramfields)
//	println resp
	
//
//    address = {
//       'partner_id': partner_id,
//       'type' : 'default',
//       'street': 'Chaussée de Namur 40',
//       'zip': '1367',
//       'city': 'Grand-Rosière',
//       'phone': '+3281813700',
//       'fax': '+3281733501',
//    }
//
//    address_id = sock.execute(dbname, uid, pwd, 'res.partner.address', 'create', address)

