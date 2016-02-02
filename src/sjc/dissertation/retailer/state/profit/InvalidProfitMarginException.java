package sjc.dissertation.retailer.state.profit;

public class InvalidProfitMarginException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidProfitMarginException(final ProfitMargin pm, final ProfitMarginChange pmc) {
		super(String.format("The profit margin %s cannot be told to %s", pm.toString(), pmc.toString()));
	}

}
