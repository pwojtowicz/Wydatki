package pl.wppiotrek.wydatki.providers;

import pl.wppiotrek.wydatki.entities.Category;
import pl.wppiotrek.wydatki.errors.CommunicationException;
import pl.wppiotrek.wydatki.interfaces.IHttpRequestToAsyncTaskCommunication;

public class CategoriesProvider extends AbstractProvider<Category> {

	@Override
	public Category[] getAllObjects(
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {
		Provider<Category[]> provider = new Provider<Category[]>(
				Category[].class);
		Category[] objects = provider.getObjects(server + "/categories",
				listener);
		return objects;
	}

	@Override
	public Category postObject(Category object,
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {
		String url = server + "/categories/category";

		Provider<Category> provider = new Provider<Category>(Category.class);
		return provider.sendObject(url, object, true, listener);
	}

	@Override
	public void deleteObject(Category object,
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {
		throw new ExceptionInInitializerError();
	}
}
