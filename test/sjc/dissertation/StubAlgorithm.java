package sjc.dissertation;

import sjc.dissertation.retailer.Algorithm;
import sjc.dissertation.retailer.RetailerAgent;
import sjc.dissertation.retailer.state.RetailerAction;
import sjc.dissertation.retailer.state.RetailerState;
import sjc.dissertation.util.Currency;

class StubAlgorithm implements Algorithm{

	public StubAlgorithm(){}

	RetailerAgent agent;

	@Override
	public RetailerAction determineAction(final RetailerState state) {
		final RetailerAction action = state.getActions().iterator().next();
		System.out.println("STUB: "+this.agent+" chose action: "+action);
		return action;
	}

	@Override
	public void informOfReward(final double profit) {
		System.out.println("STUB: "+this.agent+" gained "+Currency.prettyString(profit));

	}

	@Override
	public void giveRetailerAgent(final RetailerAgent agent) {
		if(!hasRetailer()){
			this.agent = agent;
		}
	}

	@Override
	public boolean hasRetailer() {
		return this.agent!=null;
	}

}
