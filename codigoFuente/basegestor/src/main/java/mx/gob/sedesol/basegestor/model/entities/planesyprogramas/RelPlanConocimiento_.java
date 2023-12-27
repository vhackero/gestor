package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-27T10:20:43.125-0600")
@StaticMetamodel(RelPlanConocimiento.class)
public class RelPlanConocimiento_ {
	public static volatile SingularAttribute<RelPlanConocimiento, Integer> activo;
	public static volatile SingularAttribute<RelPlanConocimiento, Date> fechaRegistro;
	public static volatile SingularAttribute<RelPlanConocimiento, Long> usuarioModifico;
	public static volatile SingularAttribute<RelPlanConocimiento, CatConocimientosPlan> catAreasConocimiento;
	public static volatile SingularAttribute<RelPlanConocimiento, TblPlan> tblPlan;
	public static volatile SingularAttribute<RelPlanConocimiento, Integer> idPlan;
	public static volatile SingularAttribute<RelPlanConocimiento, Integer> idCatConocimiento;
}
