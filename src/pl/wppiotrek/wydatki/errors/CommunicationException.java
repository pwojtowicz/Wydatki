package pl.wppiotrek.wydatki.errors;

public class CommunicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7789596311271124416L;

	/**
	 * kod b¸«du
	 */
	private final ExceptionErrorCodes code;

	private final Object object;

	public CommunicationException(String message, ExceptionErrorCodes code) {
		super(message);
		this.code = code;
		this.object = null;
	}

	public CommunicationException(String message, ExceptionErrorCodes code,
			Object object) {
		super(message);
		this.code = code;
		this.object = object;
	}

	/**
	 * Getter dla kodu b¸«du
	 * 
	 * @return kod b¸«du
	 */
	public ExceptionErrorCodes getErrorCode() {
		return code;
	}

	/**
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}

}
