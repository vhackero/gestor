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
import mx.gob.sedesol.basegestor.model.entities.admin.CatAsentamientoReporteUsuario_;
import mx.gob.sedesol.basegestor.model.entities.admin.CatEntidadFederativaReporteUsuario_;
import mx.gob.sedesol.basegestor.model.entities.admin.CatMunicipioReporteUsuario_;
import mx.gob.sedesol.basegestor.model.entities.admin.TblDomiciliosPersonaReporteUsuario;
import mx.gob.sedesol.basegestor.model.entities.admin.TblDomiciliosPersonaReporteUsuario_;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersonaReporteUsuario_;

public class DomiciliosPersonaReporteUsuarioEspecificacion implements Specification<TblDomiciliosPersonaReporteUsuario> {

	private static final Logger log = Logger.getLogger(DomiciliosPersonaReporteUsuarioEspecificacion.class);

	private DomicilioPersonaDTO criterios;
	private String tipoDatoFechas;

	public DomiciliosPersonaReporteUsuarioEspecificacion(DomicilioPersonaDTO criterios, String tipoDatoFechas) {
		this.criterios = criterios;
		this.tipoDatoFechas = tipoDatoFechas;
	}

	@Override
	public Predicate toPredicate(Root<TblDomiciliosPersonaReporteUsuario> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
				predicates.add(cb.equal(root.get(TblDomiciliosPersonaReporteUsuario_.persona).get(TblPersonaReporteUsuario_.tipoUsuario),
						criterios.getPersona().getTipoUsuario()));
			}

			if (ObjectUtils.isNotNull(criterios.getPersona().getActivo())) {
				predicates.add(cb.equal(root.get(TblDomiciliosPersonaReporteUsuario_.persona).get(TblPersonaReporteUsuario_.activo),
						criterios.getPersona().getActivo()));
			}

			if (!ObjectUtils.isNullOrEmpty(criterios.getPersona().getGenero())) {
				predicates.add(cb.equal(root.get(TblDomiciliosPersonaReporteUsuario_.persona).get(TblPersonaReporteUsuario_.genero),
						criterios.getPersona().getGenero()));
			}

			if (!ObjectUtils.isNullOrCero(criterios.getIdMunicipio())) {
				predicates.add(cb.equal(root.get(TblDomiciliosPersonaReporteUsuario_.asentamiento).get(CatAsentamientoReporteUsuario_.municipio)
						.get(CatMunicipioReporteUsuario_.idMunicipio), criterios.getIdMunicipio()));
			} else if (!ObjectUtils.isNullOrCero(criterios.getIdEntidadFederativa())) {
				predicates.add(cb.equal(
						root.get(TblDomiciliosPersonaReporteUsuario_.asentamiento).get(CatAsentamientoReporteUsuario_.municipio)
								.get(CatMunicipioReporteUsuario_.entidadFederativa).get(CatEntidadFederativaReporteUsuario_.idEntidadFederativa),
						criterios.getIdEntidadFederativa()));
			}

		}

		return cb.and(predicates.toArray(new Predicate[predicates.size()]));

	}

	public void inicianYTerminanEntre(List<Predicate> predicates, CriteriaBuilder cb, Root<TblDomiciliosPersonaReporteUsuario> root) {
		if (!ObjectUtils.isNull(criterios.getPersona().getFechaInicial())
				&& !ObjectUtils.isNull(criterios.getPersona().getFechaFinal())) {

			predicates.add(cb.or(
					cb.between(root.join(TblDomiciliosPersonaReporteUsuario_.persona).get(TblPersonaReporteUsuario_.fechaRegistro),
							DateUtils.procesarFechaInicio(criterios.getPersona().getFechaInicial()),
							DateUtils.procesarFechaFin(criterios.getPersona().getFechaFinal())),
					cb.between(root.join(TblDomiciliosPersonaReporteUsuario_.persona).get(TblPersonaReporteUsuario_.fechaRegistro),
							DateUtils.procesarFechaInicio(criterios.getPersona().getFechaInicial()),
							DateUtils.procesarFechaFin(criterios.getPersona().getFechaFinal()))));
		}

	}

	public void inicianEntre(List<Predicate> predicates, CriteriaBuilder cb, Root<TblDomiciliosPersonaReporteUsuario> root) {
		if (!ObjectUtils.isNull(criterios.getPersona().getFechaInicial())
				&& !ObjectUtils.isNull(criterios.getPersona().getFechaFinal())) {

			predicates.add(cb.between(root.join(TblDomiciliosPersonaReporteUsuario_.persona).get(TblPersonaReporteUsuario_.fechaRegistro),
					DateUtils.procesarFechaInicio(criterios.getPersona().getFechaInicial()),
					DateUtils.procesarFechaFin(criterios.getPersona().getFechaFinal())));
		}

	}

	public void terminanEntre(List<Predicate> predicates, CriteriaBuilder cb, Root<TblDomiciliosPersonaReporteUsuario> root) {
		if (!ObjectUtils.isNull(criterios.getPersona().getFechaInicial())
				&& !ObjectUtils.isNull(criterios.getPersona().getFechaFinal())) {

			predicates.add(cb.between(root.join(TblDomiciliosPersonaReporteUsuario_.persona).get(TblPersonaReporteUsuario_.fechaRegistro),
					DateUtils.procesarFechaInicio(criterios.getPersona().getFechaInicial()),
					DateUtils.procesarFechaFin(criterios.getPersona().getFechaFinal())));
		}

	}

}
