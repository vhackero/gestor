package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-27T10:23:02.937-0600")
@StaticMetamodel(RelPlanHabilidad.class)
public class RelPlanHabilidad_ {
	public static volatile SingularAttribute<RelPlanHabilidad, Integer> activo;
	public static volatile SingularAttribute<RelPlanHabilidad, Date> fechaRegistro;
	public static volatile SingularAttribute<RelPlanHabilidad, Long> usuarioModifico;
	public static volatile SingularAttribute<RelPlanHabilidad, CatHabilidadesPlan> catHabilidadesPlan;
	public static volatile SingularAttribute<RelPlanHabilidad, TblPlan> tblPlan;
	public static volatile SingularAttribute<RelPlanHabilidad, Integer> idHabilidad;
	public static volatile SingularAttribute<RelPlanHabilidad, Integer> idPlan;
}
