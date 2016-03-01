package sjc.dissertation.retailer;

import java.util.List;

import sjc.dissertation.retailer.state.InvalidRetailerActionException;
import sjc.dissertation.retailer.state.RetailerAction;

//In control of controlling passing messages between
//JAVADOC ReAg
public class RetailerAgent {
	private final int id;
	private final Retailer retailer;
	private final Algorithm policy;


	protected RetailerAgent(final int uniqueId, final Retailer retailer, final Algorithm controller){
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

	//Consider: Giving them the scope of other's states?
	//JAVADOC RetailerAgent#demmandAction
	public void demandAction(final List<Retailer> competitors) throws InvalidRetailerActionException{
		final RetailerAction chosenAction = this.policy.determineAction(this.retailer.getState(), competitors);
		this.retailer.getState().computeAction(chosenAction);
	}

	public double informOfCustomers(final int noOfCustomers){
		final double profit = this.retailer.informOfCustomers(noOfCustomers);
		this.policy.informOfReward(profit);
		return profit;
	}

	public Retailer getRetailer(){
		return this.retailer;
	}

	@Override
	public String toString(){
		return String.format("Agent[%d]: %s", this.id, this.retailer.toString());
	}
}
