package sjc.dissertation;

import java.util.ArrayList;
import java.util.List;

import sjc.dissertation.consumer.Consumer;
import sjc.dissertation.consumer.ConsumerFactory;
import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.RetailerAgent;
import sjc.dissertation.retailer.RetailerAgentFactory;

public class ModelRunner {

	public static void main(final String[] args){


		final ModelController model = new ModelController(makeRetailers(), makeConsumers());
		model.performWeek();
	}

	static List<RetailerAgent> makeRetailers(){
		final RetailerAgentFactory factory = RetailerAgentFactory.getSingleton();

		final List<RetailerAgent> agents = new ArrayList<>(2);

		final RetailerAgent agent1 = factory.createNewAgent(new Retailer("Tesco"), new StubAlgorithm());
		System.out.println("MADE: Retailer "+agent1);
		final RetailerAgent agent2 = factory.createNewAgent(new Retailer("ASDA"), new StubAlgorithm());
		System.out.println("MADE: Retailer "+agent2);

		agents.add(agent1);
		agents.add(agent2);

		return agents;
	}

	static List<Consumer> makeConsumers(){
		final ConsumerFactory factory = ConsumerFactory.getSingleton();

		final List<Consumer> consumers = new ArrayList<>(10);

		for(int i = 0; i < 100; i++){
			consumers.add(factory.createNewConsumer());
			System.out.println("MADE: "+consumers.get(i));
		}

		return consumers;
	}

}
