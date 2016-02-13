package sjc.dissertation.retailer.agent;

import java.util.HashSet;
import java.util.Set;

import sjc.dissertation.retailer.Retailer;

public class RetailerAgentFactory {

	/** A set to contain retailers that have been assigned an agent*/
	private Set<Retailer> retailers;

	public RetailerAgentFactory(){
		this.retailers = new HashSet<Retailer>(5);
	}

	public RetailerAgent createNewAgent(final Retailer brainlessRetailer, final Algorithm policy){
		if(retailerInUse(brainlessRetailer) || policy.hasRetailer()) {
			return null;
		}
		final int id = this.retailers.size();
		final RetailerAgent newAgent = new RetailerAgent(id, brainlessRetailer, policy);
		policy.giveRetailerAgent(newAgent);

		return newAgent;
	}

	/**
	 * If the provided retailer has already be claimed by a controlling agent.
	 *
	 * @param retailer -- The retailer to be checked for an agent.
	 * @return true if the retailer has an agent.
	 */
	private boolean retailerInUse(final Retailer retailer){
		return this.retailers.contains(retailer);
	}


}
