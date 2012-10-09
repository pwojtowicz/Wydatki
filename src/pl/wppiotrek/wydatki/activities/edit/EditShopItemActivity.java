package pl.wppiotrek.wydatki.activities.edit;

import java.util.Date;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.entities.ShopItem;
import pl.wppiotrek.wydatki.managers.ShopItemManager;
import pl.wppiotrek.wydatki.units.ResultCodes;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

public class EditShopItemActivity extends EditAbstractActivity<ShopItem> {

	private static final int REQUEST_CODE = 1234;
	private ShopItem currentObject;
	private EditText itemName;
	private CheckBox edit_cbx_visibleForAll;
	private CheckBox edit_cbx_isComplete;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.activity_new_shopitem);

	}

	@Override
	protected void prepareToSave() {
		boolean isValid = false;
		String v = etbx_name.getText().toString();
		if (isUpdate || (etbx_name.getText().length() > 0)) {
			isValid = true;
		}

		if (isValid) {
			ShopItem item = new ShopItem();
			if (isUpdate) {
				int itemId = currentObject.getId();
				item.setId(itemId);
			}
			item.setName(etbx_name.getText().toString().trim());
			item.setVisibleForAll(edit_cbx_visibleForAll.isChecked());
			item.setComplet(edit_cbx_isComplete.isChecked());

			item.setUserId(1);
			item.setUnit(1);
			item.setValue(1.2);
			item.setAddDate(new Date());

			this.currentObject = item;
			saveObject();
		}
	}

	@Override
	protected void saveObject() {
		super.setProgressDialogCancelable(false);
		super.setIsSendProgressBar(true);
		super.setShowProgressBar(true);
		ShopItemManager manager = new ShopItemManager(this);
		if (isUpdate)
			manager.editItem(this, currentObject);
		else
			manager.createNewItem(this, currentObject);

	}

	@Override
	public void refreshActivity(boolean isForceRefresh) {
		// TODO Auto-generated method stub

	}

	@Override
	public void leaveActivity(int requestCode) {
		Intent intent = this.getIntent();
		this.setResult(requestCode, intent);
		finish();

	}

	@Override
	public void linkViews() {
		edit_cbx_visibleForAll = (CheckBox) findViewById(R.id.edit_cbx_visibleForAll);
		edit_cbx_isComplete = (CheckBox) findViewById(R.id.edit_cbx_isComplete);

	}

	@Override
	public void configureViews() {
		if (!isUpdate)
			currentObject = new ShopItem();
		// else
		// currentObject = AndroidGlobals.getInstance()
		// .getCurrentSelectedProject();

		if (isUpdate) {
			etbx_name.setText(currentObject.getName());
			edit_cbx_isComplete.setChecked(currentObject.isComplet());
			edit_cbx_visibleForAll.setChecked(currentObject.isVisibleForAll());
		}
	}

	public void onObjectsReceived(Object object, Object object2) {
		super.onObjectsReceived(object, object2);
		if (object instanceof ShopItem) {
			// AndroidGlobals globals = AndroidGlobals.getInstance();
			// if (isUpdate) {
			// globals.updateProjectsList((Project) object);
			// } else {
			// Project p = (Project) object;
			// globals.updateProjectsList(p);
			// }
			leaveActivity(ResultCodes.RESULT_NEED_UPDATE);
		}

	}

	@Override
	public void onRecognitionRequest(String value) {
		itemName.setText(value);

	}

}
