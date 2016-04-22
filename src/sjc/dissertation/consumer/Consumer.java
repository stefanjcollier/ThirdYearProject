package sjc.dissertation.consumer;

import java.util.List;

import sjc.dissertation.retailer.branch.Branch;

public interface Consumer {

	public int chooseBranch(final List<Branch> RetailBranches);

	public String getSocialClass();

	public double getBudget();

	public boolean canAfford(final Branch RetailBranch);

	public double costOfShop(final Branch re);

	public double chanceOf(final List<Branch> allBranches, final Branch testBranch);

	public int getId();

	public double getX();

	public double getY();

	public int getSettlementId();
}
