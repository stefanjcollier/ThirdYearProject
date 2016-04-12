package sjc.dissertation.retailer.herbivore;

import sjc.dissertation.model.logging.LoggerFactory;
import sjc.dissertation.retailer.CarnivoreBranchImpl;
import sjc.dissertation.retailer.branch.Branch;
import sjc.dissertation.retailer.state.InternalRetailerState;

public class HerbivoreBranchImpl extends CarnivoreBranchImpl implements Branch {

	protected HerbivoreBranchImpl(final HerbivoreRetailer owner, final int id, final double x, final double y){
		super(owner, id, x, y);
	}

	@Override
	public double informOfCustomers(final int customers) {
		// Profit = Sale Price - Cost
		// Profit = (1 + Margin)*Cost - Cost
		// Profit = Margin * Cost
		final double cost = getState().getQuality().getCost();
		final double margin = getState().getProfitMargin().getProfitMargin();
		final double profit = customers * (margin*cost);

		//Inform owner retailers and state
		getRetailer().informOfProfit(this, profit);

		//log
		LoggerFactory.getSingleton().getMasterLogger().trace(String.format(
				"RetailerImpl(%s)::Profit:: Customers*(margin * cost) = %d * (%f * %f) = %f",
				this.getBranchName(),customers, margin, cost, profit));

		return profit;

	}

	@Override
	public InternalRetailerState getState() {
		return ((HerbivoreRetailer) getRetailer()).getState();
	}

}