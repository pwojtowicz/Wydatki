package pl.wppiotrek.wydatki.adapters;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.entities.Parameter;
import pl.wppiotrek.wydatki.support.ListSupport;
import pl.wppiotrek.wydatki.units.ParameterTypes;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class ParametersAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Parameter> items;
	private LayoutInflater mInflater;
	private Boolean isCheckable;

	private ArrayList<Integer> selectedItemsId = new ArrayList<Integer>();

	public ParametersAdapter(Context context, ArrayList<Parameter> items,
			Boolean isCheckable, String selectedItems) {
		if (selectedItems != null)
			selectedItemsId = ListSupport.StringToArrayList(selectedItems);

		this.isCheckable = isCheckable;
		this.context = context;
		this.items = items;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return items.size();
	}

	public Object getItem(int index) {
		return items.get(index);
	}

	public long getItemId(int index) {
		return index;
	}

	public View getView(int index, View convertView, ViewGroup arg2) {
		ParameterAdapterObjectHandler oh = null;
		if (convertView != null)
			oh = (ParameterAdapterObjectHandler) convertView.getTag();

		Object o = getItem(index);
		if (convertView == null) {
			oh = new ParameterAdapterObjectHandler();
			convertView = mInflater.inflate(R.layout.row_property_layout, null);
			oh.name = (TextView) convertView
					.findViewById(R.id.row_parameter_name);
			oh.type = (TextView) convertView
					.findViewById(R.id.row_parameter_type);
			oh.value = (TextView) convertView
					.findViewById(R.id.row_parameter_default);

			oh.lock = (ImageView) convertView
					.findViewById(R.id.row_parameter_lock);

			oh.cbx_selected = (CheckBox) convertView
					.findViewById(R.id.row_cbx_selected);
			oh.cbx_selected.setOnClickListener(new OnClickListener() {

				public void onClick(View view) {
					int itemId = (Integer) view.getTag();
					if (((CheckBox) view).isChecked())
						selectedItemsId.add(itemId);
					else {
						boolean tmp = selectedItemsId.remove((Integer) itemId);
						int p = 0;
					}
				}
			});
			// oh.cbx_selected
			// .setOnCheckedChangeListener(new OnCheckedChangeListener() {
			//
			// public void onCheckedChanged(CompoundButton cbx,
			// boolean value) {
			// int itemId = (Integer) cbx.getTag();
			// if (value)
			// selectedItemsId.add(itemId);
			// else {
			// boolean tmp = selectedItemsId
			// .remove((Integer) itemId);
			// int p = 0;
			// }
			//
			// }
			// });

			convertView.setTag(oh);
		}

		fillRow(oh, convertView, o, index);

		return convertView;
	}

	private void fillRow(ParameterAdapterObjectHandler oh, View convertView,
			Object o, int index) {
		Parameter p = (Parameter) o;
		if (p != null) {
			if (isCheckable)
				oh.cbx_selected.setVisibility(CheckBox.VISIBLE);
			else
				oh.cbx_selected.setVisibility(CheckBox.GONE);
			oh.cbx_selected.setTag(p.getId());

			if (selectedItemsId.contains((Integer) p.getId()))
				oh.cbx_selected.setChecked(true);
			else
				oh.cbx_selected.setChecked(false);

			if (p.isActive())
				oh.lock.setVisibility(ImageView.GONE);
			else
				oh.lock.setVisibility(ImageView.VISIBLE);

			oh.name.setText(p.getName());

			oh.type.setText(ParameterTypes.getParameterName(p.getTypeId()));

			oh.value.setText(p.getDefaultValue());

			oh.parameter = p;
		}
	}

	public ArrayList<Integer> getSelectedItems() {
		return selectedItemsId;
	}

	public class ParameterAdapterObjectHandler {
		public Parameter parameter;
		public TextView name;
		public ImageView lock;
		public TextView type;
		public TextView value;
		public CheckBox cbx_selected;

	}

}
