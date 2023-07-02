package mx.gob.sedesol.basegestor.model.entities.encuestas;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-16T13:00:14.922-0600")
@StaticMetamodel(CatEncuestaObjetivo.class)
public class CatEncuestaObjetivo_ {
	public static volatile SingularAttribute<CatEncuestaObjetivo, Integer> id;
	public static volatile SingularAttribute<CatEncuestaObjetivo, String> descripcion;
	public static volatile SingularAttribute<CatEncuestaObjetivo, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatEncuestaObjetivo, Date> fechaRegistro;
	public static volatile SingularAttribute<CatEncuestaObjetivo, String> nombre;
	public static volatile SingularAttribute<CatEncuestaObjetivo, BigInteger> usuarioModifico;
	public static volatile ListAttribute<CatEncuestaObjetivo, TblEncuesta> tblEncuestas;
	public static volatile SingularAttribute<CatEncuestaObjetivo, Byte> activo;
	public static volatile SingularAttribute<CatEncuestaObjetivo, Byte> orden;
}
