package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-10-10T13:18:25.462-0500")
@StaticMetamodel(TblTema.class)
public class TblTema_ {
	public static volatile SingularAttribute<TblTema, Integer> idTema;
	public static volatile SingularAttribute<TblTema, Boolean> activo;
	public static volatile SingularAttribute<TblTema, Date> fechaActualizacion;
	public static volatile SingularAttribute<TblTema, Date> fechaRegistro;
	public static volatile SingularAttribute<TblTema, String> nombre;
	public static volatile SingularAttribute<TblTema, String> ruta;
	public static volatile SingularAttribute<TblTema, Integer> tipoTema;
	public static volatile SingularAttribute<TblTema, Long> usuarioModifico;
}
