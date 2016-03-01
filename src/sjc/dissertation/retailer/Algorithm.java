package sjc.dissertation.retailer;

import java.util.List;

import sjc.dissertation.retailer.state.RetailerAction;
import sjc.dissertation.retailer.state.RetailerState;

public abstract class Algorithm {
	private RetailerAgent agent;

	public abstract RetailerAction determineAction(RetailerState state, List<Retailer> competitors);

	public abstract void informOfReward(double profit);

	protected void giveRetailerAgent(final RetailerAgent agent){
		if(!hasRetailer()){
			this.agent = agent;
		}
	}

	protected RetailerAgent getAgent(){
		return this.agent;
	}

	protected boolean hasRetailer(){
		return this.agent != null;
	}
}
