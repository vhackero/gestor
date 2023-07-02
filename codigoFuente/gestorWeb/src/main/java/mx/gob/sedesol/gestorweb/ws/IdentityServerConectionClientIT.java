package mx.gob.sedesol.gestorweb.ws;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import org.springframework.stereotype.Service;

@Service (value = "identityServerConectionClientIT")
public class IdentityServerConectionClientIT {

    private static final Logger LOG = Logger.getLogger(IdentityServerConectionClientIT.class);

    @Autowired
    private ParametroSistemaService parametroSistemaService;

    /*String SERVER_URL = "https://34.209.193.196:9443/oauth2/introspect/";
	String userName = "elearning";
	String userPassword = "Elearning@123";*/
    public boolean validar_token(String ValorToken) throws ParseException, IOException, URISyntaxException {
        String SERVER_URL = parametroSistemaService.obtenerParametro("SSO_IDENTITY_RUTA");
        String result = executePost(SERVER_URL, "token=" + ValorToken, ValorToken);
        LOG.info("###");
        LOG.info(result);
        LOG.info("###");
        if (ObjectUtils.isNullOrEmpty(result)) {
            return false;
        } else {
            return result.contains("\"active\":true");
        }
    }
    
    /**
     * 
     * @param targetURL
     * @param urlParameters
     * @param accessTokenIdentifier
     * @return 
     */
    public String executePost(String targetURL, String urlParameters, String accessTokenIdentifier) {
        URL url;
        HttpURLConnection connection = null;
        try {
            //System.setProperty("javax.net.ssl.trustStore", "wso2carbon.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");
            System.setProperty("javax.net.ssl.trustStoreType", "JKS");
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String string, SSLSession ssls) {
                    return true;
                }
            });

            url = new URL(targetURL);
            LOG.info("url=" + targetURL);

            String userName = parametroSistemaService.obtenerParametro("SSO_IDENTITY_USERNAME");
            String userPassword = parametroSistemaService.obtenerParametro("SSO_IDENTITY_PASSWORD");

            String userpass = userName + ":" + userPassword;
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setRequestProperty("Authorization", basicAuth);//"Bearer " + accessTokenIdentifier);
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            try ( // Send request
                    DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.write(postData);
                wr.flush();
            }
            // Get Response
            InputStream is = connection.getInputStream();
            StringBuilder response;
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(is))) {
                String line;
                response = new StringBuilder();
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
            }
            return response.toString();

        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            return null;

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }

    }

}
