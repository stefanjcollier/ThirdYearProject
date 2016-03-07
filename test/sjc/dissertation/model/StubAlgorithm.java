package sjc.dissertation.model;

import java.util.List;

import sjc.dissertation.retailer.Algorithm;
import sjc.dissertation.retailer.RetailerImpl;
import sjc.dissertation.retailer.state.RetailerAction;
import sjc.dissertation.retailer.state.RetailerState;
import sjc.dissertation.util.Currency;

class StubAlgorithm extends Algorithm{

	public StubAlgorithm(){}


	@Override
	public RetailerAction determineAction(final RetailerState state, final List<RetailerImpl> competitors) {
		final RetailerAction action = state.getActions().iterator().next();
		System.out.println("STUB: "+this.getAgent()+" chose action: "+action);
		return action;
	}

	@Override
	public void informOfReward(final double profit) {
		System.out.println("STUB: "+this.getAgent()+" gained "+Currency.prettyString(profit));

	}

}