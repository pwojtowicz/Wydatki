package pl.wppiotrek.wydatki.support;

import android.app.Application;
import android.content.Context;

public class WydatkiApp extends Application {

	private static Context context;

	public WydatkiApp(Context context) {
		this.context = context;
	}

	public static Context getContext() {
		return context;
	}

}
