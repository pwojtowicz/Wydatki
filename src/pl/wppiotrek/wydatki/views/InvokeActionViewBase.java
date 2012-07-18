package pl.wppiotrek.wydatki.views;

import pl.wppiotrek.wydatki.entities.InvokeTransactionParameter;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public abstract class InvokeActionViewBase extends
		CustomViewBase<InvokeTransactionParameter> {

	/**
	 * WartoÊç
	 */
	protected String value;

	/**
	 * Etykieta pola
	 */
	protected TextView label;
	/**
	 * Widok etykiety
	 */
	protected View labelView;

	/**
	 * ID widoku etykiety
	 */
	protected Integer labelID;
	/**
	 * DomyÊlna wartoÊç
	 */
	protected String defaultValue;
	/**
	 * Sporób dost´pu do pola
	 */
	protected int defaultAccessMode;

	/**
	 * Listener ˝àdaƒ rozmna˝ania
	 */

	public InvokeActionViewBase(Context context, View view,
			InvokeTransactionParameter content) {
		super(context, view, content);
		// TODO Auto-generated constructor stub
	}

	@Override
	public abstract void linkViews();

	@Override
	public abstract void fillViews();

	@Override
	protected void clean() {
		value = null;
	}

	@Override
	public View getView() {
		manageView();
		return view;
	}

	@Override
	protected void manageView() {
		// if (labelID != null) {
		// this.linkLabelView();
		// this.manageMultiplicity();
		// }
		super.manageView();
		// Parsowanie opcji ze stringa i ustawianie parametrów pól
		parseOptions();
		// Obs∏uga wartoÊci domyÊlnych pól
		manageDefaultValues();
		manageDataSources();
		// Akcje koƒczàce
		finishingActions();
		saveValue();
		// Ustawienie domyÊlego trybu dost´pu...
		// defaultAccessMode = content.getEventParamDefinition()
		// .getDefaultAccessMode();
		//
		// manageAccessMode();
	}

	protected void manageDataSources() {
		// Uzupe∏nianie êróde∏ danych w EventParamInstance
		if (content.getDataSource() != null)
			if (!(content.getValue() != null && content.getValue().length() != 0)) {
				fillFromDataSource();
			}
	}

	/**
	 * Obs∏uga typów dost´pu do pól
	 */
	private void manageAccessMode() {
		// switch (defaultAccessMode) {
		// case DefaultAccessMode.EDITABLE:
		// setEditable();
		// break;
		// case DefaultAccessMode.READ_ONLY:
		// setReadOnly();
		// break;
		// case DefaultAccessMode.INVISIBLE:
		// setInvisible();
		// break;
		// }
	}

	protected abstract void parseOptions();

	/**
	 * Ustaw widok edytowalny
	 */
	protected abstract void setEditable();

	/**
	 * Ustaw widok tylko do odczytu
	 */
	protected abstract void setReadOnly();

	/**
	 * Ustaw widok niewidoczny
	 */
	protected abstract void setInvisible();

	/**
	 * Obs∏uga wartoÊci domyÊlnych
	 */
	protected void manageDefaultValues() {
		// if (!(content.getEventParamValue() != null
		// && content.getEventParamValue().getValue() != null && content
		// .getEventParamValue().getValue().length() != 0)) {
		// fillDefaultValues();
		// }
	}

	/**
	 * Zapis wartoÊci
	 */
	protected abstract void saveValue();

	/**
	 * Uzupe∏nianie wartoÊciami ze êród∏a danych
	 */
	protected abstract void fillFromDataSource();

	/**
	 * Akcje koƒcowe
	 */
	protected abstract void finishingActions();

	/**
	 * Obs∏uga walidacji
	 */
	protected abstract void manageValidation();

	/**
	 * Zapis parametrów
	 * 
	 * @param value
	 *            wartoÊç
	 * @param dbValue
	 *            wartoÊç w bazie danych
	 */
	protected void save() {
		content.setValueAndNotify(value);
	}

	/**
	 * @param labelID
	 *            the labelID to set
	 */
	public void setLabelID(Integer labelID) {
		this.labelID = labelID;
	}

	/**
	 * Kontrola WidzialnoÊci pól
	 * 
	 * @param isVisible
	 */
	protected abstract void manageVisibility(boolean isVisible);

	protected void fillDefaultValues() {
		value = "default";// defaultValue;

	}

}
