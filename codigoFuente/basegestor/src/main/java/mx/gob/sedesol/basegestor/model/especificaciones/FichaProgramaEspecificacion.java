package mx.gob.sedesol.basegestor.model.especificaciones;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatModalidadPlanPrograma_;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatNivelEnsenanzaPrograma_;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatStatusPrograma_;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma_;

public class FichaProgramaEspecificacion implements Specification<TblFichaDescriptivaPrograma> {
	
	private FichaDescProgramaDTO filtro;
	
	public FichaProgramaEspecificacion(FichaDescProgramaDTO filtro){
		this.filtro = filtro;
	}

	@Override
	public Predicate toPredicate(Root<TblFichaDescriptivaPrograma> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(ObjectUtils.isNotNull(filtro)){
			
			if(ObjectUtils.isNotNull(filtro.getTipoCompetencia())){
				predicates.add(cb.equal(root.get(TblFichaDescriptivaPrograma_.tipoCompetencia),filtro.getTipoCompetencia()));
			}
			
			if(ObjectUtils.isNotNull(filtro.getEjeCapacitacion())){
				predicates.add(cb.equal(root.get(TblFichaDescriptivaPrograma_.ejeCapacitacion),filtro.getEjeCapacitacion()));
			}
			
			if(!ObjectUtils.isNullOrEmpty(filtro.getNombreTentativo())){
				predicates.add(cb.like(root.get(TblFichaDescriptivaPrograma_.nombreTentativo), "%" + filtro.getNombreTentativo() +"%" ));
			}
			
			if(ObjectUtils.isNotNull(filtro.getCatStatusPrograma())){
				predicates.add(cb.equal(root.join(TblFichaDescriptivaPrograma_.catStatusPrograma).get(CatStatusPrograma_.id),filtro.getCatStatusPrograma().getId()));
			}
			
			if(ObjectUtils.isNotNull(filtro.getCatModalidad())){
				predicates.add(cb.equal(root.join(TblFichaDescriptivaPrograma_.catModalidad).get(CatModalidadPlanPrograma_.id),filtro.getCatModalidad().getId()));
			}
			
			if(ObjectUtils.isNotNull(filtro.getCatNivelEnsenanzaPrograma())){
				predicates.add(cb.equal(root.join(TblFichaDescriptivaPrograma_.catNivelEnsenanzaPrograma).get(CatNivelEnsenanzaPrograma_.id),filtro.getCatNivelEnsenanzaPrograma().getId()));
			}

		}
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}

}
