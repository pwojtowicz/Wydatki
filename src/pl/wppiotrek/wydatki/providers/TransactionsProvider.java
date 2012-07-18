package pl.wppiotrek.wydatki.providers;

import pl.wppiotrek.wydatki.entities.ItemsContainer;
import pl.wppiotrek.wydatki.entities.Transaction;
import pl.wppiotrek.wydatki.errors.CommunicationException;
import pl.wppiotrek.wydatki.interfaces.IHttpRequestToAsyncTaskCommunication;
import pl.wppiotrek.wydatki.support.AndroidGlobals;

public class TransactionsProvider {
	String server = AndroidGlobals.getInstance().getServerAddress();

	public ItemsContainer<Transaction> getAllTransactions(int from, int to,
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {
		String url = server + "/transactions?account=0&from=" + from + "&to="
				+ to;

		Provider<Transaction> provider = new Provider<Transaction>(
				Transaction.class);
		return provider.getTransactions(0, 100, url, listener);
	}

	public Transaction postTransaction(Transaction transaction,
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {
		String url = server + "/transactions/transaction";

		Provider<Transaction> provider = new Provider<Transaction>(
				Transaction.class);
		return provider.sendObject(url, transaction, true, listener);
	}

	public void deleteTransaction(Transaction transaction,
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {
		String url = server + "/transactions/transaction/"
				+ transaction.getId();

		Provider<Transaction> provider = new Provider<Transaction>(
				Transaction.class);
		provider.deleteObject(url, listener);
	}
}
