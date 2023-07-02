package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-27T10:21:54.577-0600")
@StaticMetamodel(RelPlanAptitud.class)
public class RelPlanAptitud_ {
	public static volatile SingularAttribute<RelPlanAptitud, Integer> activo;
	public static volatile SingularAttribute<RelPlanAptitud, Date> fechaRegistro;
	public static volatile SingularAttribute<RelPlanAptitud, Long> usuarioModifico;
	public static volatile SingularAttribute<RelPlanAptitud, CatAptitudesPlan> catAptitudesPlan;
	public static volatile SingularAttribute<RelPlanAptitud, TblPlan> tblPlan;
	public static volatile SingularAttribute<RelPlanAptitud, Integer> idPlan;
	public static volatile SingularAttribute<RelPlanAptitud, Integer> idAptitud;
}
