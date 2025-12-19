package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.SelectImportarDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.service.impl.admin.PersonaServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@ManagedBean
@ViewScoped
public class NuevaAltaUsuariosBean extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{personaServiceFacade}")
    private transient PersonaServiceFacade personaServiceFacade;

    private List<SelectImportarDTO> listaFuentesExternas;
    private List<SelectImportarDTO> listaPlanes;
    private List<SelectImportarDTO> listaSemestres;
    private List<SelectImportarDTO> listaBloques;
    private List<SelectImportarDTO> listaProgramas;
    private List<SelectImportarDTO> listaPeriodos;
    private List<SelectImportarDTO> listaEventos;
    private List<SelectImportarDTO> listaGrupos;

    private String fuenteExternaSeleccionada;
    private String planSeleccionado;
    private String semestreSeleccionado;
    private String bloqueSeleccionado;
    private String programaSeleccionado;
    private String periodoSeleccionado;
    private String eventoSeleccionado;

    @PostConstruct
    public void init() {
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
        eventoSeleccionado = null;
        listaSemestres.clear();
        listaBloques.clear();
        listaProgramas.clear();
        listaEventos.clear();
        listaGrupos.clear();

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
        listaProgramas.clear();
        listaEventos.clear();
        listaGrupos.clear();

        Integer idSemestre = parseEntero(semestreSeleccionado);
        if (ObjectUtils.isNotNull(idSemestre)) {
            listaBloques = personaServiceFacade.consultaBloquesPorSemestre(idSemestre);
        } else {
            listaBloques = new ArrayList<>();
        }
        cargarProgramas();
    }

    public void onBloqueChange() {
        programaSeleccionado = null;
        eventoSeleccionado = null;
        listaEventos.clear();
        listaGrupos.clear();
        cargarProgramas();
    }

    public void onProgramaChange() {
        eventoSeleccionado = null;
        listaGrupos.clear();
        cargarEventos();
    }

    public void onPeriodoChange() {
        eventoSeleccionado = null;
        listaEventos.clear();
        listaGrupos.clear();
        cargarEventos();
    }

    public void onEventoChange() {
        listaGrupos.clear();
        Integer idEvento = parseEntero(eventoSeleccionado);
        if (ObjectUtils.isNotNull(idEvento)) {
            listaGrupos = personaServiceFacade.consultaGruposPorEvento(idEvento);
        }
    }

    private void cargarProgramas() {
        programaSeleccionado = null;
        listaProgramas.clear();

        Integer idEje = parseEntero(bloqueSeleccionado);
        if (ObjectUtils.isNotNull(idEje)) {
            listaProgramas = personaServiceFacade.consultaProgramasPorEje(idEje);
        }
    }

    private void cargarEventos() {
        listaEventos.clear();
        listaGrupos.clear();

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

    public void setPersonaServiceFacade(PersonaServiceFacade personaServiceFacade) {
        this.personaServiceFacade = personaServiceFacade;
    }
}
