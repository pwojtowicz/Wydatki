package pl.wppiotrek.wydatki.providers;

import pl.wppiotrek.wydatki.errors.CommunicationException;
import pl.wppiotrek.wydatki.interfaces.IHttpRequestToAsyncTaskCommunication;
import pl.wppiotrek.wydatki.support.AndroidGlobals;

public abstract class AbstractProvider<T> {

	String server = AndroidGlobals.getInstance().getServerAddress();

	public abstract T[] getAllObjects(
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException;

	public abstract T postObject(T object,
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException;

	public abstract void deleteObject(T object,
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException;
}
