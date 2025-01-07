package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.math.BigInteger;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaNivelEducativo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaParamConsulta;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaTableroResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionesConsultaResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionesTableroResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoProceso;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;

@Repository
public class InscripcionesRepository implements IinscripcionesRepository {

	@Autowired
	public EntityManager entityManager;

	@Override
	public List<TipoProceso> consultarTipoProceso() {

		List<TipoProceso> lista = new ArrayList<TipoProceso>();

		String consulta = "SELECT cp.id_proceso, cp.nombre FROM cat_procesos_inscripcion cp WHERE cp.activo = 1";

		Query query = entityManager.createNativeQuery(consulta);

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				TipoProceso convocatoria = mapeo(obj);
				lista.add(convocatoria);

			}
		}

		return lista;

	}

	
	@Override
	public List<TblPlan> consultarPlan() {

		List<TblPlan> lista = new ArrayList<TblPlan>();

		String consulta = "select tp.id_plan, tp.nombre from tbl_planes tp where tp.id_estatus_plan = 1";

		Query query = entityManager.createNativeQuery(consulta);

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				TblPlan tblPlan = mapeoTblPlan(obj);
				lista.add(tblPlan);

			}
		}

		return lista;

	}
	
	@Override
	public List<TipoProceso> consultarNombre(ConvocatoriaParamConsulta tableroParamConsulta) {

		List<TipoProceso> lista = new ArrayList<TipoProceso>();

		String consulta = "SELECT t.*\r\n"
				+ "FROM tbl_procesos_inscripcion t\r\n"
				+ "WHERE convocatoria_id = :id_convocatoria_selecionada AND id_tipo_proceso = :id_del_tipo_proceso AND id_categoria_proceso = 1";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_convocatoria_selecionada", tableroParamConsulta.getValueConvocatoriaEstatus());
		query.setParameter("id_del_tipo_proceso", tableroParamConsulta.getConsulNivelEducativo());

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				TipoProceso convocatoria = mapeo(obj);
				lista.add(convocatoria);

			}
		}

		return lista;

	}

	@Override
	public List<InscripcionesTableroResumen> consultarTableroResumen(ConvocatoriaParamConsulta tableroParamConsulta) {

		List<InscripcionesTableroResumen> lista = new ArrayList<InscripcionesTableroResumen>();

		String consulta = "SELECT tc.nombre convocatoria ,tp.nombre plan,fd.id_programa, fd.nombre_tentativo programa, fd.identificador_final clave, (SELECT tmc2.nombre FROM tbl_malla_curricular tmc2 WHERE tmc2.id = tmc.id_padre) semestre, tmc.nombre bloque,  count(ti.id) no_estudiantes_inscritos FROM tbl_inscripciones ti\r\n"
				+ "                                                                                                                                                                                                                                                                INNER JOIN rel_proceso_inscipcion_planesyprogramas rpi ON rpi.id_programa = ti.idprograma AND rpi.id_plan = ti.idplan\r\n"
				+ "                                                                                                                                                                                                                                                                INNER JOIN tbl_procesos_inscripcion tpi ON tpi.proceso_inscripcion_id =  rpi.id_proceso_inscripcion\r\n"
				+ "                                                                                                                                                                                                                                                                INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = ti.idprograma and ti.idplan = fd.id_plan\r\n"
				+ "                                                                                                                                                                                                                                                                INNER JOIN tbl_planes tp ON tp.id_plan = ti.idplan\r\n"
				+ "                                                                                                                                                                                                                                                                INNER JOIN tbl_malla_curricular tmc ON tmc.id = fd.id_eje_capacitacion\r\n"
				+ "                                                                                                                                                                                                                                                                INNER JOIN tbl_convocatoria tc ON tc.convocatoria_id = tpi.convocatoria_id\r\n"
				+ "WHERE tpi.convocatoria_id = :id_convocatoria_selecionada AND tpi.id_tipo_proceso = :id_del_tipo_proceso  AND (ti.fecha_registro >= tpi.fecha_inicio AND ti.fecha_registro <= tpi.fecha_fin) AND (tpi.proceso_inscripcion_id = :id_del_nombre_selecionado AND tpi.id_categoria_proceso = 1 )\r\n"
				+ "group by rpi.id_programa";

		Query query = entityManager.createNativeQuery(consulta);
		
		query.setParameter("id_convocatoria_selecionada", tableroParamConsulta.getValueConvocatoriaEstatus());
		query.setParameter("id_del_tipo_proceso", tableroParamConsulta.getConsulNivelEducativo());
		query.setParameter("id_del_nombre_selecionado", tableroParamConsulta.getConsulNombreCorto());



		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				InscripcionesTableroResumen tblPlan = tableroInscrip(obj);
				lista.add(tblPlan);

			}
		}

		return lista;

	}

	@Override
	public List<InscripcionesConsultaResumen> consultarFiltros(ConvocatoriaParamConsulta tableroParamConsulta) {

	    List<InscripcionesConsultaResumen> lista = new ArrayList<>();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	    // Inicializar consulta base
	    StringBuilder consulta = new StringBuilder(
	        "SELECT tp.proceso_inscripcion_id, \n" +
	        "tp.convocatoria_id, \n" +
	        "tc.nombre convocatoria, \n" +
	        "tp.nombre nombre, \n" +
	        "tp.fecha_inicio, \n" +
	        "tp.fecha_fin, \n" +
	        "cp.nombre tipo_proceso, \n" +
	        "IF(tp.estatus = 0, 'Inactivo', 'Activo') estatus,\n" +
	        "tp.id_tipo_proceso\n"+
	        "FROM tbl_procesos_inscripcion tp\n" +
	        "INNER JOIN tbl_convocatoria tc ON tc.convocatoria_id = tp.convocatoria_id\n" +
	        "INNER JOIN cat_procesos_inscripcion cp ON cp.id_proceso = tp.id_tipo_proceso\n" +
	        "WHERE tp.id_categoria_proceso = 1\n" +
	        "AND tp.convocatoria_id = :id_convocatoria_selecionada "
	    );

	    // Variables auxiliares para agregar filtros opcionales
	    boolean hasNombre = tableroParamConsulta.getConsulNombreCorto() != null && !tableroParamConsulta.getConsulNombreCorto().isEmpty();
	    boolean hasTipoProceso = tableroParamConsulta.getConsulNivelEducativo() != null;
	    boolean hasFechaInicio = tableroParamConsulta.getConsulFechaApertura() != null;
	    boolean hasFechaFin = tableroParamConsulta.getConsulFechaCierre() != null;

	    // Añadir filtros opcionales
	    if (hasNombre) {
	        consulta.append("AND tp.nombre LIKE :nombre ");
	    }
	    if (hasTipoProceso) {
	        consulta.append("AND tp.id_tipo_proceso = :id_tipo_proceso ");
	    }
	    if (hasFechaInicio && hasFechaFin) {
	        consulta.append("AND tp.fecha_inicio >= :fecha_inicio AND tp.fecha_fin <= :fecha_fin ");
	    }

	    Query query = entityManager.createNativeQuery(consulta.toString());

	    // Parámetro obligatorio
	    query.setParameter("id_convocatoria_selecionada", tableroParamConsulta.getValueConvocatoriaEstatus());

	    // Parámetros opcionales
	    if (hasNombre) {
	        query.setParameter("nombre", "%" + tableroParamConsulta.getConsulNombreCorto() + "%");
	    }
	    if (hasTipoProceso) {
	        query.setParameter("id_tipo_proceso", tableroParamConsulta.getConsulNivelEducativo());
	    }
	    if (hasFechaInicio && hasFechaFin) {
	        String fechaInicioStr = sdf.format(tableroParamConsulta.getConsulFechaApertura());
	        String fechaFinStr = sdf.format(tableroParamConsulta.getConsulFechaCierre());
	        query.setParameter("fecha_inicio", fechaInicioStr);
	        query.setParameter("fecha_fin", fechaFinStr);
	    }

	    // Ejecutar consulta
	    List<Object[]> listaQuery = query.getResultList();

	    if (!listaQuery.isEmpty()) {
	        for (Object[] obj : listaQuery) {
	            InscripcionesConsultaResumen tblPlan = mapFiltrosInscrip(obj);
	            lista.add(tblPlan);
	        }
	    }

	    return lista;
	}
	 
	private InscripcionesConsultaResumen mapFiltrosInscrip(Object[] obj) {

		InscripcionesConsultaResumen regresa = new InscripcionesConsultaResumen();

			regresa.setProcesoInscripcionId(obj[0].toString());
			regresa.setIdConvocatoria(obj[1].toString());
			regresa.setNombreConvocatoria(obj[2].toString());
			regresa.setNombre(obj[3].toString());
			regresa.setFecIni(obj[4].toString());
			regresa.setFecFin(obj[5].toString());
			regresa.setTipoProceso(obj[6].toString());
			regresa.setEstatus(obj[7].toString());
			regresa.setIdTipoProceso(obj[8].toString());

			return regresa;
		}
	 
	
		private InscripcionesTableroResumen tableroInscrip(Object[] obj) {

		 InscripcionesTableroResumen regresa = new InscripcionesTableroResumen();
		 
			regresa.setConvocatoria(obj[0].toString());
			regresa.setPlan(obj[1].toString());
			regresa.setIdPrograma(obj[2].toString());
			regresa.setPrograma(obj[3].toString());
			regresa.setClave(obj[4].toString());
			regresa.setSemestre(obj[5].toString());
			regresa.setBloque(obj[6].toString());
			regresa.setNoEstudiantesInscritos(obj[7].toString());

			return regresa;
		}
	 
	 
	private TblPlan mapeoTblPlan(Object[] obj) {

		TblPlan regresa = new TblPlan();

		regresa.setIdPlan((Integer) obj[0]);
		regresa.setNombre(obj[1].toString());

		return regresa;
	}
	
	private TipoProceso mapeo(Object[] obj) {

		TipoProceso regresa = new TipoProceso();

		regresa.setIdProceso((Integer) obj[0]);
		regresa.setNombre(obj[1].toString());

		return regresa;
	}
	
	@Override
	public Object getProcesoInscripcionById(Long procesoInscripcionId) {
        String query = "SELECT * FROM tbl_procesos_inscripcion WHERE proceso_inscripcion_id = :procesoInscripcionId";
        try {
            return entityManager.createNativeQuery(query)
                                .setParameter("procesoInscripcionId", procesoInscripcionId)
                                .getSingleResult();
        } catch (NoResultException e) {
            return null; // No se encontró el registro
        }
    }

    // Método para actualizar los campos permitidos
	@Transactional
	@Override
	public void updateProcesoInscripcion(
	        Long procesoInscripcionId,
	        String nombre,
	        LocalDateTime fechaInicio,
	        LocalDateTime fechaFin,
	        int estatus,
	        Long idTipoProceso,
	        Long convocatoriaId) {

	    String query = "UPDATE tbl_procesos_inscripcion " +
	                   "SET nombre = ?, " +
	                   "fecha_inicio = ?, " +
	                   "fecha_fin = ?, " +
	                   "estatus = ?, " +
	                   "id_tipo_proceso = ?, " +
	                   "convocatoria_id = ? " +
	                   "WHERE proceso_inscripcion_id = ?";

	    entityManager.createNativeQuery(query)
	                 .setParameter(1, nombre)
	                 .setParameter(2, fechaInicio)
	                 .setParameter(3, fechaFin)
	                 .setParameter(4, estatus)
	                 .setParameter(5, idTipoProceso)
	                 .setParameter(6, convocatoriaId)
	                 .setParameter(7, procesoInscripcionId)
	                 .executeUpdate();
	}
	
	@Transactional
	@Override
	public void deleteProcesoInscripcion(Long procesoInscripcionId, Long convocatoriaId, String tipoProceso) {
	    try {
	        // 1. Eliminar registros relacionados en tbl_terminosycondiciones
	        String deleteTerminosQuery = "DELETE FROM tbl_terminosycondiciones WHERE id_proceso_inscripcion = ?";
	        entityManager.createNativeQuery(deleteTerminosQuery)
	                     .setParameter(1, procesoInscripcionId)
	                     .executeUpdate();

	        // 2. Eliminar registros relacionados en tbl_encuestas_contestadas
	        String deleteEncuestasQuery = "DELETE FROM tbl_encuestas_contestadas WHERE id_proceso_inscripcion = ?";
	        entityManager.createNativeQuery(deleteEncuestasQuery)
	                     .setParameter(1, procesoInscripcionId)
	                     .executeUpdate();

	        // 3. Eliminar registros relacionados en tbl_dispersiones
	        String deleteDispersionesQuery = "DELETE FROM tbl_dispersiones WHERE id_proceso_inscripcion = ?";
	        entityManager.createNativeQuery(deleteDispersionesQuery)
	                     .setParameter(1, procesoInscripcionId)
	                     .executeUpdate();    
            //4. Eliminar registros en rel_proceso_inscipcion_planesyprogramas
            String deleteRelacionesQuery = "DELETE FROM rel_proceso_inscipcion_planesyprogramas WHERE id_proceso_inscripcion = ?";
            entityManager.createNativeQuery(deleteRelacionesQuery)
                                                 .setParameter(1, procesoInscripcionId)
                                                 .executeUpdate();
	        
	        if (tipoProceso.equalsIgnoreCase("Ordinario")) {
	        	
	            // 5. Eliminar registros en tbl_inscripcion_resumen
		        String deleteResumenQuery = "DELETE FROM tbl_inscripcion_resumen WHERE id_convocatoria = ?";
		        entityManager.createNativeQuery(deleteResumenQuery)
		                     .setParameter(1, convocatoriaId)
		                     .executeUpdate();

	        }
	        
	        // 6. Eliminar el registro principal en tbl_procesos_inscripcion
	        String deletePrincipalQuery = "DELETE FROM tbl_procesos_inscripcion WHERE proceso_inscripcion_id = ?";
	        entityManager.createNativeQuery(deletePrincipalQuery)
	                     .setParameter(1, procesoInscripcionId)
	                     .executeUpdate();
	    } catch (Exception e) {
	        throw new RuntimeException("Error al eliminar el proceso de inscripción y sus relaciones", e);
	    }
	}
	
