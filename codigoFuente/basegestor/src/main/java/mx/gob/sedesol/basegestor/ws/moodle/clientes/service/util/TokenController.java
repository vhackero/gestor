package mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;

public class TokenController {

	private static final RestTemplate TEMPLATE = new RestTemplate();

	private static final Logger logger = Logger.getLogger(TokenController.class);
	
	private ParametroWSMoodleDTO dto;
	
	public TokenController(ParametroWSMoodleDTO dto) {
		super();
		this.dto = dto;
	}
    
    
    public String getAccessToken() throws ErrorWS {
    	logger.debug("[getAccessToken]");
        //Build the param map
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(StaticsConstants.GRANT_SERVICE, getDto().getService());
        paramMap.put(StaticsConstants.GRANT_PASSWORD, getDto().getPassword());
        paramMap.put(StaticsConstants.GRANT_USER, getDto().getUsername());

        //Build URI to call oauth/token Web Service
        URIBuilder uriBuilder = new URIBuilder(getDto());
        URI uri = uriBuilder.buildWSUri(getDto().getOuth(), paramMap);
        return getTokenValue(TEMPLATE.getForObject(uri, String.class));
    }

    private String getTokenValue(String json) throws ErrorWS{
        String tokenValue = null;
        org.json.JSONObject ojson = new org.json.JSONObject(json);
        try {
            tokenValue = ojson.getString(StaticsConstants.ACCESS_VALUE.toString());
        } catch (org.json.JSONException ex) {
            try {
                ObjectMapper om = new ObjectMapper();
                ErrorWS error = om.readValue(json, ErrorWS.class);
                throw error;
            } catch (IOException ex1) {
                //Logger.getLogger(TokenController.class.getName()).log(Level.SEVERE, null, ex1);
                throw new ErrorWS("Problemas al leer la respuesta. Respuesta inesperada",ex1);
            }
        }
        return tokenValue;
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
