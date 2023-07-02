package mx.gob.sedesol.basegestor.model.especificaciones;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import mx.gob.sedesol.basegestor.commons.dto.admin.LoteCargaUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.admin.TblLoteCargaUsuario;
import mx.gob.sedesol.basegestor.model.entities.admin.TblLoteCargaUsuario_;

public class LoteCargaUsuarioEspecificacion implements Specification<TblLoteCargaUsuario> {
	
	private LoteCargaUsuarioDTO criterios;
	
	public LoteCargaUsuarioEspecificacion(LoteCargaUsuarioDTO criterios) {
		this.criterios = criterios;
	}
	
	@Override
	public Predicate toPredicate(Root<TblLoteCargaUsuario> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<>();
		if (ObjectUtils.isNotNull(criterios)) {
			if (ObjectUtils.isNotNull(criterios.getFechaInicio()) 
					&& ObjectUtils.isNotNull(criterios.getFechaFin())) {
				predicates.add(cb.between(root.get(TblLoteCargaUsuario_.fechaRegistro), 
						criterios.getFechaInicio(), criterios.getFechaFin()));
			} else {
				if (ObjectUtils.isNotNull(criterios.getFechaInicio())) {
					predicates.add(cb.greaterThanOrEqualTo(root.get(TblLoteCargaUsuario_.fechaRegistro), 
							criterios.getFechaInicio()));
				}
			}
			
			if (!ObjectUtils.isNullOrEmpty(criterios.getIdLoteCargaUsuarios())) {
				predicates.add(cb.equal(root.get(TblLoteCargaUsuario_.idLoteCargaUsuarios), 
						criterios.getIdLoteCargaUsuarios()));
			}
			
			if (!ObjectUtils.isNullOrEmpty(criterios.getNombre())) {
				predicates.add(cb.like(root.get(TblLoteCargaUsuario_.nombre), "%" +
						criterios.getNombre() + "%"));
			}
		}
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}

}
