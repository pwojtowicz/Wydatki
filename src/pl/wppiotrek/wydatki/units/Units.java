package pl.wppiotrek.wydatki.units;


public class Units {

	public final static int UnitSzt = 1;
	public final static int UnitKg = 2;

	public static String getParameterName(int parameter) {
		switch (parameter) {
		case UnitSzt:
			return "szt.";
		case UnitKg:
			return "kg";
		default:
			return "";
		}
	}

}
