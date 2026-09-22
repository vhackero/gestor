package mx.gob.sedesol.basegestor.model.repositories.inscripcion;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.commons.dto.inscripcion.InscripcionPreviaMateriasDTO;

@Repository
public class InscripcionPreviaMateriasRepository implements IInscripcionPreviaMateriasRepository {

	@Autowired
	public EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<InscripcionPreviaMateriasDTO> obtenerInscripcionPrevia(Long idPersona) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT ");
		sql.append("  ti.programa,");
		sql.append("  ti.asignatura,");
		sql.append("  tf.tipo,");
		sql.append("  ti.semestre,");
		sql.append("  ti.bloque");
		sql.append("  FROM tbl_inscripciones ti ");
		sql.append(" JOIN tbl_persona_aspirante tpa ON tpa.id_persona = ti.Idpersona  ");
		sql.append("  JOIN tbl_procesos_inscripcion tpi ON tpi.convocatoria_id = tpa.id_convocatoria AND ti.fecha_registro BETWEEN tpi.fecha_inicio AND tpi.fecha_fin ");
		sql.append("  JOIN tbl_ficha_descriptiva_programa tf ON tf.id_programa = ti.idprograma  ");
		sql.append("   WHERE ti.Idpersona = :idPersona ");
		sql.append("     AND (NOW() BETWEEN tpi.fecha_inicio AND tpi.fecha_fin ");
		sql.append("          OR EXISTS ( ");
		sql.append("              SELECT 1 ");
		sql.append("                FROM rel_grupo_participante rgp ");
		sql.append("                JOIN tbl_grupos tg ON tg.id = rgp.id_grupo ");
		sql.append("                JOIN tbl_eventos te ON te.id_evento = tg.id_evento ");
		sql.append("               WHERE rgp.id_persona_participante = ti.Idpersona ");
		sql.append("                 AND te.id_programa = ti.idprograma ");
		sql.append("                 AND te.id_estatus_ec = 2 ");
		sql.append("                 AND rgp.calificacion_final IS NULL ");
		sql.append("          )) ");
		sql.append(" ORDER BY ti.semestre, ti.bloque;");

		List<Object[]> resultados = entityManager.createNativeQuery(sql.toString())
				.setParameter("idPersona", idPersona)
				.getResultList();

		return resultados.stream().map(this::mapearInscripcionPreviaMaterias).collect(Collectors.toList());
	}

	private InscripcionPreviaMateriasDTO mapearInscripcionPreviaMaterias(Object[] row) {
		InscripcionPreviaMateriasDTO dto = new InscripcionPreviaMateriasDTO();
		dto.setPrograma((String) row[0]);
		dto.setAsignatura((String) row[1]);
		dto.setTipoPrograma(row[2] != null ? String.valueOf(row[2]) : null);
		dto.setSemestre(row[3] != null ? String.valueOf(row[3]) : null);
	    dto.setBloque(row[4] != null ? String.valueOf(row[4]) : null);
	    
		return dto;
	}


}
