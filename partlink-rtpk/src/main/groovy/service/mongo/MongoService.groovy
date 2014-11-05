package service.mongo
import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCursor
import com.mongodb.DBObject
import org.json.JSONObject
import org.json.XML
import ratpack.file.MimeTypes
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.http.Response
import com.mongodb.util.JSON
import groovy.xml.*
import ratpack.path.PathBinders;


import javax.inject.Inject
class MongoService {

	@Inject
	DB db
	private String collection
	//private final String COMPAIGN = 'assemblages'

//	//@Override
//	void handle(Context context) {
//		def token = context.pathTokens.name
//		this.collection = context.getRequest().path
//		def dbo = (token)?findOneByName(token):findOne()
//		(!dbo)?context.clientError( 404):context.getResponse().contentType('application/json').send(produceResponce(dbo))
//	}

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

}

