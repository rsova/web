package service.web

import java.util.regex.Matcher

class UspsShippingLookupService {
	private String base
	private String xmlTemplate

	public UspsShippingLookupService(String user, String base, String xmlTemplate) {
		this.base = base;
		this.xmlTemplate = xmlTemplate.replace('{0}', user);
	}
	
	public Map lookupPackageServiceStandard(String zipFrom, String zipTo){
		def params = [API:'StandardB', XML:java.net.URLEncoder.encode(xmlTemplate.replace('{1}', zipFrom).replace('{2}', zipTo))]
		def xml = new URL(base + params.collect{ k,v -> "$k=$v" }.join('&')).text
		def map = [:]
		Matcher matcher = xml =~ ~/<Days>(.+)<\/Days>/
		try {
			if(matcher.find()){
				map.put('DaysToShip', matcher?.getAt(0)?.getAt(1)?.toInteger())
			}
		} catch (Exception e) {}
		
		matcher = xml =~ ~/<Message>(.+)<\/Message>/
		if(matcher.find()){
			map.put('Message',matcher?.getAt(0)?.getAt(1))
		}
		
		return map
	}
}
