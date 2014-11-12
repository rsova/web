package db
import groovy.xml.*

import javax.inject.Inject

import org.json.JSONObject

import ratpack.handling.Context
import ratpack.handling.Handler

import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCursor
import com.mongodb.DBObject
import com.mongodb.WriteResult
import com.mongodb.util.JSON
class MongoServiceHandler implements Handler {

	@Inject
	DB db
	private String collection
	private final String COMPAIGN = 'compaign'

	@Override
	void handle(Context context) {
		def token = context.pathTokens.name
		this.collection = context.getRequest().path
		def dbo = (token)?findOneByName(token):findOne()
		(!dbo)?context.clientError( 404):context.getResponse().contentType('application/json').send(produceResponce(dbo))
	}

	public DBObject findOne(){
		return db.getCollection(this.collection).findOne()
	}
	
	def findOneByName(String token){
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put('name', token);
		DBCursor cursor = db.getCollection(this.collection).find(whereQuery)
		DBObject dbo = (cursor.hasNext())?cursor.next():null // get one
		return dbo
	}

	public String produceResponce(def dbo){
		String jsonStr = JSON.serialize(dbo)
		JSONObject json = new JSONObject(jsonStr)
		return json.get('data')
	}
}