//	
//	@Override
//	public List<ConvocatoriaTableroResumen> consultarTableroResumen(Integer convocatoriaId) {
//
//		List<ConvocatoriaTableroResumen> lista = new ArrayList<ConvocatoriaTableroResumen>();
//
//		String consulta = "SELECT tc.nombre nombre_convocatoria, tp.nombre nombre_plan,  COUNT(tpa.id_persona_aspirante) total FROM tbl_persona_aspirante tpa\r\n"
//				+ "INNER JOIN tbl_planes tp ON tp.id_plan = tpa.id_plan\r\n"
//				+ "INNER JOIN tbl_convocatoria tc ON tc.convocatoria_id = tpa.id_convocatoria\r\n"
//				+ "WHERE tpa.id_convocatoria = :convocatoriaId \r\n"
//				+ "GROUP BY tpa.id_plan";
//
//		Query query = entityManager.createNativeQuery(consulta);
//		query.setParameter("convocatoriaId", convocatoriaId);
//		
//		List<Object[]> listaQuery = query.getResultList();
//
//		if (!listaQuery.isEmpty()) {
//			for (Object[] obj : listaQuery) {
//
//				ConvocatoriaTableroResumen convocatoria = mapeoTablero(obj);
//				lista.add(convocatoria);
//
//			}
//		}
//
//		return lista;
//
//	}
//	
//	
//	
//	
//	private ConvocatoriaTableroResumen mapeoTablero(Object[] obj) {
//
//		ConvocatoriaTableroResumen regresa = new ConvocatoriaTableroResumen();
//		
//		regresa.setNombreConvocatoria(obj[0].toString());
//		regresa.setNombrePlan(obj[1].toString());
//		BigInteger total = (BigInteger) obj[2];
//		regresa.setTotal(total);
//
//		return regresa;
//	}
//	
//	
//	@Override
//	public List<Convocatoria> consultarConvocatoriasFiltros(ConvocatoriaParamConsulta convocatoriaParamConsulta) {
//
//		List<Convocatoria> lista = new ArrayList<Convocatoria>();
//
////		String consulta = "SELECT tb.convocatoria_id,tb.nombre,tb.nombre_corto,tb.descripcion,tb.fecha_apertura,tb.fecha_cierre,\r\n"
////				+ "tb.semestre,tb.tipo,tb.url_convocatoria,tb.activo,tb.fecha_alta,tb.fecha_modificacion,tb.cupo_limite FROM tbl_convocatoria tb WHERE tb.activo = 1";
//		
//		String consulta = "SELECT DISTINCT(tb.convocatoria_id),tb.nombre,tb.nombre_corto,tb.descripcion,tb.fecha_apertura,tb.fecha_cierre, tb.semestre,tb.tipo,tb.url_convocatoria,tb.activo,tb.fecha_alta,tb.fecha_modificacion,tb.cupo_limite \r\n"
//				+ "FROM tbl_convocatoria tb\r\n"
//				+ "         INNER JOIN rel_convocatoria_planesyprogramas rcpp ON rcpp.id_convocatoria = tb.convocatoria_id\r\n"
//				+ "         INNER JOIN cat_nivel_ensenanza_programa cne ON cne.id = rcpp.id_nivel_ensenanza ";
//		
//		String queryFiltro = obtieneFiltro(convocatoriaParamConsulta);
//		
//		String es = " AND cne.activo = 1";
//		
//		Query query = entityManager.createNativeQuery(consulta.concat(queryFiltro).concat(es));
//
//		List<Object[]> listaQuery = query.getResultList();
//
//		if (!listaQuery.isEmpty()) {
//			for (Object[] obj : listaQuery) {
//
//				Convocatoria convocatoria = mapeo2(obj);
//				lista.add(convocatoria);
//
//			}
//		}
//
//		return lista;
//
//	}
//	
//	private String obtieneFiltro(ConvocatoriaParamConsulta convocatoriaParamConsulta) {
//		
//		StringBuilder filtro = new StringBuilder("");
//		boolean isPrimerFiltro = false;
//		
//		
//		if (!("").equals(convocatoriaParamConsulta.getConsulNombreConvocatoria())) {
//			filtro.append(validaOperador(isPrimerFiltro)+"tb.nombre = '").append(convocatoriaParamConsulta.getConsulNombreConvocatoria()).append("'").append(" ");
//			isPrimerFiltro = true;
//		}
//		
//		if (!("").equals(convocatoriaParamConsulta.getConsulNombreCorto())) {
//			filtro.append(validaOperador(isPrimerFiltro)+"tb.nombre_corto = '").append(convocatoriaParamConsulta.getConsulNombreCorto()).append("'").append(" ");
//			isPrimerFiltro = true;
//		}
//		
//		if (!("").equals(convocatoriaParamConsulta.getValueConvocatoriaEstatus())) {
//			filtro.append(validaOperador(isPrimerFiltro)+"tb.activo = ").append(convocatoriaParamConsulta.getValueConvocatoriaEstatus()).append(" ");
//			isPrimerFiltro = true;
//		}
//		
//		if (!("0").equals(convocatoriaParamConsulta.getConsulNivelEducativo())) {
//			filtro.append(validaOperador(isPrimerFiltro)+"rcpp.id_nivel_ensenanza = ").append(convocatoriaParamConsulta.getConsulNivelEducativo()).append(" ");
//			isPrimerFiltro = true;
//		}
//		
//		
//		obtieneFiltroFechas(filtro, convocatoriaParamConsulta, isPrimerFiltro);
//		
//		return filtro.toString();
//		
//	}
//	
//	private StringBuilder obtieneFiltroFechas(StringBuilder filtro, ConvocatoriaParamConsulta filter, boolean isPrimerFiltro) {
//		
//		if (!filter.getConsulFechaApertura().trim().equals("") && !filter.getConsulFechaCierre().trim().equals("")) {
//			filtro.append(validaOperador(isPrimerFiltro)+"tb.fecha_apertura BETWEEN '")
//			.append(filter.getConsulFechaApertura()).append("' AND '").append(filter.getConsulFechaApertura()).append("'");
//		}
//		
//		if (!filter.getConsulFechaApertura().trim().equals("") && !filter.getConsulFechaCierre().trim().equals("")) {
//			filtro.append(validaOperador(isPrimerFiltro)+"tb.fecha_cierre BETWEEN '")
//			.append(filter.getConsulFechaCierre()).append("' AND '").append(filter.getConsulFechaCierre()).append("'");
//		}
//		
//		return filtro;
//		
//	}
//	
//	 public static String validaOperador(boolean filtro) {
//			String operador = "";
//			if (!filtro) {
//				operador = " WHERE ";
//			} else {
//				operador = " AND ";
//			}			
//			return operador;
//		}
//	
//	private Convocatoria mapeo2(Object[] obj) {
//
//		Convocatoria regresa = new Convocatoria();
//
//		regresa.setConvocatoriaId((Integer) obj[0]);
//		regresa.setNombre(obj[1].toString());
//		regresa.setNombreCorto(obj[2].toString());
//		regresa.setDescripcion(obj[3].toString());
//		regresa.setFecha_Apertura((java.util.Date) obj[4]);
//		regresa.setFechaCierre((java.util.Date) obj[5]);
//		regresa.setSemestre((Integer) obj[6]);
//		regresa.setTipo( obj[7].toString());
//		regresa.setUrlConvocatoria(obj[8].toString());
//		regresa.setActivo( obj[9].toString());
//		regresa.setFechaAlta((java.util.Date) obj[10]);
//		regresa.setFechaModificacion((java.util.Date) obj[11]);
//		regresa.setCupoLimite((Integer) obj[12]);
//
//		return regresa;
//	}
//	
//	
//	@Override
//	public List<ConvocatoriaNivelEducativo> consultarNivelEducativo() {
//
//		List<ConvocatoriaNivelEducativo> lista = new ArrayList<ConvocatoriaNivelEducativo>();
//
//		String consulta = "SELECT cne.id, cne.nombre FROM cat_nivel_ensenanza_programa cne WHERE cne.activo = 1";
//
//		Query query = entityManager.createNativeQuery(consulta);
//
//		List<Object[]> listaQuery = query.getResultList();
//
//		if (!listaQuery.isEmpty()) {
//			for (Object[] obj : listaQuery) {
//
//				ConvocatoriaNivelEducativo convocatoria = mapeoNivel(obj);
//				lista.add(convocatoria);
//
//			}
//		}
//
//		return lista;
//
//	}
//	
//	private ConvocatoriaNivelEducativo mapeoNivel(Object[] obj) {
//
//		ConvocatoriaNivelEducativo regresa = new ConvocatoriaNivelEducativo();
//
//		regresa.setId((Integer) obj[0]);
//		regresa.setNombre(obj[1].toString());
//
//		return regresa;
//	}
//	
//	
//	@Override
//	public void altaConvocatorias() {
//
//		List<ConvocatoriaNivelEducativo> lista = new ArrayList<ConvocatoriaNivelEducativo>();
//
//		String consulta = "SELECT cne.id, cne.nombre FROM cat_nivel_ensenanza_programa cne WHERE cne.activo = 1";
//
//		Query query = entityManager.createNativeQuery(consulta);
//
//		 query.getResultList();
//
//		
//		
//
//	}
//	
//	@Transactional
//	@Override
//	public void eliminarConvocatorias(Convocatoria elminarConvo) {
//
//		String consulta = "DELETE FROM tbl_convocatoria WHERE convocatoria_id = :id";
//		String consulta2 = "DELETE FROM rel_convocatoria_planesyprogramas WHERE id_convocatoria = :id";
//
//		Query query2 = entityManager.createNativeQuery(consulta2);
//        query2.setParameter("id", elminarConvo.getConvocatoriaId());
//        query2.executeUpdate();
//        
//		Query query = entityManager.createNativeQuery(consulta);
//        query.setParameter("id", elminarConvo.getConvocatoriaId());
//        query.executeUpdate();
// 
//	}

}

