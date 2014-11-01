package websearch

//curl 
//def uri = 'http://websearch.ramidx.com/smartframe/search.php?Task=Featured&dbid=dbid1139259934'
//def uri = 'http://websearch.ramidx.com/smartframe/search.php?Task=Featured&dbid=dbid1139259934'
//def uri = 'http://websearch.ramidx.com/smartframe/search.php?Task=Featured&dbid=dbid1139259934'
//def uri = 'http://demo.ramidx.com/ListExample.php?WhatList=district'
//-H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8'
//-H 'Accept-Encoding: gzip, deflate'
//-H 'Accept-Language: en-US,en;q=0.5' 
//-H 'Cache-Control: max-age=0' 
//-H 'Connection: keep-alive' 
//-H 'Cookie: WebSearchID=r2so30lip7g5rp8ibonnej9ha2; D_SID=74.123.217.146:3rK08PPRKVcCOWmibSyxVs9T5+e0ovpplUhtdFEsY+4; D_PID=3119DF0B-3C06-308A-88B4-6118E4B86D16; D_IID=0B90D182-B361-3247-BBB8-F4E25374A8DD; D_UID=2DF8F0F0-54E8-3AC8-9B9F-566FA9407FDA; D_HID=D/r38/ISyXBwlPemp21MktzpUBkVEfdJwsLQeFu/JaM' 
//-H 'DNT: 1' 
//-H 'Host: websearch.ramidx.com' 
//-H 'Referer: http://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=2&ved=0CEUQFjAB&url=http%3A%2F%2Fwebsearch.ramidx.com%2Fsmartframe%2Fsearch.php%3FTask%3DFeatured%26dbid%3Ddbid1139259934&ei=QClQVIyNOYrloAS94IDYCg&usg=AFQjCNG0E1DL0i6QfXuUVOF6qhnLXHH49g&sig2=U3MM35gIxIM9MsFPcyxEQQ&bvm=bv.78597519,d.cGU' 
//-H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0'

//def process = ['curl',
//	'-user, ramaui:demo1',
//	'-H', 'Cookie: WebSearchID=r2so30lip7g5rp8ibonnej9ha2; D_SID=74.123.217.146:3rK08PPRKVcCOWmibSyxVs9T5+e0ovpplUhtdFEsY+4; D_PID=3119DF0B-3C06-308A-88B4-6118E4B86D16; D_IID=0B90D182-B361-3247-BBB8-F4E25374A8DD; D_UID=2DF8F0F0-54E8-3AC8-9B9F-566FA9407FDA; D_HID=D/r38/ISyXBwlPemp21MktzpUBkVEfdJwsLQeFu/JaM',
//	'-H', 'Host: websearch.ramidx.com',
//	//'-H', 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0',
////	'-H', 'Referer: http://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=2&ved=0CEUQFjAB&url=http%3A%2F%2Fwebsearch.ramidx.com%2Fsmartframe%2Fsearch.php%3FTask%3DFeatured%26dbid%3Ddbid1139259934&ei=QClQVIyNOYrloAS94IDYCg&usg=AFQjCNG0E1DL0i6QfXuUVOF6qhnLXHH49g&sig2=U3MM35gIxIM9MsFPcyxEQQ&bvm=bv.78597519,d.cGU',	
//	 uri].execute()
//def html = process.text
//println html
http://websearch.ramidx.com/smartframe/search.php?Task=Search&dbid=dbid1142571627&WhatPropType%5B%5D=Condominium&WhatCondo%5B%5D=Alaeloa&WhatSortType1=ListPrice&WhatSortDirection1=ASC
def search = "http://websearch.ramidx.com/smartframe/search.php?Task=Search&dbid=dbid1139259934&WhatPropType%5B%5D=Vacant%20Land&WhatDistrict%5B%5D=Haiku&WhatSortType1=ListPrice&WhatSortDirection1=ASC"
def process = ['curl',
	//'-u ramaui:demo1',
	'-H', 'Cookie: WebSearchID=r2so30lip7g5rp8ibonnej9ha2, D_SID=74.123.217.146:3rK08PPRKVcCOWmibSyxVs9T5+e0ovpplUhtdFEsY+4; D_PID=3119DF0B-3C06-308A-88B4-6118E4B86D16; D_IID=0B90D182-B361-3247-BBB8-F4E25374A8DD; D_UID=2DF8F0F0-54E8-3AC8-9B9F-566FA9407FDA; D_HID=D/r38/ISyXBwlPemp21MktzpUBkVEfdJwsLQeFu/JaM',
	'-H', 'Host: websearch.ramidx.com',
	'-H', 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0',
//	'-H', 'Referer: http://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=2&ved=0CEUQFjAB&url=http%3A%2F%2Fwebsearch.ramidx.com%2Fsmartframe%2Fsearch.php%3FTask%3DFeatured%26dbid%3Ddbid1139259934&ei=QClQVIyNOYrloAS94IDYCg&usg=AFQjCNG0E1DL0i6QfXuUVOF6qhnLXHH49g&sig2=U3MM35gIxIM9MsFPcyxEQQ&bvm=bv.78597519,d.cGU',
	 search].execute()
def html  = process.text
println html
new File('out.html')<<html
//println html

//Document doc = Jsoup.connect("http://example.com").userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0").get();
//String NIIN = '016033650'
//NIIN = '015127231'
//def uri = 'http://www.logisticsinformationservice.dla.mil/WebFlis/pub/pub_search.aspx'
//def data = '&__VIEWSTATE=%2FwEPDwUJNTMzMDY2Nzk3D2QWAgICD2QWJAIID2QWAgIBDzwrAAsAZAIJD2QWAgIDDzwrAAsAZAIKD2QWAgIBDzwrAAsAZAILD2QWAgIBDzwrAAsAZAIMD2QWAgIBDzwrAAsAZAIND2QWAgIBDzwrAAsAZAIOD2QWAgIDDzwrAAsAZAIPD2QWAgIBDzwrAAsAZAIQD2QWAgIDDzwrAAsAZAIRD2QWBAIDDzwrAAsAZAIFDzwrAAsAZAISD2QWAgIBDzwrAAsAZAITD2QWAgIBDzwrAAsAZAIUD2QWAgIJDzwrAAsAZAIVD2QWAgIFDzwrAAsAZAIWD2QWAgIHDzwrAAsAZAIXD2QWAgIFDzwrAAsAZAIYD2QWAgIFDzwrAAsAZAIZD2QWAgIFDzwrAAsAZBgBBR5fX0NvbnRyb2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WCQURY2hrRXhwYW5kZWRTZWFyY2gFB2Noa0NhZ2UFAkMxBQJDMwUCQzYFAkM0BQJDNwUCQzkFA0MxNCThyg84CZcp1hAIXCkU7WWhv6pn'+
//'&__EVENTVALIDATION=%2FwEWEQK196%2FRAgLDkqnvDQLHkL%2B1AgK%2BsdPcCAK54Yr1AQLIhLeDDAKE3r2bCAK%2B61kC3v3%2B%2FgsC4a%2BaDQLd7%2BbtDALd797tDALd79LtDALd79rtDALd787tDALd74buDALj04CPBJ5oI514%2BmUpSMjvGSLrGeZpCy%2Be'
//def process = ['curl',
//'-H','Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8', '-H','Connection: keep-alive', //headers optional
//"-d $data"+"&btnNIIN=Go"+"&C1=on"+"&C6=on"+"&txtNiin=$NIIN",
//uri].execute()
	 
//def html = process.text
