package sjc.dissertation.retailer.state;

import java.util.HashSet;
import java.util.Set;

import sjc.dissertation.retailer.state.profit.InvalidProfitMarginException;
import sjc.dissertation.retailer.state.profit.ProfitMargin;
import sjc.dissertation.retailer.state.profit.ProfitMarginChange;
import sjc.dissertation.retailer.state.quality.InvalidQualityException;
import sjc.dissertation.retailer.state.quality.Quality;
import sjc.dissertation.retailer.state.quality.QualityChange;

/**
 * An implementation of {@link RetailerState} that allows state
 * change, via the mutator {@link InternalRetailerState#computeAction(RetailerAction)}..
 *
 * Due to the size of Java Objects each retailer should contain their own
 * instance of this class. Mutators enable abstract/theoretical state changes even though
 * they use the same state instance.
 *
 *
 * @author Stefan Collier
 *
 */
public class InternalRetailerState implements RetailerState{
	static final int INITIAL_VALUE = -111;
	static final int NOT_INFORMED_OF_CUSTOMERS = -999;

	private Quality quality;
	private ProfitMargin profit;
	private int numberOfCustomers;

	public InternalRetailerState(){
		this.quality = Quality.MediumQuality;
		this.profit = ProfitMargin.LowProfitMargin;
		this.numberOfCustomers = INITIAL_VALUE;
	}

	//TODO Consider: odd stuff around this like the unkown number of customers
	@Override
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

	@Override
	public boolean isCompleteState() {
		return this.numberOfCustomers == NOT_INFORMED_OF_CUSTOMERS;
	}

	@Override
	public Quality getQuality(){
		return this.quality;
	}

	@Override
	public ProfitMargin getProfitMargin(){
		return this.profit;
	}

	@Override
	public int getNumberOfCustomers(){
		return this.numberOfCustomers;
	}

	@Override
	public String getSymbol(){
		return String.format("(%s, %s, %i)",
				this.quality.getSymbol(), this.profit.getSymbol(), this.numberOfCustomers);
	}

	@Override
	public String toString(){
		return String.format("(%s, %s, %i)", this.quality, this.profit, this.numberOfCustomers);
	}
}
