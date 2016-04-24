package sjc.dissertation.model.logging.votes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sjc.dissertation.consumer.Consumer;
import sjc.dissertation.model.logging.results.PrintResultsInterface;
import sjc.dissertation.model.logging.wrappers.WrappedRetailer;
import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.branch.Branch;
import sjc.dissertation.util.FileUtils;

/**
 * A central place to store the history of votes in the simulation.
 *
 * 	Key control is to start a round.
 * 	Get all votes (Storing social class and branch choice)
 *  Save each round to file
 *
 *
 *
 * @author Stefan Collier
 *
 */
public class VoteLogger implements PrintResultsInterface{

	/** A mapping of round -> (social class x Votes for branch)*/
	private final Map<Integer, int[][]> branch_roundScores;
	private final Map<Integer, int[][]> retailer_roundScores;

	/** A mapping of String social class -> position in the range value of roundScores */
	private Map<String, Integer> classToIndex;

	/** A mapping of Branch -> position in the range value of roundScores
	 * The last mapping is null->(int) to represent the option of not voting */
	private Map<Branch, Integer> branchToIndex;

	private Map<Retailer, Integer> retailerToIndex;

	private int currentRound;

	private final VoteFileWriter files;

	public VoteLogger(final String filePath, final List<Branch> branches, final List<Retailer> retailers, final String[] classes){
		//Populate index converters
		this.classToIndex = generateClassIds(classes);
		this.branchToIndex = generateBranchIds(branches);
		this.retailerToIndex = generateRetailerIds(retailers);

		//Create table for first round
		this.branch_roundScores = new HashMap<Integer, int[][]>(100);
		this.retailer_roundScores= new HashMap<Integer, int[][]>(100);

		this.currentRound = -1;
		this.incrementRound();

		//Create file stores
		this.files = new VoteFileWriter(filePath, classes, branches);
	}


	private Map<Retailer, Integer> generateRetailerIds(final List<Retailer> retailers) {
		final Map<Retailer, Integer> mappings = new HashMap<>(retailers.size());
		int id = 0;
		for(Retailer retailer : retailers){
			try{
				final WrappedRetailer wrapper = (WrappedRetailer) retailer;
				retailer = wrapper.getTrueRetailer();
			}catch(final Throwable e){}
			mappings.put(retailer, id);
			id++;
		}
		mappings.put(null, retailers.size());
		return mappings;
	}


	/**
	 * Adds a local id to each of the classes, that allow further use of social classes to
	 * 	reference the correct array index.
	 */
	private static Map<String, Integer> generateClassIds(final String[] classes) {
		final Map<String, Integer> mappings = new HashMap<>(classes.length);
		int id = 0;
		for(final String socClass : classes){
			mappings.put(socClass, id);
			id++;
		}
		return mappings;
	}

	/**
	 * Adds a local id to each of the branches, that allow further use of branches to
	 * 	reference the correct array index.
	 *
	 * Note: That there is an index for when the consumer makes a no vote (they choose no branch)
	 */
	private static Map<Branch, Integer> generateBranchIds(final List<Branch> branches){
		final Map<Branch, Integer> mappings = new HashMap<>(branches.size()+1);
		int id = 0;
		for(final Branch branch : branches){
			mappings.put(branch, id);
			id++;
		}
		mappings.put(null, branches.size());
		return mappings;
	}


	public void addVote(final Consumer consumer, final Branch branch){
		final int socIndex = this.classToIndex.get(consumer.getSocialClass());
		final int branchIndex = this.branchToIndex.get(branch);
		final Retailer retailer  = (branch!=null)?branch.getRetailer():null;
		final int reatilerIndex = this.retailerToIndex.get(retailer);

		this.branch_roundScores.get(this.currentRound)[socIndex][branchIndex]++;
		this.retailer_roundScores.get(this.currentRound)[socIndex][reatilerIndex]++;
	}


	public void startNextRound(){
		storeDataForCurrentRound();
		incrementRound();
	}

	private void incrementRound(){
		this.currentRound++;
		final int numOfClasses = this.classToIndex.size();
		final int numOfBranches = this.branchToIndex.size();
		final int numOfRetailers = this.retailerToIndex.size();

		this.branch_roundScores.put(this.currentRound, new int[numOfClasses][numOfBranches]);
		this.retailer_roundScores.put(this.currentRound, new int[numOfClasses][numOfRetailers]);
	}

	/** Store this round scores to file*/
	private void storeDataForCurrentRound() {
		final int[][] currentScores = this.branch_roundScores.get(this.currentRound);
		this.files.writeVotesToFile(currentScores);
	}

	public int[] getCurrentRoundBranchResults(){
		return this.getRoundBranchResults(this.currentRound);
	}
	public int[] getCurrentRoundRetailerResults(){
		return this.getRoundRetailerResults(this.currentRound);
	}
	public int[] getRoundBranchResults(final int round){
		final int noOfBranches = this.branchToIndex.size();
		final int noOfClasses = this.classToIndex.size();

		final int[] branchVotes= new int[noOfBranches];

		//Cycle through socialClasses
		for (int socClass = 0; socClass < noOfClasses; socClass++){
			for (int branch = 0; branch < noOfBranches; branch++){
				branchVotes[branch] += this.branch_roundScores.get(round)[socClass][branch];
			}
		}
		return branchVotes;
	}

	public int[] getRoundRetailerResults(final int round){
		final int noOfRetailer = this.retailerToIndex.size();
		final int noOfClasses = this.classToIndex.size();

		final int[] retailerVote= new int[noOfRetailer];

		//Cycle through socialClasses
		for (int socClass = 0; socClass < noOfClasses; socClass++){
			for (int retailer = 0; retailer < noOfRetailer; retailer++){
				retailerVote[retailer] += this.retailer_roundScores.get(round)[socClass][retailer];
			}
		}
		return retailerVote;
	}


	@Override
	public String printResults(final FileUtils futil) {
		final String dir = "retailers/";
		futil.makeFolder(dir);

		//Do retailer votes
		final String ret_location = dir+"RetailerVotes.csv";
		String votes  = "";
		for (int index = 0; index < this.retailerToIndex.keySet().size(); index++){
			for (final Retailer re : this.retailerToIndex.keySet()) {
				if(this.retailerToIndex.get(re)==index){
					if(re == null){
						votes += "No Vote,";

					}else{
						votes += re.getName()+",";
					}
				}
			}
		}
		votes = votes.substring(0, votes.length()-1)+System.lineSeparator();
		for (int round = 0; round < this.currentRound; round++) {
			votes += Arrays.toString(getRoundRetailerResults(round)).
					replace("[", "").replace("]", "").replace(" ", "")+System.lineSeparator();
		}
		futil.writeStringToFile(votes, ret_location);

		//Do branch votes
		final String branch_location = dir+"BranchVotes.csv";
		String b_votes  = "";
		for (int index = 0; index < this.branchToIndex.keySet().size(); index++){
			for(final Branch b : this.branchToIndex.keySet()){
				if(this.branchToIndex.get(b) == index) {
					if(b == null){
						b_votes += "No Vote,";
					}else{
						b_votes += b.getBranchName()+",";
					}
				}
			}
		}
		b_votes = b_votes.substring(0, b_votes.length()-1)+System.lineSeparator();
		for (int round = 0; round < this.currentRound; round++) {
			b_votes += Arrays.toString(getRoundBranchResults(round)).
					replace("[", "").replace("]", "").replace(" ", "")+System.lineSeparator();
		}
		futil.writeStringToFile(b_votes, branch_location);

		return "Got nothing sorry";

	}



}
