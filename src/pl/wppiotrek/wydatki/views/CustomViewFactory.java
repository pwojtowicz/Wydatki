package pl.wppiotrek.wydatki.views;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.entities.InvokeTransactionParameter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class CustomViewFactory {

	public static View create(int viewType, Context context,
			InvokeTransactionParameter paramInstance) {
		return createOrFill(viewType, context, paramInstance, false, null);
	}

	/**
	 * Metoda wype¸niajˆca zadany widokdanymi
	 * 
	 * @param viewType
	 *            typ widoku
	 * @param context
	 *            kontekst
	 * @param paramInstance
	 *            instancja parametru
	 * @param requestListener
	 *            listener rozmnaýalnoæci
	 * @param viewToFill
	 *            widok do wype¸nienia, wymagany jeæli fillOnly = true
	 * @return
	 */
	public static View fill(int viewType, Context context,
			InvokeTransactionParameter paramInstance, View viewToFill) {
		return createOrFill(viewType, context, paramInstance, true, viewToFill);
	}

	/**
	 * Metoda tworzˆca i uzupe¸niajˆca widok danymi lub tylko wype¸niajˆca
	 * zadany widokdanymi
	 * 
	 * @param viewType
	 *            typ widoku
	 * @param context
	 *            kontekst
	 * @param paramInstance
	 *            instancja parametru
	 * @param requestListener
	 *            listener rozmnaýalnoæci
	 * @param fillOnly
	 *            true == tylko wype¸nia widok, false == tworzy i wype¸nia widok
	 * @param viewToFill
	 *            widok do wype¸nienia, wymagany jeæli fillOnly = true
	 * @return
	 */
	public static View createOrFill(int viewType, Context context,
			InvokeTransactionParameter paramInstance, boolean fillOnly,
			View viewToFill) {
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = null;
		if (fillOnly)
			view = viewToFill;

		switch (viewType) {
		case ViewType.TEXT_BOX:

			if (view == null)
				view = layoutInflater.inflate(R.layout.invoke_action_text_box,
						null);
			InvokeActionTextBox textBox = new InvokeActionTextBox(context,
					view, paramInstance, false);
			return textBox.getView();

		case ViewType.CHECKBOX:
			if (view == null)
				view = layoutInflater.inflate(R.layout.invoke_action_check_box,
						null);
			InvokeActionCheckbox checkBox = new InvokeActionCheckbox(context,
					view, paramInstance);
			return checkBox.getView();
		case ViewType.NUMBER:
			if (view == null)
				view = layoutInflater.inflate(R.layout.invoke_action_text_box,
						null);
			InvokeActionTextBox number = new InvokeActionTextBox(context, view,
					paramInstance, true);
			return number.getView();
		case ViewType.COMMENT:
			if (view == null)
				view = layoutInflater.inflate(R.layout.invoke_action_comment,
						null);
			InvokeActionComment comment = new InvokeActionComment(context,
					view, paramInstance);
			return comment.getView();
		case ViewType.CALENDAR:
			if (view == null)
				view = layoutInflater.inflate(R.layout.invoke_action_text_box,
						null);
			InvokeActionDate date = new InvokeActionDate(context, view,
					paramInstance);
			return date.getView();

		case ViewType.DROP_DOWN_LIST:
			if (view == null)
				view = layoutInflater.inflate(
						R.layout.invoke_action_drop_down_list, null);
			InvokeActionDropDownList dropDownList = new InvokeActionDropDownList(
					context, view, paramInstance);
			return dropDownList.getView();

		}
		return null;
	}
}
