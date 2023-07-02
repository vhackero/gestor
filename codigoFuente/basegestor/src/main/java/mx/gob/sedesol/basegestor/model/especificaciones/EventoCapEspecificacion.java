package mx.gob.sedesol.basegestor.model.especificaciones;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatEstadoEventoCapacitacion_;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento_;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatModalidadPlanPrograma_;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatTipoEventoEc_;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma_;

public class EventoCapEspecificacion implements Specification<TblEvento> {

	private static final Logger logger = Logger.getLogger(EventoCapEspecificacion.class);
	private EventoCapacitacionDTO filtro;
	private String tipoDatoFechas;

	public EventoCapEspecificacion(EventoCapacitacionDTO filtro, String tipoDatoFechas) {
		this.filtro = filtro;
		this.tipoDatoFechas = tipoDatoFechas;
	}

	@Override
	public Predicate toPredicate(Root<TblEvento> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<>();

		if (ObjectUtils.isNotNull(filtro)) {

			/**
			 * Criterio tipoCompetencia
			 */
			if (ObjectUtils.isNotNull(filtro.getFichaDescriptivaPrograma())
					&& ObjectUtils.isNotNull(filtro.getFichaDescriptivaPrograma().getTipoCompetencia())) {
				predicates.add(cb.equal(
						root.join(TblEvento_.fichaDescriptivaPrograma)
								.get(TblFichaDescriptivaPrograma_.tipoCompetencia),
						filtro.getFichaDescriptivaPrograma().getTipoCompetencia()));
			}

			/**
			 * Criterio ejeCapacitacion
			 */
			if (ObjectUtils.isNotNull(filtro.getFichaDescriptivaPrograma())
					&& ObjectUtils.isNotNull(filtro.getFichaDescriptivaPrograma().getEjeCapacitacion())) {
				predicates.add(cb.equal(
						root.join(TblEvento_.fichaDescriptivaPrograma)
								.get(TblFichaDescriptivaPrograma_.ejeCapacitacion),
						filtro.getFichaDescriptivaPrograma().getEjeCapacitacion()));
			}

			/**
			 * Entidad federativa
			 */

			if (ObjectUtils.isNotNull(filtro.getIdEntidadFederativa())) {
				predicates.add(cb.equal(root.get(TblEvento_.idEntidadFederativa), filtro.getIdEntidadFederativa()));
			}

			/**
			 * Municipio
			 */
			if (ObjectUtils.isNotNull(filtro.getIdMunicipio())) {
				predicates.add(cb.equal(root.get(TblEvento_.idMunicipio), filtro.getIdMunicipio()));
			}

			/**
			 * nombre de evento capacitacitacion
			 */
			if (!ObjectUtils.isNullOrEmpty(filtro.getNombreEc())) {
				predicates.add(cb.like(root.get(TblEvento_.nombreEc), "%" + filtro.getNombreEc() + "%"));
			}

			/**
			 * identificador final del programa
			 */
			if (!ObjectUtils.isNullOrEmpty(filtro.getFichaDescriptivaPrograma())
					&& ObjectUtils.isNotNull(filtro.getFichaDescriptivaPrograma().getIdentificadorFinal())) {
				predicates.add(cb.like(
						root.join(TblEvento_.fichaDescriptivaPrograma)
								.get(TblFichaDescriptivaPrograma_.identificadorFinal),
						"%" + filtro.getFichaDescriptivaPrograma().getIdentificadorFinal() + "%"));
			}
			
			/**
			 * nombre tentativo del programa
			 */
			if (!ObjectUtils.isNullOrEmpty(filtro.getFichaDescriptivaPrograma())
					&& ObjectUtils.isNotNull(filtro.getFichaDescriptivaPrograma().getNombreTentativo())) {
				predicates.add(cb.like(
						root.join(TblEvento_.fichaDescriptivaPrograma)
								.get(TblFichaDescriptivaPrograma_.nombreTentativo),
						"%" + filtro.getFichaDescriptivaPrograma().getNombreTentativo() + "%"));
			}

			/**
			 * Modalidad obteniendola de programas
			 */

			if (ObjectUtils.isNotNull(filtro.getFichaDescriptivaPrograma())
					&& ObjectUtils.isNotNull(filtro.getFichaDescriptivaPrograma().getCatModalidad())) {
				predicates.add(cb.equal(
						root.join(TblEvento_.fichaDescriptivaPrograma).get(TblFichaDescriptivaPrograma_.catModalidad)
								.get(CatModalidadPlanPrograma_.id),
						filtro.getFichaDescriptivaPrograma().getCatModalidad().getId()));
			}

			/*
			 * Modalidad obteniendola del evento
			 */

			if (ObjectUtils.isNotNull(filtro.getCatModalidadPlanPrograma())
					&& ObjectUtils.isNotNull(filtro.getCatModalidadPlanPrograma().getId())) {
				predicates
						.add(cb.equal(root.join(TblEvento_.catModalidadPlanPrograma).get(CatModalidadPlanPrograma_.id),
								filtro.getCatModalidadPlanPrograma().getId()));
			}

			/**
			 * Clasificacion de la informacion
			 */
			if (ObjectUtils.isNotNull(filtro.getPrivado())) {
				predicates.add(cb.equal(root.get(TblEvento_.privado), filtro.getPrivado()));
			}

			/**
			 * Tipo de evento
			 */

			if (ObjectUtils.isNotNull(filtro.getFichaDescriptivaPrograma())
					&& ObjectUtils.isNotNull(filtro.getFichaDescriptivaPrograma().getCatTipoEventoEc())) {
				predicates.add(cb.equal(
						root.join(TblEvento_.fichaDescriptivaPrograma).get(TblFichaDescriptivaPrograma_.catTipoEventoEc)
								.get(CatTipoEventoEc_.id),
						filtro.getFichaDescriptivaPrograma().getCatTipoEventoEc().getId()));
			}

			/**
			 * Dirigido a
			 */
			if (ObjectUtils.isNotNull(filtro.getIdDirigido())) {
				predicates.add(cb.equal(root.get(TblEvento_.idDirigido), filtro.getIdDirigido()));
			}

			/* Ver si la inclucion de este criterio de busqueda no afecta */
			/**
			 * Estatus
			 */
			if (ObjectUtils.isNotNull(filtro.getCatEstadoEventoCapacitacion())
					&& ObjectUtils.isNotNull(filtro.getCatEstadoEventoCapacitacion().getId())) {
				predicates.add(
						cb.equal(root.join(TblEvento_.catEstadoEventoCapacitacion).get(CatEstadoEventoCapacitacion_.id),
								filtro.getCatEstadoEventoCapacitacion().getId()));
			}

			/**
			 * 1.-Inician y terminan entre 2.-Inician entre 3.-Terminan entre
			 */

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

		}
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));

	}

	private void inicianYTerminanEntre(List<Predicate> predicates, CriteriaBuilder cb, Root<TblEvento> root) {
		if (!ObjectUtils.isNull(filtro.getFechaInicial()) && !ObjectUtils.isNull(filtro.getFechaFinal())) {

			predicates.add(cb.or(
					cb.between(root.get(TblEvento_.fechaInicial),
							DateUtils.procesarFechaInicio(filtro.getFechaInicial()),
							DateUtils.procesarFechaFin(filtro.getFechaFinal())),
					cb.between(root.get(TblEvento_.fechaFinal), DateUtils.procesarFechaInicio(filtro.getFechaInicial()),
							DateUtils.procesarFechaFin(filtro.getFechaFinal()))));

		}

	}

	private void inicianEntre(List<Predicate> predicates, CriteriaBuilder cb, Root<TblEvento> root) {
		if (!ObjectUtils.isNull(filtro.getFechaInicial()) && !ObjectUtils.isNull(filtro.getFechaFinal())) {

			predicates.add(cb.between(root.get(TblEvento_.fechaInicial),
					DateUtils.procesarFechaInicio(filtro.getFechaInicial()),
					DateUtils.procesarFechaFin(filtro.getFechaFinal())));
		}

	}

	private void terminanEntre(List<Predicate> predicates, CriteriaBuilder cb, Root<TblEvento> root) {
		if (!ObjectUtils.isNull(filtro.getFechaInicial()) && !ObjectUtils.isNull(filtro.getFechaFinal())) {

			predicates.add(
					cb.between(root.get(TblEvento_.fechaFinal), DateUtils.procesarFechaInicio(filtro.getFechaInicial()),
							DateUtils.procesarFechaFin(filtro.getFechaFinal())));
		}

	}

}
