package mx.gob.sedesol.basegestor.model.especificaciones;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import mx.gob.sedesol.basegestor.commons.dto.encuestas.RelEncuestaEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaTipo_;
import mx.gob.sedesol.basegestor.model.entities.encuestas.RelEncuestaEventoCapacitacion;
import mx.gob.sedesol.basegestor.model.entities.encuestas.RelEncuestaEventoCapacitacion_;
import mx.gob.sedesol.basegestor.model.entities.encuestas.TblEncuesta_;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento_;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatModalidadPlanPrograma_;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma_;

public class RelEncuestaEventoCapacitacionEspecificacion implements Specification<RelEncuestaEventoCapacitacion> {

	private RelEncuestaEventoCapacitacionDTO criterios;
	private String tipoDatoFechas;

	public RelEncuestaEventoCapacitacionEspecificacion(RelEncuestaEventoCapacitacionDTO criterios,
			String tipoDatoFechas) {
		this.criterios = criterios;
		this.tipoDatoFechas = tipoDatoFechas;
	}

	@Override
	public Predicate toPredicate(Root<RelEncuestaEventoCapacitacion> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<>();

		if (ObjectUtils.isNotNull(criterios)) {

			/**
			 * Filtro por tipo de competencia
			 */
			if (ObjectUtils.isNotNull(criterios.getTblEvento())
					&& ObjectUtils.isNotNull(criterios.getTblEvento().getFichaDescriptivaPrograma()) && ObjectUtils
							.isNotNull(criterios.getTblEvento().getFichaDescriptivaPrograma().getTipoCompetencia())) {

				predicates.add(cb.equal(
						root.join(RelEncuestaEventoCapacitacion_.tblEvento).join(TblEvento_.fichaDescriptivaPrograma)
								.get(TblFichaDescriptivaPrograma_.tipoCompetencia),
						criterios.getTblEvento().getFichaDescriptivaPrograma().getTipoCompetencia()));

			}
			/**
			 * Filtro por eje de capacitacion
			 */
			if (ObjectUtils.isNotNull(criterios.getTblEvento())
					&& ObjectUtils.isNotNull(criterios.getTblEvento().getFichaDescriptivaPrograma()) && ObjectUtils
							.isNotNull(criterios.getTblEvento().getFichaDescriptivaPrograma().getEjeCapacitacion())) {

				predicates.add(cb.equal(
						root.join(RelEncuestaEventoCapacitacion_.tblEvento).join(TblEvento_.fichaDescriptivaPrograma)
								.get(TblFichaDescriptivaPrograma_.ejeCapacitacion),
						criterios.getTblEvento().getFichaDescriptivaPrograma().getEjeCapacitacion()));

			}
			/**
			 * Filtro por id del evento de capacitacion
			 */
			if (ObjectUtils.isNotNull(criterios.getTblEvento()) && ObjectUtils.isNotNull(criterios.getTblEvento().getIdEvento())) {

				predicates.add(cb.equal(root.join(RelEncuestaEventoCapacitacion_.tblEvento).get(TblEvento_.idEvento),
						criterios.getTblEvento().getIdEvento()));

			}

			/***
			 * Filtro por nombre del programa
			 */

			if (ObjectUtils.isNotNull(criterios.getTblEvento())
					&& ObjectUtils.isNotNull(criterios.getTblEvento().getFichaDescriptivaPrograma())
					&& !ObjectUtils.isNullOrEmpty(
							criterios.getTblEvento().getFichaDescriptivaPrograma().getNombreTentativo())) {
				predicates.add(cb.like(
						root.join(RelEncuestaEventoCapacitacion_.tblEvento).join(TblEvento_.fichaDescriptivaPrograma)
								.get(TblFichaDescriptivaPrograma_.nombreTentativo),
						"%" + criterios.getTblEvento().getFichaDescriptivaPrograma().getNombreTentativo() + "%"));

			}

			/**
			 * Filtro por modalidad
			 */
			if (ObjectUtils.isNotNull(criterios.getTblEvento())
					&& ObjectUtils.isNotNull(criterios.getTblEvento().getFichaDescriptivaPrograma()) && ObjectUtils
							.isNotNull(criterios.getTblEvento().getFichaDescriptivaPrograma().getCatModalidad())) {
				predicates.add(cb.equal(
						root.join(RelEncuestaEventoCapacitacion_.tblEvento).join(TblEvento_.fichaDescriptivaPrograma)
								.join(TblFichaDescriptivaPrograma_.catModalidad).get(CatModalidadPlanPrograma_.id),
						criterios.getTblEvento().getFichaDescriptivaPrograma().getCatModalidad().getId()));

			}

			/**
			 * Filtro por tipo encuesta
			 */

			if (ObjectUtils.isNotNull(criterios.getTblEncuesta())
					&& ObjectUtils.isNotNull(criterios.getTblEncuesta().getEncuestaTipo())
					&& ObjectUtils.isNotNull(criterios.getTblEncuesta().getEncuestaTipo().getId())) {
				predicates
						.add(cb.equal(
								root.join(RelEncuestaEventoCapacitacion_.tblEncuesta).join(TblEncuesta_.encuestaTipo)
										.get(CatEncuestaTipo_.id),
								criterios.getTblEncuesta().getEncuestaTipo().getId()));
			}

			if (!ObjectUtils.isNullOrEmpty(tipoDatoFechas)) {
				switch (tipoDatoFechas) {
				case "1":
					this.inicianYTerminanEntre(predicates, cb, root, criterios);
					break;
				case "2":
					this.inicianEntre(predicates, cb, root, criterios);
					break;
				case "3":
					this.terminanEntre(predicates, cb, root, criterios);
					break;
				default:
					break;
				}
			}

		}
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}

