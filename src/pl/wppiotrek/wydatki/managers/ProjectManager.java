package pl.wppiotrek.wydatki.managers;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.asynctasks.ProjectAsyncTask;
import pl.wppiotrek.wydatki.entities.Project;
import pl.wppiotrek.wydatki.interfaces.IOnObjectsReceivedListener;
import pl.wppiotrek.wydatki.units.AsyncTaskMethod;

public class ProjectManager extends Manager<Object> {

	private ArrayList<ProjectAsyncTask> tasks = new ArrayList<ProjectAsyncTask>();

	public ProjectManager(IOnObjectsReceivedListener listener) {
		super.setOnObjectsReceivedListener(listener);
	}

	public void getAllProjects() {
		ProjectAsyncTask asyncTask = new ProjectAsyncTask(this,
				AsyncTaskMethod.GetAllObjects, null);
		tasks.add(asyncTask);
		asyncTask.execute((Void) null);
	}

	public void createNewProject(Project project) {
		ProjectAsyncTask asyncTask = new ProjectAsyncTask(this,
				AsyncTaskMethod.PostNewObject, project);
		tasks.add(asyncTask);
		asyncTask.execute((Void) null);
	}

	public void editProject(Project project) {
		ProjectAsyncTask asyncTask = new ProjectAsyncTask(this,
				AsyncTaskMethod.EditExistObjects, project);
		tasks.add(asyncTask);
		asyncTask.execute((Void) null);
	}

	public void deleteProject(Project project) {
		ProjectAsyncTask asyncTask = new ProjectAsyncTask(this,
				AsyncTaskMethod.DeleteExistObject, project);
		tasks.add(asyncTask);
		asyncTask.execute((Void) null);
	}

	public void cancelAllTask() {
		for (ProjectAsyncTask item : tasks) {
			item.cancel(true);
		}
	}

}
