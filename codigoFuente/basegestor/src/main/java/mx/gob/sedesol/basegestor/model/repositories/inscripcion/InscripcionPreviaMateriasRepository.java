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
		sql.append("SELECT ");
		sql.append("  ti.programa,");
		sql.append("  ti.asignatura,");
		sql.append("  tf.tipo,");
		sql.append("  ti.semestre,");
		sql.append("  ti.bloque");
		sql.append("  FROM tbl_inscripciones ti ");
		sql.append(" JOIN tbl_persona_aspirante tpa ON tpa.id_persona = ti.Idpersona  ");
		sql.append("  JOIN tbl_procesos_inscripcion tpi ON tpi.convocatoria_id = tpa.id_convocatoria AND ti.fecha_registro BETWEEN tpi.fecha_inicio AND tpi.fecha_fin ");
		sql.append("  JOIN tbl_ficha_descriptiva_programa tf ON tf.id_programa = ti.idprograma  ");
		sql.append("   WHERE ti.Idpersona = :idPersona  AND NOW() BETWEEN tpi.fecha_inicio AND tpi.fecha_fin order by ti.semestre, ti.bloque;");

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