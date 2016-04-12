package sjc.dissertation.retailer.branch;

import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.state.BranchState;
import sjc.dissertation.retailer.state.profit.ProfitMargin;
import sjc.dissertation.retailer.state.quality.Quality;

public interface Branch {
	/**
	 * Informs the branch the number of customers shopped with them this week.
	 *  And returns the amount of profit earned this week.
	 *
	 * @param customers -- The number of UK citizens that shopped with this retailer
	 * @return profit generated this week
	 */
	public double informOfCustomers(final int customers);

	public Quality getQualityOfShop();

	public double getCostOfShopping();

	public String getBranchName();

	public BranchState getState();

	public ProfitMargin getProfiMargin();

	public Retailer getRetailer();

}
