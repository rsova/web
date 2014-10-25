package service.partlink
import java.util.concurrent.Callable;
import service.web.UspsShippingLookupService

class PartlinkCallableTask implements Callable<Map> {
	protected PartlinkSwtService service
	protected UspsShippingLookupService uspsService
	protected String niin
	
	public PartlinkCallableTask(PartlinkSwtService service, String niin, UspsShippingLookupService uspsService = null) {
		this.service = service
		this.niin = niin
		this.uspsService = uspsService
	}

	@Override
	public Map call() throws Exception{
		Map map =  null;
		try {
			map = service.lookupSupplierByNiin(niin);
			//map.
			//println uspsService.getTimePackageServiceStandard('97653','55344')
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Service failed processing this niin: " + niin);
		}
		return map;
	}

}
