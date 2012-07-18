package pl.wppiotrek.wydatki.views;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.entities.InvokeTransactionParameter;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class InvokeActionDropDownList extends InvokeActionViewBase {

	private TextView label;

	/**
	 * Wybrana wartoÊç z pola
	 */
	private String valueString;
	/**
	 * Pole z wartoÊciami
	 */
	private Spinner field;
	/**
	 * TreÊç z datasource'a
	 */
	private String[] dataSourceValues;

	public InvokeActionDropDownList(Context context, View view,
			InvokeTransactionParameter content) {
		super(context, view, content);
	}

	@Override
	public void linkViews() {
		this.label = (TextView) view.findViewById(R.id.invoke_action_label);
		field = (Spinner) view.findViewById(R.id.invoke_action_dropdown_spiner);

		field.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> adapterView, View view,
					int index, long id) {
				saveValue();
			}

			public void onNothingSelected(AdapterView<?> adapterView) {
			}
		});

		label.setText(content.getName());
	}

	@Override
	public void fillViews() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void clean() {
		// TODO Auto-generated method stub

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
		String selectedItem = (String) field.getSelectedItem();

		int index = field.getSelectedItemPosition();
		if (index >= 0 && selectedItem != null) {
			value = selectedItem;

			save();
		}
	}

	@Override
	protected void fillFromDataSource() {
		dataSourceValues = content.getDataSource();
		if (dataSourceValues != null && dataSourceValues.length > 0) {

			ArrayAdapter aa = new ArrayAdapter(context,
					android.R.layout.simple_spinner_item, dataSourceValues);

			aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			field.setAdapter(aa);

			field.setSelection(0);

		}
	}

	@Override
	protected void finishingActions() {
		// else {
		// JeÊli kontener wartoÊci istnieje i posiada w sobie ciàg znaków
		String saved = content.getValue();
		if (saved != null && saved.length() > 0) {
			if (dataSourceValues == null)
				fillFromDataSource();
			if (dataSourceValues != null) {
				int contentIndex = returnItemPosition(saved);
				field.setSelection(contentIndex);
			}
		}

	}

	private int returnItemPosition(String value) {
		for (int i = 0; i < dataSourceValues.length; i++) {
			String dsv = dataSourceValues[i];

			if (dsv.equals(value))
				return i;
			if (value == null)
				return i;

		}
		return 0;
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
