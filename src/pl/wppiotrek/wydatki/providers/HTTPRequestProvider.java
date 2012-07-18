package pl.wppiotrek.wydatki.providers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;

import pl.wppiotrek.wydatki.entities.CacheInfo;
import pl.wppiotrek.wydatki.interfaces.IHttpRequestToAsyncTaskCommunication;
import pl.wppiotrek.wydatki.support.AndroidGlobals;

public class HTTPRequestProvider {

	public static boolean isOnline = true;

	public static HTTPRequestBundle sendRequest(HTTPRequestType requestType,
			String address, IHttpRequestToAsyncTaskCommunication listener,
			String... json) {
		checkIsOnLine();
		switch (requestType) {
		case GET:
			return getRequest(requestType, address, listener, "");
		case POST:
			return postRequest(requestType, address, json[0], listener);
		default:
			return getRequest(requestType, address, listener, "");
		}
	}

	private static void checkIsOnLine() {
		// ConnectivityManager conMgr = (ConnectivityManager) WydatkiApp
		// .getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

		// NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
		// if (activeNetwork != null && activeNetwork.isConnected()) {
		isOnline = true;
		// } else {
		// isOnline = false;
		// }

	}

	private static HttpURLConnection getConnection(String address)
			throws IOException {
		URL url = new URL(address);
		HttpURLConnection connection = null;
		connection = (HttpURLConnection) url.openConnection();
		return connection;
	}

	private static HttpURLConnection setConnectionHeader(
			HttpURLConnection connection, String date, String contentType) {

		// oczekiwany typ zwracanych danych - JSON
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("Accept-Encoding", "gzip");
		if (date != null && date.length() > 0)
			connection.setRequestProperty("If-Modified-Since", date);

		connection.setConnectTimeout(HTTPRequestProperties.CONNECTION_TIMEOUT);
		connection.setReadTimeout(HTTPRequestProperties.READ_TIMEOUT);

		if (contentType != null)
			connection.setRequestProperty("Content-Type", contentType);
		return connection;
	}

	private static ByteArrayOutputStream readInpdutStream(
			InputStream inputStream, int fileSize,
			IHttpRequestToAsyncTaskCommunication listener) throws IOException {
		byte[] buf = new byte[4096];

		int ret = 0;
		long downloadedSize = 0;

		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		while ((ret = inputStream.read(buf)) > 0) {
			byteArray.write(buf, 0, ret);

			downloadedSize += ret;
			int progressPercent = 0;
			if (fileSize != 0)
				progressPercent = (int) (downloadedSize * 100) / fileSize;

			if ((progressPercent % 5) == 0) {
				if (listener != null) {
					listener.onObjectsProgressUpdate(progressPercent);
					if (listener.checkIsTaskCancled()) {
						break;
					}
				}
			}
		}
		return byteArray;
	}

	public static InputStream getConnectionInputStream(
			HttpURLConnection connection) throws IOException {
		InputStream input = null;
		String encoding = connection.getHeaderField("Content-Encoding");
		int responseCode = connection.getResponseCode();

		if (responseCode == 400)
			input = connection.getErrorStream();
		else
			input = connection.getInputStream();

		if (encoding != null && encoding.equals("gzip"))
			input = new GZIPInputStream(input);

		return input;
	}

	private static HTTPRequestBundle getRequest(HTTPRequestType requestType,
			String address, IHttpRequestToAsyncTaskCommunication listener,
			String date) {
		System.out.println("CONNECTION address: " + address);
		HttpURLConnection connection = null;
		String response = null;
		byte[] rawResponse = null;
		address = address.replace(" ", "%20");
		int responseCode = 0;
		AndroidGlobals globals = AndroidGlobals.getInstance();
		CacheInfo cache = null;
		if (globals.getCacheManager() != null) {
			cache = globals.getCacheManager().getCahceInfoForAddress(address,
					globals.getUserLogin(), null);
		}

		if (!isOnline && cache != null) {
			response = cache.response;
			rawResponse = cache.response.getBytes();
			responseCode = 2000;
		} else
			try {
				connection = getConnection(address);
				connection.setRequestMethod(requestType.toString());

				authenticate(connection);

				connection = setConnectionHeader(connection, null, null);

				connection.connect();
				responseCode = connection.getResponseCode();

				String eTag = connection.getHeaderField("etag");
				String encoding = connection.getHeaderField("Content-Encoding");

				if (cache != null && cache.eTAG.equals(eTag)) {
					System.out.println("CACHE: Read from CACHE " + address);
					response = cache.response;
					rawResponse = cache.response.getBytes();
				} else {

					int fileSize = connection.getContentLength();

					InputStream in = getConnectionInputStream(connection);

					ByteArrayOutputStream byteArray = readInpdutStream(in,
							fileSize, listener);
					try {
						response = byteArray.toString();
					} catch (Exception e) {

					}

					rawResponse = byteArray.toByteArray();

					if (responseCode == 200)// Nie cachowanie danych w przypadku
						// odpowiedzi innej niý OK
						saveChacheInfo(cache, eTag, "", address, response);
				}

				connection.disconnect();
				connection = null;

			} catch (IOException e) {
				response = e.getMessage();
			} finally {
				if (connection != null) {
					connection.disconnect();
					connection = null;
				}

			}

		HTTPRequestBundle bundle = new HTTPRequestBundle();
		bundle.setResponse(response);
		bundle.setRawResponse(rawResponse);
		bundle.setStatusCode(responseCode);

		return bundle;
	}

