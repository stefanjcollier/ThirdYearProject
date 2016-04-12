package sjc.dissertation.model.logging.wrappers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import sjc.dissertation.model.logging.MasterLogger;
import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.branch.Branch;

public class WrappedRetailer implements Retailer, Wrapper{

	private final MasterLogger logger;
	private final Retailer me;

	public WrappedRetailer(final MasterLogger logger, final Retailer retailer) {
		this.logger = logger;
		this.me = retailer;

		//Acknowledge instantiation
		this.logger.trace(this, "Instantiated");
	}

	@Override
	public String getWrapperId() {
		return String.format("Retailer(%s)", this.me.getName());
	}

	@Override
	public String getName() {
		return this.me.getName();
	}

	@Override
	public List<Branch> getBranches() {
		return this.me.getBranches();
	}

	@Override
	public Branch createBranch(final double x, final double y) {
		return this.me.createBranch(x, y);
	}

	@Override
	public void informOfProfit(final Branch branch, final double profit) {
		this.me.informOfProfit(branch, profit);
	}

	@Override
	public Map<Branch, Double> startNewWeek() {
		final Map<Branch, Double> earnings = this.me.startNewWeek();
		final double total = earnings.values().stream().mapToDouble(Double::doubleValue).sum();
		this.logger.print(String.format(
				"Earnt %f   ::Breakdown:: %s", total, Arrays.toString(earnings.values().toArray())));

		return earnings;
	}

}
