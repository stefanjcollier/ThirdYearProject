package sjc.dissertation.retailer;

import java.util.List;

import sjc.dissertation.retailer.state.InternalRetailerState;
import sjc.dissertation.retailer.state.InvalidRetailerActionException;
import sjc.dissertation.retailer.state.RetailerAction;

//In control of controlling passing messages between
//JAVADOC ReAg
public class RetailerAgent {
	private final int id;
	private final Branch retailer;
	private final Algorithm policy;


	protected RetailerAgent(final int uniqueId, final Branch retailer, final Algorithm controller){
		this.id = uniqueId;
		this.retailer = retailer;
		this.policy = controller;
		this.policy.giveRetailerAgent(this);
	}

	/**
	 * Get the unique numerical id for this agent.
	 *
	 * @return The id of the agent
	 */
	public int getId(){
		return this.id;
	}

	//JAVADOC RetailerAgent#demmandAction
	public void demandAction(final List<Branch> competitors) throws InvalidRetailerActionException{
		final RetailerAction chosenAction = this.policy.determineAction(this.retailer.getState(), competitors);
		((InternalRetailerState) this.retailer.getState()).computeAction(chosenAction);
	}

	public double informOfCustomers(final int noOfCustomers){
		final double profit = this.retailer.informOfCustomers(noOfCustomers);
		this.policy.informOfReward(profit);
		return profit;
	}

	public Branch getRetailer(){
		return this.retailer;
	}

	@Override
	public String toString(){
		return String.format("Agent[%d]: %s", this.id, this.retailer.toString());
	}
}
