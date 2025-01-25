package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Dispersiones;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParam;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParamNuevo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ProcesosInscripcion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoMatriculacion;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblDispersiones;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblDispersionesBusqueda;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;

@Repository
public class DispersionesRepository implements IDispersionesRepository {

	@Autowired
	public EntityManager entityManager;
	
	Integer IdDispercion;
	
	@Override
	public void altaDisperciones(DispersionesParam dispercionParametros){
		List<TblDispersiones> lista = new ArrayList<TblDispersiones>();

		String consulta = "SELECT\r\n"
				+ "    tir2.id id_inscirpcion_resumen,\r\n"
				+ "    tpi.proceso_inscripcion_id id_proceso_inscripcion,\r\n"
				+ "    count(ti.id) no_estudiantes_inscritos,\r\n"
				+ "    0 num_grupos,\r\n"
				+ "    0 num_estudiantes_x_grupo,\r\n"
				+ "    0 grupo_resto,\r\n"
				+ "    0 estudiantes_resto\r\n"
				+ "  FROM tbl_inscripciones ti\r\n"
				+ "         INNER JOIN rel_proceso_inscipcion_planesyprogramas rpi ON rpi.id_programa = ti.idprograma AND rpi.id_plan = ti.idplan\r\n"
				+ "         INNER JOIN tbl_procesos_inscripcion tpi ON tpi.proceso_inscripcion_id =  rpi.id_proceso_inscripcion\r\n"
				+ "         INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = ti.idprograma and ti.idplan = fd.id_plan\r\n"
				+ "         INNER JOIN tbl_planes tp ON tp.id_plan = ti.idplan\r\n"
				+ "         INNER JOIN tbl_malla_curricular tmc ON tmc.id = fd.id_eje_capacitacion\r\n"
				+ "         INNER JOIN tbl_inscripcion_resumen tir2 ON fd.identificador_final = tir2.clave_asignatura AND tir2.no_estudiantes > 0 AND tir2.bloque = SUBSTRING_INDEX(tmc.nombre,' ',-1)  AND tir2.semestre = tpi.semestre\r\n"
				+ "WHERE tpi.convocatoria_id = :idConvocatoria AND tpi.id_tipo_proceso = :idTipoProceso\r\n"
				+ "  AND tpi.proceso_inscripcion_id = :idProcesoInscripcion AND tpi.id_categoria_proceso = 1\r\n"
				+ "  AND (ti.fecha_registro >= tpi.fecha_inicio AND ti.fecha_registro <= tpi.fecha_fin)\r\n"
				+ "group by rpi.id_programa";
		
		String consultaInsert = "INSERT INTO tbl_procesos_inscripcion "
				+ "(id_inscripcion_resumen, id_proceso_inscripcion, no_total_estudiantes, no_grupos, estudiantes_x_grupo, grupo_resto, estudiantes_resto) "
				+ "VALUES "
				+ "(:idResumen, :idProcesoInscripcion, :noTotEstudiantes, :noGrupos, :estudiantesGrupo, :grupoResto, :estudianteResto )";

		Query queryConsulta = entityManager.createNativeQuery(consulta);
		Query queryInsert = entityManager.createNativeQuery(consultaInsert);
		
		queryConsulta.setParameter("idConvocatoria", dispercionParametros.getIdConvocatoriaSeleccionada());
		queryConsulta.setParameter("idTipoProceso", dispercionParametros.getIdTipoProceso());
		queryConsulta.setParameter("idProcesoInscripcion", dispercionParametros.getIdProcesoInscripcion());

		List<Object[]> listaQuery = queryConsulta.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				queryInsert.setParameter("idResumen", (Integer) obj[0]);
				queryInsert.setParameter("idProcesoInscripcion", dispercionParametros.getIdProcesoInscripcion());
				queryInsert.setParameter("noTotEstudiantes", (Integer) obj[1]);
				queryInsert.setParameter("noGrupos", (Integer) obj[2]);
				queryInsert.setParameter("estudiantesGrupo", (Integer) obj[3]);
				queryInsert.setParameter("grupoResto", (Integer) obj[4]);
				queryInsert.setParameter("estudianteResto", (Integer) obj[5]);
				queryInsert.executeUpdate();
				TblDispersiones dispersiones = mapeoDispercion(obj);
				lista.add(dispersiones);

			}
		}
	}
	
	@Override
	public List<TblDispersiones> validarDispercionExistenteOrdinario(DispersionesParam dispercionParametros){
		List<TblDispersiones> lista = new ArrayList<TblDispersiones>();
		
		String consulta = "SELECT IF(COUNT(td.id_dispersion)>0,1,0)existe_dispersion_ordinaria FROM des_sisi_gestor.tbl_dispersiones td\r\n"
				+ "INNER JOIN des_sisi_gestor.tbl_procesos_inscripcion tpi ON td.id_proceso_inscripcion = tpi.proceso_inscripcion_id\r\n"
				+ "INNER JOIN des_sisi_gestor.cat_procesos_inscripcion cpi ON cpi.id_proceso = tpi.id_tipo_proceso\r\n"
				+ "WHERE cpi.id_proceso = 1 AND tpi.convocatoria_id = :convocatoriaId";
		
		Query query = entityManager.createNativeQuery(consulta);
		
		query.setParameter("convocatoriaId", dispercionParametros.getIdConvocatoriaSeleccionada());
		
		 Object resultado = query.getSingleResult();

		    if (resultado != null) {
		        // Convertimos el resultado a un entero para manejarlo
		    	int existe = ((Number) resultado).intValue();
		        dispercionParametros.setExistente(existe);

		        // Si existe dispersión, puedes agregar más lógica para poblar la lista
		        if (existe == 1) {
		        	dispercionParametros.setExistente(existe);
		        }else {
		        	dispercionParametros.setExistente(existe);
		        }
		    }
		
		return lista;
	}
	
	
	@Override
	public List<TblDispersiones> validarDispercionExistente(DispersionesParam dispercionParametros){
		
		List<TblDispersiones> lista = new ArrayList<TblDispersiones>();
		
		String consulta = "SELECT tir.id, tir.clave_asignatura FROM rel_proceso_inscipcion_planesyprogramas  rpi\r\n"
				+ "INNER JOIN tbl_ficha_descriptiva_programa rcp ON rpi.id_programa = rcp.id_programa\r\n"
				+ "INNER JOIN tbl_malla_curricular rmc ON rmc.id = rcp.id_eje_capacitacion\r\n"
				+ "INNER JOIN tbl_inscripcion_resumen tir ON rcp.identificador_final = tir.clave_asignatura AND tir.no_estudiantes > 0 AND tir.bloque = REGEXP_REPLACE(rmc.nombre, '[^0-9]', '')\r\n"
				+ "INNER JOIN tbl_procesos_inscripcion tpi  ON rpi.id_proceso_inscripcion = tpi.proceso_inscripcion_id\r\n"
				+ "WHERE rpi.id_proceso_inscripcion = :idProcesoInscripcion  AND rcp.identificador_final IS NOT NULL  AND rcp.identificador_final != '' AND tir.semestre = tpi.semestre AND EXISTS(SELECT tbd.id_inscripcion_resumen FROM tbl_dispersiones tbd WHERE tbd.id_inscripcion_resumen = tir.id);";
		
		
		Query query = entityManager.createNativeQuery(consulta);
		
		query.setParameter("idProcesoInscripcion", dispercionParametros.getIdProcesoInscripcion());

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {
				
				TblDispersiones dispersiones = mapeoDispercion(obj);
				lista.add(dispersiones);

			}
		}
		
		return lista;
		
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
	public List<TblDispersionesBusqueda> consultaDisperciones(DispersionesParam dispercionParametros){
		
		List<TblDispersionesBusqueda> lista = new ArrayList<TblDispersionesBusqueda>();
		
		String consultaBusquedaDispercion = "	SELECT\r\n"
				+ "    tbd.id_dispersion, tp.id_plan, tp.nombre plan, fd.id_programa,\r\n"
				+ "    fd.nombre_tentativo programa, fd.identificador_final clave,\r\n"
				+ "    IF((SELECT tmc2.nombre FROM tbl_malla_curricular tmc2 WHERE tmc2.id = tmc.id_padre ) IS NOT NULL, (SELECT tmc2.nombre FROM tbl_malla_curricular tmc2 WHERE tmc2.id = tmc.id_padre ), '')semestre ,\r\n"
				+ "    tmc.nombre bloque, count(ti.id) no_estudiantes_inscritos, tbd.no_grupos,\r\n"
				+ "    tbd.estudiantes_x_grupo, tbd.grupo_resto, tbd.estudiantes_resto\r\n"
				+ "	    FROM tbl_inscripciones ti\r\n"
				+ "         INNER JOIN rel_proceso_inscipcion_planesyprogramas rpi ON rpi.id_programa = ti.idprograma AND rpi.id_plan = ti.idplan\r\n"
				+ "         INNER JOIN tbl_procesos_inscripcion tpi ON tpi.proceso_inscripcion_id =  rpi.id_proceso_inscripcion\r\n"
				+ "         INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = ti.idprograma and ti.idplan = fd.id_plan\r\n"
				+ "         INNER JOIN tbl_planes tp ON tp.id_plan = ti.idplan\r\n"
				+ "         INNER JOIN tbl_malla_curricular tmc ON tmc.id = fd.id_eje_capacitacion\r\n"
				+ "         INNER JOIN tbl_inscripcion_resumen tir2 ON fd.identificador_final = tir2.clave_asignatura AND tir2.no_estudiantes > 0 AND tir2.bloque = SUBSTRING_INDEX(tmc.nombre,' ',-1) AND tir2.semestre = tpi.semestre\r\n"
				+ "         INNER JOIN tbl_dispersiones tbd ON tbd.id_inscripcion_resumen = tir2.id AND tbd.id_proceso_inscripcion = tpi.proceso_inscripcion_id\r\n"
				+ "		WHERE tpi.convocatoria_id = :idConvocatoria AND tpi.id_tipo_proceso = :idTipoProceso\r\n"
				+ "  AND tpi.proceso_inscripcion_id = :idProcesoInscripcion AND tpi.id_categoria_proceso = 1\r\n"
				+ "  AND (ti.fecha_registro >= tpi.fecha_inicio AND ti.fecha_registro <= tpi.fecha_fin)\r\n"
				+ "  group by tbd.id_dispersion, tp.id_plan, tp.nombre, fd.id_programa,\r\n"
				+ "    fd.nombre_tentativo, fd.identificador_final,\r\n"
				+ "    tmc.nombre, tpi.proceso_inscripcion_id, tir2.id, tbd.no_grupos,\r\n"
				+ "    tbd.estudiantes_x_grupo, tbd.grupo_resto, tbd.estudiantes_resto";
		
		Query queryConsultaDispercion = entityManager.createNativeQuery(consultaBusquedaDispercion);
		
		queryConsultaDispercion.setParameter("idConvocatoria", dispercionParametros.getIdConvocatoriaSeleccionada());
		queryConsultaDispercion.setParameter("idTipoProceso", dispercionParametros.getIdTipoProceso());
		queryConsultaDispercion.setParameter("idProcesoInscripcion", dispercionParametros.getIdProcesoInscripcion());

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
	public void actualizarDispersionExc(DispersionesParamNuevo dispercionParametros){
		
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
		queryCtualizar.setParameter("id",IdDispercion );
		queryCtualizar.executeUpdate();
		
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
		
		regresa.setIdDispersion((Integer) obj[0]);
		regresa.setGruposGenerales((Integer) obj[1]);
		regresa.setCupoGeneral((Integer) obj[2]);
		regresa.setGrupoResto((Integer) obj[3]);
		regresa.setCupoResto((Integer) obj[4]);
		regresa.setNoEstudiantes((Integer) obj[5]);
	
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
		regresa.setIdDispersion((Integer) obj[0]);
		regresa.setPlan(obj[2].toString());
		regresa.setPrograma(obj[4].toString());
		regresa.setClave(obj[5].toString());
		regresa.setSemestre(obj[6].toString());
		regresa.setBloque(obj[7].toString());
		regresa.setNoEstudiantes((Integer) obj[8]);
		regresa.setGruposGenerales((Integer) obj[9]);
		regresa.setCupoGeneral((Integer) obj[10]);
		regresa.setGrupoResto((Integer) obj[11]);
		regresa.setCupoResto((Integer) obj[12]);
		
	
		return regresa;
	}
	
	
	private TblDispersiones mapeoDispercion(Object[] obj) { 

		TblDispersiones regresa = new TblDispersiones();
		
		regresa.setExistente((Integer) obj[0]);
	
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
