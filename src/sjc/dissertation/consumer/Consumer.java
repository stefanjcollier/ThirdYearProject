package sjc.dissertation.consumer;

import sjc.dissertation.retailer.Retailer;

public class Consumer {
	private final int id;
	private final String socClass;
	private final double budget;

	protected Consumer(final int unseenId, final String socialClass, final double budget){
		this.id = unseenId;
		this.socClass = socialClass;
		this.budget = budget;
	}

	public int getId(){
		return this.id;
	}

	public String getSocialClass(){
		return this.socClass;
	}

	public double getBudget(){
		return this.budget;
	}

	public boolean canAfford(final Retailer retailer){
		return false;
	}

	public double costOfShop(final Retailer retailer){
		return -Double.MIN_VALUE;
	}

	public double chanceOf(final Retailer[] allRetailers, final Retailer testRetailer){
		return 0;
	}

	public Retailer chooseRetailer(final Retailer[] allRetailers){
		return null;
	}

}
