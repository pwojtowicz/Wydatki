package pl.wppiotrek.wydatki.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = Inclusion.NON_NULL)
public class Account extends ModelBase {

	public Account() {

	}

	public Account(int id) {
		super.setId(id);
	}

	public Account(int id, String name, Double balance, Boolean isActive) {
		super.setId(id);
		setName(name);
		this.setBalance(balance);
		this.setIsActive(isActive);
	}

	@JsonProperty("Name")
	private String name;

	@JsonProperty("Balance")
	private Double balance;

	@JsonProperty("IsActive")
	private Boolean isActive;

	@JsonProperty("IsVisibleForAll")
	private Boolean isVisibleForAll;

	@JsonProperty("IsSumInGlobalBalance")
	private Boolean isSumInGlobalBalance;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Boolean isActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the isVisibleForAll
	 */
	public Boolean isVisibleForAll() {
		return isVisibleForAll;
	}

	/**
	 * @param isVisibleForAll
	 *            the isVisibleForAll to set
	 */
	public void setIsVisibleForAll(Boolean isVisibleForAll) {
		this.isVisibleForAll = isVisibleForAll;
	}

	/**
	 * @return the isSumInGlobalBalance
	 */
	public Boolean isSumInGlobalBalance() {
		return isSumInGlobalBalance;
	}

	/**
	 * @param isSumInGlobalBalance
	 *            the isSumInGlobalBalance to set
	 */
	public void setIsSumInGlobalBalance(Boolean isSumInGlobalBalance) {
		this.isSumInGlobalBalance = isSumInGlobalBalance;
	}

	// /**
	// * @return the isVisibleForAll
	// */
	// public Boolean getIsVisibleForAll() {
	// return isVisibleForAll;
	// }
	//
	// /**
	// * @param isVisibleForAll
	// * the isVisibleForAll to set
	// */
	// public void setIsVisibleForAll(Boolean isVisibleForAll) {
	// this.isVisibleForAll = isVisibleForAll;
	// }

}
