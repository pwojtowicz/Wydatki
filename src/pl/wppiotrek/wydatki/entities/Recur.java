package pl.wppiotrek.wydatki.entities;

import java.util.Date;

public class Recur {
	private Date dateFrom;
	private Date dateTo;
	private String budgetName;
	private Double amount;
	private Double spend;
	private Double left;
	/**
	 * @return the dateFrom
	 */
	public Date getDateFrom() {
		return dateFrom;
	}
	/**
	 * @param dateFrom the dateFrom to set
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	/**
	 * @return the dateTo
	 */
	public Date getDateTo() {
		return dateTo;
	}
	/**
	 * @param dateTo the dateTo to set
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	/**
	 * @return the budgetName
	 */
	public String getBudgetName() {
		return budgetName;
	}
	/**
	 * @param budgetName the budgetName to set
	 */
	public void setBudgetName(String budgetName) {
		this.budgetName = budgetName;
	}
	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	/**
	 * @return the spend
	 */
	public Double getSpend() {
		return spend;
	}
	/**
	 * @param spend the spend to set
	 */
	public void setSpend(Double spend) {
		this.spend = spend;
	}
	/**
	 * @return the left
	 */
	public Double getLeft() {
		return left;
	}
	/**
	 * @param left the left to set
	 */
	public void setLeft(Double left) {
		this.left = left;
	}

}
