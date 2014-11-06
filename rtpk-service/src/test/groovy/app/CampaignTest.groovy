package app
import static org.junit.Assert.*;

//import com.google.gson.Gson
//https://github.com/stanfy/gson-xml
import org.json.XML
import org.json.JSONObject
import com.google.gson.GsonBuilder
import groovy.xml.*

import org.junit.Test;


class CampaignTest {

	@Test
	public void testMockCompaign1() {
		Campaign compaign = new Campaign();
		compaign.teams = []
		
		Team t = new Team(utc:new Utc(code:'FFMFS', name:'Mobile Field Surgery Team'), description:'Magic Mock Team')
		List creds = []
		creds.add( new Credential(name:'ABS', description:'The American Board of Surgery Certification',active:true, issued: new GregorianCalendar(2008, Calendar.DECEMBER, 25).time,expired: new GregorianCalendar(2016, Calendar.DECEMBER, 25).time))
		creds.add( new Credential(name:'GSC-113', description:'General Surgery Certification',active:true, issued: new GregorianCalendar(2008, Calendar.SEPTEMBER, 1).time,expired: new GregorianCalendar(2015, Calendar.SEPTEMBER, 25).time))
	    creds.add( new Credential(name:'USMLE', description:'States Medical Licensing Examination',active:true, issued: new GregorianCalendar(2010, Calendar.SEPTEMBER, 10).time,expired: new GregorianCalendar(2017, Calendar.SEPTEMBER, 10).time))
		
		Personal p = new Personal(utc:new Utc(code:'FFMAA', name:'Surgent'),fullName:'John T. Right', available:true, location:'123 Apple Street, Honolulu, HI',score:100, credentials: creds)
		t.personal =[]
		t.personal.add(p)
		compaign.teams.add(t)
		
		def jsonStr = new GsonBuilder().setPrettyPrinting().create().toJson(compaign)
		println jsonStr
		
		println "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
		JSONObject json = new JSONObject(jsonStr);
		String xml = XmlUtil.serialize(XML.toString(json))
		println xml
	}
	
	
	//Gson 
	
}
