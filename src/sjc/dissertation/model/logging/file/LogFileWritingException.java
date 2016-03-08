package sjc.dissertation.model.logging.file;

public class LogFileWritingException extends Exception {

	private static final long serialVersionUID = 1L;

	public LogFileWritingException(final String message, final Throwable e){
		super(message,e);
	}

	public LogFileWritingException(final String message){
		super(message);
	}

}
