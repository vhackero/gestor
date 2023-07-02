package mx.gob.sedesol.basegestor.ws.moodle.clientes.service.controller;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.StaticsConstants;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.TokenController;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.URIBuilder;


public class WSClientBase {

    private static final RestTemplate template = new RestTemplate();
    
    private static final Logger logger = Logger.getLogger(WSClientBase.class);
    
    private ParametroWSMoodleDTO dto;
    
    public WSClientBase(ParametroWSMoodleDTO dto) {
    	super();
    	this.dto = dto;
    }

	/**
     * Ejecuta un www.
     *
     * @param nombreServicio
     * @param parametros
     * @param tipo
     * @return
     * @throws JSONException
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    
     public <T extends Object> T ejecutarServicioGET(String nombreServicio, Map<String, Object> parametros, Class<T> tipo, Object... urlVariables) throws org.springframework.web.client.ResourceAccessException, JsonParseException, JsonMappingException, IOException, ErrorWS {
    	 logger.debug("[ejecutarServicioGET]");
     	TokenController token = new TokenController(getDto());
        String accessToken = token.getAccessToken();
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("wsfunction", nombreServicio);
        param.put("moodlewsrestformat", "json");
        param.putAll(parametros);
        
        param.put(StaticsConstants.ACCESS_TOKEN, accessToken);
        
        URIBuilder uriBuilder = new URIBuilder(getDto());
        URI uri = uriBuilder.buildWSUri(getDto().getServer(), param, urlVariables);
        logger.debug(uri.toString());
        logger.info(uri.toString());
        T object = template.getForObject(uri, tipo);
        
        return object;
    }

    /**
     *
     * @param <T> Clase de objeto a recibir
     * @param nombreServicio El servicio a ejecutar
     * @param parametros parametros via GET
     * @param requestObject El objeto a enviar a la peticion
     * @param tipo el tipo de objeto a recibir
     * @return Objet T
     * @throws JSONException
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public <T extends Object> T ejecutarServicioPOST(String nombreServicio, HashMap<String, Object> parametros, Object requestObject, Class<T> tipo, Object... urlVariables) throws  ErrorWS {
    	logger.debug("[ejecutarServicioPOST]");
    	TokenController token = new TokenController(getDto());
        String accessToken = token.getAccessToken();
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("wsfunction", nombreServicio);
        param.put("moodlewsrestformat", "json");
        
        param.put(StaticsConstants.ACCESS_TOKEN, accessToken);
        param.putAll(parametros);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        
        for(String k : param.keySet()){
        	map.add(k,param.get(k)==null?"":param.get(k).toString());
        }
        
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        //ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
        
        
        
        
        
        URIBuilder uriBuilder = new URIBuilder(getDto());
        URI uri = uriBuilder.buildWSUri(getDto().getServer(), new HashMap<String, Object>(), urlVariables);
        logger.debug("URI: "+uri.toString());
        logger.info("URI TOTAL: "+uriBuilder.buildWSUri(getDto().getServer(), param, urlVariables));
        logger.debug("REQUEST: "+request);
        logger.debug("MAP: "+map);
        
        T object = null;
        ObjectMapper om = new ObjectMapper();
        try {
        	object = template.postForObject(uri.toASCIIString(), request, tipo);
        	
        }catch(Exception ex){
        	//ex.printStackTrace();
        	ex.getMessage();
        	String json = null;
            try {
            	uri = uriBuilder.buildWSUri(getDto().getServer(), param, urlVariables);
            	json = template.getForObject(uri, String.class);
                ErrorWS ee = om.readValue(json, ErrorWS.class);
                ee.setJson(json);
                throw ee;
            } catch (IOException ex1) {
            	logger.error(ex1);
                throw new ErrorWS("No fue posible leer la estructura regresada: "+json,ex1);
                //Logger.getLogger(WSClientBase.class.getName()).log(Level.SEVERE, null, ex1);
            }
        	
        	
        }
        return object;
    }
    
    
    /**
     * Ejecuta un www.
     *
     * @param <T>
     * @param nombreServicio
     * @param parametros
     * @param t
     * @param urlVariables
     * @param tipo
     * @return
     * @throws JSONException
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws com.clientes.service.util.ErrorWS
     */
    
