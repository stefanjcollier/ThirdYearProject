package sjc.dissertation.util;

import java.text.NumberFormat;

/**
 * A toolbox class used to format currencies nicely
 *
 * @author Stefan Collier
 *
 */
public class Currency {

	/** Toolboxes should not be instantiated */
	private Currency(){}

	/**
	 * Takes a double number and returns a string in the correct currency format.
	 * Adds commas between each 10^3 pounds and reduces the pennies to 2dp
	 *
	 *	e.g. prettyString(123456.7890000) => £123,456.79
	 *
	 * @param money -- The unformatted currency
	 * @return A currency in string formatted correctly
	 */
	public static String prettyString(final Double money){
		final NumberFormat formatter = NumberFormat.getCurrencyInstance();
		return formatter.format(money);
	}

}
