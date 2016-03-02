package sjc.dissertation.model.logging;

import java.util.ArrayList;
import java.util.List;

import sjc.dissertation.consumer.Consumer;
import sjc.dissertation.retailer.Retailer;

public class MasterLogger {
	/** Singleton entity */
	private final static MasterLogger single = new MasterLogger();
	public static MasterLogger getSingleton(){
		return MasterLogger.single;
	}

	private final List<String> buffer;
	private Level displayLevel;
	private MasterLogger(){
		this.displayLevel = Level.Log;
		this.buffer = new ArrayList<String>(1000);
	}


	public WrappedConsumer wrapConsumer(final Consumer consumer){
		return new WrappedConsumer(this, consumer);
	}

	public WrappedRetailer wrapRetailer(final Retailer retailer){
		return new WrappedRetailer(this, retailer);
	}

	protected void addToLog(final Wrapper sender, final String text, final Level level){
		final String line = String.format("%s:: %s", sender.getWrapperId(), text);
		this.buffer.add(line);

		if(level.compareTo(this.displayLevel)<= 0){
			System.out.println(line);
		}
	}

	protected void print(final Wrapper sender, final String text){
		addToLog(sender, text, Level.Print);
	}
	protected void log(final Wrapper sender, final String text){
		addToLog(sender, text, Level.Log);
	}
	protected void debug(final Wrapper sender, final String text){
		addToLog(sender, text, Level.Debug);
	}
	protected void trace(final Wrapper sender, final String text){
		addToLog(sender, text, Level.Trace);
	}

	public enum Level{
		Trace("Trace"), Debug("Debug"), Log("Log"), Print("");

		private final String title;
		private Level(final String title){
			this.title = title;
		}

		@Override
		public String toString(){
			return this.title;
		}
	}
}
