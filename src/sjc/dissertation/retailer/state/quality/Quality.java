package sjc.dissertation.retailer.state.quality;

import java.util.HashSet;
import java.util.Set;


/**
 * A sub-state or attribute of the state of a product.
 * This indicates the the cost of the product.
 * The quality can be one of three: Low, Medium, High.
 *
 * @author Stefan Collier
 *
 */
public enum Quality {
	HighQuality("<HQ>","High Quality", 3){
		@Override
		public Set<QualityChange> getActions(){
			final Set<QualityChange> actions = new HashSet<>(2);
			actions.add(QualityChange.DecreaseQuality);
			actions.add(QualityChange.MaintainQuality);
			return actions;
		}
		@Override
		public Quality changeQuality(final QualityChange change) throws InvalidQualityException {
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
	MediumQuality("<MQ>","Medium Quality", 2){
		@Override
		public Set<QualityChange> getActions(){
			final Set<QualityChange> actions = new HashSet<>(3);
			actions.add(QualityChange.DecreaseQuality);
			actions.add(QualityChange.MaintainQuality);
			actions.add(QualityChange.IncreaseQuality);
			return actions;
		}
		@Override
		public Quality changeQuality(final QualityChange change) throws InvalidQualityException {
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
	LowQuality("<LQ>","Low Quality", 1){
		@Override
		public Set<QualityChange> getActions(){
			final Set<QualityChange> actions = new HashSet<>(2);
			actions.add(QualityChange.MaintainQuality);
			actions.add(QualityChange.IncreaseQuality);
			return actions;
		}
		@Override
		public Quality changeQuality(final QualityChange change) throws InvalidQualityException {
			switch(change){
			case MaintainQuality: {
				return this;
			}
			case IncreaseQuality: {
				return MediumQuality;
			}
			default: {
				throw new InvalidQualityException(this, change);

			}
			}
		}
	};

	/** Human readable description of the Quality*/
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
	public double getCost(){
		return this.cost;
	}

	/**
	 * Get the shorthand notation for the quality.
	 *
	 * e.g. High quality is "+Q"
	 *
	 * @return short string indicating quality
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
		return (this.getCost() == other.getCost())
				&& (this.getSymbol() == other.getSymbol())
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
	public abstract Quality changeQuality(QualityChange change) throws InvalidQualityException;

	public abstract Set<QualityChange> getActions();
}
