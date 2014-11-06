package db;

import static org.junit.Assert.*;

import com.mongodb.DB
import com.mongodb.MongoClient
import org.junit.*;

class MongoServiceHandlerTest {
	MongoServiceHandler handler = null
	
	@Before
	void setup(){
		handler = new MongoServiceHandler()
		ConfigObject config = new ConfigSlurper().parse(new File('./src/ratpack/config/', 'Config.groovy').text).app
	    MongoClient client = new MongoClient(config.mongo.host as String, config.mongo.port)
	    handler.db = client.getDB(config.mongo.db)
	    handler.db.authenticate(config.mongo.user, config.mongo.pass as char[])
	}
	
	@Test
	public void testFindOne() {
		def dbo = handler.findOne()
		assertEquals('mock', dbo.name)
	}
	
	@Test
	public void testFindOneByName() {
		def dbo = handler.findOneByName('mock')
		assertEquals('mock', dbo.name)
	}

}
