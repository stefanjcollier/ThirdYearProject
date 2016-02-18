package sjc.dissertation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sjc.dissertation.consumer.Consumer;
import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.RetailerAgent;
import sjc.dissertation.retailer.state.InvalidRetailerActionException;

public class ModelController {

	private final List<Consumer> consumsers;
	private final List<RetailerAgent> retailerAgents;

	public ModelController(final List<RetailerAgent> retailers, final List<Consumer> consumers){
		this.retailerAgents = retailers;
		this.consumsers = consumers;
	}

	public void performWeek(){
		final List<Retailer> retailers = demmandRetailerActions();
		final int[] choices = demmandConsumerChoices(retailers);
		informRetailersOfConsumers(choices);
	}

	private List<Retailer> demmandRetailerActions(){
		final List<Retailer> retailers = new ArrayList<>(this.retailerAgents.size());

		for(final RetailerAgent agent : this.retailerAgents){

			try {
				retailers.add(agent.demandAction());

			} catch (final InvalidRetailerActionException e) {
				System.err.println(String.format("RetailerAgent %s is trying to make moves it cannot make.", agent));
				e.printStackTrace();
			}
		}
		return retailers;
	}

	private int[] demmandConsumerChoices(final List<Retailer> retailers){
		final int[] choices = new int[retailers.size()];

		for(final Consumer consumer : this.consumsers){
			final int choice = consumer.chooseRetailer(retailers);
			choices[choice]++;
		}

		return choices;
	}

	private void informRetailersOfConsumers(final int[] choices){
		System.out.println("Choices: "+Arrays.toString(choices));
		for(int re = 0; re < choices.length; re++){
			this.retailerAgents.get(re).informOfCustomers(choices[re]);
		}
	}

}
