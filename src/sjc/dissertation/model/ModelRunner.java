package sjc.dissertation.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sjc.dissertation.consumer.Consumer;
import sjc.dissertation.consumer.ConsumerFactory;
import sjc.dissertation.consumer.LocationFactory;
import sjc.dissertation.model.logging.LoggerFactory;
import sjc.dissertation.model.logging.MasterLogger.Level;
import sjc.dissertation.model.logging.results.PrintResultsInterface;
import sjc.dissertation.model.logging.votes.VoteLogger;
import sjc.dissertation.model.logging.wrappers.WrappedConsumer;
import sjc.dissertation.model.logging.wrappers.WrappedRetailer;
import sjc.dissertation.retailer.CarnivoreRetailer;
import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.branch.Branch;
import sjc.dissertation.retailer.branch.BranchAgent;
import sjc.dissertation.retailer.branch.BranchAgentFactory;
import sjc.dissertation.retailer.branch.CarnivoreBranchAgent;
import sjc.dissertation.retailer.branch.HerbivoreRetailer;
import sjc.dissertation.retailer.learn.GreedyAlgorithmFactory;
import sjc.dissertation.retailer.state.MaintainAlgorithm;
import sjc.dissertation.util.FileUtils;
import sjc.dissertation.util.TimedFileUtils;

public class ModelRunner {

	/** Location to save the files */
	static String PATH = "C:\\Users\\Stefa\\Desktop\\DissResults\\";

	/** Rounds to be played*/
	static final int ROUNDS = 1000;

	static final List<Retailer> retaiers = new ArrayList<Retailer>(4);

