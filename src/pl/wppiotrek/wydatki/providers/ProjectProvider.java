package pl.wppiotrek.wydatki.providers;

import pl.wppiotrek.wydatki.entities.Project;
import pl.wppiotrek.wydatki.errors.CommunicationException;
import pl.wppiotrek.wydatki.interfaces.IHttpRequestToAsyncTaskCommunication;

public class ProjectProvider extends AbstractProvider<Project> {

	@Override
	public Project[] getAllObjects(IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {
		String url = server + "/projects";

		Provider<Project[]> provider = new Provider<Project[]>(Project[].class);
		Project[] objects = provider.getObjects(url, listener);
		return objects;
	}

	@Override
	public Project postObject(Project object,
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {
		String url = server + "/projects/project";

		Provider<Project> provider = new Provider<Project>(Project.class);
		return provider.sendObject(url, object, true, listener);
	}

	@Override
	public void deleteObject(Project object,
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {
		String url = server + "/projects/project/" + object.getId();

		Provider<Project> provider = new Provider<Project>(Project.class);
		provider.deleteObject(url, listener);
	}
}
