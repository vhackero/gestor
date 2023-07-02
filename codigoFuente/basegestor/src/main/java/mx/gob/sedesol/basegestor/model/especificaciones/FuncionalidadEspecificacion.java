package mx.gob.sedesol.basegestor.model.especificaciones;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.admin.TblFuncionalidad;
import mx.gob.sedesol.basegestor.model.entities.admin.TblFuncionalidad_;

public class FuncionalidadEspecificacion implements Specification<TblFuncionalidad> {
	
	private TblFuncionalidad criterios;
	
	public FuncionalidadEspecificacion(TblFuncionalidad criterios) {
		this.criterios = criterios;
	}
	
	@Override
	public Predicate toPredicate(Root<TblFuncionalidad> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<>();
		if (ObjectUtils.isNotNull(criterios)) {
			if (!ObjectUtils.isNullOrEmpty(criterios.getClave())) {
				predicates.add(cb.like(root.get(TblFuncionalidad_.clave), criterios.getClave()));
			}
			if (!ObjectUtils.isNullOrEmpty(criterios.getDescripcion())) {
				predicates.add(cb.like(root.get(TblFuncionalidad_.descripcion), "%" + criterios.getDescripcion() + "%"));
			}
		}
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}

}
