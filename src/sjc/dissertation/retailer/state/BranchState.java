package sjc.dissertation.retailer.state;

import java.util.Set;

import sjc.dissertation.retailer.state.profit.ProfitMargin;
import sjc.dissertation.retailer.state.quality.Quality;

/**
 * A representation of the state of a retailer.
 *
 * Each instance of a retailer state is a 3-tuple:
 * (Q, Pm, NoC) =>
 *    Q = Quality, Q ∈ {High, Medium, Low}
 *    Pm = ProfitMargin, Pm ∈ {High, Low, Zero, Negative}
 *    NoC = Number of Customers, NoC ∈ {0 ... Count(CustomerAgents)}
 *
 * @author Stefan Collier
 *
 */
public interface BranchState {

	public Set<RetailerAction> getActions();

	public Quality getQuality();

	public ProfitMargin getProfitMargin();

	public int getNumberOfCustomers();


	/**
	 * A shorthand version of the toString() method.
	 * Outlining the quality and profit margin.
	 *
	 * @return A few characted determining the state.
	 */
	public String getSymbol();

	BranchState createNewState(RetailerAction action) throws InvalidRetailerActionException;
}