     public <T extends Object> List<T> ejecutarServicioListGET(String nombreServicio, Map<String, Object> parametros, TypeReference<List<T>> t, Object... urlVariables) throws  ErrorWS {
    	logger.debug("[ejecutarServicioListGET]");
     	TokenController token = new TokenController(getDto());
        String accessToken = token.getAccessToken();
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("wsfunction", nombreServicio);
        param.put("moodlewsrestformat", "json");
        param.putAll(parametros);
        
        param.put(StaticsConstants.ACCESS_TOKEN, accessToken);
        
        URIBuilder uriBuilder = new URIBuilder(getDto());
        URI uri = uriBuilder.buildWSUri(getDto().getServer(), param, urlVariables);
        logger.debug(uri.toString());
        logger.info(uriBuilder.buildWSUri(getDto().getServer(), param, urlVariables));
        ObjectMapper om = new ObjectMapper();
        String json = template.getForObject(uri, String.class);
        List<T> lista;
        try {
            lista = om.readValue(json, t);
        } catch (IOException ex) {
            ex.getMessage();
            try {
                ErrorWS ee = om.readValue(json, ErrorWS.class);
                ee.setJson(json);
                throw ee;
            } catch (IOException ex1) {
            	logger.error(ex1);
                throw new ErrorWS("No fue posible interpretar la respuesta: "+json,ex1);
                //Logger.getLogger(WSClientBase.class.getName()).log(Level.SEVERE, null, ex1);
            }
            //Logger.getLogger(WSClientBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    /**
     *
     * @param <T> Clase de objeto a recibir
     * @param nombreServicio El servicio a ejecutar
     * @param parametros parametros via GET
     * @param requestObject El objeto a enviar a la peticion
     * @param tipo el tipo de objeto a recibir
     * @return Objet T
     * @throws JSONException
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public <T extends Object> List<T> ejecutarServicioListPOST(String nombreServicio, Map<String, Object> parametros, Object requestObject, TypeReference<List<T>> t, Object... urlVariables) throws  ErrorWS {
    	logger.debug("[ejecutarServicioListPOST]");
    	TokenController token = new TokenController(getDto());
        String accessToken = token.getAccessToken();
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("wsfunction", nombreServicio);
        param.put("moodlewsrestformat", "json");
        param.putAll(parametros);
        
        param.put(StaticsConstants.ACCESS_TOKEN, accessToken);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        
        for(String k : param.keySet()){
        	map.add(k,param.get(k)==null?"":param.get(k).toString());
        }
        
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        
        
        URIBuilder uriBuilder = new URIBuilder(getDto());
        URI uri = uriBuilder.buildWSUri(getDto().getServer(), new HashMap<String, Object>(), urlVariables);
        logger.debug(uriBuilder.buildWSUri(getDto().getServer(), param, urlVariables));
        logger.info(uriBuilder.buildWSUri(getDto().getServer(), param, urlVariables));
        logger.debug(requestObject);
        
        
        ObjectMapper om = new ObjectMapper();
        //String json = template.getForObject(uri, String.class);
        String json = template.postForObject(uri.toASCIIString(), request, String.class, urlVariables);
        List<T> lista;
        try {
            lista = om.readValue(json, t);
        } catch (IOException ex) {
            try {
                ErrorWS ee = om.readValue(json, ErrorWS.class);
                ee.setJson(json);
                throw ee;
            } catch (IOException ex1) {
            	logger.error(ex1);
                throw new ErrorWS("No fue posible interpretar la respuesta: "+json,ex1);
                //Logger.getLogger(WSClientBase.class.getName()).log(Level.SEVERE, null, ex1);
            }
            //Logger.getLogger(WSClientBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        //T object =  mapper.readValue(sjson, tipo);
        return lista;
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