	private static HTTPRequestBundle postRequest(HTTPRequestType requestType,
			String address, String json,
			IHttpRequestToAsyncTaskCommunication listener) {
		boolean isSendOutput = false;
		boolean isRecivedInput = false;
		HttpURLConnection connection = null;
		String response = null;
		byte[] rawResponse = null;
		address = address.replace(" ", "%20");
		int responseCode = 0;

		if (!isOnline) {
			responseCode = 2000;

		} else
			try {
				connection = getConnection(address);

				// Typ po¸�czenia GET, POST itp.
				connection.setRequestMethod(requestType.toString());

				// obs¸uga po¸�czeÄ wymagaj�cych autentykacji
				authenticate(connection);
				// oczekiwany typ zwracanych danych - JSON
				connection.setDoOutput(true);
				connection.setChunkedStreamingMode(0);
				connection = setConnectionHeader(connection, null,
						"application/json");

				byte[] content = json.toString().getBytes();
				OutputStream output = null;
				try {
					output = connection.getOutputStream();

					int bufferLength = 4096;
					for (int i = 0; i < content.length; i += bufferLength) {
						int progress = (int) ((i / (float) content.length) * 100);
						if (listener != null) {
							listener.onObjectsProgressUpdate(progress);
							if (listener.checkIsTaskCancled()) {
								break;
							}
						}

						if (content.length - i >= bufferLength) {
							output.write(content, i, bufferLength);
						} else {
							output.write(content, i, content.length - i);
						}
					}
					if (listener != null)
						listener.onObjectsProgressUpdate(100);
				} finally {
					if (output != null) {
						output.close();
					}
				}
				isSendOutput = true;

				responseCode = connection.getResponseCode();

				if (responseCode == HttpsURLConnection.HTTP_OK) {

					InputStream in = getConnectionInputStream(connection);

					ByteArrayOutputStream byteArray = readInpdutStream(in, 0,
							null);

					response = byteArray.toString();
					rawResponse = byteArray.toByteArray();

				}
				isRecivedInput = true;

				connection.disconnect();
				connection = null;

			} catch (IOException e) {
				response = e.getMessage();
			} finally {
				if (connection != null) {
					connection.disconnect();
					connection = null;
				}

			}
		HTTPRequestBundle bundle = new HTTPRequestBundle();
		bundle.setResponse(response);
		bundle.setRawResponse(rawResponse);
		bundle.setStatusCode(responseCode);

		return bundle;
	}

	private static void authenticate(HttpURLConnection connection) {
		AndroidGlobals globals = AndroidGlobals.getInstance();

		String authenticationData = globals.getUserLogin() + ":"
				+ globals.getUserPassword();
		System.out.println("CONNECTION AuthenticationData: "
				+ authenticationData);
		String encodedString = new String(
				Base64.encodeBase64(authenticationData.getBytes()));
		String safeString = encodedString.replace('+', '-').replace('/', '_');
		connection
				.setRequestProperty("Authorization", "Basic " + encodedString);
		connection.setRequestProperty("Authorization", "Basic " + safeString);
	}

	private static void saveChacheInfo(CacheInfo cache, String eTag,
			String postTAG, String address, String response) {
		AndroidGlobals globals = AndroidGlobals.getInstance();
		if (eTag != null && globals.getCacheManager() != null) {
			if (cache == null) {
				System.out.println("CACHE: Insert to CACHE " + address);
				// Wyso¸ywane w przypadku, gdy w BD nie ma zapisanego
				// cache, wykonywany jest insert
				CacheInfo ci = new CacheInfo();
				ci.userLogin = globals.getUserLogin();
				ci.uri = address;
				ci.postTAG = postTAG;
				ci.eTAG = eTag;
				ci.response = response;
				ci.timestamp = new Date();
				globals.getCacheManager().insertCahceInfo(ci);
			} else {
				System.out.println("CACHE: UPDATE CACHE " + address);
				// Wywo¸ywane gdy w bazie danych jest zapisany cache,
				// wykonywany jest update
				cache.eTAG = eTag;
				cache.postTAG = postTAG;
				cache.response = response;
				cache.timestamp = new Date();
				globals.getCacheManager().updateCahceInfo(cache);
			}
		}
	}

}
