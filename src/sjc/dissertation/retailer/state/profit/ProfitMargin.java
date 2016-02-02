package sjc.dissertation.retailer.state.profit;

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
		return "N/A";
	}

	public boolean equals(final ProfitMargin other){
		return false;
	}

	public double getProfitMargin(){
		return -666;
	}
}
