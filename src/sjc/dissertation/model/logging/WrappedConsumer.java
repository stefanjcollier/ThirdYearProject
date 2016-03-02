package sjc.dissertation.model.logging;

import java.util.List;

import sjc.dissertation.consumer.Consumer;
import sjc.dissertation.retailer.RetailerImpl;

public class WrappedConsumer implements Consumer, Wrapper{

	private final Consumer me;
	private final MasterLogger logger;
	protected WrappedConsumer(final MasterLogger logger, final Consumer consumer){
		this.me = consumer;
		this.logger = logger;

		//Logging
		final String text = String.format("Created:: %s with budget %f",
				this.me.getSocialClass(), this.me.getBudget());
		this.logger.trace(this, text);
	}

	@Override
	public int chooseRetailer(final List<RetailerImpl> retailers) {
		//TODO: Log something here
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
	@Override
	public String toString(){
		return this.me.toString();
	}
	@Override
	public int getId() {
		return this.me.getId();
	}


	@Override
	public String getWrapperId() {
		return String.format("Consumer(%d)", this.me.getId());

	}


}
