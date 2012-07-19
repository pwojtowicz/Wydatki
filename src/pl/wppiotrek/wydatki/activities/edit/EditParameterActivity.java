package pl.wppiotrek.wydatki.activities.edit;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.adapters.SpinnerAdapter;
import pl.wppiotrek.wydatki.adapters.SpinnerAdapter.SpinerHelper;
import pl.wppiotrek.wydatki.entities.Parameter;
import pl.wppiotrek.wydatki.entities.SpinnerObject;
import pl.wppiotrek.wydatki.managers.ParameterManager;
import pl.wppiotrek.wydatki.support.AndroidGlobals;
import pl.wppiotrek.wydatki.support.DialogFactory;
import pl.wppiotrek.wydatki.units.DialogType;
import pl.wppiotrek.wydatki.units.ParameterTypes;
import pl.wppiotrek.wydatki.units.ResultCodes;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class EditParameterActivity extends EditAbstractActivity<Parameter> {

	private EditText etbx_defaultValue;
	private EditText etbx_dataSource;
	private Spinner spn_type;
	private LinearLayout ll_dataSource;
	private LinearLayout ll_etbx_defaultFalue;
	private LinearLayout ll_cbx_defaultValue;

	private int parameterType;

	private CheckBox cbx_defaultValue;

	private ParameterManager manager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.activity_new_parameter);

	}

	@Override
	public void linkViews() {
		etbx_defaultValue = (EditText) findViewById(R.id.edit_parameter_etbx_defaultValue);
		etbx_dataSource = (EditText) findViewById(R.id.edit_parameter_etbx_dataSource);

		spn_type = (Spinner) findViewById(R.id.edit_parameter_spn_type);
		cbx_defaultValue = (CheckBox) findViewById(R.id.edit_parameter_cbx_defaultValue);

		ll_dataSource = (LinearLayout) findViewById(R.id.edit_parameter_ll_dataSource);
		ll_etbx_defaultFalue = (LinearLayout) findViewById(R.id.edit_parameter_ll_etbx_defaultValue);
		ll_cbx_defaultValue = (LinearLayout) findViewById(R.id.edit_parameter_ll_cbx_defaultValue);

	}

	@Override
	public void configureViews() {
		if (!isUpdate)
			currentObject = new Parameter();
		else
			currentObject = AndroidGlobals.getInstance()
					.getCurrentSelectedParameter();

		getTypes();
		spn_type.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> adapterView, View view,
					int index, long id) {
				SpinerHelper oh = (SpinerHelper) view.getTag();
				if (oh.object.getId() > 0)
					onCategoryChange(oh.object);

			}

			public void onNothingSelected(AdapterView<?> adapterView) {
			}
		});

		if (isUpdate) {
			cbx_isActive.setChecked(currentObject.isActive());

			etbx_name.setText(currentObject.getName());

			etbx_dataSource.setText(currentObject.getDataSource());

			if (currentObject.getTypeId() == ParameterTypes.ParameterBoolean) {
				ll_cbx_defaultValue.setVisibility(LinearLayout.VISIBLE);
				ll_etbx_defaultFalue.setVisibility(LinearLayout.GONE);
				manageCheckBox(currentObject.getDefaultValue());
			} else {
				etbx_defaultValue.setText(currentObject.getDefaultValue());
			}
			if (currentObject.getTypeId() == ParameterTypes.ParameterList) {
				ll_dataSource.setVisibility(LinearLayout.VISIBLE);

			}

		}

	}

	private void manageCheckBox(String trueOrFalse) {
		if (trueOrFalse.equals("True"))
			cbx_defaultValue.setChecked(true);
		else if (trueOrFalse.equals("False"))
			cbx_defaultValue.setChecked(false);

	}

	protected void onCategoryChange(SpinnerObject object) {
		ll_dataSource.setVisibility(LinearLayout.GONE);
		ll_cbx_defaultValue.setVisibility(LinearLayout.GONE);
		ll_etbx_defaultFalue.setVisibility(LinearLayout.VISIBLE);
		this.parameterType = object.getId();
		switch (parameterType) {
		case ParameterTypes.ParameterList:
			ll_dataSource.setVisibility(LinearLayout.VISIBLE);
			break;
		case ParameterTypes.ParameterBoolean:
			ll_cbx_defaultValue.setVisibility(LinearLayout.VISIBLE);
			ll_etbx_defaultFalue.setVisibility(LinearLayout.GONE);
			break;
		case ParameterTypes.ParameterNumber:
			break;
		case ParameterTypes.ParameterText:
			break;

		default:
			break;
		}

	}

	private void getTypes() {
		ArrayList<SpinnerObject> items = new ArrayList<SpinnerObject>();

		for (int i = 1; i < 5; i++) {
			items.add(new SpinnerObject(i, ParameterTypes.getParameterName(i)));
		}

		SpinnerObject[] strArray = new SpinnerObject[items.size()];
		items.toArray(strArray);

		SpinnerAdapter adapter = new SpinnerAdapter(this,
				android.R.layout.simple_spinner_item, strArray);
		spn_type.setAdapter(adapter);
		if (isUpdate)
			spn_type.setSelection(currentObject.getTypeId() - 1);

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

	public void onObjectsReceived(Object object, Object object2) {
		super.onObjectsReceived(object, object2);
		if (object instanceof Parameter) {
			AndroidGlobals globals = AndroidGlobals.getInstance();
			if (isUpdate) {
				globals.updateParametersList((Parameter) object);
			} else {
				Parameter p = (Parameter) object;
				globals.getParametersDictionary().put(p.getId(), p);
			}
			leaveActivity(ResultCodes.RESULT_NEED_UPDATE);

		}
	}

	@Override
	protected void prepareToSave() {
		boolean isValid = true;
		StringBuilder sb = new StringBuilder();

		if (isUpdate) {
			int parameterId = currentObject.getId();
			currentObject = new Parameter();
			currentObject.setId(parameterId);
		}

		if (etbx_name.getText().length() > 0) {
			currentObject.setName(etbx_name.getText().toString());
		} else {
			isValid = false;
			sb.append("\n" + getText(R.string.name));
		}
		if (parameterType == ParameterTypes.ParameterList)
			if (etbx_dataSource.getText().length() > 0) {
				currentObject.setDataSource(etbx_dataSource.getText()
						.toString());
				currentObject.setDefaultValue(etbx_defaultValue.getText()
						.toString());
			} else {
				isValid = false;
				sb.append("\n" + getText(R.string.parameter_datasource));
			}

		if (parameterType == ParameterTypes.ParameterBoolean)
			currentObject.setDefaultValue(cbx_defaultValue.isChecked() ? "True"
					: "False");
		else
			currentObject.setDefaultValue(etbx_defaultValue.getText()
					.toString());

		currentObject.setTypeId(parameterType);
		currentObject.setIsActive(cbx_isActive.isChecked());

		if (isValid) {
			saveObject();

		} else {
			AlertDialog dialog = DialogFactory.create(DialogType.ErrorDialog,
					null, this, null);
			dialog.setMessage(sb.toString());
			dialog.show();
		}
	}

	@Override
	protected void saveObject() {
		super.setProgressDialogCancelable(false);
		super.setIsSendProgressBar(true);
		super.setShowProgressBar(true);
		manager = new ParameterManager(this);
		manager.createNewParameter(currentObject);

	}

}
