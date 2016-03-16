package sjc.dissertation.retailer;

import sjc.dissertation.retailer.state.InternalRetailerState;
import sjc.dissertation.retailer.state.quality.Quality;

/**
 * This is the Retailer, it is what a customer can see if they walked into a shop or
 * found retailer online.
 *
 * @author Stefan Collier
 *
 */
public class RetailerImpl implements Retailer{
	private final InternalRetailerState state;
	private final String name;

	public RetailerImpl(final String name){
		this.state = new InternalRetailerState();
		this.name = name;
	}

	@Override
	public String getName(){
		return this.name;
	}

	@Override
	public  InternalRetailerState getState(){
		return this.state;
	}

	@Override
	public Quality getQualityOfShop(){
		return this.state.getQuality();
	}

	/**
	 * Under the abstraction that retailers offer a single 'weekly shop',
	 * returns the price of that 'shop'.
	 *
	 * @return The cost of shopping at given retailer
	 */
	@Override
	public double getCostOfShopping(){
		final double rawCost = this.state.getQuality().getCost();
		final double profitMultiplier = 1 + this.state.getProfitMargin().getProfitMargin();
		return rawCost * profitMultiplier;
	}


	/**
	 * Informs the retailer how many consumers it had this week based on the actions it made.
	 *
	 * @param customers --  the number of people who shopped with this retailer.
	 * @return profit based on customers
	 */
	@Override
	public double informOfCustomers(final int customers){
		System.out.println("Retailer: "+customers);
		this.state.informOfCustomers(customers);

		// Profit = Sale Price - Cost
		// Profit = (1 + Margin)*Cost - Cost
		// Profit = Margin * Cost
		final double cost = this.state.getQuality().getCost();
		final double margin = this.state.getProfitMargin().getProfitMargin();

		return customers * (margin*cost);
	}

	@Override
	public String toString(){
		return String.format("%s: %s", this.name, this.state.getSymbol());
	}
}
