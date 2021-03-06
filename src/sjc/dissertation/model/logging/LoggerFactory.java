package sjc.dissertation.model.logging;

import sjc.dissertation.consumer.Consumer;
import sjc.dissertation.model.logging.votes.VoteLogger;
import sjc.dissertation.model.logging.wrappers.WrappedAlgorithm;
import sjc.dissertation.model.logging.wrappers.WrappedConsumer;
import sjc.dissertation.model.logging.wrappers.WrappedRetailBranch;
import sjc.dissertation.model.logging.wrappers.WrappedRetailer;
import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.retailer.branch.Algorithm;
import sjc.dissertation.retailer.branch.Branch;

public class LoggerFactory {

	private final String resourceDir;
	private final MasterLogger log;
	public LoggerFactory(final String resourcePath){
		this.resourceDir = resourcePath;

		this.log = new MasterLogger(resourcePath);
	}

	public String getResourcePath(){
		return this.resourceDir;
	}

	public MasterLogger getMasterLogger(){
		return this.log;
	}

	public WrappedConsumer wrapConsumer(final Consumer consumer, final VoteLogger voteLog){
		return new WrappedConsumer(this.log, voteLog, consumer);
	}

	public WrappedRetailBranch wrapBranch(final Branch branch){
		return new WrappedRetailBranch(this.log, branch);
	}
	public WrappedAlgorithm wrapAlgorithm(final Algorithm algorithm){
		return new WrappedAlgorithm(this.log, algorithm);
	}
	public WrappedRetailer wrapRetailer(final Retailer retailer){
		return new WrappedRetailer(this.log, retailer);
	}

	private static LoggerFactory single;

	public static boolean initiateLoggerFactory(final String resourcePath){
		if(single == null){
			LoggerFactory.single = new LoggerFactory(resourcePath);
			return true;
		}else{
			return false;
		}
	}

	public static LoggerFactory getSingleton(){
		return LoggerFactory.single;
	}
}
