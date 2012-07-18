package pl.wppiotrek.wydatki.views;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.entities.InvokeTransactionParameter;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class InvokeActionComment extends InvokeActionTextBox {

	private TextView label;
	private EditText field;

	public InvokeActionComment(Context context, View view,
			InvokeTransactionParameter content) {
		super(context, view, content, false);
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
	protected void saveValue() {
		value = field.getText().toString();
		save();

	}

	@Override
	protected void finishingActions() {
		String saved = content.getValue();
		if (saved != null && saved.length() > 0)
			field.setText(saved);

	}

}
