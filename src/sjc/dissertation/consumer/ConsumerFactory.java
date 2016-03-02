package sjc.dissertation.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sjc.dissertation.util.RandomToolbox;
//JAVADOC CoFac
/**
 * This is a singleton class used to create and locate all {@link ConsumerImpl} agents.
 *
 * @author Stefan Collier
 *
 */
public class ConsumerFactory {

	/** The ratio of choosing between the 5 classes */
	private final double[] classRatios;
	private final String[] classNames;
	private double[] budgetAvgs;

	/** The list of all existing consumers */
	private final List<ConsumerImpl> consumers;

	/** The only allowed instance of this class */
	private static ConsumerFactory singleton;

	/** Used in generating budgets */
	private final Random rng;

	public static ConsumerFactory getSingleton(){
		if(singleton == null){
			final double[] ratios = {0.15, 0.19, 0.14, 0.15, 0.06, 0.25, 0.06};
			final double[] spending = {39.68, 63.97, 88.63, 118.88, 151.15, 199.99, 428.28};
			final String[] names = new String[]{
					"Precariat",
					"Emergent Services Workers",
					"Traditional Working Class",
					"New Affluent Workers",
					"Technical Middle Class",
					"Established Middle Class",
					"Elite"
			};
			ConsumerFactory.singleton = new ConsumerFactory(names, ratios, spending);
		}
		return ConsumerFactory.singleton;
	}

	protected ConsumerFactory(final String[] classNames, final double[] classRatios, final double[] spendingAvgs){
		this.classNames = classNames;
		this.classRatios = classRatios;
		this.budgetAvgs = spendingAvgs;
		this.consumers = new ArrayList<ConsumerImpl>(200);
		this.rng = new Random();
	}


	/**
	 * Chooses a class based on the class ratios given.
	 *
	 * e.g. Say there are ratios: [0.25, 0.25, 0,5]
	 *  Calling this method 8 times would (on average) provide:
	 *  	2 class ones
	 *  	2 class twos
	 *  	4 class threes
	 *
	 * @return the index corresponding to the class
	 */
	private int chooseRandomClass(){
		return RandomToolbox.probabilisticlyChoose(this.classRatios);
	}

	/**
	 * Returns a budget based on the social class.
	 * The budget is within +- 10% of the average.
	 *
	 * @param socialClassIndex -- The index of the social class in the system
	 * @return A budget that is plus or minus 10% of the average for that class
	 */
	private double generateBudget(final int socialClassIndex){
		final double avgBudg = this.budgetAvgs[socialClassIndex];
		final double r = this.rng.nextDouble();
		// 0 < r < 1   hence   0.9 < (r/5+0.9) < 1.1
		return avgBudg*(r/5+0.9);

	}

	public ConsumerImpl createNewConsumer(){
		final int socClass = chooseRandomClass();
		final double budget = generateBudget(socClass);
		final int id = this.consumers.size()+1;

		final ConsumerImpl con = new ConsumerImpl(id, this.classNames[socClass], budget);
		this.consumers.add(con);
		return con;
	}






}
