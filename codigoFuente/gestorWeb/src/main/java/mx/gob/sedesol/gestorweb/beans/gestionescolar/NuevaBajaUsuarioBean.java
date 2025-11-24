package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@ManagedBean
@ViewScoped
public class NuevaBajaUsuarioBean extends BaseBean implements Serializable {

    private static final long serialVersionUID = -7638621743956327370L;

    private static final Logger LOGGER = Logger.getLogger(NuevaBajaUsuarioBean.class);

    private String matriculaUsuario;
    private Long idTipoBaja;
    private Long idPlan;
    private Long idSemestre;
    private Long idBloque;
    private Long idPrograma;
    private Long idPeriodo;
    private Long idEvento;
    private String motivo;
    private String quienAplica;
    private String numeroSolicitud;

    private List<SelectItem> tiposBaja;
    private List<SelectItem> planes;
    private List<SelectItem> semestres;
    private List<SelectItem> bloques;
    private List<SelectItem> programas;
    private List<SelectItem> periodos;
    private List<SelectItem> eventos;

    private Map<Long, List<SelectItem>> semestresPorPlan;
    private Map<Long, List<SelectItem>> bloquesPorSemestre;
    private Map<Long, List<SelectItem>> programasPorBloque;
    private Map<Long, List<SelectItem>> programasPorSemestre;

    @PostConstruct
    public void init() {
        LOGGER.info("Inicializando formulario de nueva baja de usuario");
        inicializarCatalogos();
        limpiarFormulario();
    }

    public void onPlanChange() {
        semestres = semestresPorPlan.getOrDefault(idPlan, Collections.emptyList());
        idSemestre = null;
        onSemestreChange();
    }

    public void onSemestreChange() {
        bloques = bloquesPorSemestre.getOrDefault(idSemestre, Collections.emptyList());
        idBloque = null;
        actualizarProgramas();
    }

    public void onBloqueChange() {
        actualizarProgramas();
    }

    public void registrarBaja() {
        List<String> errores = new ArrayList<>();

        if (matriculaUsuario == null || matriculaUsuario.trim().isEmpty()) {
            errores.add("La matrícula o usuario es obligatoria");
        }
        if (idTipoBaja == null) {
            errores.add("Seleccione un tipo de baja");
        }
        if (idPlan == null) {
            errores.add("Seleccione un plan");
        }

        if (!errores.isEmpty()) {
            errores.forEach(error -> agregarMsgError(error, null));
            return;
        }

        agregarMsgInfo("Baja registrada correctamente", null);
        limpiarFormulario();
    }

    public void limpiarFormulario() {
        matriculaUsuario = null;
        idTipoBaja = null;
        idPlan = null;
        idSemestre = null;
        idBloque = null;
        idPrograma = null;
        idPeriodo = null;
        idEvento = null;
        motivo = null;
        quienAplica = null;
        numeroSolicitud = null;

        semestres = Collections.emptyList();
        bloques = Collections.emptyList();
        programas = Collections.emptyList();
    }

    private void inicializarCatalogos() {
        tiposBaja = new ArrayList<>();
        tiposBaja.add(new SelectItem(1L, "Baja temporal"));
        tiposBaja.add(new SelectItem(2L, "Baja definitiva"));

        planes = new ArrayList<>();
        planes.add(new SelectItem(101L, "Plan de formación inicial"));
        planes.add(new SelectItem(202L, "Plan de profesionalización"));

        periodos = new ArrayList<>();
        periodos.add(new SelectItem(301L, "2024-1"));
        periodos.add(new SelectItem(302L, "2024-2"));

        eventos = new ArrayList<>();
        eventos.add(new SelectItem(401L, "Evento de inducción"));
        eventos.add(new SelectItem(402L, "Evento de actualización"));

        inicializarMapasDependencias();
    }

