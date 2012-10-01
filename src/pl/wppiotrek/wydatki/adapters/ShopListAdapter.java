package pl.wppiotrek.wydatki.adapters;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.entities.ShopItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ShopListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<ShopItem> items;
	private LayoutInflater mInflater;

	public ShopListAdapter(Context context, ArrayList<ShopItem> items) {
		this.context = context;
		this.items = items;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	public Object getItem(int index) {
		return items.get(index);
	}

	public long getItemId(int index) {
		return index;
	}

	public View getView(int index, View convertView, ViewGroup arg2) {
		ShopListAdapterObjectHandler oh = null;
		if (convertView != null)
			oh = (ShopListAdapterObjectHandler) convertView.getTag();

		Object o = getItem(index);
		if (convertView == null) {
			oh = new ShopListAdapterObjectHandler();
			convertView = mInflater.inflate(R.layout.row_shopitem_layout, null);
			oh.name = (TextView) convertView
					.findViewById(R.id.row_shopitem_name);

			oh.value = (TextView) convertView
					.findViewById(R.id.row_shopitem_value);

			oh.unit = (TextView) convertView
					.findViewById(R.id.row_shopitem_unit);

			convertView.setTag(oh);
		}

		fillRow(oh, convertView, o, index);

		return convertView;
	}

	private void fillRow(ShopListAdapterObjectHandler oh, View convertView,
			Object o, int index) {
		ShopItem si = (ShopItem) o;
		if (si != null) {
			oh.name.setText(si.getName());

			if (si.isComplet()) {
				oh.unit.setVisibility(TextView.GONE);
				oh.value.setVisibility(TextView.GONE);
			} else {
				oh.unit.setVisibility(TextView.VISIBLE);
				oh.value.setVisibility(TextView.VISIBLE);
				oh.unit.setText(si.getUnit());

				oh.value.setText(String.valueOf(si.getValue()));
			}

			oh.shopItem = si;
		}

	}

	public class ShopListAdapterObjectHandler {
		public ShopItem shopItem;
		public TextView name;
		public TextView value;
		public TextView unit;
		public TextView addDate;

	}

}
