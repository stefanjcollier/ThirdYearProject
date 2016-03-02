package sjc.dissertation.model.logging;

import java.util.List;

import sjc.dissertation.consumer.Consumer;
import sjc.dissertation.retailer.RetailerImpl;

public class WrappedConsumer implements Consumer{

	private Consumer me;
	protected WrappedConsumer(final Consumer consumer){
		this.me = consumer;
	}

	@Override
	public int chooseRetailer(final List<RetailerImpl> retailers) {
		return this.me.chooseRetailer(retailers);
	}

	@Override
	public String getSocialClass() {
		return this.me.getSocialClass();
	}

	@Override
	public double getBudget() {
		return this.me.getBudget();
	}

	@Override
	public boolean canAfford(final RetailerImpl retailer) {
		return this.me.canAfford(retailer);
	}

	@Override
	public double costOfShop(final RetailerImpl re) {
		return this.me.costOfShop(re);
	}

	@Override
	public double chanceOf(final List<RetailerImpl> allRe, final RetailerImpl tstRe) {
		return this.me.chanceOf(allRe, tstRe);
	}

}
