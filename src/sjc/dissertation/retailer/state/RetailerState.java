package sjc.dissertation.retailer.state;

import java.util.Set;

import sjc.dissertation.retailer.state.profit.ProfitMargin;
import sjc.dissertation.retailer.state.quality.Quality;

public interface RetailerState {

	public Set<RetailerAction> getActions();

	public boolean isCompleteState();

	public Quality getQuality();

	public ProfitMargin getProfitMargin();

	public int getNumberOfCustomers();

	public String getSymbol();
}
