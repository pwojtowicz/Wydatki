package pl.wppiotrek.wydatki.activities;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.activities.edit.EditAccountActivity;
import pl.wppiotrek.wydatki.adapters.AccountsAdapter;
import pl.wppiotrek.wydatki.adapters.AccountsAdapter.AccountsAdapterObjectHandler;
import pl.wppiotrek.wydatki.entities.Account;
import pl.wppiotrek.wydatki.entities.StartContainer;
import pl.wppiotrek.wydatki.managers.AccountManager;
import pl.wppiotrek.wydatki.managers.DataBaseManager;
import pl.wppiotrek.wydatki.managers.DownloadDataManager;
import pl.wppiotrek.wydatki.managers.PreferencesManager;
import pl.wppiotrek.wydatki.support.AndroidGlobals;
import pl.wppiotrek.wydatki.support.DialogFactory;
import pl.wppiotrek.wydatki.support.WydatkiApp;
import pl.wppiotrek.wydatki.units.DialogType;
import pl.wppiotrek.wydatki.units.RefreshOptions;
import pl.wppiotrek.wydatki.units.ResultCodes;
import pl.wppiotrek.wydatki.units.UnitConverter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ProgressActivity implements OnClickListener,
		OnItemClickListener, OnItemLongClickListener {

	private ListView list;
	private AccountsAdapter adapter;
	private TextView tbx_accounts_balance;
	private DownloadDataManager ddManager;

	@Override
	public void onResume() {
		super.onResume();

		if (AndroidGlobals.getInstance().isRefreshActivity()) {
			refreshActivity(true);
			AndroidGlobals.getInstance().setRefreshActivity(false);
		} else
			refreshActivity(false);

		AndroidGlobals.getInstance().setCacheManager(new DataBaseManager(this));
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new WydatkiApp(this);

		setContentView(R.layout.activity_main);
		super.setProgressDialogCancelable(false);

		PreferencesManager.loadPreferences(this);

		new DataBaseManager(this).deleteOldCacheInfo(2);

		linkViews();
		configureViews();
		// refreshActivity(false);

	}

	public void linkViews() {
		list = (ListView) findViewById(R.id.account_listview);
		tbx_accounts_balance = (TextView) findViewById(R.id.account_tbx_balance);

		list.setOnItemClickListener(this);
		list.setOnItemLongClickListener(this);
		Button btn_newTransfer = (Button) findViewById(R.id.account_btn_new_transfer);
		btn_newTransfer.setTag("transfer");
		btn_newTransfer.setOnClickListener(this);

		Button btn_newTransaction = (Button) findViewById(R.id.account_btn_new_transaction);
		btn_newTransaction.setTag("transaction");
		btn_newTransaction.setOnClickListener(this);
	}

	public void onClick(View view) {
		String tag = (String) view.getTag();
		Intent intent = null;
		Bundle extras = new Bundle();
		if (tag.equals("transfer")) {
			extras.putBoolean("isTransfer", true);
			intent = new Intent(this, InvokeTransactionActivity.class);
			intent.putExtras(extras);
		}
		if (tag.equals("transaction")) {
			extras.putBoolean("isTransaction", true);
			intent = new Intent(this, InvokeTransactionActivity.class);
			intent.putExtras(extras);
		}

		if (intent != null)
			startActivityForResult(intent,
					ResultCodes.START_ACTIVITY_INVOKE_TRANSACTION);
	}

	public void configureViews() {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onObjectsReceived(Object object, Object object2) {
		super.onObjectsReceived(object, object2);
		if (object instanceof String && ((String) object).equals("OK")) {

		} else if (object instanceof Account) {
			AndroidGlobals.getInstance().updateAccountsList((Account) object);
			refreshList();
		} else if (object instanceof Account[]) {
			refreshList();
		} else if (object instanceof StartContainer[]) {
			refreshList();
		}
	}

	@Override
	public void onObjectsNotReceived(Object exception, Object object) {
		super.onObjectsNotReceived(exception, object);
	}

	private void refreshList() {
		Double value = 0.0;
		AndroidGlobals globals = AndroidGlobals.getInstance();
		ArrayList<Account> accountsDictionary = null;
		if (globals.getAccountsDictionary() != null) {
			accountsDictionary = globals.getAccountsList();
			ArrayList<Account> accounts = new ArrayList<Account>();

			for (Account account : accountsDictionary) {
				accounts.add(account);
			}

			if (accounts != null) {

				for (Account account : accounts) {
					if (account.isSumInGlobalBalance())
						value += account.getBalance();
				}
				tbx_accounts_balance.setText(UnitConverter
						.doubleToCurrency(value));

				if (value < 0)
					tbx_accounts_balance.setTextColor(getResources().getColor(
							R.color.darkRed));
				else if (value > 0)
					tbx_accounts_balance.setTextColor(getResources().getColor(
							R.color.darkGreen));

				adapter = new AccountsAdapter(this, accounts);
				list.setAdapter(adapter);
			}
		}
	}

	@Override
	public void refreshActivity(boolean isForceRefresh) {
		if (ddManager == null) {
			ddManager = new DownloadDataManager(this,
					RefreshOptions.refreshAll, this);
			refreshList();
		} else {

			if (isForceRefresh)
				ddManager.refresh(isForceRefresh, RefreshOptions.refreshAll);
			else {
				refreshList();
			}
		}

	}

	@Override
	public void leaveActivity(int requestCode) {
		ddManager.cancleDownload();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			refreshActivity(true);
			break;
		case R.id.menu_settings:
			Intent intent = new Intent(this, UnitActivity.class);
			startActivity(intent);
			break;
		}
		return true;
	}

	public boolean onItemLongClick(AdapterView<?> arg0, View view, int arg2,
			long arg3) {
		super.vibrate();

		AccountsAdapterObjectHandler oh = (AccountsAdapterObjectHandler) view
				.getTag();

		Bundle b = new Bundle();

		final CharSequence[] items = {
				getText(R.string.account_show),
				getText(R.string.account_update),
				oh.account.isActive() ? getText(R.string.lock) + " "
						+ getText(R.string.entity_account)
						: getText(R.string.unlock) + " "
								+ getText(R.string.entity_account) };

		b.putCharSequenceArray("options", items);
		AndroidGlobals.getInstance().setCurrentSelectedAccount(oh.account);

		AlertDialog dialog = DialogFactory.create(DialogType.OptionsDialog, b,
				this, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						onDialogItemSelected(item);
					}
				});
		if (dialog != null)
			dialog.show();

		return false;
	}

	protected void onDialogItemSelected(int item) {
		if (item == 1) {
			showEditActivity(true);
		}
		if (item == 2) {
			changeLock();
		}
		if (item == 3) {
			removeObject();
		}
	}

	private void removeObject() {
		// TODO Auto-generated method stub

	}

	private void changeLock() {
		super.setProgressDialogCancelable(false);
		super.setIsSendProgressBar(true);
		super.setShowProgressBar(true);
		AccountManager manager = new AccountManager(this);
		Account account = AndroidGlobals.getInstance()
				.getCurrentSelectedAccount();
		account.setIsActive(!account.isActive());
		manager.editAccount(account);

	}

	protected void showEditActivity(boolean isUpdate) {
		Intent intent = new Intent(this, EditAccountActivity.class);
		if (isUpdate) {
			Bundle b = new Bundle();
			b.putBoolean("isUpdate", true);
			intent.putExtras(b);
		}
		startActivityForResult(intent, ResultCodes.START_ACTIVITY_EDIT_ACOOUNT);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ResultCodes.START_ACTIVITY_EDIT_ACOOUNT
				|| requestCode == ResultCodes.START_ACTIVITY_INVOKE_TRANSACTION) {
			if (resultCode == ResultCodes.RESULT_NEED_REFRESH) {
				refreshActivity(true);
			}
		}
	}

	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		AccountsAdapterObjectHandler oh = (AccountsAdapterObjectHandler) view
				.getTag();
		Bundle b = new Bundle();
		b.putInt("accountId", oh.account.getId());

		Intent intent = new Intent(this, TransactionsActivity.class);
		intent.putExtras(b);
		startActivity(intent);

	}

}
