package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.HistorialAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.HistorialAcademicoListaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TiraMateriaBajaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TiraMateriaDTO;

@Repository
public class HistorialAcademicoRepo implements IHistorialAcademicoRepo {

	@Autowired
	public EntityManager entityManager;

	@Override
	public HistorialAcademicoDTO consultaDatosHistorialAcademico(String id_persona) {

		HistorialAcademicoDTO regresa = new HistorialAcademicoDTO();

		String consulta = "SELECT  DISTINCT(p.sso_idUsuario) matricula,\n" +
				"                CONCAT(cne.nombre,' en ', pl.nombre) programaEducativo,\n" +
				"                (SELECT SUM(rgp.calificacion_final)/COUNT(rgp.calificacion_final)\n" +
				"                 FROM tbl_persona tp\n" +
				"                          INNER JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = tp.id_persona\n" +
				"                 WHERE tp.id_persona = p.id_persona  AND NOT EXISTS (SELECT NULL\n" +
				"                                                                     FROM rel_persona_bajas t2\n" +
				"                                                                              INNER JOIN rel_motivo_baja mb2 ON mb2.id_motivo_baja = t2.motivo_baja_id AND (mb2.tipo_baja_id = 1 OR mb2.tipo_baja_id = 2)\n" +
				"                                                                     WHERE t2.id_persona = tp.id_persona AND rgp.id_grupo = t2.id_grupo) ) promedio,\n" +
				"                (SELECT SUM(tblf.creditos)\n" +
				"                 FROM tbl_persona tp\n" +
				"                          INNER JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = tp.id_persona\n" +
				"                          INNER JOIN tbl_grupos tblg ON tblg.id = rgp.id_grupo\n" +
				"                          INNER JOIN tbl_eventos tble ON tble.id_evento = tblg.id_evento\n" +
				"                          INNER JOIN tbl_ficha_descriptiva_programa tblf ON tblf.id_programa = tble.id_programa\n" +
				"                 WHERE tp.id_persona = p.id_persona  AND rgp.calificacion_final >= tble.calificacion_min_aprobatoria AND NOT EXISTS (SELECT NULL\n" +
				"                                                                                                                                     FROM rel_persona_bajas t2\n" +
				"                                                                                                                                              INNER JOIN rel_motivo_baja mb2 ON mb2.id_motivo_baja = t2.motivo_baja_id AND (mb2.tipo_baja_id = 1 OR mb2.tipo_baja_id = 2)\n" +
				"                                                                                                                         WHERE t2.id_persona = tp.id_persona AND rgp.id_grupo = t2.id_grupo) ) creditos,\n" +
				"                CONCAT(350)  as totalCreditos,\n" +
				"                (SELECT COUNT(rgp.calificacion_final)\n" +
				"                 FROM tbl_persona tp\n" +
				"                          INNER JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = tp.id_persona\n" +
				"                          INNER JOIN tbl_grupos tblg ON tblg.id = rgp.id_grupo\n" +
				"                          INNER JOIN tbl_eventos tble ON tble.id_evento = tblg.id_evento\n" +
				"                          INNER JOIN tbl_ficha_descriptiva_programa tblf ON tblf.id_programa = tble.id_programa\n" +
				"                 WHERE tp.id_persona = p.id_persona  AND rgp.calificacion_final >= tble.calificacion_min_aprobatoria AND NOT EXISTS (SELECT NULL\n" +
				"                                                                                                                                     FROM rel_persona_bajas t2\n" +
				"                                                                                                                                              INNER JOIN rel_motivo_baja mb2 ON mb2.id_motivo_baja = t2.motivo_baja_id AND (mb2.tipo_baja_id = 1 OR mb2.tipo_baja_id = 2)\n" +
				"                                                                                                                                     WHERE t2.id_persona = tp.id_persona AND rgp.id_grupo = t2.id_grupo) ) aprobadas,\n" +
				"                (SELECT COUNT(rgp.calificacion_final)\n" +
				"                 FROM tbl_persona tp\n" +
				"                          INNER JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = tp.id_persona\n" +
				"                          INNER JOIN tbl_grupos tblg ON tblg.id = rgp.id_grupo\n" +
				"                          INNER JOIN tbl_eventos tble ON tble.id_evento = tblg.id_evento\n" +
				"                          INNER JOIN tbl_ficha_descriptiva_programa tblf ON tblf.id_programa = tble.id_programa\n" +
				"                 WHERE tp.id_persona = p.id_persona  AND rgp.calificacion_final < tble.calificacion_min_aprobatoria AND NOT EXISTS (SELECT NULL\n" +
				"                                                                                                                                    FROM rel_persona_bajas t2\n" +
				"                                                                                                                                             INNER JOIN rel_motivo_baja mb2 ON mb2.id_motivo_baja = t2.motivo_baja_id AND (mb2.tipo_baja_id = 1 OR mb2.tipo_baja_id = 2)\n" +
				"                                                                                                                                    WHERE t2.id_persona = tp.id_persona AND rgp.id_grupo = t2.id_grupo) ) reprobadas,\n" +
				"                (SELECT COUNT(rgp.id_grupo)\n" +
				"                 FROM tbl_persona tp\n" +
				"                          INNER JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = tp.id_persona\n" +
				"                          INNER JOIN tbl_grupos tblg ON tblg.id = rgp.id_grupo\n" +
				"                          INNER JOIN tbl_eventos tble ON tble.id_evento = tblg.id_evento\n" +
				"                          INNER JOIN tbl_ficha_descriptiva_programa tblf ON tblf.id_programa = tble.id_programa\n" +
				"                 WHERE tp.id_persona = p.id_persona  AND  EXISTS (SELECT t2.id_baja\n" +
				"                                                                  FROM rel_persona_bajas t2\n" +
				"                                                                           INNER JOIN rel_motivo_baja mb2 ON mb2.id_motivo_baja = t2.motivo_baja_id AND (mb2.tipo_baja_id = 1 OR mb2.tipo_baja_id = 2)\n" +
				"                                                                  WHERE t2.id_persona = tp.id_persona AND rgp.id_grupo = t2.id_grupo) ) nopresentadas,\n" +
				"                CONCAT(cr.nombre,' ', cne.nombre,' ',IF(p.activo = 1,'activo','inactivo')) estatus,\n" +
				"                cne.nombre nivel,\n" +
				"                DATE_FORMAT(NOW(),'%d/%m/%Y') fecha_consulta,\n" +
				"                CONCAT(p.sso_nombre,' ',p.sso_apellidoPaterno,' ',p.sso_apellidoMaterno) as nombre,\n" +
				"                og.descripcion claveinstitucion,\n" +
				"                mc.descripcion clave\n" +
				"FROM tbl_persona p\n" +
				"         INNER JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = p.id_persona AND rgp.calificacion_final is not  null\n" +
				"         INNER JOIN rel_persona_roles pr ON pr.id_persona = p.id_persona AND pr.id_rol = 2\n" +
				"         INNER JOIN cat_roles cr ON cr.id_rol = pr.id_rol\n" +
				"         INNER JOIN tbl_grupos g ON g.id = rgp.id_grupo AND g.acta_cerrada = 1\n" +
				"         INNER JOIN tbl_eventos e ON e.id_evento = g.id_evento\n" +
				"         INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa\n" +
				"         INNER JOIN tbl_planes pl ON pl.id_plan = fd.id_plan\n" +
				"         INNER JOIN tbl_malla_curricular mc ON mc.id_plan = pl.id_plan\n" +
				"         INNER JOIN tbl_organismos_gubernamentales og ON og.id = pl.id_org_gub\n" +
				"         INNER JOIN cat_nivel_ensenanza_programa cne ON cne.id = pl.id_nivel_ensenanza\n" +
				"WHERE p.id_persona  = :id_persona";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_persona", id_persona);

		List<Object[]> lista = query.getResultList();

		if (!lista.isEmpty()) {
			for (Object[] obj : lista) {
				
				regresa.setMatricula(obj[0].toString());
				regresa.setProgramaEducativo(obj[1].toString());
				regresa.setPromedio(new BigDecimal(obj[2].toString()));
				regresa.setCreditos(new BigDecimal(obj[3].toString()));
				regresa.setTotalCreditos(new BigInteger(obj[4].toString()));
				regresa.setAprobadas(new BigInteger(obj[5].toString()));
				regresa.setReprobadas(new BigInteger(obj[6].toString()));
				regresa.setNopresentadas(new BigInteger(obj[7].toString()));
				regresa.setEstatus(obj[8].toString());
				regresa.setNivel(obj[9].toString());
				regresa.setFechaConsulta(obj[10].toString());
				regresa.setNombre(obj[11].toString());
				regresa.setClaveInst(obj[12] != null ? obj[12].toString() : null );
				regresa.setClave(obj[13] != null ? obj[13].toString() : null );
				BigInteger total = ((BigInteger) obj[5]).add((BigInteger) obj[6]).add((BigInteger) obj[7]);
				regresa.setTotal(total);
			}
		}

		return regresa;

	}

