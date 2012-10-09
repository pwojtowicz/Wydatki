package pl.wppiotrek.wydatki.asynctasks;

import pl.wppiotrek.wydatki.entities.OutputBundle;
import pl.wppiotrek.wydatki.entities.ShopItem;
import pl.wppiotrek.wydatki.errors.CommunicationException;
import pl.wppiotrek.wydatki.errors.ExceptionErrorCodes;
import pl.wppiotrek.wydatki.interfaces.IOnHTTPRequestJSONParsedResponse;
import pl.wppiotrek.wydatki.repositories.ShopListRepository;
import pl.wppiotrek.wydatki.units.AsyncTaskMethod;
import android.content.Context;

public class ShopItemAsyncTask extends AbstractAsyncTask {

	private AsyncTaskMethod method;
	private ShopItem shopitem;
	private Context context;

	public ShopItemAsyncTask(
			Context context,
			IOnHTTPRequestJSONParsedResponse<Object, CommunicationException> listener,
			AsyncTaskMethod method) {
		this.listener = listener;
		this.method = method;
		this.context = context;
	}

	public ShopItemAsyncTask(
			Context context,
			IOnHTTPRequestJSONParsedResponse<Object, CommunicationException> listener,
			AsyncTaskMethod method, ShopItem shopitem) {
		this.listener = listener;
		this.method = method;
		this.shopitem = shopitem;
		this.context = context;
	}

	@Override
	protected StatusList doInBackground(Void... params) {
		// AccountProvider provider = new AccountProvider();
		ShopListRepository provider = new ShopListRepository(context);
		try {
			if (method == AsyncTaskMethod.GetAllObjects) {
				Object[] itemsContainer = provider.readAll();
				bundle = new OutputBundle<Object, CommunicationException>();
				bundle.setObject(itemsContainer);
			} else if (method == AsyncTaskMethod.PostNewObject
					|| method == AsyncTaskMethod.EditExistObjects) {
				ShopItem itemsContainer = (ShopItem) provider.create(shopitem);
				bundle = new OutputBundle<Object, CommunicationException>();
				bundle.setObject(itemsContainer);
			}
		} catch (Exception e) {
			bundle = new OutputBundle<Object, CommunicationException>();
			bundle.setException(new CommunicationException("error",
					ExceptionErrorCodes.CommunicationProblems));
			e.printStackTrace();
		}
		return null;
	}
}
