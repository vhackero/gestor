package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.HistorialAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.HistorialAcademicoListaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TiraMateriaDTO;

@Repository
public class HistorialAcademicoRepo implements IHistorialAcademicoRepo {

	@Autowired
	public EntityManager entityManager;

	@Override
	public HistorialAcademicoDTO consultaDatosHistorialAcademico(String id_persona) {

		HistorialAcademicoDTO regresa = new HistorialAcademicoDTO();

		String consulta = "SELECT  DISTINCT(p.sso_idUsuario) matricula,\r\n"
				+ "                CONCAT(cne.nombre,' en ', pl.nombre) programaEducativo,\r\n"
				+ "                (SELECT SUM(rgp.calificacion_final)/COUNT(rgp.calificacion_final)\r\n"
				+ "                 FROM tbl_persona tp\r\n"
				+ "                          INNER JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = tp.id_persona\r\n"
				+ "                 WHERE tp.id_persona = p.id_persona  AND NOT EXISTS (SELECT NULL\r\n"
				+ "                                                                     FROM rel_persona_bajas t2\r\n"
				+ "                                                                              INNER JOIN rel_motivo_baja mb2 ON mb2.id_motivo_baja = t2.motivo_baja_id AND (mb2.tipo_baja_id = 1 OR mb2.tipo_baja_id = 2)\r\n"
				+ "                                                                     WHERE t2.id_persona = tp.id_persona AND rgp.id_grupo = t2.id_grupo) ) promedio,\r\n"
				+ "                (SELECT SUM(tblf.creditos)\r\n" + "                 FROM tbl_persona tp\r\n"
				+ "                          INNER JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = tp.id_persona\r\n"
				+ "                          INNER JOIN tbl_grupos tblg ON tblg.id = rgp.id_grupo\r\n"
				+ "                          INNER JOIN tbl_eventos tble ON tble.id_evento = tblg.id_evento\r\n"
				+ "                          INNER JOIN tbl_ficha_descriptiva_programa tblf ON tblf.id_programa = tble.id_programa\r\n"
				+ "                 WHERE tp.id_persona = p.id_persona  AND rgp.calificacion_final >= tble.calificacion_min_aprobatoria AND NOT EXISTS (SELECT NULL\r\n"
				+ "                                                                                                                                     FROM rel_persona_bajas t2\r\n"
				+ "                                                                                                                                              INNER JOIN rel_motivo_baja mb2 ON mb2.id_motivo_baja = t2.motivo_baja_id AND (mb2.tipo_baja_id = 1 OR mb2.tipo_baja_id = 2)\r\n"
				+ "                                                                                                                                     WHERE t2.id_persona = tp.id_persona AND rgp.id_grupo = t2.id_grupo) ) creditos,\r\n"
				+ "                (SELECT COUNT(rgp.calificacion_final)\r\n"
				+ "                 FROM tbl_persona tp\r\n"
				+ "                          INNER JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = tp.id_persona\r\n"
				+ "                          INNER JOIN tbl_grupos tblg ON tblg.id = rgp.id_grupo\r\n"
				+ "                          INNER JOIN tbl_eventos tble ON tble.id_evento = tblg.id_evento\r\n"
				+ "                          INNER JOIN tbl_ficha_descriptiva_programa tblf ON tblf.id_programa = tble.id_programa\r\n"
				+ "                 WHERE tp.id_persona = p.id_persona  AND rgp.calificacion_final >= tble.calificacion_min_aprobatoria AND NOT EXISTS (SELECT NULL\r\n"
				+ "                                                                                                                                     FROM rel_persona_bajas t2\r\n"
				+ "                                                                                                                                              INNER JOIN rel_motivo_baja mb2 ON mb2.id_motivo_baja = t2.motivo_baja_id AND (mb2.tipo_baja_id = 1 OR mb2.tipo_baja_id = 2)\r\n"
				+ "                                                                                                                                     WHERE t2.id_persona = tp.id_persona AND rgp.id_grupo = t2.id_grupo) ) aprobadas,\r\n"
				+ "                (SELECT COUNT(rgp.calificacion_final)\r\n"
				+ "                 FROM tbl_persona tp\r\n"
				+ "                          INNER JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = tp.id_persona\r\n"
				+ "                          INNER JOIN tbl_grupos tblg ON tblg.id = rgp.id_grupo\r\n"
				+ "                          INNER JOIN tbl_eventos tble ON tble.id_evento = tblg.id_evento\r\n"
				+ "                          INNER JOIN tbl_ficha_descriptiva_programa tblf ON tblf.id_programa = tble.id_programa\r\n"
				+ "                 WHERE tp.id_persona = p.id_persona  AND rgp.calificacion_final < tble.calificacion_min_aprobatoria AND NOT EXISTS (SELECT NULL\r\n"
				+ "                                                                                                                                    FROM rel_persona_bajas t2\r\n"
				+ "                                                                                                                                             INNER JOIN rel_motivo_baja mb2 ON mb2.id_motivo_baja = t2.motivo_baja_id AND (mb2.tipo_baja_id = 1 OR mb2.tipo_baja_id = 2)\r\n"
				+ "                                                                                                                                    WHERE t2.id_persona = tp.id_persona AND rgp.id_grupo = t2.id_grupo) ) reprobadas,\r\n"
				+ "                (SELECT COUNT(rgp.id_grupo)\r\n" + "                 FROM tbl_persona tp\r\n"
				+ "                          INNER JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = tp.id_persona\r\n"
				+ "                          INNER JOIN tbl_grupos tblg ON tblg.id = rgp.id_grupo\r\n"
				+ "                          INNER JOIN tbl_eventos tble ON tble.id_evento = tblg.id_evento\r\n"
				+ "                          INNER JOIN tbl_ficha_descriptiva_programa tblf ON tblf.id_programa = tble.id_programa\r\n"
				+ "                 WHERE tp.id_persona = p.id_persona  AND  EXISTS (SELECT t2.id_baja\r\n"
				+ "                                                                  FROM rel_persona_bajas t2\r\n"
				+ "                                                                           INNER JOIN rel_motivo_baja mb2 ON mb2.id_motivo_baja = t2.motivo_baja_id AND (mb2.tipo_baja_id = 1 OR mb2.tipo_baja_id = 2)\r\n"
				+ "                                                                  WHERE t2.id_persona = tp.id_persona AND rgp.id_grupo = t2.id_grupo) ) nopresentadas,\r\n"
				+ "                CONCAT(cr.nombre,' ', cne.nombre,' ',IF(p.activo = 1,'activo','inactivo')) estatus,\r\n"
				+ "                cne.nombre nivel,\r\n" + "                NOW() fecha_consulta\r\n"
				+ "FROM tbl_persona p\r\n"
				+ "         INNER JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = p.id_persona AND rgp.calificacion_final is not  null\r\n"
				+ "         INNER JOIN rel_persona_roles pr ON pr.id_persona = p.id_persona AND pr.id_rol = 2\r\n"
				+ "         INNER JOIN cat_roles cr ON cr.id_rol = pr.id_rol\r\n"
				+ "         INNER JOIN tbl_grupos g ON g.id = rgp.id_grupo AND g.acta_cerrada = 1\r\n"
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
				regresa.setPromedio((BigDecimal) obj[2]);
				regresa.setCreditos((BigDecimal) obj[3]);
				regresa.setAprobadas((BigInteger) obj[4]);
				regresa.setReprobadas((BigInteger) obj[5]);
				regresa.setNopresentadas((BigInteger) obj[6]);
				regresa.setEstatus(obj[7].toString());
				regresa.setNivel(obj[8].toString());
				regresa.setFechaConsulta((Timestamp) obj[9]);
				BigInteger total = ((BigInteger) obj[4]).add((BigInteger) obj[5]).add((BigInteger) obj[6]);
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
	public List<TiraMateriaDTO> consultaTiraMaterias2(String id_persona) {

		List<TiraMateriaDTO> regresa = new ArrayList<TiraMateriaDTO>();
		List<Object[]> lista = new ArrayList<>();

		String consulta = "SELECT SUBSTRING_INDEX(e.cve_evento_cap, \"-\", 1) as cveasignatura,\r\n"
				+ "       fd.nombre_tentativo asigantura,\r\n" + "       CONCAT('B',cnp.bloque) bloque,\r\n"
				+ "       CONCAT(e.nombre_ec,' - ',g.nombre) grupo,\r\n"
				+ "       (SELECT CONCAT(tp.sso_nombre,' ', tp.sso_apellidoPaterno,' ', tp.sso_apellidoMaterno)\r\n"
				+ "        FROM tbl_persona tp\r\n"
				+ "        INNER JOIN rel_persona_roles rpr ON rpr.id_persona = tp.id_persona\r\n"
				+ "        INNER JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = rpr.id_persona\r\n"
				+ "        INNER JOIN tbl_grupos gpro ON gpro.id = rgp.id_grupo\r\n"
				+ "        INNER JOIN tbl_eventos te ON te.id_evento = e.id_evento\r\n"
				+ "        WHERE gpro.id =  g.id AND  rpr.id_rol = 3 AND (tp.sso_idUsuario NOT LIKE '%.%' AND tp.sso_idUsuario NOT LIKE 'ES%' AND (tp.sso_idUsuario LIKE 'DL%' OR tp.sso_idUsuario LIKE 'FA%'))) docente,\r\n"
				+ "       CONCAT('SIN ASESOR ASIGNADO') asesor\r\n" + "FROM tbl_persona p\r\n"
				+ "         INNER JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = p.id_persona AND rgp.calificacion_final is null\r\n"
				+ "         INNER JOIN tbl_grupos g ON g.id = rgp.id_grupo AND g.acta_cerrada = 0\r\n"
				+ "         INNER JOIN tbl_eventos e ON e.id_evento = g.id_evento\r\n"
				+ "         INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa #AND id_tipo_evento_programa = 5\r\n"
				+ "         INNER JOIN cat_nombres_planesyprogramas cnp ON cnp.clave_asig = fd.identificador_final AND e.cve_evento_cap LIKE CONCAT('%',cnp.clave_asig,'%') AND e.cve_evento_cap LIKe CONCAT('%',cnp.semestre,'%') AND e.cve_evento_cap LIKe CONCAT('%',cnp.bloque,'%')\r\n"
				+ "         INNER JOIN tbl_planes pl ON pl.id_plan = fd.id_plan\r\n"
				+ "         INNER JOIN cat_nivel_ensenanza_programa cne ON cne.id = pl.id_nivel_ensenanza\r\n"
				+ "WHERE p.id_persona = :id_persona";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_persona", id_persona);

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

		regresa.setAsigantura(obj[0].toString());
		regresa.setBloque(obj[1].toString());
		regresa.setGrupo(obj[2].toString());
		regresa.setDocente(obj[3].toString());
		regresa.setAsesor(obj[4].toString());

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
				+ "WHERE t.id_persona = :id_persona\r\n" + "  AND g.acta_cerrada = 1\r\n" + "  AND e.constancia = 1;";

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

}
