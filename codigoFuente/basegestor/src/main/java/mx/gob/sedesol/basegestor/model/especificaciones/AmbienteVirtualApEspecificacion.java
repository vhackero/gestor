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
import mx.gob.sedesol.basegestor.commons.utils.EstadoEventoCapEnum;
import mx.gob.sedesol.basegestor.commons.utils.EstatusAmbienteVirtualAprendizajeEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatEstadoAva_;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblAmbienteVirtualAprendizaje;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblAmbienteVirtualAprendizaje_;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatEstadoEventoCapacitacion_;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento_;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatModalidadPlanPrograma_;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma_;

public class AmbienteVirtualApEspecificacion implements Specification<TblAmbienteVirtualAprendizaje> {

	private static final Logger logger = Logger.getLogger(AmbienteVirtualApEspecificacion.class);
	private EventoCapacitacionDTO filtro;
	private String tipoDatoFechas;

	public AmbienteVirtualApEspecificacion(EventoCapacitacionDTO filtro, String tipoDatoFechas) {
		this.filtro = filtro;
		this.tipoDatoFechas = tipoDatoFechas;
		logger.info("Instanciando clase AmbienteVirtualApEspecificacion");
	}

	public Predicate toPredicate(Root<TblAmbienteVirtualAprendizaje> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		logger.info("Busqueda por criterios de TblEventoCapacitacionAva");

		List<Predicate> predicates = new ArrayList<>();

		if (ObjectUtils.isNotNull(filtro)) {

			/**
			 * Eventos de capacitacion que esten en estatus calendarizado,en
			 * ejecucion, concluidos
			 */

			predicates.add((root.join(TblAmbienteVirtualAprendizaje_.eventoCapacitacion)
					.get(TblEvento_.catEstadoEventoCapacitacion).get(CatEstadoEventoCapacitacion_.id)
					.in(this.obtenerListaIdEstatusEvento().toArray())));

			/**
			 * Criterio tipoCompetencia
			 */
			if (ObjectUtils.isNotNull(filtro.getFichaDescriptivaPrograma().getTipoCompetencia())) {
				predicates.add(cb.equal(root.join(TblAmbienteVirtualAprendizaje_.eventoCapacitacion)
						.get(TblEvento_.fichaDescriptivaPrograma).get(TblFichaDescriptivaPrograma_.tipoCompetencia),
						filtro.getFichaDescriptivaPrograma().getTipoCompetencia()));
			}

			/**
			 * Criterio ejeCapacitacion
			 */

			if (ObjectUtils.isNotNull(filtro.getFichaDescriptivaPrograma().getEjeCapacitacion())) {
				predicates.add(cb.equal(root.join(TblAmbienteVirtualAprendizaje_.eventoCapacitacion)
						.get(TblEvento_.fichaDescriptivaPrograma).get(TblFichaDescriptivaPrograma_.ejeCapacitacion),
						filtro.getFichaDescriptivaPrograma().getEjeCapacitacion()));
			}

			/**
			 * nombre final del programa
			 */
			if (!ObjectUtils.isNullOrEmpty(filtro.getFichaDescriptivaPrograma().getNombreTentativo())) {
				predicates.add(cb.equal(root.join(TblAmbienteVirtualAprendizaje_.eventoCapacitacion)
						.join(TblEvento_.fichaDescriptivaPrograma).get(TblFichaDescriptivaPrograma_.nombreTentativo),
						filtro.getFichaDescriptivaPrograma().getNombreTentativo()));
				// "%" +
				// filtro.getFichaDescriptivaPrograma().getNombreTentativo() +
				// "%"));
			}

			/**
			 * nombre de evento capacitacitacion
			 */

			if (!ObjectUtils.isNullOrEmpty(filtro.getNombreEc())) {
				predicates.add(
						cb.like(root.join(TblAmbienteVirtualAprendizaje_.eventoCapacitacion).get(TblEvento_.nombreEc),
								"%" + filtro.getNombreEc() + "%"));
			}

			/**
			 * Modalidad
			 */
			if (ObjectUtils.isNotNull(filtro.getCatModalidadPlanPrograma().getId())) {
				predicates.add(cb.equal(
						root.join(TblAmbienteVirtualAprendizaje_.eventoCapacitacion)
								.join(TblEvento_.catModalidadPlanPrograma).get(CatModalidadPlanPrograma_.id),
						filtro.getCatModalidadPlanPrograma().getId()));
			}

			/**
			 * 1.-Inincian y terminan entre 2.-Inician entre 3.-Terminan entre
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

	public void inicianYTerminanEntre(List<Predicate> predicates, CriteriaBuilder cb,
			Root<TblAmbienteVirtualAprendizaje> root) {
		if (!ObjectUtils.isNull(filtro.getFechaInicial()) && !ObjectUtils.isNull(filtro.getFechaFinal())) {

			predicates
					.add(cb.or(
							cb.between(
									root.join(TblAmbienteVirtualAprendizaje_.eventoCapacitacion)
											.get(TblEvento_.fechaInicial),
									DateUtils.procesarFechaInicio(filtro.getFechaInicial()),
									DateUtils.procesarFechaFin(filtro.getFechaFinal())),
							cb.between(
									root.join(TblAmbienteVirtualAprendizaje_.eventoCapacitacion)
											.get(TblEvento_.fechaFinal),
									DateUtils.procesarFechaInicio(filtro.getFechaInicial()),
									DateUtils.procesarFechaFin(filtro.getFechaFinal()))));
		}

	}

	public void inicianEntre(List<Predicate> predicates, CriteriaBuilder cb, Root<TblAmbienteVirtualAprendizaje> root) {
		if (!ObjectUtils.isNull(filtro.getFechaInicial()) && !ObjectUtils.isNull(filtro.getFechaFinal())) {
			predicates.add(cb.between(
					root.join(TblAmbienteVirtualAprendizaje_.eventoCapacitacion).get(TblEvento_.fechaInicial),
					DateUtils.procesarFechaInicio(filtro.getFechaInicial()),
					DateUtils.procesarFechaFin(filtro.getFechaFinal())));
		}

	}

	public void terminanEntre(List<Predicate> predicates, CriteriaBuilder cb,
			Root<TblAmbienteVirtualAprendizaje> root) {
		if (!ObjectUtils.isNull(filtro.getFechaInicial()) && !ObjectUtils.isNull(filtro.getFechaFinal())) {
			predicates.add(
					cb.between(root.join(TblAmbienteVirtualAprendizaje_.eventoCapacitacion).get(TblEvento_.fechaFinal),
							DateUtils.procesarFechaInicio(filtro.getFechaInicial()),
							DateUtils.procesarFechaFin(filtro.getFechaFinal())));
		}

	}

	private List<Integer> obtenerListaIdEstatusEvento() {

		List<Integer> idEstatusEventoCapacitacion = new ArrayList<Integer>();

		idEstatusEventoCapacitacion.add(EstadoEventoCapEnum.CALENDARIZADO.getId());
		idEstatusEventoCapacitacion.add(EstadoEventoCapEnum.EN_EJECUCION.getId());
		idEstatusEventoCapacitacion.add(EstadoEventoCapEnum.CONCLUIDOS.getId());

		return idEstatusEventoCapacitacion;

	}

}
