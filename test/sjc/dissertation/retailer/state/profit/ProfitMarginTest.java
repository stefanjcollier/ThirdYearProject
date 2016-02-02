package sjc.dissertation.retailer.state.profit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class ProfitMarginTest {

	final double HIGH_PROFIT_MARGIN = 0.2;
	final double LOW_PROFIT_MARGIN = 0.1;
	final double NO_PROFIT_MARGIN = 0d;
	final double NEGATIVE_PROFIT_MARGIN = -0.1;



	/**
	 * A test to ensure that the equals works.
	 * High = High, Low = Low, High != Low, etc
	 */
	@Test
	public void testEqualsMethodWorks(){
		//GIVEN each ProfitMargin instance
		final ProfitMargin hpm = ProfitMargin.HighProfitMargin;
		final ProfitMargin lpm = ProfitMargin.LowProfitMargin;
		final ProfitMargin nopm = ProfitMargin.NoProfitMargin;
		final ProfitMargin negpm = ProfitMargin.NegativeProfitMargin;

		//THEN They are equal to themselves
		assertEquals("High should equal itself", hpm, hpm);
		assertEquals("High should equal itself", lpm, lpm);
		assertEquals("No profit should equal itself", nopm, nopm);
		assertEquals("Negative should equal itself", negpm, negpm);

		//AND V-High != High != Medium != Low != V-High
		assertNotEquals("High should not equal Low", hpm, lpm);
		assertNotEquals("Low should not equal No Profit", lpm, nopm);
		assertNotEquals("No Profit should not equal Negative", nopm, negpm);
		assertNotEquals("Negative should not equal High", negpm, hpm);


		//GIVEN one more of each ProfitMargin instance
		final ProfitMargin hpm2 = ProfitMargin.HighProfitMargin;
		final ProfitMargin lpm2 = ProfitMargin.LowProfitMargin;
		final ProfitMargin nopm2 = ProfitMargin.NoProfitMargin;
		final ProfitMargin negpm2 = ProfitMargin.NegativeProfitMargin;

		//THEN the new instances should equal their origonal counterparts
		assertEquals("The new High instance should equal the first High instance",hpm, hpm2);
		assertEquals("The new Low instance should equal the first Low instance", lpm, lpm2);
		assertEquals("The new No Profit instance should equal the first No Profit instance", nopm, nopm2);
		assertEquals("The new Negative instance should equal the first Negative instance", negpm, negpm2);
	}

	/**
	 * A test to ensure the cost of each level is correct.
	 *
	 * Methods Under Test: Constructor, getCost()
	 */
	@Test
	public void testThatTheValuesAreCorrect(){
		//GIVEN each ProfitMargin instance
		final ProfitMargin hpm = ProfitMargin.HighProfitMargin;
		final ProfitMargin lpm = ProfitMargin.LowProfitMargin;
		final ProfitMargin nopm = ProfitMargin.NoProfitMargin;
		final ProfitMargin negpm = ProfitMargin.NegativeProfitMargin;

		//WHEN getting their profit margins in percentages
		final double hProf = hpm.getProfitMargin();
		final double lProf = lpm.getProfitMargin();
		final double noProf = nopm.getProfitMargin();
		final double negProf = negpm.getProfitMargin();

		//THEN their percentages are the intended value
		assertEquals("High percentage not correct", this.HIGH_PROFIT_MARGIN, hProf, 0);
		assertEquals("Low percentage not correct", this.LOW_PROFIT_MARGIN, lProf, 0);
		assertEquals("No Profit percentage not correct", this.NO_PROFIT_MARGIN, noProf, 0);
		assertEquals("Negative percentage not correct", this.NEGATIVE_PROFIT_MARGIN, negProf, 0);
	}

	@Test
	public void testHighProfitMarginChangeWorks(){
		fail("Not Implemented");
	}

	@Test
	public void testLowProfitMarginChangeWorks(){
		fail("Not Implemented");
	}

	@Test
	public void testNoProfitMarginChangeWorks(){
		fail("Not Implemented");
	}

	@Test
	public void testNegativeProfitMarginChangeWorks(){
		fail("Not Implemented");
	}
}
