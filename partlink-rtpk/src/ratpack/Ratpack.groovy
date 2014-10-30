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
import service.AppServiceHandler
import app.AppModule
//import service.mongo.MongoService

ratpack {
	
	bindings {
		add new AppModule(launchConfig.baseDir.file("config/Config.groovy").toFile())
	}

  handlers {AppServiceHandler appHandler ->
	get("/:name?"){ next()}
	
	get("base/") {
		response.send "Running at :" + launchConfig.baseDir
	  }
	get("mock1"){
	  def data = '''{ 'text': 'Groceries',
			'items': [{
				'text': 'Drinks',
				'items': [{
					'text': 'Water',
					'items': [{
						'text': 'Sparkling',
						leaf: true
					}, {
						'text': 'Still',
						leaf: true
					}]
				}, {
					'text': 'Coffee',
					leaf: true
				}, {
					'text': 'Espresso',
					leaf: true
				}, {
					'text': 'Redbull',
					leaf: true
				}, {
					'text': 'Coke',
					leaf: true
				}, {
					'text': 'Diet Coke',
					leaf: true
				}]
			}, {
				'text': 'Fruit',
				'items': [{
					'text': 'Bananas',
					leaf: true
				}, {
					'text': 'Lemon',
					leaf: true
				}]
			}, {
				'text': 'Snacks',
				'items': [{
					'text': 'Nuts',
					leaf: true
				}, {
					'text': 'Pretzels',
					leaf: true
				}, {
					'text': 'Wasabi Peas',
					leaf: true
				}]
			}]
		}'''
	  response.send data
	  
	}
 	get("mock") {
		def mock = 		'''
{
  "product": {
    "niin": "016033650",
    "price": "154.26",
    "assignmentDate": "2012-03-01"
  },
  "suppliers": [
    {
      "name": "DEFENSE MEDICAL STANDARDIZATION",
      "cageCode": "64616",
      "address": "1423 SULTON DR, FREDERICK, MD, UNITED STATES, 21702-5013",
      "phone": "301-619-2001",
      "cao": "S2101A",
      "adp": "HQ0338",
      "isWomanOwned": "N"
    },
    {
      "name": "KEY SURGICAL, INC.",
      "cageCode": "0SRE6",
      "address": "8101 WALLACE RD, EDEN PRAIRIE, MN, UNITED STATES, 55344-2114",
      "phone": "9529149789 9529149866",
      "cao": "S2401A",
      "adp": "HQ0339",
      "isWomanOwned": "N"
    }
  ]
}
'''
		
		response.send mock
	}
  	
	prefix("lookup/client/:niins?", appHandler)
	prefix("lookup/:niins?", appHandler)
	//prefix("lookup/:niins?", appHandler)
	
    assets "public", "index.html"
    //assets "public"
  }
}
