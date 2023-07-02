package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-19T13:30:53.166-0600")
@StaticMetamodel(CatStatusPrograma.class)
public class CatStatusPrograma_ {
	public static volatile SingularAttribute<CatStatusPrograma, Integer> id;
	public static volatile SingularAttribute<CatStatusPrograma, Integer> activo;
	public static volatile SingularAttribute<CatStatusPrograma, String> descripcion;
	public static volatile SingularAttribute<CatStatusPrograma, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatStatusPrograma, Date> fechaRegistro;
	public static volatile SingularAttribute<CatStatusPrograma, String> nombre;
	public static volatile SingularAttribute<CatStatusPrograma, Integer> orden;
	public static volatile SingularAttribute<CatStatusPrograma, Long> usuarioModifico;
}
