package mx.gob.sedesol.basegestor.model.especificaciones;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;

import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.CritBusquedaReporteReservDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatEstatusReservacion_;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.RelSolicitudEventoGeneral;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.RelSolicitudEventoGeneral_;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.TblReservacionEventoGeneral_;

public class SolicitudEventoGeneralEspecificacion implements Specification<RelSolicitudEventoGeneral> {

	private static final Logger log = Logger.getLogger(SolicitudEventoGeneralEspecificacion.class);

	CritBusquedaReporteReservDTO criterios;
	List<Integer> areasSede;

	public SolicitudEventoGeneralEspecificacion(CritBusquedaReporteReservDTO criterios, List<Integer> areasSede) {
		this.criterios = criterios;
		this.areasSede = areasSede;
	}

	@Override
	public Predicate toPredicate(Root<RelSolicitudEventoGeneral> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<>();

		if (ObjectUtils.isNotNull(criterios)) {

			// criterios de area y sede
			if (!ObjectUtils.isNullOrCero(criterios.getIdArea())) {

				predicates.add(cb.equal(root.get(RelSolicitudEventoGeneral_.tblReservacionEventoGeneral)
						.get(TblReservacionEventoGeneral_.idAreaSede), criterios.getIdAreaSede()));

			} else if (!ObjectUtils.isNullOrCero(criterios.getIdSede())) {

				Expression<Integer> exp = (Expression<Integer>) root
						.get(RelSolicitudEventoGeneral_.tblReservacionEventoGeneral)
						.get(TblReservacionEventoGeneral_.idAreaSede);

				predicates.add(exp.in(areasSede));
			}

			if (!ObjectUtils.isNullOrCero(criterios.getIdEstatusReservacion())) {

				predicates.add(cb.equal(
						root.get(RelSolicitudEventoGeneral_.tblReservacionEventoGeneral)
								.get(TblReservacionEventoGeneral_.catEstatusReservacion).get(CatEstatusReservacion_.id),
						criterios.getIdEstatusReservacion()));
			}

			if (ObjectUtils.isNotNull(criterios.getFechaInicial())
					&& ObjectUtils.isNotNull(criterios.getFechaFinal())) {
				predicates.add(cb.greaterThanOrEqualTo(root.get(RelSolicitudEventoGeneral_.tblReservacionEventoGeneral)
						.get(TblReservacionEventoGeneral_.fechaRegistro), criterios.getFechaInicial()));

				predicates.add(cb.lessThanOrEqualTo(root.get(RelSolicitudEventoGeneral_.tblReservacionEventoGeneral)
						.get(TblReservacionEventoGeneral_.fechaRegistro), criterios.getFechaFinal()));

			} else if (ObjectUtils.isNotNull(criterios.getFechaInicial())) {

				predicates.add(cb.greaterThanOrEqualTo(root.get(RelSolicitudEventoGeneral_.tblReservacionEventoGeneral)
						.get(TblReservacionEventoGeneral_.fechaRegistro), criterios.getFechaInicial()));

			} else if (ObjectUtils.isNotNull(criterios.getFechaFinal())) {

				predicates.add(cb.lessThanOrEqualTo(root.get(RelSolicitudEventoGeneral_.tblReservacionEventoGeneral)
						.get(TblReservacionEventoGeneral_.fechaRegistro), criterios.getFechaFinal()));
			}
		}
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}

}
