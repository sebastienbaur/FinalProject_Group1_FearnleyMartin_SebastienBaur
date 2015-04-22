package dataTypes;

public class NoAvailableSpaceException extends Exception {

	public NoAvailableSpaceException(String string) {
		super(string);
	}

	public NoAvailableSpaceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public NoAvailableSpaceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NoAvailableSpaceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
}
