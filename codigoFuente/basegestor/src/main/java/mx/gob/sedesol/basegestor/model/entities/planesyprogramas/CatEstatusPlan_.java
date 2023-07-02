package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-27T10:11:49.318-0600")
@StaticMetamodel(CatEstatusPlan.class)
public class CatEstatusPlan_ {
	public static volatile SingularAttribute<CatEstatusPlan, Integer> id;
	public static volatile SingularAttribute<CatEstatusPlan, Integer> activo;
	public static volatile SingularAttribute<CatEstatusPlan, String> descripcion;
	public static volatile SingularAttribute<CatEstatusPlan, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatEstatusPlan, Date> fechaRegistro;
	public static volatile SingularAttribute<CatEstatusPlan, String> nombre;
	public static volatile SingularAttribute<CatEstatusPlan, Integer> orden;
	public static volatile SingularAttribute<CatEstatusPlan, Long> usuarioModifico;
	public static volatile ListAttribute<CatEstatusPlan, TblPlan> tblPlans;
}
