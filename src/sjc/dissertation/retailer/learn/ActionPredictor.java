package sjc.dissertation.retailer.learn;

import sjc.dissertation.retailer.state.RetailerAction;

public interface ActionPredictor {
	public double predictProfit(final RetailerAction action, final double[] world);

	public void informOfAction(final RetailerAction action, final double predProf, final double[] world);

	/**
	 * This will update the weights based on the equation below.
	 *  The predicted value is used from the {@link ActionPredictorImpl#predictProfit(RetailerAction, double[])} method.
	 *
	 * 	w_n+1 = w_n +  lambda * (pred - act)
	 *
	 * @param actualProfit -- The profit that was actually received
	 * @return The new weights
	 */
	public double[] feedback(final double actualProfit);

	public double[] getWeights();

	public double getLearningRate();
}
