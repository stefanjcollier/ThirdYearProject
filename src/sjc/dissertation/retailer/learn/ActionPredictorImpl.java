package sjc.dissertation.retailer.learn;

import sjc.dissertation.retailer.state.RetailerAction;
import sjc.dissertation.util.VectorToolbox;

public class ActionPredictorImpl implements ActionPredictor{

	private double lambda = 0.001;

	/** The sturcture of the weights
	 *    {Q_me, Pm_me, #Cus_me, Q_1, CoS_1, ... , Q_n, CoS_n, QC_act, PMC_act}
	 *
	 * Such that 1..n are the retailers in the param 'others'
	 *    Q_x     = The quality of retailer x
	 *    Pm_x    = The profit margin of retailer x
	 *    #Cus_x = The number of consumer agents that chose x last week
	 *    CoS_x   = The cost of shopping at retailer x
	 *    QC_act = The quality change of the action
	 *    PMC_act = The profit margin change from the action
	 */
	private double[] w;

	/** The predicted profit*/
	private double predProf;

	/** The world used in predicting {@link ActionPredictorImpl#predProf} */
	private double[] x_n;

	protected ActionPredictorImpl(final double[] initWeights){
		this.w = initWeights;
	}

	@Override
	public double predictProfit(final double[] world){
		return VectorToolbox.multiplyVectors(world, this.w);
	}



	@Override
	public void informOfAction(final RetailerAction action, final double predProf, final double[] world){
		this.predProf = predProf;
		this.x_n = world;
	}

	/**
	 * This will update the weights based on the equation below.
	 *  The predicted value is used from the {@link ActionPredictorImpl#predictProfit(RetailerAction, double[])} method.
	 *
	 * 	w_n+1 = w_n +  x_n * (lambda * (act-pred))
	 *
	 * w_n is the variables used to predict the result
	 *
	 * @param actualProfit -- The profit that was actually received
	 * @return The new weights
	 */
	@Override
	public double[] feedback(final double actualProfit){

		//w = w + lambda * deltaE
		//deltaE = 2 * x_n * (pred-act)
		final double[] lambdaDeltaE = VectorToolbox.multiplyByConst(this.x_n, getLearningRate()*2*(actualProfit-this.predProf));
		this.w = VectorToolbox.addVectors(this.w, lambdaDeltaE);

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
