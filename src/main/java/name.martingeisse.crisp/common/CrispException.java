package name.martingeisse.crisp.common;

/**
 *
 */
public class CrispException extends RuntimeException {

	public CrispException() {
	}

	public CrispException(String message) {
		super(message);
	}

	public CrispException(String message, Throwable cause) {
		super(message, cause);
	}

	public CrispException(Throwable cause) {
		super(cause);
	}

}
