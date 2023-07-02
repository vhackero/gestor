package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-26T18:49:30.855-0500")
@StaticMetamodel(RelGrupoEvaluacion.class)
public class RelGrupoEvaluacion_ {
	public static volatile SingularAttribute<RelGrupoEvaluacion, Integer> idGpoEvaluacion;
	public static volatile SingularAttribute<RelGrupoEvaluacion, Date> fechaRegistro;
	public static volatile SingularAttribute<RelGrupoEvaluacion, String> nombreEvaluacion;
	public static volatile SingularAttribute<RelGrupoEvaluacion, BigInteger> usuarioModifico;
	public static volatile ListAttribute<RelGrupoEvaluacion, RelEvaluacionCalificacion> relEvaluacionCalificacions;
	public static volatile SingularAttribute<RelGrupoEvaluacion, TblGrupo> tblGrupo;
	public static volatile SingularAttribute<RelGrupoEvaluacion, CatTipoCalificacionEc> catTipoCalificacionEc;
}
