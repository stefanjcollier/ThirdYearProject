package sjc.dissertation.retailer.state.profit;

/** A sub-action that affects the {@link ProfitMargin} level.
 *  Allows the level to be decreased, maintained or increased.
 *
 * @author Stefan Collier
 *
 */
public enum ProfitMarginChange {
	DecreaseProfitMargin("<-P>", "Decrease Profit Margin"),
	MaintainProfitMargin("<~P>", "Maintain Profit Margin"),
	IncreaseProfitMargin("<+P>", "Increase Profit Margin");

	/** Human readable description of the Profit Margin*/
	private String desc;

	/** A shorthand notation for the profit margin*/
	private String symbol;

	private ProfitMarginChange(final String sym, final String description){
		this.symbol = sym;
		this.desc = description;
	}

	/**
	 * Get the shorthand notation for the profit margin change.
	 *
	 * e.g. Increasing profit is "+P"
	 *
	 * @return short string indicating profit margin change
	 */
	public String getSymbol(){
		return this.symbol;
	}

	@Override
	public String toString(){
		return this.desc;
	}

	/**
	 * Determines if two instances of {@link ProfitMarginChange} are the same.
	 *
	 * @param other -- Another profit margin change
	 * @return true if they are the same
	 */
	public boolean equals(final ProfitMarginChange other){
		return (this.getSymbol() == other.getSymbol())
				&& (this.toString() == other.toString());
	}


}
