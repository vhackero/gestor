package mx.gob.sedesol.basegestor.model.especificaciones;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;

import mx.gob.sedesol.basegestor.commons.dto.admin.DomicilioPersonaDTO;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.admin.CatAsentamiento_;
import mx.gob.sedesol.basegestor.model.entities.admin.CatEntidadFederativa_;
import mx.gob.sedesol.basegestor.model.entities.admin.CatMunicipio_;
import mx.gob.sedesol.basegestor.model.entities.admin.TblDomiciliosPersona;
import mx.gob.sedesol.basegestor.model.entities.admin.TblDomiciliosPersona_;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona_;

public class DomiciliosPersonaEspecificacion implements Specification<TblDomiciliosPersona> {

	private static final Logger log = Logger.getLogger(DomiciliosPersonaEspecificacion.class);

	private DomicilioPersonaDTO criterios;
	private String tipoDatoFechas;

	public DomiciliosPersonaEspecificacion(DomicilioPersonaDTO criterios, String tipoDatoFechas) {
		this.criterios = criterios;
		this.tipoDatoFechas = tipoDatoFechas;
	}

	@Override
	public Predicate toPredicate(Root<TblDomiciliosPersona> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<>();
		log.info("Tipo ususario " + criterios.getPersona().getTipoUsuario());
		log.info("Activo " + criterios.getPersona().getActivo());
		log.info("Genero " + criterios.getPersona().getGenero());

		if (ObjectUtils.isNotNull(criterios)) {

			if (ObjectUtils.isNotNull(tipoDatoFechas)) {

				switch (tipoDatoFechas) {
				case "1":
					this.inicianYTerminanEntre(predicates, cb, root);
					break;
				case "2":
					this.inicianEntre(predicates, cb, root);
					break;
				case "3":
					this.terminanEntre(predicates, cb, root);
					break;
				default:
					break;

				}
			}

			if (!ObjectUtils.isNullOrCero(criterios.getPersona().getTipoUsuario())) {
				predicates.add(cb.equal(root.get(TblDomiciliosPersona_.persona).get(TblPersona_.tipoUsuario),
						criterios.getPersona().getTipoUsuario()));
			}

			if (ObjectUtils.isNotNull(criterios.getPersona().getActivo())) {
				predicates.add(cb.equal(root.get(TblDomiciliosPersona_.persona).get(TblPersona_.activo),
						criterios.getPersona().getActivo()));
			}

			if (!ObjectUtils.isNullOrEmpty(criterios.getPersona().getGenero())) {
				predicates.add(cb.equal(root.get(TblDomiciliosPersona_.persona).get(TblPersona_.genero),
						criterios.getPersona().getGenero()));
			}

			if (!ObjectUtils.isNullOrCero(criterios.getIdMunicipio())) {
				predicates.add(cb.equal(root.get(TblDomiciliosPersona_.asentamiento).get(CatAsentamiento_.municipio)
						.get(CatMunicipio_.idMunicipio), criterios.getIdMunicipio()));
			} else if (!ObjectUtils.isNullOrCero(criterios.getIdEntidadFederativa())) {
				predicates.add(cb.equal(
						root.get(TblDomiciliosPersona_.asentamiento).get(CatAsentamiento_.municipio)
								.get(CatMunicipio_.entidadFederativa).get(CatEntidadFederativa_.idEntidadFederativa),
						criterios.getIdEntidadFederativa()));
			}

		}

		return cb.and(predicates.toArray(new Predicate[predicates.size()]));

	}

	public void inicianYTerminanEntre(List<Predicate> predicates, CriteriaBuilder cb, Root<TblDomiciliosPersona> root) {
		if (!ObjectUtils.isNull(criterios.getPersona().getFechaInicial())
				&& !ObjectUtils.isNull(criterios.getPersona().getFechaFinal())) {

			predicates.add(cb.or(
					cb.between(root.join(TblDomiciliosPersona_.persona).get(TblPersona_.fechaRegistro),
							DateUtils.procesarFechaInicio(criterios.getPersona().getFechaInicial()),
							DateUtils.procesarFechaFin(criterios.getPersona().getFechaFinal())),
					cb.between(root.join(TblDomiciliosPersona_.persona).get(TblPersona_.fechaRegistro),
							DateUtils.procesarFechaInicio(criterios.getPersona().getFechaInicial()),
							DateUtils.procesarFechaFin(criterios.getPersona().getFechaFinal()))));
		}

	}

	public void inicianEntre(List<Predicate> predicates, CriteriaBuilder cb, Root<TblDomiciliosPersona> root) {
		if (!ObjectUtils.isNull(criterios.getPersona().getFechaInicial())
				&& !ObjectUtils.isNull(criterios.getPersona().getFechaFinal())) {

			predicates.add(cb.between(root.join(TblDomiciliosPersona_.persona).get(TblPersona_.fechaRegistro),
					DateUtils.procesarFechaInicio(criterios.getPersona().getFechaInicial()),
					DateUtils.procesarFechaFin(criterios.getPersona().getFechaFinal())));
		}

	}

	public void terminanEntre(List<Predicate> predicates, CriteriaBuilder cb, Root<TblDomiciliosPersona> root) {
		if (!ObjectUtils.isNull(criterios.getPersona().getFechaInicial())
				&& !ObjectUtils.isNull(criterios.getPersona().getFechaFinal())) {

			predicates.add(cb.between(root.join(TblDomiciliosPersona_.persona).get(TblPersona_.fechaRegistro),
					DateUtils.procesarFechaInicio(criterios.getPersona().getFechaInicial()),
					DateUtils.procesarFechaFin(criterios.getPersona().getFechaFinal())));
		}

	}

}
