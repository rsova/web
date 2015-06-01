package ensemble.profiles;

import static org.junit.Assert.*

import java.util.regex.Matcher

import org.junit.Test

class ProfileParserTest {

	@Test
	public void testGetSegments() {
		ProfileParser pp = new ProfileParser('2.4', 'ADT_A01')
		List requiredSeqments = pp.getCoreSegments()
		assertNotNull(requiredSeqments)
		println requiredSeqments
	}
	
	//@Test
	void test1() {
		def segments = "MSH~EVN~PID~[~PD1~]~[~{~ROL~}~]~[~{~NK1~}~]~PV1~[~PV2~]~[~{~ROL~}~]~[~{~DB1~}~]~[~{~OBX~}~]~[~{~AL1~}~]~[~{~DG1~}~]~[~DRG~]~[~{~PR1~[~{~ROL~}~]~}~]~[~{~GT1~}~]~[~{~IN1~[~IN2~]~[~{~IN3~}~]~[~{~ROL~}~]~}~]~[~ACC~]~[~UB1~]~[~UB2~]~[~PDA~]"
		Matcher matcher = segments =~ /\[([^\[\]]*)\]/
		segments = matcher.replaceAll('*')
		println segments
		
		while (segments.contains('[')){
			matcher = segments =~ /\[([^\[\]]*)\]/
			segments = matcher.replaceAll('*')
		}
				
		println segments
	}
	
	//@Test
	void test2() {
		def requiredSegments = 'MSH~EVN~PID~*~*~*~PV1~*~*~*~*~*~*~*~*~*~*~*~*~*~*'
		List list = requiredSegments.split('~')
		println list
		list.removeAll('*')
		println list
	}
	
//	@Test
	void testB() {
		def doc = """
	<Export generator="Cache" version="25" zv="Cache for Windows (x86-64) 2014.1.3 (Build 775U)" ts="2015-04-24 15:08:05">
		<Document name="2.4.HL7">
			<Category name="2.4" std="1">
	
				<MessageGroup name='ACK' description='General acknowledgment message' />
				<MessageGroup name='ADR' description='ADT response' />
			</Category>
		</Document>
	</Export>		"""
		def export = new XmlSlurper().parseText( doc)
		//println export.Document.Category.MessageGroup.findAll { it.@name =='ACK'}.@name.each { div -> println div }
		println export.Document.Category.MessageGroup.findAll { it.@name =='ACK'}.each { div -> println div.@description.text() +' : '+ div.@name.text()}

	}

}
