package sjc.dissertation.retailer.state;

import java.util.List;

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
	private ProfitMargin margin;
	private int numberOfCustomers;

	public RetailerState(){
		this.quality = Quality.MediumQuality;
		this.margin = ProfitMargin.LowProfitMargin;
		this.numberOfCustomers = NOT_INFORMED_OF_CUSTOMERS;
	}

	public List<RetailerAction> getActions(){
		return null;
	}

	public String getSymbol(){
		return "Not Implemented";
	}

	public void computeAction(final RetailerAction action){

	}

	public boolean isCompleteState() {
		return false;
	}

	public Quality getQuality(){
		return null;
	}

	public ProfitMargin getProfitMargin(){
		return null;
	}

	public int getNumberOfCustomers(){
		return NOT_INFORMED_OF_CUSTOMERS;
	}

	public boolean equals(final RetailerState other){
		return false;
	}

	@Override
	public String toString(){
		return "Not Implemented";
	}
}
