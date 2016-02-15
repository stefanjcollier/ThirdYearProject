package sjc.dissertation.consumer;

import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.state.quality.Quality;
import sjc.dissertation.util.RandomToolbox;

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
	 * Currently the cost Of Shop is independent of the Consumer
	 *
	 * @param retailer -- The retailer whom's price we are assessing
	 * @return The cost for the consumer of shopping with the given retailer
	 */
	public double costOfShop(final Retailer re){
		return re.getCostOfShopping();
	}

	/**
	 * Return the chance of choosing a retailer out of the given retailers.
	 * Currently is based entirely on the relative value for money of the shop.
	 *
	 * Assumption: testRetailer is member of allRetailers
	 *
	 * e.g. 3 retailers with vfms of 5,5,10.
	 * The chance of retailer 3 = 0.5 as 10/(5+5+10) given the consumer
	 * has the budget >=10
	 *
	 * @param allRe -- A list of all the options
	 * @param tstRe -- The retailer from those options
	 * @return the chance [0,1] of choosing the testRetailer
	 */
	public double chanceOf(final Retailer[] allRe, final Retailer tstRe){
		//If we cannot afford the shop then we won't shop there
		if(!canAfford(tstRe)){
			return 0;
		} else {
			//Otherwise it is the relative value for money
			final double tstVfm = valueForMoney(tstRe);

			//Sum the vfm of all (affordable) retailers
			double sumVfm = 0;
			for(final Retailer re :  allRe){
				double reVfm = 0;
				if(canAfford(re)){
					reVfm = valueForMoney(re);
				}
				sumVfm += reVfm;
			}

			//relative vfm
			return tstVfm/sumVfm;
		}
	}

	/**
	 * Get the value for money of the shop at the given retailer.
	 * This is determined by the interpreted reward of the shop
	 *  compared to cost of the 'shop'
	 *
	 * @param re -- The retailer we are considering
	 * @return The value for money of shopping at retailer 're' (vfm >= 0)
	 */
	private double valueForMoney(final Retailer re){
		return rewardOfShop(re) / costOfShop(re);
	}

	/**
	 * A basic interpretation of the consumers care for the quality of the shop.
	 *
	 * @param re -- The retailer that we are considering for a shop
	 * @return The reward a consumer would receive from shopping at the given retailer
	 */
	private double rewardOfShop(final Retailer re){
		//TODO (Consumer) Create better rewards system
		// Consider: Sensitivity to quality
		final Quality q = re.getState().getQuality();
		switch(q){
		case HighQuality: {
			return 5;
		}
		case MediumQuality: {
			return 3;
		}
		case LowQuality: {
			return 1;
		}
		default: {
			//This is an error state as no other quality should exist
			return -1000;
		}
		}
	}

	/**
	 * Choose a retailer based on it's sub-state: Quality and Profit Margin.
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
		final int reChoice = RandomToolbox.probabilisticlyChoose(retailers, chances);
		return retailers[reChoice];
	}



}
