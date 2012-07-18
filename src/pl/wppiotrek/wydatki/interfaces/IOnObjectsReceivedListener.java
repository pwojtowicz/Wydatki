package pl.wppiotrek.wydatki.interfaces;

public interface IOnObjectsReceivedListener {

	void onObjectsRequestStarted();

	void onObjectsRequestEnded();

	void onObjectsNotReceived(Object exception, Object object);

	void onObjectsReceived(Object object, Object object2);

	void onObjectsProgressUpdate(int progress);

}
