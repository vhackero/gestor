package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-24T12:23:59.508-0500")
@StaticMetamodel(CatRecursoPredominanteFoa.class)
public class CatRecursoPredominanteFoa_ {
	public static volatile SingularAttribute<CatRecursoPredominanteFoa, Integer> id;
	public static volatile SingularAttribute<CatRecursoPredominanteFoa, Byte> activo;
	public static volatile SingularAttribute<CatRecursoPredominanteFoa, String> descripcion;
	public static volatile SingularAttribute<CatRecursoPredominanteFoa, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatRecursoPredominanteFoa, Date> fechaRegistro;
	public static volatile SingularAttribute<CatRecursoPredominanteFoa, String> nombre;
	public static volatile SingularAttribute<CatRecursoPredominanteFoa, Byte> orden;
	public static volatile SingularAttribute<CatRecursoPredominanteFoa, BigInteger> usuarioModifico;
}
