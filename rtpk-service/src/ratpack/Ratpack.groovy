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
import db.AppModule
import db.MongoServiceHandler

//TODO --switch to mongolab
//TODO --refactor to use mongoConnector
//TODO --insert,
//TODO --find compaign colections
//TODO --read json from mongo
//TODO --change json to xml

//TODO --fix handlers
//TODO --inject db
//TODO --add route to handler
//TODO scuffle for collection as model
//TODO cf, add mongo as service.

ratpack {
	
	bindings {
		try{
			add new AppModule(new File('./config/', 'Config.groovy'))
		}catch (Exception e){		
			add new AppModule(new File('./src/ratpack/config/', 'Config.groovy')) // local
		}

	}
		
	handlers { MongoServiceHandler mongoHandler ->
		get("/:name?"){ next()}
		
		get("echo/:name?") {
		  def name = pathTokens.name ?: "nothing"
		  response.send "Ratpack service is alive, back to you with: $name!"
		}

		prefix("compaign/:name?", mongoHandler)
		prefix("contacts/:name?", mongoHandler)
		
		assets "public"
		
	  }
	
	}