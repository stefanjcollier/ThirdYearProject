package sjc.dissertation.model.logging.votes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sjc.dissertation.consumer.Consumer;
import sjc.dissertation.retailer.Retailer;

/**
 * A central place to store the history of votes in the simulation.
 *
 * 	Key control is to start a round.
 * 	Get all votes (Storing social class and retailer choice)
 *  Save round to disk
 *
 * @author Stefan Collier
 *
 */
public class VoteLogger {

	/** A mapping of round -> (social class x Votes for retailer)*/
	private Map<Integer, int[][]> roundScores;

	/** A mapping of String social class -> position in the range value of roundScores */
	private Map<String, Integer> classToIndex;

	/** A mapping of Retailer -> position in the range value of roundScores */
	private Map<Retailer, Integer> retailerToIndex;
	private int currentRound;

	private final VoteFileWriter files;

	public VoteLogger(final String filePath, final List<Retailer> retailers, final String[] classes){
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
	 */
	private static Map<Retailer, Integer> generateRetailerIds(final List<Retailer> retailers){
		final Map<Retailer, Integer> mappings = new HashMap<>(retailers.size());
		int id = 0;
		for(final Retailer retailer : retailers){
			mappings.put(retailer, id);
			id++;
		}
		return mappings;
	}


	public void addVote(final Consumer consumer, final Retailer retailer){
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




}
