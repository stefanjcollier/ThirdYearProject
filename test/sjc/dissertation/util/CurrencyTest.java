package sjc.dissertation.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CurrencyTest {

	@Test
	public void test() {
		final double small = 1234.5678;
		final double big = 23555666.1199;

		final String strSmall= Currency.prettyString(small);
		assertEquals("The small value should become: £1,234.57","£1,234.57",strSmall);

		final String strBig= Currency.prettyString(big);
		assertEquals("The big value should become: £23,555,666.12","£23,555,666.12",strBig);
	}

}
