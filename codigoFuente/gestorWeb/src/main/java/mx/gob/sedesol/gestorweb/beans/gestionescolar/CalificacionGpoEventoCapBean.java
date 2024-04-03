package mx.gob.sedesol.gestorweb.beans.gestionescolar;

<<<<<<< HEAD
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
=======
>>>>>>> local
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

<<<<<<< HEAD
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
=======
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
>>>>>>> local

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenciaAuxDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CalificacionECDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CalificacionRecordDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CatAsistenciaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GrupoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelAsistenciaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelDiasEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelEvaluacionCalificacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoEvaluacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelPersonaPlataformaMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReponsableProduccionEcDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TablaCalificacionesDTO;
import mx.gob.sedesol.basegestor.commons.utils.DictamenCalificacionEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoCalificacionECEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatDictamen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatTipoCalificacionEc;
import mx.gob.sedesol.basegestor.service.ServiceException;
import mx.gob.sedesol.basegestor.service.encuestas.RelEncuestaUsuarioService;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.AsistenciaFacadeService;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.EventoCapacitacionServiceFacade;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Calificaciones;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Elemntos;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Grado;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.ObtenerCalificacionesGrupo;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.acceso.MenuGestorBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.beans.administracion.CorreoNotificacionBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.utils.ModalidadEnum;
<<<<<<< HEAD
import mx.gob.sedesol.gestorweb.commons.utils.ReporteUtil;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
=======
>>>>>>> local

@SessionScoped
@ManagedBean
public class CalificacionGpoEventoCapBean extends BaseBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(CalificacionGpoEventoCapBean.class);

    @ManagedProperty(value = "#{menuGestorBean}")
    private MenuGestorBean menuGestorBean;

    @ManagedProperty(value = "#{eventoCapacitacionServiceFacade}")
    private EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade;

    @ManagedProperty(value = "#{asistenciaFacadeService}")
    private AsistenciaFacadeService asistenciaFacadeService;

    private EventoCapacitacionDTO evento;
    private List<GrupoDTO> gruposXEventoCap;
    private GrupoDTO grupoSelec;

    @ManagedProperty(value = "#{eventoCapacitacionBean}")
    private EventoCapacitacionBean eventoCapacitacionBean;

    @ManagedProperty("#{bitacoraBean}")
    private BitacoraBean bitacoraBean;

    @ManagedProperty("#{correoNotificacionBean}")
    private CorreoNotificacionBean correoNotificacionBean;

    @ManagedProperty("#{relEncuestaUsuarioService}")
    private RelEncuestaUsuarioService relEncuestaUsuarioService;

    private Integer duracionEvento;
    private Integer numEvaluaciones;
    private List<CatalogoComunDTO> tposCalificacionEc;
    private CatalogoComunDTO tpoCalifSel;

    private List<CatalogoComunDTO> catDictamenes;

    private ModalidadEnum modalidadEnum;

    private List<RelGrupoParticipanteDTO> participantesByGrupo;
    private List<TablaCalificacionesDTO> tablaAuxCalif;

    // private List<TablaCalificacionesDTO> unmodifTblAuxCalif;
    private List<CalificacionECDTO> calificaciones;

    private boolean muestraTblCalif;
    private boolean grupoSeleccionado;
    private boolean cerrarActa;
    private boolean conCalifPrevias;
    private List<RelGrupoEvaluacionDTO> evaluacionesGpo;
    private boolean modLineaMixto;

    private List<RelDiasEventoCapacitacionDTO> diasEvento;
    private List<CatAsistenciaDTO> catAsistencia;
    private List<RelAsistenciaDTO> asistencias;

    List<RelGrupoParticipanteDTO> asistenciasPart;
    private TablaCalificacionesDTO participanteCalSel;

    private boolean modalidadEnLinea;
    private String escalaMin;
    private String escalaMax;

    public CalificacionGpoEventoCapBean() {
        gruposXEventoCap = new ArrayList<>();
        tpoCalifSel = new CatalogoComunDTO();
        tablaAuxCalif = new ArrayList<>();
        asistenciasPart = new ArrayList<>();
    }

    // @PostConstruct
    public String inicializaDatosCalif() {

        gruposXEventoCap = new ArrayList<>();
        tpoCalifSel = new CatalogoComunDTO();
        tablaAuxCalif = new ArrayList<>();
        asistenciasPart = new ArrayList<>();

        grupoSelec = new GrupoDTO();
        grupoSelec.setIdGrupo(0);
        setMuestraTblCalif(Boolean.FALSE);
        setGrupoSeleccionado(Boolean.FALSE);
        setNumEvaluaciones(0);
        tablaAuxCalif = new ArrayList<>();

        // escalas = generaEscalasCalificaciones();
        gruposXEventoCap = eventoCapacitacionServiceFacade.getGrupoService().getGruposByEvento(evento.getIdEvento());
        for (GrupoDTO grupoDTO : gruposXEventoCap) {
            grupoDTO.setNumAlumnosMatriculados(eventoCapacitacionServiceFacade.getGrupoParticipanteService()
                    .getParticipantesByGrupo(grupoDTO.getIdGrupo()).size());
        }

        List<ReponsableProduccionEcDTO> respProd = asistenciaFacadeService.getRelReponsableProduccionEcService()
                .getResponsableDelEvento(evento.getIdEvento());
        if (!ObjectUtils.isNullOrEmpty(respProd)) {
            evento.setResponsableCoordinadorAcademico(asistenciaFacadeService.getRelReponsableProduccionEcService()
                    .getResponsableDelEvento(evento.getIdEvento()).get(0));
        }

        eventoCapacitacionBean.especificaModeloDeCompetencias(evento.getFichaDescriptivaPrograma());
        evento.setFechaLimiteCierre(new Date());

        eventoCapacitacionBean.setEventoSelec(evento);
        escalaMin = "0";
        escalaMax = "10";

        modalidadEnum = ModalidadEnum.obtieneModalidadById(evento.getIdEvento());
        return ConstantesGestorWeb.NAVEGA_CALIFICACIONES_XEVENTO;
    }

    public String obtenerNombreDictamen(Integer idDictamen) {
        for (CatalogoComunDTO dictamen : catDictamenes) {
            if (dictamen.getId().equals(idDictamen)) {
                return dictamen.getNombre();
            }
        }
        return "";
    }

    public void abrirDialogValidar() {
        RequestContext.getCurrentInstance().execute("PF('dlgCerrarActa').show()");
    }
<<<<<<< HEAD

