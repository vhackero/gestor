package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-08-16T12:18:14.740-0500")
@StaticMetamodel(CatTipoPlan.class)
public class CatTipoPlan_ {
	public static volatile SingularAttribute<CatTipoPlan, Long> id;
	public static volatile SingularAttribute<CatTipoPlan, Integer> activo;
	public static volatile SingularAttribute<CatTipoPlan, String> descripcion;
	public static volatile SingularAttribute<CatTipoPlan, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatTipoPlan, Date> fechaRegistro;
	public static volatile SingularAttribute<CatTipoPlan, String> nombre;
	public static volatile SingularAttribute<CatTipoPlan, Integer> orden;
	public static volatile SingularAttribute<CatTipoPlan, Long> usuarioModifico;
}
