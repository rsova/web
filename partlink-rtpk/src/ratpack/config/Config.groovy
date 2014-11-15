app {
	mongo {
		host = 'ds035280.mongolab.com'
		port = 35280
		db   = 'db1'
		user = 'rs-user'
		pass = 'passw0rd'
	}
	
	web{
		//uri = 'http://www.logisticsinformationservice.dla.mil/WebFlis/pub/pub_search.aspx'
		uri = "http://www.logisticsinformationservice.dla.mil/webflis/pub/pub_search.aspx"
//		magicNbr = '&__VIEWSTATE=%2FwEPDwUJNTMzMDY2Nzk3D2QWAgICD2QWJAIID2QWAgIBDzwrAAsAZAIJD2QWAgIDDzwrAAsAZAIKD2QWAgIBDzwrAAsAZAILD2QWAgIBDzwrAAsAZAIMD2QWAgIBDzwrAAsAZAIND2QWAgIBDzwrAAsAZAIOD2QWAgIDDzwrAAsAZAIPD2QWAgIBDzwrAAsAZAIQD2QWAgIDDzwrAAsAZAIRD2QWBAIDDzwrAAsAZAIFDzwrAAsAZAISD2QWAgIBDzwrAAsAZAITD2QWAgIBDzwrAAsAZAIUD2QWAgIJDzwrAAsAZAIVD2QWAgIFDzwrAAsAZAIWD2QWAgIHDzwrAAsAZAIXD2QWAgIFDzwrAAsAZAIYD2QWAgIFDzwrAAsAZAIZD2QWAgIFDzwrAAsAZBgBBR5fX0NvbnRyb2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WCQURY2hrRXhwYW5kZWRTZWFyY2gFB2Noa0NhZ2UFAkMxBQJDMwUCQzYFAkM0BQJDNwUCQzkFA0MxNCThyg84CZcp1hAIXCkU7WWhv6pn'+
//		'&__EVENTVALIDATION=%2FwEWEQK196%2FRAgLDkqnvDQLHkL%2B1AgK%2BsdPcCAK54Yr1AQLIhLeDDAKE3r2bCAK%2B61kC3v3%2B%2FgsC4a%2BaDQLd7%2BbtDALd797tDALd79LtDALd79rtDALd787tDALd74buDALj04CPBJ5oI514%2BmUpSMjvGSLrGeZpCy%2Be'+
//		"&btnNIIN=Go"+
//		"&C1=on"+
//		"&C6=on"
	}
	swt{
		sparklEndPoint ='http://api.xsb.com/sparql/query'
	}
	usps{
		user='066QBASE5466'
		base = 'http://production.shippingapis.com/ShippingAPITest.dll?'
		xmlTemplate = '<StandardBRequest USERID="{0}"><OriginZip>{1}</OriginZip><DestinationZip>{2}</DestinationZip></StandardBRequest>'
	}
	
}