package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-03-21T16:57:11.156-0600")
@StaticMetamodel(CatTemaRecurso.class)
public class CatTemaRecurso_ {
	public static volatile SingularAttribute<CatTemaRecurso, Integer> id;
	public static volatile SingularAttribute<CatTemaRecurso, Byte> activo;
	public static volatile SingularAttribute<CatTemaRecurso, String> descripcion;
	public static volatile SingularAttribute<CatTemaRecurso, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatTemaRecurso, Date> fechaRegistro;
	public static volatile SingularAttribute<CatTemaRecurso, String> nombre;
	public static volatile SingularAttribute<CatTemaRecurso, Integer> orden;
	public static volatile SingularAttribute<CatTemaRecurso, BigInteger> usuarioModifico;
}
