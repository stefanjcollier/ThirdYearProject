package sjc.dissertation.retailer.agent;

import sjc.dissertation.retailer.state.RetailerAction;
import sjc.dissertation.retailer.state.RetailerState;

public interface Algorithm {

	public RetailerAction determineAction(RetailerState state);

	public void informOfReward(double profit);

	void giveRetailerAgent(RetailerAgent agent);

	boolean hasRetailer();
}
