package service.partlink
import java.util.concurrent.Callable;

import service.web.UspsShippingLookupService
import service.web.WebLookupService;

import javax.inject.Inject;

class PartlinkCallableTask implements Callable<Map> {	
	protected String niin
	protected String zip
	protected boolean clientFeed

	//@Inject
	PartlinkSwtService service
	
	public PartlinkCallableTask(String niin, String zip, boolean clientFeed, PartlinkSwtService service) {
		this.niin = niin
		this.zip = zip
		this.clientFeed = clientFeed
		this.service = service
	}

	@Override
	//This method starts it's own execution process.
	//Performance improvement. The whole bunch is running as fast as the slowest job.
	public Map call() throws Exception{
		Map map =  null;
		try {
			map = service.lookupSupplierByNiin(this.niin, this.zip, this.clientFeed);
		} catch (Exception e) {
			e.printStackTrace();
			map = [error:"Service failed processing niin: "<< this.niin]
		}
		return map;
	}

}
