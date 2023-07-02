package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.math.BigInteger;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-22T13:24:24.011-0600")
@StaticMetamodel(CatElementosMultimedia.class)
public class CatElementosMultimedia_
{
	public static volatile SingularAttribute<CatElementosMultimedia, Integer> id;
	public static volatile SingularAttribute<CatElementosMultimedia, Byte> activo;
	public static volatile SingularAttribute<CatElementosMultimedia, String> descripcion;
	public static volatile SingularAttribute<CatElementosMultimedia, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatElementosMultimedia, Date> fechaRegistro;
	public static volatile SingularAttribute<CatElementosMultimedia, String> nombre;
	public static volatile SingularAttribute<CatElementosMultimedia, Integer> orden;
	public static volatile SingularAttribute<CatElementosMultimedia, Long> usuarioModifico;
}
