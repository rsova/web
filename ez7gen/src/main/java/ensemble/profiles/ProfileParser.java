package ensemble.profiles;

import java.io.IOException;
import java.util.List;
//import java.util.regex.Matcher;

import org.apache.commons.io.IOUtils;

public class ProfileParser {
	//final static String REGEX = /\[([^\[\]]*)\]/;
	final static String MASK = "*";
	final static String LBRCKT = "[";
	final static String RBRCKT = "]";
	final static String BLNK = "";

	String version;
	String messageType;

	public ProfileParser(String version, String messageType) {
		this.version = version;
		this.messageType = messageType;
	}

	public List getCoreSegments() throws IOException{
		String xml = IOUtils.toString(this.getClass().getResourceAsStream("/base24.xml"), "UTF-8");
		String msgStructure = getMessageStructure(xml, messageType);
		List reqSegments = getRequiredSegments(msgStructure);
		return reqSegments;
	}

	protected String getMessageStructure(String xml, String message) {
//		def export = new XmlSlurper().parseText(xml)
//		// Get Message Type Attributes
//		def attributes = export.Document.Category.MessageType.findAll { it.@name == message}.collect { div -> return [name:div.@name.text(),structure: div.@structure.text(), returntype: div.@returntype.text()]}
//		//Get Message Structure Profile by Message Structure Name
//		def struct = attributes.get(0).structure
//		def segments = export.Document.Category.MessageStructure.findAll { it.@name == struct}.collect { div -> return div.@definition}
//		return segments
		return "";
	}

	protected List getRequiredSegments(String struct){
//		String requiredSegments = removeOptionalSegments(struct, MASK)
//		String[] list = requiredSegments.split("~");
//		//list.removeAll(MASK)
//		list.remove(MASK)
//		return list
		return null;
	}

	protected void removeOuterBrackets(String struct) {
		struct = replaceFirst(struct, LBRCKT, BLNK);
		struct = replaceLast(struct, RBRCKT, BLNK);
	}

	protected String removeOptionalSegments(String segments, String replacementToken ){
		removeOuterBrackets(segments);

		//Remove all optional segments
		while (segments.contains(LBRCKT)){
			segments = removeInerMostOptionalSegments(segments, replacementToken);
		}
		return segments;
	}

	protected String removeInerMostOptionalSegments(String segments, String replacementToken){
//		Matcher matcher = segments =~ /\[([^\[\]]*)\]/
//		//Matcher matcher = segments =~ REGEX
//		segments = matcher.replaceAll(replacementToken)
//		//println segments
//		return segments
		return null;
	}

	protected String replaceFirst(String string, String substring, String replacement){
		int index = string.indexOf(substring);
		return (index == -1)?string:replacement + string.substring(index+substring.length());
	}
	
	protected String replaceLast(String string, String substring, String replacement){
		int index = string.lastIndexOf(substring);
		return (index == -1)?string: string.substring(0, index) + replacement + string.substring(index+substring.length());
	}
}
