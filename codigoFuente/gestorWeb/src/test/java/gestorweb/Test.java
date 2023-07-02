package gestorweb;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import mx.gob.sedesol.basegestor.commons.dto.admin.InformacionMensajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.gestorweb.commons.dto.ServicioCURP;
import mx.gob.sedesol.gestorweb.ws.AES;
import mx.gob.sedesol.gestorweb.ws.IdentityServerConectionClientIT;
import mx.gob.sedesol.gestorweb.ws.ServiciosExternos;

public class Test {

	@org.junit.Test
	public void validarServicio() {
		RestTemplate restTemplate = new RestTemplate();
		System.out.println(
				restTemplate.getForObject("http://localhost:8080/plataforma/ws/mensajes/numero/39",
						InformacionMensajeDTO.class).getNumeroMensajes());
	}

	

	@org.junit.Test
	public void validarServicioCURP() {
		System.out.println("inicio");
		ServiciosExternos serviciosExternos = new ServiciosExternos();
		ServicioCURP servicioCURP = serviciosExternos.obtenerDatosPersona("LOLC811027HTLPPR01");
		System.out.println(servicioCURP);
		
		System.out.println("completado");
	}

	@org.junit.Test
	public void validarServicioCURP2() {
		PersonaDTO persona = new PersonaDTO();
		
		persona.setNombre("Carlos Alberto");
		persona.setApellidoPaterno("López");
		persona.setApellidoMaterno("López");
		persona.setFechaNacimiento(DateUtils.convierteStringADate("27/10/1981", "dd/MM/yyyy"));
		persona.setGenero("H");
		persona.setCurp("LOLC811027HTLPPR01");
//		ServicioCURP servicio = restTemplate.getForObject(
//				"http://172.25.39.44:7013/SoapServiceDetalle/webresources/renapo/query/LOPEZ/LOPEZ/CARLOS ALBERTO/H/27-10-1981/29",
//				ServicioCURP.class);
		ServiciosExternos serviciosExternos = new ServiciosExternos();
		Boolean valido = serviciosExternos.validarDatosPersona(persona);
		
		System.out.println(valido);
	}

	@org.junit.Test
	public void probartoken() {

		String ValorToken = "b54aac5a-ccc1-3b4d-a7fe-1e4cb71f8114";
		IdentityServerConectionClientIT prueba = new IdentityServerConectionClientIT();
		try {
			System.out.println(prueba.validar_token(ValorToken));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@org.junit.Test
	public void encriptar() {
		String secretKey = "1[0x1]";
		String encryptedString = AES.encrypt("jorget", secretKey);
		System.out.println(encryptedString);
		String decryptedString = AES.decrypt(encryptedString, secretKey);
		System.out.println(decryptedString);
	}

	@org.junit.Test
	public void probarServicio() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://10.38.2.196:8080/plataforma/ws/SSO/login?token=58119747-6946-3fe4-907b-565fe7b2ed56&user=MSdLSJ8jd+hc+T+UNmp5yw==";
		System.out.println(url);
		HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
		System.out.println(response);
	}
}
