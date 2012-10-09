package pl.wppiotrek.wydatki.activities;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.activities.edit.EditCategoryActivity;
import pl.wppiotrek.wydatki.activities.edit.EditParameterActivity;
import pl.wppiotrek.wydatki.adapters.ParametersAdapter;
import pl.wppiotrek.wydatki.adapters.ParametersAdapter.ParameterAdapterObjectHandler;
import pl.wppiotrek.wydatki.entities.Parameter;
import pl.wppiotrek.wydatki.managers.DownloadDataManager;
import pl.wppiotrek.wydatki.managers.ParameterManager;
import pl.wppiotrek.wydatki.support.AndroidGlobals;
import pl.wppiotrek.wydatki.support.DialogFactory;
import pl.wppiotrek.wydatki.support.ListSupport;
import pl.wppiotrek.wydatki.units.DialogType;
import pl.wppiotrek.wydatki.units.RefreshOptions;
import pl.wppiotrek.wydatki.units.ResultCodes;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class AttributesActivity extends ProgressActivity implements
		OnItemClickListener {

	public static final String BUNDLE_ISCHECKABLE = "0";
	public static final String BUNDLE_SELECT_PARAMETER_FOR_CATEGORY = "1";
	public static final String BUNDLE_SELECTED_PARAMETERS_FOR_CATEGORY = "2";

	Boolean isChecakble = false;
	Boolean isSelectedForCategory = false;
	private ListView list;
	private ParametersAdapter adapter;
	private DownloadDataManager ddManager;
	private String selectedItems = "";

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isSelectedForCategory) {
				Intent intent = this.getIntent();
				this.setResult(RESULT_OK, intent);
				String items = ListSupport.ArrayListIntegerToString(adapter
						.getSelectedItems());
				intent.putExtra(
						EditCategoryActivity.BUNDLE_SELECTED_PARAMETERS,
						items.toString());
			}
			finish();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attributes);
		super.setProgressDialogCancelable(true);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			isChecakble = bundle.getBoolean(BUNDLE_ISCHECKABLE);
			isSelectedForCategory = bundle
					.getBoolean(BUNDLE_SELECT_PARAMETER_FOR_CATEGORY);
			selectedItems = bundle
					.getString(BUNDLE_SELECTED_PARAMETERS_FOR_CATEGORY);
		}

		linkViews();
		configureViews();
		refreshActivity(false);

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

	private void refreshList() {
		AndroidGlobals globals = AndroidGlobals.getInstance();
		ArrayList<Parameter> parameters = globals.getParametersList();

		ArrayList<Parameter> parametersToShow = new ArrayList<Parameter>();
		if (isSelectedForCategory) {
			for (Parameter parameter : parameters) {
				if (parameter.isActive())
					parametersToShow.add(parameter);
			}
		} else
			parametersToShow = parameters;

		if (parameters != null) {
			adapter = new ParametersAdapter(this, parametersToShow,
					isChecakble, selectedItems);
			list.setAdapter(adapter);
			list.setOnItemClickListener(this);
		}
	}

	@Override
	public void refreshActivity(boolean isForceRefresh) {
		if (ddManager == null) {
			ddManager = new DownloadDataManager(this,
					RefreshOptions.refreshParameters, this);
			refreshList();
		} else {

			if (isForceRefresh)
				ddManager.refresh(isForceRefresh,
						RefreshOptions.refreshParameters);
			else {
				refreshList();
			}
		}
	}

	@Override
	public void leaveActivity(int requestCode) {
		if (ddManager == null)
			ddManager.cancleDownload();
		finish();
	}

	@Override
	public void linkViews() {
		list = (ListView) findViewById(R.id.parameters_listview);
		Button btn_addNew = (Button) findViewById(R.id.btn_add_new);
		btn_addNew.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				showEditActivity(false);
			}
		});

	}

	protected void showEditActivity(boolean isUpdate) {
		Intent intent = new Intent(this, EditParameterActivity.class);
		if (isUpdate) {
			Bundle b = new Bundle();
			b.putBoolean("isUpdate", true);
			intent.putExtras(b);
		}
		startActivityForResult(intent,
				ResultCodes.START_ACTIVITY_EDIT_PARAMETER);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ResultCodes.START_ACTIVITY_EDIT_PARAMETER) {
			if (resultCode == ResultCodes.RESULT_NEED_UPDATE) {
				refreshActivity(false);
			}
		}
	}

	@Override
	public void configureViews() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onObjectsReceived(Object object, Object object2) {
		if (object instanceof Parameter) {
			// AndroidGlobals.getInstance().updateParametersList(
			// (Parameter) object);
			refreshList();
		} else if (object instanceof Parameter[]) {
			refreshList();
		}
	}

	protected void onDialogItemSelected(int item) {
		if (item == 0) {
			showEditActivity(true);
		}
		if (item == 1) {
			changeLock();
		}
		if (item == 2) {
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
		ParameterManager manager = new ParameterManager(this);
		Parameter parameter = AndroidGlobals.getInstance()
				.getCurrentSelectedParameter();
		parameter.setIsActive(!parameter.isActive());
		manager.editParameter(parameter);
	}

	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		super.vibrate();
		ParameterAdapterObjectHandler oh = (ParameterAdapterObjectHandler) view
				.getTag();

		Bundle b = new Bundle();

		final CharSequence[] items = {
				getText(R.string.edit),
				oh.parameter.isActive() ? getText(R.string.lock) + " "
						+ getText(R.string.entity_parameter)
						: getText(R.string.unlock) + " "
								+ getText(R.string.entity_parameter) };

		b.putCharSequenceArray("options", items);
		AndroidGlobals.getInstance().setCurrentSelectedParameter(oh.parameter);

		AlertDialog dialog = DialogFactory.create(DialogType.OptionsDialog, b,
				this, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						onDialogItemSelected(item);
					}
				});
		if (dialog != null)
			dialog.show();
	}

	@Override
	public void onRecognitionRequest(String value) {
		// TODO Auto-generated method stub

	}
}
