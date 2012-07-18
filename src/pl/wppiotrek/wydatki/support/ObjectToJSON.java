package pl.wppiotrek.wydatki.support;

import java.io.IOException;
import java.io.StringWriter;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.StdSerializerProvider;

public class ObjectToJSON {

	public static String getJSONFromObject(Object object) {
		ObjectMapper mapperRequest = new ObjectMapper();
		StringWriter sw = new StringWriter();

		try {
			StdSerializerProvider sp = new StdSerializerProvider();
			sp.setNullValueSerializer(new NullSerializer());
			mapperRequest.setSerializerProvider(sp);
			mapperRequest.writeValue(sw, object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sw.toString();
	}
}

class NullSerializer extends JsonSerializer<Object> {
	@Override
	public void serialize(Object value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {

		jgen.writeNull();
	}
}
