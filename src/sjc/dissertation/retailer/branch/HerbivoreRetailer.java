package sjc.dissertation.retailer.branch;

import java.util.List;
import java.util.Map;

import sjc.dissertation.retailer.CarnivoreRetailer;
import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.learn.GreedyCarnivoreAlgorithm;
import sjc.dissertation.retailer.state.InternalRetailerState;
import sjc.dissertation.retailer.state.InvalidRetailerActionException;
import sjc.dissertation.retailer.state.RetailerAction;

public class HerbivoreRetailer extends CarnivoreRetailer implements Retailer{
	private final Algorithm policy;
	private final InternalRetailerState state;
	private boolean decidedOnAction;

	protected HerbivoreRetailer(final String name, final GreedyCarnivoreAlgorithm policy)  {
		super(name);
		this.policy = policy;
		this.state = new InternalRetailerState();
		this.decidedOnAction = false;
	}

	@Override
	public Branch createBranch(final double x, final double y) {
		final Branch newBranch = new HerbivoreBranchImpl(this, this.myBranches.size(), x, y);
		this.myBranches.add(newBranch);
		return newBranch;
	}

	/**
	 * Resets the internal workings so that it can determine the given by each of its branhces
	 * @return The results for last week
	 */
	@Override
	public Map<Branch, Double> startNewWeek(){
		final Map<Branch,Double> earnings = super.startNewWeek();

		this.updateAlgorithm(earnings);

		//New week = new action
		this.decidedOnAction = false;

		return earnings;
	}

	private void updateAlgorithm(final Map<Branch,Double> earnings){
		final double totalProfit = earnings.values().stream().mapToDouble(Double::doubleValue).sum();
		this.policy.informOfReward(totalProfit);
	}

	/**
	 * Decides on the new state for all the branches
	 * to share for the next week. The first branch to ask will cause it to decide.
	 * The next branches asking will be ignored as it has decided.
	 *
	 * @param competitors
	 */
	protected void determineAction(final List<Branch> competitors){
		if(this.decidedOnAction){
			return;
		}

		//Otherwise decide on an action for the week
		final RetailerAction chosenAction = this.policy.determineAction(this.state, competitors);
		try {
			this.state.computeAction(chosenAction);
		} catch (final InvalidRetailerActionException e) {
			throw new RuntimeException("The Agent in the Herbivore Retailer ("+getName()+") is trying to make the move "+chosenAction.getSymbol()+" which cannot be made legally", e);
		}

		this.decidedOnAction = true;
	}

	protected InternalRetailerState getState(){
		return this.state;
	}




}
