package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-27T10:12:50.113-0600")
@StaticMetamodel(CatHabilidadesPlan.class)
public class CatHabilidadesPlan_ {
	public static volatile SingularAttribute<CatHabilidadesPlan, Integer> id;
	public static volatile SingularAttribute<CatHabilidadesPlan, Integer> activo;
	public static volatile SingularAttribute<CatHabilidadesPlan, String> descripcion;
	public static volatile SingularAttribute<CatHabilidadesPlan, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatHabilidadesPlan, Date> fechaRegistro;
	public static volatile SingularAttribute<CatHabilidadesPlan, String> nombre;
	public static volatile SingularAttribute<CatHabilidadesPlan, Integer> orden;
	public static volatile SingularAttribute<CatHabilidadesPlan, Long> usuarioModifico;
	public static volatile ListAttribute<CatHabilidadesPlan, RelPlanHabilidad> relPlanHabilidades;
}
