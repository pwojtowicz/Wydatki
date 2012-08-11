package pl.wppiotrek.wydatki.providers;

import pl.wppiotrek.wydatki.entities.StartContainer;
import pl.wppiotrek.wydatki.errors.CommunicationException;
import pl.wppiotrek.wydatki.interfaces.IHttpRequestToAsyncTaskCommunication;

public class StartProvider extends AbstractProvider<StartContainer> {

	@Override
	public StartContainer[] getAllObjects(
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {
		String url = server + "/start";

		Provider<StartContainer> provider = new Provider<StartContainer>(
				StartContainer.class);
		StartContainer[] objects = new StartContainer[1];
		objects[0] = provider.getObjects(url, listener);
		return objects;
	}

	@Override
	public StartContainer postObject(StartContainer object,
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteObject(StartContainer object,
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {
		// TODO Auto-generated method stub

	}

}
