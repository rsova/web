package segments

import java.lang.invoke.MethodHandleImpl.BindCaller.T

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import service.MessageFactory;
import types.TypeAwareFieldGenerator
import ca.uhn.hl7v2.model.AbstractMessage
import ca.uhn.hl7v2.model.AbstractSegment
import ca.uhn.hl7v2.model.Type

@Component
//@Configuration
class MagicSegmentGenerator implements ISegmentGenerator {
	//Set set = []
	static final Random random = new Random()
	
	int maxReps = 5
	TypeAwareFieldGenerator fieldGenerator = new TypeAwareFieldGenerator()

	@Value('''#{'${person.names.first}'.split(',')}''') List<String> firstNames
	@Value('''#{'${person.names.last}'.split(',')}''') List<String> lastNames
	
	@Value('''#{'${address.streetNames}'.split(',')}''') List<String> streetNames
	@Value('''#{'${address.cities}'.split(',')}''') List<String> cities
	@Value('''#{'${address.states}'.split(',')}''') List<String> states
	@Value('''#{'${address.zips}'.split(',')}''') List<String> zips
	@Value('''#{'${address.countries}'.split(',')}''') List<String> countries
	@Value('''#{'${phones}'.split(',')}''') List<String> phones

	
//	public MagicSegmentGenerator() {}

	public AbstractMessage generate(AbstractMessage message, String segment, List attributes) {
		
		boolean isRep = isSegmentRepeated(segment)
		String segmentName = getSegmentName(segment)
		
		int totalReps = (isRep)? Math.abs(random.nextInt() % maxReps ):1 //decide if segment needs to repeat and how many times
		
		for(int i=0; i<totalReps; i++){
			AbstractSegment seg = (isRep)?message."get$segmentName"(i) :message."get$segmentName"()
			generateSegment(attributes, seg)
		}
		println ('--------------')
		println MessageFactory.set
		return message
	}
	
	// Metadata removed segment to get its name 
	//TODO: message dup in ProfileParser
	public String getSegmentName(String segment){
		return segment.replaceAll("~|\\[|\\]|\\{|\\}","")
	}
	
	//Check if segment alows repetitions
	public boolean isSegmentRepeated(String segment){
		return segment.contains("~{")
	}
	
	// Generate a Segment based on list of attributes for each field
	// Method uses reflection to call particular type of field implementation
	public void generateSegment(List details, AbstractSegment seg) {
		for(int i=0; i<details.size(); i++){
			def attributes = details.get(i)
			
			//Get field by position and add it to the map, which will be sent to generator
			def fld = seg.getTypedField(attributes?.piece?.toInteger(), 0)
			//((Type<T>)fld)
			Map map = ['fld':fld]
			map.putAll(attributes)
			
			//Derive the method name from the data type, and call generator method via 'reflection'
			def methodName = attributes.datatype?.toLowerCase()
			try{
				println methodName
				fieldGenerator."${methodName}"(map)
			}catch (Exception e){
				//MessageFactory.set.add(methodName)
				println e.message
			}
		}
	}


}
