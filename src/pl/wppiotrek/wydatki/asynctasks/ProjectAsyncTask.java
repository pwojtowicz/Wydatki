package pl.wppiotrek.wydatki.asynctasks;

import pl.wppiotrek.wydatki.entities.OutputBundle;
import pl.wppiotrek.wydatki.entities.Project;
import pl.wppiotrek.wydatki.errors.CommunicationException;
import pl.wppiotrek.wydatki.interfaces.IOnHTTPRequestJSONParsedResponse;
import pl.wppiotrek.wydatki.providers.ProjectProvider;
import pl.wppiotrek.wydatki.units.AsyncTaskMethod;

public class ProjectAsyncTask extends AbstractAsyncTask {

	private AsyncTaskMethod method;
	private Project project;

	public ProjectAsyncTask(
			IOnHTTPRequestJSONParsedResponse<Object, CommunicationException> listener,
			AsyncTaskMethod method, Project project) {
		this.listener = listener;
		this.method = method;
		this.project = project;
	}

	@Override
	protected StatusList doInBackground(Void... params) {
		ProjectProvider provider = new ProjectProvider();
		try {
			if (method == AsyncTaskMethod.GetAllObjects) {
				Project[] itemsContainer = provider.getAllObjects(this);
				bundle = new OutputBundle<Object, CommunicationException>();
				bundle.setObject(itemsContainer);
			} else if (method == AsyncTaskMethod.DeleteExistObject) {
				provider.deleteObject(project, this);
				bundle = new OutputBundle<Object, CommunicationException>();
				bundle.setObject("OK");
			} else if (method == AsyncTaskMethod.PostNewObject
					|| method == AsyncTaskMethod.EditExistObjects) {
				Project itemsContainer = provider.postObject(project, this);
				bundle = new OutputBundle<Object, CommunicationException>();
				bundle.setObject(itemsContainer);
			}
		} catch (CommunicationException e) {
			bundle = new OutputBundle<Object, CommunicationException>();
			bundle.setException(e);
			e.printStackTrace();
		}
		return null;
	}

}
