package sjc.dissertation.model.logging.wrappers;

import java.util.List;

import sjc.dissertation.consumer.Consumer;
import sjc.dissertation.model.logging.MasterLogger;
import sjc.dissertation.model.logging.votes.VoteLogger;
import sjc.dissertation.retailer.branch.Branch;

public class WrappedConsumer implements Consumer, Wrapper{

	private final Consumer me;
	private final MasterLogger logger;
	private final VoteLogger votesLog;
	public WrappedConsumer(final MasterLogger logger, final VoteLogger voteLogger, final Consumer consumer){
		this.me = consumer;
		this.logger = logger;
		this.votesLog = voteLogger;

		//Logging
		final String text = String.format("Instantiated:: %s with budget %f :: x,y:%d,%d",
				this.me.getSocialClass(), this.me.getBudget(), this.me.getX(), this.me.getY());
		this.logger.trace(this, text);
	}

	@Override
	public int chooseBranch(final List<Branch> branches) {
		final int indexChoice = this.me.chooseBranch(branches);
		//Logging
		if(indexChoice > -1){
			final Branch branch = branches.get(indexChoice);

			this.votesLog.addVote(this.me, branch);
			this.logger.debug(this, String.format("%s:: Voted:: %s",
					this.me.getSocialClass(), branch.getBranchName()));
		}else{
			//an index choice of -1 indicates a no vote
			this.votesLog.addVote(this.me, null);
			this.logger.debug(this, String.format("%s:: Voted:: %s",
					this.me.getSocialClass(), "-Nobody-"));
		}
		return indexChoice;
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
	public boolean canAfford(final Branch retailer) {
		return this.me.canAfford(retailer);
	}
	@Override
	public double costOfShop(final Branch re) {
		return this.me.costOfShop(re);
	}
	@Override
	public double chanceOf(final List<Branch> allRe, final Branch tstRe) {
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

	@Override
	public double getX() {
		return this.me.getX();
	}

	@Override
	public double getY() {
		return this.me.getY();
	}


}
