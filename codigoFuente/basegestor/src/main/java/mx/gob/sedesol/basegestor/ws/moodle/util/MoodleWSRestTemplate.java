package mx.gob.sedesol.basegestor.ws.moodle.util;

import java.net.URI;
import java.util.Date;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;

import mx.gob.sedesol.basegestor.ws.moodle.util.serializer.NumericBooleanSerializer;
import mx.gob.sedesol.basegestor.ws.moodle.util.serializer.UnixDateSerializer;

public class MoodleWSRestTemplate{
	
	private RestTemplate template;
	private HttpQueryBuilder httpQueryBuilder;

	public RestTemplate getTemplate() {
		return template;
	}

	public void setTemplate(RestTemplate template) {
		this.template = template;
	}

	public HttpQueryBuilder getHttpQueryBuilder() {
		return httpQueryBuilder;
	}

	public void setHttpQueryBuilder(HttpQueryBuilder httpQueryBuilder) {
		this.httpQueryBuilder = httpQueryBuilder;
	}

	public MoodleWSRestTemplate(String host, int port, String path){
		httpQueryBuilder = new HttpQueryBuilder(host, port, path);
		setDefaultValues();
	}
	
	public MoodleWSRestTemplate(){
		setDefaultValues();
	}
	
	private void setDefaultValues(){
		template = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();
		for(HttpMessageConverter<?> hmc : template.getMessageConverters()){
			if(hmc instanceof MappingJackson2HttpMessageConverter){
				mapper = ((MappingJackson2HttpMessageConverter) hmc).getObjectMapper();
				break;
			}
		}
		PropertyNamingStrategy strategy = new MoodleWSPropertyNamingStrategy();
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setPropertyNamingStrategy(strategy);
		SimpleModule module = new SimpleModule();
		module.addSerializer(Boolean.class, new NumericBooleanSerializer());
		module.addSerializer(Date.class, new UnixDateSerializer());
		mapper.registerModule(module);
		mapper.setSerializationInclusion(Include.NON_NULL);
		httpQueryBuilder.setMapper(mapper);
	}
	
	public <T> T getForObject(Object request, Class<T> responseType){
		URI uri = httpQueryBuilder.getObjectURI(request);
		T res = template.getForObject(uri, responseType);
		return res;
	}

}
