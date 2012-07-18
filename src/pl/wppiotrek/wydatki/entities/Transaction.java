package pl.wppiotrek.wydatki.entities;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import pl.wppiotrek.wydatki.support.JSONDateDeserializer;
import pl.wppiotrek.wydatki.support.JSONDateSerializer;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = Inclusion.NON_NULL)
public class Transaction extends ModelBase {

	@JsonProperty("AccMinus")
	private int accMinus;
	@JsonProperty("AccPlus")
	private int accPlus;

	@JsonProperty("Note")
	private String note;
	@JsonProperty("Value")
	private Double value;

	@JsonDeserialize(using = JSONDateDeserializer.class)
	@JsonProperty("Date")
	private Date date;

	@JsonProperty("CategoryID")
	private Category category;

	@JsonProperty("ProjectId")
	private int projectId;

	/**
	 * @return the accMinus
	 */
	public int getAccMinus() {
		return accMinus;
	}

	/**
	 * @param accMinus
	 *            the accMinus to set
	 */
	public void setAccMinus(int accMinus) {
		this.accMinus = accMinus;
	}

	/**
	 * @return the accPlus
	 */
	public int getAccPlus() {
		return accPlus;
	}

	/**
	 * @param accPlus
	 *            the accPlus to set
	 */
	public void setAccPlus(int accPlus) {
		this.accPlus = accPlus;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Double value) {
		this.value = value;
	}

	/**
	 * @return the date
	 */
	@JsonSerialize(using = JSONDateSerializer.class)
	public java.util.Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(java.util.Date date) {
		this.date = date;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

}