	@Override
	public HistorialAcademicoDTO consultaTiraMaterias(String id_persona) {

		HistorialAcademicoDTO regresa = new HistorialAcademicoDTO();

		String consulta = "SELECT DISTINCT (p.sso_idUsuario) matricula, CONCAT(cne.nombre,' en ', pl.nombre) carrera\r\n"
				+ "FROM tbl_persona p\r\n"
				+ "         INNER JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = p.id_persona AND rgp.calificacion_final is null\r\n"
				+ "         INNER JOIN tbl_grupos g ON g.id = rgp.id_grupo AND g.acta_cerrada = 0\r\n"
				+ "         INNER JOIN tbl_eventos e ON e.id_evento = g.id_evento\r\n"
				+ "         INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa\r\n"
				+ "         INNER JOIN tbl_planes pl ON pl.id_plan = fd.id_plan\r\n"
				+ "         INNER JOIN cat_nivel_ensenanza_programa cne ON cne.id = pl.id_nivel_ensenanza\r\n"
				+ "WHERE p.id_persona = :id_persona";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_persona", id_persona);

		List<Object[]> lista = query.getResultList();

		if (!lista.isEmpty()) {
			for (Object[] obj : lista) {
				regresa.setMatricula(obj[0].toString());
				regresa.setProgramaEducativo(obj[1].toString());
			}
		}

		return regresa;

	}

