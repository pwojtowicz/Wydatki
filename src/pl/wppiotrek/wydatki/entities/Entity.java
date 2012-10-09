package pl.wppiotrek.wydatki.entities;

public class Entity extends ModelBase {

	public Entity(int Id, String name) {
		setId(Id);
		setName(name);
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
