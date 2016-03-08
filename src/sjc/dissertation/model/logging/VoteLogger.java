package sjc.dissertation.model.logging;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sjc.dissertation.retailer.Retailer;

/**
 * A central place to store the history of votes in the simulation
 *
 * @author Stefan Collier
 *
 */
public class VoteLogger {

	/** A mapping of round -> (social class x Votes for retailer)*/
	private Map<Integer, int[][]> roundScores;

	/** A mapping of String social class -> position in the range value of roundScores */
	private Map<String, Integer> classToIndex;

	/** A mapping of Reatiler -> position in the range value of roundScores */
	private Map<Retailer, Integer> retailerToIndex;
	private int currentRound;

	public VoteLogger(final Retailer[] retailers, final List<String> classes){
		this.roundScores = new HashMap<Integer, int[][]>(100);
		this.classToIndex = generateClassIds(classes);
		this.retailerToIndex = generateRetailerIds(retailers);
		this.currentRound = -1;
		this.startNextRound();
	}

	private static Map<String, Integer> generateClassIds(final List<String> classes) {
		final Map<String, Integer> mappings = new HashMap<>(classes.size());
		int id = 0;
		for(final String socClass : classes){
			mappings.put(socClass, id);
			id++;
		}
		return mappings;
	}

	private static Map<Retailer, Integer> generateRetailerIds(final Retailer[] retailers){
		final Map<Retailer, Integer> mappings = new HashMap<>(retailers.length);
		int id = 0;
		for(final Retailer retailer : retailers){
			mappings.put(retailer, id);
			id++;
		}
		return mappings;
	}


	private void addVote(final String socClass, final Retailer retailer){
		final int socIndex = this.classToIndex.get(socClass);
		final int retIndex = this.retailerToIndex.get(retailer);

		this.roundScores.get(this.currentRound)[socIndex][retIndex]++;
	}


	private void startNextRound(){
		storeDataForPreviousRound();

		this.currentRound++;
		final int numOfClasses = this.classToIndex.size();
		final int numOfRetailers = this.retailerToIndex.size();
		this.roundScores.put(this.currentRound, new int[numOfClasses][numOfRetailers]);
	}

	private void storeDataForPreviousRound() {
		// TODO Auto-generated method stub

	}




}
