package pl.wppiotrek.wydatki.interfaces;

import pl.wppiotrek.wydatki.entities.OutputBundle;

public interface IOnHTTPRequestJSONParsedResponse<K, T> {

	// zapytanie zosta³o wys³ane
	public void httpRequestSent();

	// serwer zwróci³ odpowiedŸ
	public void onJSONOutputReceived(OutputBundle<K, T> bundle);

	// HTTPRequest zwóci³ postêp pobierania/wysy³ania
	public void httpRequestSendProgress(int progress);

}
