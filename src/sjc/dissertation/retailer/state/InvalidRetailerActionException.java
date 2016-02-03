package sjc.dissertation.retailer.state;

import sjc.dissertation.retailer.state.profit.InvalidProfitMarginException;
import sjc.dissertation.retailer.state.quality.InvalidQualityException;

public class InvalidRetailerActionException extends Exception {

	private static final long serialVersionUID = 1L;

	protected InvalidRetailerActionException(final RetailerAction ra, final InternalRetailerState rs, final InvalidQualityException iqe){
		super(String.format(
				"The state %s cannot change due to the quality change in action %s",
				rs.getSymbol(), ra.getSymbol()), iqe);
	}

	protected InvalidRetailerActionException(final RetailerAction ra, final InternalRetailerState rs, final InvalidProfitMarginException ipme){
		super(String.format(
				"The state %s cannot change due to the profit margin change in action %s",
				rs.getSymbol(), ra.getSymbol()), ipme);
	}


}
