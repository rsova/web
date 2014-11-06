package db;

import groovy.util.ConfigObject;

import com.google.inject.AbstractModule
import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.MongoClient

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
    bind(MongoServiceHandler).toInstance(new MongoServiceHandler())

  }
}