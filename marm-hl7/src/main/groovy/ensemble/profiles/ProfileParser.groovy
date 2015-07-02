package ensemble.profiles

import groovy.util.slurpersupport.NodeChild

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
	
	protected Map getSegments(){
		String xml = IOUtils.toString(this.getClass().getResourceAsStream("/base24.xml"), "UTF-8");
		String segments = getMessageStructure(xml, messageType)
		return processSegments(segments)
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

	public List getCoreSegments(){
		String xml = IOUtils.toString(this.getClass().getResourceAsStream("/base24.xml"), "UTF-8");
		String msgStructure = getMessageStructure(xml, messageType)
		def reqSegments = getRequiredSegments(msgStructure)
		return reqSegments
	}
		
	protected String getMessageStructure(String xml, String message) {
		def export = new XmlSlurper().parseText(xml)
		String strtructAttribute = export.Document.Category.MessageType.find{ it.@name == message}.@structure.text()
		def structure = export.Document.Category.MessageStructure.find{ it.@name == strtructAttribute}.@definition.text()
		return structure
	}
		
		//R = Required; N = Not required; C = Conditionally Required/Recommended.
	protected List getSegmentStructure(String xml, String segment) {
		def export = new XmlSlurper().parseText(xml)
		NodeChild node = export.Document.Category.SegmentStructure.find{ it.@name == segment}
		//Collect all attributes
		//List pieces = node.SegmentSubStructure.collect{ return[datatype:it.@datatype, required: it.@required, ifrepeating:it.@ifrepeating, codetable:it.@codetable] }
		List pieces = node.SegmentSubStructure.collect{ return it.attributes() }
		pieces.each{println it}
		return pieces
	}

	protected List getCodeTable(String xml, String tableName) {
		def export = new XmlSlurper().parseText(xml)
		List values = export.Document.Category.CodeTable.find { it.@name == tableName}.Enumerate.collect{return it.attributes() }
		values.each{println it}
		return values
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
		//segments = removeOuterBrackets(segments)

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
	
}
