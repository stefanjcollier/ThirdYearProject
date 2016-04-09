package sjc.dissertation.retailer.state;

import sjc.dissertation.retailer.state.profit.ProfitMargin;
import sjc.dissertation.retailer.state.profit.ProfitMarginChange;
import sjc.dissertation.retailer.state.quality.Quality;
import sjc.dissertation.retailer.state.quality.QualityChange;


/**
 * The action a retailer can make, an action composes of changing the {@link Quality} and {@link ProfitMargin},
 * using a {@link QualityChange} and {@link ProfitMarginChange}.
 *
 * {@link RetailerAction} are consumed by {@link RetailState}s to change state.
 *
 * @author Stefan Collier
 *
 */
public class RetailerAction {

	private final QualityChange quality;
	private final ProfitMarginChange profit;

	protected RetailerAction(final QualityChange qualityChange, final ProfitMarginChange profitChange){
		this.quality = qualityChange;
		this.profit = profitChange;
	}

	public QualityChange getQualityChange(){
		return this.quality;
	}

	public ProfitMarginChange getProfitMarginChange(){
		return this.profit;
	}

	public boolean equals(final RetailerAction other){
		return this.quality.equals(other.quality)
				&& this.profit.equals(other.profit);
	}

	public String getSymbol(){
		return String.format("(%s, %s)",
				this.quality.getSymbol(), this.profit.getSymbol());
	}


	@Override
	public String toString(){
		return String.format("(%s, %s)", this.quality, this.profit);
	}

}
