package service

import com.google.gson.GsonBuilder

import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import javax.inject.Inject;

import ratpack.handling.Context
import ratpack.handling.Handler
import service.partlink.PartlinkCallableTask
import service.partlink.PartlinkSwtService
import groovyx.gpars.*
import groovyx.gpars.group.*
import groovyx.gpars.scheduler.ResizeablePool
import service.web.UspsShippingLookupService
import service.web.WebLookupService

// November 17 - December 3	App Web presentations to Judges
// December 8	Challenge winners announced

class AppServiceHandler implements Handler {

	@Inject
	DefaultPGroup pooledParallelGroup
	
//	@Inject
//	WebLookupService webLookupService
	@Inject
	PartlinkSwtService service
//	@Inject
//	UspsShippingLookupService uspsService

	@Override
	void handle(Context context) {
		def params = context.pathTokens.niins
		boolean clientFeed = context.request.path.startsWith("lookup/client")
		def niins = (params?.contains(','))?params.split(",") as List:[params]
		def results = []
		List<Future> handles = []
		
		//Start concurrent processing
		for (niin in niins) {
			def task = pooledParallelGroup.task (new PartlinkCallableTask(niin,clientFeed,service))
			handles.add(task)
		}

		//Collect results
		for (handle in handles) {
			results.add(handle.get())
		}
		
		(!results)?context.clientError( 404):context.getResponse().contentType('application/json').send(toJson(['items':results]))
	}

	String toJson(Map results){
		return new GsonBuilder().setPrettyPrinting().create().toJson(results)
	}
	
	//@Override
	void handleOld(Context context) {
		def params = context.pathTokens.niins
		def niins = (params?.contains(','))?params.split(",") as List:[params]
		def result = null
		if(niins){
			def matches = resolveNiinToCageCodes(niins)
			//def codes = matches*.'CAGE CD'
			def codes = matches
			result = resolveCageCodeDetails(codes);
		}
		(!result)?context.clientError( 404):context.getResponse().contentType('application/json').send(result)

		//this.collection = context.getRequest().path

		// 1) niin service - look in mongo/webflis
		// 2) partlink service - get rdf with cage details
		// 3) build response json

		//		def dbo = (token)?findOneByName(token):findOne()
		//		(!dbo)?context.clientError( 404):context.getResponse().contentType('application/json').send(produceResponce(dbo))
	}

	protected Collection resolveNiinToCageCodes(Collection niins){
		Collection partToCageMap = webLookupService.lookup(niins)
		return partToCageMap
	}

	protected Collection resolveCageCodeDetails(List codes){
		return codes
	}

	protected produceResponce(Collection col){
		new GsonBuilder().setPrettyPrinting().create().toJson('data':col)
	}
}
