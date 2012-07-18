package pl.wppiotrek.wydatki.asynctasks;

import pl.wppiotrek.wydatki.entities.OutputBundle;
import pl.wppiotrek.wydatki.entities.Parameter;
import pl.wppiotrek.wydatki.errors.CommunicationException;
import pl.wppiotrek.wydatki.interfaces.IOnHTTPRequestJSONParsedResponse;
import pl.wppiotrek.wydatki.providers.ParametersProvider;
import pl.wppiotrek.wydatki.units.AsyncTaskMethod;

public class ParametersAsyncTask extends AbstractAsyncTask {

	private final Parameter currentParameter;
	private AsyncTaskMethod method;

	public ParametersAsyncTask(
			AsyncTaskMethod method,
			IOnHTTPRequestJSONParsedResponse<Object, CommunicationException> listener) {
		this.listener = listener;
		this.currentParameter = null;
		this.method = method;
	}

	public ParametersAsyncTask(
			AsyncTaskMethod method,
			IOnHTTPRequestJSONParsedResponse<Object, CommunicationException> listener,
			Parameter currentParameter) {
		this.method = method;
		this.listener = listener;
		this.currentParameter = currentParameter;
	}

	@Override
	protected StatusList doInBackground(Void... params) {
		ParametersProvider provider = new ParametersProvider();
		try {
			if (method == AsyncTaskMethod.GetAllObjects) {
				Parameter[] itemsContainer = provider.getAllObjects(this);
				bundle = new OutputBundle<Object, CommunicationException>();
				bundle.setObject(itemsContainer);
				// throw new CommunicationException("test",
				// ExceptionErrorCodes.CommunicationProblems);
			} else if (method == AsyncTaskMethod.PostNewObject
					|| method == AsyncTaskMethod.EditExistObjects) {
				Parameter itemsContainer = provider.postObject(
						currentParameter, this);
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
