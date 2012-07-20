package pl.wppiotrek.wydatki.adapters;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.entities.Account;
import pl.wppiotrek.wydatki.units.AccountImages;
import pl.wppiotrek.wydatki.units.UnitConverter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountsAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Account> items;
	private LayoutInflater mInflater;

	public AccountsAdapter(Context context, ArrayList<Account> items) {
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
		AccountsAdapterObjectHandler oh = null;
		if (convertView != null)
			oh = (AccountsAdapterObjectHandler) convertView.getTag();

		Object o = getItem(index);
		if (convertView == null) {
			oh = new AccountsAdapterObjectHandler();
			convertView = mInflater.inflate(R.layout.row_account_layout, null);
			oh.name = (TextView) convertView
					.findViewById(R.id.row_account_name);
			oh.balance = (TextView) convertView
					.findViewById(R.id.row_account_balance);
			oh.image = (ImageView) convertView
					.findViewById(R.id.row_account_image);
			oh.lock = (ImageView) convertView
					.findViewById(R.id.row_account_lock);
			convertView.setTag(oh);
		}

		fillRow(oh, convertView, o, index);

		return convertView;
	}

	private void fillRow(AccountsAdapterObjectHandler oh, View convertView,
			Object o, int index) {
		Account a = (Account) o;
		if (a != null) {
			if (a.isActive())
				oh.lock.setVisibility(ImageView.GONE);
			else
				oh.lock.setVisibility(ImageView.VISIBLE);

			oh.name.setText(a.getName());

			oh.balance.setText(UnitConverter.doubleToCurrency(a.getBalance()));

			if (a.getBalance() < 0)
				oh.balance.setTextColor(context.getResources().getColor(
						R.color.darkRed));
			else if (a.getBalance() > 0)
				oh.balance.setTextColor(context.getResources().getColor(
						R.color.darkGreen));
			oh.image.setImageDrawable(AccountImages.getImageForImageIndex(
					a.getImageIndex(), context));

			oh.account = a;
		}

	}

	public class AccountsAdapterObjectHandler {
		public Account account;
		public TextView balance;
		public TextView name;

		public ImageView image;
		public ImageView lock;

	}

}
