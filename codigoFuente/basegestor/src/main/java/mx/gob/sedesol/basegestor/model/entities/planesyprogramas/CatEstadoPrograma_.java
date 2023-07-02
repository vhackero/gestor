package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-08-16T12:18:14.676-0500")
@StaticMetamodel(CatEstadoPrograma.class)
public class CatEstadoPrograma_ {
	public static volatile SingularAttribute<CatEstadoPrograma, Long> id;
	public static volatile SingularAttribute<CatEstadoPrograma, Integer> activo;
	public static volatile SingularAttribute<CatEstadoPrograma, String> descripcion;
	public static volatile SingularAttribute<CatEstadoPrograma, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatEstadoPrograma, Date> fechaRegistro;
	public static volatile SingularAttribute<CatEstadoPrograma, String> nombre;
	public static volatile SingularAttribute<CatEstadoPrograma, Integer> orden;
	public static volatile SingularAttribute<CatEstadoPrograma, Long> usuarioModifico;
}
