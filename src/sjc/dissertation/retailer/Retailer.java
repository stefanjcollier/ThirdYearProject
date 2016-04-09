package sjc.dissertation.retailer;

import java.util.List;

import sjc.dissertation.retailer.state.RetailerState;
import sjc.dissertation.retailer.state.profit.ProfitMargin;
import sjc.dissertation.retailer.state.quality.Quality;

public interface Retailer {
	/**
	 * Informs the retailer the number of customers shopped with them this week.
	 *  And returns the amount of profit earned this week.
	 *
	 * @param customers -- The number of UK citizens that shopped with this retailer
	 * @return profit generated this week
	 */
	public double informOfCustomers(final int customers);

	public Quality getQualityOfShop();

	public double getCostOfShopping();

	public String getName();

	public RetailerState getState();

	public ProfitMargin getProfiMargin();

	public List<Branch> getBranches();

	public Branch makeBranch(final int x, final int y);
}
