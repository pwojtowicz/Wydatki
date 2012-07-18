package pl.wppiotrek.wydatki.providers;

public class ControlBundle {
	/**
	 * Dowolny obiekt do przerkazania
	 */
	private Object object;
	/**
	 * Obiekt ma wi«cej danych do pobrania
	 */
	private boolean objectHasMoreItems;
	/**
	 * Odæwieýanie
	 */
	private boolean afterRefresh;
	/**
	 * Polecenie wyczyszczenia danych
	 */
	private boolean doClean;

	/**
	 * @return the doClean
	 */
	public boolean doClean() {
		return doClean;
	}

	/**
	 * @param doClean
	 *            the doClean to set
	 */
	public void setDoClean(boolean doClean) {
		this.doClean = doClean;
	}

	/**
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * @param object the object to set
	 */
	public void setObject(Object object) {
		this.object = object;
	}

	/**
	 * @return the objectHasMoreItems
	 */
	public boolean isObjectHasMoreItems() {
		return objectHasMoreItems;
	}

	/**
	 * @param objectHasMoreItems the objectHasMoreItems to set
	 */
	public void setObjectHasMoreItems(boolean objectHasMoreItems) {
		this.objectHasMoreItems = objectHasMoreItems;
	}

	/**
	 * @return the afterRefresh
	 */
	public boolean isAfterRefresh() {
		return afterRefresh;
	}

	/**
	 * @param afterRefresh the afterRefresh to set
	 */
	public void setAfterRefresh(boolean afterRefresh) {
		this.afterRefresh = afterRefresh;
	}
}
