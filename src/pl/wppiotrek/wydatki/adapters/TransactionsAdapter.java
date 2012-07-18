package pl.wppiotrek.wydatki.adapters;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.entities.Category;
import pl.wppiotrek.wydatki.entities.ItemsContainer;
import pl.wppiotrek.wydatki.entities.Transaction;
import pl.wppiotrek.wydatki.errors.CommunicationException;
import pl.wppiotrek.wydatki.errors.ExceptionErrorCodes;
import pl.wppiotrek.wydatki.interfaces.IOnObjectsReceivedListener;
import pl.wppiotrek.wydatki.providers.ControlBundle;
import pl.wppiotrek.wydatki.support.AndroidGlobals;
import pl.wppiotrek.wydatki.support.ControlView;
import pl.wppiotrek.wydatki.support.ListSupport;
import pl.wppiotrek.wydatki.units.UnitConverter;
import pl.wppiotrek.wydatki.units.ViewState;
import pl.wppiotrek.wydatki.views.ViewType;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TransactionsAdapter extends BaseAdapter implements
		IOnObjectsReceivedListener {

	/**
	 * Czy adapter ma dodaç pole "pobierz kolejne"
	 */
	private boolean hasMore = false;
	/**
	 * Czy by∏o odÊwia˝anie danych
	 */
	private boolean afterRefresh = false;
	/**
	 * Czy by∏o odÊwia˝anie danych
	 */
	private boolean isRefreshing = false;

	private ViewState controlViewState = ViewState.Normal;

	private Context context;
	private ArrayList<Transaction> items = new ArrayList<Transaction>();
	private LayoutInflater mInflater;
	private View controlView;

	public TransactionsAdapter(Context context, ArrayList<Transaction> items) {
		this.context = context;
		this.items = items;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public TransactionsAdapter(Context context) {
		this.context = context;
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

	@Override
	public int getItemViewType(int index) {
		if (items.get(index) != null)
			return ViewType.DEFAULT;
		else
			return ViewType.CONTROL;
	}

	public View getView(int index, View convertView, ViewGroup arg2) {
		TransactionAdapterObjectHandler oh = null;
		int viewType = getItemViewType(index);
		Object obj = null;
		if (convertView != null) {
			obj = convertView.getTag();
		}

		Object o = getItem(index);
		if (convertView == null
				|| (obj != null && viewType == ViewType.DEFAULT && obj instanceof ViewState)
				|| (obj != null && viewType == ViewType.CONTROL && obj instanceof TransactionAdapterObjectHandler)) {

			switch (viewType) {
			case ViewType.DEFAULT: {
				oh = new TransactionAdapterObjectHandler();
				convertView = mInflater.inflate(
						R.layout.row_transaction_layout, null);
				oh.value = (TextView) convertView
						.findViewById(R.id.row_transaction_value);
				oh.accounts = (TextView) convertView
						.findViewById(R.id.row_transaction_accounts);
				oh.note = (TextView) convertView
						.findViewById(R.id.row_transaction_note);
				oh.date = (TextView) convertView
						.findViewById(R.id.row_transaction_date);
				convertView.setTag(oh);
			}
				break;
			case ViewType.CONTROL:
				convertView = mInflater
						.inflate(R.layout.control_row_view, null);
				break;
			}

		}
		if (o != null) {
			fillRow(convertView, o, index);
		} else {
			controlView = convertView;
			setDownloadView();
		}

		return convertView;
	}

	private void fillRow(View convertView, Object o, int index) {
		TransactionAdapterObjectHandler oh = (TransactionAdapterObjectHandler) convertView
				.getTag();
		Transaction transaction = (Transaction) o;
		if (transaction != null) {
			AndroidGlobals globals = AndroidGlobals.getInstance();

			StringBuilder accounts = new StringBuilder();
			if (transaction.getAccMinus() > 0)
				accounts.append(globals.getAccountById(
						transaction.getAccMinus()).getName());

			if (transaction.getAccMinus() > 0 && transaction.getAccPlus() > 0)
				accounts.append(" >> ");

			if (transaction.getAccPlus() > 0)
				accounts.append(globals
						.getAccountById(transaction.getAccPlus()).getName());

			oh.accounts.setText(accounts.toString());

			StringBuilder note = new StringBuilder();
			if (transaction.getCategory() != null
					&& transaction.getCategory().getName() != null)
				note.append(transaction.getCategory().getName());
			if (transaction.getNote() != null
					&& transaction.getNote().length() > 0)
				note.append(" (" + transaction.getNote() + ")");

			oh.note.setText(note.toString());

			oh.value.setText(UnitConverter.doubleToCurrency(transaction
					.getValue()));

			oh.date.setText(UnitConverter.dateTimeString(transaction.getDate()));

			if (transaction.getAccMinus() > 0 && transaction.getAccPlus() > 0) {
				oh.value.setTextColor(context.getResources().getColor(
						R.color.yellow));
			} else if (transaction.getAccMinus() > 0)
				oh.value.setTextColor(context.getResources().getColor(
						R.color.darkRed));
			else if (transaction.getAccPlus() > 0)
				oh.value.setTextColor(context.getResources().getColor(
						R.color.darkGreen));
			oh.transaction = transaction;
		}

	}

	public class TransactionAdapterObjectHandler {
		public Transaction transaction;
		public TextView value;
		public TextView accounts;
		public TextView note;
		public TextView date;

	}

	public void onObjectsRequestStarted() {
		if (hasMore && !isRefreshing) {
			controlViewState = ViewState.Downloading;
			this.setDownloadView();
		}
	}

	private void setDownloadView() {
		ControlView control = new ControlView(context, controlView,
				controlViewState);
		control.addCustomMessage(ViewState.Normal,
				R.string.transaction_list_get_more_issues);
		control.addCustomMessage(ViewState.Downloading,
				R.string.transaction_list_getting_more_issues);
		controlView = control.getView();
	}

	public void onObjectsRequestEnded() {
		// TODO Auto-generated method stub

	}

	public void onObjectsNotReceived(Object exception, Object object) {
		CommunicationException comException = (CommunicationException) exception;
		ControlBundle bundle = (ControlBundle) object;
		if (!isRefreshing) {

			if (comException.getErrorCode() == ExceptionErrorCodes.NoObjects)
				controlViewState = ViewState.NoObjects;
			else

				controlViewState = ViewState.DownloadException;
			if (this.items.size() == 0) {
				this.items.add(null);
				this.notifyDataSetChanged();
			}
			this.setDownloadView();
		}
		isRefreshing = false;
	}

	public void onObjectsReceived(Object object, Object object2) {
		if (object instanceof String && ((String) object).equals("OK")) {

		} else {
			ControlBundle bundle = (ControlBundle) object2;
			// wykonanie bezwarunkowego czyszczenia
			if (bundle.doClean()) {
				this.items.clear();
				// //Log.d("SEARCH", "CANCELLED");
			}

			ItemsContainer<Transaction> transactions = (ItemsContainer<Transaction>) object;
			if (transactions.getItems().length > 0) {
				AndroidGlobals g = AndroidGlobals.getInstance();
				ArrayList<Category> categories = g.getCategoryList();
				for (Transaction item : transactions.getItems()) {
					if (item.getCategory() != null) {
						Category cat = ListSupport.getCategoryById(item
								.getCategory().getId(), categories);
						if (cat != null)
							item.getCategory().setName(cat.getName());
					}
				}
			}

			// TODO: sprawdziç, czy dzia∏a ok, po dodany OR, bo nie dzia∏a∏o
			// poprawnie odÊwie˝anie
			if (!isRefreshing || bundle.isAfterRefresh()) {
				controlViewState = ViewState.Normal;
				this.hasMore = bundle.isObjectHasMoreItems();
				this.afterRefresh = bundle.isAfterRefresh();

				if (afterRefresh)
					this.setTransactions(transactions);
				else {
					if (this.items.size() > 0)
						this.addTransactions(transactions);
					else
						this.setTransactions(transactions);
				}

				if (this.items.size() == 0) {
					controlViewState = ViewState.NoObjects;
					this.items.add(null);
					this.notifyDataSetChanged();
				}
			}
			isRefreshing = false;
		}

	}

	public void setTransactions(ItemsContainer<Transaction> issuesContainer) {
		this.items.clear();
		for (Transaction issue : issuesContainer.getItems()) {
			this.items.add(issue);
		}
		if (hasMore)
			this.items.add(null);
		this.notifyDataSetChanged();
	}

	public ArrayList<Transaction> getIssues() {
		return this.items;
	}

	public void addTransactions(ItemsContainer<Transaction> issuesContainer) {
		this.items.remove(this.items.size() - 1);
		for (Transaction issue : issuesContainer.getItems()) {
			this.items.add(issue);

		}
		if (hasMore)
			this.items.add(null);
		this.notifyDataSetChanged();
	}

	public void onObjectsProgressUpdate(int progress) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the isRefreshing
	 */
	public boolean isRefreshing() {
		return isRefreshing;
	}

	/**
	 * @param isRefreshing
	 *            the isRefreshing to set
	 */
	public void setRefreshing(boolean isRefreshing) {
		this.isRefreshing = isRefreshing;
	}

	public void clear() {
		items.clear();
		this.notifyDataSetChanged();

	}

}
