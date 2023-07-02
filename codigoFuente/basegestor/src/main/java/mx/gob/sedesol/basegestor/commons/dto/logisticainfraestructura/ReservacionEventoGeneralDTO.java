package mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;

public class ReservacionEventoGeneralDTO implements Serializable {

    private static final long serialVersionUID = 8096413291328424961L;

    private Integer idReservacionEg;
    private Byte activo;
    private Byte eventoPrivado;
    private Date fechaFinalReservacion;
    private Date fechaInicialReservacion;
    private Date fechaModificacion;
    private Date fechaRegistro;
    private Integer idAreaSede;
    private CatalogoComunDTO catEstatusReservacion;
    private Integer idPersonalizacionArea;
    private Long usuarioRegistro;
    private EventoGeneralDTO tblEventoGeneral;

    /**
     * @return the idReservacionEg
     */
    public Integer getIdReservacionEg() {
        return idReservacionEg;
    }

    /**
     * @param idReservacionEg the idReservacionEg to set
     */
    public void setIdReservacionEg(Integer idReservacionEg) {
        this.idReservacionEg = idReservacionEg;
    }

    /**
     * @return the activo
     */
    public Byte getActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(Byte activo) {
        this.activo = activo;
    }

    /**
     * @return the eventoPrivado
     */
    public Byte getEventoPrivado() {
        return eventoPrivado;
    }

    /**
     * @param eventoPrivado the eventoPrivado to set
     */
    public void setEventoPrivado(Byte eventoPrivado) {
        this.eventoPrivado = eventoPrivado;
    }

    /**
     * @return the fechaFinalReservacion
     */
    public Date getFechaFinalReservacion() {
        return fechaFinalReservacion;
    }

    /**
     * @param fechaFinalReservacion the fechaFinalReservacion to set
     */
    public void setFechaFinalReservacion(Date fechaFinalReservacion) {
        this.fechaFinalReservacion = fechaFinalReservacion;
    }

    /**
     * @return the fechaInicialReservacion
     */
    public Date getFechaInicialReservacion() {
        return fechaInicialReservacion;
    }

    /**
     * @param fechaInicialReservacion the fechaInicialReservacion to set
     */
    public void setFechaInicialReservacion(Date fechaInicialReservacion) {
        this.fechaInicialReservacion = fechaInicialReservacion;
    }

    /**
     * @return the fechaModificacion
     */
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    /**
     * @param fechaModificacion the fechaModificacion to set
     */
    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    /**
     * @return the fechaRegistro
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @return the idAreaSede
     */
    public Integer getIdAreaSede() {
        return idAreaSede;
    }

    /**
     * @param idAreaSede the idAreaSede to set
     */
    public void setIdAreaSede(Integer idAreaSede) {
        this.idAreaSede = idAreaSede;
    }

    /**
     * @return the idPersonalizacionArea
     */
    public Integer getIdPersonalizacionArea() {
        return idPersonalizacionArea;
    }

    /**
     * @param idPersonalizacionArea the idPersonalizacionArea to set
     */
    public void setIdPersonalizacionArea(Integer idPersonalizacionArea) {
        this.idPersonalizacionArea = idPersonalizacionArea;
    }

    /**
     * @return the usuarioRegistro
     */
    public Long getUsuarioRegistro() {
        return usuarioRegistro;
    }

    /**
     * @param usuarioRegistro the usuarioRegistro to set
     */
    public void setUsuarioRegistro(Long usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    /**
     * @return the tblEventoGeneral
     */
    public EventoGeneralDTO getTblEventoGeneral() {
        return tblEventoGeneral;
    }

    /**
     * @param tblEventoGeneral the tblEventoGeneral to set
     */
    public void setTblEventoGeneral(EventoGeneralDTO tblEventoGeneral) {
        this.tblEventoGeneral = tblEventoGeneral;
    }

    public CatalogoComunDTO getCatEstatusReservacion() {
        return catEstatusReservacion;
    }

    public void setCatEstatusReservacion(CatalogoComunDTO catEstatusReservacion) {
        this.catEstatusReservacion = catEstatusReservacion;
    }

}
