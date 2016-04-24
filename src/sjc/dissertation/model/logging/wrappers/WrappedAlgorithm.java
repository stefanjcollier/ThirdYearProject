package sjc.dissertation.model.logging.wrappers;

import java.util.ArrayList;
import java.util.List;

import sjc.dissertation.model.logging.MasterLogger;
import sjc.dissertation.model.logging.results.PrintResultsInterface;
import sjc.dissertation.retailer.branch.Algorithm;
import sjc.dissertation.retailer.branch.Branch;
import sjc.dissertation.retailer.state.BranchState;
import sjc.dissertation.retailer.state.InvalidRetailerActionException;
import sjc.dissertation.retailer.state.RetailerAction;
import sjc.dissertation.util.FileUtils;

public class WrappedAlgorithm extends Algorithm implements Wrapper, PrintResultsInterface{

	private final Algorithm me;
	private final MasterLogger logger;

	public final List<Double> weeklyCost;
	public final List<Double> weeklyMarkup;


	public WrappedAlgorithm(final MasterLogger logger, final Algorithm objectObserved){
		this.me = objectObserved;
		this.logger = logger;

		this.weeklyCost = new ArrayList<>(500);
		this.weeklyMarkup= new ArrayList<>(500);


		//Acknowledge instantiation
		this.logger.trace(this, "Instantiated");
	}

	@Override
	public RetailerAction determineAction(final BranchState state, final List<Branch> competitors) {
		final RetailerAction action = this.me.determineAction(state, competitors);

		final String line = String.format(" At state:%s\tchose:%s", state.getSymbol(), action.getSymbol());
		this.logger.print(this, line);

		BranchState newState = null;
		try {
			newState = state.createNewState(action);
		} catch (final InvalidRetailerActionException e) {
			e.printStackTrace();
		}
		this.weeklyCost.add(newState.getQuality().getCost());
		this.weeklyMarkup.add(newState.getProfitMargin().getProfitMargin());

		return action;
	}

	@Override
	public void informOfReward(final double profit) {
		this.me.informOfReward(profit);

	}

	@Override
	public String getWrapperId() {
		return String.format("%s::Algorithm(%s)", this.me.toString(), getOwnerersName());
	}

	public String getOwnerersName(){
		if(this.hasBranchAgent()){
			return this.getBranchAgent().getBranch().getBranchName();
		}else if (this.hasRetailerAgent()){
			return this.getRetailerAgent().getName();
		}
		return "-";

	}

	@Override
	public String toString() {
		return String.format("Wrapper(%s)", this.me.toString());
	}

	@Override
	public String printResults(final FileUtils futil) {
		final String dir;
		final String name;
		if(this.getBranchAgent() != null){
			dir = "retailers/Retailer("+getBranchAgent().getBranch().getRetailer().getName()+")/";
			name = getBranchAgent().getBranch().getBranchName();
		}else if(this.getRetailerAgent() != null){
			dir = "retailers/Retailer("+getRetailerAgent().getName()+")/";
			name = getRetailerAgent().getName();
		}else{
			throw new RuntimeException("The Algorithm "+getWrapperId()+" seems to have no agent");
		}

		futil.makeFolder(dir);
		//Write Quality
		String qs = "";
		for(final Double q : this.weeklyCost){
			qs += q+System.lineSeparator();
		}
		final String qualityFile = dir+name+"_quality.csv";
		futil.writeStringToFile(qs, qualityFile);

		//Write Profit margin
		String pms = "";
		for(final Double pm : this.weeklyMarkup){
			pms += pm+System.lineSeparator();
		}
		final String makupFile = dir+name+"_markup.csv";
		futil.writeStringToFile(pms, makupFile);


		return "No String Output";
	}



}
