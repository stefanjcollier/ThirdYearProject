package sjc.dissertation.model.logging.votes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sjc.dissertation.consumer.Consumer;
import sjc.dissertation.retailer.RetailBranch;

/**
 * A central place to store the history of votes in the simulation.
 *
 * 	Key control is to start a round.
 * 	Get all votes (Storing social class and retailer choice)
 *  Save each round to file
 *
 *
 *
 * @author Stefan Collier
 *
 */
public class VoteLogger {

	/** A mapping of round -> (social class x Votes for retailer)*/
	private Map<Integer, int[][]> roundScores;

	/** A mapping of String social class -> position in the range value of roundScores */
	private Map<String, Integer> classToIndex;

	/** A mapping of Retailer -> position in the range value of roundScores
	 * The last mapping is null->(int) to represent the option of not voting */
	private Map<RetailBranch, Integer> retailerToIndex;
	private int currentRound;

	private final VoteFileWriter files;

	public VoteLogger(final String filePath, final List<RetailBranch> retailers, final String[] classes){
		//Populate index converters
		this.classToIndex = generateClassIds(classes);
		this.retailerToIndex = generateRetailerIds(retailers);

		//Create table for first round
		this.roundScores = new HashMap<Integer, int[][]>(100);
		this.currentRound = -1;
		this.incrementRound();

		//Create file stores
		this.files = new VoteFileWriter(filePath, classes, retailers.size());
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
	 * Adds a local id to each of the retailers, that allow further use of retailers to
	 * 	reference the correct array index.
	 *
	 * Note: That there is an index for when the consumer makes a no vote (they choose no retailer)
	 */
	private static Map<RetailBranch, Integer> generateRetailerIds(final List<RetailBranch> retailers){
		final Map<RetailBranch, Integer> mappings = new HashMap<>(retailers.size()+1);
		int id = 0;
		for(final RetailBranch retailer : retailers){
			mappings.put(retailer, id);
			id++;
		}
		mappings.put(null, retailers.size());
		return mappings;
	}


	public void addVote(final Consumer consumer, final RetailBranch retailer){
		final int socIndex = this.classToIndex.get(consumer.getSocialClass());
		final int retIndex = this.retailerToIndex.get(retailer);

		this.roundScores.get(this.currentRound)[socIndex][retIndex]++;
	}


	public void startNextRound(){
		storeDataForCurrentRound();
		incrementRound();
	}

	private void incrementRound(){
		this.currentRound++;
		final int numOfClasses = this.classToIndex.size();
		final int numOfRetailers = this.retailerToIndex.size();
		this.roundScores.put(this.currentRound, new int[numOfClasses][numOfRetailers]);
	}

	/** Store this round scores to file*/
	private void storeDataForCurrentRound() {
		final int[][] currentScores = this.roundScores.get(this.currentRound);
		this.files.writeVotesToFile(currentScores);
	}

	public int[] getCurrentRoundResults(){
		final int noOfRetailers = this.retailerToIndex.size();
		final int noOfClasses = this.classToIndex.size();

		final int[] retailerVotes= new int[noOfRetailers];

		//Cycle through socialClasses
		for (int socClass = 0; socClass < noOfClasses; socClass++){
			for (int retailer = 0; retailer < noOfRetailers; retailer++){
				retailerVotes[retailer] += this.roundScores.get(this.currentRound)[socClass][retailer];
			}
		}
		return retailerVotes;
	}

}
