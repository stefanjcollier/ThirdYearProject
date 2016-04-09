package sjc.dissertation.model.logging.wrappers;

import sjc.dissertation.model.logging.MasterLogger;
import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.state.RetailerState;
import sjc.dissertation.retailer.state.profit.ProfitMargin;
import sjc.dissertation.retailer.state.quality.Quality;
import sjc.dissertation.util.Currency;

public class WrappedRetailer implements Retailer, Wrapper{

	private final Retailer me;
	private final MasterLogger logger;

	public WrappedRetailer(final MasterLogger logger, final Retailer retailer){
		this.me = retailer;
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
		return String.format("Retailer(%s)", this.me.getName());
	}

	@Override
	public RetailerState getState() {
		return this.me.getState();
	}

	@Override
	public ProfitMargin getProfiMargin() {
		return this.me.getProfiMargin();
	}

}
