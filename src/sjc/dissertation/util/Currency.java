package sjc.dissertation.util;

public class Currency {

	private Currency(){}

	public static String prettyString(final Double money){
		final String raw = money.toString();
		final String pennies = getMePennies(raw);
		final String pounds = getMePounds(raw);
		return pounds+"."+pennies;
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

}
