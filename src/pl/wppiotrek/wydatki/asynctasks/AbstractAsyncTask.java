package pl.wppiotrek.wydatki.asynctasks;

import pl.wppiotrek.wydatki.entities.OutputBundle;
import pl.wppiotrek.wydatki.errors.CommunicationException;
import pl.wppiotrek.wydatki.interfaces.IHttpRequestToAsyncTaskCommunication;
import pl.wppiotrek.wydatki.interfaces.IOnHTTPRequestJSONParsedResponse;
import android.os.AsyncTask;

public abstract class AbstractAsyncTask extends
		AsyncTask<Void, Void, StatusList> implements
		IHttpRequestToAsyncTaskCommunication {

	protected OutputBundle<Object, CommunicationException> bundle;
	protected IOnHTTPRequestJSONParsedResponse<Object, CommunicationException> listener;

	@Override
	protected void onPreExecute() {
		listener.httpRequestSent();
	}

	@Override
	protected void onPostExecute(StatusList result) {
		listener.onJSONOutputReceived(bundle);
	}

	@Override
	protected void onCancelled() {

		super.onCancelled();
	}

	public boolean checkIsTaskCancled() {
		return this.isCancelled();
	}

	public void onObjectsProgressUpdate(int progressPercent) {
		if (listener != null) {
			listener.httpRequestSendProgress(progressPercent);
		}
	}

}
