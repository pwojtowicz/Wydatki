package pl.wppiotrek.wydatki.managers;

import pl.wppiotrek.wydatki.support.AndroidGlobals;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesManager {

	public static void loadPreferences(Context context) {

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		AndroidGlobals globals = AndroidGlobals.getInstance();
		globals.setUserLogin(prefs.getString("prop_user_login", ""));
		globals.setUserPassword(prefs.getBoolean("prop_remember_password",
				false) ? prefs.getString("prop_user_password", "") : "");

		globals.setServerAddress(prefs.getString("prop_server_address", "")
				.trim());
	}

}
