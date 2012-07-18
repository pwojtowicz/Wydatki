package pl.wppiotrek.wydatki.entities;

public class OutputBundle<K, T> {
	/**
	 * Obiekt wynikowy
	 */
	K object;
	/**
	 * B³¹d gdy obiekt wynikowy jest nieosi¹galny
	 */
	T exception;

	public boolean outputIsCorrect() {
		return (object != null);
	}

	// Getters / Setters
	/**
	 * @return the object
	 */
	public K getObject() {
		return object;
	}

	/**
	 * @return the error
	 */
	public T getException() {
		return exception;
	}

	/**
	 * @param object
	 *            the object to set
	 */
	public void setObject(K object) {
		this.object = object;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setException(T error) {
		this.exception = error;
	}

}
