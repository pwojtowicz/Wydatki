package pl.wppiotrek.wydatki.managers;

import java.util.ArrayList;
import java.util.Hashtable;

import pl.wppiotrek.wydatki.entities.Account;
import pl.wppiotrek.wydatki.entities.Category;
import pl.wppiotrek.wydatki.entities.Parameter;
import pl.wppiotrek.wydatki.entities.Project;
import pl.wppiotrek.wydatki.interfaces.IOnObjectsReceivedListener;
import pl.wppiotrek.wydatki.support.AndroidGlobals;
import pl.wppiotrek.wydatki.support.DialogFactory;
import pl.wppiotrek.wydatki.units.DialogType;
import pl.wppiotrek.wydatki.units.RefreshOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

public class DownloadDataManager implements IOnObjectsReceivedListener {

	AlertDialog pbarDialog = null;

	boolean categoriesIsDownloaded = false;
	boolean accountsIsDownloaded = false;
	boolean parametersIsDownloaded = false;
	boolean projectsIsDownloaded = false;

	private AccountManager accountManager;
	private CategoriesManager categoryManager;
	private ParameterManager parameterManager;
	private ProjectManager projectManager;
	private IOnObjectsReceivedListener listener;
	private AndroidGlobals globals;

	private Context context;

	public DownloadDataManager(IOnObjectsReceivedListener listener,
			int refreshOpctions, Context context) {
		this.listener = listener;
		this.context = context;
		globals = AndroidGlobals.getInstance();
		refresh(false, refreshOpctions);
	}

	public void showProgressDialog(boolean hasProgress) {
		Bundle bundle = new Bundle();
		bundle.putBoolean("isCancelable", false);

		if (pbarDialog == null) {

			pbarDialog = DialogFactory.create(DialogType.WaitingDialog, bundle,
					context, null);
			pbarDialog.show();
		}
	}

	public void hideProgressDialog() {
		if (pbarDialog != null) {
			pbarDialog.dismiss();
			pbarDialog = null;
		}

	}

	public void updateProgressDialog(int value) {

	}

	public void refreshAll(boolean isForceRefresh) {
		System.out.println("DownloadDataManager: refresh");

		if (isForceRefresh) {
			globals.setAccountsDictionary(null);
			globals.setCategoryList(null);
			globals.setParametersDictionary(null);
			globals.setProjectsDictionary(null);
		}
		categoriesIsDownloaded = false;
		accountsIsDownloaded = false;
		parametersIsDownloaded = false;
		projectsIsDownloaded = false;
		reloadData();
	}

	public void cancleDownload() {
		if (accountManager != null)
			accountManager.cancelAllTask();
		if (categoryManager != null)
			categoryManager.cancelAllTask();
		if (parameterManager != null)
			parameterManager.cancelAllTask();
		if (projectManager != null)
			projectManager.cancelAllTask();
	}

	private void reloadData() {
		if (!accountsIsDownloaded)
			getAccounts(globals);
		if (accountsIsDownloaded && !parametersIsDownloaded)
			getParameters(globals);
		if (accountsIsDownloaded && parametersIsDownloaded
				&& !categoriesIsDownloaded)
			getCategories(globals);

		if (accountsIsDownloaded && categoriesIsDownloaded
				&& parametersIsDownloaded && !projectsIsDownloaded)
			getProjects(globals);
	}

	private void getProjects(AndroidGlobals globals) {
		if (globals.getProjectsDictionary() == null) {
			projectManager = new ProjectManager(this);
			projectManager.setOnObjectsReceivedListener(listener);
			projectManager.getAllProjects();
		} else
			projectsIsDownloaded = true;
	}

	public void getAccounts(AndroidGlobals globals) {
		if (globals.getAccountsDictionary() == null) {
			accountManager = new AccountManager(this);
			accountManager.setOnObjectsReceivedListener(listener);
			accountManager.getAllAccounts();
		} else
			accountsIsDownloaded = true;

	}

