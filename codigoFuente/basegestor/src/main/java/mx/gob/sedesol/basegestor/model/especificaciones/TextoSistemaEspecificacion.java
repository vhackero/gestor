package mx.gob.sedesol.basegestor.model.especificaciones;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.admin.TblTextoSistema;
import mx.gob.sedesol.basegestor.model.entities.admin.TblTextoSistema_;

public class TextoSistemaEspecificacion implements Specification<TblTextoSistema> {
	
	private TblTextoSistema criterios;
	
	public TextoSistemaEspecificacion(TblTextoSistema criterios) {
		this.criterios = criterios;
	}

	@Override
	public Predicate toPredicate(Root<TblTextoSistema> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<>();
		if (ObjectUtils.isNotNull(criterios)) {
			System.out.println(criterios.getValor());
			if (!ObjectUtils.isNullOrEmpty(criterios.getValor())) {
				predicates.add(cb.like(root.get(TblTextoSistema_.valor), "%" + criterios.getValor() + "%"));
			}
			if (ObjectUtils.isNotNull(criterios.getFuncionalidad())) {
				System.out.println(criterios.getFuncionalidad().getIdFuncionalidad());
				predicates.add(cb.equal(root.get(TblTextoSistema_.funcionalidad), criterios.getFuncionalidad()));
			}
		}
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}

}
