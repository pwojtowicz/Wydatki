package pl.wppiotrek.wydatki.units;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class UnitConverter {

	public static String doubleToCurrency(Double value) {

		DecimalFormat fmt = new DecimalFormat("#0.00");
		DecimalFormatSymbols fmts = new DecimalFormatSymbols();

		fmts.setGroupingSeparator(' ');
		fmt.setCurrency(Currency.getInstance(Locale.US));

		fmt.setGroupingSize(3);
		fmt.setGroupingUsed(true);
		fmt.setDecimalFormatSymbols(fmts);

		return fmt.format(value) + " z³";
	}

	public static String dateTimeString(Date date) {
		return convertDateToString(date) + " " + convertTimeToString(date);
	}

	public static String convertTimeToString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		return df.format(date);
	}

	public static String convertDateToString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd");
		return df.format(date);
	}

}
