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
import sjc.dissertation.model.logging.results.ResultCollator;
import sjc.dissertation.model.logging.votes.VoteLogger;
import sjc.dissertation.model.logging.wrappers.WrappedActionPredictor;
import sjc.dissertation.model.logging.wrappers.WrappedAlgorithm;
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
	public static final int ROUNDS = 161;

	static final List<Retailer> retailers = new ArrayList<Retailer>(4);
	static final List<WrappedAlgorithm> algos = new ArrayList<WrappedAlgorithm>(10);

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
		final List<BranchAgent> branches = EXPERIMENT_4__3Carn_3Herb(wrapper, greedy_factory);

		//Data Loggers
		final String[] classes = ConsumerFactory.getSingleton().getSocialClasses();
		final VoteLogger voteLog = new VoteLogger(PATH, extractBranches(branches), retailers, classes);

		//Consumer Agents and Settlements
		final List<Consumer> consumers = makeConsumers(2000, wrapper, voteLog);

		//Model
		final ModelController model = new ModelController(retailers, branches, consumers, locFac.getTotalPopulation());


		//-----------------------------------------------------------------------------------------------------------
		//-----------------------------------------------------------------------------------------------------------
		//-----------------------------------------------------------------------------------------------------------
		//Time to play the game
		for(int round = 1; round <= ROUNDS; round++){
			model.performWeek();
			wrapper.getMasterLogger().print(String.format("Round %d Retailer Votes: %s Votes: %s",
					round,
					Arrays.toString(voteLog.getCurrentRoundRetailerResults()),
					Arrays.toString(voteLog.getCurrentRoundBranchResults()))+
					"\n------------------------------------------------------------");
			voteLog.startNextRound();
		}
		wrapper.getMasterLogger().print("===[ Simulation Complete ]===");

		///---[ Results Section ]----
		//wrapper.getMasterLogger().logConsumerFactory.getSingleton().printResults());

		wrapper.getMasterLogger().print("--[ Action Predictors]--");
		final FileUtils futil = new FileUtils(PATH);
		for (final WrappedActionPredictor result : greedy_factory.getWrappedActionPredictors()){
			result.printResults(futil);
		}
		wrapper.getMasterLogger().print("--[ Action Predictors OVER ]--");
		wrapper.getMasterLogger().print("--[ Retailers]--");
		for (final Retailer retailer : retailers){
			final WrappedRetailer w = (WrappedRetailer) retailer;
			w.printResults(futil);
		}
		ResultCollator.generateRetailerCompare(futil, retailers);
		wrapper.getMasterLogger().print("--[ Retailers OVER ]--");

		wrapper.getMasterLogger().print("--[ Consumers]--");
		for(final Consumer con : consumers){
			final WrappedConsumer consumerVotes = (WrappedConsumer) con;
			consumerVotes.printResults(futil);
		}
		wrapper.getMasterLogger().print("--[ Consumers OVER ]--");
		wrapper.getMasterLogger().print("--[ Algorithms]--");
		for(final WrappedAlgorithm algo : algos){
			algo.printResults(futil);
		}
		ResultCollator.generateQualitiesCsv(futil, algos);
		wrapper.getMasterLogger().print("--[ Algorithms OVER ]--");

		wrapper.getMasterLogger().print("--[ Vote Logger ]--");
		voteLog.printResults(futil);
		wrapper.getMasterLogger().print("--[ Vote Logger OVER ]--");

		//End of simulation
		wrapper.getMasterLogger().print("===[ Results Complete ]===");
		wrapper.getMasterLogger().print(PATH);
	}
































	static List<BranchAgent> EXPERIMENT_1__1Carn_1Herb(final LoggerFactory wrapper, final GreedyAlgorithmFactory greedy_factory){
		final BranchAgentFactory agentFactory = BranchAgentFactory.getSingleton();
		final LocationFactory locFac = ConsumerFactory.getSingleton().getLocationFactory();
		final int total_branches = locFac.getNumberOfSettlements()*2;
		final List<BranchAgent> branch_agents = new ArrayList<>(total_branches);

		//Make Carn retailer
		final Retailer carn_retailer = wrapper.wrapRetailer(new CarnivoreRetailer("Carnivore"));

		//Make Herb retailer
		final WrappedAlgorithm herbAlgo = wrapper.wrapAlgorithm(
				greedy_factory.createWrappedGreedyAlgorithm(total_branches-1, "HerbRet"));
		algos.add(herbAlgo);
		final Retailer herb_retailer = wrapper.wrapRetailer(new HerbivoreRetailer("Herbivore", herbAlgo));


		//Create branch in each settlement
		for (int settlementId = 0; settlementId < locFac.getNumberOfSettlements(); settlementId++) {
			//Make Carnivore Branch
			final double[] carn_loc = locFac.generateLocationArroundSettlement(settlementId);
			final Branch carn_branch = wrapper.wrapBranch(carn_retailer.createBranch(carn_loc[0], carn_loc[1], settlementId));
			final WrappedAlgorithm carn_algo = wrapper.wrapAlgorithm(greedy_factory.createWrappedGreedyAlgorithm(total_branches-1, "Carn_"+settlementId));
			final CarnivoreBranchAgent carn_agent_1 = agentFactory.createNewCarnivoreAgent(carn_branch, carn_algo);
			branch_agents.add(carn_agent_1);
			algos.add(carn_algo);


			//Make Herbivore Branch
			final double[] herb_loc = locFac.generateLocationArroundSettlement(settlementId);
			final Branch herb_branch = wrapper.wrapBranch(herb_retailer.createBranch(herb_loc[0], herb_loc[1], settlementId));
			final BranchAgent herb_agent = agentFactory.createNewHerbivoreAgent(herb_branch);
			branch_agents.add(herb_agent);
		}





		//------------------------------
		//Add the retailers (previously added branches)
		retailers.add(carn_retailer);
		retailers.add(herb_retailer);

		if(branch_agents.size() != total_branches){
			throw new RuntimeException("The number of agents in the list("+branch_agents.size()+") does not match the number intended ("+total_branches+")");
		}

		return branch_agents;
	}

	static List<BranchAgent> EXPERIMENT_2__1Carn_3Herb(final LoggerFactory wrapper, final GreedyAlgorithmFactory greedy_factory){
		final BranchAgentFactory agentFactory = BranchAgentFactory.getSingleton();
		final LocationFactory locFac = ConsumerFactory.getSingleton().getLocationFactory();
		final int total_branches = locFac.getNumberOfSettlements()*4;
		final List<BranchAgent> branch_agents = new ArrayList<>(total_branches);

		//Make Carn retailer
		final Retailer carn_retailer = wrapper.wrapRetailer(new CarnivoreRetailer("Carnivore"));

		//Make Herb retailer A
		final WrappedAlgorithm herbAlgoA = wrapper.wrapAlgorithm(
				greedy_factory.createWrappedGreedyAlgorithm(total_branches-1, "HerbRet_A"));
		algos.add(herbAlgoA);
		final Retailer herb_retailerA = wrapper.wrapRetailer(new HerbivoreRetailer("Herbivore_A", herbAlgoA));

		//Make Herb retailer B
		final WrappedAlgorithm herbAlgoB = wrapper.wrapAlgorithm(
				greedy_factory.createWrappedGreedyAlgorithm(total_branches-1, "HerbRet_B"));
		algos.add(herbAlgoB);
		final Retailer herb_retailerB = wrapper.wrapRetailer(new HerbivoreRetailer("Herbivore_B", herbAlgoB));

		//Make Herb retailer C
		final WrappedAlgorithm herbAlgoC = wrapper.wrapAlgorithm(
				greedy_factory.createWrappedGreedyAlgorithm(total_branches-1, "HerbRet_C"));
		algos.add(herbAlgoC);
		final Retailer herb_retailerC = wrapper.wrapRetailer(new HerbivoreRetailer("Herbivore_C", herbAlgoC));


		//Create branch in each settlement
		for (int settlementId = 0; settlementId < locFac.getNumberOfSettlements(); settlementId++) {
			//Make Carnivore Branch
			final double[] carn_loc = locFac.generateLocationArroundSettlement(settlementId);
			final Branch carn_branch = wrapper.wrapBranch(carn_retailer.createBranch(carn_loc[0], carn_loc[1], settlementId));
			final WrappedAlgorithm carn_algo = wrapper.wrapAlgorithm(greedy_factory.createWrappedGreedyAlgorithm(total_branches-1, "Carn_"+settlementId));
			final CarnivoreBranchAgent carn_agent_1 = agentFactory.createNewCarnivoreAgent(carn_branch, carn_algo);
			branch_agents.add(carn_agent_1);
			algos.add(carn_algo);


			//Make Herbivore Branch
			final double[] herb_locA = locFac.generateLocationArroundSettlement(settlementId);
			final Branch herb_branchA = wrapper.wrapBranch(herb_retailerA.createBranch(herb_locA[0], herb_locA[1], settlementId));
			final BranchAgent herb_agentA = agentFactory.createNewHerbivoreAgent(herb_branchA);
			branch_agents.add(herb_agentA);


			//Make Herbivore Branch
			final double[] herb_locB = locFac.generateLocationArroundSettlement(settlementId);
			final Branch herb_branchB = wrapper.wrapBranch(herb_retailerB.createBranch(herb_locB[0], herb_locB[1], settlementId));
			final BranchAgent herb_agentB = agentFactory.createNewHerbivoreAgent(herb_branchB);
			branch_agents.add(herb_agentB);


			//Make Herbivore Branch
			final double[] herb_locC = locFac.generateLocationArroundSettlement(settlementId);
			final Branch herb_branchC = wrapper.wrapBranch(herb_retailerC.createBranch(herb_locC[0], herb_locC[1], settlementId));
			final BranchAgent herb_agentC = agentFactory.createNewHerbivoreAgent(herb_branchC);
			branch_agents.add(herb_agentC);

		}

		//------------------------------
		//Add the retailers (previously added branches)
		retailers.add(carn_retailer);
		retailers.add(herb_retailerA);
		retailers.add(herb_retailerB);
		retailers.add(herb_retailerC);

		if(branch_agents.size() != total_branches){
			throw new RuntimeException("The number of agents in the list("+branch_agents.size()+") does not match the number intended ("+total_branches+")");
		}

		return branch_agents;
	}

	static List<BranchAgent> EXPERIMENT_3__3Carn_1Herb(final LoggerFactory wrapper, final GreedyAlgorithmFactory greedy_factory){
		final BranchAgentFactory agentFactory = BranchAgentFactory.getSingleton();
		final LocationFactory locFac = ConsumerFactory.getSingleton().getLocationFactory();
		final int total_branches = locFac.getNumberOfSettlements()*4;
		final List<BranchAgent> branch_agents = new ArrayList<>(total_branches);

		//Make Carn retailer
		final Retailer carn_retailerA = wrapper.wrapRetailer(new CarnivoreRetailer("Carnivore_A"));
		final Retailer carn_retailerB = wrapper.wrapRetailer(new CarnivoreRetailer("Carnivore_B"));
		final Retailer carn_retailerC = wrapper.wrapRetailer(new CarnivoreRetailer("Carnivore_C"));

		//Make Herb retailer
		final WrappedAlgorithm herbAlgo = wrapper.wrapAlgorithm(
				greedy_factory.createWrappedGreedyAlgorithm(total_branches-1, "HerbRet"));
		algos.add(herbAlgo);
		final Retailer herb_retailer = wrapper.wrapRetailer(new HerbivoreRetailer("Herbivore", herbAlgo));



		//Create branch in each settlement
		for (int settlementId = 0; settlementId < locFac.getNumberOfSettlements(); settlementId++) {
			//Make Carnivore Branch A
			final double[] carn_locA = locFac.generateLocationArroundSettlement(settlementId);
			final Branch carn_branchA = wrapper.wrapBranch(carn_retailerA.createBranch(carn_locA[0], carn_locA[1], settlementId));
			final WrappedAlgorithm carn_algoA = wrapper.wrapAlgorithm(greedy_factory.createWrappedGreedyAlgorithm(total_branches-1, "Carn_"+settlementId));
			final CarnivoreBranchAgent carn_agent_A = agentFactory.createNewCarnivoreAgent(carn_branchA, carn_algoA);
			branch_agents.add(carn_agent_A);
			algos.add(carn_algoA);


			//Make Carnivore Branch B
			final double[] carn_locB = locFac.generateLocationArroundSettlement(settlementId);
			final Branch carn_branchB = wrapper.wrapBranch(carn_retailerB.createBranch(carn_locB[0], carn_locB[1], settlementId));
			final WrappedAlgorithm carn_algoB = wrapper.wrapAlgorithm(greedy_factory.createWrappedGreedyAlgorithm(total_branches-1, "Carn_"+settlementId));
			final CarnivoreBranchAgent carn_agent_B = agentFactory.createNewCarnivoreAgent(carn_branchB, carn_algoB);
			branch_agents.add(carn_agent_B);
			algos.add(carn_algoB);

			//Make Carnivore Branch C
			final double[] carn_locC = locFac.generateLocationArroundSettlement(settlementId);
			final Branch carn_branchC = wrapper.wrapBranch(carn_retailerC.createBranch(carn_locC[0], carn_locC[1], settlementId));
			final WrappedAlgorithm carn_algoC = wrapper.wrapAlgorithm(greedy_factory.createWrappedGreedyAlgorithm(total_branches-1, "Carn_"+settlementId));
			final CarnivoreBranchAgent carn_agent_C = agentFactory.createNewCarnivoreAgent(carn_branchC, carn_algoC);
			branch_agents.add(carn_agent_C);
			algos.add(carn_algoC);


			//Make Herbivore Branch
			final double[] herb_locA = locFac.generateLocationArroundSettlement(settlementId);
			final Branch herb_branchA = wrapper.wrapBranch(herb_retailer.createBranch(herb_locA[0], herb_locA[1], settlementId));
			final BranchAgent herb_agentA = agentFactory.createNewHerbivoreAgent(herb_branchA);
			branch_agents.add(herb_agentA);
		}

		//------------------------------
		//Add the retailers (previously added branches)
		retailers.add(herb_retailer);
		retailers.add(carn_retailerA);
		retailers.add(carn_retailerB);
		retailers.add(carn_retailerC);

		if(branch_agents.size() != total_branches){
			throw new RuntimeException("The number of agents in the list("+branch_agents.size()+") does not match the number intended ("+total_branches+")");
		}

		return branch_agents;
	}

	static List<BranchAgent> EXPERIMENT_4__3Carn_3Herb(final LoggerFactory wrapper, final GreedyAlgorithmFactory greedy_factory){
		final BranchAgentFactory agentFactory = BranchAgentFactory.getSingleton();
		final LocationFactory locFac = ConsumerFactory.getSingleton().getLocationFactory();
		final int total_branches = locFac.getNumberOfSettlements()*6;
		final List<BranchAgent> branch_agents = new ArrayList<>(total_branches);

		//Make Carn retailer
		final Retailer carn_retailerA = wrapper.wrapRetailer(new CarnivoreRetailer("Carnivore_A"));
		final Retailer carn_retailerB = wrapper.wrapRetailer(new CarnivoreRetailer("Carnivore_B"));
		final Retailer carn_retailerC = wrapper.wrapRetailer(new CarnivoreRetailer("Carnivore_C"));

		//Make Herb retailer A
		final WrappedAlgorithm herbAlgoA = wrapper.wrapAlgorithm(
				greedy_factory.createWrappedGreedyAlgorithm(total_branches-1, "HerbRet_A"));
		algos.add(herbAlgoA);
		final Retailer herb_retailerA = wrapper.wrapRetailer(new HerbivoreRetailer("Herbivore_A", herbAlgoA));

		//Make Herb retailer B
		final WrappedAlgorithm herbAlgoB = wrapper.wrapAlgorithm(
				greedy_factory.createWrappedGreedyAlgorithm(total_branches-1, "HerbRet_B"));
		algos.add(herbAlgoB);
		final Retailer herb_retailerB = wrapper.wrapRetailer(new HerbivoreRetailer("Herbivore_B", herbAlgoB));

		//Make Herb retailer C
		final WrappedAlgorithm herbAlgoC = wrapper.wrapAlgorithm(
				greedy_factory.createWrappedGreedyAlgorithm(total_branches-1, "HerbRet_C"));
		algos.add(herbAlgoC);
		final Retailer herb_retailerC = wrapper.wrapRetailer(new HerbivoreRetailer("Herbivore_C", herbAlgoC));



		//Create branch in each settlement
		for (int settlementId = 0; settlementId < locFac.getNumberOfSettlements(); settlementId++) {
			//Make Carnivore Branch A
			final double[] carn_locA = locFac.generateLocationArroundSettlement(settlementId);
			final Branch carn_branchA = wrapper.wrapBranch(carn_retailerA.createBranch(carn_locA[0], carn_locA[1], settlementId));
			final WrappedAlgorithm carn_algoA = wrapper.wrapAlgorithm(greedy_factory.createWrappedGreedyAlgorithm(total_branches-1, "Carn_"+settlementId));
			final CarnivoreBranchAgent carn_agent_A = agentFactory.createNewCarnivoreAgent(carn_branchA, carn_algoA);
			branch_agents.add(carn_agent_A);
			algos.add(carn_algoA);


			//Make Carnivore Branch B
			final double[] carn_locB = locFac.generateLocationArroundSettlement(settlementId);
			final Branch carn_branchB = wrapper.wrapBranch(carn_retailerB.createBranch(carn_locB[0], carn_locB[1], settlementId));
			final WrappedAlgorithm carn_algoB = wrapper.wrapAlgorithm(greedy_factory.createWrappedGreedyAlgorithm(total_branches-1, "Carn_"+settlementId));
			final CarnivoreBranchAgent carn_agent_B = agentFactory.createNewCarnivoreAgent(carn_branchB, carn_algoB);
			branch_agents.add(carn_agent_B);
			algos.add(carn_algoB);

			//Make Carnivore Branch C
			final double[] carn_locC = locFac.generateLocationArroundSettlement(settlementId);
			final Branch carn_branchC = wrapper.wrapBranch(carn_retailerC.createBranch(carn_locC[0], carn_locC[1], settlementId));
			final WrappedAlgorithm carn_algoC = wrapper.wrapAlgorithm(greedy_factory.createWrappedGreedyAlgorithm(total_branches-1, "Carn_"+settlementId));
			final CarnivoreBranchAgent carn_agent_C = agentFactory.createNewCarnivoreAgent(carn_branchC, carn_algoC);
			branch_agents.add(carn_agent_C);
			algos.add(carn_algoC);


			//Make Herbivore Branch
			final double[] herb_locA = locFac.generateLocationArroundSettlement(settlementId);
			final Branch herb_branchA = wrapper.wrapBranch(herb_retailerA.createBranch(herb_locA[0], herb_locA[1], settlementId));
			final BranchAgent herb_agentA = agentFactory.createNewHerbivoreAgent(herb_branchA);
			branch_agents.add(herb_agentA);


			//Make Herbivore Branch
			final double[] herb_locB = locFac.generateLocationArroundSettlement(settlementId);
			final Branch herb_branchB = wrapper.wrapBranch(herb_retailerB.createBranch(herb_locB[0], herb_locB[1], settlementId));
			final BranchAgent herb_agentB = agentFactory.createNewHerbivoreAgent(herb_branchB);
			branch_agents.add(herb_agentB);


			//Make Herbivore Branch
			final double[] herb_locC = locFac.generateLocationArroundSettlement(settlementId);
			final Branch herb_branchC = wrapper.wrapBranch(herb_retailerC.createBranch(herb_locC[0], herb_locC[1], settlementId));
			final BranchAgent herb_agentC = agentFactory.createNewHerbivoreAgent(herb_branchC);
			branch_agents.add(herb_agentC);
		}

		//------------------------------
		//Add the retailers (previously added branches)
		retailers.add(herb_retailerA);
		retailers.add(herb_retailerB);
		retailers.add(herb_retailerC);
		retailers.add(carn_retailerA);
		retailers.add(carn_retailerB);
		retailers.add(carn_retailerC);

		if(branch_agents.size() != total_branches){
			throw new RuntimeException("The number of agents in the list("+branch_agents.size()+") does not match the number intended ("+total_branches+")");
		}

		return branch_agents;
	}



	static List<BranchAgent> TEST_1Control_1Carn(final LoggerFactory wrapper, final GreedyAlgorithmFactory greedy_factory){
		final BranchAgentFactory agentFactory = BranchAgentFactory.getSingleton();
		final List<BranchAgent> agents = new ArrayList<>(2);

		final int total_agents = 4;
		//------------------------------
		////Make Control Agent
		final Retailer control_retailer = wrapper.wrapRetailer(new CarnivoreRetailer("Control"));
		//Control Branch 1
		final Branch con_branch_1 = wrapper.wrapBranch(control_retailer.createBranch(10, 10, 0));
		final CarnivoreBranchAgent con_agent_1 = agentFactory.createNewCarnivoreAgent(con_branch_1, wrapper.wrapAlgorithm(new MaintainAlgorithm()));
		agents.add(con_agent_1);

		//Control Branch 2
		final Branch con_branch_2 = wrapper.wrapBranch(control_retailer.createBranch(10, 10, 0));
		final CarnivoreBranchAgent con_agent_2 = agentFactory.createNewCarnivoreAgent(con_branch_2, wrapper.wrapAlgorithm(new MaintainAlgorithm()));
		agents.add(con_agent_2);


		//------------------------------
		////Make Carnivore Agent
		final Retailer carn_retailer = wrapper.wrapRetailer(new CarnivoreRetailer("Carnivore"));
		//Carn Branch 1
		final Branch carn_branch_1 = wrapper.wrapBranch(carn_retailer.createBranch(10, 10, 0));
		final CarnivoreBranchAgent carn_agent_1 = agentFactory.createNewCarnivoreAgent(carn_branch_1,
				wrapper.wrapAlgorithm(greedy_factory.createWrappedGreedyAlgorithm(total_agents-1, "Carn1")));
		agents.add(carn_agent_1);

		//Carn Branch 2
		final Branch carn_branch_2 = wrapper.wrapBranch(carn_retailer.createBranch(10, 10, 0));
		final CarnivoreBranchAgent carn_agent_2 = agentFactory.createNewCarnivoreAgent(carn_branch_2,
				wrapper.wrapAlgorithm(greedy_factory.createWrappedGreedyAlgorithm(total_agents-1, "Carn2")));
		agents.add(carn_agent_2);

		//------------------------------
		//Add the retailers (previously added branches)
		retailers.add(carn_retailer);
		retailers.add(control_retailer);

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
		final Branch con_branch_1 = wrapper.wrapBranch(control_retailer.createBranch(10, 10, 0));
		final CarnivoreBranchAgent con_agent_1 = agentFactory.createNewCarnivoreAgent(con_branch_1, wrapper.wrapAlgorithm(new MaintainAlgorithm()));
		agents.add(con_agent_1);

		//Control Branch 2
		final Branch con_branch_2 = wrapper.wrapBranch(control_retailer.createBranch(10, 10, 0));
		final CarnivoreBranchAgent con_agent_2 = agentFactory.createNewCarnivoreAgent(con_branch_2, wrapper.wrapAlgorithm(new MaintainAlgorithm()));
		agents.add(con_agent_2);

		//------------------------------
		////Make Herbivore Agent
		final Retailer herb_retailer = wrapper.wrapRetailer(new HerbivoreRetailer("Herbivore", wrapper.wrapAlgorithm(greedy_factory.createWrappedGreedyAlgorithm(total_agents-1, "Herb"))));

		//Herbivore Branch 1
		final Branch herb_branch_1 = wrapper.wrapBranch(herb_retailer.createBranch(10, 10, 0));
		final BranchAgent herb_agent_1 = agentFactory.createNewHerbivoreAgent(herb_branch_1);
		agents.add(herb_agent_1);

		//Herbivore Branch 2
		final Branch herb_branch_2 = wrapper.wrapBranch(herb_retailer.createBranch(10, 10, 0));
		final BranchAgent herb_agent_2 = agentFactory.createNewHerbivoreAgent(herb_branch_2);
		agents.add(herb_agent_2);

		//------------------------------
		//Add the retailers (previously added branches)
		retailers.add(herb_retailer);
		retailers.add(control_retailer);

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
