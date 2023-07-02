package mx.gob.sedesol.gestorweb.sistema;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.apache.log4j.Logger;


import mx.gob.sedesol.basegestor.commons.dto.admin.TextoSistemaDTO;
import mx.gob.sedesol.basegestor.commons.utils.TipoTemaEnum;
import mx.gob.sedesol.basegestor.service.admin.TemaService;
import mx.gob.sedesol.basegestor.service.admin.TextoSistemaService;

@ApplicationScoped
@ManagedBean(name = "sistema")
public class SistemaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(SistemaBean.class);

	@ManagedProperty(value = "#{textoSistemaService}")
	private transient TextoSistemaService textoSistemaService;
	
	@ManagedProperty(value = "#{temaService}")
	private transient TemaService temaService;
	
	private Map<String, String> textos = new HashMap<>();
	
	private String temaPrivado;
	private String temaPublico;
	
	@PostConstruct
	public void iniciarTextos() {
		
		java.util.logging.Logger mongoLogger = java.util.logging.Logger.getLogger( "org.mongodb.driver" );
		mongoLogger.setLevel(Level.WARNING);
		
		for (TextoSistemaDTO dto : textoSistemaService.findAll()) {
			textos.put(dto.getClave(), dto.getValor());
		}
		
		temaPrivado = temaService.obtenerTemaActivo(TipoTemaEnum.PRIVADO.getValor());
		temaPublico = temaService.obtenerTemaActivo(TipoTemaEnum.PUBLICO.getValor());
	}
	
	public String obtenerTexto(String clave) {
		if (textos.containsKey(clave)) {
			return textos.get(clave);
		} else {
			return clave;
		}
	}
	
	public void establecerTexto(String clave, String valor) {
		textos.put(clave, valor);
	}
	
	public String obtenerTemaPublico() {
		return temaPublico;
	}
	
	public String obtenerTemaPrivado() {
		return temaPrivado;
	}
	
	public void establecerTema(String tema, TipoTemaEnum tipo) {
		switch (tipo) {
		case PRIVADO:
			temaPrivado = tema;
			break;
		case PUBLICO:
			temaPublico = tema;
			break;
		default:
			break;
		}
	}
	
	/* INICIO DE GETS Y SETS */
	public TextoSistemaService getTextoSistemaService() {
		return textoSistemaService;
	}

	public void setTextoSistemaService(TextoSistemaService textoSistemaService) {
		this.textoSistemaService = textoSistemaService;
	}

	public TemaService getTemaService() {
		return temaService;
	}

	public void setTemaService(TemaService temaService) {
		this.temaService = temaService;
	}

}
