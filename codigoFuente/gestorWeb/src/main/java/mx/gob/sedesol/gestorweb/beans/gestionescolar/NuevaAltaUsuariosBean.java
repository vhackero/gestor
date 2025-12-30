package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GrupoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.SelectImportarDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.service.impl.admin.PersonaServiceFacade;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.EventoCapacitacionServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@ManagedBean
@ViewScoped
public class NuevaAltaUsuariosBean extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{personaServiceFacade}")
    private transient PersonaServiceFacade personaServiceFacade;

    @ManagedProperty("#{eventoCapacitacionServiceFacade}")
    private transient EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade;

    private static final Logger LOGGER = Logger.getLogger(NuevaAltaUsuariosBean.class);

    private List<SelectImportarDTO> listaFuentesExternas;
    private List<SelectImportarDTO> listaPlanes;
    private List<SelectImportarDTO> listaSemestres;
    private List<SelectImportarDTO> listaBloques;
    private List<SelectImportarDTO> listaProgramas;
    private List<SelectImportarDTO> listaPeriodos;
    private List<SelectImportarDTO> listaEventos;
    private List<SelectImportarDTO> listaGrupos;

    private String matriculaNuevaAlta;
    private String fuenteExternaSeleccionada;
    private String planSeleccionado;
    private String semestreSeleccionado;
    private String bloqueSeleccionado;
    private String programaSeleccionado;
    private String periodoSeleccionado;
    private String eventoSeleccionado;
    private String grupoSeleccionado;

    @PostConstruct
    public void init() {
        LOGGER.info("Inicializando formulario de nueva alta de usuarios");
        listaFuentesExternas = personaServiceFacade.consultaFuenteExterna();
        listaPlanes = personaServiceFacade.consultaPlanesActivos();
        listaPeriodos = personaServiceFacade.consultaPeriodosInscripcion();

        listaSemestres = new ArrayList<>();
        listaBloques = new ArrayList<>();
        listaProgramas = new ArrayList<>();
        listaEventos = new ArrayList<>();
        listaGrupos = new ArrayList<>();
    }

    public void onPlanChange() {
        semestreSeleccionado = null;
        bloqueSeleccionado = null;
        programaSeleccionado = null;
        periodoSeleccionado = null;
        eventoSeleccionado = null;
        grupoSeleccionado = null;
        listaSemestres = new ArrayList<>();
        listaBloques = new ArrayList<>();
        listaProgramas = new ArrayList<>();
        listaEventos = new ArrayList<>();
        listaGrupos = new ArrayList<>();

        Integer idPlan = parseEntero(planSeleccionado);
        if (ObjectUtils.isNotNull(idPlan)) {
            listaSemestres = personaServiceFacade.consultaSemestresPorPlan(idPlan);
        } else {
            listaSemestres = new ArrayList<>();
        }
    }

    public void onSemestreChange() {
        bloqueSeleccionado = null;
        programaSeleccionado = null;
        eventoSeleccionado = null;
        grupoSeleccionado = null;
        listaBloques = new ArrayList<>();
        listaProgramas = new ArrayList<>();
        listaEventos = new ArrayList<>();
        listaGrupos = new ArrayList<>();

        Integer idSemestre = parseEntero(semestreSeleccionado);
        if (ObjectUtils.isNotNull(idSemestre)) {
            listaBloques = personaServiceFacade.consultaBloquesPorSemestre(idSemestre);
        }
    }

    public void onBloqueChange() {
        programaSeleccionado = null;
        eventoSeleccionado = null;
        grupoSeleccionado = null;
        listaProgramas = new ArrayList<>();
        listaEventos = new ArrayList<>();
        listaGrupos = new ArrayList<>();
        cargarProgramas();
    }

    public void onProgramaChange() {
        eventoSeleccionado = null;
        grupoSeleccionado = null;
        listaEventos = new ArrayList<>();
        listaGrupos = new ArrayList<>();
        cargarEventos();
    }

    public void onPeriodoChange() {
        eventoSeleccionado = null;
        grupoSeleccionado = null;
        listaEventos = new ArrayList<>();
        listaGrupos = new ArrayList<>();
        cargarEventos();
    }

    public void onEventoChange() {
        grupoSeleccionado = null;
        listaGrupos = new ArrayList<>();
        Integer idEvento = parseEntero(eventoSeleccionado);
        if (ObjectUtils.isNotNull(idEvento)) {
            listaGrupos = personaServiceFacade.consultaGruposPorEvento(idEvento);
        }
    }

    public void guardarAlta() {
        if (faltaAlgunCampoObligatorio()) {
            LOGGER.warn("Validación fallida: campos obligatorios incompletos");
            return;
        }

        LOGGER.info(String.format("Iniciando alta de usuario con matrícula %s", matriculaNuevaAlta));
        Optional<Long> idPersona = personaServiceFacade.getPersonaService()
                .obtenerIdPersonaPorMatricula(matriculaNuevaAlta.trim());

        if (!idPersona.isPresent()) {
            agregarMsgError("No se encontró la matrícula ingresada.", null);
            LOGGER.warn("No se encontró la matrícula ingresada");
            return;
        }

        PersonaDTO persona = personaServiceFacade.obtenerPersonaPorId(idPersona.get());
        if (ObjectUtils.isNull(persona)) {
            agregarMsgError("No se encontró la información del usuario.", null);
            LOGGER.warn("No se encontró la información del usuario");
            return;
        }

        Integer idEvento = parseEntero(eventoSeleccionado);
        Integer idGrupo = parseEntero(grupoSeleccionado);
        if (ObjectUtils.isNull(idEvento) || ObjectUtils.isNull(idGrupo)) {
            agregarMsgError("No se pudo recuperar el evento o grupo seleccionado.", null);
            LOGGER.warn("Evento o grupo no seleccionados correctamente");
            return;
        }

        EventoCapacitacionDTO evento = eventoCapacitacionServiceFacade.getEventoCapacitacionService()
                .getEvento(idEvento);
        GrupoDTO grupo = eventoCapacitacionServiceFacade.getGrupoService().buscarGrupoPorId(idGrupo);

        if (ObjectUtils.isNull(evento) || ObjectUtils.isNull(grupo)) {
            agregarMsgError("No se pudo recuperar la información seleccionada.", null);
            LOGGER.warn("No se pudo recuperar la información de evento o grupo");
            return;
        }

        if (yaEstaMatriculado(idEvento, idPersona.get())) {
            agregarMsgError("El usuario ya está matriculado en el evento seleccionado.", null);
            LOGGER.info("El usuario ya está matriculado en el evento seleccionado");
            return;
        }

        grupo.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
        grupo.setFachaActualizacion(new Date());

        ParametroWSMoodleDTO parametroWSMoodleDTO = obtenerParametrosMoodle(evento);
        if (requiereMoodle(evento) && ObjectUtils.isNull(parametroWSMoodleDTO)) {
            agregarMsgError("No se encontró la plataforma para matricular en el LMS.", null);
            LOGGER.error("No se encontró la plataforma para matricular en el LMS");
            return;
        }

        LOGGER.info(String.format("Matriculando usuario %s en evento %s y grupo %s", idPersona.get(), idEvento,
                idGrupo));
        RelGrupoParticipanteDTO participante = eventoCapacitacionServiceFacade.almacenarParticipante(grupo, persona,
                evento, parametroWSMoodleDTO);

        if (ObjectUtils.isNull(participante)) {
            agregarMsgError("No fue posible matricular al usuario.", null);
            LOGGER.error("No fue posible matricular al usuario");
            return;
        }

        agregarMsgInfo("Alta registrada correctamente.", null);
        LOGGER.info("Alta registrada correctamente");
        limpiarFormulario();
    }

    private ParametroWSMoodleDTO obtenerParametrosMoodle(EventoCapacitacionDTO evento) {
        if (ObjectUtils.isNull(evento) || ObjectUtils.isNull(evento.getCatModalidadPlanPrograma())) {
            return null;
        }
        if (requiereMoodle(evento) && ObjectUtils.isNotNull(evento.getIdPlataformaLmsBorrador())) {
            return eventoCapacitacionServiceFacade.getParametroWSMoodleService()
                    .buscarPorId(evento.getIdPlataformaLmsBorrador());
        }
        return null;
    }

    private boolean requiereMoodle(EventoCapacitacionDTO evento) {
        if (ObjectUtils.isNull(evento) || ObjectUtils.isNull(evento.getCatModalidadPlanPrograma())) {
            return false;
        }
        Integer idModalidad = evento.getCatModalidadPlanPrograma().getId();
        return ConstantesGestor.MODALIDAD_LINEA.equals(idModalidad)
                || ConstantesGestor.MODALIDAD_MIXTO.equals(idModalidad);
    }

    private boolean yaEstaMatriculado(Integer idEvento, Long idPersona) {
        List<RelGrupoParticipanteDTO> participantes = eventoCapacitacionServiceFacade.getGrupoParticipanteService()
                .buscarParticipanteEnEvento(idEvento, idPersona);
        return !ObjectUtils.isNullOrEmpty(participantes);
    }

    private boolean faltaAlgunCampoObligatorio() {
        if (esVacio(matriculaNuevaAlta)) {
            agregarMsgError("La matrícula es obligatoria.", null);
            return true;
        }
        if (esVacio(planSeleccionado) || esVacio(semestreSeleccionado) || esVacio(bloqueSeleccionado)
                || esVacio(programaSeleccionado) || esVacio(periodoSeleccionado) || esVacio(eventoSeleccionado)
                || esVacio(grupoSeleccionado)) {
            agregarMsgError("Todos los campos son obligatorios.", null);
            return true;
        }
        return false;
    }

    private boolean esVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    private void limpiarFormulario() {
        matriculaNuevaAlta = null;
        planSeleccionado = null;
        semestreSeleccionado = null;
        bloqueSeleccionado = null;
        programaSeleccionado = null;
        periodoSeleccionado = null;
        eventoSeleccionado = null;
        grupoSeleccionado = null;

        listaSemestres = new ArrayList<>();
        listaBloques = new ArrayList<>();
        listaProgramas = new ArrayList<>();
        listaEventos = new ArrayList<>();
        listaGrupos = new ArrayList<>();
    }

    private void cargarProgramas() {
        programaSeleccionado = null;
        listaProgramas = new ArrayList<>();

        Integer idEje = parseEntero(bloqueSeleccionado);
        if (ObjectUtils.isNotNull(idEje)) {
            listaProgramas = personaServiceFacade.consultaProgramasPorEje(idEje);
        }
    }

    private void cargarEventos() {
        listaEventos = new ArrayList<>();
        listaGrupos = new ArrayList<>();

        Integer idPrograma = parseEntero(programaSeleccionado);
        if (ObjectUtils.isNotNull(idPrograma) && tienePeriodoSeleccionado()) {
            listaEventos = personaServiceFacade.consultaEventosPorPeriodoYPrograma(periodoSeleccionado, idPrograma);
        }
    }

    private boolean tienePeriodoSeleccionado() {
        return periodoSeleccionado != null && !periodoSeleccionado.trim().isEmpty();
    }

    private Integer parseEntero(String valor) {
        try {
            return valor != null && !valor.trim().isEmpty() ? Integer.parseInt(valor) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public List<SelectImportarDTO> getListaFuentesExternas() {
        return listaFuentesExternas;
    }

    public List<SelectImportarDTO> getListaPlanes() {
        return listaPlanes;
    }

    public List<SelectImportarDTO> getListaSemestres() {
        return listaSemestres;
    }

    public List<SelectImportarDTO> getListaBloques() {
        return listaBloques;
    }

    public List<SelectImportarDTO> getListaProgramas() {
        return listaProgramas;
    }

    public List<SelectImportarDTO> getListaPeriodos() {
        return listaPeriodos;
    }

    public List<SelectImportarDTO> getListaEventos() {
        return listaEventos;
    }

    public List<SelectImportarDTO> getListaGrupos() {
        return listaGrupos;
    }

    public String getMatriculaNuevaAlta() {
        return matriculaNuevaAlta;
    }

    public void setMatriculaNuevaAlta(String matriculaNuevaAlta) {
        this.matriculaNuevaAlta = matriculaNuevaAlta;
    }

    public String getFuenteExternaSeleccionada() {
        return fuenteExternaSeleccionada;
    }

    public void setFuenteExternaSeleccionada(String fuenteExternaSeleccionada) {
        this.fuenteExternaSeleccionada = fuenteExternaSeleccionada;
    }

    public String getPlanSeleccionado() {
        return planSeleccionado;
    }

    public void setPlanSeleccionado(String planSeleccionado) {
        this.planSeleccionado = planSeleccionado;
    }

    public String getSemestreSeleccionado() {
        return semestreSeleccionado;
    }

    public void setSemestreSeleccionado(String semestreSeleccionado) {
        this.semestreSeleccionado = semestreSeleccionado;
    }

    public String getBloqueSeleccionado() {
        return bloqueSeleccionado;
    }

    public void setBloqueSeleccionado(String bloqueSeleccionado) {
        this.bloqueSeleccionado = bloqueSeleccionado;
    }

    public String getProgramaSeleccionado() {
        return programaSeleccionado;
    }

    public void setProgramaSeleccionado(String programaSeleccionado) {
        this.programaSeleccionado = programaSeleccionado;
    }

    public String getPeriodoSeleccionado() {
        return periodoSeleccionado;
    }

    public void setPeriodoSeleccionado(String periodoSeleccionado) {
        this.periodoSeleccionado = periodoSeleccionado;
    }

    public String getEventoSeleccionado() {
        return eventoSeleccionado;
    }

    public void setEventoSeleccionado(String eventoSeleccionado) {
        this.eventoSeleccionado = eventoSeleccionado;
    }

    public String getGrupoSeleccionado() {
        return grupoSeleccionado;
    }

    public void setGrupoSeleccionado(String grupoSeleccionado) {
        this.grupoSeleccionado = grupoSeleccionado;
    }

    public void setPersonaServiceFacade(PersonaServiceFacade personaServiceFacade) {
        this.personaServiceFacade = personaServiceFacade;
    }

    public void setEventoCapacitacionServiceFacade(
            EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade) {
        this.eventoCapacitacionServiceFacade = eventoCapacitacionServiceFacade;
    }

}
