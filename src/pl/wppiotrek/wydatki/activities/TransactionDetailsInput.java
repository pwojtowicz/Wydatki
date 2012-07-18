package pl.wppiotrek.wydatki.activities;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.adapters.TransactionDetailsAdapter;
import pl.wppiotrek.wydatki.entities.DoubleStringObject;
import pl.wppiotrek.wydatki.entities.Parameter;
import pl.wppiotrek.wydatki.entities.Transaction;
import pl.wppiotrek.wydatki.support.AndroidGlobals;
import pl.wppiotrek.wydatki.units.ParameterTypes;
import pl.wppiotrek.wydatki.units.UnitConverter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

public class TransactionDetailsInput extends Activity implements
		OnClickListener {

	private ListView listView;
	private Button btn_OK;
	private Button btn_CANCEL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.transaction_details_input);

		linkViews();
		configureViews();
	}

	private void linkViews() {
		listView = (ListView) findViewById(R.id.transaction_details_listview);
		btn_OK = (Button) findViewById(R.id.bOK);
		btn_CANCEL = (Button) findViewById(R.id.bCancel);

		btn_OK.setOnClickListener(this);
		btn_CANCEL.setOnClickListener(this);

	}

	public void onClick(View view) {
		finish();

	}

	private void configureViews() {
		Transaction transaction = AndroidGlobals.getInstance()
				.getCurrentSelectedTransaction();
		if (transaction != null) {
			AndroidGlobals globals = AndroidGlobals.getInstance();

			ArrayList<DoubleStringObject> items = new ArrayList<DoubleStringObject>();
			items.add(new DoubleStringObject("Data", UnitConverter
					.dateTimeString(transaction.getDate()),
					ParameterTypes.ParameterText));
			if (transaction.getAccMinus() > 0) {
				items.add(new DoubleStringObject("Z rachunku", globals
						.getAccountById(transaction.getAccMinus()).getName(),
						ParameterTypes.ParameterText));
				items.add(new DoubleStringObject("Kwota wydana", transaction
						.getValue() * -1, ParameterTypes.ParameterCurrency));
			}
			if (transaction.getAccPlus() > 0) {
				items.add(new DoubleStringObject("Na rachunek", globals
						.getAccountById(transaction.getAccPlus()).getName(),
						ParameterTypes.ParameterText));
				items.add(new DoubleStringObject("Kwota wp¸ywu", transaction
						.getValue(), ParameterTypes.ParameterCurrency));
			}
			if (transaction.getCategory() != null) {
				items.add(new DoubleStringObject("Kategoria", transaction
						.getCategory().getName(), ParameterTypes.ParameterText));
			}

			if (transaction.getNote() != null
					&& transaction.getNote().length() > 0)
				items.add(new DoubleStringObject("Notatka", transaction
						.getNote(), ParameterTypes.ParameterText));
			if (transaction.getCategory() != null) {
				if (transaction.getCategory().getAttributes() != null) {

					for (Parameter parameter : transaction.getCategory()
							.getAttributes()) {

						Parameter param = globals.getParameterById(parameter
								.getId());

						items.add(new DoubleStringObject(param.getName(),
								parameter.getValue(), param.getTypeId()));
					}
				}
			}

			if (transaction.getProjectId() > 0) {
				items.add(new DoubleStringObject("Projekt", String
						.valueOf(transaction.getProjectId()),
						ParameterTypes.ParameterText));
			}

			TransactionDetailsAdapter adapter = new TransactionDetailsAdapter(
					this, items);
			listView.setAdapter(adapter);
		}

	}

}
