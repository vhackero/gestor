package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-20T10:44:51.904-0600")
@StaticMetamodel(CatModalidadPlanPrograma.class)
public class CatModalidadPlanPrograma_ {
	public static volatile SingularAttribute<CatModalidadPlanPrograma, Integer> id;
	public static volatile SingularAttribute<CatModalidadPlanPrograma, Integer> activo;
	public static volatile SingularAttribute<CatModalidadPlanPrograma, String> descripcion;
	public static volatile SingularAttribute<CatModalidadPlanPrograma, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatModalidadPlanPrograma, Date> fechaRegistro;
	public static volatile SingularAttribute<CatModalidadPlanPrograma, String> nombre;
	public static volatile SingularAttribute<CatModalidadPlanPrograma, Integer> orden;
	public static volatile SingularAttribute<CatModalidadPlanPrograma, Long> usuarioModifico;
}
