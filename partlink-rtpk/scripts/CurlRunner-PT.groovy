import service.web.parser.WebContentParser
//import groovyx.net.http.HTTPBuilder
//import static groovyx.net.http.Method.GET
//import static groovyx.net.http.ContentType.TEXT

//String NIIN = '016033650'
//NIIN = '015127231'
//def uri = 'http://www.logisticsinformationservice.dla.mil/WebFlis/pub/pub_search.aspx'
//def data = '&__VIEWSTATE=%2FwEPDwUJNTMzMDY2Nzk3D2QWAgICD2QWJAIID2QWAgIBDzwrAAsAZAIJD2QWAgIDDzwrAAsAZAIKD2QWAgIBDzwrAAsAZAILD2QWAgIBDzwrAAsAZAIMD2QWAgIBDzwrAAsAZAIND2QWAgIBDzwrAAsAZAIOD2QWAgIDDzwrAAsAZAIPD2QWAgIBDzwrAAsAZAIQD2QWAgIDDzwrAAsAZAIRD2QWBAIDDzwrAAsAZAIFDzwrAAsAZAISD2QWAgIBDzwrAAsAZAITD2QWAgIBDzwrAAsAZAIUD2QWAgIJDzwrAAsAZAIVD2QWAgIFDzwrAAsAZAIWD2QWAgIHDzwrAAsAZAIXD2QWAgIFDzwrAAsAZAIYD2QWAgIFDzwrAAsAZAIZD2QWAgIFDzwrAAsAZBgBBR5fX0NvbnRyb2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WCQURY2hrRXhwYW5kZWRTZWFyY2gFB2Noa0NhZ2UFAkMxBQJDMwUCQzYFAkM0BQJDNwUCQzkFA0MxNCThyg84CZcp1hAIXCkU7WWhv6pn'+
//'&__EVENTVALIDATION=%2FwEWEQK196%2FRAgLDkqnvDQLHkL%2B1AgK%2BsdPcCAK54Yr1AQLIhLeDDAKE3r2bCAK%2B61kC3v3%2B%2FgsC4a%2BaDQLd7%2BbtDALd797tDALd79LtDALd79rtDALd787tDALd74buDALj04CPBJ5oI514%2BmUpSMjvGSLrGeZpCy%2Be'
//def process = ['curl',
//'-H','Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8', '-H','Connection: keep-alive', //headers optional
//"-d $data"+"&btnNIIN=Go"+"&C1=on"+"&C6=on"+"&txtNiin=$NIIN",
//uri].execute()
//	 
//def html = process.text
//println html
//def parser = new WebContentParser()
//println parser.parse(html)*.'CAGE CD'
//def a =  ["curl",  "-H", "Content-Type: application/json", "-H", "Accept: application/json", "-X PUT", "-d", data, uri].execute().text
//def initialSize = 4096
//def out = new ByteArrayOutputStream(initialSize)
//def err = new ByteArrayOutputStream(initialSize)
//def proc = command.execute()
//process.consumeProcessOutput(out, err)
//process.waitFor()
//println err
//asyncsearchurl=advancedsearchasync.aspx
//callid=25f6147c-1502-4e6b-9a80-3d073d143f60
//formid=top_search_form
//m=875
//originalsearchtext=
//pagename=
//r=aHR0cDovL3d3dy5wYXJ0dGFyZ2V0LmNvbS9pbmRleC5hc3B4
//searchOption=sku
//searchtext=015127231
//searchtype=0
//userguid=EF048307-9F10-4856-B9F6-F28A9CF911D2
//http://production.shippingapis.com/ShippingAPITest.dll?API=PriorityMail&XML=<PriorityMailRequest USERID="066QBASE5466"> <OriginZip>55344</OriginZip> <DestinationZip>97653</DestinationZip> </PriorityMailRequest>

//Priority Mail
import groovy.time.*

def base = 'http://production.shippingapis.com/ShippingAPITest.dll?'
//def api ='API=PriorityMail'
def xml =
'''<PriorityMailRequest USERID="066QBASE5466"><OriginZip>55344</OriginZip><DestinationZip>97653</DestinationZip></PriorityMailRequest>'''
def params = [API:'PriorityMail', XML:java.net.URLEncoder.encode(xml)]
def uri = base + params.collect{ k,v -> "$k=$v" }.join('&')
def html = new URL(uri).text
println html

//Package
base = 'http://production.shippingapis.com/ShippingAPITest.dll?'
xml = '<StandardBRequest USERID="066QBASE5466"><OriginZip>97653</OriginZip><DestinationZip>55344</DestinationZip></StandardBRequest>'
params = [API:'StandardB', XML:java.net.URLEncoder.encode(xml)]
//API=StandardB
def start = new Date()
html = new URL(base + params.collect{ k,v -> "$k=$v" }.join('&')).text
use ( TimeCategory ) {println "time to run : " + (new Date() - start)}

println html
//def process = new URI(uri)
//def html = process.getText([API:'PriorityMail',XML:xml])
//def http = new HTTPBuilder('http://www.google.com')
//def html = http.get( path : '/ShippingAPITest.dll', query : [API:'PriorityMail', XML:xml] )
//println html
	
//uri =	'''http://production.shippingapis.com/ShippingAPI.dll?API=RateV4&XML=<RateV4Request USERID="066QBASE5466"><Revision/><Package ID="1ST"> <Service>PRIORITY</Service> <ZipOrigination>44106</ZipOrigination> <ZipDestination>20770</ZipDestination> <Pounds>1</Pounds> <Ounces>8</Ounces> <Container>NONRECTANGULAR</Container> <Size>LARGE</Size> <Width>15</Width> <Length>30</Length> <Height>15</Height> <Girth>55</Girth> </Package> </RateV4Request>'''
//java.net.URISyntaxException: Illegal character in query at index 76: 
//http://production.shippingapis.com/ShippingAPITest.dll?API=PriorityMail&XML=<PriorityMailRequest USERID="066QBASE5466"><OriginZip>55344</OriginZip><DestinationZip>97653</DestinationZip></PriorityMailRequest>
