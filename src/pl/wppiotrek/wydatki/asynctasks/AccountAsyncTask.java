package pl.wppiotrek.wydatki.asynctasks;

import pl.wppiotrek.wydatki.entities.Account;
import pl.wppiotrek.wydatki.entities.OutputBundle;
import pl.wppiotrek.wydatki.errors.CommunicationException;
import pl.wppiotrek.wydatki.interfaces.IOnHTTPRequestJSONParsedResponse;
import pl.wppiotrek.wydatki.providers.AccountProvider;
import pl.wppiotrek.wydatki.units.AsyncTaskMethod;

public class AccountAsyncTask extends AbstractAsyncTask {

	private AsyncTaskMethod method;
	private Account account;

	public AccountAsyncTask(
			IOnHTTPRequestJSONParsedResponse<Object, CommunicationException> listener,
			AsyncTaskMethod method) {
		this.listener = listener;
		this.method = method;
	}

	public AccountAsyncTask(
			IOnHTTPRequestJSONParsedResponse<Object, CommunicationException> listener,
			AsyncTaskMethod method, Account account) {
		this.listener = listener;
		this.method = method;
		this.account = account;
	}

	@Override
	protected StatusList doInBackground(Void... params) {
		AccountProvider provider = new AccountProvider();
		try {
			if (method == AsyncTaskMethod.GetAllObjects) {
				Account[] itemsContainer = provider.getAllObjects(this);
				bundle = new OutputBundle<Object, CommunicationException>();
				bundle.setObject(itemsContainer);
			} else if (method == AsyncTaskMethod.PostNewObject
					|| method == AsyncTaskMethod.EditExistObjects) {
				Account itemsContainer = provider.postObject(account, this);
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
