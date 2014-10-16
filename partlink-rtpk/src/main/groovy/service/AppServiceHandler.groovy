package service

import com.google.gson.GsonBuilder

import javax.inject.Inject;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import service.partlink.PartlinkSwtService;
import service.web.WebLookupService;
// November 17 - December 3	App Web presentations to Judges
// December 8	Challenge winners announced

//TODO: add mongo save and lookup
//TODO: flash out sparql endpoint to work with cage codes
//TODO: add suppliers information to json 

class AppServiceHandler implements Handler {

	@Inject
	WebLookupService webLookupService	
	@Inject
	PartlinkSwtService swtService 
	
	@Override
	void handle(Context context) {
		def params = context.pathTokens.niins
		def niins = (params?.contains(','))?params.split(",") as List:[params]
		def results = null
		if(niins){
			results=swtService.lookupSupplierByNiin(niins.get(0))
		}
		(!results)?context.clientError( 404):context.getResponse().contentType('application/json').send(results)
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
