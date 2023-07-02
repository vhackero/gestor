package mx.gob.sedesol.basegestor.model.especificaciones;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoUsuarioEnum;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersonaBasica;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersonaBasica_;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona_;

public class PersonaBasicaEspecificacion implements Specification<TblPersonaBasica> {
	
	private TblPersonaBasica criteriosPersona;
	
	public PersonaBasicaEspecificacion( TblPersonaBasica criteriosPersona) {
		this.criteriosPersona = criteriosPersona;
	}

	@Override
	public Predicate toPredicate(Root<TblPersonaBasica> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

		List<Predicate> predicates = new ArrayList<>();


		if (ObjectUtils.isNotNull(criteriosPersona)) {
			
			if (ObjectUtils.isNotNull(criteriosPersona.getTipoUsuario())) {
				if (criteriosPersona.getTipoUsuario() == TipoUsuarioEnum.EXTERNO.getValor() ||
						criteriosPersona.getTipoUsuario() == TipoUsuarioEnum.INTERNO.getValor()) {
					predicates.add(cb.equal(root.get(TblPersonaBasica_.tipoUsuario), criteriosPersona.getTipoUsuario()));
				} 
			}
			
			if (!ObjectUtils.isNullOrEmpty(criteriosPersona.getCurp())) {
				predicates.add(cb.like(root.get(TblPersonaBasica_.curp), "%" + criteriosPersona.getCurp() + "%"));
			}

			if (!ObjectUtils.isNullOrEmpty(criteriosPersona.getNombre())) {
				predicates.add(cb.like(root.get(TblPersonaBasica_.nombre), "%" + criteriosPersona.getNombre() + "%"));
			}

			if (!ObjectUtils.isNullOrEmpty(criteriosPersona.getApellidoPaterno())) {
				predicates.add(cb.like(root.get(TblPersonaBasica_.apellidoPaterno),
						"%" + criteriosPersona.getApellidoPaterno() + "%"));
			}

			if (!ObjectUtils.isNullOrEmpty(criteriosPersona.getApellidoMaterno())) {
				predicates.add(cb.like(root.get(TblPersonaBasica_.apellidoMaterno),
						"%" + criteriosPersona.getApellidoMaterno() + "%"));
			}
			if (!ObjectUtils.isNullOrEmpty(criteriosPersona.getUsuario())) {
				predicates.add(cb.like(root.get(TblPersonaBasica_.usuario),
						"%" + criteriosPersona.getUsuario() + "%"));
			}
		}

		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}

}