	@Override
	public List<TiraMateriaDTO> consultaTiraMaterias2(Long id_persona, Integer idEstatusEc) {

		List<TiraMateriaDTO> regresa = new ArrayList<TiraMateriaDTO>();
		List<Object[]> lista = new ArrayList<>();

		String consulta = "SELECT gp.id id, fd.identificador_final as clave, (SELECT CONCAT(SUBSTRING(mc.nombre,1,1),cnp.bloque)\r\n"
				+ "FROM tbl_ficha_descriptiva_programa fdp\r\n"
				+ "INNER JOIN tbl_malla_curricular mc ON mc.id = fdp.id_eje_capacitacion\r\n"
				+ "WHERE fdp.id_programa = e.id_programa) as bloquemodulo,\r\n"
				+ "CONCAT(e.nombre_ec ,' - ',g.nombre) as grupo,\r\n"
				+ "    (SELECT CONCAT(tp.sso_nombre,' ', tp.sso_apellidoPaterno,' ', tp.sso_apellidoMaterno)\r\n"
				+ "        FROM tbl_persona tp\r\n"
				+ "        INNER JOIN rel_persona_roles rpr ON rpr.id_persona = tp.id_persona\r\n"
				+ "        INNER JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = rpr.id_persona\r\n"
				+ "        INNER JOIN tbl_grupos gpro ON gpro.id = rgp.id_grupo\r\n"
				+ "        INNER JOIN tbl_eventos te ON te.id_evento = gpro.id_evento\r\n"
				+ "        WHERE gpro.id =  g.id AND  rpr.id_rol = 3 AND (tp.sso_idUsuario NOT LIKE '%.%' AND tp.sso_idUsuario NOT LIKE 'ES%' AND (tp.sso_idUsuario LIKE 'DL%' OR tp.sso_idUsuario LIKE 'FA%'))) docente,\r\n"
				+ "        CONCAT('SIN ASESOR ASIGNADO') asesor\r\n"
				+ "                  FROM rel_grupo_participante gp\r\n"
				+ "                  INNER JOIN tbl_grupos g on g.id = gp.id_grupo\r\n"
				+ "                  INNER JOIN tbl_persona t on t.id_persona = gp.id_persona_participante\r\n"
				+ "                  INNER JOIN tbl_eventos e on e.id_evento = g.id_evento\r\n"
				+ "                  INNER JOIN cat_estado_evento_capacitacion c on  c.id  = e.id_estatus_ec\r\n"
				+ "                  INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa\r\n"
				+ "                  INNER JOIN cat_nombres_planesyprogramas cnp ON cnp.clave_asig = fd.identificador_final AND e.cve_evento_cap LIKE CONCAT('%',cnp.clave_asig,'%') AND e.cve_evento_cap LIKe CONCAT('%',cnp.semestre,'%') AND e.cve_evento_cap LIKe CONCAT('%',cnp.bloque,'%')\r\n"
				+ "                  INNER JOIN tbl_planes pl ON pl.id_plan = fd.id_plan\r\n"
				+ "WHERE\r\n"
				+ "  c.id = :idEstatusEc AND t.id_persona = :id_persona AND g.acta_cerrada = 0";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_persona", id_persona);
		query.setParameter("idEstatusEc", idEstatusEc);


		lista = query.getResultList();

		if (!lista.isEmpty()) {
			for (Object[] obj : lista) {
				TiraMateriaDTO oOrdenServicioDTO = creaDtoconsultaTiraMaterias(obj);
				regresa.add(oOrdenServicioDTO);
			}
		}

		return regresa;

	}

	private TiraMateriaDTO creaDtoconsultaTiraMaterias(Object[] obj) {

		TiraMateriaDTO regresa = new TiraMateriaDTO();
		
		regresa.setId_grupo((Integer) (obj[0]));
		regresa.setClave(obj[1].toString());
		regresa.setBloque(obj[2].toString());
		regresa.setGrupo(obj[3].toString());
		regresa.setDocente(obj[4].toString());
		regresa.setAsesor(obj[5].toString());

		return regresa;
	}

