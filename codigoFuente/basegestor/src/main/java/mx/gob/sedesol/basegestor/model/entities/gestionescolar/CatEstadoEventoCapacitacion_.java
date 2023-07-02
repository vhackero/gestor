package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-07T15:53:15.800-0600")
@StaticMetamodel(CatEstadoEventoCapacitacion.class)
public class CatEstadoEventoCapacitacion_ {
	public static volatile SingularAttribute<CatEstadoEventoCapacitacion, Integer> id;
	public static volatile SingularAttribute<CatEstadoEventoCapacitacion, Integer> activo;
	public static volatile SingularAttribute<CatEstadoEventoCapacitacion, String> descripcion;
	public static volatile SingularAttribute<CatEstadoEventoCapacitacion, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatEstadoEventoCapacitacion, Date> fechaRegistro;
	public static volatile SingularAttribute<CatEstadoEventoCapacitacion, String> nombre;
	public static volatile SingularAttribute<CatEstadoEventoCapacitacion, Integer> orden;
	public static volatile SingularAttribute<CatEstadoEventoCapacitacion, BigInteger> usuarioModifico;
	public static volatile ListAttribute<CatEstadoEventoCapacitacion, TblEvento> tblEventos;
}
