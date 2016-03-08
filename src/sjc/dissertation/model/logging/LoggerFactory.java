package sjc.dissertation.model.logging;

import sjc.dissertation.consumer.Consumer;
import sjc.dissertation.model.logging.wrappers.WrappedConsumer;
import sjc.dissertation.model.logging.wrappers.WrappedRetailer;
import sjc.dissertation.retailer.Retailer;

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

	public WrappedConsumer wrapConsumer(final Consumer consumer){
		return new WrappedConsumer(this.log, consumer);
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
