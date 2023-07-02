package mx.gob.sedesol.gestorweb.beans.encuestas;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import mx.gob.sedesol.basegestor.service.encuestas.RelEncuestaEventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.encuestas.RelEncuestaUsuarioService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@ViewScoped
@ManagedBean(name = "asignarEncuestaBean")
public class AsignarEncuestaBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{relEncuestaEventoCapacitacionService}")
    private RelEncuestaEventoCapacitacionService encuestaEventoCapacitacionService;

    @ManagedProperty(value = "#{relEncuestaUsuarioService}")
    private RelEncuestaUsuarioService encuestaUsuarioService;

    private Integer idEvento;
    private Integer idContexto;
    private Integer idGrupo;
    private Long idPersona;
    private Long usuarioModifico;
    //La fecha apertura de la encuesta corresponde a la fecha de termino del evento de capacitacion
    private Date fechaApertura;

    public AsignarEncuestaBean() {

    }

    @PostConstruct
    public void init() {

    }

    public void asignaEncuestaEvento() {
        encuestaEventoCapacitacionService.asignarEncuestas(idEvento, idContexto, usuarioModifico);
    }

    public void asignaEncuestaParticipantes() {
        encuestaUsuarioService.asignarEncuestaParticipantes(idGrupo, fechaApertura, getUsuarioModifico());
    }

    public void asignaEncuestaAlumno() {
        encuestaUsuarioService.asigarEncuestaAlumno(idEvento, idGrupo, idPersona, fechaApertura, usuarioModifico);
    }

    /**
     * @return the encuestaEventoCapacitacionService
     */
    public RelEncuestaEventoCapacitacionService getEncuestaEventoCapacitacionService() {
        return encuestaEventoCapacitacionService;
    }

    /**
     * @param encuestaEventoCapacitacionService the
     * encuestaEventoCapacitacionService to set
     */
    public void setEncuestaEventoCapacitacionService(RelEncuestaEventoCapacitacionService encuestaEventoCapacitacionService) {
        this.encuestaEventoCapacitacionService = encuestaEventoCapacitacionService;
    }

    /**
     * @return the relEncuestaUsuarioService
     */
    public RelEncuestaUsuarioService getRelEncuestaUsuarioService() {
        return getEncuestaUsuarioService();
    }

    /**
     * @param relEncuestaUsuarioService the relEncuestaUsuarioService to set
     */
    public void setRelEncuestaUsuarioService(RelEncuestaUsuarioService encuestaUsuarioService) {
        this.setEncuestaUsuarioService(encuestaUsuarioService);
    }

    /**
     * @return the idEvento
     */
    public Integer getIdEvento() {
        return idEvento;
    }

    /**
     * @param idEvento the idEvento to set
     */
    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    /**
     * @return the idContexto
     */
    public Integer getIdContexto() {
        return idContexto;
    }

    /**
     * @param idContexto the idContexto to set
     */
    public void setIdContexto(Integer idContexto) {
        this.idContexto = idContexto;
    }

    /**
     * @return the idGrupo
     */
    public Integer getIdGrupo() {
        return idGrupo;
    }

    /**
     * @param idGrupo the idGrupo to set
     */
    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    /**
     * @return the idPersona
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * @param idPersona the idPersona to set
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * @return the fechaApertura
     */
    public Date getFechaApertura() {
        return fechaApertura;
    }

    /**
     * @param fechaApertura the fechaApertura to set
     */
    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    /**
     * @return the usuarioModifico
     */
    public Long getUsuarioModifico() {
        return usuarioModifico;
    }

    /**
     * @param usuarioModifico the usuarioModifico to set
     */
    public void setUsuarioModifico(Long usuarioModifico) {
        this.usuarioModifico = usuarioModifico;
    }

    /**
     * @return the encuestaUsuarioService
     */
    public RelEncuestaUsuarioService getEncuestaUsuarioService() {
        return encuestaUsuarioService;
    }

    /**
     * @param encuestaUsuarioService the encuestaUsuarioService to set
     */
    public void setEncuestaUsuarioService(RelEncuestaUsuarioService encuestaUsuarioService) {
        this.encuestaUsuarioService = encuestaUsuarioService;
    }
}
