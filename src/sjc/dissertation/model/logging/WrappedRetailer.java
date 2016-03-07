package sjc.dissertation.model.logging;

import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.state.quality.Quality;

public class WrappedRetailer implements Retailer, Wrapper{

	private final Retailer me;
	private final MasterLogger logger;

	protected WrappedRetailer(final MasterLogger logger, final Retailer retailer){
		this.me = retailer;
		this.logger = logger;

	}

	@Override
	public double informOfCustomers(final int customers) {
		final double profit = this.me.informOfCustomers(customers);

		//Print the output
		final String line = String.format("Consumers:%d\t\tProfit:%f",
				customers, profit);
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


}