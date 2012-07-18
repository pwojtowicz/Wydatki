package pl.wppiotrek.wydatki.views;

import pl.wppiotrek.wydatki.entities.InvokeTransactionParameter;
import android.content.Context;
import android.view.View;

public class InvokeActionDate extends InvokeActionTextBox {

	public InvokeActionDate(Context context, View view,
			InvokeTransactionParameter content) {
		super(context, view, content, false);
	}

}
