package pl.wppiotrek.wydatki.units;

import pl.wppiotrek.wydatki.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class AccountImages {
	public final static int Money = 1;
	public final static int Cash = 2;
	public final static int Deposit = 3;
	public final static int CreditCard = 4;

	public static Drawable getImageForImageIndex(int imageIndex, Context context) {
		Resources resources = context.getResources();
		switch (imageIndex) {
		case Money:
			return resources.getDrawable(R.drawable.money);
		case Cash:
			return resources.getDrawable(R.drawable.cash);
		case Deposit:
			return resources.getDrawable(R.drawable.deposit);
		case CreditCard:
			return resources.getDrawable(R.drawable.credit_card);
		}
		return resources.getDrawable(R.drawable.money);

	}

}
