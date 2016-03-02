package sjc.dissertation.consumer;

import java.util.List;

import sjc.dissertation.retailer.RetailerImpl;

public interface Consumer {

	public int chooseRetailer(final List<RetailerImpl> retailers);

	public String getSocialClass();

	public double getBudget();

	public boolean canAfford(final RetailerImpl retailer);

	public double costOfShop(final RetailerImpl re);

	public double chanceOf(final List<RetailerImpl> allRe, final RetailerImpl tstRe);

	public int getId();
}
