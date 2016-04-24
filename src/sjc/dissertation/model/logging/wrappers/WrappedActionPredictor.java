package sjc.dissertation.model.logging.wrappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sjc.dissertation.model.logging.MasterLogger;
import sjc.dissertation.model.logging.results.PrintResultsInterface;
import sjc.dissertation.retailer.learn.ActionPredictor;
import sjc.dissertation.retailer.state.RetailerAction;
import sjc.dissertation.util.FileUtils;

public class WrappedActionPredictor implements Wrapper, ActionPredictor, PrintResultsInterface{

	private final MasterLogger log;
	private final ActionPredictor me;
	private final String id;
	private double predicted;

	private final List<Double> error;
	private final List<Double> errPer;

	public WrappedActionPredictor(final MasterLogger logger, final ActionPredictor observedObject, final String id) {
		this.me = observedObject;
		this.id = String.format("LinReg(%s)", id);
		this.log = logger;

		//Logging
		final String text = String.format("Created:: with w: %s", Arrays.toString(this.me.getWeights()));
		this.log.trace(this, text);

		this.error = new ArrayList<>(500);
		this.errPer = new ArrayList<>(500);
	}

	@Override
	public double predictProfit(final double[] world) {
		return this.me.predictProfit(world);
	}

	@Override
	public void informOfAction(final RetailerAction action, final double predProf, final double[] world) {
		this.predicted = predProf;
		this.log.trace(this, String.format("Predicts %f for action %s", predProf, action.getSymbol()) );

		this.me.informOfAction(action, predProf, world);
	}

	@Override
	public double[] feedback(final double actualProfit) {
		final double[] newW = this.me.feedback(actualProfit);
		final double error = this.predicted - actualProfit;
		final double errorPercent = error/ actualProfit *100.0;

		this.log.debug(this, String.format("Error was %f (%.2f%%), Weights updated to: %s", error, errorPercent ,Arrays.toString(newW)));
		this.error.add(error);
		this.errPer.add(errorPercent);
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

	@Override
	public String printResults(final FileUtils futil) {
		final String out = "Perceptron Results for "+getWrapperId()+System.lineSeparator();
		String error ="";
		for (final double err : this.error){
			error += ""+err+System.lineSeparator();
		}

		String percent = "";
		for (final double errPer : this.errPer){
			percent += ""+errPer+System.lineSeparator();
		}

		//Save to disk
		final String dir = "linreg/"+getWrapperId();
		futil.makeFolder(dir);

		futil.writeStringToFile(error, dir+"/ActualError.csv");
		futil.writeStringToFile(percent, dir+"/PercentError.csv");


		return 	out+
				"Error:"+System.lineSeparator()+error+
				"PercentError"+System.lineSeparator()+percent;

	}


}
