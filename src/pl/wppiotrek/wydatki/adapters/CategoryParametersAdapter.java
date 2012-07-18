package pl.wppiotrek.wydatki.adapters;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.entities.Parameter;
import pl.wppiotrek.wydatki.units.ParameterTypes;
import pl.wppiotrek.wydatki.units.ViewState;
import pl.wppiotrek.wydatki.views.ViewType;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryParametersAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Object> items = new ArrayList<Object>();
	private LayoutInflater mInflater;

	public CategoryParametersAdapter(Context context) {
		this.context = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void addParameter(Object object) {
		items.add(object);
	}

	public void clear() {
		items = new ArrayList<Object>();
	}

	public void refresh() {
		this.notifyDataSetChanged();
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

	@Override
	public int getItemViewType(int index) {
		Object o = items.get(index);
		if (o != null)
			if (o instanceof Parameter)
				return ViewType.DEFAULT;

		return ViewType.CONTROL;
	}

	public View getView(int index, View convertView, ViewGroup arg2) {
		ParameterAdapterObjectHandler oh = null;
		int viewType = getItemViewType(index);
		Object obj = null;
		if (convertView != null) {
			obj = convertView.getTag();
		}

		Object o = getItem(index);
		if (convertView == null
				|| (obj != null && viewType == ViewType.DEFAULT && obj instanceof ViewState)
				|| (obj != null && viewType == ViewType.CONTROL && obj instanceof ParameterAdapterObjectHandler)) {

			switch (viewType) {
			case ViewType.DEFAULT: {
				oh = new ParameterAdapterObjectHandler();
				convertView = mInflater.inflate(R.layout.row_property_layout,
						null);
				oh.name = (TextView) convertView
						.findViewById(R.id.row_parameter_name);
				oh.type = (TextView) convertView
						.findViewById(R.id.row_parameter_type);
				oh.value = (TextView) convertView
						.findViewById(R.id.row_parameter_default);

				oh.lock = (ImageView) convertView
						.findViewById(R.id.row_parameter_lock);
				convertView.setTag(oh);
			}
				break;
			case ViewType.CONTROL:
				convertView = mInflater.inflate(
						R.layout.control_row_section_header, null);
				break;
			}

		}

		if (o instanceof Parameter) {
			oh = (ParameterAdapterObjectHandler) convertView.getTag();
			fillRow(oh, convertView, o, index);
		} else {
			convertView.setTag(ViewState.Section);
			fillControll(o, convertView);
		}

		return convertView;
	}

	private void fillControll(Object o, View convertView) {
		TextView sectionName = (TextView) convertView
				.findViewById(R.id.control_row_tbx_section_name);
		sectionName.setText(o.toString());

	}

	private void fillRow(ParameterAdapterObjectHandler oh, View convertView,
			Object o, int index) {
		Parameter p = (Parameter) o;
		if (p != null) {
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

	public class ParameterAdapterObjectHandler {
		public Parameter parameter;
		public TextView name;
		public ImageView lock;
		public TextView type;
		public TextView value;

	}

}