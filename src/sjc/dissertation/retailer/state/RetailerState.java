package sjc.dissertation.retailer.state;

import java.util.HashSet;
import java.util.Set;

import sjc.dissertation.retailer.state.profit.InvalidProfitMarginException;
import sjc.dissertation.retailer.state.profit.ProfitMargin;
import sjc.dissertation.retailer.state.profit.ProfitMarginChange;
import sjc.dissertation.retailer.state.quality.InvalidQualityException;
import sjc.dissertation.retailer.state.quality.Quality;
import sjc.dissertation.retailer.state.quality.QualityChange;

//TODO 'Finish' Javadoc ReState
/**
 * Each instance of a retailer state is a 3-tuple:
 * (Q, Pm, NoC) =>
 *    Q = Quality, Q ∈ {High, Medium, Low}
 *    Pm = ProfitMargin, Pm ∈ {High, Low, Zero, Negative}
 *    NoC = Number of Customers, NoC ∈ {0 ... Count(CustomerAgents)}
 *
 *
 * @author Stefan Collier
 *
 */
public class RetailerState {
	static final int INITIAL_VALUE = -111;
	static final int NOT_INFORMED_OF_CUSTOMERS = -999;

	private Quality quality;
	private ProfitMargin profit;
	private int numberOfCustomers;

	public RetailerState(){
		this.quality = Quality.MediumQuality;
		this.profit = ProfitMargin.LowProfitMargin;
		this.numberOfCustomers = INITIAL_VALUE;
	}

	//TODO Consider: odd stuff around this like the unkown number of customers
	public Set<RetailerAction> getActions(){
		final Set<RetailerAction> actions = new HashSet<>();
		for (final QualityChange qc : this.quality.getActions()){
			for (final ProfitMarginChange pmc : this.profit.getActions()){
				actions.add(new RetailerAction(qc, pmc));
			}
		}
		return actions;
	}

	public void computeAction(final RetailerAction action) throws InvalidRetailerActionException{
		try {
			this.quality.changeQuality(action.getQualityChange());
			this.profit.changeProfitMargin(action.getProfitMarginChange());
			//TODO Consider: Reset number of customers?

		} catch (final InvalidQualityException e) {
			throw new InvalidRetailerActionException(action, this, e);
		} catch (final InvalidProfitMarginException e) {
			throw new InvalidRetailerActionException(action, this, e);
		}
	}

	public void informOfCustomers(final int noOfCustomers){
		//TODO Consider: What happens if the #(Customer) < 0 ??
		this.numberOfCustomers = noOfCustomers;
	}

	public boolean isCompleteState() {
		return this.numberOfCustomers == NOT_INFORMED_OF_CUSTOMERS;
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

	public String getSymbol(){
		return String.format("(%s, %s, %i)",
				this.quality.getSymbol(), this.profit.getSymbol(), this.numberOfCustomers);
	}

	@Override
	public String toString(){
		return String.format("(%s, %s, %i)", this.quality, this.profit, this.numberOfCustomers);
	}
}
