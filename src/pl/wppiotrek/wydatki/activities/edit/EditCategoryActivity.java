package pl.wppiotrek.wydatki.activities.edit;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.adapters.CategoryParametersAdapter;
import pl.wppiotrek.wydatki.adapters.SpinnerAdapter;
import pl.wppiotrek.wydatki.adapters.SpinnerAdapter.SpinerHelper;
import pl.wppiotrek.wydatki.entities.Category;
import pl.wppiotrek.wydatki.entities.Parameter;
import pl.wppiotrek.wydatki.entities.SpinnerObject;
import pl.wppiotrek.wydatki.managers.CategoriesManager;
import pl.wppiotrek.wydatki.managers.ParameterManager;
import pl.wppiotrek.wydatki.support.AndroidGlobals;
import pl.wppiotrek.wydatki.support.ListSupport;
import pl.wppiotrek.wydatki.units.ResultCodes;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class EditCategoryActivity extends EditAbstractActivity<Category> {

	private Spinner spn_parent;
	private ParameterManager manager;
	private ListView listView;
	private CategoryParametersAdapter adapter;
	private ToggleButton tbn_isPositive;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.activity_new_category);

	}

	@Override
	public void linkViews() {
		LayoutInflater layoutInflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View header = layoutInflater.inflate(R.layout.edit_category_header,
				null);

		listView = (ListView) findViewById(R.id.edit_category_lv_parameters);

		etbx_name = (EditText) header.findViewById(R.id.edit_etbx_name);
		cbx_isActive = (CheckBox) header.findViewById(R.id.edit_cbx_isActive);
		tbn_isPositive = (ToggleButton) header
				.findViewById(R.id.edit_category_tbn_ispositive);
		spn_parent = (Spinner) header
				.findViewById(R.id.edit_category_spn_parent);

		listView.addHeaderView(header);

	}

	@Override
	public void configureViews() {
		adapter = new CategoryParametersAdapter(this);

		if (!isUpdate) {
			currentObject = new Category();
		} else {
			currentObject = AndroidGlobals.getInstance()
					.getCurrentSelectedCategory();
		}

		listView.setAdapter(adapter);

		refreshActivity(false);
		if (isUpdate) {
			etbx_name.setText(currentObject.getName());
			cbx_isActive.setChecked(currentObject.isActive());
			tbn_isPositive.setChecked(currentObject.isPositive());

		}
	}

	private void getCategories() {
		AndroidGlobals globals = AndroidGlobals.getInstance();
		ArrayList<SpinnerObject> items = new ArrayList<SpinnerObject>();
		items.add(new SpinnerObject(0, getText(R.string.no_selected_value)
				.toString()));

		ArrayList<Category> categories = globals.getCategoryList();
		if (categories != null) {
			for (Category item : categories) {
				if (item.isActive() && item.getParentId() > 0)
					items.add(new SpinnerObject(item.getId(), "- "
							+ item.getName()));
				else if (item.isActive() && item.getParentId() <= 0)
					items.add(new SpinnerObject(item.getId(), item.getName()));
			}

			SpinnerObject[] strArray = new SpinnerObject[items.size()];
			items.toArray(strArray);

			SpinnerAdapter adapter = new SpinnerAdapter(this,
					android.R.layout.simple_spinner_item, strArray);
			spn_parent.setAdapter(adapter);
			if (isUpdate && currentObject.getParentId() != 0)
				spn_parent.setSelection(getSelectedIndexById(
						currentObject.getParentId(), items));

			spn_parent.setOnItemSelectedListener(new OnItemSelectedListener() {

				public void onItemSelected(AdapterView<?> adapterView,
						View view, int index, long id) {
					SpinerHelper oh = (SpinerHelper) view.getTag();
					if (oh.object.getId() >= 0)
						onCategoryChange(oh.object);

				}

				public void onNothingSelected(AdapterView<?> adapterView) {
				}
			});

		}
	}

	protected void onCategoryChange(SpinnerObject object) {
		currentObject.setParentId(object.getId());
		adapter.clear();

		if (currentObject.getAttributes() != null
				&& currentObject.getAttributes().length > 0) {
			adapter.addParameter("Parametry kategorii");
			for (Parameter item : currentObject.getAttributes()) {
				adapter.addParameter(item);
			}
		}

		if (object.getId() > 0) {
			adapter.addParameter("Parametry nadrz«dne");
			addParameterForCategoryId(object.getId(), AndroidGlobals
					.getInstance().getCategoryList());
		}
		adapter.refresh();

	}

	private void addParameterForCategoryId(int categoryId,
			ArrayList<Category> cat) {
		for (Category item : cat) {
			if (item.getId() == categoryId) {
				// Dodanie parametr—w kategorii nadrz«dnej jećli istniej�
				if (item.getParentId() > 0)
					addParameterForCategoryId(item.getParentId(), cat);
				for (Parameter parameter : item.getAttributes()) {
					adapter.addParameter(parameter);
				}
			}
		}
	}

	private int getSelectedIndexById(int id, ArrayList<SpinnerObject> items) {
		for (int i = 0; i < items.size(); i++) {
			if (((SpinnerObject) items.get(i)).getId() == id)
				return i;
		}
		return 0;
	}

	@Override
	public void refreshActivity(boolean isForceRefresh) {
		if (isForceRefresh) {
			// AndroidGlobals.getInstance().setParameterList(null);
		}
		getCategories();
		getParameters();

	}

	private void getParameters() {
		AndroidGlobals globals = AndroidGlobals.getInstance();
		ArrayList<SpinnerObject> items = new ArrayList<SpinnerObject>();

		// ArrayList<Parameter> parameters = globals.getParameterList();
		// if (parameters != null) {
		//
		// } else {
		// manager = new ParameterManager(this);
		// manager.getAllParameters();
		// }

	}

	@Override
	public void leaveActivity(int requestCode) {
		Intent intent = this.getIntent();
		this.setResult(requestCode, intent);
		finish();

	}

	public void onObjectsReceived(Object object, Object object2) {
		super.onObjectsReceived(object, object2);
		if (object instanceof Parameter[]) {
			// ArrayList<Parameter> elements = new ArrayList<Parameter>();
			// Parameter[] items = (Parameter[]) object;
			// for (Parameter item : items) {
			// elements.add(item);
			// }
			// AndroidGlobals globals = AndroidGlobals.getInstance();
			// globals.setParameterList(elements);
			// refreshActivity(false);
		} else if (object instanceof Category) {
			AndroidGlobals globals = AndroidGlobals.getInstance();
			if (isUpdate) {
				globals.updateCategoriesList((Category) object);
				leaveActivity(ResultCodes.RESULT_NEED_UPDATE);
			} else {
				globals.getCategoryList().add((Category) object);
				leaveActivity(ResultCodes.RESULT_NEED_REFRESH);
			}

		}

	}

	@Override
	protected void prepareToSave() {
		boolean isValid = false;
		if (isUpdate
				|| (etbx_name.getText().length() > 0 && !ListSupport
						.isProjectNameUsed(etbx_name.getText().toString()))) {
			isValid = true;
		}

		if (isValid) {
			Category category = new Category();

			if (isUpdate) {
				int categoryId = currentObject.getId();
				category.setId(categoryId);
			}
			category.setParentId(currentObject.getParentId());
			category.setName(etbx_name.getText().toString());
			category.setIsActive(cbx_isActive.isChecked());
			category.setIsPositive(tbn_isPositive.isChecked());

			category.setAttributes(currentObject.getAttributes());
			this.currentObject = category;
			saveObject();

		}

	}

	@Override
	protected void saveObject() {
		super.setProgressDialogCancelable(false);
		super.setIsSendProgressBar(true);
		super.setShowProgressBar(true);
		CategoriesManager manager = new CategoriesManager(this);
		if (isUpdate)
			manager.editCategory(currentObject);
		else
			manager.createNewCategory(currentObject);

	}

}
