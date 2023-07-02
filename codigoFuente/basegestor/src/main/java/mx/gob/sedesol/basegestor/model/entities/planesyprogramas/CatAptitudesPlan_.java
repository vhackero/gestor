package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-27T10:07:04.197-0600")
@StaticMetamodel(CatAptitudesPlan.class)
public class CatAptitudesPlan_ {
	public static volatile SingularAttribute<CatAptitudesPlan, Integer> id;
	public static volatile SingularAttribute<CatAptitudesPlan, Integer> activo;
	public static volatile SingularAttribute<CatAptitudesPlan, String> descripcion;
	public static volatile SingularAttribute<CatAptitudesPlan, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatAptitudesPlan, Date> fechaRegistro;
	public static volatile SingularAttribute<CatAptitudesPlan, String> nombre;
	public static volatile SingularAttribute<CatAptitudesPlan, Integer> orden;
	public static volatile SingularAttribute<CatAptitudesPlan, Long> usuarioModifico;
	public static volatile ListAttribute<CatAptitudesPlan, RelPlanAptitud> relPlanAptitudes;
}
