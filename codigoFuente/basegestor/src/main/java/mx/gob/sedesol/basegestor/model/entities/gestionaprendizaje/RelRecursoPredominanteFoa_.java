package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-24T12:23:59.518-0500")
@StaticMetamodel(RelRecursoPredominanteFoa.class)
public class RelRecursoPredominanteFoa_ {
	public static volatile SingularAttribute<RelRecursoPredominanteFoa, RelRecursoPredominanteFoaPK> id;
	public static volatile SingularAttribute<RelRecursoPredominanteFoa, Date> fechaActualizacion;
	public static volatile SingularAttribute<RelRecursoPredominanteFoa, Date> fechaRegistro;
	public static volatile SingularAttribute<RelRecursoPredominanteFoa, BigInteger> usuarioModifico;
	public static volatile SingularAttribute<RelRecursoPredominanteFoa, CatRecursoPredominanteFoa> catRecursoPredominanteFoa;
	public static volatile SingularAttribute<RelRecursoPredominanteFoa, TblFichaDescriptivaObjetoAprendizaje> tblFichaDescriptivaObjetoAprendizaje;
}
