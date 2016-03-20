package sjc.dissertation.model.logging.wrappers;

import java.util.List;

import sjc.dissertation.model.logging.MasterLogger;
import sjc.dissertation.retailer.Algorithm;
import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.state.RetailerAction;
import sjc.dissertation.retailer.state.RetailerState;

public class WrappedAlgorithm extends Algorithm implements Wrapper{

	private final Algorithm me;
	private final MasterLogger logger;

	public WrappedAlgorithm(final Algorithm objectObserved, final MasterLogger logger){
		this.me = objectObserved;
		this.logger = logger;
	}

	@Override
	public RetailerAction determineAction(final RetailerState state, final List<Retailer> competitors) {
		final RetailerAction action = this.me.determineAction(state, competitors);

		final String line = String.format(" At state:%s\tchose:%s", state.getSymbol(), action.getSymbol());
		this.logger.print(this, line);

		return action;
	}

	@Override
	public void informOfReward(final double profit) {
		this.informOfReward(profit);

	}

	@Override
	public String getWrapperId() {
		return String.format("%s::Algorithm(%s)", this.me.toString(), this.getAgent().getRetailer().getName());
	}



}
