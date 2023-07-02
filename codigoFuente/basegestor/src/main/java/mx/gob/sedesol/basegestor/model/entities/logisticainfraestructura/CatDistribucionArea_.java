package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-17T17:21:46.824-0600")
@StaticMetamodel(CatDistribucionArea.class)
public class CatDistribucionArea_ {
	public static volatile SingularAttribute<CatDistribucionArea, Integer> id;
	public static volatile SingularAttribute<CatDistribucionArea, Integer> activo;
	public static volatile SingularAttribute<CatDistribucionArea, String> decripcion;
	public static volatile SingularAttribute<CatDistribucionArea, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatDistribucionArea, Date> fechaRegistro;
	public static volatile SingularAttribute<CatDistribucionArea, String> nombre;
	public static volatile SingularAttribute<CatDistribucionArea, Integer> orden;
	public static volatile SingularAttribute<CatDistribucionArea, Long> usuarioModifico;
	public static volatile ListAttribute<CatDistribucionArea, RelAreaDistribucion> relAreaDistribucions;
}
