package sjc.dissertation.retailer.branch;

import java.util.List;

import sjc.dissertation.retailer.state.InvalidRetailerActionException;

public interface BranchAgent {

	public void demandAction(final List<Branch> competitors) throws InvalidRetailerActionException;

	public double informOfCustomers(final int noOfCustomers);

	public Branch getBranch();

	public int getId();

}
