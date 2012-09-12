package pl.wppiotrek.wydatki.adapters;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.entities.Recur;
import pl.wppiotrek.wydatki.units.UnitConverter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RecurAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Recur> items;
	private LayoutInflater mInflater;

	public RecurAdapter(Context context, ArrayList<Recur> items) {
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
		RecurAdapterObjectHandler oh = null;
		if (convertView != null)
			oh = (RecurAdapterObjectHandler) convertView.getTag();

		Object o = getItem(index);
		if (convertView == null) {
			oh = new RecurAdapterObjectHandler();
			convertView = mInflater.inflate(R.layout.row_recur, null);
			oh.name = (TextView) convertView
					.findViewById(R.id.row_recur_budget_name);
			oh.amount = (TextView) convertView
					.findViewById(R.id.row_recur_budget_amount);
			oh.date = (TextView) convertView.findViewById(R.id.row_recur_date);
			oh.left = (TextView) convertView
					.findViewById(R.id.row_recur_budget_left);
			oh.progress = (ProgressBar) convertView
					.findViewById(R.id.row_recur_budget_progress);
			convertView.setTag(oh);
		}

		fillRow(oh, convertView, o, index);

		return convertView;
	}

	private void fillRow(RecurAdapterObjectHandler oh, View convertView,
			Object o, int index) {
		Recur r = (Recur) o;
		if (r != null) {

			oh.name.setText(r.getBudgetName());

			oh.amount.setText(UnitConverter.doubleToCurrency(r.getAmount()));

			oh.date.setText(UnitConverter.convertDateToString(r.getDateFrom())
					+ " - " + UnitConverter.convertDateToString(r.getDateTo()));

			r.setLeft(r.getAmount() - r.getSpend());

			oh.left.setText(UnitConverter.doubleToCurrency(r.getLeft()));

			if (r.getLeft() < 0)
				oh.left.setTextColor(context.getResources().getColor(
						R.color.darkRed));
			else
				oh.left.setTextColor(context.getResources().getColor(
						R.color.darkGreen));

			int x = (int) (r.getSpend() * 100 / r.getAmount());
			x = 100 - x;
			if (x < 0)
				x = 0;

			oh.progress.setProgress(x);

			oh.recur = r;
		}

	}

	public class RecurAdapterObjectHandler {
		public Recur recur;
		public TextView amount;
		public TextView name;
		public TextView date;
		public TextView left;

		public ProgressBar progress;

	}

}
