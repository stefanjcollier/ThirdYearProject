package sjc.dissertation.retailer.carnivore;

import java.util.List;

import sjc.dissertation.retailer.Algorithm;
import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.state.RetailerAction;
import sjc.dissertation.retailer.state.RetailerState;

public class GreedyAlgorithm extends Algorithm{

	private List<Retailer> competitors;

	public GreedyAlgorithm(final List<Retailer> competitors) {
		this.competitors = competitors;
	}

	@Override
	public RetailerAction determineAction(final RetailerState state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void informOfReward(final double profit) {
		// TODO Auto-generated method stub

	}


	protected RetailerAction selectBestOption(final Object undecided){
		return null;
	}




}
