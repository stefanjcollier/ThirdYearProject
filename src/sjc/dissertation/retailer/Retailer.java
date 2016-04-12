package sjc.dissertation.retailer;

import java.util.List;

import sjc.dissertation.retailer.branch.Branch;

/**
 * A retailer is the chain of branches
 *
 * @author Stefan Collier
 *
 */
public interface Retailer {

	public String getName();

	public List<Branch> getBranches();

	public Branch createBranch(double x, double y);

}
