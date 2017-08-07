package tx03.ude;

public class EndOfPromotionException extends RuntimeException {
	public EndOfPromotionException() {
	}

	public EndOfPromotionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EndOfPromotionException(String message, Throwable cause) {
		super(message, cause);
	}

	public EndOfPromotionException(String message) {
		super(message);
	}

	public EndOfPromotionException(Throwable cause) {
		super(cause);
	}
}
