package pl.wppiotrek.wydatki.activities;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.adapters.RecurAdapter;
import pl.wppiotrek.wydatki.entities.Recur;
import pl.wppiotrek.wydatki.support.AndroidGlobals;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RecurActivity extends ProgressActivity implements
		OnItemClickListener {

	private ListView list;
	private RecurAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recurs);
		super.setProgressDialogCancelable(true);

		// Bundle bundle = getIntent().getExtras();
		// if (bundle != null) {
		// isChecakble = bundle.getBoolean(BUNDLE_ISCHECKABLE);
		// isSelectedForCategory = bundle
		// .getBoolean(BUNDLE_SELECT_PARAMETER_FOR_CATEGORY);
		// selectedItems = bundle
		// .getString(BUNDLE_SELECTED_PARAMETERS_FOR_CATEGORY);
		// }

		linkViews();
		configureViews();
		refreshActivity(false);

	}

	private void refreshList() {
		AndroidGlobals globals = AndroidGlobals.getInstance();
		ArrayList<Recur> recurs = new ArrayList<Recur>();
		Recur r1 = new Recur();
		r1.setBudgetName("Jedzenie - tydzieÄ 1");
		r1.setAmount(75.0);
		r1.setSpend(90.0);

		Recur r2 = new Recur();
		r2.setBudgetName("Jedzenie - tydzieÄ 2");
		r2.setAmount(75.0);
		r2.setSpend(0.0);

		Recur r3 = new Recur();
		r3.setBudgetName("Jedzenie praca - miesiˆc");
		r3.setAmount(100.0);
		r3.setSpend(10.0);

		recurs.add(r1);
		recurs.add(r2);
		recurs.add(r3);

		if (recurs != null) {
			adapter = new RecurAdapter(this, recurs);
			list.setAdapter(adapter);
			list.setOnItemClickListener(this);
		}
	}

	@Override
	public void refreshActivity(boolean isForceRefresh) {
		// if (ddManager == null) {
		// ddManager = new DownloadDataManager(this,
		// RefreshOptions.refreshProjects, this);
		// refreshList();
		// } else {
		//
		// if (isForceRefresh)
		// ddManager.refresh(isForceRefresh,
		// RefreshOptions.refreshProjects);
		// else {
		refreshList();
		// }
		// }
	}

	@Override
	public void leaveActivity(int requestCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void linkViews() {
		list = (ListView) findViewById(R.id.recur_listview);

	}

	@Override
	public void configureViews() {
		// TODO Auto-generated method stub

	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}
}
