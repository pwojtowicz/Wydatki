package pl.wppiotrek.wydatki.views;

import android.content.Context;
import android.view.View;

public abstract class CustomViewBase<T> {
	/**
	 * Widok
	 */
	protected View view;
	/**
	 * Kontekst
	 */
	protected Context context;

	public CustomViewBase(Context context, View view, T content) {
		this.view = view;
		this.context = context;
		this.content = content;
	}

	/**
	 * Zawarto�
	 */
	protected T content;

	/**
	 * Metoda pod��czaj�ca widoki
	 */
	public abstract void linkViews();

	/**
	 * Metoda wype�niaj�ca pod��czone widoki danymi
	 */
	public abstract void fillViews();

	/**
	 * Czyszczenie warto�ci i stan�w na polu
	 */
	protected abstract void clean();

	// Getters / Setters
	/**
	 * @return the view
	 */
	public View getView() {
		if (view != null) {
			manageView();
		}
		return view;
	}

	/**
	 * Pod��czenie p�l oraz przypisanie warto�ci
	 */
	protected void manageView() {
		this.linkViews();
		clean();
		this.fillViews();
	}

	/**
	 * @param view
	 *            the view to set
	 */
	public void setView(View view) {
		this.view = view;
	}

	/**
	 * @return the content
	 */
	public T getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(T content) {
		this.content = content;
		manageView();
	}

}