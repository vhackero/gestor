package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-01-23T11:39:28.536-0600")
@StaticMetamodel(DetEtematicaTema.class)
public class DetEtematicaTema_ {
	public static volatile SingularAttribute<DetEtematicaTema, Integer> idDetTema;
	public static volatile SingularAttribute<DetEtematicaTema, Date> fechaActualizacion;
	public static volatile SingularAttribute<DetEtematicaTema, Date> fechaRegistro;
	public static volatile SingularAttribute<DetEtematicaTema, String> nombreTema;
	public static volatile SingularAttribute<DetEtematicaTema, Long> usuarioModifico;
	public static volatile SingularAttribute<DetEtematicaTema, TblEstructuraTematica> tblEstructuraTematica;
	public static volatile ListAttribute<DetEtematicaTema, RelEstructuraUnidadDidactica> relEstructuraUnidadDidacticas;
}
