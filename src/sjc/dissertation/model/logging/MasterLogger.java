package sjc.dissertation.model.logging;

import sjc.dissertation.model.logging.file.LogFileWriter;
import sjc.dissertation.model.logging.file.LogFileWritingException;
import sjc.dissertation.model.logging.wrappers.Wrapper;

public class MasterLogger {
	private Level displayLevel;
	private LogFileWriter logFile;
	private final String filename = "ThirdYearProject_Log";
	private final String fileExtension = ".txt";
	private final AnonymousSender anon;

	protected MasterLogger(final String filePath){
		this.displayLevel = Level.Log;
		this.logFile = new LogFileWriter(filePath, this.filename, this.fileExtension);
		this.anon = new AnonymousSender();
	}

	private void addToLog(final Wrapper sender, final String text, final Level level){
		final String line = String.format("%s:: %s", sender.getWrapperId(), text);

		//Add all lines to log
		try {
			this.logFile.writeLine(line);
		} catch (final LogFileWritingException e) {
			e.printStackTrace();
		}

		//Only print ones with the correct permission
		if(level.compareTo(this.displayLevel) >= 0){
			System.out.println(line);
		}
	}


	public void print(final Wrapper sender, final String text){
		addToLog(sender, text, Level.Print);
	}
	public void log(final Wrapper sender, final String text){
		addToLog(sender, text, Level.Log);
	}
	public void debug(final Wrapper sender, final String text){
		addToLog(sender, text, Level.Debug);
	}
	public void trace(final Wrapper sender, final String text){
		addToLog(sender, text, Level.Trace);
	}


	private class AnonymousSender implements Wrapper{
		@Override
		public String getWrapperId() {
			return "Anonymous::";
		}
	}
	public void print(final String text){
		addToLog(this.anon, text, Level.Print);
	}
	public void log(final String text){
		addToLog(this.anon, text, Level.Log);
	}
	public void debug(final String text){
		addToLog(this.anon, text, Level.Debug);
	}
	public void trace(final String text){
		addToLog(this.anon, text, Level.Trace);
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
