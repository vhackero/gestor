package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-01-17T16:41:17.302-0600")
@StaticMetamodel(TblSubtemasUdidactica.class)
public class TblSubtemasUdidactica_ {
	public static volatile SingularAttribute<TblSubtemasUdidactica, Integer> idSubtemaUdidactica;
	public static volatile SingularAttribute<TblSubtemasUdidactica, Integer> activo;
	public static volatile SingularAttribute<TblSubtemasUdidactica, Date> fechaActualizacion;
	public static volatile SingularAttribute<TblSubtemasUdidactica, Date> fechaRegistro;
	public static volatile SingularAttribute<TblSubtemasUdidactica, Integer> horas;
	public static volatile SingularAttribute<TblSubtemasUdidactica, Integer> minutos;
	public static volatile SingularAttribute<TblSubtemasUdidactica, String> nombre;
	public static volatile SingularAttribute<TblSubtemasUdidactica, BigInteger> usuarioModifico;
	public static volatile SingularAttribute<TblSubtemasUdidactica, DetEstUnidadDidactica> detEstUnidadDidactica;
}
