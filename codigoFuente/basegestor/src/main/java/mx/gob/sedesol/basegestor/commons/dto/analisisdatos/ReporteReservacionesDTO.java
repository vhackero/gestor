package mx.gob.sedesol.basegestor.commons.dto.analisisdatos;

import java.text.SimpleDateFormat;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.AreaSedeDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.PersonalizacionAreaDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelSolicitudEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelSolicitudEventoGeneralDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.SolicitudReservacionDTO;

public class ReporteReservacionesDTO {

    private AreaSedeDTO areaSede;
    private PersonalizacionAreaDTO personalizacion;
    private RelSolicitudEventoCapacitacionDTO relSolicitudReservacion;
    private RelSolicitudEventoGeneralDTO relSolicitudResGeneral;

    // propiedades compartidas
    private SolicitudReservacionDTO solicitud;
    private String nombreEvento;
    private String tipoEvento;
    private CatalogoComunDTO estatusReservacion;
    private Date fechaCreacion;
    private Date fechaInicio;
    private Date fechaFinal;

    public String horaInicial() {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        return formatter.format(fechaInicio);
    }

    public String horaFinal() {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        return formatter.format(fechaFinal);
    }

    public AreaSedeDTO getAreaSede() {
        return areaSede;
    }

    public void setAreaSede(AreaSedeDTO areaSede) {
        this.areaSede = areaSede;
    }

    public RelSolicitudEventoCapacitacionDTO getRelSolicitudReservacion() {
        return relSolicitudReservacion;
    }

    public void setRelSolicitudReservacion(RelSolicitudEventoCapacitacionDTO relSolicitudReservacion) {
        this.relSolicitudReservacion = relSolicitudReservacion;
    }

    public PersonalizacionAreaDTO getPersonalizacion() {
        return personalizacion;
    }

    public void setPersonalizacion(PersonalizacionAreaDTO personalizacion) {
        this.personalizacion = personalizacion;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public SolicitudReservacionDTO getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudReservacionDTO solicitud) {
        this.solicitud = solicitud;
    }

    public CatalogoComunDTO getEstatusReservacion() {
        return estatusReservacion;
    }

    public void setEstatusReservacion(CatalogoComunDTO estatusReservacion) {
        this.estatusReservacion = estatusReservacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public RelSolicitudEventoGeneralDTO getRelSolicitudResGeneral() {
        return relSolicitudResGeneral;
    }

    public void setRelSolicitudResGeneral(RelSolicitudEventoGeneralDTO relSolicitudResGeneral) {
        this.relSolicitudResGeneral = relSolicitudResGeneral;
    }

}
