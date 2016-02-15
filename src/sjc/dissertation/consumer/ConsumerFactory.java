package sjc.dissertation.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sjc.dissertation.util.RandomToolbox;
//TODO JavaDoc CoFac
/**
 * This is a singleton class used to create and locate all {@link Consumer} agents.
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
	private final List<Consumer> consumers;

	/** The only allowed instance of this class */
	private static ConsumerFactory singleton;

	/** Used in generating budgets */
	private final Random rng;

	public static ConsumerFactory getSingleton(){
		if(singleton == null){
			final double[] ratios = {0.15, 0.19, 0.14, 0.15, 0.6, 0.25, 0.6};
			final double[] spending = {0.25, 0.25, 0.22, 0.21, 0.21, 0.22, 0.15};
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
		this.consumers = new ArrayList<Consumer>(200);
		this.rng = new Random();
	}

	/**
	 * @return the index corresponding to the class
	 */
	private int chooseRandomClass(){
		final int[] indexes = {1, 2, 3, 4 ,5, 6, 7};
		return RandomToolbox.probabilisticlyChoose(this.classNames, this.classRatios);
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

	public void createNewConsumer(){
		final int socClass = chooseRandomClass();
		final double budget = generateBudget(socClass);
		final int id = this.consumers.size()+1;

		final Consumer con = new Consumer(id, this.classNames[socClass], budget);
		this.consumers.add(con);
	}






}
