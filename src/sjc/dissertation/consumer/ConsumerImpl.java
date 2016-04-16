package sjc.dissertation.consumer;

import java.util.List;

import sjc.dissertation.retailer.branch.Branch;
import sjc.dissertation.retailer.state.quality.Quality;
import sjc.dissertation.util.RandomToolbox;

/**
 * @author Stefan Collier
 *
 */
public class ConsumerImpl implements Consumer{
	private final int id;
	private final String socClass;
	private final double budget, x, y;

	protected ConsumerImpl(final int unseenId, final String socialClass, final double budget, final double x, final double y){
		this.id = unseenId;
		this.socClass = socialClass;
		this.budget = budget;
		this.x = x;
		this.y = y;
	}

	@Override
	public int getId(){
		return this.id;
	}

	@Override
	public String getSocialClass(){
		return this.socClass;
	}

	@Override
	public double getBudget(){
		return this.budget;
	}

	/**
	 * Determines whether this consumer can purchase their shop from the given retailer.
	 *
	 * @param branch -- The branch whom's price we are assessing
	 * @return true if the consumer can purchase their shop from the retailer.
	 */
	@Override
	public boolean canAfford(final Branch branch){
		return (costOfShop(branch)+costOfTravel(branch)) <= this.budget;
	}

	/**
	 * Returns the cost for this consumer to purchase their weekly shop.
	 *
	 * Currently the cost Of Shop is independent of the Consumer
	 *
	 * @param retailer -- The retailer whom's price we are assessing
	 * @return The cost for the consumer of shopping with the given retailer
	 */
	@Override
	public double costOfShop(final Branch re){
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
	@Override
	public double chanceOf(final List<Branch> allRe, final Branch tstRe){
		//If we cannot afford the shop then we won't shop there
		if(!canAfford(tstRe)){
			return 0;
		} else {
			//Otherwise it is the relative value for money
			final double tstVfm = valueForMoney(tstRe);

			//Sum the vfm of all (affordable) retailers
			double sumVfm = 0;
			for(final Branch re :  allRe){
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
	 *  vfm = reward * distance multiplier / (cost to shop + cost of travel)
	 *
	 * @param branch -- The retailer we are considering
	 * @return The value for money of shopping at branch (vfm >= 0)
	 */
	private double valueForMoney(final Branch branch){
		final double cost = costOfShop(branch) + costOfTravel(branch);

		//Determine how the distance affects the reward
		// dm = (d / (D/2))^2
		final double halfMapWidth = ConsumerFactory.getSingleton().getLocationFactory().getMapWidth()/2;
		final double distanceRewardMultiplier = Math.pow(distanceFromBranch(branch)/halfMapWidth,2);

		return rewardOfShop(branch) * distanceRewardMultiplier  / cost;
	}

	protected double distanceFromBranch(final Branch branch){
		final double dx = branch.getX() - this.getX();
		final double dy = branch.getY() - this.getY();
		return Math.sqrt(dx*dx + dy*dy);
	}

	/**
	 * The cost for this consumer to travel to the given branch (in pounds).
	 */
	protected double costOfTravel(final Branch branch) {
		final double diagonalDistance = distanceFromBranch(branch);
		final double costPerKm = ConsumerFactory.getSingleton().getCostOfTravelPerKm();

		return diagonalDistance * costPerKm;
	}

	/**
	 * A basic interpretation of the consumers care for the quality of the shop.
	 *
	 * @param re -- The retailer that we are considering for a shop
	 * @return The reward a consumer would receive from shopping at the given retailer
	 */
	private double rewardOfShop(final Branch re){
		//Consider: (Consumer) Create better rewards system
		//Consider: (Consumer) Sensitivity to quality
		final Quality q = re.getQualityOfShop();
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
	@Override
	public int chooseBranch(final List<Branch> retailers){
		//Find the chance for choosing each retailer
		final double[] chances = new double[retailers.size()];
		for (int re = 0; re < retailers.size(); re++){
			chances[re] = chanceOf(retailers, retailers.get(re));
		}
		//Make a weighted random choice of the retailers
		final int reChoice = RandomToolbox.probabilisticlyChoose(chances);
		return reChoice;
	}

	@Override
	public String toString(){
		return String.format("Consumer[%d]\tBudget: %s\t  Class: %s",
				this.id, budgetToString(), this.socClass);
	}


	/**
	 * Converts the budget (float) into a string of with the £ sign.
	 *
	 * There is a small amount of formatting to ensure that alignment:
	 * 		e.g.   £23.50
	 * 			  £123.50
	 *
	 * @return the budget in a pretty string format
	 */
	private String budgetToString(){
		final String raw = String.format("%s", this.budget);
		String pounds = raw.split("[.]")[0];
		final String pennies = raw.split("[.]")[1].substring(0, 2);

		pounds = (pounds.length() == 2)? " £"+pounds : "£"+pounds;
		return String.format("%s.%s", pounds, pennies);
	}

	@Override
	public double getX() {
		return this.x;
	}

	@Override
	public double getY() {
		return this.y;
	}




}
