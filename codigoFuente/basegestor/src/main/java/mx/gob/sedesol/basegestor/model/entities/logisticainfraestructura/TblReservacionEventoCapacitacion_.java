package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import mx.gob.sedesol.basegestor.model.entities.admin.TblDomiciliosPersona;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;

@Generated(value="Dali", date="2017-03-22T18:48:01.797-0600")
@StaticMetamodel(TblReservacionEventoCapacitacion.class)
public class TblReservacionEventoCapacitacion_ {
	public static volatile SingularAttribute<TblReservacionEventoCapacitacion, Integer> idReservacionEc;
	public static volatile SingularAttribute<TblReservacionEventoCapacitacion, Byte> activo;
	public static volatile SingularAttribute<TblReservacionEventoCapacitacion, Byte> eventoPrivado;
	public static volatile SingularAttribute<TblReservacionEventoCapacitacion, Date> fechaFinalReservacion;
	public static volatile SingularAttribute<TblReservacionEventoCapacitacion, Date> fechaInicialReservacion;
	public static volatile SingularAttribute<TblReservacionEventoCapacitacion, Date> fechaModificacion;
	public static volatile SingularAttribute<TblReservacionEventoCapacitacion, Date> fechaRegistro;
	public static volatile SingularAttribute<TblReservacionEventoCapacitacion, Integer> idAreaSede;
	public static volatile SingularAttribute<TblReservacionEventoCapacitacion, CatEstatusReservacion> catEstatusReservacion;
	public static volatile SingularAttribute<TblReservacionEventoCapacitacion, Integer> idPersonalizacionArea;
	public static volatile SingularAttribute<TblReservacionEventoCapacitacion, Long> usuarioRegistro;
	public static volatile SingularAttribute<TblReservacionEventoCapacitacion, TblEvento> tblEvento;
}
