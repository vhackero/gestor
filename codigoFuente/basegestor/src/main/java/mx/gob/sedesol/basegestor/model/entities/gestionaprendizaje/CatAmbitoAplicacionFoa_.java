package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-27T15:40:26.717-0600")
@StaticMetamodel(CatAmbitoAplicacionFoa.class)
public class CatAmbitoAplicacionFoa_ {
	public static volatile SingularAttribute<CatAmbitoAplicacionFoa, Integer> id;
	public static volatile SingularAttribute<CatAmbitoAplicacionFoa, Byte> activo;
	public static volatile SingularAttribute<CatAmbitoAplicacionFoa, String> descripcion;
	public static volatile SingularAttribute<CatAmbitoAplicacionFoa, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatAmbitoAplicacionFoa, Date> fechaRegistro;
	public static volatile SingularAttribute<CatAmbitoAplicacionFoa, String> nombre;
	public static volatile SingularAttribute<CatAmbitoAplicacionFoa, Byte> orden;
	public static volatile SingularAttribute<CatAmbitoAplicacionFoa, BigInteger> usuarioModifico;
}
