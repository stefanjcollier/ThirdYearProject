package sjc.dissertation.retailer.carnivore;

import java.util.List;

import sjc.dissertation.retailer.Algorithm;
import sjc.dissertation.retailer.RetailerImpl;
import sjc.dissertation.retailer.state.RetailerAction;
import sjc.dissertation.retailer.state.RetailerState;

public class GreedyAlgorithm extends Algorithm{

	private WorldPerceptor eyes;
	private ActionPredictor brain;


	@Override
	public RetailerAction determineAction(final RetailerState state, final List<RetailerImpl> competitors) {
		//Percieve World
		final double[] world = this.eyes.percieveWorld(state, competitors);


		//Estimate the consequence of every action and select the best action (highest profit wielding)
		double bestProfit = -1;
		RetailerAction bestAction = null;
		for (final RetailerAction action : state.getActions()){
			final double predictedProfit = this.brain.predictProfit(action, world);
			if(predictedProfit > bestProfit){
				bestProfit = predictedProfit;
				bestAction = action;
			}
		}

		//Inform brain of decision to allow for feedback loop later
		this.brain.informOfAction(bestAction, bestProfit);

		return bestAction;
	}

	@Override
	public void informOfReward(final double actualProfit) {
		this.brain.feedback(actualProfit);
	}


}
