package pl.wppiotrek.wydatki.units;

public class ParameterTypes {
	public final static int ParameterText = 1;
	public final static int ParameterNumber = 2;
	public final static int ParameterList = 3;
	public final static int ParameterBoolean = 4;

	public final static int ParameterCurrency = 10;

	public static String getParameterName(int parameter) {
		switch (parameter) {
		case ParameterText:
			return "Tekst";
		case ParameterNumber:
			return "Liczba";
		case ParameterList:
			return "Lista wyboru";
		case ParameterBoolean:
			return "Pole wyboru";
		default:
			return "";
		}
	}
}
