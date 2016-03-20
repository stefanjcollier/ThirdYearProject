package sjc.dissertation.model;

import java.util.List;

import sjc.dissertation.model.logging.LoggerFactory;
import sjc.dissertation.model.logging.wrappers.Wrapper;
import sjc.dissertation.retailer.Algorithm;
import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.state.RetailerAction;
import sjc.dissertation.retailer.state.RetailerState;
import sjc.dissertation.util.Currency;

class StubAlgorithm extends Algorithm implements Wrapper{

	public StubAlgorithm(){}


	@Override
	public RetailerAction determineAction(final RetailerState state, final List<Retailer> competitors) {
		final RetailerAction action = state.getActions().iterator().next();

		LoggerFactory.getSingleton().getMasterLogger().print(this,
				String.format("chose action: %s",action.getSymbol()));
		return action;
	}

	@Override
	public void informOfReward(final double profit) {
		LoggerFactory.getSingleton().getMasterLogger().print(this,
				String.format("gained %s",Currency.prettyString(profit)));
	}


	@Override
	public String getWrapperId() {
		return String.format("STUB::Algorithm(%s)", (this.hasRetailer())?this.getAgent().getRetailer().getName():"-");
	}

	@Override
	public String toString(){
		return "STUB";
	}
}
