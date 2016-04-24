package sjc.dissertation.retailer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sjc.dissertation.retailer.branch.Branch;

public class CarnivoreRetailer implements Retailer{
	protected final String name;
	protected final List<Branch> myBranches;
	protected Map<Branch,Double> profitPerWeek;

	public CarnivoreRetailer(final String name) {
		this.name = name;
		this.myBranches = new ArrayList<Branch>(3);
		this.profitPerWeek = new HashMap<Branch, Double>(3);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public List<Branch> getBranches() {
		return this.myBranches;
	}

	@Override
	public Branch createBranch(final double x, final double y, final int settlment) {
		final Branch newBranch = new CarnivoreBranchImpl(this, this.myBranches.size(), x, y, settlment);
		this.myBranches.add(newBranch);
		return newBranch;
	}

	@Override
	public void informOfProfit(final Branch branch, final double profit) {
		this.profitPerWeek.put(branch, profit);
	}

	/**
	 * Resets the internal workings so that it can determine the given by each of its branhces
	 * @return The results for last week
	 */
	@Override
	public Map<Branch, Double> startNewWeek(){
		final Map<Branch,Double> lastWeek = this.profitPerWeek;
		this.profitPerWeek = new HashMap<Branch, Double>(3);
		return lastWeek;
	}
}
