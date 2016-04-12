package sjc.dissertation.retailer.branch;

import java.util.List;

import sjc.dissertation.retailer.state.InternalRetailerState;
import sjc.dissertation.retailer.state.InvalidRetailerActionException;
import sjc.dissertation.retailer.state.RetailerAction;

/**
 * This is the agent that passes the control messages between the retailer and the algorithm
 * It is is involved in the carnivore agents.
 *
 * @author Stefan Collier
 *
 */
public class CarnivoreBranchAgent implements BranchAgent {
	private final int id;
	private final Branch branch;
	private final Algorithm policy;


	protected CarnivoreBranchAgent(final int uniqueId, final Branch branch, final Algorithm controller){
		this.id = uniqueId;
		this.branch = branch;
		this.policy = controller;
		this.policy.giveRetailerAgent(this);
	}

	/**
	 * Get the unique numerical id for this agent.
	 *
	 * @return The id of the agent
	 */
	@Override
	public int getId(){
		return this.id;
	}

	//JAVADOC BranchAgent#demmandAction
	@Override
	public void demandAction(final List<Branch> competitors) throws InvalidRetailerActionException{
		final RetailerAction chosenAction = this.policy.determineAction(this.branch.getState(), competitors);
		((InternalRetailerState) this.branch.getState()).computeAction(chosenAction);
	}

	@Override
	public double informOfCustomers(final int noOfCustomers){
		final double profit = this.branch.informOfCustomers(noOfCustomers);
		this.policy.informOfReward(profit);
		return profit;
	}

	@Override
	public Branch getBranch(){
		return this.branch;
	}

	@Override
	public String toString(){
		return String.format("Agent[%d]: %s", this.id, this.branch.toString());
	}
}
