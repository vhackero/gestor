package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-24T12:23:59.501-0500")
@StaticMetamodel(CatFormatoFoa.class)
public class CatFormatoFoa_ {
	public static volatile SingularAttribute<CatFormatoFoa, Integer> id;
	public static volatile SingularAttribute<CatFormatoFoa, Byte> activo;
	public static volatile SingularAttribute<CatFormatoFoa, String> descripcion;
	public static volatile SingularAttribute<CatFormatoFoa, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatFormatoFoa, Date> fechaRegistro;
	public static volatile SingularAttribute<CatFormatoFoa, String> nombre;
	public static volatile SingularAttribute<CatFormatoFoa, Integer> orden;
	public static volatile SingularAttribute<CatFormatoFoa, BigInteger> usuarioModifico;
}
