package pl.wppiotrek.wydatki.adapters;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.entities.SpinnerObject;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

public class SpinnerAdapter extends ArrayAdapter<SpinnerObject> {

	private SpinnerObject[] objects;
	private LayoutInflater inflater;

	public SpinnerAdapter(Context context, int textViewResourceId,
			SpinnerObject[] objects) {
		super(context, textViewResourceId, objects);

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.objects = objects;
	}

	public void refresh() {
		this.notifyDataSetChanged();
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent) {

		SpinerHelper helper = null;
		if (convertView != null)
			helper = (SpinerHelper) convertView.getTag();

		if (convertView == null) {
			helper = new SpinerHelper();
			convertView = inflater.inflate(R.layout.single_textview_list_row,
					parent, false);
			helper.text = (TextView) convertView
					.findViewById(R.id.single_textview_list_row_content_textview);

		}

		SpinnerObject object = objects[position];
		helper.object = object;

		if (helper.text != null)
			helper.text.setText(object.getValue());
		convertView.setTag(helper);
		return convertView;
	}

	public class SpinerHelper {
		TextView text;
		RadioButton radioButton;
		int position;
		public SpinnerObject object;
	}

}
