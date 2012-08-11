package pl.wppiotrek.wydatki.managers;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.asynctasks.StartAsyncTask;
import pl.wppiotrek.wydatki.interfaces.IOnObjectsReceivedListener;

public class StartManager extends Manager<Object> {
	private ArrayList<StartAsyncTask> tasks = new ArrayList<StartAsyncTask>();

	public StartManager(IOnObjectsReceivedListener listener) {
		super.setOnObjectsReceivedListener(listener);
	}

	public void getAll() {
		StartAsyncTask asyncTask = new StartAsyncTask(this);
		tasks.add(asyncTask);
		asyncTask.execute((Void) null);
	}

	public void cancelAllTask() {
		for (StartAsyncTask item : tasks) {
			item.cancel(true);
		}
	}
}
