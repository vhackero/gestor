package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.FuenteExternaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaSigeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.SelectImportarDTO;

@Repository
public class UsuariosImportarRepo implements IUsuariosImportarRepo {

	@Autowired
	public EntityManager entityManager;

	@Override
	public List<SelectImportarDTO> consultaConvocatorias() {

		List<SelectImportarDTO> regresa = new ArrayList<SelectImportarDTO>();

		String consulta = "SELECT convocatoria_id, nombre FROM tbl_convocatoria WHERE activo = 1" + "";

		Query query = entityManager.createNativeQuery(consulta);

		List<Object[]> lista = query.getResultList();

		if (!lista.isEmpty()) {
			for (Object[] obj : lista) {
				SelectImportarDTO dato = new SelectImportarDTO();
				dato.setId(obj[0] != null ? obj[0].toString() : null);
				dato.setNombre(obj[1] != null ? obj[1].toString() : null);
				regresa.add(dato);
			}
		}

		return regresa;

	}

	@Override
	public List<SelectImportarDTO> consultaFuenteExterna() {

		List<SelectImportarDTO> regresa = new ArrayList<SelectImportarDTO>();

		String consulta = "SELECT id_fuente_externa, nombre FROM cat_fuentes_externas WHERE activo = 1";

		Query query = entityManager.createNativeQuery(consulta);

		List<Object[]> lista = query.getResultList();

		if (!lista.isEmpty()) {
			for (Object[] obj : lista) {
				SelectImportarDTO dato = new SelectImportarDTO();
				dato.setId(obj[0] != null ? obj[0].toString() : null);
				dato.setNombre(obj[1] != null ? obj[1].toString() : null);
				regresa.add(dato);
			}
		}
		return regresa;
	}

	@Override
	public FuenteExternaDTO buscarFuenteExternaPorId(Integer idFuente) {
		String consulta = "SELECT id_fuente_externa, nombre, servidor, usuario, alias, nombre_base_datos, consulta "
				+ "FROM cat_fuentes_externas WHERE activo = 1 AND id_fuente_externa = :idFuente";
		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("idFuente", idFuente);
		List<Object[]> resultado = query.getResultList();
		if (resultado.isEmpty()) {
			return null;
		}
		Object[] fila = resultado.get(0);
		FuenteExternaDTO dto = new FuenteExternaDTO();
		dto.setId(fila[0] != null ? Integer.parseInt(fila[0].toString()) : null);
		dto.setNombre(fila[1] != null ? fila[1].toString() : null);
		dto.setServidor(fila[2] != null ? fila[2].toString() : null);
		dto.setUsuario(fila[3] != null ? fila[3].toString() : null);
		dto.setAlias(fila[4] != null ? fila[4].toString() : null);
		dto.setNombreBaseDatos(fila[5] != null ? fila[5].toString() : null);
		dto.setConsulta(fila[6] != null ? fila[6].toString() : null);
		return dto;
	}

	@Override
	public List<SelectImportarDTO> consultaPlanesActivos() {
		String consulta = "SELECT tmc.id_plan, tmc.nombre FROM tbl_malla_curricular tmc WHERE tmc.activo = 1 AND tmc.id_plan IS NOT NULL";
		return obtenerSelectImportarDTO(consulta);
	}

	@Override
	public List<SelectImportarDTO> consultaSemestresPorPlan(Integer idPlan) {
		StringBuilder consulta = new StringBuilder();
		consulta.append("SELECT hijo.id, hijo.nombre ");
		consulta.append("FROM tbl_malla_curricular hijo ");
		consulta.append("WHERE hijo.id_padre = (");
		consulta.append("    SELECT plan.id FROM tbl_malla_curricular plan ");
		consulta.append("    WHERE plan.id_plan = :idPlan AND plan.id_padre IS NULL LIMIT 1");
		consulta.append(") AND hijo.activo = 1");
		return obtenerSelectImportarDTO(consulta.toString(), "idPlan", idPlan);
	}

	@Override
	public List<SelectImportarDTO> consultaBloquesPorSemestre(Integer idSemestre) {
		String consulta = "SELECT tmc.id, tmc.nombre FROM tbl_malla_curricular tmc WHERE tmc.id_padre = :idSemestre AND tmc.activo = 1";
		return obtenerSelectImportarDTO(consulta, "idSemestre", idSemestre);
	}

	@Override
	public List<SelectImportarDTO> consultaProgramasPorEje(Integer idEjeCapacitacion) {
		String consulta = "SELECT tfdp.id_programa, tfdp.nombre_tentativo FROM tbl_ficha_descriptiva_programa tfdp WHERE tfdp.id_eje_capacitacion = :idEjeCapacitacion";
		return obtenerSelectImportarDTO(consulta, "idEjeCapacitacion", idEjeCapacitacion);
	}

	@Override
	public List<SelectImportarDTO> consultaPeriodosInscripcion() {
		String consulta = "SELECT tpi.nombre_periodo FROM tbl_periodos_inscripcion tpi";
		return obtenerSelectImportarDTO(consulta);
	}

	@Override
	public List<SelectImportarDTO> consultaEventosPorPeriodoYPrograma(String nombrePeriodo, Integer idPrograma) {
		String consulta = "SELECT te.id_evento, te.nombre_ec FROM tbl_eventos te WHERE te.cve_evento_cap LIKE CONCAT('%',:nombrePeriodo,'%') AND te.id_programa = :idPrograma";
		return obtenerSelectImportarDTO(consulta, "nombrePeriodo", nombrePeriodo, "idPrograma", idPrograma);
	}

