package sjc.dissertation.model;

import java.util.ArrayList;
import java.util.List;

import sjc.dissertation.consumer.ConsumerImpl;
import sjc.dissertation.consumer.ConsumerFactory;
import sjc.dissertation.model.StubAlgorithm;
import sjc.dissertation.retailer.RetailerImpl;
import sjc.dissertation.retailer.RetailerAgent;
import sjc.dissertation.retailer.RetailerAgentFactory;

public class ModelRunner {

	/** ref:{@link http://countrymeters.info/en/United_Kingdom_(UK)} */
	static final int UK_POPULATION = 65086445;

	public static void main(final String[] args){
		final String[] names = {"Tesco", "ASDA"};
		final ModelController model = new ModelController(makeRetailers(names), makeConsumers(100), UK_POPULATION);
		model.performWeek();
	}

	static List<RetailerAgent> makeRetailers(final String[] names){
		final RetailerAgentFactory factory = RetailerAgentFactory.getSingleton();

		final List<RetailerAgent> agents = new ArrayList<>(names.length);

		for(final String name : names){
			final RetailerAgent agent = factory.createNewAgent(new RetailerImpl(name), new StubAlgorithm());
			agents.add(agent);
			System.out.println("MADE: Retailer "+agent);
		}

		return agents;
	}

	static List<ConsumerImpl> makeConsumers(final int total){
		final ConsumerFactory factory = ConsumerFactory.getSingleton();

		final List<ConsumerImpl> consumers = new ArrayList<>(10);

		for(int i = 0; i < total; i++){
			consumers.add(factory.createNewConsumer());
			System.out.println("MADE: "+consumers.get(i));
		}

		return consumers;
	}

}
