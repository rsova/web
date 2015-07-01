package ensemble.profiles

import java.util.regex.Matcher

import org.apache.commons.io.IOUtils

class ProfileParser {
	final static String REGEX = /\[([^\[\]]*)\]/
	final static String MASK = '*'
	final static String LBRCKT = '['
	final static String RBRCKT = ']'
	final static String LBRS = '{'
	final static String RBRS = '}'
	final static String SEGCNTRL = '~'
	final static String BLNK = ''

	String version
	String messageType

	public ProfileParser(String version, String messageType) {
		this.version = version;
		this.messageType = messageType;
	}

	public List getCoreSegments(){
		String xml = IOUtils.toString(this.getClass().getResourceAsStream("/base24.xml"), "UTF-8");
		def msgStructure = getMessageStructure(xml, messageType)
		def reqSegments = getRequiredSegments(msgStructure)
		return reqSegments
	}

	protected String getMessageStructure(String xml, String message) {
		def export = new XmlSlurper().parseText(xml)
		// Get Message Type Attributes
		def attributes = export.Document.Category.MessageType.findAll { it.@name == message}.collect { div -> return [name:div.@name.text(),structure: div.@structure.text(), returntype: div.@returntype.text()]}
		//Get Message Structure Profile by Message Structure Name
		def struct = attributes.get(0).structure
		def segments = export.Document.Category.MessageStructure.findAll { it.@name == struct}.collect { div -> return div.@definition}
		return segments
	}

	protected List getRequiredSegments(String struct){
		String requiredSegments = removeOptionalSegments(struct, MASK)
		List list = requiredSegments.split('~')
		list.removeAll(MASK)
		return list
	}

	static public String removeOuterBrackets(String struct) {
		struct = replaceFirst(struct, LBRCKT, BLNK)
		struct = replaceLast(struct, RBRCKT, BLNK)
		return struct
	}

	protected String removeOptionalSegments(String segments, String replacementToken ){
		segments = removeOuterBrackets(segments)

		//Remove all optional segments
		while (segments.contains(LBRCKT)){
			segments = removeInerMostOptionalSegments(segments, replacementToken)
		}
		return segments
	}

	protected String removeInerMostOptionalSegments(String segments, String replacementToken){
		Matcher matcher = segments =~ /\[([^\[\]]*)\]/
		//Matcher matcher = segments =~ REGEX
		segments = matcher.replaceAll(replacementToken)
		//println segments
		return segments
	}

	static public String replaceFirst(String string, String substring, String replacement){
		int index = string.indexOf(substring);
		return (index == -1)?string:replacement + string.substring(index+substring.length());
	}
	
	static public String replaceLast(String string, String substring, String replacement){
		int index = string.lastIndexOf(substring);
		return (index == -1)?string: string.substring(0, index) + replacement + string.substring(index+substring.length());
	}
	
	protected Map getSegments(){
		String xml = IOUtils.toString(this.getClass().getResourceAsStream("/base24.xml"), "UTF-8");
		def msgStructure = removeOuterBrackets(getMessageStructure(xml, messageType))
		return processSegments(msgStructure)
	}

	private Map processSegments(String segments) {
		def segColl = []
		Integer idx = 0;

		while(segments.contains("[")){

			Matcher matcher = segments =~ /\[([^\[\]]*)\]/

			StringBuffer sb = new StringBuffer();
			while (matcher.find()) {
				matcher.appendReplacement(sb, idx.toString());
				idx++
			}
			matcher.appendTail(sb);
			segments = sb.toString()
			System.out.println(segments);

			matcher.each{ segColl.add(it);}
		}
		segColl.eachWithIndex {o,i -> println i +": "+o }
		return [profile: segments, collection: segColl]
	}
}
