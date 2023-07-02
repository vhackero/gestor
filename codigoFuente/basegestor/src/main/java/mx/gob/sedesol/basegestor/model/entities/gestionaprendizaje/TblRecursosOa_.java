package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-03-21T16:57:11.164-0600")
@StaticMetamodel(TblRecursosOa.class)
public class TblRecursosOa_ {
	public static volatile SingularAttribute<TblRecursosOa, Integer> id;
	public static volatile SingularAttribute<TblRecursosOa, Date> fechaActualizacion;
	public static volatile SingularAttribute<TblRecursosOa, Date> fechaRegistro;
	public static volatile SingularAttribute<TblRecursosOa, String> instruccionesContenido;
	public static volatile SingularAttribute<TblRecursosOa, String> nombre;
	public static volatile SingularAttribute<TblRecursosOa, BigInteger> usuarioModifico;
	public static volatile SingularAttribute<TblRecursosOa, CatTemaRecurso> catTemaRecurso;
	public static volatile SingularAttribute<TblRecursosOa, RelUnidadOaAva> unidadOaAva;
}
