package sjc.dissertation.retailer.branch;

import java.util.HashSet;
import java.util.Set;

public class BranchAgentFactory {

	/** A set to contain branches that have been assigned agents*/
	private Set<Branch> branches;

	private static BranchAgentFactory singelton = new BranchAgentFactory();

	public static BranchAgentFactory getSingleton(){
		return BranchAgentFactory.singelton;
	}

	private BranchAgentFactory(){
		this.branches = new HashSet<Branch>(5);
	}

	public CarnivoreBranchAgent createNewCarnivoreAgent(final Branch brainlessRetailer, final Algorithm policy){
		if(retailerInUse(brainlessRetailer) || policy.hasRetailer()) {
			return null;
		}
		final int id = this.branches.size()+1;
		final CarnivoreBranchAgent newAgent = new CarnivoreBranchAgent(id, brainlessRetailer, policy);

		this.branches.add(brainlessRetailer);

		return newAgent;
	}

	public HerbivoreBranchAgent createNewHerbivoreAgent(final Branch brainlessRetailer){
		//If the branch has a agent or is not a herbivoreBranchImpl
		if(retailerInUse(brainlessRetailer) ||
				!brainlessRetailer.getClass().getName().equals(HerbivoreBranchImpl.class.getName())) {
			return null;
		}


		final int id = this.branches.size()+1;
		final HerbivoreBranchAgent newAgent = new HerbivoreBranchAgent(id, (HerbivoreBranchImpl)brainlessRetailer);

		this.branches.add(brainlessRetailer);

		return newAgent;
	}

	/**
	 * If the provided branch has already be claimed by a controlling agent.
	 *
	 * @param retailer -- The branch to be checked for an agent.
	 * @return true if the branch has an agent.
	 */
	private boolean retailerInUse(final Branch retailer){
		return this.branches.contains(retailer);
	}


}
