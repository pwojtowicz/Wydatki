package pl.wppiotrek.wydatki.managers;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.entities.OutputBundle;
import pl.wppiotrek.wydatki.errors.CommunicationException;
import pl.wppiotrek.wydatki.interfaces.IOnHTTPRequestJSONParsedResponse;
import pl.wppiotrek.wydatki.interfaces.IOnObjectsReceivedListener;

public class Manager<T> implements
		IOnHTTPRequestJSONParsedResponse<T, CommunicationException> {

	/**
	 * Listenery
	 */
	protected final ArrayList<IOnObjectsReceivedListener> listeners = new ArrayList<IOnObjectsReceivedListener>();

	/**
	 * Dodawanie listenera
	 * 
	 * @param listener
	 *            IOnObjectsReceivedListener
	 */
	public void setOnObjectsReceivedListener(IOnObjectsReceivedListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * Usuwanie listenera
	 * 
	 * @param listener
	 *            IOnObjectsReceivedListener
	 */
	public void removeOnObjectsReceivedListener(
			IOnObjectsReceivedListener listener) {
		this.listeners.remove(listener);
	}

	public void httpRequestSent() {
		for (IOnObjectsReceivedListener listener : listeners)
			if (listener != null)
				listener.onObjectsRequestStarted();
	}

	public void onJSONOutputReceived(
			OutputBundle<T, CommunicationException> bundle) {
		if (bundle.outputIsCorrect()) {
			for (IOnObjectsReceivedListener listener : listeners)
				if (listener != null) {
					listener.onObjectsRequestEnded();
					listener.onObjectsReceived(bundle.getObject(), null);
				}
		} else {
			for (IOnObjectsReceivedListener listener : listeners)
				if (listener != null) {
					listener.onObjectsRequestEnded();
					listener.onObjectsNotReceived(bundle.getException(), null);
				}
		}

	}

	public void httpRequestSendProgress(int progress) {
		for (IOnObjectsReceivedListener listener : listeners)
			if (listener != null)
				listener.onObjectsProgressUpdate(progress);
	}

}
