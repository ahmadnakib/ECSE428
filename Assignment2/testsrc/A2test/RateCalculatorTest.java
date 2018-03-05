package A2test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import A2.RateCalculator;

public class RateCalculatorTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	@Before
	public void setUp() throws Exception {
		System.setOut(new PrintStream(outContent));
	}

	@Test
	public void testNoArgs() {
		RateCalculator.main(null);
		assertEquals("No arguments provided!", outContent.toString());
	}
	
	@Test
	public void testArgsNotSeven() {
		String[] testArr = {"30", "20"};
		RateCalculator.main(testArr);
		assertEquals("Input has to be of the form: FromPostalCode ToPostalCode Length(cm) Width(cm) Height(cm) Weight (kg) PostType(Regular Xpress Priority)", outContent.toString());
	}
	
	@Test
	public void testFromPostal() {
		String[] testArr = new String[]{"111111", "H2X3L1", "10", "20", "10", "10", "Regular"};
		RateCalculator.main(testArr);
		assertEquals("Invalid FromPostalCode", outContent.toString());
	}
	
	@Test
	public void testToPostal() {
		String[] testArr = new String[]{"H2X3R2", "ABCDEF", "10", "20", "10", "10", "Regular"};
		RateCalculator.main(testArr);
		assertEquals("Invalid ToPostalCode", outContent.toString());
	}
	
	@Test
	public void testNonFloatDimensions() {
		String[] testArr = new String[]{"H2X3R2", "H2X3L1", "float", "float", "float", "10", "Regular"};
		RateCalculator.main(testArr);
		assertEquals("length, width, height and weight should all be floating numbers", outContent.toString());
	}

	@Test
	public void testLengthOutOfRange() {
		String[] testArr = new String[]{"H2X3R2", "H2X3L1", "-30", "20", "10", "10", "Regular"};
		RateCalculator.main(testArr);
		assertEquals("length has to be less than 200 and greater than 0", outContent.toString());
	}
	
	@Test
	public void testWidthOutOfRange() {
		String[] testArr = new String[]{"H2X3R2", "H2X3L1", "30", "5000", "10", "10", "Regular"};
		RateCalculator.main(testArr);
		assertEquals("width has to be less than 200 and greater than 0", outContent.toString());
	}
	
	@Test
	public void testHeightOutOfRange() {
		String[] testArr = new String[]{"H2X3R2", "H2X3L1", "30", "20", "1000", "10", "Regular"};
		RateCalculator.main(testArr);
		assertEquals("height has to be less than 200 and greater than 0", outContent.toString());
	}
	
	@Test
	public void testWeightOutOfRange() {
		String[] testArr = new String[]{"H2X3R2", "H2X3L1", "30", "20", "10", "35", "Regular"};
		RateCalculator.main(testArr);
		assertEquals("weight has to be less than 30 and greater than 0", outContent.toString());
	}
	
	@Test
	public void testLengthPlusGirth() {
		String[] testArr = new String[]{"H2X3R2", "H2X3L1", "200", "200", "200", "30", "Regular"};
		RateCalculator.main(testArr);
		assertEquals("Length + girth must not exceed 300 cm", outContent.toString());
	}
	
	@Test
	public void testInvalidType() {
		String[] testArr = new String[]{"H2X3R2", "H2X3L1", "10", "20", "10", "10", "Type"};
		RateCalculator.main(testArr);
		assertEquals("Post type can only be 'Regular', 'Express', or 'Priority'", outContent.toString());
	}
	
	@Test
	public void testLengthAboveRange() {
		String[] testArr = new String[]{"H2X3R2", "H2X3L1", "1000", "20", "10", "10", "Regular"};
		RateCalculator.main(testArr);
		assertEquals("length has to be less than 200 and greater than 0", outContent.toString());
	}
	
	@Test
	public void testWidtBelowRange() {
		String[] testArr = new String[]{"H2X3R2", "H2X3L1", "30", "-50", "10", "10", "Regular"};
		RateCalculator.main(testArr);
		assertEquals("width has to be less than 200 and greater than 0", outContent.toString());
	}
	
	@Test
	public void testHeightBelowRange() {
		String[] testArr = new String[]{"H2X3R2", "H2X3L1", "30", "20", "-40", "10", "Regular"};
		RateCalculator.main(testArr);
		assertEquals("height has to be less than 200 and greater than 0", outContent.toString());
	}
	
	@Test
	public void testWeightBelowRange() {
		String[] testArr = new String[]{"H2X3R2", "H2X3L1", "30", "20", "10", "-10", "Regular"};
		RateCalculator.main(testArr);
		assertEquals("weight has to be less than 30 and greater than 0", outContent.toString());
	}
	
	@Test
	public void testRegular() {
		String[] testArr = new String[]{"H2X3R2", "H2X3L1", "10", "20", "10", "11.3", "regular"};
		RateCalculator.main(testArr);
		assertEquals("The rate is: 18.15", outContent.toString());
	}
	
	@Test
	public void testXpress() {
		String[] testArr = new String[]{"K1A0B1", "H2X3L1", "10", "20", "10", "1", "xpress"};
		RateCalculator.main(testArr);
		assertEquals("The rate is: 13.14", outContent.toString());
	}
	
	@Test
	public void testPriority() {
		String[] testArr = new String[]{"K1A0B1", "H2X3L1", "10", "20", "10", "1", "priority"};
		RateCalculator.main(testArr);
		assertEquals("The rate is: 24.31", outContent.toString());
	}
}
