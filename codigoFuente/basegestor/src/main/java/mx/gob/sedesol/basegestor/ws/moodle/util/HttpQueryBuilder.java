package mx.gob.sedesol.basegestor.ws.moodle.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class HttpQueryBuilder {
	
	private ObjectMapper mapper = new ObjectMapper();
	private String host;
	private int port;
	private String path;
	
	public HttpQueryBuilder(){}
	
	public HttpQueryBuilder(String host, int port, String path){
		this.host = host;
		this.port = port;
		this.path = path;
	}

	public ObjectMapper getMapper() {
		return mapper;
	}

	public void setMapper(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public URI getObjectURI(Object JsonObject){
		String query = null;
		URI uri = null;
		try {
			query = ObjectToHttpBuildQuery(JsonObject);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			uri = new URI("http", null, host, port, path, query, null);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return uri;
	}

	public String ObjectToHttpBuildQuery(Object JsonObject) throws JsonProcessingException, IOException {
		ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
		JsonNode root = mapper.readTree(writer.writeValueAsString(JsonObject));
		return JsonNodeToHttpBuildQuery(root);
	}

	public String JsonToHttpBuildQuery(String JsonString) throws JsonProcessingException, IOException {
		JsonNode root = mapper.readTree(JsonString);
		return JsonNodeToHttpBuildQuery(root);
	}

	private String JsonNodeToHttpBuildQuery(JsonNode root) {
		StringBuilder res = new StringBuilder();
		generateBuildQuery(root, res);
		return res.toString();
	}
	
	private void generateBuildQuery(JsonNode root, StringBuilder res){
		generateBuildQuery(root, true, new StringBuilder(),res);
	}

	private void generateBuildQuery(JsonNode node, boolean isRoot, StringBuilder prefix, StringBuilder res) {
		if (!node.isArray() && !node.isObject()) {
			res.append("&");
			res.append(prefix);
			res.append("=");
			res.append(node.asText());
		} else if (node.isObject()) {
			Iterator<String> it = node.fieldNames();
			while (it.hasNext()) {
				String newField = it.next();
				StringBuilder newPrefix = appendPrefix(prefix, newField, isRoot);
				JsonNode newNode = node.get(newField);
				generateBuildQuery(newNode, false, newPrefix, res);
			}
		} else if (node.isArray()) {
			Integer pos = 0;
			Iterator<JsonNode> it = node.elements();
			while (it.hasNext()) {
				JsonNode newNode = it.next();
				StringBuilder newPrefix = appendPrefix(prefix, pos.toString(), isRoot);
				generateBuildQuery(newNode, false, newPrefix, res);
				pos++;
			}
		}
	}

	private StringBuilder appendPrefix(StringBuilder prefix, String field, boolean isRoot) {
		StringBuilder newPrefix = new StringBuilder();
		newPrefix.append(prefix);
		if(isRoot){
			newPrefix.append(field);
		}else {
			newPrefix.append("[");
			newPrefix.append(field);
			newPrefix.append("]");
		}
		return newPrefix;
	}

}
