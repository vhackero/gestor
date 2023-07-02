package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-24T12:23:59.496-0500")
@StaticMetamodel(CatEstatusFoa.class)
public class CatEstatusFoa_ {
	public static volatile SingularAttribute<CatEstatusFoa, Integer> id;
	public static volatile SingularAttribute<CatEstatusFoa, Byte> activo;
	public static volatile SingularAttribute<CatEstatusFoa, String> descripcion;
	public static volatile SingularAttribute<CatEstatusFoa, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatEstatusFoa, Date> fechaRegistro;
	public static volatile SingularAttribute<CatEstatusFoa, String> nombre;
	public static volatile SingularAttribute<CatEstatusFoa, Integer> orden;
	public static volatile SingularAttribute<CatEstatusFoa, BigInteger> usuarioModifico;
}
