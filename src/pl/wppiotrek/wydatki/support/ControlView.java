package pl.wppiotrek.wydatki.support;

import java.util.HashMap;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.units.ViewState;
import pl.wppiotrek.wydatki.views.CustomViewBase;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ControlView extends CustomViewBase<ViewState> {
	/**
	 * Okrˆg¸y progressbar
	 */
	private ProgressBar roundBar;
	/**
	 * Opis
	 */
	private TextView stateTextView;
	/**
	 * Ikonka odæwieýania
	 */
	private ImageView refreshIndicator;
	/**
	 * Mapa do przechowywania komunikat—w
	 */
	private final HashMap<ViewState, Integer> messageMap = new HashMap<ViewState, Integer>();

	public ControlView(Context context, View view, ViewState content) {
		super(context, view, content);
	}

	@Override
	public void linkViews() {
		stateTextView = (TextView) view
				.findViewById(R.id.control_row_view_content_textview);

		roundBar = (ProgressBar) view
				.findViewById(R.id.control_row_view_progress_indicator);

		refreshIndicator = (ImageView) view
				.findViewById(R.id.control_row_view_refresh_indicator);
	}

	@Override
	public void fillViews() {
		String message = "";
		int progressIndicatorVisibility = ProgressBar.INVISIBLE;
		int refreshIndicatorVisibility = ImageView.INVISIBLE;
		switch (content) {
		case Normal:
			message = (messageMap.containsKey(ViewState.Normal) ? context
					.getString(messageMap.get(ViewState.Normal)) : context
					.getString(R.string.get_more_data));
			progressIndicatorVisibility = ProgressBar.INVISIBLE;
			refreshIndicatorVisibility = ImageView.INVISIBLE;
			break;
		case NoObjects:
			message = (messageMap.containsKey(ViewState.NoObjects) ? context
					.getString(messageMap.get(ViewState.NoObjects)) : context
					.getString(R.string.nothing_to_show_on_list));
			progressIndicatorVisibility = ProgressBar.INVISIBLE;
			refreshIndicatorVisibility = ImageView.INVISIBLE;
			break;
		case Downloading:
			message = (messageMap.containsKey(ViewState.Downloading) ? context
					.getString(messageMap.get(ViewState.Downloading)) : context
					.getString(R.string.getting_more_data));
			progressIndicatorVisibility = ProgressBar.VISIBLE;
			refreshIndicatorVisibility = ImageView.INVISIBLE;
			break;
		case DownloadException:
			message = (messageMap.containsKey(ViewState.DownloadException) ? context
					.getString(messageMap.get(ViewState.DownloadException))
					: context.getString(R.string.download_error_try_again));
			progressIndicatorVisibility = ProgressBar.INVISIBLE;
			refreshIndicatorVisibility = ImageView.VISIBLE;
			break;
		}
		// ustawianie
		stateTextView.setText(message.toString());
		roundBar.setVisibility(progressIndicatorVisibility);
		refreshIndicator.setVisibility(refreshIndicatorVisibility);
		view.setTag(content);
	}

	public void addCustomMessage(ViewState state, Integer messageID) {
		messageMap.put(state, messageID);
	}

	@Override
	protected void clean() {

	}
}
