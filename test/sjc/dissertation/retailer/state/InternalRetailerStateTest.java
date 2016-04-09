package sjc.dissertation.retailer.state;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import sjc.dissertation.retailer.state.profit.ProfitMargin;
import sjc.dissertation.retailer.state.profit.ProfitMarginChange;
import sjc.dissertation.retailer.state.quality.Quality;
import sjc.dissertation.retailer.state.quality.QualityChange;

/**
 * A set of tests to observe that the substate changes occur in the {@link InternalRetailerState} entities
 *
 * Follows the GIVEN, WHEN, THEN structure
 *
 * @author Stefan Collier
 *
 */
public class InternalRetailerStateTest {

	/**
	 * Test that the state instantiates with with Medium Quality and Low Profit
	 * Note: Does not consider all permutations, merely tries (+Q,+PM),(~Q,~PM),(-Q,-PM)
	 *
	 * Method Under Test: {@link InternalRetailerState#InternalRetailerState()}
	 */
	@Test
	public void testInstantiatesWithCorrectValues() {
		//GIVEN a state MQ,P+
		final RetailState state = new InternalRetailerState();


		//WHEN getting it's quality
		final Quality q = state.getQuality();

		//AND getting its profit margin
		final ProfitMargin pm = state.getProfitMargin();


		//THEN the quality should be medium
		assertEquals("Quality should be medium", Quality.MediumQuality, q);

		//AND the profit margin should be medium
		assertEquals("profit margin should be Low", ProfitMargin.LowProfitMargin, pm);

	}
	/**
	 * Check that the state of the retailer will change due to actions.
	 *
	 * Method Under Test: {@link InternalRetailerState#computeAction(RetailerAction)}
	 * @throws InvalidRetailerActionException -- The unexpected exception caused by an unknown enum
	 */
	@Test
	public void testThatRetailerActionsActuallyChangeState() throws InvalidRetailerActionException{
		//GIVEN 3 states: initial states: MQ,P+
		final InternalRetailerState state1 = new InternalRetailerState();
		final InternalRetailerState state2 = new InternalRetailerState();
		final InternalRetailerState state3 = new InternalRetailerState();

		//AND a 3 retailer actions
		final RetailerAction increaseBoth = new RetailerAction(QualityChange.IncreaseQuality, ProfitMarginChange.IncreaseProfitMargin);
		final RetailerAction maintainBoth = new RetailerAction(QualityChange.MaintainQuality, ProfitMarginChange.MaintainProfitMargin);
		final RetailerAction decreaseBoth = new RetailerAction(QualityChange.DecreaseQuality, ProfitMarginChange.DecreaseProfitMargin);


		//WHEN applying each action
		state1.computeAction(increaseBoth);
		state2.computeAction(maintainBoth);
		state3.computeAction(decreaseBoth);


		//THEN state1 should be HQ P++
		assertEquals("State1: Quality should be High", Quality.HighQuality, state1.getQuality());
		assertEquals("State1: profit margin should be High", ProfitMargin.HighProfitMargin, state1.getProfitMargin());

		//AND state2should be MQ P+
		assertEquals("State1: Quality should be Medium", Quality.MediumQuality, state2.getQuality());
		assertEquals("State1: profit margin should be Low", ProfitMargin.LowProfitMargin, state2.getProfitMargin());

		//THEN state1 should be LQ 0P
		assertEquals("State1: Quality should be Low", Quality.LowQuality, state3.getQuality());
		assertEquals("State1: profit margin should be Zero", ProfitMargin.NoProfitMargin, state3.getProfitMargin());
	}
}
