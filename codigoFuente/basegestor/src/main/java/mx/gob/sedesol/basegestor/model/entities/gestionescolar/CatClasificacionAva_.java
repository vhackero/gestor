package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-03T10:58:40.922-0600")
@StaticMetamodel(CatClasificacionAva.class)
public class CatClasificacionAva_ {
	public static volatile SingularAttribute<CatClasificacionAva, Integer> id;
	public static volatile SingularAttribute<CatClasificacionAva, Byte> activo;
	public static volatile SingularAttribute<CatClasificacionAva, String> descripcion;
	public static volatile SingularAttribute<CatClasificacionAva, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatClasificacionAva, Date> fechaRegistro;
	public static volatile SingularAttribute<CatClasificacionAva, String> nombre;
	public static volatile SingularAttribute<CatClasificacionAva, Integer> orden;
	public static volatile SingularAttribute<CatClasificacionAva, BigInteger> usuarioModifico;
}
