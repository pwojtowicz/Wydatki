package pl.wppiotrek.wydatki.adapters;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.entities.Category;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoriesAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Category> items;
	private LayoutInflater mInflater;

	public CategoriesAdapter(Context context, ArrayList<Category> items) {
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
		CategoryAdapterObjectHandler oh = null;
		if (convertView != null)
			oh = (CategoryAdapterObjectHandler) convertView.getTag();

		Object o = getItem(index);
		if (convertView == null) {
			oh = new CategoryAdapterObjectHandler();
			convertView = mInflater.inflate(R.layout.row_category_layout, null);
			oh.name = (TextView) convertView
					.findViewById(R.id.row_category_name);

			oh.details = (TextView) convertView
					.findViewById(R.id.row_category_details);

			oh.lock = (ImageView) convertView
					.findViewById(R.id.row_category_lock);

			oh.subitem = (ImageView) convertView
					.findViewById(R.id.row_category_subitem);
			oh.cbx_selected = (CheckBox) convertView
					.findViewById(R.id.row_cbx_selected);
			convertView.setTag(oh);
		}

		fillRow(oh, convertView, o, index);

		return convertView;
	}

	private void fillRow(CategoryAdapterObjectHandler oh, View convertView,
			Object o, int index) {
		Category c = (Category) o;
		if (c != null) {
			if (c.isActive())
				oh.lock.setVisibility(ImageView.GONE);
			else
				oh.lock.setVisibility(ImageView.VISIBLE);

			if (c.getParentId() == 0)
				oh.subitem.setVisibility(ImageView.GONE);
			else
				oh.subitem.setVisibility(ImageView.VISIBLE);

			oh.name.setText(c.getLvl() + c.getName());

			oh.details.setText(c.getParameters());

			oh.category = c;
		}

	}

	public class CategoryAdapterObjectHandler {
		public CheckBox cbx_selected;
		public Category category;
		public TextView name;
		public TextView details;
		public ImageView lock;
		public ImageView subitem;

	}

}
