package mx.gob.sedesol.basegestor.model.especificaciones;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaTipo_;
import mx.gob.sedesol.basegestor.model.entities.encuestas.TblEncuesta;
import mx.gob.sedesol.basegestor.model.entities.encuestas.TblEncuesta_;

public class EncuestaEspecificacion implements Specification<TblEncuesta> {

	private TblEncuesta criterios;

	public EncuestaEspecificacion(TblEncuesta criterios) {
		this.criterios = criterios;
	}

	@Override
	public Predicate toPredicate(Root<TblEncuesta> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (ObjectUtils.isNotNull(criterios)) {

			if (!ObjectUtils.isNullOrEmpty(criterios.getNombre())) {
				predicates.add(cb.like(cb.lower(root.get(TblEncuesta_.nombre)),
						"%" + criterios.getNombre().toLowerCase() + "%"));
			}

			if (!ObjectUtils.isNullOrEmpty(criterios.getClave())) {
				predicates.add(cb.like(cb.lower(root.get(TblEncuesta_.clave)),
						"%" + criterios.getClave().toLowerCase() + "%"));
			}

			if (!ObjectUtils.isNullOrEmpty(criterios.getFechaCreacion())) {
				predicates.add(cb.between(root.get(TblEncuesta_.fechaCreacion), DateUtils.procesarFechaInicio(criterios.getFechaCreacion()),
						DateUtils.procesarFechaFin(criterios.getFechaRegistroAux())));
			}

			if (!ObjectUtils.isNullOrEmpty(criterios.getEncuestaEstatus())
					&& criterios.getEncuestaEstatus().getId() > 0) {
				predicates.add(cb.equal(root.get(TblEncuesta_.encuestaEstatus), criterios.getEncuestaEstatus()));
			}

			if (!ObjectUtils.isNullOrEmpty(criterios.getEncuestaObjetivo())
					&& criterios.getEncuestaObjetivo().getId() > 0) {
				predicates.add(
						cb.equal(root.get(TblEncuesta_.encuestaObjetivo), criterios.getEncuestaObjetivo().getId()));
			}

			if (!ObjectUtils.isNullOrEmpty(criterios.getEncuestaTipo()) && criterios.getEncuestaTipo().getId()!=null) {
				predicates.add(cb.equal(root.get(TblEncuesta_.encuestaTipo), criterios.getEncuestaTipo().getId()));
			}

			if (!ObjectUtils.isNullOrEmpty(criterios.getEncuestaTipo())
					&& !ObjectUtils.isNullOrEmpty(criterios.getEncuestaTipo().getContexto())
					&& criterios.getEncuestaTipo().getContexto().getId() > 0) {
				
				predicates.add(cb.equal(root.join(TblEncuesta_.encuestaTipo).get(CatEncuestaTipo_.contexto),
						criterios.getEncuestaTipo().getContexto().getId()));
			}
		}

		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}

}
