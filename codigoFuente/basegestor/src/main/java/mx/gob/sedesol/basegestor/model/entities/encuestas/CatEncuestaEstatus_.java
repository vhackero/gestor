package mx.gob.sedesol.basegestor.model.entities.encuestas;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-16T13:00:14.921-0600")
@StaticMetamodel(CatEncuestaEstatus.class)
public class CatEncuestaEstatus_ {
	public static volatile SingularAttribute<CatEncuestaEstatus, Integer> id;
	public static volatile SingularAttribute<CatEncuestaEstatus, String> descripcion;
	public static volatile SingularAttribute<CatEncuestaEstatus, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatEncuestaEstatus, Date> fechaRegistro;
	public static volatile SingularAttribute<CatEncuestaEstatus, String> nombre;
	public static volatile SingularAttribute<CatEncuestaEstatus, BigInteger> usuarioModifico;
	public static volatile ListAttribute<CatEncuestaEstatus, TblEncuesta> tblEncuestas;
	public static volatile SingularAttribute<CatEncuestaEstatus, Byte> activo;
	public static volatile SingularAttribute<CatEncuestaEstatus, Byte> orden;
}
