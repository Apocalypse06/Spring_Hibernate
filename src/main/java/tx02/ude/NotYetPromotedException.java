package tx02.ude;

public class NotYetPromotedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public NotYetPromotedException() {
	}

	public NotYetPromotedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotYetPromotedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotYetPromotedException(String message) {
		super(message);
	}

	public NotYetPromotedException(Throwable cause) {
		super(cause);
	}
}