	@Override
	public List<SelectImportarDTO> consultaGruposPorEvento(Integer idEvento) {
		String consulta = "SELECT tg.id, tg.nombre FROM tbl_grupos tg WHERE tg.id_evento = :idEvento";
		return obtenerSelectImportarDTO(consulta, "idEvento", idEvento);
	}

	private List<SelectImportarDTO> obtenerSelectImportarDTO(String consulta, Object... parametros) {
		List<SelectImportarDTO> resultado = new ArrayList<>();
		Query query = entityManager.createNativeQuery(consulta);
		if (parametros != null && parametros.length > 0) {
			for (int i = 0; i < parametros.length; i += 2) {
				String nombre = parametros[i].toString();
				Object valor = parametros[i + 1];
				query.setParameter(nombre, valor);
			}
		}

		List<?> lista = query.getResultList();

		if (!lista.isEmpty()) {
			for (Object fila : lista) {
				SelectImportarDTO dato = new SelectImportarDTO();
				if (fila instanceof Object[]) {
					Object[] obj = (Object[]) fila;
					dato.setId(obj[0] != null ? obj[0].toString() : null);
					dato.setNombre(obj.length > 1 && obj[1] != null ? obj[1].toString() : dato.getId());
				} else {
					dato.setId(fila != null ? fila.toString() : null);
					dato.setNombre(dato.getId());
				}
				resultado.add(dato);
			}
		}
		return resultado;
	}

	@Override
	public List<PersonaSigeDTO> consultaPersonasImportar(String fuenteExterna, String convocatoria) {

		List<PersonaSigeDTO> listaPersonas = new ArrayList<PersonaSigeDTO>();

		String consulta = "SELECT * FROM tbl_persona_externos tpe " + "WHERE tpe.id_fuente_externa = :fuenteExterna "
				+ "AND tpe.id_convocatoria = :convocatoria "
				+ "AND NOT EXISTS(SELECT tp.id_persona FROM tbl_persona tp "
				+ "WHERE tp.sso_idUsuario = tpe.nombre_usuario)";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("fuenteExterna", fuenteExterna);
		query.setParameter("convocatoria", convocatoria);

		List<Object[]> lista = query.getResultList();

		if (!lista.isEmpty()) {
			for (Object[] obj : lista) {
				PersonaSigeDTO personaObj = new PersonaSigeDTO();

				personaObj.setIdPersonaSige(obj[0] != null ? Long.valueOf(obj[0].toString()) : null);
				personaObj.setMatricula(obj[1] != null ? obj[1].toString() : null);
				personaObj.setPassword(obj[2] != null ? obj[2].toString() : null);
				personaObj.setNombre(obj[3] != null ? obj[3].toString() : null);
				personaObj.setApellidoPaterno(obj[4] != null ? obj[4].toString() : null);
				personaObj.setApellidoMaterno(obj[5] != null ? obj[5].toString() : null);
				personaObj.setProgramaEducativo(obj[6] != null ? obj[6].toString() : null);
				personaObj.setDivision(obj[7] != null ? obj[7].toString() : null);
				personaObj.setCorreoInstitucional(obj[8] != null ? obj[8].toString() : null);
				personaObj.setFechaNacimiento(convertStringToDate(obj[9].toString()));
				personaObj.setCurp(obj[10] != null ? obj[10].toString() : null);
				personaObj.setNivelSige(obj[11] != null ? obj[11].toString() : null);
				listaPersonas.add(personaObj);
			}
		}
		return listaPersonas;
	}

	@Transactional
	@Override
	public void insertAspirante(String idPersonaRegistrada, String programaEducativo, String idConvocatoria) {
		String consulta = "INSERT INTO tbl_persona_aspirante ( id_persona, id_plan, id_convocatoria) VALUES ( ?, ?, ?)";
		entityManager.createNativeQuery(consulta).setParameter(1, idPersonaRegistrada)
				.setParameter(2, programaEducativo).setParameter(3, idConvocatoria).executeUpdate();
	}

	public static Date convertStringToDate(String dateString) {
	    if (dateString != null) {
	        try {
	            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	            return formatter.parse(dateString);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	    }
	    return null;
	}
	
	@Override
	public boolean verificarRelacionConvocatoria(String planId, String convocatoriaId) {
	    String consulta = "SELECT IF(COUNT(tc.convocatoria_id) > 0, 1, 0) AS existe_relacion " +
	                      "FROM tbl_convocatoria tc " +
	                      "INNER JOIN rel_convocatoria_planesyprogramas rcpp " +
	                      "ON rcpp.id_convocatoria = tc.convocatoria_id AND rcpp.id_plan = :plan_id " +
	                      "WHERE tc.convocatoria_id = :convocatoria_id";
	    
		Query query = entityManager.createNativeQuery(consulta);
		
		String[] partes = planId.split("\\.");
		Integer plan = Integer.parseInt(partes[0]);
	

		query.setParameter("plan_id", plan);
		
		String[] partes2 = convocatoriaId.split("\\.");		
		Integer convocatoria2 = Integer.parseInt(partes2[0]);

		
		query.setParameter("convocatoria_id", convocatoria2);
		Integer resultado = 0;
		
		List<BigInteger> lista = query.getResultList();
		
		if (!lista.isEmpty()) {
			 BigInteger rel = lista.get(0);
			 resultado = rel.intValue();
		}
		
	    return resultado == 1;
	}


}