	public static void main(final String[] args){
		//Set the path for files
		genPATH();

		//Text Logger
		LoggerFactory.initiateLoggerFactory(PATH);
		final LoggerFactory wrapper = LoggerFactory.getSingleton();
		wrapper.getMasterLogger().setDisplayLevel(Level.Print);

		//Make Map
		final LocationFactory locFac = ConsumerFactory.getSingleton().getLocationFactory();
		locFac.addSettlement(4,  17, 30000);
		locFac.addSettlement(5,  5, 7000);
		locFac.addSettlement(9,  13, 80000);
		locFac.addSettlement(13, 8, 160000);
		locFac.addSettlement(16, 15, 50000);


		//Retailer Agents
		//		final List<RetailerAgent> retailers = makeRetailers(names, wrapper);
		final GreedyAlgorithmFactory greedy_factory = new GreedyAlgorithmFactory(wrapper.getMasterLogger(), ConsumerFactory.getSingleton().getLocationFactory().getTotalPopulation());
		final List<BranchAgent> branches = EXPERIMENT_1__1Carn_1Herb(wrapper, greedy_factory);

		//Data Loggers
		final String[] classes = ConsumerFactory.getSingleton().getSocialClasses();
		final VoteLogger voteLog = new VoteLogger(PATH, extractBranches(branches), classes);

		//Consumer Agents and Settlements
		final List<Consumer> consumers = makeConsumers(500, wrapper, voteLog);

		//Model
		final ModelController model = new ModelController(retaiers, branches, consumers, locFac.getTotalPopulation());


		//-----------------------------------------------------------------------------------------------------------
		//-----------------------------------------------------------------------------------------------------------
		//-----------------------------------------------------------------------------------------------------------
		//Time to play the game
		for(int round = 1; round <= ROUNDS; round++){
			model.performWeek();
			wrapper.getMasterLogger().print(String.format("Round %d Votes: %s"+
					"\n------------------------------------------------------------",
					round, Arrays.toString(voteLog.getCurrentRoundResults())));
			voteLog.startNextRound();
		}

		///---[ Results Section ]----
		//wrapper.getMasterLogger().log(ConsumerFactory.getSingleton().printResults());

		final FileUtils futils = new FileUtils(PATH);
		for (final PrintResultsInterface result : greedy_factory.getWrappedActionPredictors()){
			result.printResults(futils);
		}

		for (final Retailer retailer : retaiers){
			final WrappedRetailer w = (WrappedRetailer) retailer;
			w.printResults(futils);
		}


		for(final Consumer con : consumers){
			final WrappedConsumer voteHolder = (WrappedConsumer) con;
			voteHolder.printResults(futils);
		}

	}
	static List<BranchAgent> EXPERIMENT_1__1Carn_1Herb(final LoggerFactory wrapper, final GreedyAlgorithmFactory greedy_factory){
		final BranchAgentFactory agentFactory = BranchAgentFactory.getSingleton();
		final List<BranchAgent> agents = new ArrayList<>(2);
		final LocationFactory locFac = ConsumerFactory.getSingleton().getLocationFactory();

		final int total_branches = locFac.getNumberOfSettlements()*2;
		//Make Carn retailer
		final Retailer carn_retailer = wrapper.wrapRetailer(new CarnivoreRetailer("Carnivore"));

		//Make Herb retailer
		final Retailer herb_retailer = wrapper.wrapRetailer(new HerbivoreRetailer("Herbivore",
				wrapper.wrapAlgorithm(greedy_factory.createWrappedGreedyAlgorithm(total_branches-1))));


		//Create branch in each settlement
		for (int settlementId = 0; settlementId < locFac.getNumberOfSettlements(); settlementId++) {
			//Make Carnivore Branch
			final double[] carn_loc = locFac.generateLocationArroundSettlement(settlementId);
			final Branch carn_branch = wrapper.wrapBranch(carn_retailer.createBranch(carn_loc[0], carn_loc[1]));
			final CarnivoreBranchAgent carn_agent_1 = agentFactory.createNewCarnivoreAgent(carn_branch,
					wrapper.wrapAlgorithm(greedy_factory.createWrappedGreedyAlgorithm(total_branches-1)));
			agents.add(carn_agent_1);


			//Make Herbivore Branch
			final double[] herb_loc = locFac.generateLocationArroundSettlement(settlementId);
			final Branch herb_branch = wrapper.wrapBranch(herb_retailer.createBranch(herb_loc[0], herb_loc[1]));
			final BranchAgent herb_agent = agentFactory.createNewHerbivoreAgent(herb_branch);
			agents.add(herb_agent);
		}





		//------------------------------
		//Add the retailers (previously added branches)
		retaiers.add(carn_retailer);
		retaiers.add(herb_retailer);

		if(agents.size() != total_branches){
			throw new RuntimeException("The number of agents in the list("+agents.size()+") does not match the number intended ("+total_branches+")");
		}

		return agents;
	}

	static List<BranchAgent> TEST_1Control_1Carn(final LoggerFactory wrapper, final GreedyAlgorithmFactory greedy_factory){
		final BranchAgentFactory agentFactory = BranchAgentFactory.getSingleton();
		final List<BranchAgent> agents = new ArrayList<>(2);

		final int total_agents = 4;
		//------------------------------
		////Make Control Agent
		final Retailer control_retailer = wrapper.wrapRetailer(new CarnivoreRetailer("Control"));
		//Control Branch 1
		final Branch con_branch_1 = wrapper.wrapBranch(control_retailer.createBranch(10, 10));
		final CarnivoreBranchAgent con_agent_1 = agentFactory.createNewCarnivoreAgent(con_branch_1, wrapper.wrapAlgorithm(new MaintainAlgorithm()));
		agents.add(con_agent_1);

		//Control Branch 2
		final Branch con_branch_2 = wrapper.wrapBranch(control_retailer.createBranch(10, 10));
		final CarnivoreBranchAgent con_agent_2 = agentFactory.createNewCarnivoreAgent(con_branch_2, wrapper.wrapAlgorithm(new MaintainAlgorithm()));
		agents.add(con_agent_2);


		//------------------------------
		////Make Carnivore Agent
		final Retailer carn_retailer = wrapper.wrapRetailer(new CarnivoreRetailer("Carnivore"));
		//Carn Branch 1
		final Branch carn_branch_1 = wrapper.wrapBranch(carn_retailer.createBranch(10, 10));
		final CarnivoreBranchAgent carn_agent_1 = agentFactory.createNewCarnivoreAgent(carn_branch_1,
				wrapper.wrapAlgorithm(greedy_factory.createWrappedGreedyAlgorithm(total_agents-1)));
		agents.add(carn_agent_1);

		//Carn Branch 2
		final Branch carn_branch_2 = wrapper.wrapBranch(carn_retailer.createBranch(10, 10));
		final CarnivoreBranchAgent carn_agent_2 = agentFactory.createNewCarnivoreAgent(carn_branch_2,
				wrapper.wrapAlgorithm(greedy_factory.createWrappedGreedyAlgorithm(total_agents-1)));
		agents.add(carn_agent_2);

		//------------------------------
		//Add the retailers (previously added branches)
		retaiers.add(carn_retailer);
		retaiers.add(control_retailer);

		//Check to make sure no issues
		if(agents.size() != total_agents){
			throw new RuntimeException("The number of agents in the list("+agents.size()+") does not match the number intended ("+total_agents+")");
		}

		return agents;
	}

