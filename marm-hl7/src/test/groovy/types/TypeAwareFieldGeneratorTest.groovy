package types;

import static org.junit.Assert.*

import org.junit.Test

class TypeAwareFieldGeneratorTest {

	@Test
	public void testGenerateLengthBoundId() {
		Random random = new Random();
		
		TypeAwareFieldGenerator tfg = new TypeAwareFieldGenerator()
		println tfg.generateLengthBoundId(9, "12345")
		println tfg.generateLengthBoundId(3, "12345678")
		println tfg.generateLengthBoundId(10, Math.abs(random.nextInt()).toString())
	}

}
