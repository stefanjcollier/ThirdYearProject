package sjc.dissertation.model.logging;

public class MasterLoggerException extends Exception {
	private static final long serialVersionUID = 1L;

	public MasterLoggerException(final String message, final Throwable e){
		super(message,e);
	}

	public MasterLoggerException(final String message){
		super(message);
	}
}
