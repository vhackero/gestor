package mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.TokenController;

public class LoginWS {
	
	private ParametroWSMoodleDTO parametroWSMoodleDTO;
	
	public LoginWS(ParametroWSMoodleDTO plataforma){
		this.parametroWSMoodleDTO = plataforma;
	}
	
	public String generarAccesoMoodle(String usuario, String password, int idCurso) throws ErrorWS{
		
		ParametroWSMoodleDTO param = new ParametroWSMoodleDTO();
		param.setHost(this.parametroWSMoodleDTO.getHost());
		param.setOuth("/local/wstemplate/tokensisi.php");
		param.setUsername(usuario);
		param.setPassword(password);
		TokenController token = new TokenController(param);
        String accessToken = token.getAccessToken();
		
		return this.parametroWSMoodleDTO.getHost()+"/local/wstemplate/index_sin_login.php?courseid="+idCurso+"&token="+accessToken;
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
