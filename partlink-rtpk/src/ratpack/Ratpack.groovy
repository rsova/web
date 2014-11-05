import static ratpack.groovy.Groovy.groovyTemplate
import static ratpack.groovy.Groovy.ratpack
import com.mongodb.BasicDBObject
import com.mongodb.DBCollection
import com.mongodb.DBCursor
import com.mongodb.DBObject
import com.mongodb.Mongo
import groovy.io.FileType
import org.json.JSONObject
import com.mongodb.util.JSON
import groovy.xml.*
import org.json.XML
import service.AppServiceHandler
import service.MongoServiceHandler
import app.AppModule
//import service.mongo.MongoService

ratpack {
	
	bindings {
		add new AppModule(launchConfig.baseDir.file("config/Config.groovy").toFile())
	}

  handlers {AppServiceHandler appHandler, MongoServiceHandler mongoHandler ->
	get("/:name?"){ next()}
	
	get("base/") {
		response.send "Running at :" + launchConfig.baseDir
	  }
	get("gold"){
		def data ='''{
          "items": [
            {
              "text": "<i>Assemblage #1  Lot TCDF-12034E</i>",
			        "url": "/lookup/client/016033650,015127231,010077987,015303893,015208009,001186249,010127380,010767912,015118286,012746352",
              "leaf": "true"
            },
            {
              "text": "<i>Assemblage #2  Lot TCDF-243034E</i>",
			        "url": "/lookup/client/013754314,003820220,001103420,000097159,002558080,000327480,014802308,014602274",
              "leaf": "true"
            },
            {
              "text": "<i>Assemblage #3  Lot TCDF-17757D</i>",
			         "url": "http://localhost:5050/lookup/client/012123138,013767247,013487680,014962000",
              "leaf": "true"
            },
            {
              "text": "<i>Assemblage #4  Lot TCDF-12257-1D</i>",
			        "url": "http://localhost:5050/lookup/client/012123138,013767247,013487680,014962000,015427723,015427723,015856689,004320110,000166421,000530945,010576354,015748101",
              "leaf": "true"
            }
 ]}'''		
		response.send data
		
	}
	prefix("work/:name?", mongoHandler)	
	prefix("lookup/client/:zip?/:niins?", appHandler)
	prefix("lookup/:zip?/:niins?", appHandler)
	//prefix("lookup/:niins?", appHandler)
	
    assets "public", "index.html"
    //assets "public"
  }
}
