package sjc.dissertation.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sjc.dissertation.consumer.Consumer;
import sjc.dissertation.consumer.ConsumerFactory;
import sjc.dissertation.model.logging.LoggerFactory;
import sjc.dissertation.model.logging.MasterLogger.Level;
import sjc.dissertation.model.logging.votes.VoteLogger;
import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.RetailerAgent;
import sjc.dissertation.retailer.RetailerAgentFactory;
import sjc.dissertation.retailer.RetailerImpl;
import sjc.dissertation.retailer.carnivore.GreedyAlgorithmFactory;
import sjc.dissertation.retailer.state.MaintainAlgorithm;
import sjc.dissertation.util.FileUtils;

//TODO Extend the model runner to 2 classes model runner and model-set-up
public class ModelRunner {

	/** ref:{@link http://countrymeters.info/en/United_Kingdom_(UK)} */
	static final int UK_POPULATION = 65086445;

	/** Location to save the files */
	static String PATH = "C:\\Users\\Stefa\\Desktop\\DissResults\\";

	/** Rounds to be played*/
	static final int ROUNDS = 5;

	public static void main(final String[] args){
		genPATH();
		//		final String[] names = {"Tesco", "ASDA"};
		final String[] classes = ConsumerFactory.getSingleton().getSocialClasses();

		//Text Logger
		LoggerFactory.initiateLoggerFactory(PATH);
		final LoggerFactory wrapper = LoggerFactory.getSingleton();
		wrapper.getMasterLogger().setDisplayLevel(Level.Print);


		//Retailer Agents
		//		final List<RetailerAgent> retailers = makeRetailers(names, wrapper);
		final List<RetailerAgent> retailers = TEST_1Control_1Greedy(wrapper);
		//Data Loggers
		final VoteLogger voteLog = new VoteLogger(PATH, extractRetailers(retailers), classes);

		//Consumer Agents
		final List<Consumer> consumers = makeConsumers(100, wrapper, voteLog);

		//Model
		final ModelController model = new ModelController(retailers, consumers, UK_POPULATION);

		//Time to play the game
		for(int i = 0; i < ROUNDS; i++){
			model.performWeek();
			voteLog.startNextRound();
		}

	}
	static List<RetailerAgent> TEST_1Control_1Greedy(final LoggerFactory wrapper){
		final RetailerAgentFactory agentFactory = RetailerAgentFactory.getSingleton();
		final List<RetailerAgent> agents = new ArrayList<>(2);

		//Make Control Agent
		final Retailer con_retailer = wrapper.wrapRetailer(new RetailerImpl("Control_1"));
		final RetailerAgent con_agent = agentFactory.createNewAgent(con_retailer, wrapper.wrapAlgorithm(new MaintainAlgorithm()));
		agents.add(con_agent);

		//Make Greedy Agent
		final GreedyAlgorithmFactory greedy_factory = new GreedyAlgorithmFactory(wrapper.getMasterLogger());
		final Retailer greedy_retailer = wrapper.wrapRetailer(new RetailerImpl("Greedy_1"));
		final RetailerAgent greedy_agent = agentFactory.createNewAgent(greedy_retailer, wrapper.wrapAlgorithm(greedy_factory.createGreedyAlgorithm(1)));
		agents.add(greedy_agent);

		return agents;
	}

	static List<RetailerAgent> makeRetailers(final String[] names, final LoggerFactory wrapper){
		final RetailerAgentFactory factory = RetailerAgentFactory.getSingleton();

		final List<RetailerAgent> agents = new ArrayList<>(names.length);

		for(final String name : names){
			//Gen a new retailer and wrap it for logging
			final Retailer retailer = wrapper.wrapRetailer(new RetailerImpl(name));
			final RetailerAgent agent = factory.createNewAgent(retailer, wrapper.wrapAlgorithm(new MaintainAlgorithm()));
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

	private static void genPATH(){
		final String date =FileUtils.getCurrentDate("test");

		for(int i = 1; true; i++){
			final String testPath = PATH+date+"_"+i;
			final File testFile = new File(testPath);
			if(!testFile.exists()){
				testFile.mkdirs();
				PATH = testPath;
				return;
			}
		}
	}

}
