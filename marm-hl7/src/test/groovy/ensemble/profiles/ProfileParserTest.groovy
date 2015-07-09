package ensemble.profiles;

import static org.junit.Assert.*

import java.util.regex.Matcher
import java.util.regex.Pattern

import org.junit.Test

class ProfileParserTest {

	@Test
	public void testGetMessageStructure() {
		def doc = """
	<Export generator="Cache" version="25" zv="Cache for Windows (x86-64) 2014.1.3 (Build 775U)" ts="2015-04-24 15:08:05">
		<Document name="2.4.HL7">
			<Category name="2.4" std="1">
	
				<MessageGroup name='ACK' description='General acknowledgment message' />
				<MessageGroup name='ADR' description='ADT response' />

				<MessageEvent name='Z98' description='Dispense History (response)' />
				<MessageEvent name='Z99' description='Who Am I' />

				<MessageType name='ACK' structure='ACK' />
				<MessageType name='ACK_N02' structure='ACK_N02' />
				<MessageType name='ACK_R01' structure='ACK_R01' />
				<MessageType name='ADR_A19' structure='ADR_A19' />
				<MessageType name='ADT_A01' structure='ADT_A01' returntype='ACK_A01' />

				<MessageStructure name='ADR_A19' definition='MSH~MSA~[~ERR~]~[~QAK~]~QRD~[~QRF~]~{~[~EVN~]~PID~[~PD1~]~[~{~ROL~}~]~[~{~NK1~}~]~PV1~[~PV2~]~[~{~ROL~}~]~[~{~DB1~}~]~[~{~OBX~}~]~[~{~AL1~}~]~[~{~DG1~}~]~[~DRG~]~[~{~PR1~[~{~ROL~}~]~}~]~[~{~GT1~}~]~[~{~IN1~[~IN2~]~[~{~IN3~}~]~[~{~ROL~}~]~}~]~[~ACC~]~[~UB1~]~[~UB2~]~}~[~DSC~]' />
			   <MessageStructure name='ADT_A01' definition='MSH~EVN~PID~[~PD1~]~[~{~ROL~}~]~[~{~NK1~}~]~PV1~[~PV2~]~[~{~ROL~}~]~[~{~DB1~}~]~[~{~OBX~}~]~[~{~AL1~}~]~[~{~DG1~}~]~[~DRG~]~[~{~PR1~[~{~ROL~}~]~}~]~[~{~GT1~}~]~[~{~IN1~[~IN2~]~[~{~IN3~}~]~[~{~ROL~}~]~}~]~[~ACC~]~[~UB1~]~[~UB2~]~[~PDA~]' />

			</Category>
		</Document>
	</Export>		"""
	
		ProfileParser pp = new ProfileParser('2.4', 'ADT_A01')
		String struct = pp.getMessageStructure(doc, pp.messageType)
		assertNotNull(struct)
		println struct
	}
	
	@Test
	public void testGetSegments() {
			
		ProfileParser pp = new ProfileParser('2.4', 'ADT_A01')
		Map segments = pp.getSegments()
		assertNotNull(segments)
		println segments
	}

	@Test
	public void testGetCoreSegments() {
		ProfileParser pp = new ProfileParser('2.4', 'ADT_A01')
		List requiredSeqments = pp.getCoreSegments()
		assertNotNull(requiredSeqments)
		assertEquals(4, requiredSeqments.size())
		println requiredSeqments
	}
	
	
	@Test
	void test1() {
		def segments = "MSH~EVN~PID~[~PD1~]~[~{~ROL~}~]~[~{~NK1~}~]~PV1~[~PV2~]~[~{~ROL~}~]~[~{~DB1~}~]~[~{~OBX~}~]~[~{~AL1~}~]~[~{~DG1~}~]~[~DRG~]~[~{~PR1~[~{~ROL~}~]~}~]~[~{~GT1~}~]~[~{~IN1~[~IN2~]~[~{~IN3~}~]~[~{~ROL~}~]~}~]~[~ACC~]~[~UB1~]~[~UB2~]~[~PDA~]"
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
	}

	@Test
	void testProcessSegments() {
		ProfileParser pp = new ProfileParser('2.4', 'ADT_A01')
		Map m = pp.processSegments("MSH~EVN~PID~[~PD1~]~[~{~ROL~}~]~[~{~NK1~}~]~PV1~[~PV2~]~[~{~ROL~}~]~[~{~DB1~}~]~[~{~OBX~}~]~[~{~AL1~}~]~[~{~DG1~}~]~[~DRG~]~[~{~PR1~[~{~ROL~}~]~}~]~[~{~GT1~}~]~[~{~IN1~[~IN2~]~[~{~IN3~}~]~[~{~ROL~}~]~}~]~[~ACC~]~[~UB1~]~[~UB2~]~[~PDA~]")
		//def segments = m.base.split ("~")
		assertEquals("MSH~EVN~PID~0~1~2~PV1~3~4~5~6~7~8~9~19~11~20~15~16~17~18", m.profile)
		
	}
	
