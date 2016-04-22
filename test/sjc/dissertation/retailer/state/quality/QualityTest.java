package sjc.dissertation.retailer.state.quality;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import org.junit.Test;

public class QualityTest {

	final double HIGH_QUALITY_COST = 100;
	final double MEDIUM_QUALITY_COST = 66;
	final double LOW_QUALITY_COST = 44.44;


	/**
	 * A test to ensure that the equals works.
	 * High = High, Low = Low, High != Low, etc
	 */
	@Test
	public void testEqualsMethodWorks(){
		//GIVEN each Quality instance
		final Quality hq = Quality.HighQuality;
		final Quality mq = Quality.MediumQuality;
		final Quality lq = Quality.LowQuality;

		//THEN They are equal to themselves
		assertEquals("High should equal itself", hq, hq);
		assertEquals("Medium should equal itself", mq, mq);
		assertEquals("Low should equal itself", lq, lq);

		//AND High != Medium != Low != High
		assertNotEquals("High should not equal Medium", hq, mq);
		assertNotEquals("Medium should not equal Low", mq, lq);
		assertNotEquals("Low should not equal High", lq, hq);


		//GIVEN one more of each quality instance
		final Quality hq2 = Quality.HighQuality;
		final Quality mq2 = Quality.MediumQuality;
		final Quality lq2 = Quality.LowQuality;

		//THEN the new instances should equal their origonal counterparts
		assertEquals("The new High instance should equal the first High instance", hq, hq2);
		assertEquals("The new Medium instance should equal the first Medium instance", mq, mq2);
		assertEquals("The new Low instance should equal the first Low instance", lq, lq2);
	}

	/**
	 * A test to ensure the cost of each level is correct.
	 *
	 * Methods Under Test: Constructor, getCost()
	 */
	@Test
	public void testThatTheValuesAreCorrect(){
		//GIVEN each Quality instance
		final Quality hq = Quality.HighQuality;
		final Quality mq = Quality.MediumQuality;
		final Quality lq = Quality.LowQuality;

		//WHEN getting their costs
		final double hC = hq.getCost();
		final double mC = mq.getCost();
		final double lC = lq.getCost();

		//THEN their costs are the intended value
		assertEquals("High cost not correct", this.HIGH_QUALITY_COST, hC, 0);
		assertEquals("Medium cost not correct", this.MEDIUM_QUALITY_COST, mC, 0);
		assertEquals("Low cost not correct", this.LOW_QUALITY_COST, lC, 0);
	}


	/**
	 * A test to see that HighQuality behaves correctly under all quality change commands.
	 *
	 * Methods Under Test: Constructor, changeQuality()
	 * @throws InvalidQualityException -- The unexpected error caused from changing the quality level
	 */
	@Test
	public void testHighQualityChangeCommands() throws InvalidQualityException {
		//GIVEN a HighQuality instance
		final Quality q = Quality.HighQuality;

		//AND all quality changes
		final QualityChange plusQ= QualityChange.IncreaseQuality;
		final QualityChange mainQ= QualityChange.MaintainQuality;
		final QualityChange minusQ= QualityChange.DecreaseQuality;

		//WHEN changing the quality using each change level
		final Quality q_mainQ = q.changeQuality(mainQ);
		final Quality q_minusQ = q.changeQuality(minusQ);

		//THEN High + Increase = throws an exception
		try {
			q.changeQuality(plusQ);
			fail("Commanding HighQuality to IncreaseQuality should throw an Exception");

		} catch (final InvalidQualityException e) {
			//We expect an exception to be thrown
		}

		//AND High + Maintain = High
		assertEquals("'High + Maintain = High' is not true", Quality.HighQuality, q_mainQ);

		//AND High + Decrease = Medium
		assertEquals("'High + Decrease = Medium' is not true", Quality.MediumQuality, q_minusQ);
	}

