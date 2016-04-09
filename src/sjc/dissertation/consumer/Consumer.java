package sjc.dissertation.consumer;

import java.util.List;

import sjc.dissertation.retailer.Branch;

public interface Consumer {

	public int chooseRetailer(final List<Branch> RetailBranches);

	public String getSocialClass();

	public double getBudget();

	public boolean canAfford(final Branch RetailBranch);

	public double costOfShop(final Branch re);

	public double chanceOf(final List<Branch> allBranches, final Branch testBranch);

	public int getId();

	public int getX();

	public int getY();
}
