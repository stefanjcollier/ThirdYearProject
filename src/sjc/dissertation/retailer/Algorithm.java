package sjc.dissertation.retailer;

import java.util.List;

import sjc.dissertation.retailer.state.RetailState;
import sjc.dissertation.retailer.state.RetailerAction;

public abstract class Algorithm {
	private RetailerAgent agent;

	public abstract RetailerAction determineAction(RetailState state, List<RetailBranch> competitors);

	public abstract void informOfReward(double profit);

	protected void giveRetailerAgent(final RetailerAgent agent){
		if(!hasRetailer()){
			this.agent = agent;
		}
	}

	public RetailerAgent getAgent(){
		return this.agent;
	}

	public boolean hasRetailer(){
		return this.agent != null;
	}

	/*
	 * @see java.lang.Object#toString()
	 * This method will return what time of algorithm it is.
	 * i.e. Greedy, Stub, Maintainer
	 */
	@Override
	public abstract String toString();
}
