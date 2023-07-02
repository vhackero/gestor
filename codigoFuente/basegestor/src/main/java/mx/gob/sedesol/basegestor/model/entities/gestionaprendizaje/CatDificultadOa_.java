package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-22T13:24:23.862-0600")
@StaticMetamodel(CatDificultadOa.class)
public class CatDificultadOa_ {
	public static volatile SingularAttribute<CatDificultadOa, Integer> id;
	public static volatile SingularAttribute<CatDificultadOa, Byte> activo;
	public static volatile SingularAttribute<CatDificultadOa, String> descripcion;
	public static volatile SingularAttribute<CatDificultadOa, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatDificultadOa, Date> fechaRegistro;
	public static volatile SingularAttribute<CatDificultadOa, String> nombre;
	public static volatile SingularAttribute<CatDificultadOa, Integer> orden;
	public static volatile SingularAttribute<CatDificultadOa, BigInteger> usuarioModifico;
}
