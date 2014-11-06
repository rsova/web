package scripts

import app.Campaign
import app.Credential
import app.Personal
import app.Team
import app.Utc
import com.google.gson.GsonBuilder
import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCursor
import com.mongodb.DBObject
import com.mongodb.MongoDB.Bson.*
import com.mongodb.MongoClient
import  com.mongodb.util.JSON

//def name = "ratpack-footer.png"
//def url = "http://www.ratpack-framework.org/images/ratpack-footer.png"

Campaign compaign = new Campaign();
compaign.teams = []

Team t = new Team(utc:new Utc(code:'FFMFS', name:'Mobile Field Surgery Team'), description:'Mickey Mouse Hospital')

//Surgent
List creds = []
creds.add( new Credential(name:'ABS', description:'The American Board of Surgery Certification',active:true, issued: new GregorianCalendar(2008, Calendar.DECEMBER, 25).time,expired: new GregorianCalendar(2016, Calendar.DECEMBER, 25).time))
creds.add( new Credential(name:'GSC-113', description:'General Surgery Certification',active:true, issued: new GregorianCalendar(2008, Calendar.SEPTEMBER, 1).time,expired: new GregorianCalendar(2015, Calendar.SEPTEMBER, 25).time))
creds.add( new Credential(name:'USMLE', description:'States Medical Licensing Examination',active:true, issued: new GregorianCalendar(2010, Calendar.SEPTEMBER, 10).time,expired: new GregorianCalendar(2017, Calendar.SEPTEMBER, 10).time))

Personal p = new Personal(utc:new Utc(code:'FFMAA', name:'Surgent'),fullName:'Mickey T. Mouse', available:true, location:'123 Apple Street, Kihei, HI',score:100, credentials: creds)
t.personal =[]
t.personal.add(p)

//Nurse
creds = []
creds.add( new Credential(name:'XYZ', description:'Ambulatory Care Nursing Certification',active:true, issued: new GregorianCalendar(2009, Calendar.NOVEMBER, 25).time,expired: new GregorianCalendar(2016, Calendar.OCTOBER, 25).time))
creds.add( new Credential(name:'AGNPC', description:'Advanced General Nursing Practice Certification',active:true, issued: new GregorianCalendar(2012, Calendar.OCTOBER, 1).time,expired: new GregorianCalendar(2015, Calendar.SEPTEMBER, 25).time))
p = new Personal(utc:new Utc(code:'FFMAN', name:'Nurse'),fullName:'Minnie Mouse', available:true, location:'123 Pear Street, Honolulu, HI',score:90, credentials: creds)
t.personal.add(p)

//Anesthesiologist 
creds.add( new Credential(name:'FGH', description:'Primary Anesthesiology Certification',active:false, issued: new GregorianCalendar(2013, Calendar.JUNE, 25).time,expired: new GregorianCalendar(2014, Calendar.JUNE, 25).time))
p = new Personal(utc:new Utc(code:'FFMAN', name:'Anesthesiologist'),fullName:'Chip', available:true, location:'999 Apple Street, Alaska, AK',score:20, credentials: creds)
t.personal.add(p)

//Anesthesiologist
creds = []
creds.add( new Credential(name:'CDMC', description:'Critical Care Medicine Certification',active:true, issued: new GregorianCalendar(2012, Calendar.AUGUST, 25).time,expired: new GregorianCalendar(2016, Calendar.JUNE, 25).time))
creds.add( new Credential(name:'HPMC', description:'Hospice & Palliative Medicine Certification',active:true, issued: new GregorianCalendar(2010, Calendar.SEPTEMBER, 1).time,expired: new GregorianCalendar(2015, Calendar.SEPTEMBER, 25).time))
p = new Personal(utc:new Utc(code:'FFMAN', name:'Anesthesiologist'),fullName:'Dale', available:true, location:'12 Apple Street, Kihei, HI',score:70, credentials: creds)
t.personal.add(p)

compaign.teams.add(t)

def jsonStr = new GsonBuilder().setPrettyPrinting().create().toJson(compaign)
println jsonStr
System.exit(0)
















MongoClient client = new MongoClient("ds035280.mongolab.com", 35280)
DB db = client.getDB("db1")
db.authenticate("rs-user", "passw0rd" as char[])
def coll = db.getCollection("compaign")
//coll.insert new BasicDBObject(name:'mock', compaign: jsonStr)
//DBObject dbObject = (DBObject)JSON.parse(jsonStr);
//coll.find(new BasicDBObject('name','mickey1')).collect { it.remove() }
BasicDBObject whereQuery = new BasicDBObject();
whereQuery.put('name', 'mickey');
//db.getCollection("compaign").find(whereQuery).collect{it.remove()}
DBCursor cursor = db.getCollection("compaign").find(whereQuery)
//DBObject dbo = (cursor.hasNext())?cursor.next():null // get one
try {
	while(cursor.hasNext()) {
		DBObject dbo = cursor.next()
		coll.remove(dbo)
	}
 } finally {
	cursor.close();
 }
println 'Number of documents before insert: ' + coll.getCount()
BasicDBObject dbObject = (BasicDBObject)JSON.parse(jsonStr);
coll.insert(dbObject.append('name', 'mickey'))
println 'Number of documents after insert: ' + coll.getCount()

