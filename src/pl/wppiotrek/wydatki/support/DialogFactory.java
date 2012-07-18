package pl.wppiotrek.wydatki.support;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.units.DialogType;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

public class DialogFactory {

	public static AlertDialog create(DialogType type, Bundle bundle,
			Context context, OnClickListener clickListener) {

		switch (type) {
		case ErrorDialog:
			AlertDialog.Builder errorBuilder = new AlertDialog.Builder(context);
			errorBuilder.setTitle(context.getText(R.string.dialog_title_error));
			errorBuilder.setNegativeButton(context.getText(R.string.dialog_ok),
					new OnClickListener() {

						public void onClick(DialogInterface dialog, int arg1) {
							dialog.dismiss();
						}
					});
			return errorBuilder.create();

		case OptionsDialog:
			CharSequence[] items = null;
			if (bundle != null) {
				items = bundle.getCharSequenceArray("options");
			}

			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle(context.getText(R.string.options) + ":");
			builder.setItems(items, clickListener);
			return builder.create();
		case SendInformation:
		case WaitingDialog:
			ProgressDialog waitingDialog = new ProgressDialog(context);
			boolean iswaitingDialogCancelable = false;
			if (bundle != null) {
				iswaitingDialogCancelable = bundle.getBoolean("isCancelable");
			}
			if (type == DialogType.WaitingDialog)
				waitingDialog.setMessage(context
						.getText(R.string.getting_more_data));
			else if (type == DialogType.SendInformation)
				waitingDialog.setMessage(context
						.getText(R.string.sending_information));

			waitingDialog.setCancelable(iswaitingDialogCancelable);
			if (iswaitingDialogCancelable)
				waitingDialog.setOnCancelListener(new OnCancelListener() {

					public void onCancel(DialogInterface dialog) {
						// leaveActivity();
					}
				});
			return waitingDialog;
		case ProgressDialog:
			ProgressDialog progressDialog = new ProgressDialog(context);
			boolean isprogressDialogCancelable = false;
			if (bundle != null) {
				isprogressDialogCancelable = bundle.getBoolean("isCancelable");
			}

			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setMax(100);
			progressDialog.setProgress(0);

			progressDialog.setCancelable(isprogressDialogCancelable);
			if (isprogressDialogCancelable)
				progressDialog.setOnCancelListener(new OnCancelListener() {

					public void onCancel(DialogInterface dialog) {
						// leaveActivity();
					}
				});
			return progressDialog;

		case TryAgainDialog:

		default:
			break;
		}

		return null;
	}

	public static AlertDialog createTryAgainDialog(Context context,
			OnClickListener positive, OnClickListener negative) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(R.string.dialog_title_error);
		dialog.setPositiveButton(R.string.dialog_try_again, positive);
		dialog.setNegativeButton(R.string.cancel, negative);
		dialog.setMessage(R.string.download_error_try_again);
		dialog.setCancelable(false);

		return dialog.create();

	}

	public static AlertDialog createOkCancelDialog(Context context,
			OnClickListener positive, OnClickListener negative, int messageId) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(R.string.dialog_title_message);
		dialog.setPositiveButton(R.string.dialog_ok, positive);
		dialog.setNegativeButton(R.string.dialog_cancel, negative);
		dialog.setCancelable(false);
		dialog.setMessage(messageId);

		return dialog.create();

	}
}
