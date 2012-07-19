package pl.wppiotrek.wydatki.activities.edit;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.entities.Account;
import pl.wppiotrek.wydatki.managers.AccountManager;
import pl.wppiotrek.wydatki.support.AndroidGlobals;
import pl.wppiotrek.wydatki.support.ListSupport;
import pl.wppiotrek.wydatki.units.ResultCodes;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ToggleButton;

public class EditAccountActivity extends EditAbstractActivity<Account> {

	private EditText etbx_balance;
	private CheckBox cbx_visibleForAll;
	private ToggleButton tbtn_isPositive;
	private CheckBox cbx_sumInGlobalbalance;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.activity_new_account);

	}

	@Override
	public void linkViews() {
		etbx_balance = (EditText) findViewById(R.id.edit_account_etxb_balance);
		cbx_visibleForAll = (CheckBox) findViewById(R.id.edit_account_cbx_visibleForAll);
		cbx_sumInGlobalbalance = (CheckBox) findViewById(R.id.edit_account_cbx_sumInGlobalbalance);
		tbtn_isPositive = (ToggleButton) findViewById(R.id.edit_account_tbtn_isPositive);
	}

	@Override
	public void configureViews() {
		if (isUpdate) {
			currentObject = AndroidGlobals.getInstance()
					.getCurrentSelectedAccount();

			etbx_name.setText(currentObject.getName());
			etbx_balance.setText(String.valueOf(Math.abs(currentObject
					.getBalance())));
			if (currentObject.getBalance() > 0)
				tbtn_isPositive.setChecked(true);

			cbx_isActive.setChecked(currentObject.isActive());
			cbx_visibleForAll.setChecked(currentObject.isVisibleForAll());
			cbx_sumInGlobalbalance.setChecked(currentObject
					.isSumInGlobalBalance());
		}

	}

	@Override
	public void refreshActivity(boolean isForceRefresh) {
		if (isForceRefresh)
			saveObject();
	}

	@Override
	public void leaveActivity(int requestCode) {
		Intent intent = this.getIntent();
		this.setResult(requestCode, intent);
		finish();
	}

	@Override
	public void onObjectsReceived(Object object, Object object2) {
		super.onObjectsReceived(object, object2);
		if (currentObject != null
				&& currentObject.getId() == ((Account) object).getId()) {
			AndroidGlobals globals = AndroidGlobals.getInstance();
			globals.updateAccountsList(currentObject);

		} else {
			AndroidGlobals globals = AndroidGlobals.getInstance();
			Account a = (Account) object;
			globals.updateAccountsList(a);
			// globals.getAccountsDictionary().put(a.getId(), a);
		}
		leaveActivity(ResultCodes.RESULT_NEED_REFRESH);
	}

	@Override
	protected void prepareToSave() {
		boolean isValid = false;
		double value = 0;

		if (isUpdate
				|| (etbx_name.getText().length() > 0 && !ListSupport
						.isAccountNameUsed(etbx_name.getText().toString()))) {
			if (etbx_balance.getText().length() > 0) {
				try {
					value = Double.parseDouble(etbx_balance.getText()
							.toString())
							* (tbtn_isPositive.isChecked() ? 1 : -1);
					isValid = true;
				} catch (Exception e) {

				}
			}
		}

		if (isValid) {
			Account account = new Account();

			if (isUpdate) {
				int accountId = currentObject.getId();
				account.setId(accountId);
			}

			account.setName(etbx_name.getText().toString());
			account.setIsActive(cbx_isActive.isChecked());
			account.setIsVisibleForAll(cbx_visibleForAll.isChecked());
			account.setIsSumInGlobalBalance(cbx_sumInGlobalbalance.isChecked());
			account.setBalance(value);
			this.currentObject = account;
			saveObject();
		}
	}

	@Override
	protected void saveObject() {
		super.setProgressDialogCancelable(false);
		super.setIsSendProgressBar(true);
		super.setShowProgressBar(true);
		AccountManager manager = new AccountManager(this);
		if (isUpdate)
			manager.editAccount(currentObject);
		else
			manager.createNewAccount(currentObject);
	}

}
