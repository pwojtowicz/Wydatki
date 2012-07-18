package pl.wppiotrek.wydatki.managers;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.asynctasks.TransactionAsyncTask;
import pl.wppiotrek.wydatki.entities.ItemsContainer;
import pl.wppiotrek.wydatki.entities.OutputBundle;
import pl.wppiotrek.wydatki.entities.Transaction;
import pl.wppiotrek.wydatki.errors.CommunicationException;
import pl.wppiotrek.wydatki.interfaces.IOnObjectsReceivedListener;
import pl.wppiotrek.wydatki.providers.ControlBundle;
import pl.wppiotrek.wydatki.units.AsyncTaskMethod;

public class TransactionManager extends Manager<Object> {

	private int from = 0;
	private int transactionToDownload = 20;
	/**
	 * Pobrane rekordy wynikowe od
	 */
	private int to = from + transactionToDownload;
	/**
	 * Wszystkich rekordów
	 */
	/**
	 * Pobrane rekordy wynikowe do tymczasowe
	 */
	private int tmpFrom;
	/**
	 * Pobrane rekordy wynikowe od tymczasowe
	 */
	private int tmpTo;

	/**
	 * Czy odÊwie˝anie si´ zacz´∏o
	 */
	private boolean afterRefresh = false;
	/**
	 * Czy adapter ma wyczyÊciç dane
	 */
	private boolean doClean = false;
	/**
	 * Pobieranie dodatkowych spraw
	 */
	private boolean wantMore = false;

	private ArrayList<TransactionAsyncTask> tasks = new ArrayList<TransactionAsyncTask>();

	public TransactionManager(IOnObjectsReceivedListener listener) {
		super.setOnObjectsReceivedListener(listener);
	}

	public void getAllTransactions() {
		this.afterRefresh = false;
		this.wantMore = false;
		this.tmpFrom = 0;
		this.tmpTo = tmpFrom + transactionToDownload;
		this.getTransactions(tmpFrom, tmpTo);
	}

	public void getMoreTransactions() {
		this.afterRefresh = false;
		this.wantMore = true;
		this.tmpFrom = to;
		this.tmpTo = tmpFrom + transactionToDownload;
		this.getTransactions(tmpFrom, tmpTo);
	}

	public void refresh() {
		this.afterRefresh = true;

		getTransactions(0, to);
	}

	private void getTransactions(int from, int to) {
		TransactionAsyncTask asyncTask = new TransactionAsyncTask(this,
				AsyncTaskMethod.GetAllObjects, 0, from, to);
		tasks.add(asyncTask);
		asyncTask.execute((Void) null);
	}

	public void getAllTransactionsForAccount(int accountId) {
		TransactionAsyncTask asyncTask = new TransactionAsyncTask(this,
				AsyncTaskMethod.GetAllObjects, accountId, from, to);
		tasks.add(asyncTask);
		asyncTask.execute((Void) null);
	}

	public void createNewTransaction(Transaction transaction) {
		TransactionAsyncTask asyncTask = new TransactionAsyncTask(this,
				AsyncTaskMethod.PostNewObject, transaction);
		tasks.add(asyncTask);
		asyncTask.execute((Void) null);
	}

	public void cancelAllTask() {
		for (TransactionAsyncTask item : tasks) {
			item.cancel(true);
		}
	}

	public void deleteTransaction(Transaction transaction) {
		TransactionAsyncTask asyncTask = new TransactionAsyncTask(this,
				AsyncTaskMethod.DeleteExistObject, transaction);
		tasks.add(asyncTask);
		asyncTask.execute((Void) null);
	}

	@Override
	public void onJSONOutputReceived(
			OutputBundle<Object, CommunicationException> bundle) {
		// ustawianie pakietu kontroli
		ControlBundle controlBundle = null;

		if (bundle.getObject() instanceof ItemsContainer<?>) {
			controlBundle = new ControlBundle();
			controlBundle.setAfterRefresh(afterRefresh);
			controlBundle.setDoClean(doClean);
			this.doClean = false;
			if (bundle.outputIsCorrect()) {
				this.from = tmpFrom;
				this.to = tmpTo;
				if (this.from == this.to)
					controlBundle.setObjectHasMoreItems(false);
				else
					controlBundle
							.setObjectHasMoreItems((to < ((ItemsContainer<Transaction>) bundle
									.getObject()).getTotalCount()));
			}
		}

		if (bundle.outputIsCorrect()) {
			for (IOnObjectsReceivedListener listener : listeners)
				if (listener != null) {
					listener.onObjectsRequestEnded();
					listener.onObjectsReceived(bundle.getObject(),
							controlBundle);
				}
		} else {
			controlBundle = new ControlBundle();
			for (IOnObjectsReceivedListener listener : listeners)
				if (listener != null) {
					listener.onObjectsRequestEnded();
					listener.onObjectsNotReceived(bundle.getException(),
							controlBundle);
				}
		}
	}
}