	public void getCategories(AndroidGlobals globals) {
		if (globals.getCategoryList() == null) {
			categoryManager = new CategoriesManager(this);
			categoryManager.setOnObjectsReceivedListener(listener);
			categoryManager.getAllCategories();
		} else
			categoriesIsDownloaded = true;
	}

	public void getParameters(AndroidGlobals globals) {
		if (globals.getParametersDictionary() == null) {
			parameterManager = new ParameterManager(this);
			parameterManager.setOnObjectsReceivedListener(listener);
			parameterManager.getAllParameters();
		} else
			parametersIsDownloaded = true;
	}

	public void onObjectsRequestStarted() {
		showProgressDialog(false);
	}

	public void onObjectsRequestEnded() {
		hideProgressDialog();
	}

	public void onObjectsNotReceived(Object exception, Object object) {
	}

	public void onObjectsReceived(Object object, Object object2) {
		if (object instanceof Account[]) {
			System.out.println("DownloadDataManager:onObjectsReceived Account");
			accountsIsDownloaded = true;
			Hashtable<Integer, Account> elements = new Hashtable<Integer, Account>();
			Account[] items = (Account[]) object;
			for (Account item : items) {
				elements.put(item.getId(), item);
			}
			globals.setAccountsDictionary(elements);
		}
		if (object instanceof Category[]) {
			System.out
					.println("DownloadDataManager:onObjectsReceived Category");
			categoriesIsDownloaded = true;
			ArrayList<Category> elements = new ArrayList<Category>();
			Category[] items = (Category[]) object;

			for (Category item : items) {
				item.setDescription();
				elements.add(item);
			}
			globals.setCategoryList(elements);
		}
		if (object instanceof Parameter[]) {
			System.out
					.println("DownloadDataManager:onObjectsReceived Parameter");
			parametersIsDownloaded = true;
			Hashtable<Integer, Parameter> elements = new Hashtable<Integer, Parameter>();
			Parameter[] items = (Parameter[]) object;
			for (Parameter item : items) {
				elements.put(item.getId(), item);
			}

			globals.setParametersDictionary(elements);
		}
		if (object instanceof Project[]) {
			System.out.println("DownloadDataManager:onObjectsReceived Project");
			projectsIsDownloaded = true;
			Hashtable<Integer, Project> elements = new Hashtable<Integer, Project>();
			Project[] items = (Project[]) object;
			for (Project item : items) {
				elements.put(item.getId(), item);
			}
			globals.setProjectsDictionary(elements);
		}
		reloadData();

	}

	public void onObjectsProgressUpdate(int progress) {
		updateProgressDialog(progress);
	}

	public void refresh(boolean isForceRefresh, int refreshOpctions) {
		categoriesIsDownloaded = true;
		accountsIsDownloaded = true;
		parametersIsDownloaded = true;
		projectsIsDownloaded = true;

		int i = 8;
		while (i > 0) {

			if (refreshOpctions / i == 1
					|| (refreshOpctions >= i && refreshOpctions % i > 0)) {
				setRefreshOptions(isForceRefresh, i);
				refreshOpctions -= i;
			}

			i = i / 2;
		}

		reloadData();

	}

	private void setRefreshOptions(boolean isForceRefresh, int refreshOpctions) {
		switch (refreshOpctions) {
		case RefreshOptions.refreshAccounts:
			if (isForceRefresh)
				globals.setAccountsDictionary(null);
			accountsIsDownloaded = false;
			break;
		case RefreshOptions.refreshCategories:
			if (isForceRefresh)
				globals.setCategoryList(null);
			categoriesIsDownloaded = false;
			break;
		case RefreshOptions.refreshParameters:
			if (isForceRefresh)
				globals.setParametersDictionary(null);
			parametersIsDownloaded = false;

			break;
		case RefreshOptions.refreshProjects:
			if (isForceRefresh)
				globals.setProjectsDictionary(null);
			projectsIsDownloaded = false;
			break;

		default:
			break;
		}
	}
}
