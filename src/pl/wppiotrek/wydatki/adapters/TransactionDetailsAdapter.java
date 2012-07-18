package pl.wppiotrek.wydatki.adapters;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.entities.DoubleStringObject;
import pl.wppiotrek.wydatki.units.ParameterTypes;
import pl.wppiotrek.wydatki.units.UnitConverter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TransactionDetailsAdapter extends BaseAdapter {

	private ArrayList<DoubleStringObject> elements = new ArrayList<DoubleStringObject>();
	private LayoutInflater mInflater;
	private Context context;

	public TransactionDetailsAdapter(Context context,
			ArrayList<DoubleStringObject> elements) {
		this.context = context;
		this.elements = elements;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return elements.size();
	}

	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return elements.get(index);
	}

	public long getItemId(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int index, View convertView, ViewGroup arg2) {
		TransactionDetailsObjectHandler oh = null;
		if (convertView != null)
			oh = (TransactionDetailsObjectHandler) convertView.getTag();

		Object o = getItem(index);
		if (convertView == null) {
			oh = new TransactionDetailsObjectHandler();
			convertView = mInflater.inflate(
					R.layout.row_transaction_details_layout, null);
			oh.Name = (TextView) convertView
					.findViewById(R.id.row_tran_det_name);
			oh.Value = (TextView) convertView
					.findViewById(R.id.row_tran_det_value);

			convertView.setTag(oh);
		}
		fillView(oh, o);

		return convertView;
	}

	private void fillView(TransactionDetailsObjectHandler oh, Object o) {
		DoubleStringObject dso = (DoubleStringObject) o;
		if (dso != null) {
			oh.Name.setText(dso.Name);

			switch (dso.TypeId) {
			case ParameterTypes.ParameterCurrency:
			case ParameterTypes.ParameterNumber:

				if (dso.TypeId == ParameterTypes.ParameterCurrency) {
					oh.Value.setText(UnitConverter
							.doubleToCurrency((Double) dso.Value));
					if (Double.parseDouble(dso.Value.toString()) < 0)
						oh.Value.setTextColor(context.getResources().getColor(
								R.color.darkRed));
					else
						oh.Value.setTextColor(context.getResources().getColor(
								R.color.darkGreen));
				} else
					oh.Value.setText(dso.Value.toString());

				break;
			case ParameterTypes.ParameterText:
				oh.Value.setText((String) dso.Value);
				break;

			case ParameterTypes.ParameterBoolean:
				String value = (Boolean.parseBoolean(dso.Value.toString())) ? "TAK"
						: "NIE";
				oh.Value.setText(value);
				break;
			default:
				oh.Value.setText(dso.Value.toString());
				break;
			}

			oh.object = dso;

		}

	}

	private class TransactionDetailsObjectHandler {
		DoubleStringObject object;
		TextView Name;
		TextView Value;
	}

}
