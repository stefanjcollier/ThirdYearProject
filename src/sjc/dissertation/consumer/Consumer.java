package sjc.dissertation.consumer;

import java.util.List;

import sjc.dissertation.retailer.Retailer;

public interface Consumer {

	public int chooseRetailer(final List<Retailer> retailers);

	public String getSocialClass();

	public double getBudget();

	public boolean canAfford(final Retailer retailer);

	public double costOfShop(final Retailer re);

	public double chanceOf(final List<Retailer> allRe, final Retailer tstRe);

	public int getId();

	public int getX();

	public int getY();
}
