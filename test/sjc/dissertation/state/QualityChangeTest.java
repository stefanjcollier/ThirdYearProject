package sjc.dissertation.state;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * A JUnit test for the methods of {@link QualityChange}.
 *
 * @author Stefan Collier
 *
 */
public class QualityChangeTest {

	/**
	 * A test to see that the IncreaseQuality enum has the correct code.
	 *
	 * Methods Under Test: Constructor, getSymbol()
	 */
	@Test
	public void testIncreaseValue() {
		final QualityChange qc = QualityChange.IncreaseQuality;
		assertEquals("The 'IncreaseQuality' should have code: <++Q>",
				"<++Q>", qc.getSymbol());
	}

	/**
	 * A test to see that the MaintainQuality enum has the correct code.
	 *
	 * Methods Under Test: Constructor, getSymbol()
	 */
	@Test
	public void testMaintainValue() {
		final QualityChange qc = QualityChange.MaintainQuality;
		assertEquals("The 'MaintainQuality' should have code: <~~Q>",
				"<~~Q>", qc.getSymbol());
	}

	/**
	 * A test to see that the DecreaseQuality enum has the correct code.
	 *
	 * Methods Under Test: Constructor, getSymbol()
	 */
	@Test
	public void testDecreaseValue() {
		final QualityChange qc = QualityChange.DecreaseQuality;
		assertEquals("The 'DecreaseQuality' should have code: <--Q>",
				"<--Q>", qc.getSymbol());
	}

}
