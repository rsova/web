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
import app.AppModule
//import service.mongo.MongoService

ratpack {
	
	bindings {
		add new AppModule(launchConfig.baseDir.file("config/Config.groovy").toFile())
	}

  handlers {AppServiceHandler appHandler ->
	get("/:name?"){ next()}
	
	get("base/") {
		response.send "Running at :" + launchConfig.baseDir
	  }
  	
	prefix("lookup/:niins?", appHandler)
	//prefix("partlink/:niins?", appHandler)
	
    assets "public", "index.html"
    //assets "public"
  }
}
