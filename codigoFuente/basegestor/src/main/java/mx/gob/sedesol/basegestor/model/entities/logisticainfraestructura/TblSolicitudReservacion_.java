package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-03-22T18:48:01.799-0600")
@StaticMetamodel(TblSolicitudReservacion.class)
public class TblSolicitudReservacion_ {
	public static volatile SingularAttribute<TblSolicitudReservacion, Integer> idSolicitud;
	public static volatile SingularAttribute<TblSolicitudReservacion, Date> fechaActualizacion;
	public static volatile SingularAttribute<TblSolicitudReservacion, Date> fechaRegistro;
	public static volatile SingularAttribute<TblSolicitudReservacion, String> nombreSolicitud;
	public static volatile SingularAttribute<TblSolicitudReservacion, Long> usuarioModifico;
	public static volatile SingularAttribute<TblSolicitudReservacion, Byte> privado;
	public static volatile SingularAttribute<TblSolicitudReservacion, String> solicitante;
	public static volatile SingularAttribute<TblSolicitudReservacion, String> emailSolicitante;
	public static volatile ListAttribute<TblSolicitudReservacion, RelSolicitudEventoCapacitacion> relSolicitudEventoCapacitacions;
	public static volatile ListAttribute<TblSolicitudReservacion, RelSolicitudEventoGeneral> relSolicitudEventoGenerals;
}
