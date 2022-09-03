package cl.jp.calculator;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CalculatorTests {
	private Calculator calculator = new Calculator();

	@Test
	public void testSum() {
		assertEquals(5, calculator.sum(2, 3));
	}
}
