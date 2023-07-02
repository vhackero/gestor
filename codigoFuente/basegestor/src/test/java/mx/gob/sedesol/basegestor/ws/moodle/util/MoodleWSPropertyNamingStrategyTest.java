package mx.gob.sedesol.basegestor.ws.moodle.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import mx.gob.sedesol.basegestor.ws.moodle.util.MoodleWSPropertyNamingStrategy;

public class MoodleWSPropertyNamingStrategyTest {
	
	private class TestPojo{
		private String camelCasedProperty = "a";
		private String UPPERCASEDPROPERTY = "b";
		private String rAnDoMCaSEdPropeRTY = "c";
		public String getCamelCasedProperty() {
			return camelCasedProperty;
		}
		public void setCamelCasedProperty(String camelCasedProperty) {
			this.camelCasedProperty = camelCasedProperty;
		}
		public String getUPPERCASEDPROPERTY() {
			return UPPERCASEDPROPERTY;
		}
		public void setUPPERCASEDPROPERTY(String uPPERCASEDPROPERTY) {
			UPPERCASEDPROPERTY = uPPERCASEDPROPERTY;
		}
		public String getrAnDoMCaSEdPropeRTY() {
			return rAnDoMCaSEdPropeRTY;
		}
		public void setrAnDoMCaSEdPropeRTY(String rAnDoMCaSEdPropeRTY) {
			this.rAnDoMCaSEdPropeRTY = rAnDoMCaSEdPropeRTY;
		}
	}

	@Test
	public void testSerializationUsingMoodleWSPropertyNamingStrategyTest() {
		TestPojo test = new TestPojo();
		ObjectMapper mapper = new ObjectMapper();
		MoodleWSPropertyNamingStrategy strategy = new MoodleWSPropertyNamingStrategy();
		mapper.setPropertyNamingStrategy(strategy);
		ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
		try {
			String generatedJson = writer.writeValueAsString(test);
			assertTrue(generatedJson.contains("camelcasedproperty"));
			assertFalse(generatedJson.contains("camelCasedProperty"));
			assertTrue(generatedJson.contains("uppercasedproperty"));
			assertFalse(generatedJson.contains("UPPERCASEDPROPERTY"));
			assertTrue(generatedJson.contains("randomcasedproperty"));
			assertFalse(generatedJson.contains("rAnDoMCaSEdPropeRTY"));
		} catch (JsonProcessingException e) {
			fail("La conversion del objeto a Json ha fallado");
		}
	}

}
