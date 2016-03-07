package sjc.dissertation.util;
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
		final String raw = money.toString();
		final String pennies = getMePennies(raw);
		final String pounds = getMePounds(raw);
		return pounds+"."+pennies;
	}

	private static String getMePounds(final String raw) {
		final String raw_pounds = raw.split("[.]")[0];
		String pounds = "";
		if(raw_pounds.length()>3){
			for(int i = raw_pounds.length()-1; i > -1; i--){
				pounds = raw_pounds.charAt(i) + pounds;

				//If this is the third number AND if we have numbers coming after this
				// Second clause prevents: £,900.00
				if(((raw_pounds.length()-i) % 3) == 0 && (i-1 > -1)){
					pounds = ","+pounds;
				}
			}
		}else{
			pounds = raw_pounds;
		}
		return "£"+pounds;
	}

	private static String getMePennies(final String raw) {
		final String raw_pennies = raw.split("[.]")[1];

		switch(raw_pennies.length()){
		case 1: {
			return raw_pennies+"0";
		}

		case 2: {
			return raw_pennies;
		}
		default: {
			final Long twoSigFig = Math.round(new Double(raw_pennies.substring(0,3))/10);
			return twoSigFig.toString();
		}
		}
	}


}
