package service.web

class UspsShippingLookupService {
	//private String user
	private String base
	private String xmlTemplate


	public UspsShippingLookupService(String user, String base, String xmlTemplate) {
		this.base = base;
		this.xmlTemplate = xmlTemplate.replace('{0}', user);
	}
	
	public String getTimePackageServiceStandard(String zipFrom, String zipTo){
		def params = [API:'StandardB', XML:java.net.URLEncoder.encode(xmlTemplate.replace('{1}', zipFrom).replace('{2}', zipTo))]
		def xml = new URL(base + params.collect{ k,v -> "$k=$v" }.join('&')).text
		return xml
		
	}
}
