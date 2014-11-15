package service

import groovyx.gpars.*
import groovyx.gpars.group.*

import java.util.concurrent.Future

import javax.inject.Inject

import org.apache.http.entity.ContentType
import org.json.JSONObject
import org.json.XML

import ratpack.handling.Context
import ratpack.handling.Handler
import service.partlink.PartlinkCallableTask
import service.partlink.PartlinkSwtService
import utils.MimeTypeUtil

import com.google.gson.GsonBuilder
//import groovyx.net.http.*


// November 17 - December 3	App Web presentations to Judges
// December 8	Challenge winners announced
class AppServiceHandler implements Handler {

	@Inject
	DefaultPGroup pooledParallelGroup
	
	@Inject
	PartlinkSwtService service

	@Override
	void handle(Context context) {
		def params = context.pathTokens.niins
		def zip = context.pathTokens.zip
		boolean clientFeed = context.request.path.startsWith("lookup/client")
		def niins = (params?.contains(','))?params.split(",") as List:[params]
		def results = []
		List<Future> handles = []

		//Start concurrent processing
		for (niin in niins) {
			def task = pooledParallelGroup.task (new PartlinkCallableTask(niin, zip, clientFeed,service))
			handles.add(task)
		}

		//Collect results
		for (handle in handles) {
			results.add(handle.get())
		}
				
		//Send Json back as default
		String contextType = ContentType.APPLICATION_JSON
		String rspns = toJson(['items':results])
		 
		//Send client xml if requested
		if(isResponseAsXml(context)){
			contextType = ContentType.APPLICATION_XML			
			rspns = toXml(rspns)
		}
		
		context.getResponse().contentType(contextType).send(rspns)
		
	}

	// Convert Map into Json 
	protected String toJson(Map results){
		return new GsonBuilder().setPrettyPrinting().create().toJson(results)
	}
	
	// Checks if client wants xml back
	protected boolean isResponseAsXml(Context context) {
		return context.request?.headers?.getAll('Content-Type')?.any(){String type-> type.contains('xml')}
	}

	// Convert response to xml
	protected String toXml(String rspns) {
		return XML.toString(new JSONObject(rspns))
	}

//	protected produceResponce(Collection col){
//		new GsonBuilder().setPrettyPrinting().create().toJson('data':col)
//	}
}
