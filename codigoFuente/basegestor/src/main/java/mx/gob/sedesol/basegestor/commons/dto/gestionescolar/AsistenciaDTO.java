package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * Created by jhcortes on 2/02/17.
 */
public class AsistenciaDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *
     */

    private Integer id;
    private Integer activo;
    private String descripcion;
    private Date fechaActualizacion;
    @NotNull( message = "gw.global.msg.dato.req")
    private Date fechaRegistro;
    @NotNull( message = "gw.global.msg.dato.req")
    private String nombre;
    private String nombreCorto;
    private Integer orden;
    private Long usuarioModifico;
    private String nombreUsuarioMod;
    private boolean activoBoolean;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Long getUsuarioModifico() {
        return usuarioModifico;
    }

    public void setUsuarioModifico(Long usuarioModifico) {
        this.usuarioModifico = usuarioModifico;
    }

    public String getNombreUsuarioMod() {
        return nombreUsuarioMod;
    }

    public void setNombreUsuarioMod(String nombreUsuarioMod) {
        this.nombreUsuarioMod = nombreUsuarioMod;
    }

    public boolean getActivoBoolean() {
        this.activoBoolean = this.activo.equals(1);

        return this.activoBoolean;
    }

    public void setActivoBoolean(boolean activoBoolean) {
        this.activoBoolean = activoBoolean;
    }
}
