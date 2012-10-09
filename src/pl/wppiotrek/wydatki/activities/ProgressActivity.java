package pl.wppiotrek.wydatki.activities;

import java.util.ArrayList;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.errors.CommunicationException;
import pl.wppiotrek.wydatki.errors.ExceptionErrorCodes;
import pl.wppiotrek.wydatki.interfaces.IOnObjectsReceivedListener;
import pl.wppiotrek.wydatki.support.DialogFactory;
import pl.wppiotrek.wydatki.units.DialogType;
import pl.wppiotrek.wydatki.units.ResultCodes;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.view.MenuItem;

public abstract class ProgressActivity extends Activity implements
		IOnObjectsReceivedListener {

	AlertDialog pbarDialog = null;
	private boolean isProgressDialogCancelable = false;
	private boolean isSendProgressBar = false;
	private boolean showProgressBar = false;
	private Vibrator vibrator;

	public void setProgressDialogCancelable(boolean isCancelable) {
		isProgressDialogCancelable = isCancelable;
	}

	public void setShowProgressBar(boolean showProgressBar) {
		this.showProgressBar = showProgressBar;
	}

	public void setIsSendProgressBar(boolean isCancelable) {
		isSendProgressBar = isCancelable;
	}

	public void setActionBarBackButton() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
	}

	public void showProgressDialog(boolean hasProgress) {
		Bundle bundle = new Bundle();
		bundle.putBoolean("isCancelable", isProgressDialogCancelable);

		if (pbarDialog == null) {

			pbarDialog = DialogFactory.create(
					!isSendProgressBar ? DialogType.WaitingDialog
							: DialogType.SendInformation, bundle, this, null);
			pbarDialog.show();
		}
	}

	public void hideProgressDialog() {
		if (pbarDialog != null) {
			pbarDialog.dismiss();
			pbarDialog = null;
		}

	}

	public abstract void refreshActivity(boolean isForceRefresh);

	public abstract void leaveActivity(int requestCode);

	public abstract void linkViews();

	public abstract void configureViews();

	public void updateProgressDialog(int value) {

	}

	public void onObjectsRequestStarted() {
		if (showProgressBar)
			showProgressDialog(false);
	}

	public void onObjectsRequestEnded() {
		hideProgressDialog();
	}

	public void onObjectsNotReceived(Object exception, Object object) {
		if (exception != null) {
			CommunicationException ex = (CommunicationException) exception;

			if (ex.getErrorCode() == ExceptionErrorCodes.SendInformationOffline) {

				DialogInterface.OnClickListener buttonClick = new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						leaveActivity(ResultCodes.RESULT_NO_ACTION);
					}
				};

				AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				dialog.setTitle(R.string.dialog_title_error);
				dialog.setPositiveButton(R.string.dialog_ok, buttonClick);
				dialog.setMessage(R.string.dialog_message_offline);
				dialog.setCancelable(false);

				dialog.create().show();

			} else {

				DialogInterface.OnClickListener positiveClick = new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						refreshActivity(true);
					}
				};
				DialogInterface.OnClickListener negativeClick = new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						leaveActivity(ResultCodes.RESULT_NO_ACTION);
					}
				};

				AlertDialog dialog = DialogFactory.createTryAgainDialog(this,
						positiveClick, negativeClick);
				dialog.show();
			}
		}

	}

	public void onObjectsReceived(Object object, Object object2) {

	}

	public void onObjectsProgressUpdate(int progress) {
		// updateProgressDialog(progress);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case android.R.id.home:
			leaveActivity(ResultCodes.RESULT_NO_ACTION);
			break;
		}
		return true;
	}

	public void vibrate() {
		if (vibrator != null) {
			vibrator.vibrate(20);
		}
	}

	public void startVoiceRecognitionActivity() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
				"Voice recognition Demo...");
		startActivityForResult(intent, ResultCodes.START_ACTIVITY_VOICE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ResultCodes.START_ACTIVITY_VOICE
				&& resultCode == RESULT_OK) {

			ArrayList<String> matches = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

			onRecognitionRequest(matches.get(0));

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public abstract void onRecognitionRequest(String value);
}
