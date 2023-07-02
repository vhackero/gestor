package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-17T17:23:01.956-0600")
@StaticMetamodel(CatEstatusArea.class)
public class CatEstatusArea_ {
	public static volatile SingularAttribute<CatEstatusArea, Integer> id;
	public static volatile SingularAttribute<CatEstatusArea, Integer> activo;
	public static volatile SingularAttribute<CatEstatusArea, String> decripcion;
	public static volatile SingularAttribute<CatEstatusArea, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatEstatusArea, Date> fechaRegistro;
	public static volatile SingularAttribute<CatEstatusArea, String> nombre;
	public static volatile SingularAttribute<CatEstatusArea, Integer> orden;
	public static volatile SingularAttribute<CatEstatusArea, Long> usuarioModifico;
	public static volatile ListAttribute<CatEstatusArea, CatAreasSede> catAreasSedes;
}
