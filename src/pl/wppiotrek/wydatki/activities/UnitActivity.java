package pl.wppiotrek.wydatki.activities;

import pl.wppiotrek.wydatki.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UnitActivity extends Activity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_units);

		linkViews();
		configureViews();

	}

	private void configureViews() {

	}

	private void linkViews() {
		Button btn_settings = (Button) findViewById(R.id.unit_btn_settings);
		btn_settings.setTag(0);
		btn_settings.setOnClickListener(this);

		Button btn_categories = (Button) findViewById(R.id.unit_btn_categories);
		btn_categories.setTag(1);
		btn_categories.setOnClickListener(this);

		Button btn_parameters = (Button) findViewById(R.id.unit_btn_parameters);
		btn_parameters.setTag(2);
		btn_parameters.setOnClickListener(this);

		Button btn_projects = (Button) findViewById(R.id.unit_btn_projects);
		btn_projects.setTag(3);
		btn_projects.setOnClickListener(this);

	}

	public void onClick(View view) {
		int id = (Integer) view.getTag();
		Intent intent = null;
		switch (id) {
		case 0:
			intent = new Intent(this, PreferencesActivity.class);
			break;
		case 1:
			intent = new Intent(this, CategoriesActivity.class);
			break;
		case 2:
			intent = new Intent(this, AttributesActivity.class);
			break;
		case 3:
			intent = new Intent(this, ProjectsActivity.class);
			break;

		default:
			break;
		}
		if (intent != null)
			startActivity(intent);

	}
}
