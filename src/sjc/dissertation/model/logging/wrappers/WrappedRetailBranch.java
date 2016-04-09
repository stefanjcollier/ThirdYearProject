package sjc.dissertation.model.logging.wrappers;

import sjc.dissertation.model.logging.MasterLogger;
import sjc.dissertation.retailer.Branch;
import sjc.dissertation.retailer.state.RetailState;
import sjc.dissertation.retailer.state.profit.ProfitMargin;
import sjc.dissertation.retailer.state.quality.Quality;
import sjc.dissertation.util.Currency;

public class WrappedRetailBranch implements Branch, Wrapper{

	private final Branch me;
	private final MasterLogger logger;

	public WrappedRetailBranch(final MasterLogger logger, final Branch branch){
		this.me = branch;
		this.logger = logger;

		//Acknowledge instantiation
		this.logger.trace(this, "Instantiated");

	}

	@Override
	public double informOfCustomers(final int customers) {
		final double profit = this.me.informOfCustomers(customers);

		//Print the output
		final String line = String.format("   Consumers:%d\tProfit:%s", customers, Currency.prettyString(profit));
		this.logger.print(this, line);

		return profit;
	}

	@Override
	public Quality getQualityOfShop() {
		return this.me.getQualityOfShop();
	}

	@Override
	public double getCostOfShopping() {
		return this.me.getCostOfShopping();
	}

	@Override
	public String getName() {
		return this.me.getName();
	}

	@Override
	public String toString(){
		return this.me.toString();
	}

	@Override
	public String getWrapperId(){
		return String.format("Branch(%s)", this.me.getName());
	}

	@Override
	public RetailState getState() {
		return this.me.getState();
	}

	@Override
	public ProfitMargin getProfiMargin() {
		return this.me.getProfiMargin();
	}

}
