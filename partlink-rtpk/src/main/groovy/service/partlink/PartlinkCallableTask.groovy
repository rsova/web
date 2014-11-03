package service.partlink
import java.util.concurrent.Callable;

import service.web.UspsShippingLookupService
import service.web.WebLookupService;

import javax.inject.Inject;

class PartlinkCallableTask implements Callable<Map> {
	//@Inject
	PartlinkSwtService service
	
//	protected PartlinkSwtService service
//	protected UspsShippingLookupService uspsService
	protected String niin
	protected boolean clientFeed
	
	public PartlinkCallableTask(String niin, boolean clientFeed, PartlinkSwtService service) {
		this.niin = niin
		this.clientFeed = clientFeed
		this.service = service
	}

	@Override
	public Map call() throws Exception{
		Map map =  null;
		try {
			map = service.lookupSupplierByNiin(this.niin,this.clientFeed);
			//map = service.generateShippingAdvice(map)
		} catch (Exception e) {
			e.printStackTrace();
			//throw new Exception("Service failed processing this niin: " + niin);
			map = [error:"Service failed processing niin: "<< this.niin]
		}
		return map;
	}

}
