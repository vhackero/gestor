package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-19T13:30:53.194-0600")
@StaticMetamodel(RelMallaPlan.class)
public class RelMallaPlan_ {
	public static volatile SingularAttribute<RelMallaPlan, Integer> id;
	public static volatile SingularAttribute<RelMallaPlan, Integer> idPlan;
	
	public static volatile SingularAttribute<RelMallaPlan, String> nombreEstructuras;
	public static volatile SingularAttribute<RelMallaPlan, Integer> elementosEstructuras;
	

	public static volatile SingularAttribute<RelMallaPlan, Integer> niveles;

	public static volatile SingularAttribute<RelMallaPlan, String> nombre_subestructuras_1;
	public static volatile SingularAttribute<RelMallaPlan, Integer> elementos_subestructuras_1;
	

	public static volatile SingularAttribute<RelMallaPlan, String> nombre_subestructuras_2;
	public static volatile SingularAttribute<RelMallaPlan, Integer> elementos_subestructuras_2;
	

	public static volatile SingularAttribute<RelMallaPlan, String> nombre_subestructuras_3;
	public static volatile SingularAttribute<RelMallaPlan, Integer> elementos_subestructuras_3;
	
	public static volatile SingularAttribute<RelMallaPlan, String> activo;
	public static volatile SingularAttribute<RelMallaPlan, Date> fechaRegistro;
	public static volatile SingularAttribute<RelMallaPlan, Long> usuarioModifico;
}
