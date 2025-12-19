package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.FiltroModificacionInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.FiltroReenvioCorreoInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.CatProcesoInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.ConvocatoriaDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.ModificacionInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.ProcesoInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.ConvocatoriaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionesService;
import mx.gob.sedesol.basegestor.service.inscripcion.CatProcesoInscripcionService;
import mx.gob.sedesol.basegestor.service.inscripcion.ProcesoInscripcionService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@ManagedBean
@ViewScoped
public class ModificacionInscripcionBean extends BaseBean {

	private static final long serialVersionUID = -4348522429538060563L;

	private static final Logger logger = Logger.getLogger(ModificacionInscripcionBean.class);

	@ManagedProperty(value = "#{personaService}")
	private PersonaService personaService;

	@ManagedProperty(value = "#{inscripcionesService}")
	private InscripcionesService inscripcionesService;

	@ManagedProperty(value = "#{convocatoriaService}")
	private ConvocatoriaService convocatoriaService;

	@ManagedProperty(value = "#{catProcesoInscripcionService}")
	private CatProcesoInscripcionService catProcesoInscripcionService;

	@ManagedProperty(value = "#{procesoInscripcionService}")
	private ProcesoInscripcionService procesoInscripcionService;

	private FiltroModificacionInscripcionDTO filtroBusqueda;
	private List<ConvocatoriaDTO> convocatorias;
	private List<CatProcesoInscripcionDTO> tiposProcesoInscripcion;
	private List<ProcesoInscripcionDTO> procesosInscripcion;
	private List<ModificacionInscripcionDTO> materias;

	@PostConstruct
	public void inicializarBean() {
		limpiarFiltroBusqueda();
		cargarConvocatorias();
		cargarTiposProcesoInscripcion();
	}

	public void limpiarFiltroBusqueda() {
		materias = new ArrayList<>();
		filtroBusqueda = new FiltroModificacionInscripcionDTO();
	}

	private void cargarConvocatorias() {
		this.convocatorias = convocatoriaService.obtenerConvocatoriasActivas();
	}

	private void cargarTiposProcesoInscripcion() {
		this.tiposProcesoInscripcion = catProcesoInscripcionService.obtenerTiposProcesoInscripcion();
	}

	public void onConvocatoriaChange() {
		cargarProcesosInscripcion();
	}

	public void onTipoProcesoChange() {
		cargarProcesosInscripcion();
	}

	private void cargarProcesosInscripcion() {
		if (faltaSeleccionarConvocatoriaOTipoDeProceso()) {
			limpiarProcesosInscripcion();
			return;
		}
		Long idConvocatoria = obtenerIdConvocatoriaSeleccionada();
		Long idTipoProceso = obtenerIdTipoProcesoSeleccionado();
		this.procesosInscripcion = obtenerProcesosInscripcion(idConvocatoria, idTipoProceso);
	}

	private boolean faltaSeleccionarConvocatoriaOTipoDeProceso() {
		return obtenerIdConvocatoriaSeleccionada() == null || obtenerIdTipoProcesoSeleccionado() == null;
	}

	private void limpiarProcesosInscripcion() {
		this.filtroBusqueda.setIdProcesoInscripcion(null);
		this.procesosInscripcion = new ArrayList<>();
	}

	private Long obtenerIdConvocatoriaSeleccionada() {
		return filtroBusqueda.getIdConvocatoria();
	}

	private Long obtenerIdTipoProcesoSeleccionado() {
		return filtroBusqueda.getIdTipoProceso();
	}

	private List<ProcesoInscripcionDTO> obtenerProcesosInscripcion(Long idConvocatoria, Long idTipoProcesoInscripcion) {
		return procesoInscripcionService.obtenerPorConvocatoriaYTipoProceso(idConvocatoria, idTipoProcesoInscripcion);
	}

	public void buscarInscripcion() {
		Long idPersona = obtenerIdPersona(filtroBusqueda.getMatricula());
		materias = inscripcionesService.obtenerMateriasParaModificarInscripcion(idPersona,
				filtroBusqueda.getIdProcesoInscripcion());
	}

