package sjc.dissertation.retailer.state.quality;

/** A sub-action that affects the {@link Quality} level.
 *  Allows the level to be decreased, maintained or increased.
 *
 * @author Stefan Collier
 *
 */
public enum QualityChange {
	DecreaseQuality("<-Q>", "Decrease Quality"),
	MaintainQuality("<~Q>", "Maintain Quality"),
	IncreaseQuality("<+Q>", "Increase Quality");

	/** Human readable description of the Quality*/
	private String desc;

	/** A shorthand notation for the quality*/
	private String symbol;

	private QualityChange(final String sym, final String description){
		this.symbol = sym;
		this.desc = description;
	}

	/**
	 * Get the shorthand notation for the quality change.
	 *
	 * e.g. Increasing quality is "++Q"
	 *
	 * @return short string indicating quality change
	 */
	public String getSymbol(){
		return this.symbol;
	}

	@Override
	public String toString(){
		return this.desc;
	}

	/**
	 * Determines if two instances of {@link QualityChange} are the same.
	 *
	 * @param other -- Another quality change
	 * @return true if they are the same
	 */
	public boolean equals(final QualityChange other){
		return (this.getSymbol() == other.getSymbol())
				&& (this.toString() == other.toString());
	}


}