	@Test
	void testGetSegmentStructure() {
		def xml = """
	<Export generator="Cache" version="25" zv="Cache for Windows (x86-64) 2014.1.3 (Build 775U)" ts="2015-04-24 15:08:05">
		<Document name="2.4.HL7">
			<Category name="2.4" std="1">
	
				<MessageGroup name='ACK' description='General acknowledgment message' />
				<MessageGroup name='ADR' description='ADT response' />

				<SegmentStructure name='ROL' description='Role'>
					<SegmentSubStructure piece='1' description='Role Instance ID' datatype='EI' symbol='?' max_length='60' required='C' ifrepeating='0' />
					<SegmentSubStructure piece='2' description='Action Code'
						datatype='ID' symbol='!' max_length='2' required='R' ifrepeating='0'
						codetable='287' />
					<SegmentSubStructure piece='3' description='Role-ROL'
						datatype='CE' symbol='!' max_length='250' required='R' ifrepeating='0'
						codetable='443' />
					<SegmentSubStructure piece='4' description='Role Person'
						datatype='XCN' symbol='+' max_length='250' required='R'
						ifrepeating='1' />
					<SegmentSubStructure piece='5'
						description='Role Begin Date/Time' datatype='TS' max_length='26'
						required='O' ifrepeating='0' />
					<SegmentSubStructure piece='6' description='Role End Date/Time'
						datatype='TS' max_length='26' required='O' ifrepeating='0' />
					<SegmentSubStructure piece='7' description='Role Duration'
						datatype='CE' max_length='250' required='O' ifrepeating='0' />
					<SegmentSubStructure piece='8' description='Role Action Reason'
						datatype='CE' max_length='250' required='O' ifrepeating='0' />
					<SegmentSubStructure piece='9' description='Provider Type'
						datatype='CE' symbol='*' max_length='250' required='O' ifrepeating='1' />
					<SegmentSubStructure piece='10'
						description='Organization Unit Type - ROL' datatype='CE'
						max_length='250' required='O' ifrepeating='0' codetable='406' />
					<SegmentSubStructure piece='11' description='Office/Home Address'
						datatype='XAD' symbol='*' max_length='250' required='O'
						ifrepeating='1' />
					<SegmentSubStructure piece='12' description='Phone'
						datatype='XTN' symbol='*' max_length='250' required='O'
						ifrepeating='1' />
				</SegmentStructure>
			</Category>
		</Document>
	</Export>		"""
	
		ProfileParser pp = new ProfileParser('2.4', 'ADT_A01')
		List fields = pp.getSegmentStructure(xml,"ROL")
		assertEquals(12,fields.size())
		
	}
	@Test
	void testGetCodeTable() {
		def xml = """
				<Export generator="Cache" version="25" zv="Cache for Windows (x86-64) 2014.1.3 (Build 775U)" ts="2015-04-24 15:08:05">
				<Document name="2.4.HL7">
				<Category name="2.4" std="1">
				
				<MessageGroup name='ACK' description='General acknowledgment message' />
				<MessageGroup name='ADR' description='ADT response' />
				
				<CodeTable name='286' tabletype='1' description='Provider role'>
					<Enumerate position='1' value='RP' description='Referring Provider' />
					<Enumerate position='2' value='PP' description='Primary Care Provider' />
					<Enumerate position='3' value='CP' description='Consulting Provider' />
					<Enumerate position='4' value='RT' description='Referred to Provider' />
				</CodeTable>
				<CodeTable name='287' tabletype='2' description='Problem/goal action code'>
					<Enumerate position='1' value='AD' description='ADD' />
					<Enumerate position='2' value='CO' description='CORRECT' />
					<Enumerate position='3' value='DE' description='DELETE' />
					<Enumerate position='4' value='LI' description='LINK' />
					<Enumerate position='5' value='UC' description='UNCHANGED *' />
					<Enumerate position='6' value='UN' description='UNLINK' />
					<Enumerate position='7' value='UP' description='UPDATE' />
				</CodeTable>
			</Category>
				</Document>
				</Export>		"""
				
		ProfileParser pp = new ProfileParser('2.4', 'ADT_A01')
		List vals = pp.getCodeTable(xml,"287")
		assertEquals(7, vals.size())
	}


}
