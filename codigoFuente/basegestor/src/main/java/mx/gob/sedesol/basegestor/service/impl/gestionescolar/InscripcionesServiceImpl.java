package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.FiltroReenvioCorreoInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.ModificacionInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.ReenvioCorreoInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaNivelEducativo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaParamConsulta;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaTableroResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionParamNueva;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionPlanesProgramas;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionesConsultaResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionesTableroResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoProceso;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;
import mx.gob.sedesol.basegestor.model.repositories.admin.IPersonaRepository;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.IConvocatoriaRepository;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.IinscripcionesRepository;
import mx.gob.sedesol.basegestor.service.gestionescolar.ConvocatoriaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionesService;

/**
 * 
 * 
 * @author ITTIVA
 * 
 */
@Service("inscripcionesService")
public class InscripcionesServiceImpl implements InscripcionesService {

	private static final Logger logger = Logger.getLogger(InscripcionesServiceImpl.class);

	@Autowired
	private IinscripcionesRepository inscripcionesRepository;

	@Autowired
	private IPersonaRepository personaRepository;

	@Override
	public void altaConvocatorias() {

		// iConvocatoriaRepository.altaConvocatorias();

	}

	@Override
	public List<TipoProceso> consultarTipoProceso() {

		List<TipoProceso> lista = inscripcionesRepository.consultarTipoProceso();

		if (lista.isEmpty()) {
			return new ArrayList<TipoProceso>();
		}
		return lista;
	}

	@Override
	public List<TipoProceso> consultarTipoProcesoDisponibles(Integer convocatoriaId) {
		return inscripcionesRepository.consultarTipoProcesoDisponibles(convocatoriaId, LocalDateTime.now());
	}

	@Override
	public List<TipoProceso> consultarNombre(ConvocatoriaParamConsulta tableroParamConsulta) {

		List<TipoProceso> lista = inscripcionesRepository.consultarNombre(tableroParamConsulta);

		if (lista.isEmpty()) {
			return new ArrayList<TipoProceso>();
		}
		return lista;
	}

	@Override
	public List<InscripcionesTableroResumen> consultarTableroResumen(ConvocatoriaParamConsulta tableroParamConsulta) {

		List<InscripcionesTableroResumen> lista = inscripcionesRepository.consultarTableroResumen(tableroParamConsulta);

		if (lista.isEmpty()) {
			return new ArrayList<InscripcionesTableroResumen>();
		}
		return lista;
	}

	@Override
	public void altaInscripciones(InscripcionParamNueva inscripcionParamNueva) {
		if (inscripcionParamNueva != null && (inscripcionParamNueva.getCalveProceso() == null
				|| inscripcionParamNueva.getCalveProceso().trim().isEmpty())) {
			inscripcionParamNueva.setCalveProceso(generarClaveProceso(inscripcionParamNueva.getNombre()));
		}
		inscripcionesRepository.altaInscripcion(inscripcionParamNueva);
	}

	@Override
	public void altaInscripcionesExtra(InscripcionParamNueva inscripcionParamNueva) {
		if (inscripcionParamNueva != null && (inscripcionParamNueva.getCalveProceso() == null
				|| inscripcionParamNueva.getCalveProceso().trim().isEmpty())) {
			inscripcionParamNueva.setCalveProceso(generarClaveProceso(inscripcionParamNueva.getNombre()));
		}
		inscripcionesRepository.altaInscripcionExtra(inscripcionParamNueva);
	}

	@Override
	public List<InscripcionesConsultaResumen> consultarFiltros(ConvocatoriaParamConsulta tableroParamConsulta) {

		List<InscripcionesConsultaResumen> lista = inscripcionesRepository.consultarFiltros(tableroParamConsulta);

		if (lista.isEmpty()) {
			return new ArrayList<InscripcionesConsultaResumen>();
		}
		return lista;
	}

	@Override
	public boolean updateProcesoInscripcion(Long procesoInscripcionId, String nombre, LocalDateTime fechaInicio,
			LocalDateTime fechaFin, int estatus, Long idTipoProceso, Long convocatoriaId) {

		// Actualizar el registro
		inscripcionesRepository.updateProcesoInscripcion(procesoInscripcionId, nombre, fechaInicio, fechaFin, estatus,
				idTipoProceso, convocatoriaId);

		return true;
	}

	@Override
	public void deleteProcesoInscripcion(Long procesoInscripcionId, Long convocatoriaId, String tipoProceso) {

		inscripcionesRepository.deleteProcesoInscripcion(procesoInscripcionId, convocatoriaId, tipoProceso);
	}

