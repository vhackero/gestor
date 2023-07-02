package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-29T10:57:04.324-0600")
@StaticMetamodel(CatAlcancePlan.class)
public class CatAlcancePlan_ {
	public static volatile SingularAttribute<CatAlcancePlan, Integer> id;
	public static volatile SingularAttribute<CatAlcancePlan, Integer> activo;
	public static volatile SingularAttribute<CatAlcancePlan, String> descripcion;
	public static volatile SingularAttribute<CatAlcancePlan, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatAlcancePlan, Date> fechaRegistro;
	public static volatile SingularAttribute<CatAlcancePlan, String> nombre;
	public static volatile SingularAttribute<CatAlcancePlan, Integer> orden;
	public static volatile SingularAttribute<CatAlcancePlan, Long> usuarioModifico;
}
