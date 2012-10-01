package pl.wppiotrek.wydatki.activities;

import java.util.ArrayList;
import java.util.Date;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.adapters.ShopListAdapter;
import pl.wppiotrek.wydatki.entities.ShopItem;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ShopingListActivity extends ProgressActivity implements
		OnItemClickListener {

	private ListView list;
	private ShopListAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopinglist);
		super.setProgressDialogCancelable(true);

		linkViews();
		configureViews();
		refreshActivity(false);
	}

	@Override
	public void linkViews() {
		list = (ListView) findViewById(R.id.shopinglist_listview);
	}

	@Override
	public void configureViews() {
		ArrayList<ShopItem> items = new ArrayList<ShopItem>();

		ShopItem i1 = new ShopItem();
		i1.setName("Zielona papryka");
		i1.setComplet(false);
		i1.setUserId(1);
		i1.setVisibleForAll(true);
		i1.setAddDate(new Date());
		i1.setValue(1.0);
		i1.setUnit("szt.");
		items.add(i1);

		i1 = new ShopItem();
		i1.setName("Pomidory");
		i1.setComplet(true);
		i1.setUserId(1);
		i1.setVisibleForAll(true);
		i1.setAddDate(new Date());
		i1.setValue(1.0);
		i1.setUnit("kg");
		items.add(i1);

		i1 = new ShopItem();
		i1.setName("Doniczki dla kwiatków");
		i1.setComplet(false);
		i1.setUserId(1);
		i1.setVisibleForAll(true);
		i1.setAddDate(new Date());
		i1.setValue(3.0);
		i1.setUnit("szt.");
		items.add(i1);

		if (items != null) {
			adapter = new ShopListAdapter(this, items);
			list.setAdapter(adapter);
			list.setOnItemClickListener(this);
		}
	}

	@Override
	public void refreshActivity(boolean isForceRefresh) {

	}

	@Override
	public void leaveActivity(int requestCode) {
		// TODO Auto-generated method stub

	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

}
