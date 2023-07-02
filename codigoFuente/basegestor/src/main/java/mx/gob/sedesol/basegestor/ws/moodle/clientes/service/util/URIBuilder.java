package mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util;

import java.net.URI;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.util.UriComponentsBuilder;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;

public class URIBuilder {

	private static final Logger logger = Logger.getLogger(URIBuilder.class);
	
	private ParametroWSMoodleDTO dto;
	
	public URIBuilder(ParametroWSMoodleDTO dto) {
		super();
		this.dto = dto;
	}
	
	/*public static URI buildWSUri(String path, Map<String, Object> paramMap){
		UriComponentsBuilder ucb = UriComponentsBuilder.fromUriString(bundle.getString(StaticsConstants.WS_URL_HOST));
		ucb.path(path);
		for(String key : paramMap.keySet()){
			ucb.queryParam(key, paramMap.get(key));
		}
		return ucb.build().toUri();
	}*/
        
	public URI buildWSUri(String path, Map<String, Object> paramMap, Object... uriVariables){
		logger.debug("[buildWSUri]");
        UriComponentsBuilder ucb = UriComponentsBuilder.fromUriString(getDto().getHost());
		ucb.path(path);
		//ucb.buildAndExpand(uriVariables); //Reemplazar las variables de path
		for(String key : paramMap.keySet()){
			ucb.queryParam(key, paramMap.get(key));
		}
                for( Object x: uriVariables){
                	logger.debug("variables   "+x+"-");
                }
		return ucb.buildAndExpand(uriVariables).toUri();
	}
	
	/**
	 * @return the dto
	 */
	public ParametroWSMoodleDTO getDto() {
		return dto;
	}

	/**
	 * @param dto the dto to set
	 */
	public void setDto(ParametroWSMoodleDTO dto) {
		this.dto = dto;
	}
}
