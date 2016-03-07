package sjc.dissertation.model.logging;

import java.io.IOException;

import sjc.dissertation.consumer.Consumer;
import sjc.dissertation.retailer.Retailer;

public class MasterLogger {
	/** The path to the output of the file */
	private static String logOutputPath = "logOutput.txt";
	/**
	 * Given that the singleton ha	s not been initialised, the path to the file is set.
	 *
	 * @param newPath --  The path to where the log will be stored.
	 * @return true -- The path was change.
	 * 		   false -- The singleton has already been initialised and the path was not set
	 */
	public static boolean setLogOutputPath(final String newPath){
		if(MasterLogger.single == null) {
			return false;
		}
		MasterLogger.logOutputPath = newPath;
		return true;
	}
	/** Singleton entity */
	private static MasterLogger single;
	public static MasterLogger getSingleton() throws MasterLoggerException{
		if(MasterLogger.single == null) {
			try {
				MasterLogger.single = new MasterLogger(logOutputPath);
			} catch (final IOException e) {
				throw new MasterLoggerException("Initialisation Error", e);
			}
		}
		return MasterLogger.single;
	}


	private Level displayLevel;
	private LogFileWriter logFile;
	private MasterLogger(final String filePath) throws IOException{
		this.displayLevel = Level.Log;
		this.logFile = new LogFileWriter(filePath);
	}


	public WrappedConsumer wrapConsumer(final Consumer consumer){
		return new WrappedConsumer(this, consumer);
	}

	public WrappedRetailer wrapRetailer(final Retailer retailer){
		return new WrappedRetailer(this, retailer);
	}

	private void addToLog(final Wrapper sender, final String text, final Level level){
		final String line = String.format("%s:: %s", sender.getWrapperId(), text);

		//Add all lines to log
		try {
			this.logFile.writeLine(line);
		} catch (final MasterLoggerException e) {
			e.printStackTrace();
		}

		//Only print ones with the correct permission
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
