package mx.gob.sedesol.basegestor.ws.moodle.util.serializer;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;

import mx.gob.sedesol.basegestor.ws.moodle.util.MoodleWSPropertyNamingStrategy;
import mx.gob.sedesol.basegestor.ws.moodle.util.serializer.NumericBooleanSerializer;

public class NumericBooleanSerializerTest {

	@Test
	public void testSerializeBooleanJsonGeneratorSerializerProvider() {
		
		NumericBooleanSerializer serializer = new NumericBooleanSerializer();
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(Boolean.class,serializer);
		mapper.registerModule(module);
		ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
		try {
			assertEquals("1", writer.writeValueAsString(true));
			assertEquals("0", writer.writeValueAsString(false));
		} catch (JsonProcessingException e) {
			fail("La serialización de booleanos es incorrecta");
		}
		
	}

}
