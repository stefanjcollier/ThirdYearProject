package sjc.dissertation.model;

import java.util.ArrayList;
import java.util.List;

import sjc.dissertation.consumer.Consumer;
import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.RetailerAgent;
import sjc.dissertation.retailer.state.InvalidRetailerActionException;
import sjc.dissertation.util.MyTools;

public class ModelController {

	private final List<Consumer> consumsers;
	private final List<RetailerAgent> retailerAgents;
	private final int voteWeight;

	/**
	 * Creates a Model with a discrete number of agents and consumers
	 *
	 * @param retailers -- The players in the model
	 * @param consumers -- A discrete number of consumer agents
	 * @param ukPopulation -- The actual UK population
	 */
	public ModelController(final List<RetailerAgent> retailers, final List<Consumer> consumers, final int ukPopulation){
		this.retailerAgents = retailers;
		this.consumsers = consumers;
		this.voteWeight = ukPopulation / consumers.size();
	}

	/**
	 * Asks the retailers for the prices they will offer for this week.
	 * Asks the consumers where they shopped. Then informs the retailers.
	 *
	 */
	public void performWeek(){
		final List<Retailer> retailers = demmandRetailerActions();
		final int[] choices = demmandConsumerChoices(retailers);
		informRetailersOfConsumers(choices);
	}

	/**
	 * Asks the retailer agents what their prices will be for this week.
	 *
	 * @return A list of retailers, containing each agents retail chain.
	 */
	private List<Retailer> demmandRetailerActions(){
		final List<Retailer> retailers = new ArrayList<>(this.retailerAgents.size());

		for(final RetailerAgent agent : this.retailerAgents){
			retailers.add(agent.getRetailer());
		}

		int retailerIndex = 0;
		for(final RetailerAgent agent : this.retailerAgents){
			try {
				final List<Retailer> competitors = MyTools.skipIndex(retailerIndex, retailers);
				agent.demandAction(competitors);
				retailerIndex++;

			} catch (final InvalidRetailerActionException e) {
				System.err.println(String.format("RetailerAgent %s is trying to make moves it cannot make.", agent));
				e.printStackTrace();
			}
		}
		return retailers;
	}

	/**
	 * Given the list of retailers, asks where the consumers purchased their food.
	 * Also includes the number of consumers who chose not to buy their food from the given retailers.
	 * This would be due to them not being able to afford any of them.
	 *
	 * @param retailers -- The retailers they can purchase from.
	 * @return An array saying how many consumers visited each retailer. The last index is the number of no buys
	 */
	private int[] demmandConsumerChoices(final List<Retailer> retailers){
		final int[] choices = new int[retailers.size()+1];

		for(final Consumer consumer : this.consumsers){
			final int choice = consumer.chooseRetailer(retailers);
			if(choice != -1) {
				choices[choice]++;
			}else{
				choices[retailers.size()]++;
			}
		}

		return choices;
	}

	/**
	 * Informs each retailer of how many consumers chose them for their weekly shop
	 *
	 * @param votes -- The shoppers at each retailer
	 */
	private void informRetailersOfConsumers(final int[] votes){
		//TODO Inform the world of the choices made
		//Actually use the bloody vote logger
		for(int re = 0; re < this.retailerAgents.size(); re++){
			this.retailerAgents.get(re).informOfCustomers(reweighVotes(votes[re]));
		}
	}

	/**
	 * Returns the amount of the population that 'chose' this retailer.
	 *
	 * As we are working with a subset of the population. Each consumer represents
	 * 	 a large number of consumers of that type.
	 *
	 * Hence if we a population of 1 million and represented that with 100 Consumer Agents
	 * 	 each consumer agent's vote would be worth 10,000
	 *
	 * @param votes - The number of consumers that chose this retailer
	 * @return The amount of the population that 'chose' this retailer
	 */
	private int reweighVotes(final int votes){
		return votes*this.voteWeight;
	}

}
