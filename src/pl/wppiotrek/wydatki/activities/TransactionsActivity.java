package pl.wppiotrek.wydatki.activities;

import java.util.ArrayList;
import java.util.Date;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.adapters.TransactionsAdapter;
import pl.wppiotrek.wydatki.adapters.TransactionsAdapter.TransactionAdapterObjectHandler;
import pl.wppiotrek.wydatki.entities.Transaction;
import pl.wppiotrek.wydatki.managers.TransactionManager;
import pl.wppiotrek.wydatki.support.AndroidGlobals;
import pl.wppiotrek.wydatki.support.DialogFactory;
import pl.wppiotrek.wydatki.units.DialogType;
import pl.wppiotrek.wydatki.units.ViewState;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class TransactionsActivity extends ProgressActivity implements
		OnItemLongClickListener, OnItemClickListener {

	private ListView list;
	private TransactionsAdapter adapter;
	private Transaction currentSelectedTransaction;
	private TransactionManager manager;
	private int accountId = 0;
	private Date lastRefreshDate;
	private boolean gettingMore;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transactions);
		super.setProgressDialogCancelable(false);
		super.setShowProgressBar(true);

		adapter = new TransactionsAdapter(this);
		manager = new TransactionManager(this);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			accountId = extras.getInt("accountId");
		}

		linkViews();
		configureViews();
		// refreshActivity(false);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			refreshActivity(true);
			break;
		}
		return true;
	}

	private void refreshList(ArrayList<Transaction> elements) {

		list.setAdapter(adapter);
	}

	public void refreshIssues() {

		if (!adapter.isRefreshing()) {
			adapter.setRefreshing(true);
			manager.refresh();
		}
	}

	@Override
	public void refreshActivity(boolean isForceRefresh) {

		if (isForceRefresh) {
			manager.refresh();
		}
	}

	@Override
	public void leaveActivity(int requestCode) {
		// manager.cancelAllTask();
	}

	@Override
	public void linkViews() {
		list = (ListView) findViewById(R.id.transactions_listview);

	}

	@Override
	public void configureViews() {
		list.setOnItemLongClickListener(this);
		list.setOnItemClickListener(this);
		// manager.setOnObjectsReceivedListener(this);
		manager.setOnObjectsReceivedListener(adapter);

		manager.getAllTransactions();

		// list.setOnItemClickListener(this);
		list.setAdapter(adapter);
	}

	@Override
	public void onObjectsReceived(Object object, Object object2) {
		if (object instanceof String && ((String) object).equals("OK")) {
			AndroidGlobals.getInstance().setRefreshActivity(true);
			refreshActivity(true);
		} else {
			this.lastRefreshDate = new Date();
			if (gettingMore)
				this.gettingMore = false;
		}
	}

	// @Override
	// public void onObjectsReceived(Object object, Object object2) {
	// if (object instanceof String && ((String) object).equals("OK")) {
	// AndroidGlobals.getInstance().setRefreshActivity(true);
	// refreshActivity(true);
	// } else if (object instanceof ItemsContainer) {
	// AndroidGlobals g = AndroidGlobals.getInstance();
	// ArrayList<Category> categories = g.getCategoryList();
	// ArrayList<Account> accounts = g.getAccountList();
	//
	// ArrayList<Transaction> elements = new ArrayList<Transaction>();
	// ItemsContainer<Transaction> itemContainer = (ItemsContainer<Transaction>)
	// object;
	// for (Transaction item : itemContainer.getItems()) {
	// if (item.getAccMinus() != null) {
	// Account from = ListSupport.getAccountById(item
	// .getAccMinus().getId(), accounts);
	// if (from != null)
	// item.getAccMinus().setName(from.getName());
	// }
	// if (item.getAccPlus() != null) {
	// Account to = ListSupport.getAccountById(item.getAccPlus()
	// .getId(), accounts);
	// if (to != null)
	// item.getAccPlus().setName(to.getName());
	// }
	// if (item.getCategory() != null) {
	// Category cat = ListSupport.getCategoryById(item
	// .getCategory().getId(), categories);
	// if (cat != null)
	// item.getCategory().setName(cat.getName());
	// }
	//
	// elements.add(item);
	// }
	//
	// refreshList(elements);
	// }
	// }

	public boolean onItemLongClick(AdapterView<?> arg0, View view, int arg2,
			long arg3) {
		super.vibrate();

		TransactionAdapterObjectHandler oh = (TransactionAdapterObjectHandler) view
				.getTag();

		Bundle b = new Bundle();

		final CharSequence[] items = { getText(R.string.transaction_remove) };

		b.putCharSequenceArray("options", items);
		this.currentSelectedTransaction = oh.transaction;

		AlertDialog dialog = DialogFactory.create(DialogType.OptionsDialog, b,
				this, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						switch (item) {
						case 0:
							showDialogQuestion();
							break;

						}
					}
				});
		if (dialog != null)
			dialog.show();

		return false;
	}

	protected void showDialogQuestion() {

		DialogInterface.OnClickListener positiveClick = new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				removeTransaction();
			}
		};
		DialogInterface.OnClickListener negativeClick = new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		};
		AlertDialog dialog = DialogFactory.createOkCancelDialog(this,
				positiveClick, negativeClick,
				R.string.dialog_question_remove_transaction);
		dialog.show();
	}

	protected void removeTransaction() {
		manager.deleteTransaction(currentSelectedTransaction);
	}

	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		Object object = view.getTag();
		if (object instanceof ViewState) {
			ViewState tag = (ViewState) object;
			if (tag != ViewState.NoObjects) {
				if (adapter.getCount() == 1) {
					manager.getAllTransactions();
				} else {
					this.gettingMore = true;
					manager.getMoreTransactions();
				}
			}

		} else if (object instanceof TransactionAdapterObjectHandler) {
			AndroidGlobals.getInstance().setCurrentSelectedTransaction(
					((TransactionAdapterObjectHandler) object).transaction);
			Intent intent = new Intent(this, TransactionDetailsInput.class);
			startActivity(intent);
		}
	}
}
