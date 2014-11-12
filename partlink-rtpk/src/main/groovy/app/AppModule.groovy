package app;

import groovy.util.ConfigObject;
import groovyx.gpars.scheduler.ResizeablePool
import groovyx.gpars.group.DefaultPGroup
import net.sf.ehcache.config.CacheConfiguration
import net.sf.ehcache.management.Cache
import net.sf.ehcache.CacheManager
import net.sf.ehcache.Element
import service.mongo.MongoService
import service.web.UspsShippingLookupService;
import service.web.WebLookupService;
import service.web.parser.WebContentParser;

import com.google.gson.Gson
import com.google.inject.AbstractModule
import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.MongoClient
import org.apache.log4j.BasicConfigurator
import service.partlink.PartlinkSwtService


class AppModule extends AbstractModule {
	static final String WORKLOAD_CACHE = 'workload'	
	private static ConfigObject config;

	public AppModule(File cfg){
		config = new ConfigSlurper().parse(cfg.text).app
	}

	@Override
	protected void configure() {
		BasicConfigurator.configure();
		
		MongoClient client = new MongoClient(config.mongo.host as String, config.mongo.port)
		DB db = client.getDB(config.mongo.db)
		db.authenticate(config.mongo.user, config.mongo.pass as char[])
		bind(DB).toInstance(db)
		bind(MongoService).toInstance(new MongoService())

		bind(PartlinkSwtService).toInstance(new PartlinkSwtService(config.swt.sparklEndPoint))

		bind(WebLookupService).toInstance(new WebLookupService(config.web.uri, config.web.magicNbr))
		bind(WebContentParser).toInstance(new WebContentParser())

		bind(UspsShippingLookupService).toInstance(new UspsShippingLookupService(config.usps.user,config.usps.base, config.usps.xmlTemplate ))

		bind(DefaultPGroup).toInstance(new DefaultPGroup(new ResizeablePool(true)))

		CacheManager.getInstance().addCache(WORKLOAD_CACHE)
	}
}