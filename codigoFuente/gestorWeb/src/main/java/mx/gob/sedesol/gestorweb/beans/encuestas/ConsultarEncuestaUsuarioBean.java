package mx.gob.sedesol.gestorweb.beans.encuestas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.RelEncuestaUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.service.encuestas.RelEncuestaUsuarioService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;

@ViewScoped
@ManagedBean(name = "consultarEncuestaUsuarioBean")
public class ConsultarEncuestaUsuarioBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{relEncuestaUsuarioService}")
    private RelEncuestaUsuarioService relEncuestaUsuarioService;

    private RelEncuestaUsuarioDTO relEncuestaUsuarioDTO;

    private List<RelEncuestaUsuarioDTO> listaEncuestaUsuario;

    private Integer idEvento;

    private Integer idTipoEncuesta;

    public ConsultarEncuestaUsuarioBean() {
        relEncuestaUsuarioDTO = new RelEncuestaUsuarioDTO();
        listaEncuestaUsuario = new ArrayList<>();
    }

    @PostConstruct
    public void init() {

        consultarEncuestasUsuario(idEvento, idTipoEncuesta);
    }

    public void consultarEncuestasUsuario(Integer clEvento, Integer idTipoEncuesta) {

        Boolean esConsultaActiva = Boolean.TRUE;

        Long clPersona = getUsuarioEnSession().getIdPersona();
        if (ObjectUtils.isNotNull(clEvento) && ObjectUtils.isNull(idTipoEncuesta)) {
            listaEncuestaUsuario = relEncuestaUsuarioService.consultarEncuestasAsignadas(clEvento, clPersona, esConsultaActiva);
        } else if (ObjectUtils.isNotNull(clEvento) && ObjectUtils.isNotNull(idTipoEncuesta)) {
            listaEncuestaUsuario = relEncuestaUsuarioService.consultarEncuestasAsignadas(clEvento, clPersona, idTipoEncuesta, esConsultaActiva);
        } else {
            listaEncuestaUsuario = relEncuestaUsuarioService.consultarEncuestasAsignadas(clPersona, esConsultaActiva);
        }
    }

    public String navegaConsultarEncuestaUsuario() {

        this.consultarEncuestasUsuario(idEvento, idTipoEncuesta);

        return ConstantesGestorWeb.NAVEGA_ENCUESTAS_PENDIENTES;
    }

    public String navegaResponderEncuesta() {
        getSession().setAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_SELEC, relEncuestaUsuarioDTO);

        return ConstantesGestorWeb.NAVEGA_CONTESTA_ENCUESTA;
    }

    public String fecharLimite(Date fecha, Integer dias) {
        return relEncuestaUsuarioService.fecharLimite(fecha, ConstantesGestorWeb.FECHA_LIMITE_REACCION);
    }

    /**
     * @return the relEncuestaUsuarioService
     */
    public RelEncuestaUsuarioService getRelEncuestaUsuarioService() {
        return relEncuestaUsuarioService;
    }

    /**
     * @param relEncuestaUsuarioService the relEncuestaUsuarioService to set
     */
    public void setRelEncuestaUsuarioService(RelEncuestaUsuarioService relEncuestaUsuarioService) {
        this.relEncuestaUsuarioService = relEncuestaUsuarioService;
    }

    /**
     * @return the relEncuestaUsuarioDTO
     */
    public RelEncuestaUsuarioDTO getRelEncuestaUsuarioDTO() {
        return relEncuestaUsuarioDTO;
    }

    /**
     * @param relEncuestaUsuarioDTO the relEncuestaUsuarioDTO to set
     */
    public void setRelEncuestaUsuarioDTO(RelEncuestaUsuarioDTO relEncuestaUsuarioDTO) {
        this.relEncuestaUsuarioDTO = relEncuestaUsuarioDTO;
    }

    /**
     * @return the listaEncuestaUsuario
     */
    public List<RelEncuestaUsuarioDTO> getListaEncuestaUsuario() {
        return listaEncuestaUsuario;
    }

    /**
     * @param listaEncuestaUsuario the listaEncuestaUsuario to set
     */
    public void setListaEncuestaUsuario(List<RelEncuestaUsuarioDTO> listaEncuestaUsuario) {
        this.listaEncuestaUsuario = listaEncuestaUsuario;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Integer getIdTipoEncuesta() {
        return idTipoEncuesta;
    }

    public void setIdTipoEncuesta(Integer idTipoEncuesta) {
        this.idTipoEncuesta = idTipoEncuesta;
    }

    /**
     * @return the listaEncuestaUsuario
     */
}
