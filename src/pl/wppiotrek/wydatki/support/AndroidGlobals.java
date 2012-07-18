package pl.wppiotrek.wydatki.support;

import java.util.ArrayList;
import java.util.Hashtable;

import pl.wppiotrek.wydatki.entities.Account;
import pl.wppiotrek.wydatki.entities.Category;
import pl.wppiotrek.wydatki.entities.Parameter;
import pl.wppiotrek.wydatki.entities.Project;
import pl.wppiotrek.wydatki.entities.Transaction;
import pl.wppiotrek.wydatki.managers.DataBaseManager;

public class AndroidGlobals {

	private String userLogin = "wppiotrek85";
	private String userPassword = "123qwe";
	private String serverAddress = "";

	private ArrayList<Category> categoryList;

	private ArrayList<Transaction> transactionList;

	private Hashtable<Integer, Account> accountsDictionary;
	private Hashtable<Integer, Parameter> parametersDictionary;
	private Hashtable<Integer, Project> projectsDictionary;

	private Account currentSelectedAccount;
	private Parameter currentSelectedParameter;
	private Category currentSelectedCategory;
	private Project currentSelectedProject;

	private boolean refreshActivity;
	private DataBaseManager cacheManager;
	private Transaction currentSelectedTransaction;

	private static volatile AndroidGlobals instance = null;

	public static AndroidGlobals getInstance() {
		if (instance == null) {
			synchronized (AndroidGlobals.class) {
				if (instance == null) {
					instance = new AndroidGlobals();
				}
			}
		}
		return instance;
	}

	private AndroidGlobals() {
	}

	public void setCategoryList(ArrayList<Category> elements) {
		categoryList = elements;
	}

	public void setTransactionList(ArrayList<Transaction> elements) {
		transactionList = elements;
	}

	public ArrayList<Category> getCategoryList() {
		return categoryList;
	}

	public ArrayList<Transaction> getTransactionList() {
		return transactionList;
	}

	public void setCurrentSelectedAccount(Account account) {
		this.currentSelectedAccount = account;

	}

	public Account getCurrentSelectedAccount() {
		return this.currentSelectedAccount;

	}

	/**
	 * @return the userLogin
	 */
	public String getUserLogin() {
		return userLogin;
	}

	/**
	 * @param userLogin
	 *            the userLogin to set
	 */
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	/**
	 * @return the userPassword
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * @param userPassword
	 *            the userPassword to set
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * @return the serverAddress
	 */
	public String getServerAddress() {
		return serverAddress;
	}

	/**
	 * @param serverAddress
	 *            the serverAddress to set
	 */
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	/**
	 * @return the currentSelectedParameter
	 */
	public Parameter getCurrentSelectedParameter() {
		return currentSelectedParameter;
	}

	/**
	 * @param currentSelectedParameter
	 *            the currentSelectedParameter to set
	 */
	public void setCurrentSelectedParameter(Parameter currentSelectedParameter) {
		this.currentSelectedParameter = currentSelectedParameter;
	}

	/**
	 * @return the currentSelectedCategory
	 */
	public Category getCurrentSelectedCategory() {
		return currentSelectedCategory;
	}

	/**
	 * @param currentSelectedCategory
	 *            the currentSelectedCategory to set
	 */
	public void setCurrentSelectedCategory(Category currentSelectedCategory) {
		this.currentSelectedCategory = currentSelectedCategory;
	}

	public Transaction getCurrentSelectedTransaction() {
		return currentSelectedTransaction;
	}

	public void setCurrentSelectedTransaction(
			Transaction currentSelectedTransaction) {
		this.currentSelectedTransaction = currentSelectedTransaction;

	}

	public void updateParametersList(Parameter parameter) {
		if (parametersDictionary.containsKey(parameter.getId()))
			parametersDictionary.remove(parameter.getId());
		parametersDictionary.put(parameter.getId(), parameter);
	}

	public void updateCategoriesList(Category category) {
		for (Category item : categoryList) {
			if (item.getId() == category.getId()) {
				item.setName(category.getName());
				item.setIsActive(category.isActive());
				item.setAttributes(category.getAttributes());
				item.setParentId(category.getParentId());
				break;
			}
		}
	}

	public void updateProjectsList(Project project) {
		if (projectsDictionary.containsKey(project.getId()))
			projectsDictionary.remove(project.getId());
		projectsDictionary.put(project.getId(), project);

	}

	public Project getProjectById(int projectId) {
		return projectsDictionary.get(projectId);

	}

	public void updateAccountsList(Account account) {
		if (accountsDictionary.containsKey(account.getId()))
			accountsDictionary.remove(account.getId());
		accountsDictionary.put(account.getId(), account);

	}

	public Account getAccountById(int accountId) {
		return accountsDictionary.get(accountId);

	}

	public Parameter getParameterById(int parameterId) {
		return parametersDictionary.get(parameterId);

	}

	public DataBaseManager getCacheManager() {
		return cacheManager;
	}

	/**
	 * @param cacheManager
	 *            the cacheManager to set
	 */
	public void setCacheManager(DataBaseManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public Project getCurrentSelectedProject() {
		return currentSelectedProject;
	}

	public void setCurrentSelectedProject(Project currentSelectedProject) {
		this.currentSelectedProject = currentSelectedProject;
	}

	/**
	 * @return the refreshActivity
	 */
	public boolean isRefreshActivity() {
		return refreshActivity;
	}

	/**
	 * @param refreshActivity
	 *            the refreshActivity to set
	 */
	public void setRefreshActivity(boolean refreshActivity) {
		this.refreshActivity = refreshActivity;
	}

	/**
	 * @return the parametersDictionary
	 */
	public Hashtable<Integer, Parameter> getParametersDictionary() {
		return parametersDictionary;
	}

	/**
	 * @param parametersDictionary
	 *            the parametersDictionary to set
	 */
	public void setParametersDictionary(
			Hashtable<Integer, Parameter> parametersDictionary) {
		this.parametersDictionary = parametersDictionary;
	}

	/**
	 * @return the projectsDictionary
	 */
	public Hashtable<Integer, Project> getProjectsDictionary() {
		return projectsDictionary;
	}

	/**
	 * @param projectsDictionary
	 *            the projectsDictionary to set
	 */
	public void setProjectsDictionary(
			Hashtable<Integer, Project> projectsDictionary) {
		this.projectsDictionary = projectsDictionary;
	}

	/**
	 * @return the accountsDictionary
	 */
	public Hashtable<Integer, Account> getAccountsDictionary() {
		return accountsDictionary;
	}

	/**
	 * @param accountsDictionary
	 *            the accountsDictionary to set
	 */
	public void setAccountsDictionary(
			Hashtable<Integer, Account> accountsDictionary) {
		this.accountsDictionary = accountsDictionary;
	}

}
