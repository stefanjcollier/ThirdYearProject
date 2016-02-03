package sjc.dissertation.retailer.state;

import sjc.dissertation.retailer.state.profit.ProfitMarginChange;
import sjc.dissertation.retailer.state.quality.QualityChange;

//TODO Javadoc
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
