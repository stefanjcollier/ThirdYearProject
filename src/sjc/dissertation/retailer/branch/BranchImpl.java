package sjc.dissertation.retailer.branch;

import sjc.dissertation.model.logging.LoggerFactory;
import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.state.InternalRetailerState;
import sjc.dissertation.retailer.state.profit.ProfitMargin;
import sjc.dissertation.retailer.state.quality.Quality;

/**
 * This is the Branch, it is what a customer can see if they walked into a shop or
 * found retailer online.
 *
 * @author Stefan Collier
 *
 */
public class BranchImpl implements Branch{
	private final InternalRetailerState state;
	private final Retailer retailer;
	private final int id;
	private final double x,y;

	public BranchImpl(final Retailer owner, final int id, final double x, final double y){
		this.state = new InternalRetailerState();
		this.retailer = owner;
		this.id = id;
		this.x = x;
		this.y = y;
	}

	@Override
	public  InternalRetailerState getState(){
		return this.state;
	}

	@Override
	public Quality getQualityOfShop(){
		return this.state.getQuality();
	}

	/**
	 * Under the abstraction that branches offer a single 'weekly shop',
	 * returns the price of that 'shop'.
	 *
	 * @return The cost of shopping at given retailer
	 */
	@Override
	public double getCostOfShopping(){
		final double rawCost = this.state.getQuality().getCost();
		final double profitMultiplier = 1 + this.state.getProfitMargin().getProfitMargin();
		return rawCost * profitMultiplier;
	}


	/**
	 * Informs the branch how many consumers it had this week based on the actions it made.
	 *
	 * @param customers --  the number of people who shopped with this branch.
	 * @return profit based on customers
	 */
	@Override
	public double informOfCustomers(final int customers){
		this.state.informOfCustomers(customers);

		// Profit = Sale Price - Cost
		// Profit = (1 + Margin)*Cost - Cost
		// Profit = Margin * Cost
		final double cost = this.state.getQuality().getCost();
		final double margin = this.state.getProfitMargin().getProfitMargin();
		LoggerFactory.getSingleton().getMasterLogger().trace(String.format(
				"RetailerImpl(%s)::Profit:: Customers*(margin * cost) = %d * (%f * %f) = %f",
				this.getBranchName(),customers, margin, cost, (customers*margin*cost)));
		return customers * (margin*cost);
	}


	@Override
	public String getBranchName(){
		return String.format("%s[%d]", this.retailer.getName(), this.id);
	}


	@Override
	public String toString(){
		return String.format("%s: %s", this.getBranchName(), this.state.getSymbol());
	}

	@Override
	public ProfitMargin getProfiMargin() {
		return this.state.getProfitMargin();
	}

	@Override
	public Retailer getRetailer() {
		return this.retailer;
	}

	@Override
	public double getX(){
		return this.x;
	}

	@Override
	public double getY(){
		return this.y;
	}

}
