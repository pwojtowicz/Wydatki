package pl.wppiotrek.wydatki.activities.edit;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.activities.ProgressActivity;
import pl.wppiotrek.wydatki.units.ResultCodes;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public abstract class EditAbstractActivity<T> extends ProgressActivity {

	protected EditText etbx_name;
	protected CheckBox cbx_isActive;

	protected T currentObject;
	protected boolean isUpdate = false;

	public void onCreate(Bundle savedInstanceState, int viewLayoutId) {
		super.onCreate(savedInstanceState);
		setContentView(viewLayoutId);
		super.setProgressDialogCancelable(false);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			this.isUpdate = bundle.getBoolean("isUpdate");
		}

		linkStandardViews();
		linkViews();
		configureViews();

	}

	protected void linkStandardViews() {
		etbx_name = (EditText) findViewById(R.id.edit_etbx_name);
		cbx_isActive = (CheckBox) findViewById(R.id.edit_cbx_isActive);

		Button b = (Button) findViewById(R.id.bSave);
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				prepareToSave();
			}
		});
		b = (Button) findViewById(R.id.bCancel);
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				leaveActivity(ResultCodes.RESULT_NO_ACTION);
			}
		});
	}

	protected abstract void prepareToSave();

	protected abstract void saveObject();

}
