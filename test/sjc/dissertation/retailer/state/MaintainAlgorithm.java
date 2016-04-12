package sjc.dissertation.retailer.state;

import java.util.List;

import sjc.dissertation.retailer.branch.Algorithm;
import sjc.dissertation.retailer.branch.Branch;
import sjc.dissertation.retailer.state.profit.ProfitMarginChange;
import sjc.dissertation.retailer.state.quality.QualityChange;

/**
 * This is a static algorithm, it will constantly stay at the same price and quality level.
 * It can be used as a control.
 *
 * @author Stefan Collier
 *
 */
public class MaintainAlgorithm extends Algorithm {

	public  MaintainAlgorithm() {}

	@Override
	public RetailerAction determineAction(final BranchState state, final List<Branch> competitors) {
		return new RetailerAction(QualityChange.MaintainQuality, ProfitMarginChange.MaintainProfitMargin);
	}

	@Override
	public void informOfReward(final double profit) {
		//Does nothing as there is no feedback required
	}

	@Override
	public String toString(){
		return "Maintiner";
	}

}
