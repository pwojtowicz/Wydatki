package pl.wppiotrek.wydatki.managers;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.asynctasks.ParametersAsyncTask;
import pl.wppiotrek.wydatki.entities.Parameter;
import pl.wppiotrek.wydatki.interfaces.IOnObjectsReceivedListener;
import pl.wppiotrek.wydatki.units.AsyncTaskMethod;

public class ParameterManager extends Manager<Object> {

	private ArrayList<ParametersAsyncTask> tasks = new ArrayList<ParametersAsyncTask>();

	public ParameterManager(IOnObjectsReceivedListener listener) {
		super.setOnObjectsReceivedListener(listener);
	}

	public void getAllParameters() {
		ParametersAsyncTask asyncTask = new ParametersAsyncTask(
				AsyncTaskMethod.GetAllObjects, this);
		tasks.add(asyncTask);
		asyncTask.execute((Void) null);
	}

	public void cancelAllTask() {
		for (ParametersAsyncTask item : tasks) {
			item.cancel(true);
		}
	}

	public void createNewParameter(Parameter currentParameter) {
		ParametersAsyncTask asyncTask = new ParametersAsyncTask(
				AsyncTaskMethod.PostNewObject, this, currentParameter);
		tasks.add(asyncTask);
		asyncTask.execute((Void) null);
	}

	public void editParameter(Parameter currentParameter) {
		ParametersAsyncTask asyncTask = new ParametersAsyncTask(
				AsyncTaskMethod.EditExistObjects, this, currentParameter);
		tasks.add(asyncTask);
		asyncTask.execute((Void) null);
	}
}