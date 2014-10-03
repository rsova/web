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

ratpack {
	bindings {
		add new AppModule(launchConfig.baseDir.file("config/Config.groovy").toFile())
	}

  handlers {MongoServiceHandler mongoHandler ->
	get("/:name?"){ next()}
	get("base/") {
		response.send "Running at :" + launchConfig.baseDir
	  }
  
	get("echo/:name?") {
	  response.send "Ratpack service is alive, back to you with: " + (pathTokens.name?:"My own hello")
	}
	
	prefix("contacts/:name?", mongoHandler)
	
    assets "public", "index.html"
    //assets "public"
  }
}
