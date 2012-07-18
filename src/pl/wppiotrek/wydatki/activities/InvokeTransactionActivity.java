package pl.wppiotrek.wydatki.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.adapters.InvokeTransactionAdapter;
import pl.wppiotrek.wydatki.adapters.SpinnerAdapter;
import pl.wppiotrek.wydatki.adapters.SpinnerAdapter.SpinerHelper;
import pl.wppiotrek.wydatki.entities.Account;
import pl.wppiotrek.wydatki.entities.Category;
import pl.wppiotrek.wydatki.entities.InvokeTransactionParameter;
import pl.wppiotrek.wydatki.entities.Parameter;
import pl.wppiotrek.wydatki.entities.Project;
import pl.wppiotrek.wydatki.entities.SpinnerObject;
import pl.wppiotrek.wydatki.entities.Transaction;
import pl.wppiotrek.wydatki.managers.DownloadDataManager;
import pl.wppiotrek.wydatki.managers.TransactionManager;
import pl.wppiotrek.wydatki.support.AndroidGlobals;
import pl.wppiotrek.wydatki.support.DialogFactory;
import pl.wppiotrek.wydatki.units.DialogType;
import pl.wppiotrek.wydatki.units.RefreshOptions;
import pl.wppiotrek.wydatki.units.ResultCodes;
import pl.wppiotrek.wydatki.units.UnitConverter;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class InvokeTransactionActivity extends ProgressActivity implements
		OnClickListener {

	private static final int CalculatorInput_REQUESTCODE = 123654;

	int hour, minute;
	int Year, month, day;

	static final int TIME_DIALOG_ID = 0;
	static final int DATE_DIALOG_ID = 1;

	private ListView list;
	private boolean isTransfer = false;
	private Spinner accountFrom;
	private Spinner accountTo;
	private Spinner category;
	private LinearLayout ll_accountTo;
	private InvokeTransactionAdapter adapter;
	private TextView additionParametersTextView;
	private EditText note;
	private EditText value;
	private boolean hasAdditionalParameters;
	private ToggleButton isPositive;
	private LinearLayout ll_category;
	private Button btn_save;
	private Button btn_cancel;

	private DownloadDataManager ddManager;

	private Button btn_time;

	private Button btn_date;

	private Spinner spn_project;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invoke_transaction);

		Bundle b = getIntent().getExtras();
		if (b != null) {
			isTransfer = b.getBoolean("isTransfer");
		}

		linkViews();
		configureViews();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.refresh_menu, menu);
		return true;
	}

	@Override
	public void refreshActivity(boolean isForceRefresh) {
		// TODO Auto-generated method stub

	}

	@Override
	public void leaveActivity(int requestCode) {
		finish();

	}

	@Override
	public void linkViews() {
		adapter = new InvokeTransactionAdapter(this);
		LayoutInflater layoutInflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View header = layoutInflater.inflate(
				R.layout.invoke_transaction_header, null);

		View footer = layoutInflater.inflate(
				R.layout.invoke_transaction_footer, null);

		btn_save = (Button) findViewById(R.id.bSave);
		btn_cancel = (Button) findViewById(R.id.bCancel);

		btn_cancel.setTag("cancel");
		btn_save.setTag("save");

		btn_cancel.setOnClickListener(this);
		btn_save.setOnClickListener(this);

		ll_accountTo = (LinearLayout) header
				.findViewById(R.id.invoke_transaction_header_ll_account_to);

		ll_category = (LinearLayout) header
				.findViewById(R.id.invoke_transaction_header_ll_category);

		isPositive = (ToggleButton) header
				.findViewById(R.id.invoke_transaction_header_tbn_ispositive);

		additionParametersTextView = (TextView) header
				.findViewById(R.id.invoke_transaction_header_addition_parameters);

		accountFrom = (Spinner) header
				.findViewById(R.id.invoke_transaction_header_spinner_account_from);
		accountTo = (Spinner) header
				.findViewById(R.id.invoke_transaction_header_spinner_account_to);
		category = (Spinner) header
				.findViewById(R.id.invoke_transaction_header_spinner_category);

		spn_project = (Spinner) footer
				.findViewById(R.id.invoke_transaction_footer_spinner_project);

		note = (EditText) header
				.findViewById(R.id.invoke_transaction_header_etbx_note);
		value = (EditText) header
				.findViewById(R.id.invoke_transaction_header_etbx_value);

		btn_time = (Button) header
				.findViewById(R.id.invoke_transaction_header_btn_time);
		btn_time.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID); // TODO Auto-generated method stub

			}
		});

		btn_date = (Button) header
				.findViewById(R.id.invoke_transaction_header_btn_date);
		btn_date.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID); // TODO Auto-generated method stub

			}
		});

		Button btn_calculate = (Button) header
				.findViewById(R.id.invoke_transaction_header_btn_calculator);
		btn_calculate.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				showCalculatorInput();
			}
		});

		list = (ListView) findViewById(R.id.invoke_transactions_listview);
		list.addHeaderView(header);
		list.addFooterView(footer);

	}

	private void showCalculatorInput() {
		Intent intent = new Intent(InvokeTransactionActivity.this,
				CalculatorInput.class);
		intent.putExtra(CalculatorInput.EXTRA_AMOUNT, value.getText()
				.toString());
		startActivityForResult(intent, CalculatorInput_REQUESTCODE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CalculatorInput_REQUESTCODE) {
			if (resultCode == RESULT_OK) {
				String val = data.getStringExtra(CalculatorInput.EXTRA_AMOUNT);
				val = val.replace("-", "");
				value.setText(val);
			}
		}
	}

	private void getAccounts(Spinner spinner) {
		AndroidGlobals globals = AndroidGlobals.getInstance();
		Collection<Account> accounts = globals.getAccountsDictionary().values();

		if (accounts != null) {
			ArrayList<SpinnerObject> items = new ArrayList<SpinnerObject>();
			for (Account item : accounts) {
				if (item.isActive())
					items.add(new SpinnerObject(item.getId(), item.getName()
							+ " "
							+ UnitConverter.doubleToCurrency(item.getBalance())));
			}

			SpinnerObject[] strArray = new SpinnerObject[items.size()];
			items.toArray(strArray);

			SpinnerAdapter adapter = new SpinnerAdapter(this,
					android.R.layout.simple_spinner_item, strArray);
			spinner.setAdapter(adapter);
		}

	}

	private void getProjects() {
		AndroidGlobals globals = AndroidGlobals.getInstance();
		Collection<Project> projects = globals.getProjectsDictionary().values();

		if (projects != null) {

			ArrayList<SpinnerObject> items = new ArrayList<SpinnerObject>();
			items.add(new SpinnerObject(-1, getText(R.string.no_selected_value)
					.toString()));

			for (Project item : projects) {
				if (item.isActive())
					items.add(new SpinnerObject(item.getId(), item.getName()));
			}

			SpinnerObject[] strArray = new SpinnerObject[items.size()];
			items.toArray(strArray);

			SpinnerAdapter adapter = new SpinnerAdapter(this,
					android.R.layout.simple_spinner_item, strArray);

			spn_project.setAdapter(adapter);
		}

	}

	private void getCategories() {

		AndroidGlobals globals = AndroidGlobals.getInstance();
		ArrayList<Category> categories = globals.getCategoryList();
		if (categories != null) {

			ArrayList<SpinnerObject> items = new ArrayList<SpinnerObject>();

			for (Category item : categories) {
				if (item.isActive() && item.getParentId() > 0)
					items.add(new SpinnerObject(item.getId(), "- "
							+ item.getName()));
				else if (item.isActive())
					items.add(new SpinnerObject(item.getId(), item.getName()));
			}

			SpinnerObject[] strArray = new SpinnerObject[items.size()];
			items.toArray(strArray);

			SpinnerAdapter adapter = new SpinnerAdapter(this,
					android.R.layout.simple_spinner_item, strArray);

			category.setAdapter(adapter);
		}
		// } else if (categoriesIsDownloading == false) {
		// categoriesIsDownloading = true;
		// CategoriesManager manager = new CategoriesManager(this);
		// manager.getAllCategories();
		// }
	}

	@Override
	public void configureViews() {
		configureDateAndTime();

		if (isTransfer) {
			ll_accountTo.setVisibility(LinearLayout.VISIBLE);
			ll_category.setVisibility(LinearLayout.GONE);
			isPositive.setVisibility(ToggleButton.GONE);
			additionParametersTextView.setVisibility(TextView.GONE);
		} else {
			ll_accountTo.setVisibility(LinearLayout.GONE);
			ll_category.setVisibility(LinearLayout.VISIBLE);
			isPositive.setVisibility(ToggleButton.VISIBLE);
		}
		refreshActivity();

		if (!isTransfer) {
			category.setOnItemSelectedListener(new OnItemSelectedListener() {

				public void onItemSelected(AdapterView<?> adapterView,
						View view, int index, long id) {
					SpinerHelper oh = (SpinerHelper) view.getTag();
					if (oh.object.getId() > 0)
						onCategoryChange(oh.object);

				}

				public void onNothingSelected(AdapterView<?> adapterView) {
				}
			});
		}

		list.setAdapter(adapter);
	}

	private void configureDateAndTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);

		hour = cal.get(Calendar.HOUR_OF_DAY);
		minute = cal.get(Calendar.MINUTE);

		btn_date.setText(UnitConverter.convertDateToString(new Date()));
		btn_time.setText(UnitConverter.convertTimeToString(new Date()));

	}

	protected void onCategoryChange(SpinnerObject object) {
		if (!isTransfer) {
			AndroidGlobals globals = AndroidGlobals.getInstance();
			ArrayList<Category> cat = globals.getCategoryList();
			ArrayList<InvokeTransactionParameter> parameters = new ArrayList<InvokeTransactionParameter>();
			if (cat != null) {

				addParameterForCategoryId(object.getId(), cat, parameters);

			}
			if (parameters.size() > 0) {
				additionParametersTextView.setVisibility(TextView.VISIBLE);
				hasAdditionalParameters = true;
			} else {
				additionParametersTextView.setVisibility(TextView.GONE);
				hasAdditionalParameters = false;
			}

			adapter.setParamaters(parameters);
		}
	}

	private void addParameterForCategoryId(int categoryId,
			ArrayList<Category> cat,
			ArrayList<InvokeTransactionParameter> parameters) {
		AndroidGlobals globals = AndroidGlobals.getInstance();
		for (Category item : cat) {
			if (item.getId() == categoryId) {
				// Dodanie parametr—w kategorii nadrz«dnej jećli istniej�
				if (item.getParentId() > 0)
					addParameterForCategoryId(item.getParentId(), cat,
							parameters);
				for (Parameter parameter : item.getAttributes()) {
					parameter = globals.getParameterById(parameter.getId());

					InvokeTransactionParameter param = new InvokeTransactionParameter(
							parameter.getId(), parameter.getName(),
							parameter.getTypeId(), parameter.getDefaultValue(),
							parameter.getDataSource());
					parameters.add(param);

				}
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			refreshActivity();
			break;

		}
		return true;
	}

	private void saveTransaction() {
		Transaction transaction = new Transaction();

		String valueTxt = value.getText().toString();
		String noteTxt = note.getText().toString();
		if (valueTxt.length() == 0) {
			AlertDialog warning = DialogFactory.create(DialogType.ErrorDialog,
					null, this, null);
			warning.setMessage(getText(R.string.dialog_no_value));
			warning.show();

		} else {

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DAY_OF_MONTH, day);
			cal.set(Calendar.HOUR_OF_DAY, hour);
			cal.set(Calendar.MINUTE, minute);

			transaction.setValue(Double.parseDouble(valueTxt));
			transaction.setNote(noteTxt);
			transaction.setDate(cal.getTime());

			if (!isTransfer) {
				int accountFromId = ((SpinnerObject) accountFrom
						.getSelectedItem()).getId();

				int projectId = ((SpinnerObject) spn_project.getSelectedItem())
						.getId();

				if (projectId > 0) {
					transaction.setProjectId(projectId);
				}

				transaction.setAccMinus(accountFromId);
				int accountToId = -1;
				if (isTransfer) {
					accountToId = ((SpinnerObject) accountTo.getSelectedItem())
							.getId();
					transaction.setAccPlus(accountToId);
				}

				if (isPositive.isChecked()) {
					accountToId = accountFromId;
					transaction.setAccPlus(accountToId);
					transaction.setAccMinus(0);
				}

				int categoryId = ((SpinnerObject) category.getSelectedItem())
						.getId();

				Category c = new Category();
				c.setId(categoryId);
				if (hasAdditionalParameters) {
					ArrayList<Parameter> items = new ArrayList<Parameter>();
					ArrayList<InvokeTransactionParameter> parameters = adapter
							.getAllItems();
					for (InvokeTransactionParameter item : parameters) {
						items.add(new Parameter(item.getId(), item.getValue()));
					}
					Parameter[] elements = new Parameter[items.size()];
					c.setAttributes(items.toArray(elements));
				}

				transaction.setCategory(c);
			} else if (isTransfer) {
				int accountFromId = ((SpinnerObject) accountFrom
						.getSelectedItem()).getId();
				int accountToId = ((SpinnerObject) accountTo.getSelectedItem())
						.getId();
				transaction.setAccMinus(accountFromId);
				transaction.setAccPlus(accountToId);

			}
			sendToWS(transaction);
		}
	}

	private void sendToWS(Transaction transaction) {
		super.setProgressDialogCancelable(false);
		super.setIsSendProgressBar(true);
		TransactionManager manager = new TransactionManager(this);
		manager.createNewTransaction(transaction);
	}

	public void onObjectsReceived(Object object, Object object2) {
		super.onObjectsReceived(object, object2);
		if (object instanceof Transaction) {
			leaveActivity(ResultCodes.RESULT_NEED_REFRESH);
		} else if (object instanceof Account[]) {
			refreshList();
		} else if (object instanceof Category[]) {
			refreshList();
		} else if (object instanceof Project[]) {
			refreshList();
		}
		// else if (object instanceof Account[]) {
		// ArrayList<Account> elements = new ArrayList<Account>();
		// Account[] items = (Account[]) object;
		// for (Account account : items) {
		// elements.add(account);
		// }
		// AndroidGlobals globals = AndroidGlobals.getInstance();
		// globals.setAccountList(elements);
		// refreshActivity();
		// } else if (object instanceof Category[]) {
		// ArrayList<Category> elements = new ArrayList<Category>();
		// Category[] items = (Category[]) object;
		// for (Category item : items) {
		// elements.add(item);
		// }
		// AndroidGlobals globals = AndroidGlobals.getInstance();
		// globals.setCategoryList(elements);
		// refreshActivity();
		// }

	}

	private void refreshActivity() {
		if (ddManager == null) {
			ddManager = new DownloadDataManager(this,
					RefreshOptions.refreshCategories
							| RefreshOptions.refreshParameters
							| RefreshOptions.refreshProjects, this);
			refreshList();
		} else {
			ddManager.refresh(true, RefreshOptions.refreshCategories
					| RefreshOptions.refreshParameters
					| RefreshOptions.refreshProjects);
		}

	}

	private void refreshList() {
		getAccounts(accountFrom);
		if (isTransfer)
			getAccounts(accountTo);
		if (!isTransfer) {
			getCategories();
			getProjects();
		}
	}

	public void onClick(View view) {
		String tag = (String) view.getTag();
		if (tag.equals("save"))
			saveTransaction();
		else if (tag.equals("cancel"))
			leaveActivity(ResultCodes.RESULT_NO_ACTION);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, hour, minute,
					true);

		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, Year, month,
					day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			Year = year;
			month = monthOfYear;
			day = dayOfMonth;
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DAY_OF_MONTH, day);

			btn_date.setText(UnitConverter.convertDateToString(cal.getTime()));
		}
	};

	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
			hour = hourOfDay;
			minute = minuteOfHour;
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, hour);
			cal.set(Calendar.MINUTE, minute);

			btn_time.setText(UnitConverter.convertTimeToString(cal.getTime()));
		}
	};
}
