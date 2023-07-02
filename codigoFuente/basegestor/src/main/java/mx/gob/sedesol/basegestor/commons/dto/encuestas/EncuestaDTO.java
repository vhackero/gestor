package mx.gob.sedesol.basegestor.commons.dto.encuestas;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

public class EncuestaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer activo;
    private String clave;
    private Integer numeroRevision;
    private Date fechaActualizacion;
    private Date fechaCierre;
    private Date fechaCreacion;
    private Date fechaPublicacion;
    @NotEmpty(message = "gw.global.msg.dato.req")
    @Size(max = 500, message = "gw.global.msg.dato.max.500")
    private String instrucciones;
    @NotEmpty(message = "gw.global.msg.dato.req")
    @Size(max = 100, message = "gw.global.msg.dato.max.100")
    private String nombre;
    @Size(max = 500, message = "gw.global.msg.dato.max.500")
    private String observaciones;
    private Integer orden;
    private Long usuarioAutor;
    private Long usuarioModifico;
    @NotNull(message = "gw.global.msg.dato.req")
    private CatalogoComunDTO encuestaEstatus;
    @NotNull(message = "gw.global.msg.dato.req")
    private CatalogoComunDTO encuestaObjetivo;
    @NotNull(message = "gw.global.msg.dato.req")
    private CatalogoComunDTO encuestaTipo;
//    @NotNull(message = "gw.global.msg.dato.req")
//    private CatalogoComunDTO encuestaContexto;
    
    private String comentarios;
    /**
     * Referencia de las preguntas de la encuesta
     */
    private List<PreguntaEncuestaDTO> preguntasEncuesta;
    private Integer idParent;
    private Date fechaRegistroAux;
    private String nombreUsuarioMod;

    /* id del usuario destinatario de la notificación*/
    private Long idUsrMensajeDestino;
    
	private List<RelEncuestaEventoCapacitacionDTO> relEncuestaEvento;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Long getUsuarioAutor() {
        return usuarioAutor;
    }

    public void setUsuarioAutor(Long usuarioAutor) {
        this.usuarioAutor = usuarioAutor;
    }

    public Long getUsuarioModifico() {
        return usuarioModifico;
    }

    public void setUsuarioModifico(Long usuarioModifico) {
        this.usuarioModifico = usuarioModifico;
    }

    public CatalogoComunDTO getEncuestaEstatus() {
        if (ObjectUtils.isNull(encuestaEstatus)) {
            encuestaEstatus = new CatalogoComunDTO();
        }
        return encuestaEstatus;
    }

    public void setEncuestaEstatus(CatalogoComunDTO encuestaEstatus) {
        this.encuestaEstatus = encuestaEstatus;
    }

    public CatalogoComunDTO getEncuestaObjetivo() {
        return encuestaObjetivo;
    }

    public void setEncuestaObjetivo(CatalogoComunDTO encuestaObjetivo) {
        this.encuestaObjetivo = encuestaObjetivo;
    }

    public CatalogoComunDTO getEncuestaTipo() {
        return encuestaTipo;
    }

    public void setEncuestaTipo(CatalogoComunDTO encuestaTipo) {
        this.encuestaTipo = encuestaTipo;
    }

    public List<PreguntaEncuestaDTO> getPreguntasEncuesta() {
        return preguntasEncuesta;
    }

    public void setPreguntasEncuesta(List<PreguntaEncuestaDTO> preguntasEncuesta) {
        this.preguntasEncuesta = preguntasEncuesta;
    }

    public Integer getIdParent() {
        return idParent;
    }

    public void setIdParent(Integer idParent) {
        this.idParent = idParent;
    }

    public Date getFechaRegistroAux() {
        return fechaRegistroAux;
    }

    public void setFechaRegistroAux(Date fechaRegistroAux) {
        this.fechaRegistroAux = fechaRegistroAux;
    }

    public String getNombreUsuarioMod() {
        return nombreUsuarioMod;
    }

    public void setNombreUsuarioMod(String nombreUsuarioMod) {
        this.nombreUsuarioMod = nombreUsuarioMod;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Long getIdUsrMensajeDestino() {
        return idUsrMensajeDestino;
    }

    public void setIdUsrMensajeDestino(Long idUsrMensajeDestino) {
        this.idUsrMensajeDestino = idUsrMensajeDestino;
    }

    /**
     * @return the numeroRevision
     */
    public Integer getNumeroRevision() {
        return numeroRevision;
    }

    /**
     * @param numeroRevision the numeroRevision to set
     */
    public void setNumeroRevision(Integer numeroRevision) {
        this.numeroRevision = numeroRevision;
    }

	public List<RelEncuestaEventoCapacitacionDTO> getRelEncuestaEvento() {
		return relEncuestaEvento;
	}

	public void setRelEncuestaEvento(List<RelEncuestaEventoCapacitacionDTO> relEncuestaEvento) {
		this.relEncuestaEvento = relEncuestaEvento;
	}

//	public CatalogoComunDTO getEncuestaContexto() {
//		return encuestaContexto;
//	}
//
//	public void setEncuestaContexto(CatalogoComunDTO encuestaContexto) {
//		this.encuestaContexto = encuestaContexto;
//	}

}
