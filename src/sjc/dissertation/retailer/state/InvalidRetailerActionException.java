package sjc.dissertation.retailer.state;

import sjc.dissertation.retailer.state.profit.InvalidProfitMarginException;
import sjc.dissertation.retailer.state.quality.InvalidQualityException;

public class InvalidRetailerActionException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidRetailerActionException(final RetailerAction ra, final RetailerState rs, final InvalidQualityException iqe){
		super(String.format(
				"The state %s cannot change due to the quality change in action %s",
				rs.getSymbol(), ra.getSymbol()), iqe);
	}

	public InvalidRetailerActionException(final RetailerAction ra, final RetailerState rs, final InvalidProfitMarginException ipme){
		super(String.format(
				"The state %s cannot change due to the profit margin change in action %s",
				rs.getSymbol(), ra.getSymbol()), ipme);
	}


}
