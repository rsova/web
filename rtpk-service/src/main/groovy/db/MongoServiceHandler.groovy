package db
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
	  if(!dbo){
		  context.clientError( 404)
	  }else{
	  	Response r = context.getResponse()
		r.contentType('application/json')  
		r.send(produceResponce(dbo))
	  }
	 // (!dbo)?context.clientError( 404):context.getResponse().send(produceResponce(dbo))
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
  
  public String dboToXml(String json, String root){
	  String xml = XML.toString(json)
	  return XmlUtil.serialize(xml.replace("array", root))
  }
   
  public String produceResponce(def dbo){
	  String jsonStr = JSON.serialize(dbo)
	  JSONObject json = new JSONObject(jsonStr)
	  def root = (this.collection == COMPAIGN)?"teams":"data"
	  String resp = json.get(root)
	  if(this.collection == COMPAIGN){
	   resp =  dboToXml(resp, root)
	  }
	  //return resp
	  return resp
    
  }
}

