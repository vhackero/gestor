package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-08-16T12:18:14.682-0500")
@StaticMetamodel(CatModeloPedagogicoPlan.class)
public class CatModeloPedagogicoPlan_ {
	public static volatile SingularAttribute<CatModeloPedagogicoPlan, Long> id;
	public static volatile SingularAttribute<CatModeloPedagogicoPlan, Integer> activo;
	public static volatile SingularAttribute<CatModeloPedagogicoPlan, String> descripcion;
	public static volatile SingularAttribute<CatModeloPedagogicoPlan, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatModeloPedagogicoPlan, Date> fechaRegistro;
	public static volatile SingularAttribute<CatModeloPedagogicoPlan, String> nombre;
	public static volatile SingularAttribute<CatModeloPedagogicoPlan, Integer> orden;
	public static volatile SingularAttribute<CatModeloPedagogicoPlan, Long> usuarioModifico;
}
