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

	/**
	 * Combine all permutations of quality and profit margin changes to provide all possible actions.
	 */
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

	/**
	 * Taking an action that composes of a {@link QualityChange} and {@link ProfitMarginChange},
	 *  alter this state.
	 *
	 * @param action -- The quality and profit margin changes
	 * @throws InvalidRetailerActionException -- When a quality or profit margin cannot be changed in the requested way
	 */
	public void computeAction(final RetailerAction action) throws InvalidRetailerActionException{
		try {
			this.quality = this.quality.changeQuality(action.getQualityChange());
			this.profit = this.profit.changeProfitMargin(action.getProfitMarginChange());
			//Consider: (RtlrImpl) Reset number of customers?

		} catch (final InvalidQualityException e) {
			throw new InvalidRetailerActionException(action, this, e);
		} catch (final InvalidProfitMarginException e) {
			throw new InvalidRetailerActionException(action, this, e);
		}
	}

	/**
	 * Set the number of customers they have received based on the action that was computed
	 *
	 * @param noOfCustomers -- A natural integer of customers that shopped with this retailer
	 */
	public void informOfCustomers(final int noOfCustomers){
		this.numberOfCustomers = noOfCustomers;
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
		return String.format("(%s, %s, %d)",
				this.quality.getSymbol(), this.profit.getSymbol(), this.numberOfCustomers);
	}

	@Override
	public String toString(){
		return String.format("(%s, %s, %d)", this.quality.toString(), this.profit.toString(), this.numberOfCustomers);
	}
}
