package pl.wppiotrek.wydatki.interfaces;

import pl.wppiotrek.wydatki.entities.OutputBundle;

public interface IOnHTTPRequestJSONParsedResponse<K, T> {

	// zapytanie zosta�o wys�ane
	public void httpRequestSent();

	// serwer zwr�ci� odpowied�
	public void onJSONOutputReceived(OutputBundle<K, T> bundle);

	// HTTPRequest zw�ci� post�p pobierania/wysy�ania
	public void httpRequestSendProgress(int progress);

}
