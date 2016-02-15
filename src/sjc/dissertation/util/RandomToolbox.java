package sjc.dissertation.util;

import java.util.Random;

/**
 * A toolbox class used to make weighted choices.
 *
 * @author Stefan Collier
 *
 */
public class RandomToolbox {

	private static final Random rng = new Random();

	/** Toolboxes should not be instantiated */
	private RandomToolbox(){}

	/**
	 * Makes a choice based on the various probabilities of each option.
	 *
	 * e.g. chances=[0.7, 0.2, 0.1]
	 *  There is a high chance of choosing option 1 (out of 1,2 & 3)
	 *  however any option could be chosen.
	 *
	 *  Note: If the chances of an option are 0, then it it is impossible to choose that
	 *
	 *  @param options -- The possible choices
	 *  @param chances -- The chances for each of the options that all sum to 1
	 *  @return the index of the weighted random choice.
	 */
	public static int probabilisticlyChoose(final Object[] options, final double[] chances){
		//Assume Sum(chances) = 1   i.e. Pie chart
		//Choose a random point in a circle (circumference = 1)
		final double randomPoint = rng.nextDouble();

		//The start of the slice
		double lowThresh = 0;

		//The end of the slice
		double highThresh = 0;

		//Cycle through all 'slices' and see if that point is in that slice
		for(int i = 0; i < chances.length; i++){

			//if current chance is 0, then ignore
			if(chances[i] == 0){
				continue;
			}

			highThresh += chances[i];
			//If the point is in the slice
			if(lowThresh <= randomPoint && randomPoint < highThresh){
				//Return the index of that slice
				return i;
			}
			lowThresh = highThresh;
		}
		return -1;
	}

}
