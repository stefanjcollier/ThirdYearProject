package sjc.dissertation.retailer.state.profit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

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
	public void testHighProfitMarginChangeWorks() throws InvalidProfitMarginException{
		//GIVEN a profit Margin
		final ProfitMargin pm = ProfitMargin.HighProfitMargin;

		//AND all change commands
		final ProfitMarginChange plus_pm = ProfitMarginChange.IncreaseProfitMargin;
		final ProfitMarginChange main_pm = ProfitMarginChange.MaintainProfitMargin;
		final ProfitMarginChange minus_pm = ProfitMarginChange.DecreaseProfitMargin;

		//WHEN commanding the margin to change
		final ProfitMargin pm_main_pm = pm.changeProfitMargin(main_pm);
		final ProfitMargin pm_minus_pm = pm.changeProfitMargin(minus_pm);

		//THEN High + Increase = throw exception
		try{
			pm.changeProfitMargin(plus_pm);
			fail("An exception should have been thrown.");

		}catch (final InvalidProfitMarginException e){
			// We wanted to catch an exception
		}

		//AND High + Maintain = High
		assertEquals("'High + Maintain = High' is not true", pm_main_pm, ProfitMargin.HighProfitMargin);

		//AND High + Decrease = Low
		assertEquals("'High + Decrease = Low' is not true", pm_minus_pm, ProfitMargin.LowProfitMargin);
	}

	@Test
	public void testLowProfitMarginChangeWorks() throws InvalidProfitMarginException{
		//GIVEN a profit Margin
		final ProfitMargin pm = ProfitMargin.LowProfitMargin;

		//AND all change commands
		final ProfitMarginChange plus_pm = ProfitMarginChange.IncreaseProfitMargin;
		final ProfitMarginChange main_pm = ProfitMarginChange.MaintainProfitMargin;
		final ProfitMarginChange minus_pm = ProfitMarginChange.DecreaseProfitMargin;

		//WHEN commanding the margin to change
		final ProfitMargin pm_plus_pm = pm.changeProfitMargin(plus_pm);
		final ProfitMargin pm_main_pm = pm.changeProfitMargin(main_pm);
		final ProfitMargin pm_minus_pm = pm.changeProfitMargin(minus_pm);

		//THEN Low + Increase = High
		assertEquals("'High + Maintain = High' is not true", pm_plus_pm, ProfitMargin.HighProfitMargin);

		//AND Low + Maintain = Low
		assertEquals("'High + Maintain = High' is not true", pm_main_pm, ProfitMargin.LowProfitMargin);

		//AND Low + Decrease = No Profit
		assertEquals("'High + Decrease = Low' is not true", pm_minus_pm, ProfitMargin.NoProfitMargin);
	}

	@Test
	public void testNoProfitMarginChangeWorks() throws InvalidProfitMarginException{
		//GIVEN a profit Margin
		final ProfitMargin pm = ProfitMargin.NoProfitMargin;

		//AND all change commands
		final ProfitMarginChange plus_pm = ProfitMarginChange.IncreaseProfitMargin;
		final ProfitMarginChange main_pm = ProfitMarginChange.MaintainProfitMargin;
		final ProfitMarginChange minus_pm = ProfitMarginChange.DecreaseProfitMargin;

		//WHEN commanding the margin to change
		final ProfitMargin pm_plus_pm = pm.changeProfitMargin(plus_pm);
		final ProfitMargin pm_main_pm = pm.changeProfitMargin(main_pm);
		final ProfitMargin pm_minus_pm = pm.changeProfitMargin(minus_pm);

		//THEN No Profit + Increase = Low
		assertEquals("'No Profit + Maintain = Low' is not true", pm_plus_pm, ProfitMargin.LowProfitMargin);

		//AND No Profit + Maintain = No Profit
		assertEquals("'No Profit + Maintain = No Profit' is not true", pm_main_pm, ProfitMargin.NoProfitMargin);

		//AND No Profit + Decrease = Negative
		assertEquals("'No Profit + Decrease = Negative' is not true", pm_minus_pm, ProfitMargin.NegativeProfitMargin);
	}

	@Test
	public void testNegativeProfitMarginChangeWorks() throws InvalidProfitMarginException{
		//GIVEN a profit Margin
		final ProfitMargin pm = ProfitMargin.NegativeProfitMargin;

		//AND all change commands
		final ProfitMarginChange plus_pm = ProfitMarginChange.IncreaseProfitMargin;
		final ProfitMarginChange main_pm = ProfitMarginChange.MaintainProfitMargin;
		final ProfitMarginChange minus_pm = ProfitMarginChange.DecreaseProfitMargin;

		//WHEN commanding the margin to change
		final ProfitMargin pm_plus_pm = pm.changeProfitMargin(plus_pm);
		final ProfitMargin pm_main_pm = pm.changeProfitMargin(main_pm);

		//THEN Negative + Increase = No Profit
		assertEquals("'Negative + Increase = No Profit' is not true", ProfitMargin.NoProfitMargin, pm_plus_pm);

		//AND Negative + Maintain = Negative
		assertEquals("'Negative + Maintain = Negative' is not true", ProfitMargin.NegativeProfitMargin, pm_main_pm);

		//AND Negative + Decrease = throw Exception
		try{
			pm.changeProfitMargin(minus_pm);
			fail("Negative cannot be reduced any further, an exception should have been thrown.");

		}catch (final InvalidProfitMarginException e){
			//We wanted an exception to be thrown
		}
	}

	@Test
	public void testHighProfitMarginProducesCorrectActions(){
		//GIVEN a profit margin
		final ProfitMargin pm = ProfitMargin.HighProfitMargin;

		//WHEN getting available actions
		final Set<ProfitMarginChange> actions = pm.getActions();

		//THEN There are only 2 actions
		assertEquals("High Profit Margin should have only 2 actions", 2, actions.size());

		//AND they are the correct actions
		assertTrue("High Profit Margin should be able to Maintain", actions.contains(ProfitMarginChange.MaintainProfitMargin));
		assertTrue("High Profit Margin should be able to Decrease", actions.contains(ProfitMarginChange.DecreaseProfitMargin));
	}

	@Test
	public void testLowProfitMarginProducesCorrectActions(){
		//GIVEN a profit margin
		final ProfitMargin pm = ProfitMargin.LowProfitMargin;

		//WHEN getting available actions
		final Set<ProfitMarginChange> actions = pm.getActions();

		//THEN There are only 2 actions
		assertEquals("Low Profit Margin should have only 3 actions", 3, actions.size());

		//AND they are the correct actions
		assertTrue("Low Profit Margin should be able to Increase", actions.contains(ProfitMarginChange.IncreaseProfitMargin));
		assertTrue("Low Profit Margin should be able to Maintain", actions.contains(ProfitMarginChange.MaintainProfitMargin));
		assertTrue("Low Profit Margin should be able to Decrease", actions.contains(ProfitMarginChange.DecreaseProfitMargin));
	}

	@Test
	public void testNoProfitMarginProducesCorrectActions(){
		//GIVEN a profit margin
		final ProfitMargin pm = ProfitMargin.NoProfitMargin;

		//WHEN getting available actions
		final Set<ProfitMarginChange> actions = pm.getActions();

		//THEN There are only 2 actions
		assertEquals("No Profit Margin should have only 3 actions", 3, actions.size());

		//AND they are the correct actions
		assertTrue("No Profit Margin should be able to Increase", actions.contains(ProfitMarginChange.IncreaseProfitMargin));
		assertTrue("No Profit Margin should be able to Maintain", actions.contains(ProfitMarginChange.MaintainProfitMargin));
		assertTrue("No Profit Margin should be able to Decrease", actions.contains(ProfitMarginChange.DecreaseProfitMargin));
	}

	@Test
	public void testNegativeProfitMarginProducesCorrectActions(){
		//GIVEN a profit margin
		final ProfitMargin pm = ProfitMargin.NegativeProfitMargin;

		//WHEN getting available actions
		final Set<ProfitMarginChange> actions = pm.getActions();

		//THEN There are only 2 actions
		assertEquals("Negative Profit Margin should have only 2 actions", 2, actions.size());

		//AND they are the correct actions
		assertTrue("Negative Profit Margin should be able to Increase", actions.contains(ProfitMarginChange.IncreaseProfitMargin));
		assertTrue("Negative Profit Margin should be able to Maintain", actions.contains(ProfitMarginChange.MaintainProfitMargin));
	}


}
