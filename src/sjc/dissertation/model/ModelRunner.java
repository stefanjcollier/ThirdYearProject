package sjc.dissertation.model;

import java.util.ArrayList;
import java.util.List;

import sjc.dissertation.consumer.Consumer;
import sjc.dissertation.consumer.ConsumerFactory;
import sjc.dissertation.model.logging.LoggerFactory;
import sjc.dissertation.model.logging.votes.VoteLogger;
import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.RetailerAgent;
import sjc.dissertation.retailer.RetailerAgentFactory;
import sjc.dissertation.retailer.RetailerImpl;

public class ModelRunner {

	/** ref:{@link http://countrymeters.info/en/United_Kingdom_(UK)} */
	static final int UK_POPULATION = 65086445;
	static final String PATH = "resources";

	public static void main(final String[] args){
		final String[] names = {"Tesco", "ASDA"};
		final String[] classes = ConsumerFactory.getSingleton().getSocialClasses();

		//Text Logger
		LoggerFactory.initiateLoggerFactory(PATH);
		final LoggerFactory wrapper = LoggerFactory.getSingleton();

		//Retailer Agents
		final List<RetailerAgent> retailers = makeRetailers(names, wrapper);

		//Data Loggers
		final VoteLogger voteLog = new VoteLogger(PATH, extractRetailers(retailers), classes);

		//Consumer Agents
		final List<Consumer> consumers = makeConsumers(100, wrapper, voteLog);

		//Model
		final ModelController model = new ModelController(retailers, consumers, UK_POPULATION);
		model.performWeek();
	}

	static List<RetailerAgent> makeRetailers(final String[] names, final LoggerFactory wrapper){
		final RetailerAgentFactory factory = RetailerAgentFactory.getSingleton();

		final List<RetailerAgent> agents = new ArrayList<>(names.length);

		for(final String name : names){
			//Gen a new retailer and wrap it for logging
			final Retailer retailer = wrapper.wrapRetailer(new RetailerImpl(name));
			final RetailerAgent agent = factory.createNewAgent(retailer, new StubAlgorithm());
			agents.add(agent);
		}

		return agents;
	}

	static List<Consumer> makeConsumers(final int total, final LoggerFactory wrapper, final VoteLogger voteLog){
		final ConsumerFactory factory = ConsumerFactory.getSingleton();

		final List<Consumer> consumers = new ArrayList<>(10);

		for(int i = 0; i < total; i++){
			//Gen a new consumer and wrap it for logging
			final Consumer consumer = wrapper.wrapConsumer(factory.createNewConsumer(), voteLog);
			consumers.add(consumer);
		}

		return consumers;
	}

	/** Extract retailers from agents */
	private static List<Retailer> extractRetailers(final List<RetailerAgent> agents){
		final List<Retailer> retailers = new ArrayList<>(agents.size());
		for(final RetailerAgent agent : agents){
			retailers.add(agent.getRetailer());
		}
		return retailers;
	}

}
