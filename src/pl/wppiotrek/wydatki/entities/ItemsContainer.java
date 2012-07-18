package pl.wppiotrek.wydatki.entities;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemsContainer<T> extends ModelBase {
	@JsonProperty("Items")
	private T[] items;
	@JsonProperty("TotalCount")
	private int totalCount;

	public boolean isEmpty() {
		return (items.length == 0);
	}

	// Getters / Setters
	/**
	 * @return the items
	 */
	public T[] getItems() {
		return items;
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(T[] items) {
		this.items = items;
	}

	/**
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 
	 */
	public void removeNullReferences() {
		ArrayList<T> elements = new ArrayList<T>();
		for (T item : items) {
			if (item != null)
				elements.add(item);
		}
		// items= new T[elements.size()];

	}
}
