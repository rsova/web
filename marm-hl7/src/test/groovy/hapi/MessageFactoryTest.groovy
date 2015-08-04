package hapi;

import static org.junit.Assert.*

import java.util.List;

import org.junit.Test

import segments.MagicSegmentGenerator
import service.ContextGenerator;
import service.MessageFactory;
import service.picker.SmartSegmentPickerService
import groovy.time.Duration
import groovy.time.TimeCategory

class MessageFactoryTest {
/*
 * -phone#
 * -timestamp
 * codetable range
 * codetable sub
 */
	@Test
	public void testGenerate() {
		MagicSegmentGenerator msgen = new MagicSegmentGenerator();
		msgen.maxReps = 5 // 1 for no reps				
		msgen.fieldGenerator.firstNames = ['Bob','Bill']
		msgen.fieldGenerator.lastNames = ['Smith','Lee']
		msgen.fieldGenerator.streetNames=["504 Honey Elk Wynd","7608 Silent Glen","7741 Foggy Pond Jetty","7061 Iron Blossom Ridge","5777 Fallen Panda Expressway"]
		msgen.fieldGenerator.cities=["Oatmeal","Owl","Dollar Settlement","Saint-Quentin","Ragtown"]
		msgen.fieldGenerator.states=["TN","MS","TN","MS","GA"]
		msgen.fieldGenerator.zips=["37166-9572","38889-6760","37326-6978","39377-7406","39833-8563"]
		msgen.fieldGenerator.countries=["USA","USA","USA","USA","USA"]
		msgen.fieldGenerator.phones=["(931)872-7634","(601)110-8688","(731)538-7434","(662)120-6474","(470)446-7282"]
		msgen.fieldGenerator.allStates = ["AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"]
		
		SmartSegmentPickerService segmentPicker = new SmartSegmentPickerService()
		segmentPicker.loadFactor = 1
		MessageFactory mf = new MessageFactory()
		mf.magicSegmentGenerator = msgen
		mf.smartSegmentPickerService = segmentPicker
		
		
		def msg = mf.generate("2.4","ADT_A01")
		println new ContextGenerator().outAsEr7(msg, true)
	}
	
		
	@Test
	void testHelper() {
//		def formatter = java.text.NumberFormat.
//		def values = [0, 100000000, 9123123.25, 10.20, 1907.23]
//		def formatted = values.collect  { formatter.format(it) }
//		def maxLen = formatted*.length().max()
//		println formatted.collect { it.padLeft(maxLen) }.join("\n")
		 Random random = new Random();
		 
		def dbl = ((Math.abs(random.nextDouble())) *1000)
		println dbl
		println String.format("%.2f", (Math.abs(random.nextDouble())) *1000)
		println Math.abs(random.nextInt())
		println String.format("%07d", (Math.abs(random.nextInt())))
		
		def map = ["description":"Something Balance "]
		def moneyFormatIndicator = ["Balance", "Charges", "Adjustments","Income","Amount","Money"]
		def a = moneyFormatIndicator.find{ map?.description?.contains(it)}
		println a	
		println null!= moneyFormatIndicator.find{ map?.description?.contains(it)}//check for specific numeric for money
		
		map =[]
		a = moneyFormatIndicator.find{ map?.description?.contains(it)}
		println a
		println null!= moneyFormatIndicator.find{ map?.description?.contains(it)}//check for specific numeric for money
		
		println '-----------------------------'
		def max_length=83
		println String.sprintf("%0${max_length}d", 123456)
		def frmt = "%"+max_length+"d"
		println frmt
		max_length=9
		println '1234iuyiyiooiiuyuyyiuuiiyuiy'.padLeft(max_length,'0')
		fail()
		
		def str = Math.abs(random.nextInt()).toString()
		println str[0..(max_length >str.length()?str.length()-1 :max_length)]
		//fail()
		map = ['max_length':'2']
		def maxlen = (map.max_length)?map.max_length.toInteger():1
		println maxlen
		str = Math.abs(random.nextInt()).toString()
		def idx = (maxlen >str.length()?str.length()-1:maxlen)
		println idx
		println str[1..idx]
	    println str[1..(maxlen >str.length()?str.length()-1:maxlen)]
		
		map = [required:'O']
		println map.required in ['X','W','B']
		map = [required:'X']
		println map.required in ['X','W','B']
		
		//TS[20140806062704.486-1000]
		def isFutureEvent = true
		def val = ''
		int seed = 52 //seed bounds duration of time to 52 weeks, a year baby...
		use(TimeCategory) {
			Duration duration = Math.abs(random.nextInt() % seed).toInteger().week
			Date evnt = (isFutureEvent)?new Date() + duration:new Date() - duration
			//time of an event (TSComponentOne)
			val = evnt.format("YYYYMMDDHHSS.SSS")
			//degree of precision (ST)
		} 
		//YYYY[MM[DD[HHMM[SS[.S[S[S[S]]]]]]]][+/-ZZZZ]
		//println Date.parse("YYYYMMDDHHSS.SS", val)
		
		println val
		
		//O1 ... O10
		//def code = 'O1 ... O10'
		def code = '...'
		println code
		if(code.contains("...")){
			def range = code.tokenize("...")
			//1 range
			code = (range.size() > 0)?range?.get(0)?.trim():''
			// code = range?.get(0)?.trim()
			//range.each{println it.trim()}
			}
			//2 junk
		println code
		
		//def z = "Q1".."Q6"
		//def z = "${code}"
		//println z
		def tableName = '73'
		if(tableName in ['72','88','132','264','269','471','9999']){
			println tableName
			println '-----------'
		}

	}
	
