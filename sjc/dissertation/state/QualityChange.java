package sjc.dissertation.state;

public enum QualityChange {
	DecreaseQuality("<--Q>"),
	MaintainQuality("<~~Q>"),
	IncreaseQuality("<++Q>");

	private final String symbol;

	private QualityChange(final String sym){
		this.symbol = sym;
	}

	public String getSymbol(){
		return this.symbol;
	}


}
