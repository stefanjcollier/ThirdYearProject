package sjc.dissertation.consumer;

import java.util.List;

import sjc.dissertation.retailer.RetailBranch;

public interface Consumer {

	public int chooseRetailer(final List<RetailBranch> RetailBranches);

	public String getSocialClass();

	public double getBudget();

	public boolean canAfford(final RetailBranch RetailBranch);

	public double costOfShop(final RetailBranch re);

	public double chanceOf(final List<RetailBranch> allBranches, final RetailBranch testBranch);

	public int getId();

	public int getX();

	public int getY();
}
