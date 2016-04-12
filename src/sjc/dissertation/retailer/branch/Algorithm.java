package sjc.dissertation.retailer.branch;

import java.util.List;

import sjc.dissertation.retailer.state.BranchState;
import sjc.dissertation.retailer.state.RetailerAction;

public abstract class Algorithm {
	private BranchAgent agent;

	public abstract RetailerAction determineAction(BranchState state, List<Branch> competitors);

	public abstract void informOfReward(double profit);

	protected void giveRetailerAgent(final BranchAgent agent){
		if(!hasRetailer()){
			this.agent = agent;
		}
	}

	public BranchAgent getAgent(){
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
