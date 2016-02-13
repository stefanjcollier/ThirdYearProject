package sjc.dissertation.util;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

/**
 * This is a test to ensure that the {@link Combinatorics} toolbox behaves correctly.
 *
 * @author Stefan Collier
 *
 */
public class CombinatoricsUtilTest {

	@Test
	public void testMchooseNmethod_1() {
		//Small test
		final BigInteger mCn1 = Combinatorics.MchooseN(6, 3);
		assertEquals("6 Choose 3 should = 20", BigInteger.valueOf(20), mCn1);

		//Medium test
		final BigInteger mCn2 = Combinatorics.MchooseN(20, 6);
		assertEquals("20 Choose 6 should = 38760", BigInteger.valueOf(38760), mCn2);

		//Large test
		final BigInteger mCn3 = Combinatorics.MchooseN(200, 145);
		final String exp3 = "77183408114309579595976889045669798389726483201440";
		assertEquals("200 Choose 145 should = 7.72e+49", new BigInteger(exp3), mCn3);

	}

}
