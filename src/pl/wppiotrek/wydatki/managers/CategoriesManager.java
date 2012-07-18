package pl.wppiotrek.wydatki.managers;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.asynctasks.CategoryAsyncTask;
import pl.wppiotrek.wydatki.entities.Category;
import pl.wppiotrek.wydatki.interfaces.IOnObjectsReceivedListener;
import pl.wppiotrek.wydatki.units.AsyncTaskMethod;

public class CategoriesManager extends Manager<Object> {

	private ArrayList<CategoryAsyncTask> tasks = new ArrayList<CategoryAsyncTask>();

	public CategoriesManager(IOnObjectsReceivedListener listener) {
		super.setOnObjectsReceivedListener(listener);
	}

	public void getAllCategories() {
		CategoryAsyncTask asyncTask = new CategoryAsyncTask(this,
				AsyncTaskMethod.GetAllObjects, null);
		tasks.add(asyncTask);
		asyncTask.execute((Void) null);
	}

	public void cancelAllTask() {
		for (CategoryAsyncTask item : tasks) {
			item.cancel(true);
		}
	}

	public void createNewCategory(Category category) {
		CategoryAsyncTask asyncTask = new CategoryAsyncTask(this,
				AsyncTaskMethod.PostNewObject, category);
		tasks.add(asyncTask);
		asyncTask.execute((Void) null);
	}

	public void editCategory(Category category) {
		CategoryAsyncTask asyncTask = new CategoryAsyncTask(this,
				AsyncTaskMethod.EditExistObjects, category);
		tasks.add(asyncTask);
		asyncTask.execute((Void) null);
	}

	public void deleteCategory(Category category) {
		CategoryAsyncTask asyncTask = new CategoryAsyncTask(this,
				AsyncTaskMethod.DeleteExistObject, category);
		tasks.add(asyncTask);
		asyncTask.execute((Void) null);
	}
}
