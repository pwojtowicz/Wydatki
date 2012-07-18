package pl.wppiotrek.wydatki.adapters;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.entities.Project;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProjectsAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Project> items;
	private LayoutInflater mInflater;

	public ProjectsAdapter(Context context, ArrayList<Project> items) {
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
		ProjectAdapterObjectHandler oh = null;
		if (convertView != null)
			oh = (ProjectAdapterObjectHandler) convertView.getTag();

		Object o = getItem(index);
		if (convertView == null) {
			oh = new ProjectAdapterObjectHandler();
			convertView = mInflater.inflate(R.layout.row_project_layout, null);
			oh.name = (TextView) convertView
					.findViewById(R.id.row_project_name);

			oh.lock = (ImageView) convertView
					.findViewById(R.id.row_project_lock);

			convertView.setTag(oh);
		}

		fillRow(oh, convertView, o, index);

		return convertView;
	}

	private void fillRow(ProjectAdapterObjectHandler oh, View convertView,
			Object o, int index) {
		Project p = (Project) o;
		if (p != null) {
			if (p.isActive())
				oh.lock.setVisibility(ImageView.GONE);
			else
				oh.lock.setVisibility(ImageView.VISIBLE);

			oh.name.setText(p.getName());
			oh.project = p;
		}

	}

	public class ProjectAdapterObjectHandler {
		public Project project;
		public TextView name;
		public ImageView lock;

	}
}
