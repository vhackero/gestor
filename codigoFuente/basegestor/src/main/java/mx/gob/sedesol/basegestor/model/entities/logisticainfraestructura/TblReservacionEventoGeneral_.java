package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.math.BigInteger;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-03-22T18:48:01.798-0600")
@StaticMetamodel(TblReservacionEventoGeneral.class)
public class TblReservacionEventoGeneral_ {
	public static volatile SingularAttribute<TblReservacionEventoGeneral, Integer> idReservacionEg;
	public static volatile SingularAttribute<TblReservacionEventoGeneral, Byte> activo;
	public static volatile SingularAttribute<TblReservacionEventoGeneral, Byte> eventoPrivado;
	public static volatile SingularAttribute<TblReservacionEventoGeneral, Date> fechaFinalReservacion;
	public static volatile SingularAttribute<TblReservacionEventoGeneral, Date> fechaInicialReservacion;
	public static volatile SingularAttribute<TblReservacionEventoGeneral, Date> fechaModificacion;
	public static volatile SingularAttribute<TblReservacionEventoGeneral, Date> fechaRegistro;
	public static volatile SingularAttribute<TblReservacionEventoGeneral, Integer> idAreaSede;
	public static volatile SingularAttribute<TblReservacionEventoGeneral, CatEstatusReservacion> catEstatusReservacion;
	public static volatile SingularAttribute<TblReservacionEventoGeneral, Integer> idPersonalizacionArea;
	public static volatile SingularAttribute<TblReservacionEventoGeneral, Long> usuarioRegistro;
	public static volatile SingularAttribute<TblReservacionEventoGeneral, TblEventoGeneral> tblEventosGenerale;
}
