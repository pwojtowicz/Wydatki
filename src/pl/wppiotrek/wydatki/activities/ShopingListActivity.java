package pl.wppiotrek.wydatki.activities;

import java.util.ArrayList;
import java.util.Date;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.activities.edit.EditShopItemActivity;
import pl.wppiotrek.wydatki.adapters.ShopListAdapter;
import pl.wppiotrek.wydatki.entities.ShopItem;
import pl.wppiotrek.wydatki.managers.ShopItemManager;
import pl.wppiotrek.wydatki.units.ResultCodes;
import pl.wppiotrek.wydatki.units.Units;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ShopingListActivity extends ProgressActivity implements
		OnItemClickListener {

	private static final int REQUEST_CODE = 1234;
	private ListView list;
	private ShopListAdapter adapter;
	private ArrayList<ShopItem> items;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopinglist);
		super.setProgressDialogCancelable(true);

		linkViews();
		configureViews();
		refreshActivity(true);
	}

	@Override
	public void linkViews() {
		list = (ListView) findViewById(R.id.shopinglist_listview);

		Button btn_addNew = (Button) findViewById(R.id.btn_add_new);
		btn_addNew.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				showEditActivity(false);
			}
		});

	}

	protected void showEditActivity(boolean isUpdate) {
		Intent intent = new Intent(this, EditShopItemActivity.class);
		if (isUpdate) {
			Bundle b = new Bundle();
			b.putBoolean("isUpdate", true);
			intent.putExtras(b);
		}
		startActivityForResult(intent, ResultCodes.START_ACTIVITY_EDIT_SHOPITEM);

	}

	@Override
	public void configureViews() {

		ShopItemManager manager = new ShopItemManager(this);
		manager.getAllItems(this);

		list.setOnItemClickListener(this);

	}

	@Override
	public void refreshActivity(boolean isForceRefresh) {
		if (isForceRefresh)
			items = new ArrayList<ShopItem>();

		ShopItem i1 = new ShopItem();
		i1.setName("Zielona papryka");
		i1.setComplet(false);
		i1.setUserId(1);
		i1.setVisibleForAll(true);
		i1.setAddDate(new Date());
		i1.setValue(1.0);
		i1.setUnit(Units.UnitSzt);
		items.add(i1);

		i1 = new ShopItem();
		i1.setName("Pomidory");
		i1.setComplet(true);
		i1.setUserId(1);
		i1.setVisibleForAll(true);
		i1.setAddDate(new Date());
		i1.setValue(1.0);
		i1.setUnit(Units.UnitKg);
		items.add(i1);

		i1 = new ShopItem();
		i1.setName("Doniczki dla kwiatków");
		i1.setComplet(false);
		i1.setUserId(1);
		i1.setVisibleForAll(true);
		i1.setAddDate(new Date());
		i1.setValue(3.0);
		i1.setUnit(Units.UnitSzt);
		items.add(i1);

		adapter = new ShopListAdapter(this, items);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
	}

	@Override
	public void leaveActivity(int requestCode) {
		// TODO Auto-generated method stub

	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRecognitionRequest(String value) {
		// TODO Auto-generated method stub

	}

	public void onObjectsReceived(Object object, Object object2) {
		super.onObjectsReceived(object, object2);
		if (object instanceof Object[]) {
			items = new ArrayList<ShopItem>();

			for (Object item : (Object[]) object) {
				items.add((ShopItem) item);
			}
		}
		refreshActivity(false);
	}

}
