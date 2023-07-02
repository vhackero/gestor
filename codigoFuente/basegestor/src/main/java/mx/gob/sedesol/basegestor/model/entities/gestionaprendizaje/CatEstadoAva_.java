package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;



/**
 * The persistent class for the cat_estado_ava database table.
 * 
 */
@Generated(value="Dali", date="2017-02-07T15:53:15.800-0600")
@StaticMetamodel(CatEstadoAva.class)
public class CatEstadoAva_  {	

	public static volatile SingularAttribute<CatEstadoAva, Integer> id;
	public static volatile SingularAttribute<CatEstadoAva, Integer> activo;
	public static volatile SingularAttribute<CatEstadoAva, String> descripcion;
	public static volatile SingularAttribute<CatEstadoAva, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatEstadoAva, Date> fechaRegistro;
	public static volatile SingularAttribute<CatEstadoAva, String> nombre;
	public static volatile SingularAttribute<CatEstadoAva, Integer> orden;
	public static volatile SingularAttribute<CatEstadoAva, BigInteger> usuarioModifico;	
	

}