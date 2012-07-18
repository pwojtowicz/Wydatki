/**
 * 
 */
package pl.wppiotrek.wydatki.support;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

/**
 * @author kamiltustanowski
 * 
 *         Klasa odpowiadaj�ca za deserializacj� dat
 */
public class JSONDateDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {

		// pobranie tekstu z parsera
		Date date = null;
		String dateString = parser.getText();
		if (dateString.contains("/Date")) { // Obs�uga starej daty
			// usuwanie niepotrzebnych znak�w
			dateString = dateString.replace("/Date(", "");
			dateString = dateString.replace(")/", "");
			// rozbijanie wyniku w miejscu "+" na dwie tablice
			String[] dateArray = dateString.split("\\+");
			// tworzenie daty z milisekund
			date = new Date(Long.parseLong(dateArray[0]));
		} else { // Obs�uga nowej daty
			if (dateString.contains("+")) {
				String[] dateArray = dateString.split("T");
				String dateToFill = dateArray[0];

				String[] timeArray = dateArray[1].split("+");
				String timeToFill = timeArray[0];

				dateString = dateToFill + " " + timeToFill;
			} else {
				dateString = dateString.replace("T", " ");
			}
			// TODO obs�uga +02:00 itp.

			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			try {
				date = dateFormat.parse(dateString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return date;
	}
}
