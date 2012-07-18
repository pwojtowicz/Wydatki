package pl.wppiotrek.wydatki.asynctasks;

import pl.wppiotrek.wydatki.entities.ItemsContainer;
import pl.wppiotrek.wydatki.entities.OutputBundle;
import pl.wppiotrek.wydatki.entities.Transaction;
import pl.wppiotrek.wydatki.errors.CommunicationException;
import pl.wppiotrek.wydatki.interfaces.IOnHTTPRequestJSONParsedResponse;
import pl.wppiotrek.wydatki.providers.TransactionsProvider;
import pl.wppiotrek.wydatki.units.AsyncTaskMethod;

public class TransactionAsyncTask extends AbstractAsyncTask {

	private AsyncTaskMethod methodName;
	private Transaction transaction;
	private final int from;
	private final int to;
	private final int accountId;

	public TransactionAsyncTask(
			IOnHTTPRequestJSONParsedResponse<Object, CommunicationException> listener,
			AsyncTaskMethod method, Transaction transaction) {
		this.listener = listener;
		this.methodName = method;
		this.transaction = transaction;
		this.from = -1;
		this.to = -1;
		this.accountId = 0;
	}

	public TransactionAsyncTask(
			IOnHTTPRequestJSONParsedResponse<Object, CommunicationException> listener,
			AsyncTaskMethod method, int accountId, int from, int to) {
		this.listener = listener;
		this.methodName = method;
		this.from = from;
		this.to = to;
		this.accountId = accountId;
	}

	@Override
	protected StatusList doInBackground(Void... arg0) {
		TransactionsProvider tranProvider = new TransactionsProvider();
		try {
			if (methodName == AsyncTaskMethod.GetAllObjects) {
				ItemsContainer<Transaction> itemsContainer = tranProvider
						.getAllTransactions(from, to, this);
				bundle = new OutputBundle<Object, CommunicationException>();
				bundle.setObject(itemsContainer);
			} else if (methodName == AsyncTaskMethod.DeleteExistObject) {
				tranProvider.deleteTransaction(transaction, this);
				bundle = new OutputBundle<Object, CommunicationException>();
				bundle.setObject("OK");
			} else if (methodName == AsyncTaskMethod.PostNewObject) {
				Transaction itemsContainer = tranProvider.postTransaction(
						transaction, this);
				bundle = new OutputBundle<Object, CommunicationException>();
				bundle.setObject(itemsContainer);
			} else if (methodName == AsyncTaskMethod.EditExistObjects) {

			}

		} catch (CommunicationException e) {
			bundle = new OutputBundle<Object, CommunicationException>();
			bundle.setException(e);
			e.printStackTrace();
		}
		return null;
	}

}
