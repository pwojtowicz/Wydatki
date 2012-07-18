/**
 * 
 */
package pl.wppiotrek.wydatki.support;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * @author kamiltustanowski
 * 
 */

public class JSONDateSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date date, JsonGenerator gen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		if (date != null) {
			String formattedDate = "/Date(" + date.getTime() + "+0100)/";
			gen.writeString(formattedDate);
		}
	}

}
