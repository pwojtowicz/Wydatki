package pl.wppiotrek.wydatki.views;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.entities.InvokeTransactionParameter;
import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class InvokeActionTextBox extends InvokeActionViewBase {

	private TextView label;
	private EditText field;
	private boolean isNumber;

	public InvokeActionTextBox(Context context, View view,
			InvokeTransactionParameter content, boolean isNumber) {
		super(context, view, content);
		this.isNumber = isNumber;
	}

	@Override
	public void linkViews() {
		this.field = (EditText) view
				.findViewById(R.id.invoke_action_text_edittext);
		this.label = (TextView) view.findViewById(R.id.invoke_action_label);

	}

	@Override
	public void fillViews() {
		this.label.setText(content.getName());

		field.setTag(content);
		field.setSingleLine();

		if (isNumber) {
			field.setInputType(InputType.TYPE_CLASS_NUMBER
					| InputType.TYPE_NUMBER_FLAG_DECIMAL);
		} else {
			field.setInputType(InputType.TYPE_CLASS_TEXT);
		}

		// Zapisywanie wartoæci do parametru gdy pole traci focus
		field.setOnFocusChangeListener(new OnFocusChangeListener() {

			public void onFocusChange(View view, boolean hasFocus) {
				if (!hasFocus) {
					content.setHasFocus(false);

					saveValue();

				} else
					content.setHasFocus(true);
			}
		});
		field.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				EditText et = (EditText) v;
				et.clearFocus();
				et.requestFocus();
			}
		});

	}

	@Override
	protected void clean() {
		if (field != null) {
			field.setText("");
			field.setHint("");
			field.setFilters(new InputFilter[] {});
			value = null;
		}
		super.clean();
	}

	@Override
	protected void fillDefaultValues() {
		super.fillDefaultValues();
		field.setText(value);
	}

	@Override
	protected void parseOptions() {
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
	protected void saveValue() {
		value = field.getText().toString();
		save();

	}

	@Override
	protected void fillFromDataSource() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void finishingActions() {
		String saved = content.getValue();
		if (saved != null && saved.length() > 0)
			field.setText(saved);

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
