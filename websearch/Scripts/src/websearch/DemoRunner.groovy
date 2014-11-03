package websearch

def demo='http://demo.ramidx.com/ListExample.php?WhatList=condo'
process = ['curl',
//	'-u ramaui:demo1',
//	'-H', 'Cookie: WebSearchID=r2so30lip7g5rp8ibonnej9ha2; D_SID=74.123.217.146:3rK08PPRKVcCOWmibSyxVs9T5+e0ovpplUhtdFEsY+4; D_PID=3119DF0B-3C06-308A-88B4-6118E4B86D16; D_IID=0B90D182-B361-3247-BBB8-F4E25374A8DD; D_UID=2DF8F0F0-54E8-3AC8-9B9F-566FA9407FDA; D_HID=D/r38/ISyXBwlPemp21MktzpUBkVEfdJwsLQeFu/JaM',
	'-H', 'Host: websearch.ramidx.com',
	'-H', 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0',
//	'-H', 'Referer: http://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=2&ved=0CEUQFjAB&url=http%3A%2F%2Fwebsearch.ramidx.com%2Fsmartframe%2Fsearch.php%3FTask%3DFeatured%26dbid%3Ddbid1139259934&ei=QClQVIyNOYrloAS94IDYCg&usg=AFQjCNG0E1DL0i6QfXuUVOF6qhnLXHH49g&sig2=U3MM35gIxIM9MsFPcyxEQQ&bvm=bv.78597519,d.cGU',
	 demo]
//.execute()
//println process.text
//"http://websearch.ramidx.com/smartframe/search.php?Task=Search&dbid=dbid1139259934&WhatPropType%5B%5D=Vacant%20Land&WhatDistrict%5B%5D=Haiku&WhatSortType1=ListPrice&WhatSortDirection1=ASC"

demo='http://websearch.ramidx.com/ramxml.php/?list=condo&dbid=dbid1139259934'
//'dbid=dbid1139259934',
process = ['curl',
           //'-u ramaui:demo1',
	'-H', 'Cookie: WebSearchID=r2so30lip7g5rp8ibonnej9ha2; D_SID=74.123.217.146:3rK08PPRKVcCOWmibSyxVs9T5+e0ovpplUhtdFEsY+4; D_PID=3119DF0B-3C06-308A-88B4-6118E4B86D16; D_IID=0B90D182-B361-3247-BBB8-F4E25374A8DD; D_UID=2DF8F0F0-54E8-3AC8-9B9F-566FA9407FDA; D_HID=D/r38/ISyXBwlPemp21MktzpUBkVEfdJwsLQeFu/JaM',
           '-H', 'Host: websearch.ramidx.com',
           '-H', 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0',
//	'-H', 'Referer: http://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=2&ved=0CEUQFjAB&url=http%3A%2F%2Fwebsearch.ramidx.com%2Fsmartframe%2Fsearch.php%3FTask%3DFeatured%26dbid%3Ddbid1139259934&ei=QClQVIyNOYrloAS94IDYCg&usg=AFQjCNG0E1DL0i6QfXuUVOF6qhnLXHH49g&sig2=U3MM35gIxIM9MsFPcyxEQQ&bvm=bv.78597519,d.cGU',
   demo].execute()
   println process.text
 