package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.math.BigInteger;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-24T12:23:59.511-0500")
@StaticMetamodel(RelCatRelacionOtrosObjetosFoa.class)
public class RelCatRelacionOtrosObjetosFoa_ {
	public static volatile SingularAttribute<RelCatRelacionOtrosObjetosFoa, RelCatRelacionOtrosObjetosFoaPK> id;
	public static volatile SingularAttribute<RelCatRelacionOtrosObjetosFoa, Date> fechaActualizacion;
	public static volatile SingularAttribute<RelCatRelacionOtrosObjetosFoa, Date> fechaRegistro;
	public static volatile SingularAttribute<RelCatRelacionOtrosObjetosFoa, BigInteger> usuarioModifico;
	public static volatile SingularAttribute<RelCatRelacionOtrosObjetosFoa, CatRelacionOtrosObjeto> catRelacionOtrosObjeto;
	public static volatile SingularAttribute<RelCatRelacionOtrosObjetosFoa, TblFichaDescriptivaObjetoAprendizaje> tblFichaDescriptivaObjetoAprendizaje;
}
