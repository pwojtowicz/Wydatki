package pl.wppiotrek.wydatki.providers;

import pl.wppiotrek.wydatki.entities.Account;
import pl.wppiotrek.wydatki.errors.CommunicationException;
import pl.wppiotrek.wydatki.interfaces.IHttpRequestToAsyncTaskCommunication;

public class AccountProvider extends AbstractProvider<Account> {

	@Override
	public Account[] getAllObjects(IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {
		String url = server + "/accounts";

		Provider<Account[]> provider = new Provider<Account[]>(Account[].class);
		Account[] objects = provider.getObjects(url, listener);
		return objects;
	}

	@Override
	public Account postObject(Account object,
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {
		String url = server + "/accounts/account";

		Provider<Account> provider = new Provider<Account>(Account.class);
		return provider.sendObject(url, object, true, listener);
	}

	@Override
	public void deleteObject(Account object,
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {
		String url = server + "/accounts/account/" + object.getId();

		Provider<Account> provider = new Provider<Account>(Account.class);
		provider.deleteObject(url, listener);

	}
}
