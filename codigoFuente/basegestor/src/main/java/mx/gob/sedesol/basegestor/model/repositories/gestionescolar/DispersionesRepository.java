package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.apache.log4j.Logger;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DispersionGrupoEventoDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionPreEvento;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblAmbienteVirtualAprendizaje;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParam;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParamNuevo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionPlanesProgramas;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ProcesosInscripcion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblGrupo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoMatriculacion;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblDispersionesBusqueda;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;

@Repository
public class DispersionesRepository implements IDispersionesRepository {

	@Autowired
	public EntityManager entityManager;
	
	private static final Logger logger = Logger.getLogger(DispersionesRepository.class);
	
	Integer IdDispercion;
	
	@Transactional
	@Override
	public void altaDisperciones(DispersionesParam dispercionParametros){

		StringBuilder consulta = new StringBuilder("SELECT \r\n"
				+ "    fd.id_plan,\r\n"
				+ "    fd.id_programa,\r\n"
				+ "    COUNT(ti.id) no_estudiantes_inscritos\r\n"
				+ "  FROM rel_proceso_inscipcion_planesyprogramas rpi\r\n"
				+ "         INNER JOIN tbl_procesos_inscripcion tpi ON tpi.proceso_inscripcion_id = rpi.id_proceso_inscripcion\r\n"
				+ "         INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_plan = rpi.id_plan AND fd.id_programa = rpi.id_programa\r\n"
				+ "         INNER JOIN tbl_planes tp ON tp.id_plan = fd.id_plan\r\n"
				+ "         INNER JOIN tbl_malla_curricular tmc ON tmc.id = fd.id_eje_capacitacion\r\n"
				+ "         INNER JOIN tbl_convocatoria tc ON tc.convocatoria_id = tpi.convocatoria_id\r\n"
				+ "         LEFT JOIN tbl_inscripciones ti ON ti.idplan = rpi.id_plan AND ti.idprograma = rpi.id_programa\r\n"
				+ "                AND ti.fecha_registro >= tpi.fecha_inicio AND ti.fecha_registro <= tpi.fecha_fin\r\n"
				+ "WHERE tpi.convocatoria_id = :idConvocatoria AND tpi.id_tipo_proceso = :idTipoProceso\r\n"
				+ "  AND tpi.proceso_inscripcion_id = :idProcesoInscripcion AND tpi.id_categoria_proceso = 1");

		if (dispercionParametros.getIdPlan() != null) {
			consulta.append(" AND fd.id_plan = :idPlan");
		}
		if (dispercionParametros.getIdPrograma() != null) {
			consulta.append(" AND fd.id_programa = :idPrograma");
		}

		consulta.append(" GROUP BY fd.id_plan, fd.id_programa");
		
		String consultaInsert = "INSERT INTO tbl_dispersiones "
				+ "(id_proceso_inscripcion, id_programa, no_total_estudiantes, no_grupos, estudiantes_x_grupo, grupo_resto, estudiantes_resto, tipo_matriculacion, usuario_modifico) "
				+ "VALUES "
				+ "(:idProcesoInscripcion, :idPrograma, :noTotEstudiantes, :noGrupos, :estudiantesGrupo, :grupoResto, :estudianteResto, :tipoMatriculacion, :usuarioModifico)";

		Query queryConsulta = entityManager.createNativeQuery(consulta.toString());
		Query queryInsert = entityManager.createNativeQuery(consultaInsert);
		
		queryConsulta.setParameter("idConvocatoria", dispercionParametros.getIdConvocatoriaSeleccionada());
		queryConsulta.setParameter("idTipoProceso", dispercionParametros.getIdTipoProceso());
		queryConsulta.setParameter("idProcesoInscripcion", dispercionParametros.getIdProcesoInscripcion());

		if (dispercionParametros.getIdPlan() != null) {
			queryConsulta.setParameter("idPlan", dispercionParametros.getIdPlan());
		}
		if (dispercionParametros.getIdPrograma() != null) {
			queryConsulta.setParameter("idPrograma", dispercionParametros.getIdPrograma());
		}

		Integer tipoMatriculacion = dispercionParametros.getIdTipoMatriculacion() != null
				? dispercionParametros.getIdTipoMatriculacion()
				: 0;
		Integer usuarioModifico = dispercionParametros.getIdUsuarioAccion() != null
				? dispercionParametros.getIdUsuarioAccion().intValue()
				: 0;

		List<Object[]> listaQuery = queryConsulta.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

					queryInsert.setParameter("idProcesoInscripcion", dispercionParametros.getIdProcesoInscripcion());
					queryInsert.setParameter("idPrograma", ((Number) obj[1]).intValue());
					queryInsert.setParameter("noTotEstudiantes", ((Number) obj[2]).intValue());
				queryInsert.setParameter("noGrupos", 0);
				queryInsert.setParameter("estudiantesGrupo", 0);
				queryInsert.setParameter("grupoResto", 0);
				queryInsert.setParameter("estudianteResto", 0);
				queryInsert.setParameter("tipoMatriculacion", tipoMatriculacion);
				queryInsert.setParameter("usuarioModifico", usuarioModifico);
				queryInsert.executeUpdate();
			}
		}
	}
	
	@Override
	public boolean validarDispercionExistenteOrdinario(DispersionesParam dispercionParametros){
		if (dispercionParametros.getIdConvocatoriaSeleccionada() == null) {
			return false;
		}
		
		String consulta = "SELECT COUNT(td.id_dispersion) FROM tbl_dispersiones td\r\n"
				+ "INNER JOIN tbl_procesos_inscripcion tpi ON td.id_proceso_inscripcion = tpi.proceso_inscripcion_id\r\n"
				+ "INNER JOIN cat_procesos_inscripcion cpi ON cpi.id_proceso = tpi.id_tipo_proceso\r\n"
				+ "WHERE cpi.id_proceso = 1 AND tpi.convocatoria_id = :convocatoriaId";
		
		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("convocatoriaId", dispercionParametros.getIdConvocatoriaSeleccionada());
		
		Object resultado = query.getSingleResult();
		return resultado != null && ((Number) resultado).intValue() > 0;
	}
	
	
	@Override
	public boolean validarDispercionExistente(DispersionesParam dispercionParametros){
		
		if (dispercionParametros.getIdProcesoInscripcion() == null) {
			return false;
		}
		
		String consulta = "SELECT COUNT(*) FROM tbl_dispersiones WHERE id_proceso_inscripcion = :idProcesoInscripcion";
		
		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("idProcesoInscripcion", dispercionParametros.getIdProcesoInscripcion());

		Object resultado = query.getSingleResult();
		return resultado != null && ((Number) resultado).intValue() > 0;
	}
	
	
	@Transactional
	@Override
	public void borrarDispercsion(TblDispersionesBusqueda tblBusqueda) {

		String consultaBorrarDispersion = "DELETE FROM tbl_dispersiones WHERE id_dispersion = :idDispersion";

		Query query = entityManager.createNativeQuery(consultaBorrarDispersion);
        query.setParameter("idDispersion", tblBusqueda.getIdDispersion());
        query.executeUpdate();

	}

	@Override
	public boolean existeRelDispersionGrupo(Integer idDispersion) {
		if (idDispersion == null) {
			return false;
		}
		String consulta = "SELECT COUNT(*) FROM rel_dispersiones_grupo WHERE id_dispersion = :idDispersion";
		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("idDispersion", idDispersion);
		Object resultado = query.getSingleResult();
		return resultado != null && ((Number) resultado).intValue() > 0;
	}
	
	
	@Override
	public List<TblDispersionesBusqueda> consultaDisperciones(DispersionesParam dispercionParametros){
		
		List<TblDispersionesBusqueda> lista = new ArrayList<TblDispersionesBusqueda>();
		
		StringBuilder consultaBusquedaDispercion = new StringBuilder("	SELECT\r\n"
				+ "    tbd.id_dispersion, rpi.id_plan, tp.nombre plan, fd.id_programa,\r\n"
				+ "    fd.nombre_tentativo programa, fd.identificador_final clave,\r\n"
				+ "    IF((SELECT tmc2.nombre FROM tbl_malla_curricular tmc2 WHERE tmc2.id = tmc.id_padre ) IS NOT NULL, (SELECT tmc2.nombre FROM tbl_malla_curricular tmc2 WHERE tmc2.id = tmc.id_padre ), '')semestre ,\r\n"
				+ "    tmc.nombre bloque,\r\n"
				+ "    COALESCE((SELECT COUNT(ti.id)\r\n"
				+ "                FROM tbl_inscripciones ti\r\n"
				+ "               WHERE ti.idplan = rpi.id_plan\r\n"
				+ "                 AND ti.idprograma = rpi.id_programa\r\n"
				+ "                 AND ti.fecha_registro >= tpi.fecha_inicio\r\n"
				+ "                 AND ti.fecha_registro <= tpi.fecha_fin), tbd.no_total_estudiantes) no_total_estudiantes,\r\n"
				+ "    tbd.no_grupos,\r\n"
				+ "    tbd.estudiantes_x_grupo, tbd.grupo_resto, tbd.estudiantes_resto,\r\n"
				+ "    tbd.tipo_matriculacion,\r\n"
				+ "    COALESCE((SELECT COUNT(DISTINCT rdg.id_grupo) FROM rel_dispersiones_grupo rdg WHERE rdg.id_dispersion = tbd.id_dispersion), 0) grupos_creados,\r\n"
				+ "    COALESCE((SELECT COUNT(*)\r\n"
				+ "               FROM rel_grupo_participante rgp\r\n"
				+ "               INNER JOIN rel_dispersiones_grupo rdg ON rdg.id_grupo = rgp.id_grupo\r\n"
				+ "              WHERE rdg.id_dispersion = tbd.id_dispersion), 0) usuarios_matriculados\r\n"
				+ "\t    FROM tbl_dispersiones tbd\r\n"
				+ "         INNER JOIN tbl_procesos_inscripcion tpi ON tpi.proceso_inscripcion_id =  tbd.id_proceso_inscripcion\r\n"
				+ "         INNER JOIN rel_proceso_inscipcion_planesyprogramas rpi ON rpi.id_proceso_inscripcion = tpi.proceso_inscripcion_id AND rpi.id_programa = tbd.id_programa\r\n"
				+ "         INNER JOIN tbl_planes tp ON tp.id_plan = rpi.id_plan\r\n"
				+ "         INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = tbd.id_programa\r\n"
				+ "         INNER JOIN tbl_malla_curricular tmc ON tmc.id = fd.id_eje_capacitacion\r\n"
				+ "\tWHERE tpi.convocatoria_id = :idConvocatoria AND tpi.id_tipo_proceso = :idTipoProceso\r\n"
				+ "  AND tpi.proceso_inscripcion_id = :idProcesoInscripcion AND tpi.id_categoria_proceso = 1");
		
		boolean tieneListaPlanes = dispercionParametros.getListaIdPlan() != null
				&& !dispercionParametros.getListaIdPlan().isEmpty();
		boolean tieneListaProgramas = dispercionParametros.getListaIdPrograma() != null
				&& !dispercionParametros.getListaIdPrograma().isEmpty();
		
		if (tieneListaPlanes) {
			consultaBusquedaDispercion.append(" AND rpi.id_plan IN (:listaPlanes)");
		} else if (dispercionParametros.getIdPlan() != null) {
			consultaBusquedaDispercion.append(" AND rpi.id_plan = :idPlan");
		}
		
		if (tieneListaProgramas) {
			consultaBusquedaDispercion.append(" AND fd.id_programa IN (:listaProgramas)");
		} else if (dispercionParametros.getIdPrograma() != null) {
			consultaBusquedaDispercion.append(" AND fd.id_programa = :idPrograma");
		}
		
		Query queryConsultaDispercion = entityManager.createNativeQuery(consultaBusquedaDispercion.toString());
		
		queryConsultaDispercion.setParameter("idConvocatoria", dispercionParametros.getIdConvocatoriaSeleccionada());
		queryConsultaDispercion.setParameter("idTipoProceso", dispercionParametros.getIdTipoProceso());
		queryConsultaDispercion.setParameter("idProcesoInscripcion", dispercionParametros.getIdProcesoInscripcion());
		
		if (tieneListaPlanes) {
			queryConsultaDispercion.setParameter("listaPlanes", dispercionParametros.getListaIdPlan());
		} else if (dispercionParametros.getIdPlan() != null) {
			queryConsultaDispercion.setParameter("idPlan", dispercionParametros.getIdPlan());
		}
		
		if (tieneListaProgramas) {
			queryConsultaDispercion.setParameter("listaProgramas", dispercionParametros.getListaIdPrograma());
		} else if (dispercionParametros.getIdPrograma() != null) {
			queryConsultaDispercion.setParameter("idPrograma", dispercionParametros.getIdPrograma());
		}
		
		List<Object[]> listaBusqueda = queryConsultaDispercion.getResultList();
		
		if (!listaBusqueda.isEmpty()) {
			for (Object[] obj : listaBusqueda) {
				TblDispersionesBusqueda dispersiones = mapeoDispercionBusqueda(obj);
				lista.add(dispersiones);
			}
		}
		
		return lista;
		
	}

	@Transactional
	@Override
	public boolean actualizarDispersionExc(DispersionesParamNuevo dispercionParametros){
		
		Integer idDispersion = dispercionParametros != null ? dispercionParametros.getIdDispersion() : null;
		if (idDispersion == null) {
			idDispersion = IdDispercion;
		}
		
		if (idDispersion == null) {
			logger.warn("No se proporcionó id_dispersion para actualizar la dispersión.");
			return false;
		}
		
		String actualizarDispersion = "UPDATE tbl_dispersiones c " +
	            "SET c.no_grupos = :noGrupos, " +
	            "    c.estudiantes_x_grupo = :estudiantesGrupo, " +
	            "    c.grupo_resto = :grupoResto, " +
	            "    c.estudiantes_resto = :estudianteResto " +
	            "WHERE c.id_dispersion = :id";
		
		Query queryCtualizar = entityManager.createNativeQuery(actualizarDispersion);
		queryCtualizar.setParameter("noGrupos", dispercionParametros.getNoGrupos());
		queryCtualizar.setParameter("estudiantesGrupo", dispercionParametros.getEstudiantesGrupo());
		queryCtualizar.setParameter("grupoResto", dispercionParametros.getGrupoResto());
		queryCtualizar.setParameter("estudianteResto", dispercionParametros.getCupoResto());
		queryCtualizar.setParameter("id", idDispersion);
		
		logger.info(String.format("Actualizando dispersión %d (no_grupos=%d, estudiantes_x_grupo=%d, grupo_resto=%d, estudiantes_resto=%d)",
				idDispersion,
				dispercionParametros.getNoGrupos(),
				dispercionParametros.getEstudiantesGrupo(),
				dispercionParametros.getGrupoResto(),
				dispercionParametros.getCupoResto()));
		
		int registrosActualizados = queryCtualizar.executeUpdate();
		boolean exito = registrosActualizados > 0;
		
		if (exito) {
			logger.info(String.format("Actualización de dispersión %d completada. Registros afectados: %d", idDispersion, registrosActualizados));
		} else {
			logger.warn(String.format("No se actualizaron registros para la dispersión %d.", idDispersion));
		}
		
		return exito;
	}
	
	@Transactional
	@Override
	public List<TblDispersionesBusqueda> actualizarDispersion(DispersionesParamNuevo dispercionParametros){
		
		List<TblDispersionesBusqueda> lista = new ArrayList<TblDispersionesBusqueda>();
		
		String consultarDispersionId = "SELECT dis.id_dispersion, dis.no_grupos, dis.estudiantes_x_grupo, dis.grupo_resto, dis.estudiantes_resto, "
				+ "COALESCE((SELECT COUNT(ti.id) "
				+ "           FROM tbl_inscripciones ti "
				+ "           JOIN rel_proceso_inscipcion_planesyprogramas rpi "
				+ "             ON rpi.id_proceso_inscripcion = dis.id_proceso_inscripcion "
				+ "            AND rpi.id_programa = dis.id_programa "
				+ "            AND rpi.id_plan = ti.idplan "
				+ "           JOIN tbl_procesos_inscripcion tpi "
				+ "             ON tpi.proceso_inscripcion_id = dis.id_proceso_inscripcion "
				+ "          WHERE ti.idprograma = dis.id_programa "
				+ "            AND ti.fecha_registro >= tpi.fecha_inicio "
				+ "            AND ti.fecha_registro <= tpi.fecha_fin), dis.no_total_estudiantes) no_total_estudiantes "
				+ "FROM tbl_dispersiones dis "
				+ "WHERE dis.id_dispersion = :idDispersion ";
		
		
		
		
		Query query = entityManager.createNativeQuery(consultarDispersionId);
		query.setParameter("idDispersion", dispercionParametros.getIdDispersion());
		
		List<Object[]> listaDispersion = query.getResultList();
		
		
		if (!listaDispersion.isEmpty()) {
			for (Object[] obj : listaDispersion) {

				TblDispersionesBusqueda dispersiones = mapeoDispersionActu(obj);
				lista.add(dispersiones);
				IdDispercion = lista.get(0).getIdDispersion();

			}
		}
		
		return lista;
	}
	
	@Override
	public List<ProcesosInscripcion> consultarProcesoInscripcion(DispersionesParam dispercionParametros) {

		List<ProcesosInscripcion> lista = new ArrayList<ProcesosInscripcion>();

		//String consulta = "SELECT  t.proceso_inscripcion_id,t.nombre,t.clave_proceso,t.descripcion,t.fecha_inicio,t.fecha_fin,t.estatus,t.semestre,t.perfil,t.id_tipo_proceso,t.id_categoria_proceso,t.convocatoria_id\r\n"
		String consulta = "SELECT  t.proceso_inscripcion_id,t.nombre \r\n"
				+ "FROM tbl_procesos_inscripcion t\r\n"
				+ "WHERE convocatoria_id = :idConvocatoria \r\n"
				+ "AND id_tipo_proceso = :idTipoProceso \r\n"
				+ "AND id_categoria_proceso = 1";

		Query query = entityManager.createNativeQuery(consulta);
		
		query.setParameter("idConvocatoria", dispercionParametros.getIdConvocatoriaSeleccionada());
		query.setParameter("idTipoProceso", dispercionParametros.getIdTipoProceso());

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				ProcesosInscripcion dispersiones = mapeo(obj);
				lista.add(dispersiones);

			}
		}

		return lista;

	}
	
	@Override
	public List<TblFichaDescriptivaPrograma> consultarPrograma(DispersionesParam dispercionParametros){
		List<TblFichaDescriptivaPrograma> lista = new ArrayList<TblFichaDescriptivaPrograma>();

		//String consulta = "SELECT  t.proceso_inscripcion_id,t.nombre,t.clave_proceso,t.descripcion,t.fecha_inicio,t.fecha_fin,t.estatus,t.semestre,t.perfil,t.id_tipo_proceso,t.id_categoria_proceso,t.convocatoria_id\r\n"
		String consulta = "SELECT tp.id_plan, tp.nombre plan, tfd.id_programa, tfd.nombre_tentativo programa\r\n"
				+ "FROM tbl_convocatoria tc\r\n"
				+ "         INNER JOIN rel_convocatoria_planesyprogramas rcpp ON rcpp.id_convocatoria = tc.convocatoria_id\r\n"
				+ "         INNER JOIN tbl_ficha_descriptiva_programa tfd ON tfd.id_plan = rcpp.id_plan ANd tfd.id_programa = rcpp.id_programa\r\n"
				+ "         INNER JOIN tbl_planes tp ON tp.id_plan = tfd.id_plan\r\n"
				+ "         INNER JOIN tbl_malla_curricular tmc ON tmc.id_plan = tp.id_plan AND tmc.activo = 1\r\n"
				+ "WHERE tc.convocatoria_id = :idConvocatoria AND tp.id_plan = :idPlan ";

		Query query = entityManager.createNativeQuery(consulta);
		
		query.setParameter("idConvocatoria", dispercionParametros.getIdConvocatoriaSeleccionada());
		query.setParameter("idPlan", dispercionParametros.getIdPlan());

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				TblFichaDescriptivaPrograma dispersiones = mapeoPrograma(obj);
				lista.add(dispersiones);

			}
		}

		return lista;
	}
	
	@Override
	public List<InscripcionPlanesProgramas> consultarPlanesProgramas(DispersionesParam dispercionParametros){
		List<InscripcionPlanesProgramas> lista = new ArrayList<InscripcionPlanesProgramas>();
		
		if (dispercionParametros.getIdConvocatoriaSeleccionada() == null) {
			return lista;
		}
		
		boolean filtrarPorProceso = dispercionParametros.getIdProcesoInscripcion() != null;
		StringBuilder consulta = new StringBuilder("SELECT tp.id_plan, tp.nombre plan, tfd.id_programa, tfd.nombre_tentativo programa\r\n");
		if (filtrarPorProceso) {
			consulta.append("FROM rel_proceso_inscipcion_planesyprogramas rpip\r\n")
				.append("         JOIN tbl_ficha_descriptiva_programa tfd ON tfd.id_programa = rpip.id_programa\r\n")
				.append("         JOIN tbl_planes tp ON tp.id_plan = tfd.id_plan\r\n")
				.append("         JOIN tbl_procesos_inscripcion tpi ON tpi.proceso_inscripcion_id = rpip.id_proceso_inscripcion\r\n")
				.append("WHERE rpip.id_proceso_inscripcion = :idProcesoInscripcion\r\n")
				.append("  AND tpi.convocatoria_id = :idConvocatoria");
		} else {
			consulta.append("FROM tbl_convocatoria tc\r\n")
				.append("         INNER JOIN rel_convocatoria_planesyprogramas rcpp ON rcpp.id_convocatoria = tc.convocatoria_id\r\n")
				.append("         INNER JOIN tbl_ficha_descriptiva_programa tfd ON tfd.id_plan = rcpp.id_plan AND tfd.id_programa = rcpp.id_programa\r\n")
				.append("         INNER JOIN tbl_planes tp ON tp.id_plan = tfd.id_plan\r\n")
				.append("         INNER JOIN tbl_malla_curricular tmc ON tmc.id_plan = tp.id_plan AND tmc.activo = 1\r\n")
				.append("WHERE tc.convocatoria_id = :idConvocatoria");
		}
		
		Query query = entityManager.createNativeQuery(consulta.toString());
		query.setParameter("idConvocatoria", dispercionParametros.getIdConvocatoriaSeleccionada());
		if (filtrarPorProceso) {
			query.setParameter("idProcesoInscripcion", dispercionParametros.getIdProcesoInscripcion());
		}

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {
				InscripcionPlanesProgramas planPrograma = new InscripcionPlanesProgramas();
				planPrograma.setIdPlan(((Number) obj[0]).intValue());
				planPrograma.setNombrePlan(obj[1].toString());
				planPrograma.setIdPrograma(((Number) obj[2]).intValue());
				planPrograma.setNombrePrograma(obj[3].toString());
				lista.add(planPrograma);
			}
		}

		return lista;
	}
	
	@Override
	public List<TblPlan> consultarPlan(DispersionesParam dispercionParametros){
		List<TblPlan> lista = new ArrayList<TblPlan>();

		//String consulta = "SELECT  t.proceso_inscripcion_id,t.nombre,t.clave_proceso,t.descripcion,t.fecha_inicio,t.fecha_fin,t.estatus,t.semestre,t.perfil,t.id_tipo_proceso,t.id_categoria_proceso,t.convocatoria_id\r\n"
		String consulta = "SELECT DISTINCT (rcpp.id_plan), tp.nombre plan\r\n"
				+ "FROM tbl_convocatoria tc\r\n"
				+ "         INNER JOIN rel_convocatoria_planesyprogramas rcpp ON rcpp.id_convocatoria = tc.convocatoria_id\r\n"
				+ "         INNER JOIN tbl_planes tp ON tp.id_plan = rcpp.id_plan\r\n"
				+ "         INNER JOIN tbl_malla_curricular tmc ON tmc.id_plan = tp.id_plan AND tmc.activo = 1\r\n"
				+ "			WHERE tc.convocatoria_id = :idConvocatoria ";

		Query query = entityManager.createNativeQuery(consulta);
		
		query.setParameter("idConvocatoria", dispercionParametros.getIdConvocatoriaSeleccionada());

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				TblPlan dispersiones = mapeoPlan(obj);
				lista.add(dispersiones);

			}
		}

		return lista;
	}
	
	
	private TblDispersionesBusqueda mapeoDispersionActu(Object[] obj) {

		TblDispersionesBusqueda regresa = new TblDispersionesBusqueda();
		
		regresa.setIdDispersion(((Number) obj[0]).intValue());
		regresa.setGruposGenerales(((Number) obj[1]).intValue());
		regresa.setCupoGeneral(((Number) obj[2]).intValue());
		regresa.setGrupoResto(((Number) obj[3]).intValue());
		regresa.setCupoResto(((Number) obj[4]).intValue());
		regresa.setNoEstudiantes(((Number) obj[5]).intValue());
	
		return regresa;
	}
	
	
	private ProcesosInscripcion mapeo(Object[] obj) {

		ProcesosInscripcion regresa = new ProcesosInscripcion();
		
		regresa.setIdProcesoInscripcion((Integer) obj[0]);
		regresa.setNombre(obj[1].toString());
	
		return regresa;
	}
	
	
	private TblDispersionesBusqueda mapeoDispercionBusqueda(Object[] obj) { 

		TblDispersionesBusqueda regresa = new TblDispersionesBusqueda();
		regresa.setIdDispersion(((Number) obj[0]).intValue());
		regresa.setIdPlan(((Number) obj[1]).intValue());
		regresa.setPlan(obj[2].toString());
		regresa.setIdPrograma(((Number) obj[3]).intValue());
		regresa.setPrograma(obj[4].toString());
		regresa.setClave(obj[5].toString());
		regresa.setSemestre(obj[6].toString());
		regresa.setBloque(obj[7].toString());
		regresa.setNoEstudiantes(((Number) obj[8]).intValue());
		regresa.setGruposGenerales(((Number) obj[9]).intValue());
		regresa.setCupoGeneral(((Number) obj[10]).intValue());
		regresa.setGrupoResto(((Number) obj[11]).intValue());
		regresa.setCupoResto(((Number) obj[12]).intValue());
		regresa.setTipoMatriculacion(obj[13] != null ? ((Number) obj[13]).intValue() : null);
		regresa.setGruposCreados(obj[14] != null ? ((Number) obj[14]).intValue() : 0);
		regresa.setUsuariosMatriculados(obj[15] != null ? ((Number) obj[15]).intValue() : 0);
		
	
		return regresa;
	}
	
	private DispersionPreEvento mapeoDispersionPreEvento(Object[] obj) {
		DispersionPreEvento dto = new DispersionPreEvento();
		dto.setNombrePlan(obj[0] != null ? obj[0].toString() : null);
		dto.setNombrePrograma(obj[1] != null ? obj[1].toString() : null);
		dto.setClavePrograma(obj[2] != null ? obj[2].toString() : null);
		dto.setClaveParaEvento(obj[3] != null ? obj[3].toString() : null);
		dto.setClaveParaGrupo(obj[4] != null ? obj[4].toString() : null);
		dto.setBloque(obj[5] != null ? obj[5].toString() : null);
		dto.setObjetivosGenerales(obj[6] != null ? obj[6].toString() : null);
		dto.setPerfilEgreso(obj[7] != null ? obj[7].toString() : null);
		dto.setRequisitosIngreso(obj[8] != null ? obj[8].toString() : null);
		dto.setCalificacionMinAprobatoria(obj[9] != null ? obj[9].toString() : null);
		return dto;
	}
	
	private TblPlan mapeoPlan(Object[] obj) {

		TblPlan regresa = new TblPlan();
		
		regresa.setIdPlan((Integer) obj[0]);
		regresa.setNombre(obj[1].toString());
	
		return regresa;
	}
	
	private TblFichaDescriptivaPrograma mapeoPrograma(Object[] obj) {

		TblFichaDescriptivaPrograma regresa = new TblFichaDescriptivaPrograma();
		
		regresa.setIdPrograma((Integer) obj[2]);
		regresa.setNombreTentativo(obj[3].toString());
	
		return regresa;
	}
	
	
	@Override
	public List<TipoMatriculacion> consultarTipoMatriculacion() {

		List<TipoMatriculacion> lista = new ArrayList<TipoMatriculacion>();

		String consulta = "SELECT ctp.id_tipo_matriculacion, ctp.nombre FROM cat_tipo_matriculacion ctp WHERE ctp.estatus = 1";

		Query query = entityManager.createNativeQuery(consulta);

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				TipoMatriculacion dispersiones = mapeo2(obj);
				lista.add(dispersiones);

			}
		}

		return lista;
		
	}
	
	@Override
	public DispersionPreEvento obtenerDatosPreviosEvento(Integer idPrograma) {
		if (idPrograma == null) {
			return null;
		}
		String consulta = "SELECT tp.nombre plan, tfdp.nombre_tentativo programa, tfdp.cve_programa clavePrograma,\r\n"
				+ "    CONCAT(tfdp.cve_programa,SUBSTRING(tmc.nombre,1,1),SUBSTRING_INDEX(tmc.nombre,' ',-1),SUBSTRING(tmc2.nombre,1,1),SUBSTRING_INDEX(tmc2.nombre,' ',-1)) claveParaEvento,\r\n"
				+ "    CONCAT(SUBSTRING_INDEX(tp.identificador,'-',1),'-',tfdp.cve_programa,SUBSTRING(tmc.nombre,1,1),SUBSTRING_INDEX(tmc.nombre,' ',-1),SUBSTRING(tmc2.nombre,1,1),SUBSTRING_INDEX(tmc2.nombre,' ',-1)) claveParaGrupo,\r\n"
				+ "    CONCAT(SUBSTRING(tmc.nombre,1,1),SUBSTRING_INDEX(tmc.nombre,' ',-1)) bloque,\r\n"
				+ "    tfdp.objetivos_generales, tfdp.perfil_egreso, tfdp.requisitos_ingreso, tfdp.calificacion_min_aprobatoria\r\n"
				+ "    FROM tbl_ficha_descriptiva_programa tfdp\r\n"
				+ "    JOIN tbl_planes tp ON tfdp.id_plan = tp.id_plan\r\n"
				+ "    JOIN tbl_malla_curricular tmc ON tmc.id = tfdp.id_eje_capacitacion\r\n"
				+ "    JOIN tbl_malla_curricular tmc2 ON tmc2.id = tmc.id_padre\r\n"
				+ " WHERE tfdp.id_programa = :idPrograma";
		
		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("idPrograma", idPrograma);
		List<Object[]> resultados = query.getResultList();
		if (resultados == null || resultados.isEmpty()) {
			return null;
		}
		return mapeoDispersionPreEvento(resultados.get(0));
	}
	
	@Override
	public TblEvento guardarEventoDispersion(TblEvento evento) {
		entityManager.persist(evento);
		entityManager.flush();
		entityManager.refresh(evento);
		return evento;
	}
	
	@Override
	public void actualizarEvento(TblEvento evento) {
		entityManager.merge(evento);
		entityManager.flush();
	}
	
	@Override
	public TblAmbienteVirtualAprendizaje guardarAmbienteVirtual(TblAmbienteVirtualAprendizaje ambienteVirtual) {
		entityManager.persist(ambienteVirtual);
		entityManager.flush();
		entityManager.refresh(ambienteVirtual);
		return ambienteVirtual;
	}
	
	@Override
	public TblGrupo guardarGrupoDispersion(TblGrupo grupo) {
		entityManager.persist(grupo);
		entityManager.flush();
		entityManager.refresh(grupo);
		return grupo;
	}
	
	@Override
	public void actualizarGrupoDispersion(TblGrupo grupo) {
		entityManager.merge(grupo);
		entityManager.flush();
	}
	
	@Override
	public void guardarRelacionDispersionGrupo(Integer idDispersion, Integer idEvento, Integer idGrupo, Long idPersona) {
		String insert = "INSERT INTO rel_dispersiones_grupo(id_dispersion, id_evento, id_grupo, id_persona) "
				+ " VALUES(:idDispersion, :idEvento, :idGrupo, :idPersona)";
		Query query = entityManager.createNativeQuery(insert);
		query.setParameter("idDispersion", idDispersion);
		query.setParameter("idEvento", idEvento);
		query.setParameter("idGrupo", idGrupo);
		query.setParameter("idPersona", idPersona);
		query.executeUpdate();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<DispersionGrupoEventoDTO> obtenerRelacionesDispersion(Integer idDispersion) {
		List<DispersionGrupoEventoDTO> resultado = new ArrayList<>();
		if (idDispersion == null) {
			return resultado;
		}
		String sql = "SELECT rdg.id_evento, rdg.id_grupo, te.id_curso_lms_borrador, te.id_plataforma_lms_borrador,\r\n"
				+ "       te.modalidad, tg.num_max_alumnos, tg.id_moodle, tg.nombre,\r\n"
				+ "       COALESCE((SELECT COUNT(*) FROM rel_grupo_participante rgp "
				+ "		  JOIN tbl_persona_aspirante tpa ON tpa.id_persona = rgp.id_persona_participante"
				+ " WHERE rgp.id_grupo = tg.id), 0) inscritos\r\n"
				+ "  FROM rel_dispersiones_grupo rdg\r\n"
				+ "       INNER JOIN tbl_eventos te ON te.id_evento = rdg.id_evento\r\n"
				+ "       INNER JOIN tbl_grupos tg ON tg.id = rdg.id_grupo\r\n"
				+ " WHERE rdg.id_dispersion = :idDispersion\r\n"
				+ " ORDER BY tg.id";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("idDispersion", idDispersion);
		List<Object[]> registros = query.getResultList();
		for (Object[] row : registros) {
			DispersionGrupoEventoDTO dto = mapearGrupoEvento(row);
			if (dto != null) {
				resultado.add(dto);
			}
		}
		return resultado;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<DispersionGrupoEventoDTO> obtenerGruposOrdinariosPorPrograma(Integer idPrograma,
			Integer idProcesoInscripcion) {
		List<DispersionGrupoEventoDTO> resultado = new ArrayList<>();
		if (idPrograma == null || idProcesoInscripcion == null) {
			return resultado;
		}
		String sql = "SELECT rdg.id_evento, rdg.id_grupo, te.id_curso_lms_borrador, te.id_plataforma_lms_borrador,\r\n"
				+ "       te.modalidad, tg.num_max_alumnos, tg.id_moodle, tg.nombre,\r\n"
				+ "       COALESCE((SELECT COUNT(*) FROM rel_grupo_participante rgp WHERE rgp.id_grupo = tg.id), 0) inscritos\r\n"
				+ "  FROM rel_dispersiones_grupo rdg\r\n"
				+ "       INNER JOIN tbl_eventos te ON te.id_evento = rdg.id_evento\r\n"
				+ "       INNER JOIN tbl_grupos tg ON tg.id = rdg.id_grupo\r\n"
				+ "       INNER JOIN tbl_dispersiones td ON td.id_dispersion = rdg.id_dispersion\r\n"
				+ " WHERE te.id_programa = :idPrograma\r\n"
				+ "   AND td.id_proceso_inscripcion = :idProcesoInscripcion\r\n"
				+ " ORDER BY rdg.id_evento, rdg.id_grupo";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("idPrograma", idPrograma);
		query.setParameter("idProcesoInscripcion", idProcesoInscripcion);
		List<Object[]> registros = query.getResultList();
		for (Object[] row : registros) {
			DispersionGrupoEventoDTO dto = mapearGrupoEvento(row);
			if (dto != null) {
				resultado.add(dto);
			}
		}
		return resultado;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Long> obtenerPersonasMatriculacion(Integer idDispersion, Integer idConvocatoria, Integer idTipoProceso,
			Integer idProcesoInscripcion) {
		List<Long> personas = new ArrayList<>();
		if (idDispersion == null || idConvocatoria == null || idTipoProceso == null || idProcesoInscripcion == null) {
			return personas;
		}
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT ti.idpersona ")
		   .append("FROM tbl_inscripciones ti ")
		   .append("INNER JOIN rel_proceso_inscipcion_planesyprogramas rpi ON rpi.id_programa = ti.idprograma AND rpi.id_plan = ti.idplan ")
		   .append("INNER JOIN tbl_procesos_inscripcion tpi ON tpi.proceso_inscripcion_id = rpi.id_proceso_inscripcion ")
		   .append("INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = ti.idprograma AND fd.id_plan = ti.idplan ")
		   .append("INNER JOIN tbl_planes tp ON tp.id_plan = ti.idplan ")
		   .append("INNER JOIN tbl_malla_curricular tmc ON tmc.id = fd.id_eje_capacitacion ")
		   .append("INNER JOIN tbl_convocatoria tc ON tc.convocatoria_id = tpi.convocatoria_id ")
		   .append("WHERE tpi.convocatoria_id = :idConvocatoria ")
		   .append("  AND tpi.id_tipo_proceso = :idTipoProceso ")
		   .append("  AND ti.fecha_registro >= tpi.fecha_inicio AND ti.fecha_registro <= tpi.fecha_fin ")
		   .append("  AND tpi.proceso_inscripcion_id = :idProcesoInscripcion ")
		   .append("  AND EXISTS (SELECT 1 FROM tbl_dispersiones td ")
		   .append("               WHERE td.id_dispersion = :idDispersion ")
		   .append("                 AND td.id_proceso_inscripcion = tpi.proceso_inscripcion_id ")
		   .append("                 AND td.id_programa = ti.idprograma) ")
		   .append("  AND NOT EXISTS (SELECT 1 FROM rel_grupo_participante rgp ")
		   .append("                   INNER JOIN rel_dispersiones_grupo rdg2 ON rdg2.id_grupo = rgp.id_grupo ")
		   .append("                   WHERE rdg2.id_dispersion = :idDispersion ")
		   .append("                     AND rgp.id_persona_participante = ti.idpersona) ")
		   .append("ORDER BY ti.idpersona");
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("idConvocatoria", idConvocatoria);
		query.setParameter("idTipoProceso", idTipoProceso);
		query.setParameter("idProcesoInscripcion", idProcesoInscripcion);
		query.setParameter("idDispersion", idDispersion);
		List<Number> registros = query.getResultList();
		for (Number numero : registros) {
			if (numero != null) {
				personas.add(numero.longValue());
			}
		}
		return personas;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Long> obtenerPersonasMatriculacionCompartidos(Integer idDispersion, Integer idProcesoInscripcion,
			Integer idPrograma, String nombreProgramaSeleccionado, String bloque) {
		List<Long> personas = new ArrayList<>();
		if (idDispersion == null || idProcesoInscripcion == null || idPrograma == null
				|| nombreProgramaSeleccionado == null || nombreProgramaSeleccionado.trim().isEmpty()
				|| bloque == null || bloque.trim().isEmpty()) {
			return personas;
		}
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT tis.Idpersona, tis.idplan, td.id_programa AS idprograma, :idDispersion id_dispersion ")
		   .append("FROM tbl_inscripciones tis ")
		   .append("JOIN tbl_procesos_inscripcion tpi ")
		   .append("  ON tis.fecha_registro >= tpi.fecha_inicio ")
		   .append(" AND tis.fecha_registro <= tpi.fecha_fin ")
		   .append(" AND tpi.proceso_inscripcion_id = :idProcesosInscripcion ")
		   .append("JOIN tbl_dispersiones td ")
		   .append("  ON td.id_proceso_inscripcion = tpi.proceso_inscripcion_id ")
		   .append(" AND td.id_dispersion = :idDispersion ")
		   .append("WHERE tis.asignatura LIKE CONCAT('%', TRIM(:nombreProgramaSelecionado), '%') ")
		   .append("  AND :bloque LIKE CONCAT('%', tis.bloque, '%') ")
		   .append("  AND NOT EXISTS( ")
		   .append("      SELECT 1 ")
		   .append("      FROM rel_grupo_participante rgp ")
		   .append("      JOIN tbl_grupos tg ON rgp.id_grupo = tg.id ")
		   .append("      JOIN tbl_eventos te ON te.id_evento = tg.id_evento ")
		   .append("      JOIN tbl_ficha_descriptiva_programa tfpp ")
		   .append("        ON tfpp.id_programa = te.id_programa ")
		   .append("      WHERE rgp.id_persona_participante = tis.Idpersona ")
		   .append("        AND tis.asignatura LIKE CONCAT(tfpp.nombre_tentativo,'%') ")
		   .append("        AND rgp.fecha_registro >= tis.fecha_registro) ")
		   .append("ORDER BY CASE WHEN tis.idprograma = :idPrograma THEN 0 ELSE 1 END, ")
		   .append("         tis.idprograma");
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("idDispersion", idDispersion);
		query.setParameter("idProcesosInscripcion", idProcesoInscripcion);
		query.setParameter("nombreProgramaSelecionado", nombreProgramaSeleccionado);
		query.setParameter("bloque", bloque);
		query.setParameter("idPrograma", idPrograma);
		List<Object[]> registros = query.getResultList();
		for (Object[] row : registros) {
			if (row != null && row.length > 0 && row[0] instanceof Number) {
				personas.add(((Number) row[0]).longValue());
			}
		}
		return personas;
	}
	
	@Override
	public Integer crearDispersionBasica(Integer idProcesoInscripcion, Integer idPrograma, Integer totalEstudiantes,
			Integer tipoMatriculacion, Long usuarioModifico) {
		String sql = "INSERT INTO tbl_dispersiones (id_proceso_inscripcion, id_programa, no_total_estudiantes, no_grupos, estudiantes_x_grupo, grupo_resto, estudiantes_resto, tipo_matriculacion, usuario_modifico) "
				+ " VALUES (:idProcesoInscripcion, :idPrograma, :totalEstudiantes, :noGrupos, :estudiantesGrupo, :grupoResto, :estudiantesResto, :tipoMatriculacion, :usuarioModifico)";
		Query insert = entityManager.createNativeQuery(sql);
		insert.setParameter("idProcesoInscripcion", idProcesoInscripcion);
		insert.setParameter("idPrograma", idPrograma);
		insert.setParameter("totalEstudiantes", totalEstudiantes != null ? totalEstudiantes : 0);
		insert.setParameter("noGrupos", 0);
		insert.setParameter("estudiantesGrupo", 0);
		insert.setParameter("grupoResto", 0);
		insert.setParameter("estudiantesResto", 0);
		insert.setParameter("tipoMatriculacion", tipoMatriculacion != null ? tipoMatriculacion : 0);
		insert.setParameter("usuarioModifico", usuarioModifico != null ? usuarioModifico : 0);
		insert.executeUpdate();
		Query lastId = entityManager.createNativeQuery("SELECT LAST_INSERT_ID()");
		Object id = lastId.getSingleResult();
		return id != null ? ((Number) id).intValue() : null;
	}
	
	@Override
	public List<Long> obtenerPersonasMatriculaExistente(Integer idProcesoInscripcionMatricular, Integer idPrograma) {
		List<Long> personas = new ArrayList<>();
		if (idProcesoInscripcionMatricular == null || idPrograma == null) {
			return personas;
		}
		String sql = "SELECT DISTINCT ti.idpersona "
				+ "FROM tbl_inscripciones ti "
				+ "INNER JOIN rel_proceso_inscipcion_planesyprogramas rpi ON rpi.id_programa = ti.idprograma AND rpi.id_plan = ti.idplan "
				+ "INNER JOIN tbl_procesos_inscripcion tpi ON tpi.proceso_inscripcion_id = rpi.id_proceso_inscripcion "
				+ "WHERE ti.fecha_registro >= tpi.fecha_inicio AND ti.fecha_registro <= tpi.fecha_fin "
				+ "AND tpi.proceso_inscripcion_id = :idProcesoInscripcionMatricular "
				+ "AND ti.idprograma = :idPrograma "
				+ "ORDER BY ti.idpersona";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("idProcesoInscripcionMatricular", idProcesoInscripcionMatricular);
		query.setParameter("idPrograma", idPrograma);
		List<Number> registros = query.getResultList();
		for (Number numero : registros) {
			if (numero != null) {
				personas.add(numero.longValue());
			}
		}
		return personas;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ProcesosInscripcion> consultarProcesosConDispersion() {
		String sql = "SELECT tpi.proceso_inscripcion_id AS idProcesoInscripcion, "
				+ " tpi.convocatoria_id AS idConvocatoria, "
				+ " tpi.id_tipo_proceso AS idTipoProceso, "
				+ " CONCAT(tc.nombre,'>',tpi.nombre) AS nombre"
				+ " FROM tbl_procesos_inscripcion tpi"
				+ " JOIN tbl_convocatoria tc ON tc.convocatoria_id = tpi.convocatoria_id"
				+ " WHERE tpi.estatus = 1 AND tpi.id_categoria_proceso IN (1,2)"
				+ " AND EXISTS(SELECT 1 FROM rel_proceso_inscipcion_planesyprogramas rpip where rpip.id_proceso_inscripcion = tpi.proceso_inscripcion_id)"
				+ " AND EXISTS(SELECT 1 FROM tbl_dispersiones td"
				+ "                 JOIN rel_dispersiones_grupo rdg ON rdg.id_dispersion = td.id_dispersion"
				+ "                 WHERE td.id_proceso_inscripcion = tpi.proceso_inscripcion_id)";
		Query query = entityManager.createNativeQuery(sql);
		List<Object[]> registros = query.getResultList();
		List<ProcesosInscripcion> lista = new ArrayList<>();
		for (Object[] row : registros) {
			ProcesosInscripcion proc = new ProcesosInscripcion();
			proc.setIdProcesoInscripcion(row[0] != null ? ((Number) row[0]).intValue() : null);
			proc.setIdConvocatoria(row[1] != null ? ((Number) row[1]).intValue() : null);
			proc.setIdTipoProceso(row[2] != null ? ((Number) row[2]).intValue() : null);
			proc.setNombre(row[3] != null ? row[3].toString() : null);
			lista.add(proc);
		}
		return lista;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ProcesosInscripcion> consultarProcesosSinDispersion() {
		String sql = "SELECT tpi.proceso_inscripcion_id AS idProcesoInscripcion, "
				+ " tpi.convocatoria_id AS idConvocatoria, "
				+ " tpi.id_tipo_proceso AS idTipoProceso, "
				+ " CONCAT(tc.nombre,'>',tpi.nombre) AS nombre"
				+ " FROM tbl_procesos_inscripcion tpi"
				+ " JOIN tbl_convocatoria tc ON tc.convocatoria_id = tpi.convocatoria_id"
				+ " WHERE tpi.estatus = 1 AND tpi.id_categoria_proceso IN (1,2)"
				+ " AND EXISTS(SELECT 1 FROM rel_proceso_inscipcion_planesyprogramas rpip where rpip.id_proceso_inscripcion = tpi.proceso_inscripcion_id)"
				+ " AND NOT EXISTS(SELECT 1 FROM tbl_dispersiones td"
				+ "                 JOIN rel_dispersiones_grupo rdg ON rdg.id_dispersion = td.id_dispersion"
				+ "                 WHERE td.id_proceso_inscripcion = tpi.proceso_inscripcion_id)";
		Query query = entityManager.createNativeQuery(sql);
		List<Object[]> registros = query.getResultList();
		List<ProcesosInscripcion> lista = new ArrayList<>();
		for (Object[] row : registros) {
			ProcesosInscripcion proc = new ProcesosInscripcion();
			proc.setIdProcesoInscripcion(row[0] != null ? ((Number) row[0]).intValue() : null);
			proc.setIdConvocatoria(row[1] != null ? ((Number) row[1]).intValue() : null);
			proc.setIdTipoProceso(row[2] != null ? ((Number) row[2]).intValue() : null);
			proc.setNombre(row[3] != null ? row[3].toString() : null);
			lista.add(proc);
		}
		return lista;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> consultarProgramasCompartidos(Integer idProcesoInscripcion) {
		List<String> lista = new ArrayList<>();
		if (idProcesoInscripcion == null) {
			return lista;
		}
		String sql = "SELECT tfdp.nombre_tentativo value, tfdp.nombre_tentativo programa "
				+ "FROM rel_proceso_inscipcion_planesyprogramas rpip "
				+ "JOIN tbl_ficha_descriptiva_programa tfdp ON tfdp.id_programa = rpip.id_programa "
				+ "WHERE EXISTS(SELECT 1 FROM tbl_ficha_descriptiva_programa tfdpp "
				+ "              WHERE tfdpp.nombre_tentativo = tfdp.nombre_tentativo "
				+ "                AND tfdpp.id_plan != rpip.id_plan) "
				+ "AND rpip.id_proceso_inscripcion = :idProcesoInscripcion "
				+ "GROUP BY tfdp.nombre_tentativo";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("idProcesoInscripcion", idProcesoInscripcion);
		List<Object[]> registros = query.getResultList();
		for (Object[] row : registros) {
			if (row != null && row.length > 0 && row[0] != null) {
				lista.add(row[0].toString());
			}
		}
		return lista;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TblDispersionesBusqueda> consultarDispersionesGruposCompartidos(Integer idProcesoInscripcion,
			String nombreProgramaSeleccionado) {
		List<TblDispersionesBusqueda> lista = new ArrayList<>();
		if (idProcesoInscripcion == null || nombreProgramaSeleccionado == null
				|| nombreProgramaSeleccionado.trim().isEmpty()) {
			return lista;
		}
		String sql = "SELECT * "
				+ "FROM ( "
				+ "    SELECT td.id_dispersion, tp.id_plan, tp.nombre AS plan, tfdp.id_programa, "
				+ "           tfdp.nombre_tentativo AS programa, tfdp.cve_programa AS clavePrograma, "
				+ "           COALESCE(tmc2.nombre, '-') AS semestre, tmc.nombre AS bloque, "
				+ "           ( "
				+ "               SELECT COUNT(tis.asignatura) "
				+ "               FROM tbl_inscripciones tis "
				+ "               JOIN tbl_procesos_inscripcion tpi "
				+ "                 ON tis.fecha_registro >= tpi.fecha_inicio "
				+ "                AND tis.fecha_registro <= tpi.fecha_fin "
				+ "                AND tpi.proceso_inscripcion_id = :idProcesosInscripcion "
				+ "               WHERE tis.asignatura LIKE CONCAT(tfdp.nombre_tentativo, '%') "
				+ "                 AND tmc.nombre LIKE CONCAT('%', tis.bloque, '%') "
				+ "                 AND NOT EXISTS ( "
				+ "                     SELECT 1 "
				+ "                     FROM rel_grupo_participante rgp "
				+ "                     JOIN tbl_grupos tg ON rgp.id_grupo = tg.id "
				+ "                     JOIN tbl_eventos te ON te.id_evento = tg.id_evento "
				+ "                     JOIN tbl_ficha_descriptiva_programa tfpp ON tfpp.id_programa = te.id_programa "
				+ "                     WHERE rgp.id_persona_participante = tis.Idpersona "
				+ "                       AND tis.asignatura LIKE CONCAT(tfpp.nombre_tentativo, '%') "
				+ "                       AND rgp.fecha_registro >= tis.fecha_registro "
				+ "                 ) "
				+ "           ) AS num_usuarios_inscripcion, "
				+ "           td.no_grupos AS gruposgenerales, td.estudiantes_x_grupo AS cupogeneral, "
				+ "           td.grupo_resto AS gruposresto, td.estudiantes_resto AS cuporesto, "
				+ "           td.tipo_matriculacion AS tipo_matriculacion, "
				+ "           COALESCE((SELECT COUNT(DISTINCT rdg.id_grupo) "
				+ "                    FROM rel_dispersiones_grupo rdg "
				+ "                    WHERE rdg.id_dispersion = td.id_dispersion), 0) AS grupos_creados, "
				+ "           COALESCE((SELECT COUNT(*) "
				+ "                    FROM rel_grupo_participante rgp "
				+ "                    INNER JOIN rel_dispersiones_grupo rdg "
				+ "                            ON rdg.id_grupo = rgp.id_grupo "
				+ "                    WHERE rdg.id_dispersion = td.id_dispersion "
				+ "                      AND rgp.fecha_registro >= (SELECT tpi1.fecha_inicio "
				+ "                                                   FROM tbl_procesos_inscripcion tpi1 "
				+ "                                                  WHERE tpi1.proceso_inscripcion_id = td.id_proceso_inscripcion)"
				+ "                   ), 0) AS usuarios_matriculados "
				+ "    FROM rel_proceso_inscipcion_planesyprogramas rpip "
				+ "    JOIN tbl_ficha_descriptiva_programa tfdp ON tfdp.id_programa = rpip.id_programa "
				+ "    JOIN tbl_planes tp ON tfdp.id_plan = tp.id_plan "
				+ "    JOIN tbl_malla_curricular tmc ON tmc.id = tfdp.id_eje_capacitacion "
				+ "    LEFT JOIN tbl_malla_curricular tmc2 ON tmc2.id = tmc.id_padre "
				+ "    JOIN tbl_dispersiones td ON td.id_proceso_inscripcion = rpip.id_proceso_inscripcion "
				+ "                             AND td.id_programa = tfdp.id_programa "
				+ "    WHERE rpip.id_proceso_inscripcion = :idProcesosInscripcion "
				+ "      AND tfdp.nombre_tentativo = TRIM(:nombreProgramaSelecionado) "
				+ "      AND EXISTS ( "
				+ "            SELECT 1 "
				+ "            FROM tbl_inscripciones ti "
				+ "            JOIN tbl_procesos_inscripcion tpi "
				+ "              ON ti.fecha_registro >= tpi.fecha_inicio "
				+ "             AND ti.fecha_registro <= tpi.fecha_fin "
				+ "            WHERE ti.asignatura = tfdp.nombre_tentativo "
				+ "      ) "
				+ "      AND EXISTS ( "
				+ "            SELECT 1 "
				+ "            FROM tbl_ficha_descriptiva_programa tfdp2 "
				+ "            WHERE tfdp2.nombre_tentativo = tfdp.nombre_tentativo "
				+ "              AND tfdp2.id_plan != tfdp.id_plan "
				+ "      ) "
				+ ") t "
				+ "WHERE t.num_usuarios_inscripcion > 0 "
				+ "ORDER BY t.plan, t.semestre, t.bloque";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("idProcesosInscripcion", idProcesoInscripcion);
		query.setParameter("nombreProgramaSelecionado", nombreProgramaSeleccionado);
		List<Object[]> registros = query.getResultList();
		for (Object[] row : registros) {
			lista.add(mapeoDispercionBusqueda(row));
		}
		return lista;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DispersionMatriculaExistenteDTO> consultarDispersionesExistentes(
			Integer idProcesoConDispersion, Integer idProcesoMatricular) {
		List<mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DispersionMatriculaExistenteDTO> resultado = new ArrayList<>();
		if (idProcesoConDispersion == null || idProcesoMatricular == null) {
			return resultado;
		}
		String sql = "SELECT td.id_dispersion, "
				+ " tpi.proceso_inscripcion_id AS idProcesoInscripcionMatricular, "
				+ " tp.nombre AS plan, "
				+ " fd.id_programa, "
				+ " fd.nombre_tentativo AS programa, "
				+ " fd.id_plan, "
				+ " fd.cve_programa AS clave, "
				+ " tmc2.nombre AS semestre, "
				+ " tmc.nombre AS bloque, "
				+ " COUNT(ti.id) AS no_estudiantes_inscritos "
				+ " FROM rel_proceso_inscipcion_planesyprogramas rpi "
				+ " INNER JOIN tbl_procesos_inscripcion tpi ON tpi.proceso_inscripcion_id = rpi.id_proceso_inscripcion "
				+ " INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_plan = rpi.id_plan AND fd.id_programa = rpi.id_programa "
				+ " INNER JOIN tbl_planes tp ON tp.id_plan = fd.id_plan "
				+ " INNER JOIN tbl_malla_curricular tmc ON tmc.id = fd.id_eje_capacitacion "
				+ " INNER JOIN tbl_convocatoria tc ON tc.convocatoria_id = tpi.convocatoria_id "
				+ " JOIN tbl_inscripciones ti ON ti.idplan = rpi.id_plan AND ti.idprograma = rpi.id_programa "
				+ "      AND ti.fecha_registro >= tpi.fecha_inicio AND ti.fecha_registro <= tpi.fecha_fin "
				+ " LEFT JOIN tbl_malla_curricular tmc2  ON tmc2.id = tmc.id_padre "
				+ " INNER JOIN rel_proceso_inscipcion_planesyprogramas rpip ON rpi.id_programa = rpip.id_programa AND rpip.id_programa = ti.idprograma AND rpip.id_proceso_inscripcion = :idProcesoInscripcionConDispersion "
				+ " INNER JOIN tbl_dispersiones td ON td.id_proceso_inscripcion = rpip.id_proceso_inscripcion AND td.id_programa = rpip.id_programa "
				+ " WHERE EXISTS(SELECT 1 FROM rel_dispersiones_grupo rdg WHERE rdg.id_dispersion = td.id_dispersion) "
				+ " AND NOT EXISTS(SELECT 1 FROM tbl_dispersiones td2 WHERE td2.id_proceso_inscripcion = tpi.proceso_inscripcion_id AND td2.id_programa = rpi.id_programa) "
				+ " AND tpi.proceso_inscripcion_id = :idProcesoInscripcionMatricular "
				+ " GROUP BY td.id_dispersion, "
				+ " tpi.proceso_inscripcion_id, "
				+ " tp.nombre, "
				+ " fd.id_programa, "
				+ " fd.nombre_tentativo, "
				+ " fd.id_plan, "
				+ " fd.cve_programa, "
				+ " tmc2.nombre, "
				+ " tmc.nombre";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("idProcesoInscripcionConDispersion", idProcesoConDispersion);
		query.setParameter("idProcesoInscripcionMatricular", idProcesoMatricular);
		List<Object[]> registros = query.getResultList();
		for (Object[] row : registros) {
			mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DispersionMatriculaExistenteDTO dto = new mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DispersionMatriculaExistenteDTO();
			dto.setIdDispersion(row[0] != null ? ((Number) row[0]).intValue() : null);
			dto.setIdProcesoInscripcionMatricular(row[1] != null ? ((Number) row[1]).intValue() : null);
			dto.setPlan(row[2] != null ? row[2].toString() : null);
			dto.setIdPrograma(row[3] != null ? ((Number) row[3]).intValue() : null);
			dto.setPrograma(row[4] != null ? row[4].toString() : null);
			dto.setIdPlan(row[5] != null ? ((Number) row[5]).intValue() : null);
			dto.setClave(row[6] != null ? row[6].toString() : null);
			dto.setSemestre(row[7] != null ? row[7].toString() : null);
			dto.setBloque(row[8] != null ? row[8].toString() : null);
			dto.setNoEstudiantesInscritos(row[9] != null ? ((Number) row[9]).intValue() : null);
			resultado.add(dto);
		}
		return resultado;
	}
	
	
	private TipoMatriculacion mapeo2(Object[] obj) {

		TipoMatriculacion regresa = new TipoMatriculacion();
		
		regresa.setIdTipoMatriculacion((Integer) obj[0]);
		regresa.setNombre(obj[1].toString());
	
		return regresa;
	}
	
	private DispersionGrupoEventoDTO mapearGrupoEvento(Object[] row) {
		if (row == null) {
			return null;
		}
		DispersionGrupoEventoDTO dto = new DispersionGrupoEventoDTO();
		dto.setIdEvento(row[0] != null ? ((Number) row[0]).intValue() : null);
		dto.setIdGrupo(row[1] != null ? ((Number) row[1]).intValue() : null);
		dto.setIdCursoLms(row[2] != null ? ((Number) row[2]).intValue() : null);
		dto.setIdPlataformaLms(row[3] != null ? ((Number) row[3]).intValue() : null);
		dto.setIdModalidad(row[4] != null ? ((Number) row[4]).intValue() : null);
		dto.setCapacidadTotal(row[5] != null ? ((Number) row[5]).intValue() : null);
		dto.setIdGrupoMoodle(row[6] != null ? ((Number) row[6]).intValue() : null);
		dto.setNombreGrupo(row[7] != null ? row[7].toString() : null);
		dto.setInscritosActuales(row[8] != null ? ((Number) row[8]).intValue() : 0);
		return dto;
	}
	
	
}
