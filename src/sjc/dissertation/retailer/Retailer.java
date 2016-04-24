package sjc.dissertation.retailer;

import java.util.List;
import java.util.Map;

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

	public Branch createBranch(double x, double y, int settlment);

	public void informOfProfit(Branch branch, double profit);

	Map<Branch, Double> startNewWeek();

}
