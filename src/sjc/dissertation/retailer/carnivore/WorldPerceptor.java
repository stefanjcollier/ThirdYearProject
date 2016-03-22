package sjc.dissertation.retailer.carnivore;

import java.util.List;

import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.state.RetailerState;
import sjc.dissertation.retailer.state.profit.ProfitMargin;
import sjc.dissertation.retailer.state.profit.ProfitMarginChange;
import sjc.dissertation.retailer.state.quality.Quality;
import sjc.dissertation.retailer.state.quality.QualityChange;

/**
 * Takes the world and converts it into a numerical representation.
 * Making it easier to compute.
 *
 * @author Stefan Collier
 *
 */
public class WorldPerceptor {

	/**
	 * Convert the {@link Quality} into an agent interpretable format (a double)
	 *
	 * @param q -- The quality we intend to interpret.
	 * @return A numerical representation of the {@link Quality}
	 */
	protected static double convertQuality(final Quality q){
		switch(q){
		case LowQuality:{
			return 1;
		}
		case MediumQuality: {
			return 2;
		}
		case HighQuality: {
			return 3;
		}
		default:{
			//So large we'd notice it (also different to the profit margin to indicate difference)
			return -1000;
		}
		}
	}

	/**
	 * Convert the {@link ProfitMargin} into an agent interpretable format (a double).
	 *
	 * @param pm -- The profit margin we intend to interpret.
	 * @return A numerical representation of the {@link ProfitMargin}
	 */
	protected static double convertProfitMargin(final ProfitMargin pm){
		switch(pm){
		case NegativeProfitMargin:{
			return -1;
		}
		case NoProfitMargin: {
			return 0;
		}
		case LowProfitMargin: {
			return 1;
		}
		case HighProfitMargin: {
			return 2;
		}
		default:{
			//So large we'd notice it (also different to the quality to indicate difference)
			return -100000;
		}
		}
	}

	protected static double convertProfitChange(final ProfitMarginChange pmc){
		switch(pmc){
		case DecreaseProfitMargin:{
			return -1;
		}
		case MaintainProfitMargin: {
			return 0;
		}
		case IncreaseProfitMargin: {
			return 1;
		}
		default:{
			//So large we'd notice it (also different to the quality to indicate difference)
			return -1000000;
		}
		}
	}

	protected static double convertQualityChange(final QualityChange qc){
		switch(qc){
		case DecreaseQuality:{
			return -1;
		}
		case MaintainQuality: {
			return 0;
		}
		case IncreaseQuality: {
			return 1;
		}
		default:{
			//So large we'd notice it (also different to the quality to indicate difference)
			return -100000000;
		}
		}
	}


	/**
	 * Converts the state and other retailers into a multivariate vector.
	 * The structure:
	 *    {Q_me, Pm_me, #Cus_me, Q_1, CoS_1, ... , Q_n, CoS_n}
	 *
	 * Such that 1..n are the retailers in the param 'others'
	 *    Q_x     = The quality of retailer x
	 *    Pm_x    = The profit margin of retailer x
	 *    #Cus_x = The number of consumer agents that chose x last week
	 *    CoS_x   = The cost of shopping at retailer x
	 *
	 * @param me -- The state of the retailer making the decision
	 * @param others -- The other competing retailers
	 *
	 * @return A numerical vector representation of how the agent views the world
	 */
	//TODO: (WorldPerceptor) Check the influence of the #Cus as could be V large
	public double[] percieveWorld(final RetailerState me, final List<Retailer> others){
		final int variables = 2 + others.size()*2;
		final double[] perception = new double[variables];

		//Handle perception of self
		perception[0] = convertQuality(me.getQuality());
		perception[1] = convertProfitMargin(me.getProfitMargin());

		//Interpret competitors
		int index = 3;
		for(final Retailer other : others){
			perception[index++] = convertQuality(other.getQualityOfShop());
			perception[index++] = convertProfitMargin(other.getProfiMargin());
		}
		return perception;
	}

}
