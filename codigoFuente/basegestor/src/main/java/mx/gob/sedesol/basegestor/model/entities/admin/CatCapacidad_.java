package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-07-13T12:40:10.214-0500")
@StaticMetamodel(CatCapacidad.class)
public class CatCapacidad_ {
	public static volatile SingularAttribute<CatCapacidad, Integer> id;
	public static volatile SingularAttribute<CatCapacidad, Integer> activo;
	public static volatile SingularAttribute<CatCapacidad, String> descripcion;
	public static volatile SingularAttribute<CatCapacidad, Date> fechaModificacion;
	public static volatile SingularAttribute<CatCapacidad, Date> fechaRegistro;
	public static volatile SingularAttribute<CatCapacidad, String> nombre;
	public static volatile SingularAttribute<CatCapacidad, Long> usuarioModifico;
}
