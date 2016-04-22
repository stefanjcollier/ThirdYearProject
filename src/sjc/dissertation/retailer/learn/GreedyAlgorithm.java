package sjc.dissertation.retailer.learn;

import java.util.List;

import sjc.dissertation.retailer.branch.Algorithm;
import sjc.dissertation.retailer.branch.Branch;
import sjc.dissertation.retailer.state.BranchState;
import sjc.dissertation.retailer.state.RetailerAction;

public class GreedyAlgorithm extends Algorithm{

	private WorldPerceptor eyes;
	private ActionPredictor brain;


	protected GreedyAlgorithm(final WorldPerceptor perceptor, final ActionPredictor predictor){
		this.eyes = perceptor;
		this.brain = predictor;
	}


	@Override
	public RetailerAction determineAction(final BranchState state, final List<Branch> competitors) {
		//Estimate the consequence of every action and select the best action (highest profit wielding)
		double bestProfit = Double.NEGATIVE_INFINITY;
		RetailerAction bestAction = null;
		for (final RetailerAction action : state.getActions()){
			//Percieve World
			final double[] worldWithAction = this.eyes.percieveWorld(state, competitors, action);

			//Predict profit
			final double predictedProfit = this.brain.predictProfit(worldWithAction);
			if(predictedProfit > bestProfit){
				bestProfit = predictedProfit;
				bestAction = action;
			}
		}

		//Inform brain of decision to allow for feedback loop later
		final double[] worldWithBestAction = this.eyes.percieveWorld(state, competitors, bestAction);
		this.brain.informOfAction(bestAction, bestProfit, worldWithBestAction);

		return bestAction;
	}

	@Override
	public void informOfReward(final double actualProfit) {
		this.brain.feedback(actualProfit);
	}

	@Override
	public String toString(){
		return "Greedy";
	}

}
