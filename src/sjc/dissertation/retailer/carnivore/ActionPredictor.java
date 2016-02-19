package sjc.dissertation.retailer.carnivore;

import sjc.dissertation.retailer.state.RetailerAction;

public class ActionPredictor {

	public int predictCustomers(final RetailerAction action, final double[] world){
		return -1;
	}

	public void informOfAction(final RetailerAction action){

	}

	public double[] feedback(final int customers){
		return null;
	}

	protected double getLearningRate(){
		return -0.1;
	}

	protected double[] getWeights(){
		return null;
	}
}
