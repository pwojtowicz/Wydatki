package pl.wppiotrek.wydatki.interfaces;

public interface IHttpRequestToAsyncTaskCommunication {

	void onObjectsProgressUpdate(int progressPercent);

	boolean checkIsTaskCancled();

}
