package pl.wppiotrek.wydatki.views;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.entities.InvokeTransactionParameter;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class InvokeActionCheckbox extends InvokeActionViewBase {

	private CheckBox checkBox;

	public InvokeActionCheckbox(Context context, View view,
			InvokeTransactionParameter content) {
		super(context, view, content);
	}

	@Override
	public void linkViews() {
		this.checkBox = (CheckBox) view
				.findViewById(R.id.invoke_action_check_box_cbx);
	}

	@Override
	public void fillViews() {
		this.checkBox.setText(content.getName());
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				saveValue();
			}
		});

	}

	@Override
	protected void parseOptions() {
		String[] options = new String[] { "1", "0" };// this.prepareOptionsArray();
		this.defaultValue = "False";
		if (options != null && options.length >= 1)
			if (options[0].equals("1"))
				defaultValue = "True";
			else
				defaultValue = "False";
	}

	@Override
	protected void saveValue() {
		if (content != null)
			if (checkBox.isChecked()) {
				value = "True";
			} else {
				value = "False";
			}
		save();
	}

	private void manageCheckBox(String trueOrFalse) {
		if (trueOrFalse.equals("True"))
			checkBox.setChecked(true);
		else if (trueOrFalse.equals("False"))
			checkBox.setChecked(false);

	}

	@Override
	protected void fillDefaultValues() {
		super.fillDefaultValues();
		manageCheckBox(value);
	}

	@Override
	protected void clean() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setEditable() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setReadOnly() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setInvisible() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void fillFromDataSource() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void finishingActions() {
		String trueOrFalse = content.getValue();
		if (trueOrFalse != null && trueOrFalse.length() > 0)
			manageCheckBox(trueOrFalse);
	}

	@Override
	protected void manageValidation() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void manageVisibility(boolean isVisible) {
		// TODO Auto-generated method stub

	}

}
