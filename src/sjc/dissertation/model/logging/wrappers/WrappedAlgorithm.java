package sjc.dissertation.model.logging.wrappers;

import java.util.List;

import sjc.dissertation.model.logging.MasterLogger;
import sjc.dissertation.retailer.branch.Algorithm;
import sjc.dissertation.retailer.branch.Branch;
import sjc.dissertation.retailer.state.BranchState;
import sjc.dissertation.retailer.state.RetailerAction;

public class WrappedAlgorithm extends Algorithm implements Wrapper{

	private final Algorithm me;
	private final MasterLogger logger;

	public WrappedAlgorithm(final MasterLogger logger, final Algorithm objectObserved){
		this.me = objectObserved;
		this.logger = logger;

		//Acknowledge instantiation
		this.logger.trace(this, "Instantiated");
	}

	@Override
	public RetailerAction determineAction(final BranchState state, final List<Branch> competitors) {
		final RetailerAction action = this.me.determineAction(state, competitors);

		final String line = String.format(" At state:%s\tchose:%s", state.getSymbol(), action.getSymbol());
		this.logger.print(this, line);

		return action;
	}

	@Override
	public void informOfReward(final double profit) {
		this.me.informOfReward(profit);

	}

	@Override
	public String getWrapperId() {
		String identifier = "-";
		if(this.hasBranchAgent()){
			identifier = this.getBranchAgent().getBranch().getBranchName();
		}else if (this.hasRetailerAgent()){
			identifier = this.getRetailerAgent().getName();
		}
		return String.format("%s::Algorithm(%s)", this.me.toString(), identifier);
	}

	@Override
	public String toString() {
		return String.format("Wrapper(%s)", this.me.toString());
	}



}
