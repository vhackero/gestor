package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-03-22T18:48:01.784-0600")
@StaticMetamodel(RelSolicitudEventoGeneral.class)
public class RelSolicitudEventoGeneral_ {
	public static volatile SingularAttribute<RelSolicitudEventoGeneral, RelSolicitudEventoGeneralPK> id;
	public static volatile SingularAttribute<RelSolicitudEventoGeneral, Date> fechaRegistro;
	public static volatile SingularAttribute<RelSolicitudEventoGeneral, Long> usuarioModifico;
	public static volatile SingularAttribute<RelSolicitudEventoGeneral, TblSolicitudReservacion> tblSolicitudReservacion;
	public static volatile SingularAttribute<RelSolicitudEventoGeneral, TblReservacionEventoGeneral> tblReservacionEventoGeneral;
}
