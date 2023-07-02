package mx.gob.sedesol.basegestor.ws.moodle.util;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

public class MoodleWSPropertyNamingStrategy extends PropertyNamingStrategy {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1240713464319370716L;

	@Override
    public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
        return defaultName.toLowerCase();
    }

    @Override
    public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
        return defaultName.toLowerCase();
    }

    @Override
    public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
        return defaultName.toLowerCase();
    }
	
}
