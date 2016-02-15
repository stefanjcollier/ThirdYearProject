package sjc.dissertation.retailer;

import sjc.dissertation.retailer.state.InternalRetailerState;

public class Retailer {
	private final InternalRetailerState state;
	private final String name;

	public Retailer(final String name){
		this.state = new InternalRetailerState();
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public InternalRetailerState getState(){
		return this.state;
	}

	/**
	 * Under the abstraction that retailers offer a single 'weekly shop',
	 * returns the price of that 'shop'.
	 *
	 * @return The cost of shopping at given retailer
	 */
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
	public double informOfCustomers(final int customers){
		this.state.informOfCustomers(customers);

		// Profit = Sale Price - Cost
		// Profit = (1 + Margin)*Cost - Cost
		// Profit = Margin * Cost
		final double cost = this.state.getQuality().getCost();
		final double margin = this.state.getProfitMargin().getProfitMargin();

		return margin*cost;
	}

	@Override
	public String toString(){
		return String.format("%s: %s", this.name, this.state);
	}
}
