package app;

import groovy.util.ConfigObject;
import service.mongo.MongoService
import service.web.WebLookupService;
import service.web.parser.WebContentParser;

import com.google.inject.AbstractModule
import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.MongoClient
import service.partlink.PartlinkSwtService

class AppModule extends AbstractModule {
  private static ConfigObject config;

  public AppModule(File cfg){	
	  config = new ConfigSlurper().parse(cfg.text).app
  }
  
  @Override
  protected void configure() {
	  
    MongoClient client = new MongoClient(config.mongo.host as String, config.mongo.port)
    DB db = client.getDB(config.mongo.db)
    db.authenticate(config.mongo.user, config.mongo.pass as char[])

    bind(DB).toInstance(db)
    bind(MongoService).toInstance(new MongoService())
  
	bind(PartlinkSwtService).toInstance(new PartlinkSwtService(config.swt.sparklEndPoint))
	
	bind(WebContentParser).toInstance(new WebContentParser())
	bind(WebLookupService).toInstance(new WebLookupService(config.web.uri, config.web.magicNbr))
  }
}