=======
>>>>>>> local

    /**
     * @param e
     */
    public void onChangeGpoEC() {

        Integer idGpo = grupoSelec.getIdGrupo();
        if (!ObjectUtils.isNullOrCero(idGpo)) {

            if (grupoTieneMatriculados(idGpo)) {

                setModLineaMixto(Boolean.FALSE);
                setModalidadEnLinea(Boolean.FALSE);

                setGrupoSeleccionado(Boolean.TRUE);
                setCerrarActa(Boolean.FALSE);
                setTablaAuxCalif(new ArrayList<>());
                setNumEvaluaciones(0);

                catDictamenes = eventoCapacitacionServiceFacade.getCatDictamenService().findAll(CatDictamen.class);

                grupoSelec = eventoCapacitacionServiceFacade.getGrupoService().buscarGrupoPorId(idGpo);

                if (ObjectUtils.isNotNull(grupoSelec)) {

                    setCerrarActa(grupoSelec.isActaCerrada());
                    participantesByGrupo = eventoCapacitacionServiceFacade.getRegistroAsistenciaService()
                            .getGrupoParticipante(grupoSelec.getIdEventoTemp(), idGpo);
                    grupoSelec.setNumAlumnosMatriculados(participantesByGrupo.size());
                    asistenciaFacadeService.getRegistroAsistenciaService()
                            .calcularPorcentajeAsistencia(participantesByGrupo);

                    List<RelDiasEventoCapacitacionDTO> lstAux = eventoCapacitacionServiceFacade
                            .getRelDiasEventoCapacitacionService().getDiasEventoByGrupo(grupoSelec.getIdGrupo());
                    if (ObjectUtils.isNotNull(lstAux)) {
                        duracionEvento = lstAux.size();
                    }

                    modalidadEnum = ModalidadEnum.obtieneModalidadById(evento.getCatModalidadPlanPrograma().getId());
                    // Consulta si existen registros de evaluacion asociados al
                    // gpo
                    evaluacionesGpo = eventoCapacitacionServiceFacade.getRelGpoEvaluacionService()
                            .obtieneEvaluacionesPorIdGrupo(idGpo);
                    if (!ObjectUtils.isNullOrEmpty(evaluacionesGpo)) {
                        tablaAuxCalif = generaTblCalifGpoEvaluaciones(evaluacionesGpo);

                        List<RelGrupoParticipanteDTO> gpoPart = new ArrayList<>();
                        for (RelGrupoEvaluacionDTO eval : evaluacionesGpo) {
                            for (RelEvaluacionCalificacionDTO cal : eval.getRelEvaluacionCalificaciones()) {
                                gpoPart.add(cal.getRelGrupoParticipante());
                            }
                        }

                        setMuestraTblCalif(Boolean.TRUE);
                        setConCalifPrevias(Boolean.TRUE);

                    } else {
                        // En caso de no exisitir Registros
                        if (!ObjectUtils.isNullOrEmpty(participantesByGrupo)) {
                            setConCalifPrevias(Boolean.FALSE);
                        }
                    }

                    try {
                        switch (modalidadEnum) {

                            case ENLINEA:
                                // obtieneTiposCalificacionPromSuma();
                                setModLineaMixto(Boolean.TRUE);
                                setModalidadEnLinea(Boolean.TRUE);
                                break;

                            case PRESENCIAL:
                                setModalidadEnLinea(Boolean.FALSE);
                                tposCalificacionEc = eventoCapacitacionServiceFacade.getCatTipoCalificacionService()
                                        .findAll(CatTipoCalificacionEc.class);
                                break;

                            case MIXTO:
                                // setModalidadEnLinea(Boolean.FALSE);
                                // obtieneTiposCalificacionPromSuma();
                                setModLineaMixto(Boolean.TRUE);
                                break;

                            default:
                                break;
                        }

                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }

            } else {
                grupoSelec.setNumAlumnosMatriculados(0);
                tablaAuxCalif = new ArrayList<>();
                setMuestraTblCalif(Boolean.FALSE);
                setGrupoSeleccionado(Boolean.FALSE);
            }
        } else if (idGpo.equals(0)) {
            tablaAuxCalif = new ArrayList<>();
            setMuestraTblCalif(Boolean.FALSE);
            setGrupoSeleccionado(Boolean.FALSE);
        }
    }

    public boolean grupoTieneMatriculados(Integer idGrupo) {
        List<RelGrupoParticipanteDTO> rgp = eventoCapacitacionServiceFacade.getGrupoParticipanteService()
                .getParticipantesByGrupo(idGrupo);
        if (rgp.isEmpty() || ObjectUtils.isNull(rgp)) {
            return false;
        }

        return true;
    }

    /**
     * @param evaluacionesGpo
     * @return
     */
    private List<TablaCalificacionesDTO> generaTblCalifGpoEvaluaciones(List<RelGrupoEvaluacionDTO> evaluacionesGpo) {

        // Se genera el Array de Calificaciones
        this.calificaciones = new ArrayList<CalificacionECDTO>();
        for (RelGrupoEvaluacionDTO eg : evaluacionesGpo) {
            CalificacionECDTO evalCal = new CalificacionECDTO();
            evalCal.setNombreEvaluacion(eg.getNombreEvaluacion());
            evalCal.setPonderacion(eg.getPonderacion());
            this.calificaciones.add(evalCal);
        }
        setNumEvaluaciones(this.calificaciones.size());

        // Se genera tabla de participantes primeramente
        List<TablaCalificacionesDTO> aux = new ArrayList<>();
        int i = 1;
        for (RelEvaluacionCalificacionDTO eval : evaluacionesGpo.get(ConstantesGestorWeb.INDICE_INICIAL)
                .getRelEvaluacionCalificaciones()) {

            if (!containsPersona(eval.getRelGrupoParticipante().getPersona().getIdPersona(), aux)) {

                TablaCalificacionesDTO auxCal = new TablaCalificacionesDTO();
                auxCal.setDictamen(eval.getDictamen() != null ? eval.getDictamen() : new CatalogoComunDTO());
                auxCal.setParticipante(eval.getRelGrupoParticipante().getPersona());
                auxCal.setConsecutivo(i);

                RelGrupoParticipanteDTO rgp = this.obtieneGpoParticipanteByIdPersona(
                        eval.getRelGrupoParticipante().getPersona().getIdPersona(), participantesByGrupo);
                if (ObjectUtils.isNotNull(rgp)) {

                    if (ObjectUtils.isNullOrCero(rgp.getPorcentajeAsistencia())) {
                        auxCal.setConAsistencia(Boolean.FALSE);
                    } else {
                        auxCal.setAsistencia(rgp.getPorcentajeAsistencia().toString());
                        auxCal.setConAsistencia(Boolean.TRUE);
                    }

                }
                aux.add(auxCal);
                i++;
            }
        }

        // Se Asignan las calificaciones de acuerdo al participante
        for (RelGrupoEvaluacionDTO eg : evaluacionesGpo) {

            // this.tpoCalifSel = eg.getCatTipoCalificacionEc();
            for (RelEvaluacionCalificacionDTO ec : eg.getRelEvaluacionCalificaciones()) {
                TablaCalificacionesDTO row = this
                        .obtieneTCByIdPersona(ec.getRelGrupoParticipante().getPersona().getIdPersona(), aux);

                if (ObjectUtils.isNotNull(row)) {

                    row.agregaCalificacion(ec.getCalificacion().doubleValue(), ec.getNombreEvaluacion());

                    // Promedio
                    if (ObjectUtils.isNull(row.getCalifTotal())) {
                        row.setCalifTotal(new Double(0));
                    }

                    row.setCalifTotal(row.getCalifTotal() + ec.getCalificacion().doubleValue());

                }
            }
        }

        // Se genera la calificacion final
        this.calculaCalificacionesGpo(aux, this.evento);

        return aux;
    }

    /**
     * @param persona
     * @param calificaciones
     * @return
     */
    public boolean containsPersona(Long idPersona, List<TablaCalificacionesDTO> calificaciones) {
        for (TablaCalificacionesDTO cal : calificaciones) {
            if (cal.getParticipante().getIdPersona().equals(idPersona)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene un registro de la tabla de calificaciones
     *
     * @param idPersona
     * @param evaluacionesGpo
     * @return
     */
    private TablaCalificacionesDTO obtieneTCByIdPersona(Long idPersona, List<TablaCalificacionesDTO> persona) {

        for (TablaCalificacionesDTO tc : persona) {
            if (tc.getParticipante().getIdPersona().equals(idPersona)) {
                return tc;
            }
        }
        return null;
    }

    /**
     *
     */

    public void generaTablaCalifGrupo() {
        numEvaluaciones++;
        if (!ObjectUtils.isNullOrCero(numEvaluaciones) && !ObjectUtils.isNullOrEmpty(participantesByGrupo)) {

            if (tablaAuxCalif.isEmpty()) {
                this.calificaciones = new ArrayList<CalificacionECDTO>();
                for (int x = 1; x <= numEvaluaciones; x++) {
                    CalificacionECDTO evalCal = new CalificacionECDTO();
                    this.calificaciones.add(evalCal);
                }

                // tablaAuxCalif = new ArrayList<>();
                int i = 1;
                for (RelGrupoParticipanteDTO relGpoPart : participantesByGrupo) {

                    TablaCalificacionesDTO obj = new TablaCalificacionesDTO(numEvaluaciones);
                    obj.setConsecutivo(i);
                    obj.setParticipante(relGpoPart.getPersona());

                    if (ObjectUtils.isNullOrCero(relGpoPart.getPorcentajeAsistencia())) {
                        obj.setAsistencia("0");
                        obj.setConAsistencia(Boolean.FALSE);
                    } else {
                        obj.setAsistencia(relGpoPart.getPorcentajeAsistencia().toString());
                        obj.setConAsistencia(Boolean.TRUE);
                    }

                    obj.setIdGpoParticipante(relGpoPart.getId());
                    tablaAuxCalif.add(obj);
                    i++;
                }

            } else {
                // Se agrega Columna dinamica en componente dataTable
                for (int x = calificaciones.size(); x < numEvaluaciones; x++) {
                    CalificacionECDTO evalCal = new CalificacionECDTO();
                    evalCal.setCalificacion(0D);
                    this.calificaciones.add(evalCal);
                }

                // Se actualiza array de calificaciones
                for (TablaCalificacionesDTO auxCal : tablaAuxCalif) {
                    auxCal.actualizaArrayCalificaciones(numEvaluaciones);
                }
            }

        }
        setMuestraTblCalif(Boolean.TRUE);
    }

    /**
     *
     */
    public void guardaCalifBorrador() {

        try {

            if (isConCalifPrevias()) {

                ResultadoDTO<RelGrupoEvaluacionDTO> resTx = eventoCapacitacionServiceFacade
                        .actualizaCalificacionesECPresencial(calificaciones, grupoSelec, evento, isCerrarActa(),
                                evaluacionesGpo, participantesByGrupo, tablaAuxCalif,
                                getUsuarioEnSession().getIdPersona());

                if (ObjectUtils.isNotNull(resTx) && resTx.getResultado().getValor()) {
                    bitacoraBean.guardarBitacora(idPersonaEnSesion(), "REG_CAL", "", requestActual(),
                            TipoServicioEnum.LOCAL);
                    agregarMsgInfo("Actualizacion correcta de calificaciones", null);
                } else {
                    agregarMsgError("Ocurrio un error al guardar calificaciones", null);
                }

            } else {

                ResultadoDTO<RelGrupoEvaluacionDTO> resTx = eventoCapacitacionServiceFacade
                        .guardaCalificacionesECPresencial(calificaciones, grupoSelec, evento,
                                getUsuarioEnSession().getIdPersona(), isCerrarActa(), participantesByGrupo,
                                tablaAuxCalif);

                if (ObjectUtils.isNotNull(resTx) && resTx.getResultado().getValor()) {
                    bitacoraBean.guardarBitacora(idPersonaEnSesion(), "REG_CAL", "", requestActual(),
                            TipoServicioEnum.LOCAL);
                    agregarMsgInfo("guardado Correcto de calificaciones", null);
                } else {
                    agregarMsgError("Ocurrio un error al guardar calificaciones", null);
                }

            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    /**
     *
     */
    public boolean faltaLlenadoDeCalificaciones() {
        // Valida todas las calificaciones llenas
        boolean bnd = Boolean.FALSE;
        if (!ObjectUtils.isNullOrEmpty(tablaAuxCalif)) {
            for (TablaCalificacionesDTO ta : tablaAuxCalif) {

                if (!ObjectUtils.isNullOrEmpty(ta.getCalificacionEC())) {
                    for (CalificacionECDTO cal : ta.getCalificacionEC()) {
                        if (evento.getTpoCalificacion().equals(TipoCalificacionECEnum.TPO_CAL_PROMEDIO.getId())
                                && ObjectUtils.isNullOrCero(cal.getCalificacion())) {
                            bnd = Boolean.TRUE;
                            agregarMsgWarn("No se han asignado todas las calificaciones", null);
                            break;
                        }
                        if (evento.getTpoCalificacion().equals(TipoCalificacionECEnum.TPO_CAL_SUMA.getId())
                                && ObjectUtils.isNullOrCero(cal.getCalifPonderacion())) {
                            bnd = Boolean.TRUE;
                            agregarMsgWarn("No se han asignado todas las calificaciones", null);
                            break;
                        }
                    }
                } else {
                    agregarMsgWarn("No se han asignado todas las calificaciones", null);
                    bnd = Boolean.TRUE;
                    break;
                }
            }
        }

        return bnd;
    }

    /**
     * @return
     */
    public boolean isGrupoSinAsistencia() {

        boolean bnd = Boolean.FALSE;
        if (!ObjectUtils.isNullOrEmpty(tablaAuxCalif)) {
            for (TablaCalificacionesDTO ta : tablaAuxCalif) {
                if (ObjectUtils.isNullOrEmpty(ta.getAsistencia()) || ObjectUtils.isNullOrCero(ta.getAsistencia())) {
                    agregarMsgWarn("Existen participantes sin registro de asistencias", null);
                    bnd = Boolean.TRUE;
                    break;
                }
            }
        }

        return bnd;
    }

    /**
     * @return
     */
    public boolean faltaLlenadoDictamen() {
        boolean bnd = Boolean.FALSE;
        if (!ObjectUtils.isNullOrEmpty(tablaAuxCalif)) {
            for (TablaCalificacionesDTO ta : tablaAuxCalif) {
                if (ObjectUtils.isNull(ta.getDictamen()) || ObjectUtils.isNull(ta.getDictamen().getId())) {
                    bnd = Boolean.TRUE;
                    agregarMsgWarn("No se han asignado todos los dictamentes", null);
                    break;
                }
            }
        }

        return bnd;
    }

    /**
     * @param calificaciones
     * @return
     */
    private CalificacionECDTO getNextObjCalificacionVacio(List<CalificacionECDTO> calificaciones) {

        if (!ObjectUtils.isNullOrEmpty(calificaciones)) {
            for (CalificacionECDTO cl : calificaciones) {
                if (!ObjectUtils.isNullOrCero(cl.getCalificacion()) && cl.getNombreEvaluacion().isEmpty()) {
                    return cl;
                }
            }
        }
        return null;

    }

    /**
     * @param idTabla
     */
    public void calculaCalFinalParticipante(TablaCalificacionesDTO partCalSel, String nombreEval) {

        participanteCalSel = partCalSel;
        TipoCalificacionECEnum tpoCalifEnum = TipoCalificacionECEnum
                .getTipoCalificacionById(evento.getTpoCalificacion());

        if (ObjectUtils.isNotNull(tpoCalifEnum)) {

            switch (tpoCalifEnum) {

                case TPO_CAL_PROMEDIO:

                    if (!ObjectUtils.isNullOrEmpty(tablaAuxCalif)) {
                        for (TablaCalificacionesDTO ta : tablaAuxCalif) {
                            if (participanteCalSel.getConsecutivo().equals(ta.getConsecutivo())) {

                                // Lenado de acuerdo a las evaluaciones
                                CalificacionECDTO califObj = getNextObjCalificacionVacio(ta.getCalificacionEC());
                                if (ObjectUtils.isNotNull(califObj)) {
                                    califObj.setNombreEvaluacion(nombreEval);
                                }

                                // Promedio
                                Double suma = 0D;
                                for (CalificacionECDTO cal : ta.getCalificacionEC()) {
                                    if (!ObjectUtils.isNullOrCero(cal.getCalificacion())) {
                                        suma = suma + cal.getCalificacion();
                                    }
                                }
                                double prom = suma / ta.getCalificacionEC().size();
                                ta.setCalifTotal(prom);
                                break;

                            }
                        }
                    }

                    break;

                case TPO_CAL_SUMA:

                    if (!ObjectUtils.isNullOrEmpty(tablaAuxCalif)) {
                        for (TablaCalificacionesDTO ta : tablaAuxCalif) {
                            if (participanteCalSel.getConsecutivo().equals(ta.getConsecutivo())) {

                                // Lenado de acuerdo a las evaluaciones
                                CalificacionECDTO califObj = getNextObjCalificacionVacio(ta.getCalificacionEC());
                                if (ObjectUtils.isNotNull(califObj)) {
                                    califObj.setNombreEvaluacion(nombreEval);
                                }

                                Double suma = 0D;
                                for (CalificacionECDTO cal : ta.getCalificacionEC()) {
                                    if (!ObjectUtils.isNullOrCero(cal.getCalifPonderacion())) {
                                        suma = suma + cal.getCalifPonderacion();
                                    }
                                }
                                ta.setCalifTotal(suma.doubleValue());
                                break;
                            }
                        }
                    }
                    break;

                // case TPO_CAL_CUANTITATIVA:
                // break;
                default:
                    break;
            }

            // TODO: POR TIPO DICTAMEN
            participanteCalSel.setCalifFinal(this.calculaCalificacionFinal(participanteCalSel.getCalifTotal(),
                    new Long(participanteCalSel.getAsistencia()), evento).doubleValue());
        }
    }

    /**
     *
     */
    public void cierraActaGrupoEC() {
        if (validaCerrarActa()) {
            setCerrarActa(Boolean.TRUE);
            guardaCalifBorrador();
            grupoSelec.setActaCerrada(true);
            try {
                if (ObjectUtils.isNotNull(grupoSelec.getEvento())) {
                    if (ObjectUtils.isNotNull(grupoSelec.getEvento().getAplicaEncuesta())) {
                        if (grupoSelec.getEvento().getAplicaEncuesta().booleanValue() == true) {
                            relEncuestaUsuarioService.asignarEncuestaParticipantes(grupoSelec.getIdGrupo(),
                                    grupoSelec.getEvento().getFechaFinal(), getUsuarioEnSession().getIdPersona());
                            /*Asignando encuestas por defecto*/
                            relEncuestaUsuarioService.asignarEncuestasPorDefecto(grupoSelec.getIdGrupo(),
                                    grupoSelec.getEvento().getFechaFinal(), getUsuarioEnSession().getIdPersona());

                        }
                    }

                }
            } catch (Exception e) {
                log.info("No fue posible asignar las encuestas a los participantes.");
                agregarMsgError("No fue posible asignar las encuestas a los participantes.", null);
            }

            bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CER_ACT", String.valueOf(grupoSelec.getIdGrupo()),
                    requestActual(), TipoServicioEnum.LOCAL);
            // TODO Enviar notificacion y correo a los usuarios cuando se cierra
            // el acta

            String claveNotificacion = ConstantesGestorWeb.CLAVE_NOTIFICACION_AL_CERRAR_ACTA;
            String claveCorreo = ConstantesGestorWeb.CLAVE_CORREO_AL_CERRAR_ACTA;
            correoNotificacionBean.notificarUsuariosActaCerrada(claveNotificacion, claveCorreo, grupoSelec);

            RequestContext.getCurrentInstance().execute("PF('dlgCerrarActa').hide()");
        } else {
            setCerrarActa(Boolean.FALSE);
        }
    }

    /**
     *
     */
    public boolean validaCerrarActa() {
        EventoCapacitacionDTO evento = grupoSelec.getEvento();
        if (evento.getCatModalidadPlanPrograma().getId().equals(ConstantesGestor.MODALIDAD_LINEA)) {
            return true;
        } else if (!faltaLlenadoDeCalificaciones() && !faltaLlenadoDictamen() && !isGrupoSinAsistencia()) {
            return true;
        } else {
            return false;
        }
    }

    public String navegaBusqEventosCap() {
        return menuGestorBean.navegaBusquedaEventosCapacitacion();
    }

    /**
     * Consume WebService
     */
    public void obtieneCalifMoodleWS() {

        tablaAuxCalif = new ArrayList<>();

        if (ObjectUtils.isNull(evento.getTpoCalificacion())) {
            agregarMsgInfo("Debes seleccionar el tipo de calificación", null);
            return;
        }

        try {
            ParametroWSMoodleDTO plataforma = eventoCapacitacionServiceFacade.getParametroWSMoodleService()
                    .obtenerMoodleActivo();
            ObtenerCalificacionesGrupo wsConsCalif = new ObtenerCalificacionesGrupo(plataforma);
            Calificaciones calMdlGpo = wsConsCalif.obtenerCalificacionesporGrupo(evento.getIdCursoLmsBorrador(),
                    grupoSelec.getIdMoodle());
            modalidadEnum = ModalidadEnum.obtieneModalidadById(evento.getCatModalidadPlanPrograma().getId());

            switch (modalidadEnum) {

                case ENLINEA:
                    this.generaDatosCalificacionesMoodle(calMdlGpo, plataforma);
                    setModalidadEnLinea(Boolean.TRUE);
                    break;

                case PRESENCIAL:
                    setModalidadEnLinea(Boolean.FALSE);
                    break;

                case MIXTO:
                    this.generaDatosCalificacionesMoodle(calMdlGpo, plataforma);
                    setModLineaMixto(Boolean.TRUE);
                    setModalidadEnLinea(Boolean.FALSE);
                    break;
            }

            // Se genera calificacion final
            this.calculaCalificacionesGpo(tablaAuxCalif, this.evento);
            setMuestraTblCalif(Boolean.TRUE);
        } catch (ErrorWS e) {
            log.error(e.getMessage(), e);
        }

    }

    /**
     * @param calMdlGpo
     * @param plataforma
     */
    private void generaDatosCalificacionesMoodle(Calificaciones calMdlGpo, ParametroWSMoodleDTO plataforma) {

        if (ObjectUtils.isNullOrEmpty(tablaAuxCalif)) {
            tablaAuxCalif = new ArrayList<>();
        }
        List<TablaCalificacionesDTO> participantesMoodle = new ArrayList<>();
            participantesByGrupo = eventoCapacitacionServiceFacade.getRegistroAsistenciaService()
                    .getGrupoParticipante(grupoSelec.getIdEventoTemp(), grupoSelec.getIdGrupo());
            if (ObjectUtils.isNotNull(calMdlGpo)) {
                // Se genera Array de evaluaciones
                this.calificaciones = new ArrayList<CalificacionECDTO>();
                int numEval = 1;
                for (Elemntos eval : calMdlGpo.getItems()) {
                    if (!eval.getActivityid().equals(ConstantesGestorWeb.MOODLE_ACTIVITY_COURSE)) {
                        CalificacionECDTO evalCal = new CalificacionECDTO();
                        evalCal.setNombreEvaluacion(eval.getName());
                        setCerrarActa(false);
                        this.calificaciones.add(evalCal);
                        setNumEvaluaciones(numEval++);
                    }
                }
                // Obtiene participantes
                if (!ObjectUtils.isNullOrEmpty(calMdlGpo.getItems()) && !ObjectUtils
                        .isNullOrEmpty(calMdlGpo.getItems().get(ConstantesGestorWeb.INDICE_INICIAL).getGrades())) {

                    int i = 1;
                        for (Grado g : calMdlGpo.getItems().get(ConstantesGestorWeb.INDICE_INICIAL).getGrades()) {
                            try {
                            RelPersonaPlataformaMoodleDTO personaMoodle = eventoCapacitacionServiceFacade
                                    .getRelPersonaPlataformaMoodle()
                                    .obtenerPersonaPlataformaMoodle(g.getUserid(), plataforma.getIdParametroWSMoodle());

                            if (!containsPersona(personaMoodle.getIdPersona(), participantesMoodle)) {
                                TablaCalificacionesDTO auxCal = new TablaCalificacionesDTO();
                                auxCal.setParticipante(eventoCapacitacionServiceFacade.getPersonaService()
                                        .buscarPorId(personaMoodle.getIdPersona()));
                                auxCal.setConsecutivo(i);
                                auxCal.setIdPersonaMoodle(personaMoodle.getIdPersonaMoodle());
                                participantesMoodle.add(auxCal);
                                i++;
                            }
                            } catch (Exception e) {
                                PersonaDTO pers = eventoCapacitacionServiceFacade.getPersonaService().buscarPorId((long) g.getUserid());
                                agregarMsgWarn("No se encontro al alumno "
                                        + pers.getNombreCompleto()
                                        + " registrado en el Ambiente Virtual de Aprendizaje", e.getMessage());
                            }
                        }
                }

                // Asignacion de Calificaciones
                for (TablaCalificacionesDTO evalCal : participantesMoodle) {
                    List<CalificacionECDTO> califsPart = obtieneCalificacionesByIdPersonaMdl(evalCal.getIdPersonaMoodle(),
                            calMdlGpo);
                    if (!ObjectUtils.isNullOrEmpty(califsPart)) {
                        evalCal.setCalificacionEC(califsPart);
                        evalCal.setDictamen(new CatalogoComunDTO());
                        evalCal.setCalifTotal(this.obtieneSumaCalificaciones(califsPart));
                        evalCal.setTpoEvaluacion(ConstantesGestorWeb.TPO_CALIFICACION_PROMEDIO);

                        RelGrupoParticipanteDTO gpoPart = obtieneGpoParticipanteByIdPersona(
                                evalCal.getParticipante().getIdPersona(), participantesByGrupo);
                        if (ObjectUtils.isNotNull(gpoPart)) {
                            evalCal.setIdGpoParticipante(gpoPart.getId());

                            if (ObjectUtils.isNullOrCero(gpoPart.getPorcentajeAsistencia())) {
                                evalCal.setConAsistencia(Boolean.FALSE);
                                evalCal.setAsistencia("0");
                            } else {
                                evalCal.setAsistencia(gpoPart.getPorcentajeAsistencia().toString());
                                evalCal.setConAsistencia(Boolean.TRUE);
                            }
                            evalCal.setParticipante(gpoPart.getPersona());
                        }
                    } else {
                        agregarMsgWarn("No se encontro calificaciones del alumno "
                                + evalCal.getParticipante().getNombreCompleto()
                                + " en el Ambiente Virtual de Aprendizaje", null);
                    }
                }
            }
        // Se Genera Calif final
        this.calculaCalificacionesGpo(participantesMoodle, this.evento);

        tablaAuxCalif.addAll(participantesMoodle);

    }

    /**
     * @param nombreEval
     * @param idPersonaMdl
     * @param calMdlGpo
     * @return
     */
    private List<CalificacionECDTO> obtieneCalificacionesByIdPersonaMdl(Integer idPersonaMdl,
                                                                        Calificaciones calMdlGpo) {

        List<CalificacionECDTO> califPart = new ArrayList<>();
        for (Elemntos elm : calMdlGpo.getItems()) {
            if (!elm.getActivityid().equals(ConstantesGestorWeb.MOODLE_ACTIVITY_COURSE)) {
                for (Grado g : elm.getGrades()) {
                    if (idPersonaMdl.equals(g.getUserid())) {

                        // TODO: Base 10
                        CalificacionECDTO cal = new CalificacionECDTO();

                        cal.setNombreEvaluacion(elm.getName());
                        if (!ObjectUtils.isNullOrCero(g.getGrade())) {
                            cal.setCalificacion(new Double(g.getGrade() / 10));
                            if (evento.getTpoCalificacion().equals(TipoCalificacionECEnum.TPO_CAL_SUMA.getId())) {
                                cal.setCalifPonderacion(g.getGrade() / 10);
                            }
                        }
                        califPart.add(cal);
                    }
                }
            }
        }
        return califPart;
    }

    /**
     * @param calificaciones
     * @return
     */
    private Double obtieneSumaCalificaciones(List<CalificacionECDTO> calificaciones) {
        double sum = new Double(ConstantesGestorWeb.NUMERO_CERO);
        for (CalificacionECDTO cal : calificaciones) {
            sum = sum + cal.getCalificacion();
        }
        return sum;
    }

    /**
     *
     */
    public void visualizaAsistenciaUsuario(Integer idGpoParticipante) {

        // participanteCalSel.toString();
        if (ObjectUtils.isNotNull(idGpoParticipante)) {

            List<Integer> aux = new ArrayList<>();
            asistenciasPart = new ArrayList<>();

            aux.add(idGpoParticipante);
            this.catAsistencia = asistenciaFacadeService.getCatAsistencia(grupoSelec.getIdEventoTemp());
            this.diasEvento = asistenciaFacadeService.getDiasEventoByGrupo(grupoSelec.getIdGrupo());
            this.asistencias = asistenciaFacadeService.getAsistenciaByIdGrupoParticipante(aux);

            RelGrupoParticipanteDTO part = obtieneRelGpoParticipanteById(idGpoParticipante, participantesByGrupo);
            asistenciasPart.add(part);

            asistenciaFacadeService.crearArregloAsistenciasXparticipante(this.diasEvento, asistenciasPart,
                    this.asistencias);

            RequestContext.getCurrentInstance().execute("PF('dlgAsistPart').show()");
        }
    }

    /**
     * @param idGpoPart
     * @param aux
     * @return
     */
    private RelGrupoParticipanteDTO obtieneRelGpoParticipanteById(Integer idGpoPart,
                                                                  List<RelGrupoParticipanteDTO> aux) {
        for (RelGrupoParticipanteDTO gp : aux) {
            if (idGpoPart.equals(gp.getId())) {
                return gp;
            }
        }
        return null;
    }

    /**
     * @param e
     */
    public void onChangeAsistencia(ValueChangeEvent e) {
        Integer idTipoAsistencia = (Integer) e.getNewValue();

        CatAsistenciaDTO asistValor = getAsistencia(idTipoAsistencia);
        String fechaEvento = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
                .get("fechaEvento");

        if (!ObjectUtils.isNullOrEmpty(fechaEvento) && ObjectUtils.isNotNull(asistValor)) {

            for (AsistenciaAuxDTO asist : asistenciasPart.get(ConstantesGestorWeb.INDICE_INICIAL).getAsistencias()) {
                if (asist.getFechaEventoFormat().equals(fechaEvento)) {
                    asist.setIdtipoAsistencia(asistValor.getId());
                    asist.setTipoAsistencia(asistValor.getNombre());
                    asist.setValor(asistValor.getValor());
                    break;
                }
            }
        }
        // Se calcula el porcentaje
        asistenciaFacadeService.getRegistroAsistenciaService().calcularPorcentajeAsistencia(asistenciasPart);
    }

    /**
     * @param idTipoAsistencia
     * @return
     */
    private CatAsistenciaDTO getAsistencia(Integer idTipoAsistencia) {
        for (CatAsistenciaDTO as : catAsistencia) {
            if (idTipoAsistencia.equals(as.getId())) {
                return as;
            }
        }
        return null;
    }

    /**
     *
     */
    public void guardaAsistenciasPersona() {
        ResultadoDTO res = null;
        try {

            res = asistenciaFacadeService.guardarRelAsistencia(this.getUsuarioEnSession().getIdPersona(),
                    asistenciasPart);
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                participantesByGrupo = eventoCapacitacionServiceFacade.getRegistroAsistenciaService()
                        .getGrupoParticipante(grupoSelec.getIdEventoTemp(), grupoSelec.getIdGrupo());

                for (TablaCalificacionesDTO cp : tablaAuxCalif) {
                    if (cp.getParticipante().getIdPersona()
                            .equals(participanteCalSel.getParticipante().getIdPersona())) {
                        RelGrupoParticipanteDTO calPart = obtieneGpoParticipanteByIdPersona(
                                cp.getParticipante().getIdPersona(), participantesByGrupo);
                        cp.setAsistencia(calPart.getPorcentajeAsistencia().toString());
                        cp.setConAsistencia(Boolean.TRUE);
                    }

                }
                agregarMsgInfo("Se guardaron los cambios en la asistencia", null);
                // Se genera Calificacion final
                this.calculaCalificacionesGpo(tablaAuxCalif, this.evento);

            } else {
                agregarMsgError("Ocurrio un error al guardar los datos", null);
            }
        } catch (ServiceException e) {
            log.error(e.getMessage());
        }

    }

    /**
     * @param idPersona
     * @param partByGrupo
     * @return
     */
    private RelGrupoParticipanteDTO obtieneGpoParticipanteByIdPersona(Long idPersona,
                                                                      List<RelGrupoParticipanteDTO> partByGrupo) {

        for (RelGrupoParticipanteDTO gp : partByGrupo) {
            if (idPersona.equals(gp.getPersona().getIdPersona())) {
                return gp;
            }
        }
        return null;
    }

    public String navegaAsistenciasDelGrupo() {
        return "/views/private/gestionEscolar/registroAsistenciaEventoCap.xhtml?faces-redirect=true&&idEvento="
                + evento.getIdEvento();
    }

    /**
     * @param califTotal
     * @param asistencia
     * @return
     */
    private Double calculaCalificacionFinal(Double califTotal, Long asistencia, EventoCapacitacionDTO evento) {

        Double porcentajeCalTot = 0D;
        Double porcentajeAsist = 0D;
        Double califFinal = 0D;

        if (!ObjectUtils.isNullOrCero(evento.getValorCalificacionDictamen())) {

            porcentajeCalTot = (califTotal * evento.getValorCalificacionDictamen()) / 10;
        }

        if (!ObjectUtils.isNullOrCero(evento.getValorAsistenciaDictamen())) {
            Double asistb10 = (asistencia.doubleValue() / 10);
            porcentajeAsist = (asistb10 * evento.getValorAsistenciaDictamen()) / 10;
        }

        if (evento.getTpoDictamen().equals(TipoCalificacionECEnum.TPO_CAL_SUMA.getId())) {
            califFinal = (porcentajeCalTot + porcentajeAsist);
        } else if (evento.getTpoDictamen().equals(TipoCalificacionECEnum.TPO_CAL_PROMEDIO.getId())) {

            if (evento.getCatModalidadPlanPrograma().getId().equals(ConstantesGestorWeb.MODALIDAD_EN_LINEA)) {
                califFinal = califTotal;
            } else {
                califFinal = porcentajeCalTot + porcentajeAsist;
            }

        }

        return califFinal;
    }

    /**
     * @param ponderacion
     * @param partCalSel
     */
    public void calculaPonderacionCal(Integer ponderacion, Integer indexTbl, String nombreEval) {

        for (TablaCalificacionesDTO ta : tablaAuxCalif) {
            for (int i = 0; i < ta.getCalificacionEC().size(); i++) {
                // Integer pond = mapaPonderacionMdl.get(indexTbl);
                if (indexTbl == i && !ObjectUtils.isNullOrCero(ponderacion)) {
                    Double calPond = (ta.getCalificacionEC().get(i).getCalifPonderacion() * ponderacion) / 100;
                    ta.getCalificacionEC().get(i).setCalifPonderacion(calPond);
                }
            }
        }

        // Se genera Calificacion final
        this.calculaCalificacionesGpo(tablaAuxCalif, this.evento);

    }

    /**
     * @param aux
     * @param evento
     */
    private void calculaCalificacionesGpo(List<TablaCalificacionesDTO> aux, EventoCapacitacionDTO evento) {
        // Calculo de calificaciones Totales y Finales
        try {
            for (TablaCalificacionesDTO tc : aux) {
                if (evento.getTpoCalificacion().equals(TipoCalificacionECEnum.TPO_CAL_SUMA.getId())) {
                    Double sum = new Double(0);
                    for (CalificacionECDTO cal : tc.getCalificacionEC()) {
                        if (!cal.getCalificacion().isNaN())
                            sum = sum + cal.getCalifPonderacion();
                    }
                    tc.setCalifTotal(sum);

                } else if (evento.getTpoCalificacion().equals(TipoCalificacionECEnum.TPO_CAL_PROMEDIO.getId())) {

                    /*
                     * Double sum = new Double(0); for (CalificacionECDTO cal :
                     * tc.getCalificacionEC()) { log.info("Calif ponderacion: " +
                     * cal.getCalifPonderacion()); log.info("Suma: " + sum); sum =
                     * sum + cal.getCalifPonderacion(); } tc.setCalifTotal(sum /
                     * tc.getCalificacionEC().size());
                     */

                    Double sum = new Double(0);
                    for (int i = 0; i < tc.getCalificacionEC().size(); i++) {
                        if (!tc.getCalificacionEC().get(i).getCalificacion().isNaN())
                            sum = sum + tc.getCalificacionEC().get(i).getCalificacion();
                        log.info("Calif ponderacion: " + tc.getCalificacionEC().get(i).getCalifPonderacion());
                        log.info("Calificiacion normal: " + tc.getCalificacionEC().get(i).getCalificacion());
                        log.info("Suma: " + sum);
                    }
                    tc.setCalifTotal(sum / tc.getCalificacionEC().size());
                }
                log.info("Calificacion total: " + tc.getCalifTotal());
                log.info("Asistencia" + tc.getAsistencia());
                if (ObjectUtils.isNull(tc.getAsistencia()))
                    tc.setAsistencia("0");
                tc.setCalifFinal(this.calculaCalificacionFinal(tc.getCalifTotal(), new Long(tc.getAsistencia()), evento)
                        .doubleValue());
                log.info("Calificacion final: " + tc.getCalifFinal());
            }

            // Se establece el dictamen

            for (TablaCalificacionesDTO tc : aux) {
                if (!ObjectUtils.isNullOrEmpty(evento.getCalificacionMinAprobatoria())) {

                    Double califMin = new Double(evento.getCalificacionMinAprobatoria());
                    califMin = (califMin / 10);

                    if (tc.getCalifFinal().compareTo(califMin) >= 0) {
                        tc.setDictamen(obtieneDictamenById(DictamenCalificacionEnum.ACREDITADO.getId()));
                    } else if (tc.getCalifFinal().compareTo(califMin) <= 0) {
                        tc.setDictamen(obtieneDictamenById(DictamenCalificacionEnum.NO_ACREDITADO.getId()));
                    } else {
                        tc.setDictamen(obtieneDictamenById(DictamenCalificacionEnum.EN_PROCESO.getId()));
                    }
                }
            }
        } catch (Exception e) {
            agregarMsgError("No se encontro calificaciones en el Ambiente Virtual de Aprendizaje", e.getMessage());
        }
    }

    /*
     *
     */
    private CatalogoComunDTO obtieneDictamenById(Integer idDictamen) {
        if (!ObjectUtils.isNullOrEmpty(catDictamenes)) {
            for (CatalogoComunDTO dict : catDictamenes) {
                if (dict.getId().equals(idDictamen)) {
                    return dict;
                }
            }
        }
        return null;
    }

    /* SETTERS & GETTERS */

    /**
     * @return the eventoCapacitacionServiceFacade
     */
    public EventoCapacitacionServiceFacade getEventoCapacitacionServiceFacade() {
        return eventoCapacitacionServiceFacade;
    }

    /**
     * @param eventoCapacitacionServiceFacade the eventoCapacitacionServiceFacade to set
     */
    public void setEventoCapacitacionServiceFacade(EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade) {
        this.eventoCapacitacionServiceFacade = eventoCapacitacionServiceFacade;
    }

    /**
     * @return the evento
     */
    public EventoCapacitacionDTO getEvento() {
        return evento;
    }

    /**
     * @param evento the evento to set
     */
    public void setEvento(EventoCapacitacionDTO evento) {
        this.evento = evento;
    }

    /**
     * @return the grupoSelec
     */
    public GrupoDTO getGrupoSelec() {
        return grupoSelec;
    }

    /**
     * @param grupoSelec the grupoSelec to set
     */
    public void setGrupoSelec(GrupoDTO grupoSelec) {
        this.grupoSelec = grupoSelec;
    }

    /**
     * @return the gruposXEventoCap
     */
    public List<GrupoDTO> getGruposXEventoCap() {
        return gruposXEventoCap;
    }

    /**
     * @param gruposXEventoCap the gruposXEventoCap to set
     */
    public void setGruposXEventoCap(List<GrupoDTO> gruposXEventoCap) {
        this.gruposXEventoCap = gruposXEventoCap;
    }

    /**
     * @return the eventoCapacitacionBean
     */
    public EventoCapacitacionBean getEventoCapacitacionBean() {
        return eventoCapacitacionBean;
    }

    /**
     * @param eventoCapacitacionBean the eventoCapacitacionBean to set
     */
    public void setEventoCapacitacionBean(EventoCapacitacionBean eventoCapacitacionBean) {
        this.eventoCapacitacionBean = eventoCapacitacionBean;
    }

    /**
     * @return the duracionEvento
     */
    public Integer getDuracionEvento() {
        return duracionEvento;
    }

    /**
     * @param duracionEvento the duracionEvento to set
     */
    public void setDuracionEvento(Integer duracionEvento) {
        this.duracionEvento = duracionEvento;
    }

    /**
     * @return the numEvaluaciones
     */
    public Integer getNumEvaluaciones() {
        return numEvaluaciones;
    }

    /**
     * @param numEvaluaciones the numEvaluaciones to set
     */
    public void setNumEvaluaciones(Integer numEvaluaciones) {
        this.numEvaluaciones = numEvaluaciones;
    }

    /**
     * @return the tposCalificacionEc
     */
    public List<CatalogoComunDTO> getTposCalificacionEc() {
        return tposCalificacionEc;
    }

    /**
     * @param tposCalificacionEc the tposCalificacionEc to set
     */
    public void setTposCalificacionEc(List<CatalogoComunDTO> tposCalificacionEc) {
        this.tposCalificacionEc = tposCalificacionEc;
    }

    /**
     * @return the tpoCalifSel
     */
    public CatalogoComunDTO getTpoCalifSel() {
        return tpoCalifSel;
    }

    /**
     * @param tpoCalifSel the tpoCalifSel to set
     */
    public void setTpoCalifSel(CatalogoComunDTO tpoCalifSel) {
        this.tpoCalifSel = tpoCalifSel;
    }

    /**
     * @return the grupoSeleccionado
     */
    public boolean isGrupoSeleccionado() {
        return grupoSeleccionado;
    }

    /**
     * @param grupoSeleccionado the grupoSeleccionado to set
     */
    public void setGrupoSeleccionado(boolean grupoSeleccionado) {
        this.grupoSeleccionado = grupoSeleccionado;
    }

    /**
     * @return the participantesByGrupo
     */
    public List<RelGrupoParticipanteDTO> getParticipantesByGrupo() {
        return participantesByGrupo;
    }

    /**
     * @param participantesByGrupo the participantesByGrupo to set
     */
    public void setParticipantesByGrupo(List<RelGrupoParticipanteDTO> participantesByGrupo) {
        this.participantesByGrupo = participantesByGrupo;
    }

    /**
     * @return the tablaAuxCalif
     */
    public List<TablaCalificacionesDTO> getTablaAuxCalif() {
        return tablaAuxCalif;
    }

    /**
     * @param tablaAuxCalif the tablaAuxCalif to set
     */
    public void setTablaAuxCalif(List<TablaCalificacionesDTO> tablaAuxCalif) {
        this.tablaAuxCalif = tablaAuxCalif;
    }

    /**
     * @return the muestraTblCalif
     */
    public boolean isMuestraTblCalif() {
        return muestraTblCalif;
    }

    /**
     * @param muestraTblCalif the muestraTblCalif to set
     */
    public void setMuestraTblCalif(boolean muestraTblCalif) {
        this.muestraTblCalif = muestraTblCalif;
    }

    /**
     * @return the calificaciones
     */
    public List<CalificacionECDTO> getCalificaciones() {
        return calificaciones;
    }

    /**
     * @param calificaciones the calificaciones to set
     */
    public void setCalificaciones(List<CalificacionECDTO> calificaciones) {
        this.calificaciones = calificaciones;
    }

    /**
     * @return the catDictamenes
     */
    public List<CatalogoComunDTO> getCatDictamenes() {
        return catDictamenes;
    }

    /**
     * @param catDictamenes the catDictamenes to set
     */
    public void setCatDictamenes(List<CatalogoComunDTO> catDictamenes) {
        this.catDictamenes = catDictamenes;
    }

    /**
     * @return the cerrarActa
     */
    public boolean isCerrarActa() {
        return cerrarActa;
    }

    /**
     * @param cerrarActa the cerrarActa to set
     */
    public void setCerrarActa(boolean cerrarActa) {
        this.cerrarActa = cerrarActa;
    }

    /**
     * @return the conCalifPrevias
     */
    public boolean isConCalifPrevias() {
        return conCalifPrevias;
    }

    /**
     * @param conCalifPrevias the conCalifPrevias to set
     */
    public void setConCalifPrevias(boolean conCalifPrevias) {
        this.conCalifPrevias = conCalifPrevias;
    }

    /**
     * @return the modLineaMixto
     */
    public boolean isModLineaMixto() {
        return modLineaMixto;
    }

    /**
     * @param modLineaMixto the modLineaMixto to set
     */
    public void setModLineaMixto(boolean modLineaMixto) {
        this.modLineaMixto = modLineaMixto;
    }

    /**
     * @return the asistenciaFacadeService
     */
    public AsistenciaFacadeService getAsistenciaFacadeService() {
        return asistenciaFacadeService;
    }

    /**
     * @param asistenciaFacadeService the asistenciaFacadeService to set
     */
    public void setAsistenciaFacadeService(AsistenciaFacadeService asistenciaFacadeService) {
        this.asistenciaFacadeService = asistenciaFacadeService;
    }

    /**
     * @return the diasEvento
     */
    public List<RelDiasEventoCapacitacionDTO> getDiasEvento() {
        return diasEvento;
    }

    /**
     * @param diasEvento the diasEvento to set
     */
    public void setDiasEvento(List<RelDiasEventoCapacitacionDTO> diasEvento) {
        this.diasEvento = diasEvento;
    }

    /**
     * @return the catAsistencia
     */
    public List<CatAsistenciaDTO> getCatAsistencia() {
        return catAsistencia;
    }

    /**
     * @param catAsistencia the catAsistencia to set
     */
    public void setCatAsistencia(List<CatAsistenciaDTO> catAsistencia) {
        this.catAsistencia = catAsistencia;
    }

    /**
     * @return the asistenciasPart
     */
    public List<RelGrupoParticipanteDTO> getAsistenciasPart() {
        return asistenciasPart;
    }

    /**
     * @param asistenciasPart the asistenciasPart to set
     */
    public void setAsistenciasPart(List<RelGrupoParticipanteDTO> asistenciasPart) {
        this.asistenciasPart = asistenciasPart;
    }

    /**
     * @return the participanteCalSel
     */
    public TablaCalificacionesDTO getParticipanteCalSel() {
        return participanteCalSel;
    }

    /**
     * @param participanteCalSel the participanteCalSel to set
     */
    public void setParticipanteCalSel(TablaCalificacionesDTO participanteCalSel) {
        this.participanteCalSel = participanteCalSel;
    }

    /**
     * @return the modalidadEnLinea
     */
    public boolean isModalidadEnLinea() {
        return modalidadEnLinea;
    }

    /**
     * @param modalidadEnLinea the modalidadEnLinea to set
     */
    public void setModalidadEnLinea(boolean modalidadEnLinea) {
        this.modalidadEnLinea = modalidadEnLinea;
    }

    /**
     * @return the asistencias
     */
    public List<RelAsistenciaDTO> getAsistencias() {
        return asistencias;
    }

    /**
     * @param asistencias the asistencias to set
     */
    public void setAsistencias(List<RelAsistenciaDTO> asistencias) {
        this.asistencias = asistencias;
    }

    /**
     * @return the escalaMin
     */
    public String getEscalaMin() {
        return escalaMin;
    }

    /**
     * @param escalaMin the escalaMin to set
     */
    public void setEscalaMin(String escalaMin) {
        this.escalaMin = escalaMin;
    }

    /**
     * @return the escalaMax
     */
    public String getEscalaMax() {
        return escalaMax;
    }

    /**
     * @param escalaMax the escalaMax to set
     */
    public void setEscalaMax(String escalaMax) {
        this.escalaMax = escalaMax;
    }

    public BitacoraBean getBitacoraBean() {
        return bitacoraBean;
    }

    public void setBitacoraBean(BitacoraBean bitacoraBean) {
        this.bitacoraBean = bitacoraBean;
    }

    public MenuGestorBean getMenuGestorBean() {
        return menuGestorBean;
    }

    public void setMenuGestorBean(MenuGestorBean menuGestorBean) {
        this.menuGestorBean = menuGestorBean;
    }

    public CorreoNotificacionBean getCorreoNotificacionBean() {
        return correoNotificacionBean;
    }

    public void setCorreoNotificacionBean(CorreoNotificacionBean correoNotificacionBean) {
        this.correoNotificacionBean = correoNotificacionBean;
    }

    public RelEncuestaUsuarioService getRelEncuestaUsuarioService() {
        return relEncuestaUsuarioService;
    }

    public void setRelEncuestaUsuarioService(RelEncuestaUsuarioService relEncuestaUsuarioService) {
        this.relEncuestaUsuarioService = relEncuestaUsuarioService;
    }

}
