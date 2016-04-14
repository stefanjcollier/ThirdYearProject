package sjc.dissertation.retailer.branch;

import java.util.List;

import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.state.BranchState;
import sjc.dissertation.retailer.state.RetailerAction;

public abstract class Algorithm {
	private BranchAgent agent;
	private Retailer retailer;

	public abstract RetailerAction determineAction(BranchState state, List<Branch> competitors);

	public abstract void informOfReward(double profit);

	protected void giveBranchAgent(final BranchAgent agent){
		if(!hasBranchAgent() || !hasRetailerAgent()){
			this.agent = agent;
		}
	}

	protected void giveRetailer(final Retailer retailer){
		if(!hasBranchAgent() || !hasRetailerAgent()){
			this.retailer = retailer;
		}
	}


	public BranchAgent getBranchAgent(){
		return this.agent;
	}

	public Retailer getRetailerAgent(){
		return this.retailer;
	}



	public boolean hasBranchAgent(){
		return this.agent != null;
	}
	public boolean hasRetailerAgent(){
		return this.retailer != null;
	}


	/*
	 * @see java.lang.Object#toString()
	 * This method will return what time of algorithm it is.
	 * i.e. Greedy, Stub, Maintainer
	 */
	@Override
	public abstract String toString();
}
