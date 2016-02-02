package sjc.dissertation.retailer.state.profit;

//TODO javadoc PM
public enum ProfitMargin {
	VeryHighProfitMargin("<++P>","Very High Profit Margin",0.2),
	HighProfitMargin("<+P>","High Profit Margin",0.1),
	NoProfitMargin("<0P>","No Profit (Margin)",0),
	NegativeProfitMargin("<-P>","Negative Profit Margin", -0.1);

	private final String sym;
	private final String desc;
	private final double prof;

	private ProfitMargin(final String symbol, final String description, final double margin){
		this.sym = symbol;
		this.desc = description;
		this.prof = margin;
	}

	@Override
	public String toString(){
		return this.desc;
	}

	/**
	 * Determines if two instances of {@link ProfitMargin} are the same.
	 *
	 * @param other -- Another profit margin instance
	 * @return true if they are the same
	 */
	public boolean equals(final ProfitMargin other){
		return (this.sym == other.sym)
				&& (this.desc == other.desc)
				&& (this.prof == other.prof);
	}


	/**
	 * Get the percentage value of the profit margin level.
	 * I.e. the percentage that you would add/take-away from a product.s
	 *
	 * @return a percentage denoting profit margin
	 */
	public double getProfitMargin(){
		return this.prof;
	}
}
