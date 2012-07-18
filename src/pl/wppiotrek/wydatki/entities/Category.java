package pl.wppiotrek.wydatki.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = Inclusion.NON_NULL)
public class Category extends ModelBase {

	public Category() {

	}

	public Category(int id, String name, boolean isActive) {
		super.setId(id);
		this.setName(name);
		this.setIsActive(isActive);
	}

	@JsonProperty("Name")
	private String name;

	@JsonProperty("IsActive")
	private Boolean isActive;

	@JsonProperty("ParentId")
	private int parentId;

	@JsonProperty("Attributes")
	private Parameter[] attributes;

	@JsonProperty("RN")
	private Double rn;

	private String description;

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
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
	 * @return the isActive
	 */
	public Boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive
	 *            the isActive to set
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the attributes
	 */
	public Parameter[] getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(Parameter[] attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the rn
	 */
	public Double getRn() {
		return rn;
	}

	/**
	 * @param rn
	 *            the rn to set
	 */
	public void setRn(Double rn) {
		this.rn = rn;
	}

	public void setDescription() {
		if (attributes != null && attributes.length > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			boolean isFirst = true;
			for (Parameter item : attributes) {
				if (!isFirst)
					sb.append(", ");

				sb.append(item.getName());

				if (isFirst)
					isFirst = false;
			}
			sb.append("]");
			this.description = sb.toString();
		} else
			this.description = "";
	}

	public String getParameters() {
		return this.description;
	}

}
