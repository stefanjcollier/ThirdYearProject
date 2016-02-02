package sjc.dissertation.retailer.state.quality;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class QualityTest {

	final double HIGH_QUALITY_COST = 3;
	final double MEDIUM_QUALITY_COST = 2;
	final double LOW_QUALITY_COST = 1;


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
		fail("Not Implemented");
	}

	/**
	 * A test to see that LowQuality behaves correctly under all quality change commands.
	 *
	 * Methods Under Test: Constructor, changeQuality()
	 * @throws InvalidQualityException -- The unexpected error caused from changing the quality level
	 */
	@Test
	public void testLowQualityChangeCommands() throws InvalidQualityException {
		fail("Not Implemented");
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

}