	//@Test
	void testGenerateDemo2() {
		//MSH, EVN, PID, PV1, OBX, AL1, DG1 (where OBX, AL1 and DG1 are repeating and optional).
		//MSH, EVN, PID, PV1, PD1, AL1, DG1
		MagicSegmentGenerator msgen = new MagicSegmentGenerator();
		msgen.maxReps = 5 // 1 for no reps
		msgen.fieldGenerator.firstNames = ['Bob','Bill']
		msgen.fieldGenerator.lastNames = ['Smith','Lee']
		msgen.fieldGenerator.streetNames=["504 Honey Elk Wynd","7608 Silent Glen","7741 Foggy Pond Jetty","7061 Iron Blossom Ridge","5777 Fallen Panda Expressway"]
		msgen.fieldGenerator.cities=["Oatmeal","Owl","Dollar Settlement","Saint-Quentin","Ragtown"]
		msgen.fieldGenerator.states=["TN","MS","TN","MS","GA"]
		msgen.fieldGenerator.zips=["37166-9572","38889-6760","37326-6978","39377-7406","39833-8563"]
		msgen.fieldGenerator.countries=["US","US","US","US","US"]
		msgen.fieldGenerator.phones=["(931) 872-7634","(601) 110-8688","(731) 538-7434","(662) 120-6474","(470) 446-7282"]
		msgen.fieldGenerator.allStates = ["AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"]
		
		SmartSegmentPickerService segmentPicker = new SmartSegmentPickerService()
//		SmartSegmentPickerService segmentPicker = new SmartSegmentPickerService(){
//			@Override
//			public List pickSegments(){
//				return ['MSH', 'EVN', 'PID', 'PV1', '[~PD1~]', '[~{~AL1~}~]', '[~{~DG1~}~]']
//				//[MSH, EVN, PID, [~PD1~], [~{~ROL~}~], [~{~NK1~}~], PV1, [~PV2~], [~{~ROL~}~], [~{~DB1~}~], [~{~OBX~}~], [~{~AL1~}~], [~{~DG1~}~], [~DRG~], [~{~GT1~}~], [~ACC~], [~UB1~], [~UB2~], [~PDA~]]
//			}
//			
//		}
		segmentPicker.loadFactor = 1
		MessageFactory mf = new MessageFactory()
		mf.magicSegmentGenerator = msgen
		mf.smartSegmentPickerService = segmentPicker
		
		
		def msg = mf.generate1("2.4","ADT_A01")
		println new ContextGenerator().outAsEr7(msg, true)

	}

}
