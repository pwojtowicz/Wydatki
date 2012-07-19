package pl.wppiotrek.wydatki.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import pl.wppiotrek.wydatki.support.AndroidGlobals;

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
	private String rn;

	private String description = "";
	private String lvl = "";

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
	public String getRn() {
		return rn;
	}

	/**
	 * @param rn
	 *            the rn to set
	 */
	public void setRn(String rn) {
		this.rn = rn;
		if (rn != null) {
			String[] items = rn.split("\\.");
			if (items.length > 1) {
				for (int i = 0; i < items.length - 1; i++) {
					lvl += "--";
				}
			}
		}
	}

	private boolean checkDescription = false;

	public void setDescription() {
		if (!checkDescription) {
			checkDescription = true;
			AndroidGlobals globals = AndroidGlobals.getInstance();

			if (attributes != null && attributes.length > 0) {
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				boolean isFirst = true;
				for (Parameter item : attributes) {
					Parameter p = globals.getParameterById(item.getId());
					if (!isFirst)
						sb.append(", ");

					sb.append(p.getName());

					if (isFirst)
						isFirst = false;
				}
				sb.append("]");
				this.description = sb.toString();
			} else
				this.description = "";
		}
	}

	public String getParameters() {
		return this.description;
	}

	public String getLvl() {
		return lvl;
	}

	public void setLvl(String lvl) {
		this.lvl = lvl;
	}

}