	/**
	 * A test to see that MediumQuality behaves correctly under all quality change commands.
	 *
	 * Methods Under Test: Constructor, changeQuality()
	 * @throws InvalidQualityException -- The unexpected error caused from changing the quality level
	 */
	@Test
	public void testMediumQualityChangeCommands() throws InvalidQualityException {
		//GIVEN a HighQuality instance
		final Quality q = Quality.MediumQuality;

		//AND all quality changes
		final QualityChange plusQ= QualityChange.IncreaseQuality;
		final QualityChange mainQ= QualityChange.MaintainQuality;
		final QualityChange minusQ= QualityChange.DecreaseQuality;

		//WHEN changing the quality using each change level
		final Quality q_plusQ = q.changeQuality(plusQ);
		final Quality q_mainQ = q.changeQuality(mainQ);
		final Quality q_minusQ = q.changeQuality(minusQ);

		//THEN Medium + Increase = High
		assertEquals("'Medium + Increase = High' is not true", Quality.HighQuality, q_plusQ);

		//AND Medium + Maintain = Medium
		assertEquals("'Medium + Maintain = Medium' is not true", Quality.MediumQuality, q_mainQ);

		//AND Medium + Decrease = Low
		assertEquals("'Medium + Decrease = Low' is not true", Quality.LowQuality, q_minusQ);
	}

	/**
	 * A test to see that LowQuality behaves correctly under all quality change commands.
	 *
	 * Methods Under Test: Constructor, changeQuality()
	 * @throws InvalidQualityException -- The unexpected error caused from changing the quality level
	 */
	@Test
	public void testLowQualityChangeCommands() throws InvalidQualityException {
		//GIVEN a HighQuality instance
		final Quality q = Quality.LowQuality;

		//AND all quality changes
		final QualityChange plusQ= QualityChange.IncreaseQuality;
		final QualityChange mainQ= QualityChange.MaintainQuality;
		final QualityChange minusQ= QualityChange.DecreaseQuality;

		//WHEN changing the quality using each change level
		final Quality q_plusQ = q.changeQuality(plusQ);
		final Quality q_mainQ = q.changeQuality(mainQ);

		//THEN Low + Increase = Medium
		assertEquals("'Low + Increase = Medium' is not true", Quality.MediumQuality, q_plusQ);

		//AND Low + Maintain = Low
		assertEquals("'Low + Maintain = Low' is not true", Quality.LowQuality, q_mainQ);

		//AND Low + Decrease = throw exception
		try {
			q.changeQuality(minusQ);
			fail("Commanding HighQuality to IncreaseQuality should throw an Exception");

		} catch (final InvalidQualityException e) {
			//We expect an exception to be thrown
		}
	}

	@Test
	public void testHighQualityProducesCorrectActions(){
		//GIVEN a Quality level
		final Quality q = Quality.HighQuality;

		//WHEN getting all actions
		final Set<QualityChange> actions = q.getActions();

		//THEN there are only 2 actions
		assertEquals("High should only have 2 actions", 2, actions.size());

		//AND those actions are the correct actions
		assertTrue("High Quality should be able to Maintain.", actions.contains(QualityChange.MaintainQuality));
		assertTrue("High Quality should be able to Decrease.", actions.contains(QualityChange.DecreaseQuality));
	}

	@Test
	public void testMediumQualityProducesCorrectActions(){
		//GIVEN a Quality level
		final Quality q = Quality.MediumQuality;

		//WHEN getting all actions
		final Set<QualityChange> actions = q.getActions();

		//THEN there are only 3 actions
		assertEquals("Medium should only have 3 actions", 3, actions.size());

		//AND those actions are the correct actions
		assertTrue("Medium Quality should be able to Increase.", actions.contains(QualityChange.IncreaseQuality));
		assertTrue("Medium Quality should be able to Maintain.", actions.contains(QualityChange.MaintainQuality));
		assertTrue("Medium Quality should be able to Decrease.", actions.contains(QualityChange.DecreaseQuality));
	}

	@Test
	public void testLowQualityProducesCorrectActions(){
		//GIVEN a Quality level
		final Quality q = Quality.LowQuality;

		//WHEN getting all actions
		final Set<QualityChange> actions = q.getActions();

		//THEN there are only 2 actions
		assertEquals("Medium should only have 2 actions", 2, actions.size());

		//AND those actions are the correct actions
		assertTrue("Low Quality should be able to Increase.", actions.contains(QualityChange.IncreaseQuality));
		assertTrue("Low Quality should be able to Maintain.", actions.contains(QualityChange.MaintainQuality));
	}

}
