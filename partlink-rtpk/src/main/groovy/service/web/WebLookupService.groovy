package service.web
import javax.inject.Inject;
import service.web.parser.WebContentParser

class WebLookupService {
	private String uri
	private String magicNbr

	@Inject
	WebContentParser parser	
	
	public WebLookupService (String uri){
		this.uri = uri
	}
		
	// Uses WebFlis web site to get most recent information for a supplier for single product
	// Uses parser to scrape cage codes, and other bits of data
	public Collection lookupByNiin(String Niin){
		def process = ['curl',uri<<"?niin=$Niin"].execute()
		return parser.parse(process.text)	
	}
	
	
	// Uses WebFlis web site to get most recent information for suppliers for list of products
		public Collection lookup(List niins){
			def matches
			for(NIIN in niins){
				def process = ['curl',
				'-H','Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8', '-H','Connection: keep-alive', //headers optional
				//"-d $magicNbr&txtNiin=$NIIN",
				 uri].execute()
				 matches = parser.parse(process.text)?.each {it.put('niin', NIIN)}
			}
			return matches
		}
	

}
