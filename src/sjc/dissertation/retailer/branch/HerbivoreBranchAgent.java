package sjc.dissertation.retailer.branch;

import java.util.List;

import sjc.dissertation.retailer.state.InvalidRetailerActionException;

public class HerbivoreBranchAgent implements BranchAgent{
	private final int id;
	private final Branch branch;

	protected HerbivoreBranchAgent(final int uniqueId, final Branch branch) {
		this.id = uniqueId;
		this.branch = branch;
	}

	@Override
	public void demandAction(final List<Branch> competitors) throws InvalidRetailerActionException {
		final HerbivoreRetailer herbivore = ((HerbivoreRetailer) this.branch.getRetailer());
		herbivore.determineAction(competitors);
	}

	@Override
	public double informOfCustomers(final int noOfCustomers) {
		return this.branch.informOfCustomers(noOfCustomers);
	}

	@Override
	public Branch getBranch() {
		return this.branch;
	}

	@Override
	public int getId(){
		return this.id;
	}
}
