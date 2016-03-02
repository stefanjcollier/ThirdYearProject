package sjc.dissertation.model.logging;

import sjc.dissertation.consumer.Consumer;

public class MasterLogger {

	private final static MasterLogger single = new MasterLogger();

	public static MasterLogger getSingleton(){
		return MasterLogger.single;
	}

	private MasterLogger(){

	}

	public WrappedConsumer wrapConsumer(final Consumer consumer){
		return new WrappedConsumer(consumer);
	}

}
