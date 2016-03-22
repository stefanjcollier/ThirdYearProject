package sjc.dissertation.retailer.carnivore;

import sjc.dissertation.model.logging.MasterLogger;
import sjc.dissertation.model.logging.wrappers.WrappedActionPredictor;
import sjc.dissertation.retailer.Algorithm;

public class GreedyAlgorithmFactory {

	private final MasterLogger logger;
	private int currentId;

	public GreedyAlgorithmFactory(final MasterLogger logger) {
		this.logger = logger;
		this.currentId = 0;
	}

	public Algorithm createGreedyAlgorithm(final int noOfCompetitors){
		final WorldPerceptor world = new WorldPerceptor();
		final ActionPredictor predictor = new ActionPredictorImpl(getInitWeights(noOfCompetitors));
		return new GreedyAlgorithm(world, predictor);
	}

	public Algorithm createWrappedGreedyAlgorithm(final int noOfCompetitors){
		final WorldPerceptor world = new WorldPerceptor();
		final ActionPredictor predictor = new ActionPredictorImpl(getInitWeights(noOfCompetitors));
		final ActionPredictor wrappedpredictor = new WrappedActionPredictor(this.logger, predictor, this.currentId++);
		return new GreedyAlgorithm(world, wrappedpredictor);
	}

	protected double[] getInitWeights(final int noOfCompetitors){
		final double[] w = new double[5 + 2*noOfCompetitors];

		//Set all the values to the same
		final int defaultW = 5;
		for (int i = 0; i < w.length; i++){
			w[i] = defaultW;
		}
		return w;
	}

}
