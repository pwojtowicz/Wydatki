package pl.wppiotrek.wydatki.entities;

public class DoubleStringObject {

	public DoubleStringObject(String name, Object value, int typeId) {
		this.Name = name;
		this.Value = value;
		this.TypeId = typeId;
	}

	public String Name;
	public Object Value;
	public int TypeId;

}
