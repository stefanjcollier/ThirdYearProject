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
		//Percieve World
		final double[] world = this.eyes.percieveWorld(state, competitors);


		//Estimate the consequence of every action and select the best action (highest profit wielding)
		double bestProfit = Double.NEGATIVE_INFINITY;
		RetailerAction bestAction = null;
		for (final RetailerAction action : state.getActions()){
			final double predictedProfit = this.brain.predictProfit(action, world);
			if(predictedProfit > bestProfit){
				bestProfit = predictedProfit;
				bestAction = action;
			}
		}

		//Inform brain of decision to allow for feedback loop later
		this.brain.informOfAction(bestAction, bestProfit, world);

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