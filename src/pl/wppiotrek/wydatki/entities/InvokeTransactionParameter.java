package pl.wppiotrek.wydatki.entities;

public class InvokeTransactionParameter extends ModelBase {

	private String name;
	private int typeId;
	private Object defaultValue;
	private boolean hasFocus;
	private String value;
	private String dataSources;

	public InvokeTransactionParameter(String name, int typeId,
			Object defaultValue) {
		this.name = name;
		this.typeId = typeId;
		this.defaultValue = defaultValue;
	}

	public InvokeTransactionParameter(int id, String name, int typeId,
			Object defaultValue, String dataSources) {
		super.setId(id);
		this.name = name;
		this.typeId = typeId;
		this.defaultValue = defaultValue;
		this.dataSources = dataSources;
	}

	public InvokeTransactionParameter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the typeId
	 */
	public int getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId
	 *            the typeId to set
	 */
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the defaultValue
	 */
	public Object getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue
	 *            the defaultValue to set
	 */
	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the hasFocus
	 */
	public boolean hasFocus() {
		return hasFocus;
	}

	/**
	 * @param hasFocus
	 *            the hasFocus to set
	 */
	public void setHasFocus(boolean hasFocus) {
		this.hasFocus = hasFocus;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public void setValueAndNotify(String value) {
		if (value != null) {
			String oldValue = getValue();

			boolean doSave = true;

			if (oldValue != null && oldValue.equals(value))
				doSave = false;

			if (!doSave)
				doSave = true;

			if (doSave) {
				setValue(value);
			}
		}
	}

	public String[] getDataSource() {
		if (dataSources != null) {
			return dataSources.split(";");
		}
		return null;
	}

}
