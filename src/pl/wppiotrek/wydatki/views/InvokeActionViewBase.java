package pl.wppiotrek.wydatki.views;

import pl.wppiotrek.wydatki.entities.InvokeTransactionParameter;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public abstract class InvokeActionViewBase extends
		CustomViewBase<InvokeTransactionParameter> {

	/**
	 * Warto�
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
	 * Domy�lna warto�
	 */
	protected String defaultValue;
	/**
	 * Spor�b dost�pu do pola
	 */
	protected int defaultAccessMode;

	/**
	 * Listener ��da� rozmna�ania
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
		// Parsowanie opcji ze stringa i ustawianie parametr�w p�l
		parseOptions();
		// Obs�uga warto�ci domy�lnych p�l
		manageDefaultValues();
		manageDataSources();
		// Akcje ko�cz�ce
		finishingActions();
		saveValue();
		// Ustawienie domy�lego trybu dost�pu...
		// defaultAccessMode = content.getEventParamDefinition()
		// .getDefaultAccessMode();
		//
		// manageAccessMode();
	}

	protected void manageDataSources() {
		// Uzupe�nianie �r�de� danych w EventParamInstance
		if (content.getDataSource() != null)
			if (!(content.getValue() != null && content.getValue().length() != 0)) {
				fillFromDataSource();
			}
	}

	/**
	 * Obs�uga typ�w dost�pu do p�l
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
	 * Obs�uga warto�ci domy�lnych
	 */
	protected void manageDefaultValues() {
		// if (!(content.getEventParamValue() != null
		// && content.getEventParamValue().getValue() != null && content
		// .getEventParamValue().getValue().length() != 0)) {
		// fillDefaultValues();
		// }
	}

	/**
	 * Zapis warto�ci
	 */
	protected abstract void saveValue();

	/**
	 * Uzupe�nianie warto�ciami ze �r�d�a danych
	 */
	protected abstract void fillFromDataSource();

	/**
	 * Akcje ko�cowe
	 */
	protected abstract void finishingActions();

	/**
	 * Obs�uga walidacji
	 */
	protected abstract void manageValidation();

	/**
	 * Zapis parametr�w
	 * 
	 * @param value
	 *            warto�
	 * @param dbValue
	 *            warto� w bazie danych
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
	 * Kontrola Widzialno�ci p�l
	 * 
	 * @param isVisible
	 */
	protected abstract void manageVisibility(boolean isVisible);

	protected void fillDefaultValues() {
		value = "default";// defaultValue;

	}

}
