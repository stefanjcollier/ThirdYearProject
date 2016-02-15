package sjc.dissertation.retailer.agent;

import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.state.InvalidRetailerActionException;
import sjc.dissertation.retailer.state.RetailerAction;

//In control of controlling passing messages between
//TODO Javadoc ReAg
public class RetailerAgent {
	private final int id;
	private final Retailer retailer;
	private final Algorithm policy;


	protected RetailerAgent(final int uniqueId, final Retailer retailer, final Algorithm controller){
		this.id = uniqueId;
		this.retailer = retailer;
		this.policy = controller;
	}

	/**
	 * Get the unique numerical id for this agent.
	 *
	 * @return The id of the agent
	 */
	public int getId(){
		return this.id;
	}

	//TODO Consider: Giving them the scope of other's states?
	public void demandAction() throws InvalidRetailerActionException{
		final RetailerAction chosenAction = this.policy.determineAction(this.retailer.getState());;
		this.retailer.getState().computeAction(chosenAction);
	}

	public double informOfCustomers(final int noOfCustomers){
		final double profit = this.retailer.informOfCustomers(noOfCustomers);
		this.policy.informOfReward(profit);
		return profit;
	}
}
