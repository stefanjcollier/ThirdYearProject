package sjc.dissertation.model.logging.wrappers;

import java.util.List;

import sjc.dissertation.consumer.Consumer;
import sjc.dissertation.model.logging.MasterLogger;
import sjc.dissertation.retailer.Retailer;

public class WrappedConsumer implements Consumer, Wrapper{

	private final Consumer me;
	private final MasterLogger logger;
	public WrappedConsumer(final MasterLogger logger, final Consumer consumer){
		this.me = consumer;
		this.logger = logger;

		//Logging
		final String text = String.format("Created:: %s with budget %f",
				this.me.getSocialClass(), this.me.getBudget());
		this.logger.trace(this, text);
	}

	@Override
	public int chooseRetailer(final List<Retailer> retailers) {
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
	public boolean canAfford(final Retailer retailer) {
		return this.me.canAfford(retailer);
	}
	@Override
	public double costOfShop(final Retailer re) {
		return this.me.costOfShop(re);
	}
	@Override
	public double chanceOf(final List<Retailer> allRe, final Retailer tstRe) {
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
