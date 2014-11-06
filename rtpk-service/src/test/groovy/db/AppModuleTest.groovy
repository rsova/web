package db;

import static org.junit.Assert.*

import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCursor
import com.mongodb.MongoClient
import com.mongodb.util.JSON

import org.json.JSONObject
import org.json.XML
import org.junit.Test
import groovy.xml.*

class AppModuleTest {

	
	@Test
	public void test() {
	ConfigObject config = new ConfigSlurper().parse(new File('./src/ratpack/config/', 'Config.groovy').text).app
    MongoClient client = new MongoClient(config.mongo.host as String, config.mongo.port)
    DB db = client.getDB(config.mongo.db)
    db.authenticate(config.mongo.user, config.mongo.pass as char[])
	
	def dbo = db.getCollection("compaign").findOne()
		def jsonStr = JSON.serialize(dbo)
		println  jsonStr
		JSONObject json = new JSONObject(jsonStr)
		String xml = XML.toString(json.get("teams"))
		println XmlUtil.serialize(xml.replace("array", "teams"))
		
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("name", 'mock');
  
		DBCursor cursor = db.getCollection("compaign").find(whereQuery)
		dbo = cursor.next() // get one
		jsonStr = JSON.serialize(dbo)
		println  jsonStr
		json = new JSONObject(jsonStr)
		xml = XML.toString(json.get("teams"))
		println XmlUtil.serialize(xml.replace("array", "teams"))

	}

}
