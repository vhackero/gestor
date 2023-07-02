package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-03-22T18:48:01.675-0600")
@StaticMetamodel(RelSolicitudEventoCapacitacion.class)
public class RelSolicitudEventoCapacitacion_ {
	public static volatile SingularAttribute<RelSolicitudEventoCapacitacion, RelSolicitudEventoCapacitacionPK> id;
	public static volatile SingularAttribute<RelSolicitudEventoCapacitacion, Date> fechaRegistro;
	public static volatile SingularAttribute<RelSolicitudEventoCapacitacion, Long> usuarioModifico;
	public static volatile SingularAttribute<RelSolicitudEventoCapacitacion, TblSolicitudReservacion> tblSolicitudReservacion;
	public static volatile SingularAttribute<RelSolicitudEventoCapacitacion, TblReservacionEventoCapacitacion> tblReservacionEventoCapacitacion;
}
