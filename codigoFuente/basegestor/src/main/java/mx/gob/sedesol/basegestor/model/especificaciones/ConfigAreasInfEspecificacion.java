package mx.gob.sedesol.basegestor.model.especificaciones;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.CritBusquedaAreasConfigDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.admin.TblOrganismoGubernamental_;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatAreaInfraestructura_;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatAreasSede_;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatSede_;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatUbicacionTerritorial_;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.TblConfiguracionArea;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.TblConfiguracionArea_;

public class ConfigAreasInfEspecificacion  implements Specification<TblConfiguracionArea>{

	public ConfigAreasInfEspecificacion(CritBusquedaAreasConfigDTO criterios) {
		this.criterios = criterios;
	}
	
	private CritBusquedaAreasConfigDTO criterios;

	@Override
	public Predicate toPredicate(Root<TblConfiguracionArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	
		List<Predicate> predicates = new ArrayList<>();
		if(ObjectUtils.isNotNull(criterios)){
			
			if(ObjectUtils.isNotNull(criterios.getIdOrgGubernamental())){
				predicates.add(cb.equal(
						root.get(TblConfiguracionArea_.catAreasSede).get(CatAreasSede_.catSede)
							.get(CatSede_.organismoGubernamental).get(TblOrganismoGubernamental_.id)
																		,criterios.getIdOrgGubernamental()));
			}
			
			if(ObjectUtils.isNotNull(criterios.getIdUbicacion())){
				predicates.add(cb.equal(
						root.get(TblConfiguracionArea_.catAreasSede).get(CatAreasSede_.catSede)
							.get(CatSede_.catUbicacionTerritorial).get(CatUbicacionTerritorial_.id)
																		,criterios.getIdUbicacion()));
			}
			
			if(ObjectUtils.isNotNull(criterios.getIdSede())){
				predicates.add(cb.equal(
						root.get(TblConfiguracionArea_.catAreasSede).get(CatAreasSede_.catSede)
							.get(CatSede_.idSede), criterios.getIdSede()));
			}
			
			if(ObjectUtils.isNotNull(criterios.getIdArea())){
				predicates.add(cb.equal(
						root.get(TblConfiguracionArea_.catAreasSede).get(CatAreasSede_.catArea).get(CatAreaInfraestructura_.id), criterios.getIdArea()));
			}
		}
		
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}

}
