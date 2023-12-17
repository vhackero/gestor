package mx.gob.sedesol.basegestor.model.especificaciones;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.PlanDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.admin.TblOrganismoGubernamental_;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatAlcancePlan_;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatEstatusPlan_;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatModalidadPlanPrograma_;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatTipoPlan_;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan_;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatCreditosPlan_;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatDivisionesPlan_;

public class PlanEspecificacion implements Specification<TblPlan> {
	
	private PlanDTO filtro;
	
	public PlanEspecificacion( PlanDTO filtro) {
		this.filtro = filtro;
	}

	@Override
	public Predicate toPredicate(Root<TblPlan> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(ObjectUtils.isNotNull(filtro)){
			
			//query.getGroupList().add(cb.like(root.get(TblPlan_.nombre),filtro.getNombre()));
			
			if(!ObjectUtils.isNullOrEmpty(filtro.getNombre())){
				predicates.add(cb.like(root.get(TblPlan_.nombre),filtro.getNombre()));
			}
			
			if(ObjectUtils.isNotNull(filtro.getCatEstatusPlan())){
				predicates.add(cb.equal(root.join(TblPlan_.catEstatusPlan).get(CatEstatusPlan_.id),filtro.getCatEstatusPlan().getId()));
			}
			
			if(ObjectUtils.isNotNull(filtro.getTblOrganismoGubernamental())){
				predicates.add(cb.equal(root.join(TblPlan_.tblOrganismoGubernamental).get(TblOrganismoGubernamental_.id),filtro.getTblOrganismoGubernamental().getId()));
			}
			
			if(ObjectUtils.isNotNull(filtro.getCatModalidadPlanPrograma())){
				predicates.add(cb.equal(root.join(TblPlan_.catModalidadPlanPrograma).get(CatModalidadPlanPrograma_.id),filtro.getCatModalidadPlanPrograma().getId()));
			}
			
			if(ObjectUtils.isNotNull(filtro.getCatTipoPlan())){
				predicates.add(cb.equal(root.join(TblPlan_.catTipoPlan).get(CatTipoPlan_.id),filtro.getCatTipoPlan().getId()));
			}
			
			if(ObjectUtils.isNotNull(filtro.getCatAlcancePlan())){
				predicates.add(cb.equal(root.join(TblPlan_.catAlcancePlan).get(CatAlcancePlan_.id),filtro.getCatAlcancePlan().getId()));
			}

			if(ObjectUtils.isNotNull(filtro.getCatCreditosPlan())){
				predicates.add(cb.equal(root.join(TblPlan_.catCreditosPlan).get(CatCreditosPlan_.id),filtro.getCatCreditosPlan().getId()));
			}

			if(ObjectUtils.isNotNull(filtro.getCatDivisionesPlan())){
				predicates.add(cb.equal(root.join(TblPlan_.catDivisionesPlan).get(CatDivisionesPlan_.id),filtro.getCatDivisionesPlan().getId()));
			}

		}
		
		query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		query.orderBy(cb.desc(root.get(TblPlan_.idPlan)));
		
		return query.getGroupRestriction();
		//return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}

}
