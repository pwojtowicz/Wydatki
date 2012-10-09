package pl.wppiotrek.wydatki.managers;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.asynctasks.ShopItemAsyncTask;
import pl.wppiotrek.wydatki.entities.ShopItem;
import pl.wppiotrek.wydatki.interfaces.IOnObjectsReceivedListener;
import pl.wppiotrek.wydatki.units.AsyncTaskMethod;
import android.content.Context;

public class ShopItemManager extends Manager<Object> {

	private ArrayList<ShopItemAsyncTask> tasks = new ArrayList<ShopItemAsyncTask>();

	public ShopItemManager(IOnObjectsReceivedListener listener) {
		super.setOnObjectsReceivedListener(listener);
	}

	public void getAllItems(Context context) {
		ShopItemAsyncTask asyncTask = new ShopItemAsyncTask(context, this,
				AsyncTaskMethod.GetAllObjects);
		tasks.add(asyncTask);
		asyncTask.execute((Void) null);
	}

	public void createNewItem(Context context, ShopItem shopItem) {
		ShopItemAsyncTask asyncTask = new ShopItemAsyncTask(context, this,
				AsyncTaskMethod.PostNewObject, shopItem);
		tasks.add(asyncTask);
		asyncTask.execute((Void) null);
	}

	public void editItem(Context context, ShopItem shopItem) {
		ShopItemAsyncTask asyncTask = new ShopItemAsyncTask(context, this,
				AsyncTaskMethod.EditExistObjects, shopItem);
		tasks.add(asyncTask);
		asyncTask.execute((Void) null);
	}

	public void cancelAllTask() {
		for (ShopItemAsyncTask item : tasks) {
			item.cancel(true);
		}
	}
}
