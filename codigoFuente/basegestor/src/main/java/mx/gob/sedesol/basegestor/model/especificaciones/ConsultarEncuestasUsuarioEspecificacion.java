package mx.gob.sedesol.basegestor.model.especificaciones;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;

import mx.gob.sedesol.basegestor.commons.dto.encuestas.RelEncuestaUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.encuestas.RelEncuestaUsuario;
import mx.gob.sedesol.basegestor.model.entities.encuestas.RelEncuestaUsuarioPK_;
import mx.gob.sedesol.basegestor.model.entities.encuestas.RelEncuestaUsuario_;

public class ConsultarEncuestasUsuarioEspecificacion implements Specification<RelEncuestaUsuario>
{
	private static final Logger log = Logger.getLogger(DomiciliosPersonaEspecificacion.class);

	private RelEncuestaUsuarioDTO criterios;
	
	public ConsultarEncuestasUsuarioEspecificacion(RelEncuestaUsuarioDTO criterios)
	{
		this.criterios=criterios;
	}
	
	@Override
	public Predicate toPredicate(Root<RelEncuestaUsuario> root, CriteriaQuery<?> query, CriteriaBuilder cb)
	{
		List<Predicate> predicates = new ArrayList<>();
		
		log.info("Identificador del evento: " + criterios.getRelGrupoParticipante().getGrupo().getEvento().getIdEvento());
		log.info("Identificador del participante: " + criterios.getRelGrupoParticipante().getPersona().getIdPersona());
		
		if (ObjectUtils.isNotNull(criterios))
		{
			if(ObjectUtils.isNotNull(criterios.getIdEncuesta()))
			{
				predicates.add(cb.equal(root.get(RelEncuestaUsuario_.id).get(RelEncuestaUsuarioPK_.idEncuesta), criterios.getIdEncuesta()));
			}

			if(ObjectUtils.isNotNull(criterios.getIdGrupoParticipante()))
			{
				predicates.add(cb.equal(root.get(RelEncuestaUsuario_.id).get(RelEncuestaUsuarioPK_.idGrupoParticipante), criterios.getIdGrupoParticipante()));
			}

			if(ObjectUtils.isNotNull(criterios.getFechaRegistro()))
			{
				Date fecha = criterios.getFechaRegistro();
				predicates.add(cb.lessThanOrEqualTo(root.get(RelEncuestaUsuario_.fechaRegistro), DateUtils.procesarFechaInicio(fecha)));
			}
			
			if(ObjectUtils.isNotNull(criterios.getFechaActualizacion()))
			{
				Date fecha = criterios.getFechaActualizacion();
				predicates.add(cb.lessThanOrEqualTo(root.get(RelEncuestaUsuario_.fechaActualizacion), DateUtils.procesarFechaInicio(fecha)));
			}

			if(ObjectUtils.isNotNull(criterios.getFechaApertura()))
			{
				Date fecha = criterios.getFechaApertura();
				predicates.add(cb.lessThanOrEqualTo(root.get(RelEncuestaUsuario_.fechaApertura), DateUtils.procesarFechaInicio(fecha)));
			}
		
			if(ObjectUtils.isNotNull(criterios.getActivo()))
			{
				predicates.add(cb.equal(root.get(RelEncuestaUsuario_.activo), criterios.getActivo()));
			}
		}
		
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}
}
