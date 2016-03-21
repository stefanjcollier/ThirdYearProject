package sjc.dissertation.model.logging.wrappers;

import java.util.Arrays;

import sjc.dissertation.model.logging.MasterLogger;
import sjc.dissertation.retailer.carnivore.ActionPredictor;
import sjc.dissertation.retailer.state.RetailerAction;

public class WrappedActionPredictor implements Wrapper, ActionPredictor{

	private final MasterLogger log;
	private final ActionPredictor me;
	private final String id;
	private double predicted;

	public WrappedActionPredictor(final MasterLogger logger, final ActionPredictor observedObject, final int id) {
		this.me = observedObject;
		this.id = String.format("LinReg(%d)", id*10);
		this.log = logger;

		//Logging
		final String text = String.format("Created:: with w: %s", Arrays.toString(this.me.getWeights()));
		this.log.trace(this, text);
	}

	@Override
	public double predictProfit(final RetailerAction action, final double[] world) {
		return this.me.predictProfit(action, world);
	}

	@Override
	public void informOfAction(final RetailerAction action, final double predProf) {
		this.predicted = predProf;
		this.log.print(this,String.format("Predicts %f for action %s", predProf, action.getSymbol()) );

		this.me.informOfAction(action, predProf);
	}

	@Override
	public double[] feedback(final double actualProfit) {
		final double[] newW = this.me.feedback(actualProfit);
		final double error = this.predicted - actualProfit;

		this.log.print(this, String.format("Error was %f, Weights updated to: %s", error ,Arrays.toString(newW)));

		return newW;
	}

	@Override
	public double[] getWeights() {
		return this.me.getWeights();
	}

	@Override
	public double getLearningRate() {
		return this.me.getLearningRate();
	}

	@Override
	public String getWrapperId() {
		return this.id;
	}


}
