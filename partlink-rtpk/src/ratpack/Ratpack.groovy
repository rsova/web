import static ratpack.groovy.Groovy.groovyTemplate
import static ratpack.groovy.Groovy.ratpack
import groovy.xml.*

import service.AppServiceHandler
import service.MongoServiceHandler
import app.AppModule

ratpack {

	// Use configuration file on application start up.
	bindings {
		add new AppModule(launchConfig.baseDir.file("config/Config.groovy").toFile())
	}
	
	// Here the incoming requests are routed to the appropriate handlers
	handlers {AppServiceHandler appHandler, MongoServiceHandler mongoHandler ->
		get("/:name?"){ next()}
		get("base/") {response.send "Running at :" + launchConfig.baseDir}
		prefix("work/:name?", mongoHandler)
		prefix("lookup/client/:zip?/:niins?", appHandler)
		prefix("lookup/:zip?/:niins?", appHandler)

		// Default mapping
		assets "public", "index.html"
	}
}
