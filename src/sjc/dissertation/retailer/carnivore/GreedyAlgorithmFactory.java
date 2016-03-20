package sjc.dissertation.retailer.carnivore;

import sjc.dissertation.model.logging.MasterLogger;
import sjc.dissertation.model.logging.wrappers.WrappedActionPredictor;
import sjc.dissertation.retailer.Algorithm;

//TODO (GreedyFac) Actually use this!
public class GreedyAlgorithmFactory {

	private final MasterLogger logger;
	private int currentId;

	public GreedyAlgorithmFactory(final MasterLogger logger) {
		this.logger = logger;
		this.currentId = 0;
	}

	public Algorithm createGreedyAlgorithm(final int noOfCompetitors){
		final WorldPerceptor world = new WorldPerceptor();
		final ActionPredictor predictor = new ActionPredictorImpl(noOfCompetitors);
		return new GreedyAlgorithm(world, predictor);
	}

	public Algorithm createWrappedGreedyAlgorithm(final int noOfCompetitors){
		final WorldPerceptor world = new WorldPerceptor();
		final ActionPredictor predictor = new ActionPredictorImpl(noOfCompetitors);
		final ActionPredictor wrappedpredictor = new WrappedActionPredictor(this.logger, predictor, this.currentId++);
		return new GreedyAlgorithm(world, wrappedpredictor);
	}

}
