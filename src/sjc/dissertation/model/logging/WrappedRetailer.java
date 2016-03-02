package sjc.dissertation.model.logging;

import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.state.quality.Quality;

public class WrappedRetailer implements Retailer{

	private Retailer me;

	protected WrappedRetailer(final Retailer retailer){
		this.me = retailer;
	}

	@Override
	public double informOfCustomers(final int customers) {
		final double result = this.me.informOfCustomers(customers);

		//Print the output

		return result;
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



}
