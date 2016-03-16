package sjc.dissertation.retailer.carnivore;

import java.util.Arrays;

import sjc.dissertation.retailer.state.RetailerAction;
import sjc.dissertation.util.VectorToolbox;

public class ActionPredictorImpl implements ActionPredictor{

	private double lambda = 0.001;
	private double[] w;

	private double predProf;

	protected ActionPredictorImpl(final int noOfCompetitors){
		this.w = new double[5 + noOfCompetitors];

	}

	@Override
	public double predictProfit(final RetailerAction action, final double[] world){
		final double[] worldWithActions = Arrays.copyOf(world, world.length+2);
		worldWithActions[world.length] = WorldPerceptor.convertProfitChange(action.getProfitMarginChange());
		worldWithActions[world.length+1] = WorldPerceptor.convertQualityChange(action.getQualityChange());

		return VectorToolbox.multiply(worldWithActions, this.w);
	}



	@Override
	public void informOfAction(final RetailerAction action, final double predProf){
		this.predProf = predProf;
	}

	/**
	 * This will update the weights based on the equation below.
	 *  The predicted value is used from the {@link ActionPredictorImpl#predictProfit(RetailerAction, double[])} method.
	 *
	 * 	w_n+1 = w_n +  lambda * (pred - act)
	 *
	 * @param actualProfit -- The profit that was actually received
	 * @return The new weights
	 */
	@Override
	public double[] feedback(final double actualProfit){
		final double deltaProf = actualProfit - this.predProf;

		this.w = VectorToolbox.multiplyByConst(this.w, getLearningRate()*deltaProf);

		return this.w;
	}

	@Override
	public double getLearningRate(){
		return this.lambda;
	}


	@Override
	public double[] getWeights(){
		return this.w;
	}


}
