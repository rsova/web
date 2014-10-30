package service.web
import javax.inject.Inject;
import service.web.parser.WebContentParser

class WebLookupService {
	
	@Inject
	WebContentParser parser	
	private String uri
	private String magicNbr
	
	
	public WebLookupService (String uri, String magicNbr){
		this.uri = uri
		this.magicNbr = magicNbr
	}
	
	public Collection lookup(List niins){
		def matches 
		for(NIIN in niins){	
//		def data = "-d '__LASTFOCUS=&__VIEWSTATE=%2FwEPDwUJNTMzMDY2Nzk3D2QWAgICD2QWJAIID2QWAgIBDzwrAAsAZAIJD2QWAgIDDzwrAAsAZAIKD2QWAgIBDzwrAAsAZAILD2QWAgIBDzwrAAsAZAIMD2QWAgIBDzwrAAsAZAIND2QWAgIBDzwrAAsAZAIOD2QWAgIDDzwrAAsAZAIPD2QWAgIBDzwrAAsAZAIQD2QWAgIDDzwrAAsAZAIRD2QWBAIDDzwrAAsAZAIFDzwrAAsAZAISD2QWAgIBDzwrAAsAZAITD2QWAgIBDzwrAAsAZAIUD2QWAgIJDzwrAAsAZAIVD2QWAgIFDzwrAAsAZAIWD2QWAgIHDzwrAAsAZAIXD2QWAgIFDzwrAAsAZAIYD2QWAgIFDzwrAAsAZAIZD2QWAgIFDzwrAAsAZBgBBR5fX0NvbnRyb2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WCQURY2hrRXhwYW5kZWRTZWFyY2gFB2Noa0NhZ2UFAkMxBQJDMwUCQzYFAkM0BQJDNwUCQzkFA0MxNCThyg84CZcp1hAIXCkU7WWhv6pn&__VIEWSTATEGENERATOR=E69BC198&__EVENTTARGET=&__EVENTARGUMENT=&__EVENTVALIDATION=%2FwEWEQK196%2FRAgLDkqnvDQLHkL%2B1AgK%2BsdPcCAK54Yr1AQLIhLeDDAKE3r2bCAK%2B61kC3v3%2B%2FgsC4a%2BaDQLd7%2BbtDALd797tDALd79LtDALd79rtDALd787tDALd74buDALj04CPBJ5oI514%2BmUpSMjvGSLrGeZpCy%2Be&txtNiin=016033650&btnNIIN=Go&txtKeyword=&txtPART=&txtCAGE=&txtManName=&txtManPartNum=&C1=on&C6=on&C4=on&C14=on'"
//		def process = ['curl','-H','Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8','-H','Accept-Encoding: gzip, deflate', '-H','Accept-Language: en-US,en;q=0.5', '-H','Connection: keep-alive', '-H','DNT: 1', '-H','Host: www.logisticsinformationservice.dla.mil', '-H','Referer: http://www.logisticsinformationservice.dla.mil/WebFlis/pub/pub_search.aspx', '-H','User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:32.0) Gecko/20100101 Firefox/32.0', '-H','Content-Type: application/x-www-form-urlencoded', data, uri].execute()		
		def process = ['curl',
		'-H','Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8', '-H','Connection: keep-alive', //headers optional
		"-d $magicNbr&txtNiin=$NIIN",
		 uri].execute() 
		 matches = parser.parse(process.text)?.each {it.put('niin', NIIN)}
		 
		 //matches.add(current)
		}
		return matches
	}
	
	public Collection lookupByNiin(String Niin){
		def process = ['curl',
		               '-H','Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8', '-H','Connection: keep-alive', //headers optional
		               "-d $magicNbr&txtNiin=$Niin",
		               uri].execute() 
		return parser.parse(process.text)?.each {it.put('niin', Niin)}	
	}

}
