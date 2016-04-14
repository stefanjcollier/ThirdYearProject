package sjc.dissertation.retailer.learn;

import sjc.dissertation.model.logging.MasterLogger;
import sjc.dissertation.model.logging.wrappers.WrappedActionPredictor;
import sjc.dissertation.retailer.branch.Algorithm;
import sjc.dissertation.retailer.state.profit.ProfitMargin;
import sjc.dissertation.retailer.state.profit.ProfitMarginChange;
import sjc.dissertation.retailer.state.quality.Quality;
import sjc.dissertation.retailer.state.quality.QualityChange;

public class GreedyAlgorithmFactory {

	private final int ukPop;
	private final MasterLogger logger;
	private int currentId;

	public GreedyAlgorithmFactory(final MasterLogger logger, final int populationOfUK) {
		this.logger = logger;
		this.currentId = 0;
		this.ukPop = populationOfUK;
	}

	public Algorithm createGreedyAlgorithm(final int noOfCompetitors){
		final WorldPerceptor world = new WorldPerceptor();
		final ActionPredictor predictor = new ActionPredictorImpl(getInitWeights(noOfCompetitors));
		return new GreedyAlgorithm(world, predictor);
	}

	public Algorithm createWrappedGreedyAlgorithm(final int noOfCompetitors){
		final WorldPerceptor world = new WorldPerceptor();
		final ActionPredictor predictor = new ActionPredictorImpl(getInitWeights(noOfCompetitors));
		final ActionPredictor wrappedpredictor = new WrappedActionPredictor(this.logger, predictor, this.currentId++);
		return new GreedyAlgorithm(world, wrappedpredictor);
	}


	/** Creates an array of equal elements of size: 4 + 2*noOfCompetitors.
	 * 	That value they each hold is based on the a world (say there was 2 retailers):
	 *
	 *  Re_1 & Re_2 State: (MQ, P+)
	 *  Action: (~Q,~P)
	 *  Equals to CostOfShopping(Re_1) * NoOfConsumersInUK/ NoOfRetailers
	 *
	 * Works on the concept that if all retailer have the same cost of shopping then
	 * the UK population will be split evenly over all retailers
	 *
	 * @param noOfCompetitors -- number of branches that are not the one being made intelligent
	 * @return
	 */
	protected double[] getInitWeights(final int noOfCompetitors){
		final double[] w = new double[4 + 2*noOfCompetitors];

		//Create an array and sum it {Q_me, P_me, Q_1, P_1, ..., Q_N, P_N, Qact, Pact}
		//Where all qualities and profit margins are the same
		double totalW = (noOfCompetitors+1) *
				(WorldPerceptor.convertQuality(Quality.MediumQuality)+WorldPerceptor.convertProfitMargin(ProfitMargin.LowProfitMargin));

		totalW += WorldPerceptor.convertQualityChange(QualityChange.MaintainQuality)
				+WorldPerceptor.convertProfitChange(ProfitMarginChange.MaintainProfitMargin);

		//Work out cost of shop
		final double rawCost = Quality.MediumQuality.getCost();
		final double profitMultiplier = 1 + ProfitMargin.LowProfitMargin.getProfitMargin();
		final double costOfShop = rawCost * profitMultiplier;

		//population / noOfRetailers * cost of a shop with retailer
		final double profitOnCurrentWorldState = this.ukPop * costOfShop / (noOfCompetitors+1);

		final double defaultWvalue = profitOnCurrentWorldState / totalW;
		//Set all the values to the same
		for (int i = 0; i < w.length; i++){
			w[i] = defaultWvalue;
		}
		return w;
	}

}
