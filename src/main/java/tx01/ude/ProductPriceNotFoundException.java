package tx01.ude;

public class ProductPriceNotFoundException extends RuntimeException {

	public ProductPriceNotFoundException() {
		super();
	}

	public ProductPriceNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ProductPriceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductPriceNotFoundException(String message) {
		super(message);
	}

	public ProductPriceNotFoundException(Throwable cause) {
		super(cause);
	}

}
