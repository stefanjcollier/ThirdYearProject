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
	 *
	 * @param customers --  the number of people who shopped with this retailer.
	 * @return profit based on customers
	 */
	public double informOfCustomers(final int customers){
		return Double.MIN_VALUE;
	}
}
