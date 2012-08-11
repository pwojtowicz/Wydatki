package pl.wppiotrek.wydatki.asynctasks;

import pl.wppiotrek.wydatki.entities.OutputBundle;
import pl.wppiotrek.wydatki.entities.StartContainer;
import pl.wppiotrek.wydatki.errors.CommunicationException;
import pl.wppiotrek.wydatki.interfaces.IOnHTTPRequestJSONParsedResponse;
import pl.wppiotrek.wydatki.providers.StartProvider;

public class StartAsyncTask extends AbstractAsyncTask {

	public StartAsyncTask(
			IOnHTTPRequestJSONParsedResponse<Object, CommunicationException> listener) {
		this.listener = listener;
	}

	@Override
	protected StatusList doInBackground(Void... params) {
		StartProvider provider = new StartProvider();
		try {
			StartContainer[] itemsContainer = provider.getAllObjects(this);
			bundle = new OutputBundle<Object, CommunicationException>();
			bundle.setObject(itemsContainer);

		} catch (CommunicationException e) {
			bundle = new OutputBundle<Object, CommunicationException>();
			bundle.setException(e);
			e.printStackTrace();
		}
		return null;
	}

}
