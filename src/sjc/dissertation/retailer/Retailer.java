package sjc.dissertation.retailer;

import sjc.dissertation.retailer.state.quality.Quality;

public interface Retailer {
	public double informOfCustomers(final int customers);

	public Quality getQualityOfShop();

	public double getCostOfShopping();

	public String getName();
}
