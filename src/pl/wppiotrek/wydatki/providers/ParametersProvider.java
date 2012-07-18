package pl.wppiotrek.wydatki.providers;

import pl.wppiotrek.wydatki.entities.Parameter;
import pl.wppiotrek.wydatki.errors.CommunicationException;
import pl.wppiotrek.wydatki.interfaces.IHttpRequestToAsyncTaskCommunication;

public class ParametersProvider extends AbstractProvider<Parameter> {

	@Override
	public Parameter[] getAllObjects(
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {
		Provider<Parameter[]> provider = new Provider<Parameter[]>(
				Parameter[].class);
		Parameter[] objects = provider.getObjects(server + "/attributes",
				listener);
		return objects;
	}

	@Override
	public Parameter postObject(Parameter object,
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {
		String url = server + "/attributes/parameter";

		Provider<Parameter> provider = new Provider<Parameter>(Parameter.class);
		return provider.sendObject(url, object, true, listener);
	}

	@Override
	public void deleteObject(Parameter object,
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {
		String url = server + "/attributes/parameter/" + object.getId();

		Provider<Parameter> provider = new Provider<Parameter>(Parameter.class);
		provider.deleteObject(url, listener);

	}
}
