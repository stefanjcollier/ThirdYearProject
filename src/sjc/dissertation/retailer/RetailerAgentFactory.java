package sjc.dissertation.retailer;

import java.util.HashSet;
import java.util.Set;

public class RetailerAgentFactory {

	/** A set to contain retailers that have been assigned an agent*/
	private Set<Retailer> retailers;

	private static RetailerAgentFactory singelton = new RetailerAgentFactory();

	public static RetailerAgentFactory getSingleton(){
		return RetailerAgentFactory.singelton;
	}

	private RetailerAgentFactory(){
		this.retailers = new HashSet<Retailer>(5);
	}

	public RetailerAgent createNewAgent(final Retailer brainlessRetailer, final Algorithm policy){
		if(retailerInUse(brainlessRetailer) || policy.hasRetailer()) {
			return null;
		}
		final int id = this.retailers.size()+1;
		final RetailerAgent newAgent = new RetailerAgent(id, brainlessRetailer, policy);

		this.retailers.add(brainlessRetailer);

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
