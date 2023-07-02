package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-17T18:05:41.150-0600")
@StaticMetamodel(RelAreaDistribucion.class)
public class RelAreaDistribucion_ {
	public static volatile SingularAttribute<RelAreaDistribucion, RelAreaDistribucionPK> id;
	public static volatile SingularAttribute<RelAreaDistribucion, Integer> activo;
	public static volatile SingularAttribute<RelAreaDistribucion, Date> fechaRegistro;
	public static volatile SingularAttribute<RelAreaDistribucion, Long> usuarioRegistro;
	public static volatile SingularAttribute<RelAreaDistribucion, TblConfiguracionArea> tblConfiguracionArea;
	public static volatile SingularAttribute<RelAreaDistribucion, CatDistribucionArea> catDistribucionArea;
}
