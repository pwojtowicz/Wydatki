package pl.wppiotrek.wydatki.providers;

public class HTTPRequestBundle {
	/**
	 * Odpowiedü z serwera
	 */
	private String response;
	/**
	 * Odpowiedü z serwera w bajtach
	 */
	private byte[] rawResponse;
	/**
	 * Status odpowiedzi GET 200 - ok, GET 401 - nie ok - authentication error
	 */
	private int statusCode;

	@Override
	public String toString() {
		return "[Response] \n" + response + "\n[StatusCode]\n" + statusCode;
	}

	// Gettery Settery
	/**
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(String response) {
		this.response = response;
	}

	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the rawResponse
	 */
	public byte[] getRawResponse() {
		return rawResponse;
	}

	/**
	 * @param rawResponse
	 *            the rawResponse to set
	 */
	public void setRawResponse(byte[] rawResponse) {
		this.rawResponse = rawResponse;
	}

}
