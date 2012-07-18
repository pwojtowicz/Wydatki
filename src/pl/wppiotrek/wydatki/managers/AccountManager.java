package pl.wppiotrek.wydatki.managers;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.asynctasks.AccountAsyncTask;
import pl.wppiotrek.wydatki.entities.Account;
import pl.wppiotrek.wydatki.interfaces.IOnObjectsReceivedListener;
import pl.wppiotrek.wydatki.units.AsyncTaskMethod;

public class AccountManager extends Manager<Object> {

	private ArrayList<AccountAsyncTask> tasks = new ArrayList<AccountAsyncTask>();

	public AccountManager(IOnObjectsReceivedListener listener) {
		super.setOnObjectsReceivedListener(listener);
	}

	public void getAllAccounts() {
		AccountAsyncTask asyncTask = new AccountAsyncTask(this,
				AsyncTaskMethod.GetAllObjects);
		tasks.add(asyncTask);
		asyncTask.execute((Void) null);
	}

	public void createNewAccount(Account account) {
		AccountAsyncTask asyncTask = new AccountAsyncTask(this,
				AsyncTaskMethod.PostNewObject, account);
		tasks.add(asyncTask);
		asyncTask.execute((Void) null);
	}

	public void editAccount(Account account) {
		AccountAsyncTask asyncTask = new AccountAsyncTask(this,
				AsyncTaskMethod.EditExistObjects, account);
		tasks.add(asyncTask);
		asyncTask.execute((Void) null);
	}

	public void cancelAllTask() {
		for (AccountAsyncTask item : tasks) {
			item.cancel(true);
		}
	}

}
