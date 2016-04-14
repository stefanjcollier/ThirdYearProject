package sjc.dissertation.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sjc.dissertation.consumer.Consumer;
import sjc.dissertation.consumer.ConsumerFactory;
import sjc.dissertation.model.logging.LoggerFactory;
import sjc.dissertation.model.logging.MasterLogger.Level;
import sjc.dissertation.model.logging.votes.VoteLogger;
import sjc.dissertation.retailer.CarnivoreRetailer;
import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.branch.Branch;
import sjc.dissertation.retailer.branch.BranchAgentFactory;
import sjc.dissertation.retailer.branch.CarnivoreBranchAgent;
import sjc.dissertation.retailer.learn.GreedyCarnivoreAlgorithmFactory;
import sjc.dissertation.retailer.state.MaintainAlgorithm;
import sjc.dissertation.util.FileUtils;

public class ModelRunner {

	/** ref:{@link http://countrymeters.info/en/United_Kingdom_(UK)} */
	static final int UK_POPULATION = 200;//65086445;

	/** Location to save the files */
	static String PATH = "C:\\Users\\Stefa\\Desktop\\DissResults\\";

	/** Rounds to be played*/
	static final int ROUNDS = 500;


	static final List<Retailer> retaiers = new ArrayList<Retailer>(4);

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
		final List<CarnivoreBranchAgent> branches = TEST_1Control_1Greedy(wrapper);

		//Data Loggers
		final VoteLogger voteLog = new VoteLogger(PATH, extractRetailers(branches), classes);

		//Consumer Agents
		final List<Consumer> consumers = makeConsumers(100, wrapper, voteLog);

		//Model
		final ModelController model = new ModelController(retaiers, branches, consumers, UK_POPULATION);

		//Time to play the game
		for(int round = 1; round <= ROUNDS; round++){
			model.performWeek();
			wrapper.getMasterLogger().print(String.format("Round %d Votes: %s",
					round, Arrays.toString(voteLog.getCurrentRoundResults())));
			voteLog.startNextRound();

		}

	}
	static List<CarnivoreBranchAgent> TEST_1Control_1Greedy(final LoggerFactory wrapper){
		final BranchAgentFactory agentFactory = BranchAgentFactory.getSingleton();
		final List<CarnivoreBranchAgent> agents = new ArrayList<>(2);

		//------------------------------
		////Make Control Agent
		final Retailer control_retailer = wrapper.wrapRetailer(new CarnivoreRetailer("Control"));
		//Control Branch 1
		final Branch con_branch_1 = wrapper.wrapBranch(control_retailer.createBranch(0, 0));
		final CarnivoreBranchAgent con_agent_1 = agentFactory.createNewCarnivoreAgent(con_branch_1, wrapper.wrapAlgorithm(new MaintainAlgorithm()));
		agents.add(con_agent_1);

		//------------------------------
		////Make Greedy Agent
		final GreedyCarnivoreAlgorithmFactory greedy_factory = new GreedyCarnivoreAlgorithmFactory(wrapper.getMasterLogger(), UK_POPULATION);
		final Retailer carn_retailer = wrapper.wrapRetailer(new CarnivoreRetailer("Greedy"));
		//Carn Branch 1
		final Branch carn_branch_1 = wrapper.wrapBranch(carn_retailer.createBranch(10, 10));
		final CarnivoreBranchAgent carn_agent_1 = agentFactory.createNewCarnivoreAgent(carn_branch_1,
				wrapper.wrapAlgorithm(greedy_factory.createWrappedGreedyAlgorithm(2)));
		agents.add(carn_agent_1);

		//Carn Branch 2
		final Branch carn_branch_2 = wrapper.wrapBranch(carn_retailer.createBranch(10, 10));
		final CarnivoreBranchAgent carn_agent_2 = agentFactory.createNewCarnivoreAgent(carn_branch_2,
				wrapper.wrapAlgorithm(greedy_factory.createWrappedGreedyAlgorithm(2)));
		agents.add(carn_agent_2);

		//------------------------------
		//Add the retailers (previously added branches)
		retaiers.add(carn_retailer);
		retaiers.add(control_retailer);

		return agents;
	}

	//	static List<BranchAgent> makeRetailers(final String[] names, final LoggerFactory wrapper){
	//		final BranchAgentFactory factory = BranchAgentFactory.getSingleton();
	//
	//		final List<BranchAgent> agents = new ArrayList<>(names.length);
	//
	//		for(final String name : names){
	//			//Gen a new retailer and wrap it for logging
	//			final CarnivoreRetailer carny = new CarnivoreRetailer(name);
	//			final Branch retailer = wrapper.wrapBranch(carny.createBranch(5, 5));
	//			final BranchAgent agent = factory.createNewAgent(retailer, wrapper.wrapAlgorithm(new MaintainAlgorithm()));
	//			agents.add(agent);
	//		}
	//
	//		return agents;
	//	}

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
	private static List<Branch> extractRetailers(final List<CarnivoreBranchAgent> agents){
		final List<Branch> retailers = new ArrayList<>(agents.size());
		for(final CarnivoreBranchAgent agent : agents){
			retailers.add(agent.getBranch());
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
