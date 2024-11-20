package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Dispersiones;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ProcesosInscripcion;

@Repository
public class DispersionesRepository implements IDispersionesRepository {

	@Autowired
	public EntityManager entityManager;
	
	@Override
	public List<ProcesosInscripcion> consultarProcesoInscripcion() {

		List<ProcesosInscripcion> lista = new ArrayList<ProcesosInscripcion>();

		//String consulta = "SELECT  t.proceso_inscripcion_id,t.nombre,t.clave_proceso,t.descripcion,t.fecha_inicio,t.fecha_fin,t.estatus,t.semestre,t.perfil,t.id_tipo_proceso,t.id_categoria_proceso,t.convocatoria_id\r\n"
		String consulta = "SELECT  t.proceso_inscripcion_id,t.nombre \r\n"
				+ "FROM tbl_procesos_inscripcion t\r\n"
				+ "WHERE convocatoria_id = 'id_convocatoria_selecionada' \r\n"
				+ "AND id_tipo_proceso = 'id_tipo_de_proceso_seleccioanado' \r\n"
				+ "AND id_categoria_proceso = 1";

		Query query = entityManager.createNativeQuery(consulta);

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				ProcesosInscripcion dispersiones = mapeo(obj);
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
	
	
}
