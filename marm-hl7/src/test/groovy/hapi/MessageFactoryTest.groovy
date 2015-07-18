package hapi;

import static org.junit.Assert.*

import org.junit.Test

import segments.MagicSegmentGenerator
import service.picker.SmartSegmentPickerService

class MessageFactoryTest {

	@Test
	public void testGenerate() {
		MagicSegmentGenerator msgen = new MagicSegmentGenerator();
		msgen.maxReps = 5 //no reps				
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
		segmentPicker.loadFactor = 1.0
		MessageFactory mf = new MessageFactory()
		mf.magicGenerator = msgen
		mf.segmentPicker = segmentPicker
		
		
		def msg = mf.generate1("2.4","ADT_A01")
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
	}

}