	@Override
	public List<HistorialAcademicoListaDTO> getParticipanteByActaCerradaYconstancia2(String id_persona) {

		List<HistorialAcademicoListaDTO> regresa = new ArrayList<HistorialAcademicoListaDTO>();

		List<Object[]> lista = new ArrayList<>();

		String consulta = "SELECT gp.id as grupo_participante_id, " + "cnp.semestre as 'c/s',\r\n"
				+ "       fd.identificador_final as clavesep,\r\n" + "       fd.identificador_final as clave,\r\n"
				+ "       fd.creditos as creditos,\r\n"
				+ "        SUBSTRING_INDEX(e.cve_evento_cap, \"-\", -2) as periodo,\r\n"
				+ "       CONCAT('ORD') as tipodeevaluacion,\r\n"
				+ "       CONCAT(pl.identificador,'-',SUBSTRING_INDEX(e.cve_evento_cap, \"-\", 1),'-',g.nombre) as noacta\r\n"
				+ "       FROM rel_grupo_participante gp\r\n"
				+ "                  INNER JOIN tbl_grupos g on g.id = gp.id_grupo\r\n"
				+ "                  INNER JOIN tbl_eventos e on e.id_evento = g.id_evento\r\n"
				+ "                  INNER JOIN tbl_persona t on t.id_persona = gp.id_persona_participante\r\n"
				+ "                  INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa\r\n"
				+ "                  INNER JOIN tbl_planes pl ON pl.id_plan = fd.id_plan\r\n"
				+ "                  INNER JOIN cat_nombres_planesyprogramas cnp ON cnp.clave_asig = fd.identificador_final AND e.cve_evento_cap LIKE CONCAT('%',cnp.clave_asig,'%') AND e.cve_evento_cap LIKe CONCAT('%',cnp.semestre,'%') AND e.cve_evento_cap LIKe CONCAT('%',cnp.bloque,'%')\r\n"
				+ "WHERE t.id_persona = :id_persona AND g.acta_cerrada = 1 AND e.constancia = 1;";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_persona", id_persona);

		lista = query.getResultList();

		if (!lista.isEmpty()) {
			for (Object[] obj : lista) {
				HistorialAcademicoListaDTO oOrdenServicioDTO = creaHistorialAcademico(obj);
				regresa.add(oOrdenServicioDTO);
			}
		}

		return regresa;

	}

	private HistorialAcademicoListaDTO creaHistorialAcademico(Object[] obj) {

		HistorialAcademicoListaDTO regresa = new HistorialAcademicoListaDTO();
		regresa.setGrupo_participante_id((Integer) obj[0]);
		regresa.setCs(obj[1].toString());
		regresa.setClavesep(obj[2].toString());
		regresa.setClave(obj[3].toString());
		regresa.setCreditos(obj[4].toString());
		regresa.setPeriodo(obj[5].toString());
		regresa.setTipoEvaluacion(obj[6].toString());
		regresa.setnActa(obj[7].toString());

		return regresa;
	}
	
	@Override
	public List<TiraMateriaBajaDTO> consultaTiraMateriasBaja(Long id_persona) {

		List<TiraMateriaBajaDTO>  regresa = new ArrayList<TiraMateriaBajaDTO>();

		String consulta = "SELECT fd.nombre_tentativo asignatura, SUBSTRING_INDEX(e.cve_evento_cap, \"-\", -2)  periodo, tb.nombre tipobaja \r\n"
				+ "FROM rel_persona_bajas t2\r\n"
				+ "         INNER JOIN rel_motivo_baja mb2 ON mb2.id_motivo_baja = t2.motivo_baja_id\r\n"
				+ "         INNER JOIN cat_tipo_bajas tb ON tb.id_tipo_baja = mb2.tipo_baja_id\r\n"
				+ "         INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = t2.id_programa\r\n"
				+ "         INNER JOIN tbl_eventos e ON e.id_evento = t2.id_evento\r\n"
				+ "WHERE t2.id_persona = :id_persona";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_persona", id_persona);

		List<Object[]> lista = query.getResultList();

		if (!lista.isEmpty()) {
			for (Object[] obj : lista) {
				TiraMateriaBajaDTO dato = new TiraMateriaBajaDTO();
				dato.setNombreMateria(obj[0] != null ? obj[0].toString() : null );
				dato.setPeriodo(obj[1] != null ? obj[1].toString() : null );
				dato.setTipoBaja(obj[2] != null ? obj[2].toString() : null );
				regresa.add(dato);
			}
		}

		return regresa;

	}

}
