package sjc.dissertation.util;

import java.math.BigInteger;

/**
 * A toolbox class used to determine the binomial value of M Choose N of
 *
 * @author Stefan Collier
 *
 */
public class Combinatorics {


	/** Toolboxes should not be instantiated */
	private Combinatorics(){}

	/**
	 * The result of C^{m}_{n} or M choose N of the combinatorics method.
	 *
	 * @param m -- The total number of options
	 * @param n -- The number of chosen powers
	 *
	 * @return C^{m}_{n}
	 *
	 * @author PolygeneLubricants {@link http://stackoverflow.com/users/276101/polygenelubricants}
	 */
	public static BigInteger MchooseN(final int M, final int N) {
		BigInteger ret = BigInteger.ONE;
		for (int k = 0; k < N; k++) {
			ret = ret.multiply(BigInteger.valueOf(M-k)).divide(BigInteger.valueOf(k+1));
		}
		return ret;
	}

}
