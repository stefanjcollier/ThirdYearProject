package sjc.dissertation.retailer.state;

import java.util.Set;

import sjc.dissertation.retailer.state.profit.ProfitMargin;
import sjc.dissertation.retailer.state.quality.Quality;

//TODO 'Finish' Javadoc ReState
/**
 * Each instance of a retailer state is a 3-tuple: (Q, Pm, NoC)
 * Such that:
 * Q = Quality, Q ∈ {High, Medium, Low}
 * Pm = ProfitMargin, Pm ∈ {High, Low, Zero, Negative}
 * NoC = Number of Customers, NoC ∈ {0 ... Count(CustomerAgents)}
 *
 *
 * @author Stefan Collier
 *
 */
public class RetailerState {

	static final int NOT_INFORMED_OF_CUSTOMERS = -99;

	private Quality quality;
	private ProfitMargin profit;
	private int numberOfCustomers;

	public RetailerState(){
		this.quality = Quality.MediumQuality;
		this.profit = ProfitMargin.LowProfitMargin;
		this.numberOfCustomers = NOT_INFORMED_OF_CUSTOMERS;
	}

	public Set<RetailerAction> getActions(){
		return null;
	}

	public void computeAction(final RetailerAction action){

	}

	public boolean isCompleteState() {
		return false;
	}

	public Quality getQuality(){
		return this.quality;
	}

	public ProfitMargin getProfitMargin(){
		return this.profit;
	}

	public int getNumberOfCustomers(){
		return this.numberOfCustomers;
	}

	public boolean equals(final RetailerState other){
		return this.numberOfCustomers == other.numberOfCustomers
				&& this.quality.equals(other.quality)
				&& this.profit.equals(other.profit);
	}

	@Override
	public String toString(){
		return String.format("(%s, %s, %i)", this.quality, this.profit, this.numberOfCustomers);
	}
}
