package sjc.dissertation.retailer.state.profit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ProfitMarginChangeTest {


	/**
	 * A test to see that the IncreaseProfitMargin enum has the correct code.
	 *
	 * Methods Under Test: Constructor, getSymbol()
	 */
	@Test
	public void testIncreaseValue() {
		final ProfitMarginChange pmc = ProfitMarginChange.IncreaseProfitMargin;
		assertEquals("The 'IncreaseQuality' should have code: <+P>",
				"<+P>", pmc.getSymbol());
	}

	/**
	 * A test to see that the MaintainProfitMargin enum has the correct code.
	 *
	 * Methods Under Test: Constructor, getSymbol()
	 */
	@Test
	public void testMaintainValue() {
		final ProfitMarginChange pmc = ProfitMarginChange.MaintainProfitMargin;
		assertEquals("The 'MaintainQuality' should have code: <~P>",
				"<~P>", pmc.getSymbol());
	}

	/**
	 * A test to see that the DecreaseProfitMargin enum has the correct code.
	 *
	 * Methods Under Test: Constructor, getSymbol()
	 */
	@Test
	public void testDecreaseValue() {
		final ProfitMarginChange pmc = ProfitMarginChange.DecreaseProfitMargin;
		assertEquals("The 'DecreaseQuality' should have code: <-P>",
				"<-P>", pmc.getSymbol());
	}

}
