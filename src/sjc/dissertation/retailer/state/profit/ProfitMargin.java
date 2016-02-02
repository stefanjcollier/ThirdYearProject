package sjc.dissertation.retailer.state.profit;

//TODO javadoc PrMa
public enum ProfitMargin {
	HighProfitMargin("<P++>","Very High Profit Margin",0.2){
		@Override
		public ProfitMargin changeProfitMargin(final ProfitMarginChange pm) throws InvalidProfitMarginException {
			switch(pm){
			case MaintainProfitMargin: {
				return this;
			}
			case DecreaseProfitMargin: {
				return LowProfitMargin;
			}
			default: {
				throw new InvalidProfitMarginException(this, pm);
			}
			}
		}
	},
	LowProfitMargin("<P+>","High Profit Margin",0.1){
		@Override
		public ProfitMargin changeProfitMargin(final ProfitMarginChange pm) throws InvalidProfitMarginException {
			switch(pm){
			case MaintainProfitMargin: {
				return this;
			}
			case DecreaseProfitMargin: {
				return NoProfitMargin;
			}
			case IncreaseProfitMargin: {
				return HighProfitMargin;
			}
			default: {
				throw new InvalidProfitMarginException(this, pm);
			}
			}
		}
	},
	NoProfitMargin("<0P>","No Profit (Margin)",0) {
		@Override
		public ProfitMargin changeProfitMargin(final ProfitMarginChange pm) throws InvalidProfitMarginException {
			switch(pm){
			case MaintainProfitMargin: {
				return this;
			}
			case DecreaseProfitMargin: {
				return NegativeProfitMargin;
			}
			case IncreaseProfitMargin: {
				return LowProfitMargin;
			}
			default: {
				throw new InvalidProfitMarginException(this, pm);
			}
			}
		}
	},
	NegativeProfitMargin("<P->","Negative Profit Margin", -0.1){
		@Override
		public ProfitMargin changeProfitMargin(final ProfitMarginChange pm) throws InvalidProfitMarginException {
			switch(pm){
			case MaintainProfitMargin: {
				return this;
			}
			case IncreaseProfitMargin: {
				return NoProfitMargin;
			}
			default: {
				throw new InvalidProfitMarginException(this, pm);
			}
			}
		}
	};

	/** A shorthand notation for the quality*/
	private final String sym;

	/** Human readable description of the profit margin*/
	private final String desc;

	/** The percentage representation of profit margin */
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


	/**
	 * Apply a change in profit margin and get the new {@link ProfitMargin} level.
	 *
	 * @param change - The instruction to increase/maintain/decrease the profit margin
	 * @return the new profit margin level
	 *
	 * @throws InvalidProfitMarginException -- When a quality level cannot apply a particular change.
	 */

	public abstract ProfitMargin changeProfitMargin(ProfitMarginChange pm) throws InvalidProfitMarginException;
}
