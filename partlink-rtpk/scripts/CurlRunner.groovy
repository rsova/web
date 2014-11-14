import service.web.parser.WebContentParser
import groovy.time.*
def start = new Date()

String NIIN = '016033650'
//NIIN = '015127231'
//NIIN = '010229722'
 uri = 'http://www.logisticsinformationservice.dla.mil/WebFlis/pub/pub_search.aspx'
def data = '&__VIEWSTATE=%2FwEPDwUJNTMzMDY2Nzk3D2QWAgICD2QWJAIID2QWAgIBDzwrAAsAZAIJD2QWAgIDDzwrAAsAZAIKD2QWAgIBDzwrAAsAZAILD2QWAgIBDzwrAAsAZAIMD2QWAgIBDzwrAAsAZAIND2QWAgIBDzwrAAsAZAIOD2QWAgIDDzwrAAsAZAIPD2QWAgIBDzwrAAsAZAIQD2QWAgIDDzwrAAsAZAIRD2QWBAIDDzwrAAsAZAIFDzwrAAsAZAISD2QWAgIBDzwrAAsAZAITD2QWAgIBDzwrAAsAZAIUD2QWAgIJDzwrAAsAZAIVD2QWAgIFDzwrAAsAZAIWD2QWAgIHDzwrAAsAZAIXD2QWAgIFDzwrAAsAZAIYD2QWAgIFDzwrAAsAZAIZD2QWAgIFDzwrAAsAZBgBBR5fX0NvbnRyb2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WCQURY2hrRXhwYW5kZWRTZWFyY2gFB2Noa0NhZ2UFAkMxBQJDMwUCQzYFAkM0BQJDNwUCQzkFA0MxNCThyg84CZcp1hAIXCkU7WWhv6pn'+
'&__EVENTVALIDATION=%2FwEWEQK196%2FRAgLDkqnvDQLHkL%2B1AgK%2BsdPcCAK54Yr1AQLIhLeDDAKE3r2bCAK%2B61kC3v3%2B%2FgsC4a%2BaDQLd7%2BbtDALd797tDALd79LtDALd79rtDALd787tDALd74buDALj04CPBJ5oI514%2BmUpSMjvGSLrGeZpCy%2Be'
def process = ['curl',
//'-H','Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8', '-H','Connection: keep-alive', //headers optional
	'-H', 'Host: www.logisticsinformationservice.dla.mil', '-H','User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0', '-H','Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8', '-H','Accept-Language: en-US,en;q=0.5', '-H','Accept-Encoding: gzip, deflate', '-H','DNT: 1', '-H','Referer: http://www.logisticsinformationservice.dla.mil/WebFlis/pub/pub_search.aspx', '-H','Connection: keep-alive', '-H','Content-Type: application/x-www-form-urlencoded',
"-d $data"+"&btnNIIN=Go"+"&C1=on"+"&C6=on"+"&txtNiin=$NIIN",
uri].execute()
	 
def html = process.text
//println html
def parser = new WebContentParser()
def partStuff = parser.parse(html)
println partStuff
def cc =  partStuff*.'CAGE CD'
cc.removeAll([null])
println cc
def prod = partStuff*.'PROD'
prod.removeAll([null])
println prod.first()

use ( TimeCategory ) {println "time to run : " + (new Date() - start)}
//def a =  ["curl",  "-H", "Content-Type: application/json", "-H", "Accept: application/json", "-X PUT", "-d", data, uri].execute().text
//def initialSize = 4096
//def out = new ByteArrayOutputStream(initialSize)
//def err = new ByteArrayOutputStream(initialSize)
//def proc = command.execute()
//process.consumeProcessOutput(out, err)
//process.waitFor()
//println err