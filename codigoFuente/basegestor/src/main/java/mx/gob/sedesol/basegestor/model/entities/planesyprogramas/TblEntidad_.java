package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-19T13:30:53.258-0600")
@StaticMetamodel(TblEntidad.class)
public class TblEntidad_ {
	public static volatile SingularAttribute<TblEntidad, Integer> id;
	public static volatile SingularAttribute<TblEntidad, Integer> activo;
	public static volatile SingularAttribute<TblEntidad, String> descripcion;
	public static volatile SingularAttribute<TblEntidad, Date> fechaActualizacion;
	public static volatile SingularAttribute<TblEntidad, Date> fechaRegistro;
	public static volatile SingularAttribute<TblEntidad, Integer> idEntidadPadre;
	public static volatile SingularAttribute<TblEntidad, String> nombre;
	public static volatile SingularAttribute<TblEntidad, Long> usuarioModifico;
}
