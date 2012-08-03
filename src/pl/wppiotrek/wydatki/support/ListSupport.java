package pl.wppiotrek.wydatki.support;

import java.util.ArrayList;
import java.util.Collection;

import pl.wppiotrek.wydatki.entities.Account;
import pl.wppiotrek.wydatki.entities.ModelBase;
import pl.wppiotrek.wydatki.entities.Parameter;
import pl.wppiotrek.wydatki.entities.Project;

public class ListSupport {

	public static ArrayList<Integer> StringToArrayList(String items) {
		ArrayList<Integer> elements = new ArrayList<Integer>();
		String[] tmp = items.split(";");
		// if (tmp.length > 0)
		for (String string : tmp) {
			if (string.length() > 0)
				elements.add(Integer.parseInt(string));
		}
		return elements;
	}

	public static String ArrayToString(ModelBase[] items) {
		StringBuilder sb = new StringBuilder();
		for (ModelBase item : items) {
			sb.append(item.getId() + ";");
		}

		return sb.toString();
	}

	public static String ArrayListModelBaseToString(ArrayList<ModelBase> items) {
		StringBuilder sb = new StringBuilder();
		for (ModelBase item : items) {
			sb.append(item.getId() + ";");
		}

		return sb.toString();
	}

	public static String ArrayListIntegerToString(ArrayList<Integer> items) {
		StringBuilder sb = new StringBuilder();
		for (Integer item : items) {
			sb.append(item.toString() + ";");
		}

		return sb.toString();
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
