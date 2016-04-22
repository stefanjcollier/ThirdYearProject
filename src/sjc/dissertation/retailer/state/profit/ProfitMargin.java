package sjc.dissertation.retailer.state.profit;

import java.util.HashSet;
import java.util.Set;

public enum ProfitMargin {
	HighProfitMargin("<P++++>", "Very High Profit Margin", 0.2) {
		@Override
		public Set<ProfitMarginChange> getActions() {
			final Set<ProfitMarginChange> actions = new HashSet<>(2);
			actions.add(ProfitMarginChange.DecreaseProfitMargin);
			actions.add(ProfitMarginChange.MaintainProfitMargin);
			return actions;
		}

		@Override
		public ProfitMargin changeProfitMargin(final ProfitMarginChange pm) throws InvalidProfitMarginException {
			switch (pm) {
			case MaintainProfitMargin: {
				return this;
			}
			case DecreaseProfitMargin: {
				return MediumProfitMargin;
			}
			default: {
				throw new InvalidProfitMarginException(this, pm);
			}
			}
		}
	},
	MediumProfitMargin("<P+++>", "Medium Profit Margin", 0.15) {
		@Override
		public ProfitMargin changeProfitMargin(final ProfitMarginChange pm) throws InvalidProfitMarginException {
			switch (pm) {
			case MaintainProfitMargin: {
				return this;
			}
			case DecreaseProfitMargin: {
				return LowProfitMargin;
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
	LowProfitMargin("<P++>", "Low Profit Margin", 0.1) {
		@Override
		public ProfitMargin changeProfitMargin(final ProfitMarginChange pm) throws InvalidProfitMarginException {
			switch (pm) {
			case MaintainProfitMargin: {
				return this;
			}
			case DecreaseProfitMargin: {
				return VeryLowProfitMargin;
			}
			case IncreaseProfitMargin: {
				return MediumProfitMargin;
			}
			default: {
				throw new InvalidProfitMarginException(this, pm);
			}
			}
		}
	},
	VeryLowProfitMargin("<P+>", "Very Low Profit Margin", 0.05) {
		@Override
		public ProfitMargin changeProfitMargin(final ProfitMarginChange pm) throws InvalidProfitMarginException {
			switch (pm) {
			case MaintainProfitMargin: {
				return this;
			}
			case DecreaseProfitMargin: {
				return NoProfitMargin;
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
	NoProfitMargin("<0P>", "No Profit (Margin)", 0) {
		@Override
		public ProfitMargin changeProfitMargin(final ProfitMarginChange pm) throws InvalidProfitMarginException {
			switch (pm) {
			case MaintainProfitMargin: {
				return this;
			}
			case DecreaseProfitMargin: {
				return NegativeProfitMargin;
			}
			case IncreaseProfitMargin: {
				return VeryLowProfitMargin;
			}
			default: {
				throw new InvalidProfitMarginException(this, pm);
			}
			}
		}
	},
	NegativeProfitMargin("<P->", "Negative Profit Margin", -0.1) {
		@Override
		public Set<ProfitMarginChange> getActions() {
			final Set<ProfitMarginChange> actions = new HashSet<>(2);
			actions.add(ProfitMarginChange.MaintainProfitMargin);
			actions.add(ProfitMarginChange.IncreaseProfitMargin);
			return actions;
		}

		@Override
		public ProfitMargin changeProfitMargin(final ProfitMarginChange pm) throws InvalidProfitMarginException {
			switch (pm) {
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

	/** A shorthand notation for the quality */
	private final String sym;

	/** Human readable description of the profit margin */
	private final String desc;

	/** The percentage representation of profit margin */
	private final double prof;

	private ProfitMargin(final String symbol, final String description, final double margin) {
		this.sym = symbol;
		this.desc = description;
		this.prof = margin;
	}

	public String getSymbol() {
		return this.sym;
	}

	@Override
	public String toString() {
		return this.desc;
	}

	/**
	 * Determines if two instances of {@link ProfitMargin} are the same.
	 *
	 * @param other
	 *            -- Another profit margin instance
	 * @return true if they are the same
	 */
	public boolean equals(final ProfitMargin other) {
		return (this.sym == other.sym) && (this.desc == other.desc) && (this.prof == other.prof);
	}

	/**
	 * Get the percentage value of the profit margin level. I.e. the percentage
	 * that you would add/take-away from a product.s
	 *
	 * @return a percentage denoting profit margin
	 */
	public double getProfitMargin() {
		return this.prof;
	}

	/**
	 * Apply a change in profit margin and get the new {@link ProfitMargin}
	 * level.
	 *
	 * @param change
	 *            - The instruction to increase/maintain/decrease the profit
	 *            margin
	 * @return the new profit margin level
	 *
	 * @throws InvalidProfitMarginException
	 *             -- When a quality level cannot apply a particular change.
	 */

	public abstract ProfitMargin changeProfitMargin(ProfitMarginChange pm) throws InvalidProfitMarginException;

	/**
	 * Get all the possible changes that can be from this Quality.
	 *
	 * @return a set of possible actions
	 */
	public Set<ProfitMarginChange> getActions() {
		final Set<ProfitMarginChange> actions = new HashSet<>(3);
		actions.add(ProfitMarginChange.DecreaseProfitMargin);
		actions.add(ProfitMarginChange.MaintainProfitMargin);
		actions.add(ProfitMarginChange.IncreaseProfitMargin);
		return actions;
	}

}
