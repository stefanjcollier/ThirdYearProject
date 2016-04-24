package sjc.dissertation.model.logging.wrappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import sjc.dissertation.model.logging.MasterLogger;
import sjc.dissertation.model.logging.results.PrintResultsInterface;
import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.branch.Branch;
import sjc.dissertation.util.Currency;
import sjc.dissertation.util.FileUtils;

public class WrappedRetailer implements Retailer, Wrapper, PrintResultsInterface{

	private final MasterLogger logger;
	private final Retailer me;
	public final List<Double[]> weeklyBreakdown;

	public WrappedRetailer(final MasterLogger logger, final Retailer retailer) {
		this.logger = logger;
		this.me = retailer;
		this.weeklyBreakdown = new ArrayList<>(500);

		//Acknowledge instantiation
		this.logger.trace(this, "Instantiated");
	}

	public Retailer getTrueRetailer(){
		return this.me;
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
	public Branch createBranch(final double x, final double y, final int settlment) {
		return this.me.createBranch(x, y, settlment);
	}

	@Override
	public void informOfProfit(final Branch branch, final double profit) {
		this.me.informOfProfit(branch, profit);
	}

	@Override
	public Map<Branch, Double> startNewWeek() {
		final Map<Branch, Double> earnings = this.me.startNewWeek();
		final double total = earnings.values().stream().mapToDouble(Double::doubleValue).sum();
		this.logger.print(this,String.format(
				"Earnt %s   ::Breakdown:: %s", Currency.prettyString(total), Arrays.toString(earnings.values().toArray())));

		this.weeklyBreakdown.add(earnings.values().toArray(new Double[]{1.0}));

		return earnings;
	}

	@Override
	public String printResults(final FileUtils futil) {
		final String out = "Retailer Results for "+getWrapperId()+System.lineSeparator();

		String earnt = this.getName()+",";
		for(final Branch b : getBranches()){
			earnt += b.getBranchName()+",";
		}
		//Remove last comma
		earnt = earnt.substring(0, earnt.length()-1)+System.lineSeparator();


		for (final Double[] week : this.weeklyBreakdown){
			final double total = Arrays.asList(week).stream().mapToDouble(Double::doubleValue).sum();
			earnt += total+","+Arrays.toString(week).replace("[", "").replace("]", "").replaceAll("\\s+", "");
			earnt += System.lineSeparator();
		}

		//write to disk
		final String dir = "retailers/"+getWrapperId();
		futil.makeFolder(dir);
		futil.writeStringToFile(earnt, dir+"/"+getWrapperId()+"EarningBreakdown.csv");

		final String locPath = "retailers/locations.csv";
		futil.touchFile(locPath);
		for(final Branch b : getBranches()){
			futil.appendStringToFile(String.format("%f,%f,%s"+System.lineSeparator(), b.getX(), b.getY(), b.getBranchName()), locPath);
		}


		return out+earnt;
	}

}
