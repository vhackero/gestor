package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-24T12:23:59.506-0500")
@StaticMetamodel(CatRecursoDidacticoOa.class)
public class CatRecursoDidacticoOa_ {
	public static volatile SingularAttribute<CatRecursoDidacticoOa, Integer> id;
	public static volatile SingularAttribute<CatRecursoDidacticoOa, Byte> activo;
	public static volatile SingularAttribute<CatRecursoDidacticoOa, String> descripcion;
	public static volatile SingularAttribute<CatRecursoDidacticoOa, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatRecursoDidacticoOa, Date> fechaRegistro;
	public static volatile SingularAttribute<CatRecursoDidacticoOa, String> nombre;
	public static volatile SingularAttribute<CatRecursoDidacticoOa, Integer> orden;
	public static volatile SingularAttribute<CatRecursoDidacticoOa, BigInteger> usuarioModifico;
}