	private Long obtenerIdPersona(String matricula) {
		return personaService.obtenerIdPersonaPorMatricula(matricula).orElse(null);
	}

	public void actualizarInscripcion() {
		List<Long> idsEliminar = seleccionarLasQueNoEstanMarcadas();
		inscripcionesService.eliminarInscripcionesPorIds(idsEliminar);
		eliminarLasQueNoEstanMarcadasTabla();
	}

	private void eliminarLasQueNoEstanMarcadasTabla() {
		materias = materias.stream().filter(m -> m.isChecked()).collect(Collectors.toList());
	}

	private List<Long> seleccionarLasQueNoEstanMarcadas() {
		return materias.stream().filter(m -> !m.isChecked()).map(m -> m.getIdInscripciones())
				.collect(Collectors.toList());
	}

	public void eliminarTodaInscripcion() {
		List<Long> idsEliminar = obtenerIdsTodasMaterias();
		inscripcionesService.eliminarInscripcionesPorIds(idsEliminar);
		eliminarTodasMateriasMostradasTabla();
	}

	private void eliminarTodasMateriasMostradasTabla() {
		materias = new ArrayList<>();
	}
	

	private List<Long> obtenerIdsTodasMaterias() {
		return materias.stream().map(m -> m.getIdInscripciones()).collect(Collectors.toList());
	}

	public InscripcionesService getInscripcionesService() {
		return inscripcionesService;
	}

	public void setInscripcionesService(InscripcionesService inscripcionesService) {
		this.inscripcionesService = inscripcionesService;
	}

	public ConvocatoriaService getConvocatoriaService() {
		return convocatoriaService;
	}

	public void setConvocatoriaService(ConvocatoriaService convocatoriaService) {
		this.convocatoriaService = convocatoriaService;
	}

	public CatProcesoInscripcionService getCatProcesoInscripcionService() {
		return catProcesoInscripcionService;
	}

	public void setCatProcesoInscripcionService(CatProcesoInscripcionService catProcesoInscripcionService) {
		this.catProcesoInscripcionService = catProcesoInscripcionService;
	}

	public ProcesoInscripcionService getProcesoInscripcionService() {
		return procesoInscripcionService;
	}

	public void setProcesoInscripcionService(ProcesoInscripcionService procesoInscripcionService) {
		this.procesoInscripcionService = procesoInscripcionService;
	}

	public FiltroModificacionInscripcionDTO getFiltroBusqueda() {
		return filtroBusqueda;
	}

	public void setFiltroBusqueda(FiltroModificacionInscripcionDTO filtroBusqueda) {
		this.filtroBusqueda = filtroBusqueda;
	}

	public List<ConvocatoriaDTO> getConvocatorias() {
		return convocatorias;
	}

	public void setConvocatorias(List<ConvocatoriaDTO> convocatorias) {
		this.convocatorias = convocatorias;
	}

	public List<CatProcesoInscripcionDTO> getTiposProcesoInscripcion() {
		return tiposProcesoInscripcion;
	}

	public void setTiposProcesoInscripcion(List<CatProcesoInscripcionDTO> tiposProcesoInscripcion) {
		this.tiposProcesoInscripcion = tiposProcesoInscripcion;
	}

	public List<ProcesoInscripcionDTO> getProcesosInscripcion() {
		return procesosInscripcion;
	}

	public void setProcesosInscripcion(List<ProcesoInscripcionDTO> procesosInscripcion) {
		this.procesosInscripcion = procesosInscripcion;
	}

	public List<ModificacionInscripcionDTO> getMaterias() {
		return materias;
	}

	public void setMaterias(List<ModificacionInscripcionDTO> materias) {
		this.materias = materias;
	}

	public PersonaService getPersonaService() {
		return personaService;
	}

	public void setPersonaService(PersonaService personaService) {
		this.personaService = personaService;
	}

}
