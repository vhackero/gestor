package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-22T13:24:24.011-0600")
@StaticMetamodel(CatIdiomaOa.class)
public class CatIdiomaOa_ {
	public static volatile SingularAttribute<CatIdiomaOa, Integer> id;
	public static volatile SingularAttribute<CatIdiomaOa, Byte> activo;
	public static volatile SingularAttribute<CatIdiomaOa, String> descripcion;
	public static volatile SingularAttribute<CatIdiomaOa, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatIdiomaOa, Date> fechaRegistro;
	public static volatile SingularAttribute<CatIdiomaOa, String> nombre;
	public static volatile SingularAttribute<CatIdiomaOa, Integer> orden;
	public static volatile SingularAttribute<CatIdiomaOa, BigInteger> usuarioModifico;
}