	@Override
	public List<Convocatoria> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Convocatoria buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultadoDTO<Convocatoria> guardar(Convocatoria dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultadoDTO<Convocatoria> actualizar(Convocatoria dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultadoDTO<Convocatoria> eliminar(Convocatoria dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultadoDTO<Convocatoria> sonDatosRequeridosValidos(TipoAccion accion, Convocatoria dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TblPlan> consultarPlan(InscripcionParamNueva inscripcionParamNueva) {

		List<TblPlan> lista = inscripcionesRepository.consultarPlan(inscripcionParamNueva);

		if (lista.isEmpty()) {
			return new ArrayList<TblPlan>();
		}
		return lista;
	}

	@Override
	public List<InscripcionPlanesProgramas> consultarPlanPrograma(InscripcionParamNueva inscripcionParamNueva) {
		List<InscripcionPlanesProgramas> lista = inscripcionesRepository.consultarPlanPrograma(inscripcionParamNueva);

		if (lista.isEmpty()) {
			return new ArrayList<InscripcionPlanesProgramas>();
		}
		return lista;
	}
	
	@Override
	public List<InscripcionPlanesProgramas> consultarPlanProgramaPorProceso(Long procesoInscripcionId) {
		List<InscripcionPlanesProgramas> lista = inscripcionesRepository
				.consultarPlanProgramaPorProceso(procesoInscripcionId);
		if (lista == null || lista.isEmpty()) {
			return new ArrayList<>();
		}
		return lista;
	}
	
	@Override
	public void actualizarPlanesProgramasProceso(Long procesoInscripcionId,
			List<InscripcionPlanesProgramas> planesProgramas) {
		inscripcionesRepository.actualizarPlanesProgramasProceso(procesoInscripcionId, planesProgramas);
	}

	@Override
	public List<TblFichaDescriptivaPrograma> consultarPrograma(InscripcionParamNueva inscripcionParamNueva) {
		return null;
	}

	@Override
	public String generarClaveProceso(String nombre) {
		int consecutivo = inscripcionesRepository.obtenerSiguienteConsecutivoProceso();
		return generarClave(nombre, consecutivo);
	}

	private String generarClave(String nombre, int consecutivo) {
		String base = nombre != null ? nombre.trim().toUpperCase() : "";
		String[] partes = base.isEmpty() ? new String[0] : base.split("\\s+");

		String primera = partes.length > 0 ? abreviar(partes[0]) : "PRC";
		String segunda = partes.length > 1 ? abreviar(partes[1]) : "GEN";
		String anio = String.valueOf(java.time.LocalDate.now().getYear());

		return String.format("%s-%s-%s-%d", primera, segunda, anio, consecutivo);
	}

	private String abreviar(String palabra) {
		if (palabra == null || palabra.isEmpty()) {
			return "XXX";
		}
		String normalizada = palabra.replaceAll("[^A-Z0-9]", "").toUpperCase();
		if (normalizada.isEmpty()) {
			normalizada = palabra.toUpperCase();
		}
		return normalizada.length() <= 3 ? normalizada : normalizada.substring(0, 3);
	}

	@Transactional(readOnly = true)
	@Override
	public List<ReenvioCorreoInscripcionDTO> obtenerInscripcionesParaReenvioConPaginacion(
			FiltroReenvioCorreoInscripcionDTO filtro, int first, int pageSize) {
		Long idProcesoInscripcion = filtro.getIdProcesoInscripcion();
		Long estatusEnvio = filtro.getEstatusEnvio();
		Long idPlan = filtro.getIdPlan();
		Long idPrograma = filtro.getIdPrograma();
		String matricula = obtenerMatricula(filtro);

		return inscripcionesRepository.obtenerInscripcionesParaReenvioConPaginacion(idProcesoInscripcion, estatusEnvio,
				idPlan, idPrograma, matricula, first, pageSize);
	}

	private String obtenerMatricula(FiltroReenvioCorreoInscripcionDTO filtro) {
		if (filtro.getMatricula() == null || filtro.getMatricula().trim().isEmpty()) {
			return null;
		}
		return filtro.getMatricula().trim();
	}

	@Transactional(readOnly = true)
	@Override
	public Long contarInscripcionesParaReenvio(FiltroReenvioCorreoInscripcionDTO filtro) {
		Long idProcesoInscripcion = filtro.getIdProcesoInscripcion();
		Long estatusEnvio = filtro.getEstatusEnvio();
		Long idPlan = filtro.getIdPlan();
		Long idPrograma = filtro.getIdPrograma();
		String matricula = obtenerMatricula(filtro);
		return inscripcionesRepository.contarInscripcionesParaReenvio(idProcesoInscripcion, estatusEnvio, idPlan,
				idPrograma, matricula);
	}

	@Override
	public List<ModificacionInscripcionDTO> obtenerMateriasParaModificarInscripcion(Long idPersona,
			Long idProcesoInscripcion) {
		return inscripcionesRepository.obtenerMateriasParaModificarInscripcion(idPersona, idProcesoInscripcion);
	}

	@Transactional
	@Override
	public void eliminarInscripcionesPorIds(List<Long> ids) {
		inscripcionesRepository.eliminarInscripcionesPorIds(ids);
	}

}
