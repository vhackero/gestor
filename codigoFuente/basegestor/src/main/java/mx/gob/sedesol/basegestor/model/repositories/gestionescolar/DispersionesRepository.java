package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.apache.log4j.Logger;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParam;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParamNuevo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionPlanesProgramas;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ProcesosInscripcion;
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
				+ "    tmc.nombre bloque, tbd.no_total_estudiantes, tbd.no_grupos,\r\n"
				+ "    tbd.estudiantes_x_grupo, tbd.grupo_resto, tbd.estudiantes_resto\r\n"
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
		
		String consultarDispersionId = "SELECT dis.id_dispersion, dis.no_grupos, dis.estudiantes_x_grupo, dis.grupo_resto, dis.estudiantes_resto, dis.no_total_estudiantes FROM tbl_dispersiones dis " +
				"	WHERE dis.id_dispersion = :idDispersion ";
		
		
		
		
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

		String consulta = "SELECT tp.id_plan, tp.nombre plan, tfd.id_programa, tfd.nombre_tentativo programa\r\n"
				+ "FROM tbl_convocatoria tc\r\n"
				+ "         INNER JOIN rel_convocatoria_planesyprogramas rcpp ON rcpp.id_convocatoria = tc.convocatoria_id\r\n"
				+ "         INNER JOIN tbl_ficha_descriptiva_programa tfd ON tfd.id_plan = rcpp.id_plan ANd tfd.id_programa = rcpp.id_programa\r\n"
				+ "         INNER JOIN tbl_planes tp ON tp.id_plan = tfd.id_plan\r\n"
				+ "         INNER JOIN tbl_malla_curricular tmc ON tmc.id_plan = tp.id_plan AND tmc.activo = 1\r\n"
				+ "WHERE tc.convocatoria_id = :idConvocatoria";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("idConvocatoria", dispercionParametros.getIdConvocatoriaSeleccionada());

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
		regresa.setPlan(obj[2].toString());
		regresa.setPrograma(obj[4].toString());
		regresa.setClave(obj[5].toString());
		regresa.setSemestre(obj[6].toString());
		regresa.setBloque(obj[7].toString());
		regresa.setNoEstudiantes(((Number) obj[8]).intValue());
		regresa.setGruposGenerales(((Number) obj[9]).intValue());
		regresa.setCupoGeneral(((Number) obj[10]).intValue());
		regresa.setGrupoResto(((Number) obj[11]).intValue());
		regresa.setCupoResto(((Number) obj[12]).intValue());
		
	
		return regresa;
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
	
	
	private TipoMatriculacion mapeo2(Object[] obj) {

		TipoMatriculacion regresa = new TipoMatriculacion();
		
		regresa.setIdTipoMatriculacion((Integer) obj[0]);
		regresa.setNombre(obj[1].toString());
	
		return regresa;
	}
	
	
}
