package sjc.dissertation.retailer.agent;

import java.util.HashSet;
import java.util.Set;

import sjc.dissertation.retailer.Retailer;

public class RetailerAgentFactory {

	private int currentIdNumber;
	private Set<Retailer> retailers;

	public RetailerAgentFactory(){
		this.currentIdNumber = 1;
		this.retailers = new HashSet<Retailer>(5);
	}

	public RetailerAgent createNewAgent(final Retailer brainlessRetailer, final Algorithm policy){
		final int id = this.currentIdNumber;

		if(retailerInUse(brainlessRetailer) || policy.hasRetailer()) {
			//TODO ADD: Throw exception
			return null;
		}

		final RetailerAgent newAgent = new RetailerAgent(id, brainlessRetailer, policy);
		policy.giveRetailerAgent(newAgent);
		this.currentIdNumber++;

		return newAgent;
	}

	private boolean retailerInUse(final Retailer retailer){
		return this.retailers.contains(retailer);
	}


}
