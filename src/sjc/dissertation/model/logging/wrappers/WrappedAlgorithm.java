package sjc.dissertation.model.logging.wrappers;

import java.util.List;

import sjc.dissertation.model.logging.MasterLogger;
import sjc.dissertation.retailer.Algorithm;
import sjc.dissertation.retailer.RetailBranch;
import sjc.dissertation.retailer.state.RetailState;
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
	public RetailerAction determineAction(final RetailState state, final List<RetailBranch> competitors) {
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
		return String.format("%s::Algorithm(%s)", this.me.toString(), (this.hasRetailer())?this.getAgent().getRetailer().getName():"-");
	}

	@Override
	public String toString() {
		return String.format("Wrapper(%s)", this.me.toString());
	}



}
