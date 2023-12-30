package mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.service.impl.gestion.aprendizaje.AmbienteVirtualApServiceImpl;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.TokenController;

public class LoginWS {
	
	private ParametroWSMoodleDTO parametroWSMoodleDTO;
	private static final Logger logger = Logger.getLogger(AmbienteVirtualApServiceImpl.class);
	public LoginWS(ParametroWSMoodleDTO plataforma){
		this.parametroWSMoodleDTO = plataforma;
	}
	
	public String generarAccesoMoodle(String usuario, String password, int idCurso) throws ErrorWS{
		String accessToken = "";
		ParametroWSMoodleDTO param = new ParametroWSMoodleDTO();
		param.setHost(this.parametroWSMoodleDTO.getHost());
		param.setOuth("/local/wstemplate/tokensisi.php");
		param.setUsername(usuario);
		param.setPassword(password);
		//TokenController token = new TokenController(param);
        //String accessToken = token.getAccessToken();
		try {
		    accessToken = performGetRequest(param);
		    System.out.println("Respuesta del servicio web: " + accessToken);
		    logger.info(this.parametroWSMoodleDTO.getHost()+"/local/wstemplate/index_sin_login.php?courseid="+idCurso+"&token="+accessToken);
		} catch (IOException e) {
		    e.printStackTrace();
		    System.out.println("Detalles del error: " + e.getMessage());
		}
		return this.parametroWSMoodleDTO.getHost()+"/local/wstemplate/index_sin_login.php?courseid="+idCurso+"&token="+accessToken;
		//return this.parametroWSMoodleDTO.getHost()+"/local/wstemplate/index_sin_login.php?courseid="+idCurso+"&token="+accessToken;
	}
	
	
	private static String performGetRequest(ParametroWSMoodleDTO param) throws IOException {
        String urlString = param.getHost() + param.getOuth() +
                           "?username=" + param.getUsername() +
                           "&password=" + param.getPassword();

        URL url = new URL(urlString);
       logger.info(url);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            JSONObject json = new JSONObject(response.toString());
            return json.getString("token");
        } finally {
            connection.disconnect();
        }
    }	
	
	public static void main(String[] args){
		ParametroWSMoodleDTO param = new ParametroWSMoodleDTO();
		param.setHost("http://189.206.122.67/sedesol/plataformaEducativa");
		try {
			System.out.println(new LoginWS(param).generarAccesoMoodle("userdesa", "$2a$10$le8L9zxREGmC.jgWcnwbd.Eip/xA3LZxJHOAfyVknTqcSXEtPy0q.", 58));
		} catch (ErrorWS e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
