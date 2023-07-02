package mx.gob.sedesol.basegestor.model.especificaciones;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;

import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.admin.CatRol;
import mx.gob.sedesol.basegestor.model.entities.admin.CatRol_;

public class RolEspecificacion implements Specification<CatRol> {
	
	private static final Logger logger = Logger.getLogger(RolEspecificacion.class);
	
	private RolDTO criterios;
	
	public RolEspecificacion(RolDTO criterios) {
		this.criterios = criterios;
	}
	
	@Override
	public Predicate toPredicate(Root<CatRol> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

		List<Predicate> predicates = new ArrayList<>();
		logger.info(criterios.getIdRol());
		logger.info(criterios.getClave());
		logger.info(criterios.getNombre());
		
		if (ObjectUtils.isNotNull(criterios)) {
			if (!ObjectUtils.isNullOrCero(criterios.getIdRol())) {
				predicates.add(cb.equal(root.get(CatRol_.idRol), criterios.getIdRol()));
			}
			if (!ObjectUtils.isNullOrEmpty(criterios.getNombre())) {
				predicates.add(cb.like(root.get(CatRol_.nombre), "%" + criterios.getNombre() + "%"));
			}
			
			if (!ObjectUtils.isNullOrEmpty(criterios.getClave())) {
				predicates.add(cb.like(root.get(CatRol_.clave), "%" + criterios.getClave() + "%"));
			}
		}
		
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}

}
