package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-24T13:24:22.060-0500")
@StaticMetamodel(RelMaterialDidacticoFoa.class)
public class RelMaterialDidacticoFoa_ {
	public static volatile SingularAttribute<RelMaterialDidacticoFoa, RelMaterialDidacticoFoaPK> id;
	public static volatile SingularAttribute<RelMaterialDidacticoFoa, Date> fechaActualizacion;
	public static volatile SingularAttribute<RelMaterialDidacticoFoa, Date> fechaRegistro;
	public static volatile SingularAttribute<RelMaterialDidacticoFoa, BigInteger> usuarioModifico;
	public static volatile SingularAttribute<RelMaterialDidacticoFoa, CatRecursoDidacticoOa> catRecursoDidacticoOa;
}
