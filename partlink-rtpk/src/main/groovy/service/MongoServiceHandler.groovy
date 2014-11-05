package service

import javax.inject.Inject

import org.json.JSONObject

import ratpack.handling.Context
import ratpack.handling.Handler
import service.mongo.MongoService

import com.mongodb.util.JSON
class MongoServiceHandler implements Handler{
	
	@Inject
	MongoService service

	@Override
	void handle(Context context) {
		def token = context.pathTokens.name
		service.collection = token
		def dbo = service.findOne()
		(!dbo)?context.clientError( 404):context.getResponse().contentType('application/json').send(produceResponce(dbo))
	}
	
	public String produceResponce(def dbo){
		String jsonStr = JSON.serialize(dbo)
		JSONObject json = new JSONObject(jsonStr)
		return json.get('items')
	}

}