	private void inicianYTerminanEntre(List<Predicate> predicates, CriteriaBuilder cb,
			Root<RelEncuestaEventoCapacitacion> root, RelEncuestaEventoCapacitacionDTO criterios) {

		if (ObjectUtils.isNotNull(criterios.getTblEvento())
				&& ObjectUtils.isNotNull(criterios.getTblEvento().getFechaInicial())
				&& ObjectUtils.isNotNull(criterios.getTblEvento().getFechaFinal())) {

			predicates.add(cb.or(
					cb.between(root.join(RelEncuestaEventoCapacitacion_.tblEvento).get(TblEvento_.fechaInicial),
							DateUtils.procesarFechaInicio(criterios.getTblEvento().getFechaInicial()),
							DateUtils.procesarFechaFin(criterios.getTblEvento().getFechaFinal())),
					cb.between(root.join(RelEncuestaEventoCapacitacion_.tblEvento).get(TblEvento_.fechaFinal),
							DateUtils.procesarFechaInicio(criterios.getTblEvento().getFechaInicial()),
							DateUtils.procesarFechaFin(criterios.getTblEvento().getFechaFinal()))));
		}

	}

	private void inicianEntre(List<Predicate> predicates, CriteriaBuilder cb, Root<RelEncuestaEventoCapacitacion> root,
			RelEncuestaEventoCapacitacionDTO criterios) {
		if (ObjectUtils.isNotNull(criterios.getTblEvento())
				&& ObjectUtils.isNotNull(criterios.getTblEvento().getFechaInicial())
				&& ObjectUtils.isNotNull(criterios.getTblEvento().getFechaFinal()))

			predicates.add(cb.between(root.join(RelEncuestaEventoCapacitacion_.tblEvento).get(TblEvento_.fechaInicial),
					DateUtils.procesarFechaInicio(criterios.getTblEvento().getFechaInicial()),
					DateUtils.procesarFechaFin(criterios.getTblEvento().getFechaFinal())));

	}

	private void terminanEntre(List<Predicate> predicates, CriteriaBuilder cb, Root<RelEncuestaEventoCapacitacion> root,
			RelEncuestaEventoCapacitacionDTO criterios) {
		if (ObjectUtils.isNotNull(criterios.getTblEvento())
				&& ObjectUtils.isNotNull(criterios.getTblEvento().getFechaInicial())
				&& ObjectUtils.isNotNull(criterios.getTblEvento().getFechaFinal())) {
			predicates.add(cb.between(root.join(RelEncuestaEventoCapacitacion_.tblEvento).get(TblEvento_.fechaFinal),
					DateUtils.procesarFechaInicio(criterios.getTblEvento().getFechaInicial()),
					DateUtils.procesarFechaFin(criterios.getTblEvento().getFechaFinal())));
		}

	}

}
