package pl.wppiotrek.wydatki.providers;

import pl.wppiotrek.wydatki.entities.Parameter;
import pl.wppiotrek.wydatki.errors.CommunicationException;
import pl.wppiotrek.wydatki.support.AndroidGlobals;

public class AttributesProvider {
	String server = AndroidGlobals.getInstance().getServerAddress();

	public void getAllParameters() throws CommunicationException {

		Provider<Parameter[]> provider = new Provider<Parameter[]>(
				Parameter[].class);
		provider.getObjects(server + "/attributes", null);
	}
}
