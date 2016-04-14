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
import sjc.dissertation.retailer.branch.BranchAgent;
import sjc.dissertation.retailer.branch.BranchAgentFactory;
import sjc.dissertation.retailer.branch.CarnivoreBranchAgent;
import sjc.dissertation.retailer.branch.HerbivoreRetailer;
import sjc.dissertation.retailer.learn.GreedyAlgorithmFactory;
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
		final List<BranchAgent> branches = TEST_1Control_1Herb(wrapper);

		//Data Loggers
		final VoteLogger voteLog = new VoteLogger(PATH, extractBranches(branches), classes);

		//Consumer Agents
		final List<Consumer> consumers = makeConsumers(100, wrapper, voteLog);

		//Model
		final ModelController model = new ModelController(retaiers, branches, consumers, UK_POPULATION);

		//Time to play the game
		for(int round = 1; round <= ROUNDS; round++){
			model.performWeek();
			wrapper.getMasterLogger().print(String.format("Round %d Votes: %s"+
					"\n------------------------------------------------------------",
					round, Arrays.toString(voteLog.getCurrentRoundResults())));
			voteLog.startNextRound();

		}

	}
	static List<BranchAgent> TEST_1Control_1Carn(final LoggerFactory wrapper){
		final BranchAgentFactory agentFactory = BranchAgentFactory.getSingleton();
		final List<BranchAgent> agents = new ArrayList<>(2);

		final int total_agents = 6;
		//------------------------------
		////Make Control Agent
		final Retailer control_retailer = wrapper.wrapRetailer(new CarnivoreRetailer("Control"));
		//Control Branch 1
		final Branch con_branch_1 = wrapper.wrapBranch(control_retailer.createBranch(0, 0));
		final CarnivoreBranchAgent con_agent_1 = agentFactory.createNewCarnivoreAgent(con_branch_1, wrapper.wrapAlgorithm(new MaintainAlgorithm()));
		agents.add(con_agent_1);

		//Control Branch 1
		final Branch con_branch_2 = wrapper.wrapBranch(control_retailer.createBranch(0, 0));
		final CarnivoreBranchAgent con_agent_2 = agentFactory.createNewCarnivoreAgent(con_branch_2, wrapper.wrapAlgorithm(new MaintainAlgorithm()));
		agents.add(con_agent_2);

		//Control Branch 1
		final Branch con_branch_3 = wrapper.wrapBranch(control_retailer.createBranch(0, 0));
		final CarnivoreBranchAgent con_agent_3 = agentFactory.createNewCarnivoreAgent(con_branch_3, wrapper.wrapAlgorithm(new MaintainAlgorithm()));
		agents.add(con_agent_3);


		//------------------------------
		////Make Carnivore Agent
		final GreedyAlgorithmFactory greedy_factory = new GreedyAlgorithmFactory(wrapper.getMasterLogger(), UK_POPULATION);
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

		//Carn Branch 3
		final Branch carn_branch_3 = wrapper.wrapBranch(carn_retailer.createBranch(10, 10));
		final CarnivoreBranchAgent carn_agent_3 = agentFactory.createNewCarnivoreAgent(carn_branch_3,
				wrapper.wrapAlgorithm(greedy_factory.createWrappedGreedyAlgorithm(total_agents-1)));
		agents.add(carn_agent_3);

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

	static List<BranchAgent> TEST_1Control_1Herb(final LoggerFactory wrapper){
		final BranchAgentFactory agentFactory = BranchAgentFactory.getSingleton();
		final List<BranchAgent> agents = new ArrayList<>(2);

		final int total_agents = 6;
		//------------------------------
		////Make Control Agent
		final Retailer control_retailer = wrapper.wrapRetailer(new CarnivoreRetailer("Control"));
		//Control Branch 1
		final Branch con_branch_1 = wrapper.wrapBranch(control_retailer.createBranch(0, 0));
		final CarnivoreBranchAgent con_agent_1 = agentFactory.createNewCarnivoreAgent(con_branch_1, wrapper.wrapAlgorithm(new MaintainAlgorithm()));
		agents.add(con_agent_1);

		//Control Branch 1
		final Branch con_branch_2 = wrapper.wrapBranch(control_retailer.createBranch(0, 0));
		final CarnivoreBranchAgent con_agent_2 = agentFactory.createNewCarnivoreAgent(con_branch_2, wrapper.wrapAlgorithm(new MaintainAlgorithm()));
		agents.add(con_agent_2);

		//Control Branch 1
		final Branch con_branch_3 = wrapper.wrapBranch(control_retailer.createBranch(0, 0));
		final CarnivoreBranchAgent con_agent_3 = agentFactory.createNewCarnivoreAgent(con_branch_3, wrapper.wrapAlgorithm(new MaintainAlgorithm()));
		agents.add(con_agent_3);


		//------------------------------
		////Make Herbivore Agent
		final GreedyAlgorithmFactory greedy_factory = new GreedyAlgorithmFactory(wrapper.getMasterLogger(), UK_POPULATION);
		final Retailer herb_retailer = wrapper.wrapRetailer(new HerbivoreRetailer("Herbivore", wrapper.wrapAlgorithm(greedy_factory.createWrappedGreedyAlgorithm(total_agents-1))));

		//Herbivore Branch 1
		final Branch herb_branch_1 = wrapper.wrapBranch(herb_retailer.createBranch(10, 10));
		final BranchAgent herb_agent_1 = agentFactory.createNewHerbivoreAgent(herb_branch_1);
		agents.add(herb_agent_1);

		//Herbivore Branch 2
		final Branch herb_branch_2 = wrapper.wrapBranch(herb_retailer.createBranch(10, 10));
		final BranchAgent herb_agent_2 = agentFactory.createNewHerbivoreAgent(herb_branch_2);
		agents.add(herb_agent_2);

		//Herbivore Branch 3
		final Branch herb_branch_3 = wrapper.wrapBranch(herb_retailer.createBranch(10, 10));
		final BranchAgent herb_agent_3 = agentFactory.createNewHerbivoreAgent(herb_branch_3);
		agents.add(herb_agent_3);

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
