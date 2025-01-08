package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Dispersiones;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParam;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ProcesosInscripcion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoMatriculacion;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;

@Repository
public class DispersionesRepository implements IDispersionesRepository {

	@Autowired
	public EntityManager entityManager;
	
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
	
	
	private ProcesosInscripcion mapeo(Object[] obj) {

		ProcesosInscripcion regresa = new ProcesosInscripcion();
		
		regresa.setIdProcesoInscripcion((Integer) obj[0]);
		regresa.setNombre(obj[1].toString());
	
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
