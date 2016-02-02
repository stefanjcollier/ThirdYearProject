package sjc.dissertation.state;

public enum Quality {
	HighQuality("<+Q>","High Quality", 3){
		@Override
		public Quality changeValue(final QualityChange change) throws InvalidQualityException {
			switch(change){
			case DecreaseQuality: {
				return MediumQuality;
			}
			case MaintainQuality: {
				return this;
			}
			default: {
				throw new InvalidQualityException(this, change);
			}
			}
		}
	},
	MediumQuality("<~Q>","Medium Quality", 2){
		@Override
		public Quality changeValue(final QualityChange change) throws InvalidQualityException {
			switch(change){
			case DecreaseQuality: {
				return LowQuality;
			}
			case MaintainQuality: {
				return this;
			}
			case IncreaseQuality: {
				return HighQuality;
			}
			default:{
				throw new InvalidQualityException(this, change);
			}
			}
		}
	},
	LowQuality("<-Q>","Low Quality", 1){
		@Override
		public Quality changeValue(final QualityChange change) throws InvalidQualityException {
			switch(change){
			case MaintainQuality: {
				return this;
			}
			case IncreaseQuality: {
				return HighQuality;
			}
			default: {
				throw new InvalidQualityException(this, change);

			}
			}
		}
	};

	/** Human Readable description of the Quality*/
	private String desc;

	/** A shorthand notation for the quality*/
	private String symbol;

	/** The raw cost of the quality*/
	private double cost;

	private Quality(final String shortHand, final String description, final double cost) {
		this.symbol = shortHand;
		this.desc = description;
		this.cost = cost;
	}


	@Override
	public String toString(){
		return this.desc;
	}

	/**
	 * Get the cost of the product at this quality level.
	 *
	 * @return cost of the quality
	 */
	public double getValue(){
		return this.cost;
	}

	/**
	 * Get the shorthand notation for the quality.
	 *
	 * e.g. High quality is "+Q"
	 *
	 * @return cost of the quality
	 */
	public String getSymbol(){
		return this.symbol;
	}

	/**
	 * Determines if two instances of {@link Quality} are the same.
	 *
	 * @param other -- Another quality level
	 * @return true if they are the same
	 */
	public boolean equals(final Quality other){
		return (this.getValue() == other.getValue())
				&& (this.toString() == other.toString());
	}

	/**
	 * Apply a change in quality and get the new {@link Quality} level.
	 *
	 * @param change - The instruction to increase/maintain/decrease the quality
	 * @return the new quality level
	 *
	 * @throws InvalidQualityException -- When a quality level cannot apply a particular change.
	 */
	public abstract Quality changeValue(QualityChange change) throws InvalidQualityException;


}
