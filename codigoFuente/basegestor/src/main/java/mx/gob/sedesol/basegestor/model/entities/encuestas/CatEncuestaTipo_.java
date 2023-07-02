package mx.gob.sedesol.basegestor.model.entities.encuestas;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-01-11T12:20:55.810-0600")
@StaticMetamodel(CatEncuestaTipo.class)
public class CatEncuestaTipo_ {
	public static volatile SingularAttribute<CatEncuestaTipo, Integer> id;
	public static volatile SingularAttribute<CatEncuestaTipo, String> descripcion;
	public static volatile SingularAttribute<CatEncuestaTipo, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatEncuestaTipo, Date> fechaRegistro;
	public static volatile SingularAttribute<CatEncuestaTipo, String> nombre;
	public static volatile SingularAttribute<CatEncuestaTipo, BigInteger> usuarioModifico;
	public static volatile SingularAttribute<CatEncuestaTipo, CatEncuestaContexto> contexto;
	public static volatile ListAttribute<CatEncuestaTipo, TblEncuesta> tblEncuestas;
	public static volatile SingularAttribute<CatEncuestaTipo, Integer> activo;
	public static volatile SingularAttribute<CatEncuestaTipo, Integer> orden;
}
