package hapi;

import static org.junit.Assert.*

import org.junit.Test

import segments.MagicSegmentGenerator

class MessageFactoryTest {

	@Test
	public void test() {
		MagicSegmentGenerator segment = new MagicSegmentGenerator();				
		segment.fieldGenerator.firstNames = ['Bob','Bill']
		segment.fieldGenerator.lastNames = ['Smith','Lee']
		segment.fieldGenerator.streetNames=["504 Honey Elk Wynd","7608 Silent Glen","7741 Foggy Pond Jetty","7061 Iron Blossom Ridge","5777 Fallen Panda Expressway"]
		segment.fieldGenerator.cities=["Oatmeal","Owl","Dollar Settlement","Saint-Quentin","Ragtown"]
		segment.fieldGenerator.states=["TN","MS","TN","MS","GA"]
		segment.fieldGenerator.zips=["37166-9572","38889-6760","37326-6978","39377-7406","39833-8563"]
		segment.fieldGenerator.countries=["US","US","US","US","US"]
		segment.fieldGenerator.phones=["(931) 872-7634","(601) 110-8688","(731) 538-7434","(662) 120-6474","(470) 446-7282"]
		
		MessageFactory mf = new MessageFactory()
		mf.magicGenerator = segment
		
		def msg = mf.generate1("2.4","ADT_A01")
		println new ContextGenerator().outAsEr7(msg)
		
		
	}

}
