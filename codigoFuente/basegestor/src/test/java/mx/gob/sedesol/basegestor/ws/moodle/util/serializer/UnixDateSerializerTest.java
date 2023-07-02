package mx.gob.sedesol.basegestor.ws.moodle.util.serializer;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;

import mx.gob.sedesol.basegestor.ws.moodle.util.serializer.UnixDateSerializer;

public class UnixDateSerializerTest {

	@Test
	public void testSerializeDateJsonGeneratorSerializerProvider() {
		UnixDateSerializer serializer = new UnixDateSerializer();
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(Date.class,serializer);
		mapper.registerModule(module);
		ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
		Date testDate = new Date();
		try {
			assertEquals(""+testDate.getTime(), writer.writeValueAsString(testDate));
		} catch (JsonProcessingException e) {
			fail("La serialización de fechas es incorrecta");
		}
	}

}
