package pl.wppiotrek.wydatki.support;

import java.util.ArrayList;
import java.util.Collection;

import pl.wppiotrek.wydatki.entities.Account;
import pl.wppiotrek.wydatki.entities.Category;
import pl.wppiotrek.wydatki.entities.Parameter;
import pl.wppiotrek.wydatki.entities.Project;

public class ListSupport {

	public static Account getAccountById(int Id, ArrayList<Account> items) {

		for (Account account : items) {
			if (account.getId() == Id)
				return account;
		}
		return null;

	}

	public static boolean isAccountNameUsed(String name) {
		AndroidGlobals globals = AndroidGlobals.getInstance();
		Collection<Account> items = globals.getAccountsDictionary().values();
		for (Account account : items) {
			if (account.getName().equals(name))
				return true;
		}

		return false;

	}

	public static Category getCategoryById(int Id, ArrayList<Category> items) {
		if (items != null)
			for (Category category : items) {
				if (category.getId() == Id)
					return category;
			}
		return null;

	}

	public static Parameter getParameterById(int Id, ArrayList<Parameter> items) {
		if (items != null)
			for (Parameter parameter : items) {
				if (parameter.getId() == Id)
					return parameter;
			}
		return null;

	}

	public static Parameter getParameterNameById(int Id,
			ArrayList<Parameter> items) {
		if (items != null)
			for (Parameter parameter : items) {
				if (parameter.getId() == Id)
					return parameter;
			}
		return null;

	}

	public static boolean isProjectNameUsed(String name) {
		AndroidGlobals globals = AndroidGlobals.getInstance();
		Collection<Project> items = globals.getProjectsDictionary().values();
		for (Project project : items) {
			if (project.getName().equals(name))
				return true;
		}
		return false;
	}

	public static boolean isParameterNameUsed(String name) {
		AndroidGlobals globals = AndroidGlobals.getInstance();
		Collection<Parameter> items = globals.getParametersDictionary()
				.values();
		for (Parameter parameter : items) {
			if (parameter.getName().equals(name))
				return true;
		}
		return false;
	}

}
