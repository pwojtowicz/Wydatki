package pl.wppiotrek.wydatki.entities;

import java.util.Date;

import pl.wppiotrek.wydatki.units.Units;

public class ShopItem extends ModelBase {
	private Date addDate;
	private String name;
	private Double value;
	private int unit;
	private boolean isVisibleForAll;
	private int userId;

	private boolean isComplet;

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isVisibleForAll() {
		return isVisibleForAll;
	}

	public void setVisibleForAll(boolean isVisibleForAll) {
		this.isVisibleForAll = isVisibleForAll;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean isComplet() {
		return isComplet;
	}

	public void setComplet(boolean isComplet) {
		this.isComplet = isComplet;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public int getUnit() {
		return unit;
	}

	public String getUnitName() {
		return Units.getParameterName(unit);
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}
}
