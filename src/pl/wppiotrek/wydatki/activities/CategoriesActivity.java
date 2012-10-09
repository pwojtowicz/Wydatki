package pl.wppiotrek.wydatki.activities;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.activities.edit.EditCategoryActivity;
import pl.wppiotrek.wydatki.adapters.CategoriesAdapter;
import pl.wppiotrek.wydatki.adapters.CategoriesAdapter.CategoryAdapterObjectHandler;
import pl.wppiotrek.wydatki.entities.Category;
import pl.wppiotrek.wydatki.managers.CategoriesManager;
import pl.wppiotrek.wydatki.managers.DownloadDataManager;
import pl.wppiotrek.wydatki.support.AndroidGlobals;
import pl.wppiotrek.wydatki.support.DialogFactory;
import pl.wppiotrek.wydatki.units.DialogType;
import pl.wppiotrek.wydatki.units.RefreshOptions;
import pl.wppiotrek.wydatki.units.ResultCodes;
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
import android.widget.Button;
import android.widget.ListView;

public class CategoriesActivity extends ProgressActivity implements
		OnItemClickListener {

	private ListView list;
	private CategoriesAdapter adapter;
	private DownloadDataManager ddManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);
		super.setProgressDialogCancelable(false);

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
		ArrayList<Category> categories = globals.getCategoriesList();
		if (categories != null) {
			adapter = new CategoriesAdapter(this, categories);
			list.setAdapter(adapter);
		}
	}

	@Override
	public void refreshActivity(boolean isForceRefresh) {
		if (ddManager == null) {
			ddManager = new DownloadDataManager(this,
					RefreshOptions.refreshCategories, this);
			refreshList();
		} else {

			if (isForceRefresh)
				ddManager.refresh(isForceRefresh,
						RefreshOptions.refreshCategories);
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
		list = (ListView) findViewById(R.id.categories_listview);
		list.setOnItemClickListener(this);

		Button btn_addNew = (Button) findViewById(R.id.btn_add_new);
		btn_addNew.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				showEditActivity(false);
			}
		});

	}

	protected void showEditActivity(boolean isUpdate) {
		Intent intent = new Intent(this, EditCategoryActivity.class);
		if (isUpdate) {
			Bundle b = new Bundle();
			b.putBoolean("isUpdate", true);
			intent.putExtras(b);
		}
		startActivityForResult(intent, ResultCodes.START_ACTIVITY_EDIT_CATEGORY);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ResultCodes.START_ACTIVITY_EDIT_CATEGORY) {
			if (resultCode == ResultCodes.RESULT_NEED_REFRESH) {
				refreshActivity(true);
			} else if (resultCode == ResultCodes.RESULT_NEED_UPDATE) {
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
		if (object instanceof String && ((String) object).equals("OK")) {

		} else if (object instanceof Category) {
			AndroidGlobals.getInstance()
					.updateCategoriesList((Category) object);
			refreshList();
		}
		if (object instanceof Category[]) {
			refreshList();

		}
	}

	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		super.vibrate();
		CategoryAdapterObjectHandler oh = (CategoryAdapterObjectHandler) view
				.getTag();

		Bundle b = new Bundle();

		final CharSequence[] items = {
				getText(R.string.edit),
				oh.category.isActive() ? getText(R.string.lock) + " "
						+ getText(R.string.entity_category)
						: getText(R.string.unlock) + " "
								+ getText(R.string.entity_category) };

		b.putCharSequenceArray("options", items);
		AndroidGlobals.getInstance().setCurrentSelectedCategory(oh.category);

		AlertDialog dialog = DialogFactory.create(DialogType.OptionsDialog, b,
				this, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						onDialogItemSelected(item);
					}
				});
		if (dialog != null)
			dialog.show();

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
		CategoriesManager manager = new CategoriesManager(this);
		Category category = AndroidGlobals.getInstance()
				.getCurrentSelectedCategory();
		category.setIsActive(!category.isActive());
		manager.editCategory(category);
	}

	@Override
	public void onRecognitionRequest(String value) {
		// TODO Auto-generated method stub

	}

}
