package pl.wppiotrek.wydatki.asynctasks;

import pl.wppiotrek.wydatki.entities.Category;
import pl.wppiotrek.wydatki.entities.OutputBundle;
import pl.wppiotrek.wydatki.errors.CommunicationException;
import pl.wppiotrek.wydatki.interfaces.IOnHTTPRequestJSONParsedResponse;
import pl.wppiotrek.wydatki.providers.CategoriesProvider;
import pl.wppiotrek.wydatki.units.AsyncTaskMethod;

public class CategoryAsyncTask extends AbstractAsyncTask {

	private AsyncTaskMethod method;
	private Category category;

	public CategoryAsyncTask(
			IOnHTTPRequestJSONParsedResponse<Object, CommunicationException> listener,
			AsyncTaskMethod method, Category category) {
		this.listener = listener;
		this.method = method;
		this.category = category;
	}

	@Override
	protected StatusList doInBackground(Void... params) {
		CategoriesProvider provider = new CategoriesProvider();
		try {
			if (method == AsyncTaskMethod.GetAllObjects) {
				Category[] itemsContainer = provider.getAllObjects(this);
				bundle = new OutputBundle<Object, CommunicationException>();
				bundle.setObject(itemsContainer);
			} else if (method == AsyncTaskMethod.DeleteExistObject) {
				provider.deleteObject(category, this);
				bundle = new OutputBundle<Object, CommunicationException>();
				bundle.setObject("OK");
			} else if (method == AsyncTaskMethod.PostNewObject
					|| method == AsyncTaskMethod.EditExistObjects) {
				Category itemsContainer = provider.postObject(category, this);
				bundle = new OutputBundle<Object, CommunicationException>();
				bundle.setObject(itemsContainer);
			}

		} catch (CommunicationException e) {
			bundle = new OutputBundle<Object, CommunicationException>();
			bundle.setException(e);
			e.printStackTrace();
		}
		return null;
	}

}
