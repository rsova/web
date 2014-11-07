package service

import javax.inject.Inject

import net.sf.ehcache.CacheManager
import net.sf.ehcache.Element

import org.json.JSONObject

import ratpack.handling.Context
import ratpack.handling.Handler
import service.mongo.MongoService
import app.AppModule

import com.mongodb.DBObject
import com.mongodb.util.JSON
class MongoServiceHandler implements Handler{

	static final String WORKLOAD_UID = 'partlink'
	
	@Inject
	MongoService service

	@Override
	void handle(Context context) {
		def token = context.pathTokens.name
		def dbo= CacheManager.getInstance()?.getCache(AppModule.WORKLOAD_CACHE).get(WORKLOAD_UID)?.getObjectValue()
		if(!dbo){
			service.collection = token
			dbo = service.findOne()
			def elm = new Element(WORKLOAD_UID, ((DBObject) dbo))
			elm.timeToLive = 60*60*24 //24 hours
			CacheManager.getInstance()?.getCache(AppModule.WORKLOAD_CACHE).put(elm)		
		}
				
		(!dbo)?context.clientError( 404):context.getResponse().contentType('application/json').send(produceResponce(dbo))
	}
	
	public String produceResponce(def dbo){
		String jsonStr = JSON.serialize(dbo)
		JSONObject json = new JSONObject(jsonStr)
		return json.get('items')
	}

}