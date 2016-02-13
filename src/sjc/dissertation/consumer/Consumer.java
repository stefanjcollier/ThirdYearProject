package sjc.dissertation.consumer;

import java.util.Random;

import sjc.dissertation.retailer.Retailer;

public class Consumer {
	private final int id;
	private final String socClass;
	private final double budget;
	private static final Random rng = new Random();

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

	/**
	 * Determines whether this consumer can purchase their shop from the given retailer.
	 *
	 * @param retailer -- The retailer whom's price we are assessing
	 * @return true if the consumer can purchase their shop from the retailer.
	 */
	public boolean canAfford(final Retailer retailer){
		return costOfShop(retailer) <= this.budget;
	}

	/**
	 * Returns the cost for this consumer to purchase their weekly shop.
	 *
	 * @param retailer -- The retailer whom's price we are assessing
	 * @return The cost for the consumer of shopping with the given retailer
	 */
	public double costOfShop(final Retailer retailer){
		return -Double.MIN_VALUE;
	}

	public double chanceOf(final Retailer[] allRetailers, final Retailer testRetailer){
		return -1;
	}

	private double valueForMoney(final Retailer re){
		return -1;
	}

	/**
	 * Choose a retailer based on it's substate: Quality and Profit Margin.
	 * There is no guarantee that the retailer offering the best deal will
	 * be chosen as there it is
	 *
	 * @param retailers -- All the retailers to choose from
	 * @return the chosen retailer
	 */
	public Retailer chooseRetailer(final Retailer[] retailers){
		//Find the chance for choosing each retailer
		final double[] chances = new double[retailers.length];
		for (int re = 0; re < retailers.length; re++){
			chances[re] = chanceOf(retailers, retailers[re]);
		}
		//Make a weighted random choice of the retailers
		return retailers[probabilisticlyChoose(chances)];
	}

	/**
	 * Makes a choice based on the various probabilities.
	 *
	 * e.g. chances=[0.7, 0.2, 0.1]
	 *  There is a high chance of choosing option 1 however
	 *  any option could be chosen.
	 *
	 *  @param chances -- The chances that all sum to 1
	 *  @return the weighted random choice.
	 */
	private int probabilisticlyChoose(final double[] chances){
		//Assume Sum(chances) = 1 i.e. Pie chart
		//Choose a random point in a circle (circumference = 1)
		final double randomPoint = rng.nextDouble();

		//The start of the slice
		double lowThresh = 0;

		//The end of the slice
		double highThresh = 0;

		//Cycle through all 'slices' and see if that point is in that slice
		for(int i = 0; i < chances.length; i++){
			highThresh += chances[i];
			//If the point is in the slice
			if(lowThresh <= randomPoint && randomPoint < highThresh){
				//Return the index of that slice
				return i;
			}
			lowThresh = highThresh;
		}
		return -1;
	}

}
