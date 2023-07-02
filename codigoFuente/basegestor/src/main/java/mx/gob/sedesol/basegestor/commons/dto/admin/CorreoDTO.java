package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author PlanetMedia
 *
 */
public class CorreoDTO {

    private List<String> destinatarios;
    private List<String> destinatariosCC;
    private String remitente;
    private String titulo;
    private String nombre;
    private String extraInfo;
    private String contenido;
    private String host;
    private String port;
    private String asunto;
    
    private String usuarioCorreo;
    private String passwordCorreo;
    
    private String contexto;
    private String rutaAplicacion;
    private String contenidoInicial;

    
    /**
     * Lista de attachments que contiene el correo
     */
    private List<DatoAdjuntoDTO> adjuntos = new ArrayList<DatoAdjuntoDTO>();

    
    public void agregarDestinatario(String destinatario){
    	if(this.destinatarios == null)
    		this.destinatarios = new ArrayList<String>();
    	
    	destinatarios.add(destinatario);
    }
    
    /**
     * Agrega una attachment a la lista correspondiente
     * 
     * @param attachment
     *            Attachment a agregar
     */
    public void addDatoAdjunto(DatoAdjuntoDTO attachment) {
        this.adjuntos.add(attachment);
    }

	/**
	 * @return the destinatarios
	 */
	public List<String> getDestinatarios() {
		return destinatarios;
	}

	/**
	 * @param destinatarios the destinatarios to set
	 */
	public void setDestinatarios(List<String> destinatarios) {
		this.destinatarios = destinatarios;
	}

	/**
	 * @return the remitente
	 */
	public String getRemitente() {
		return remitente;
	}

	/**
	 * @param remitente the remitente to set
	 */
	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the extraInfo
	 */
	public String getExtraInfo() {
		return extraInfo;
	}

	/**
	 * @param extraInfo the extraInfo to set
	 */
	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

	/**
	 * @return the contenido
	 */
	public String getContenido() {
		return contenido;
	}

	/**
	 * @param contenido the contenido to set
	 */
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	/**
	 * @return the adjuntos
	 */
	public List<DatoAdjuntoDTO> getAdjuntos() {
		return adjuntos;
	}

	/**
	 * @param adjuntos the adjuntos to set
	 */
	public void setAdjuntos(List<DatoAdjuntoDTO> adjuntos) {
		this.adjuntos = adjuntos;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return the usuarioCorreo
	 */
	public String getUsuarioCorreo() {
		return usuarioCorreo;
	}

	/**
	 * @param usuarioCorreo the usuarioCorreo to set
	 */
	public void setUsuarioCorreo(String usuarioCorreo) {
		this.usuarioCorreo = usuarioCorreo;
	}

	/**
	 * @return the passwordCorreo
	 */
	public String getPasswordCorreo() {
		return passwordCorreo;
	}

	/**
	 * @param passwordCorreo the passwordCorreo to set
	 */
	public void setPasswordCorreo(String passwordCorreo) {
		this.passwordCorreo = passwordCorreo;
	}

	/**
	 * @return the asunto
	 */
	public String getAsunto() {
		return asunto;
	}

	/**
	 * @param asunto the asunto to set
	 */
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getContexto() {
		return contexto;
	}

	public void setContexto(String contexto) {
		this.contexto = contexto;
	}

	public String getRutaAplicacion() {
		return rutaAplicacion;
	}

	public void setRutaAplicacion(String rutaAplicacion) {
		this.rutaAplicacion = rutaAplicacion;
	}

	public String getContenidoInicial() {
		return contenidoInicial;
	}

	public void setContenidoInicial(String contenidoInicial) {
		this.contenidoInicial = contenidoInicial;
	}

	/**
	 * @return the destinatariosCC
	 */
	public List<String> getDestinatariosCC() {
		return destinatariosCC;
	}

	/**
	 * @param destinatariosCC the destinatariosCC to set
	 */
	public void setDestinatariosCC(List<String> destinatariosCC) {
		this.destinatariosCC = destinatariosCC;
	}

}
