package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

	@Override
	public void insertAspirante(String idPersonaRegistrada, String programaEducativo, String idConvocatoria) {
		String consulta = "INSERT INTO tbl_persona_aspirante (id_persona_aspirante, id_persona, id_plan, id_convocatoria) VALUES (NULL, ?, ?, ?)";
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
		query.setParameter("plan_id", planId);
		query.setParameter("convocatoria_id", convocatoriaId);
		Integer resultado = 0;
		Object[] obj = (Object[]) query.getSingleResult();
		if (obj != null) {
			resultado = Integer.valueOf(obj[0].toString());
		}

	    return resultado == 1;
	}


}