    private void inicializarMapasDependencias() {
        semestresPorPlan = new HashMap<>();
        semestresPorPlan.put(101L, crearListaSelectItem(1L, "Semestre 1", 2L, "Semestre 2"));
        semestresPorPlan.put(202L, crearListaSelectItem(3L, "Semestre 1", 4L, "Semestre 2", 5L, "Semestre 3"));

        bloquesPorSemestre = new HashMap<>();
        bloquesPorSemestre.put(1L, crearListaSelectItem(11L, "Bloque A", 12L, "Bloque B"));
        bloquesPorSemestre.put(2L, crearListaSelectItem(13L, "Bloque C"));
        bloquesPorSemestre.put(3L, crearListaSelectItem(14L, "Bloque inicial"));

        programasPorBloque = new HashMap<>();
        programasPorBloque.put(11L, crearListaSelectItem(1011L, "Programa de introducción"));
        programasPorBloque.put(12L, crearListaSelectItem(1012L, "Programa avanzado"));
        programasPorBloque.put(14L, crearListaSelectItem(1013L, "Programa base"));

        programasPorSemestre = new HashMap<>();
        programasPorSemestre.put(2L, crearListaSelectItem(2011L, "Programa por semestre"));
        programasPorSemestre.put(5L, crearListaSelectItem(2012L, "Programa optativo"));
    }

    private List<SelectItem> crearListaSelectItem(Object... datos) {
        List<SelectItem> items = new ArrayList<>();
        for (int i = 0; i < datos.length - 1; i += 2) {
            items.add(new SelectItem(datos[i], String.valueOf(datos[i + 1])));
        }
        return items;
    }

    private void actualizarProgramas() {
        if (idBloque != null && programasPorBloque.containsKey(idBloque)) {
            programas = programasPorBloque.get(idBloque);
            return;
        }

        if (idSemestre != null && programasPorSemestre.containsKey(idSemestre)) {
            programas = programasPorSemestre.get(idSemestre);
        } else {
            programas = Collections.emptyList();
        }

        idPrograma = null;
    }

    public String getMatriculaUsuario() {
        return matriculaUsuario;
    }

    public void setMatriculaUsuario(String matriculaUsuario) {
        this.matriculaUsuario = matriculaUsuario;
    }

    public Long getIdTipoBaja() {
        return idTipoBaja;
    }

    public void setIdTipoBaja(Long idTipoBaja) {
        this.idTipoBaja = idTipoBaja;
    }

    public Long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Long idPlan) {
        this.idPlan = idPlan;
    }

    public Long getIdSemestre() {
        return idSemestre;
    }

    public void setIdSemestre(Long idSemestre) {
        this.idSemestre = idSemestre;
    }

    public Long getIdBloque() {
        return idBloque;
    }

    public void setIdBloque(Long idBloque) {
        this.idBloque = idBloque;
    }

    public Long getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(Long idPrograma) {
        this.idPrograma = idPrograma;
    }

    public Long getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Long idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getQuienAplica() {
        return quienAplica;
    }

    public void setQuienAplica(String quienAplica) {
        this.quienAplica = quienAplica;
    }

    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public List<SelectItem> getTiposBaja() {
        return tiposBaja;
    }

    public void setTiposBaja(List<SelectItem> tiposBaja) {
        this.tiposBaja = tiposBaja;
    }

    public List<SelectItem> getPlanes() {
        return planes;
    }

    public void setPlanes(List<SelectItem> planes) {
        this.planes = planes;
    }

    public List<SelectItem> getSemestres() {
        return semestres;
    }

    public void setSemestres(List<SelectItem> semestres) {
        this.semestres = semestres;
    }

    public List<SelectItem> getBloques() {
        return bloques;
    }

    public void setBloques(List<SelectItem> bloques) {
        this.bloques = bloques;
    }

    public List<SelectItem> getProgramas() {
        return programas;
    }

    public void setProgramas(List<SelectItem> programas) {
        this.programas = programas;
    }

    public List<SelectItem> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<SelectItem> periodos) {
        this.periodos = periodos;
    }

    public List<SelectItem> getEventos() {
        return eventos;
    }

    public void setEventos(List<SelectItem> eventos) {
        this.eventos = eventos;
    }
}
