package sjc.dissertation.retailer;

import sjc.dissertation.retailer.state.RetailerState;

public class Retailer {
	private RetailerState state;
	private String name;

	public Retailer(final String name){
		this.state = new RetailerState();
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public RetailerState getState(){
		return this.state;
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
}