	static List<BranchAgent> TEST_1Control_1Herb(final LoggerFactory wrapper, final GreedyAlgorithmFactory greedy_factory){
		final BranchAgentFactory agentFactory = BranchAgentFactory.getSingleton();
		final List<BranchAgent> agents = new ArrayList<>(2);

		final int total_agents = 4;
		//------------------------------
		////Make Control Agent
		final Retailer control_retailer = wrapper.wrapRetailer(new CarnivoreRetailer("Control"));
		//Control Branch 1
		final Branch con_branch_1 = wrapper.wrapBranch(control_retailer.createBranch(10, 10));
		final CarnivoreBranchAgent con_agent_1 = agentFactory.createNewCarnivoreAgent(con_branch_1, wrapper.wrapAlgorithm(new MaintainAlgorithm()));
		agents.add(con_agent_1);

		//Control Branch 2
		final Branch con_branch_2 = wrapper.wrapBranch(control_retailer.createBranch(10, 10));
		final CarnivoreBranchAgent con_agent_2 = agentFactory.createNewCarnivoreAgent(con_branch_2, wrapper.wrapAlgorithm(new MaintainAlgorithm()));
		agents.add(con_agent_2);

		//------------------------------
		////Make Herbivore Agent
		final Retailer herb_retailer = wrapper.wrapRetailer(new HerbivoreRetailer("Herbivore", wrapper.wrapAlgorithm(greedy_factory.createWrappedGreedyAlgorithm(total_agents-1))));

		//Herbivore Branch 1
		final Branch herb_branch_1 = wrapper.wrapBranch(herb_retailer.createBranch(10, 10));
		final BranchAgent herb_agent_1 = agentFactory.createNewHerbivoreAgent(herb_branch_1);
		agents.add(herb_agent_1);

		//Herbivore Branch 2
		final Branch herb_branch_2 = wrapper.wrapBranch(herb_retailer.createBranch(10, 10));
		final BranchAgent herb_agent_2 = agentFactory.createNewHerbivoreAgent(herb_branch_2);
		agents.add(herb_agent_2);

		//------------------------------
		//Add the retailers (previously added branches)
		retaiers.add(herb_retailer);
		retaiers.add(control_retailer);

		//Check to make sure no issues
		if(agents.size() != total_agents){
			throw new RuntimeException("The number of agents in the list("+agents.size()+") does not match the number intended ("+total_agents+")");
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
	private static List<Branch> extractBranches(final List<BranchAgent> agents){
		final List<Branch> branches = new ArrayList<>(agents.size());
		for(final BranchAgent agent : agents){
			branches.add(agent.getBranch());
		}
		return branches;
	}

	private static void genPATH(){
		final String date =TimedFileUtils.getCurrentDate("test");

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
