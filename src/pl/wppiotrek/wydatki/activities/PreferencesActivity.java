package pl.wppiotrek.wydatki.activities;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.managers.PreferencesManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PreferencesActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.app_preferences);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		PreferencesManager.loadPreferences(this);
		finish();
	}
}
