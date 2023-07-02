package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-17T18:07:18.990-0600")
@StaticMetamodel(RelAreaRecurso.class)
public class RelAreaRecurso_ {
	public static volatile SingularAttribute<RelAreaRecurso, RelAreaRecursoPK> id;
	public static volatile SingularAttribute<RelAreaRecurso, Integer> activo;
	public static volatile SingularAttribute<RelAreaRecurso, Integer> cantidad;
	public static volatile SingularAttribute<RelAreaRecurso, Date> fechaRegistro;
	public static volatile SingularAttribute<RelAreaRecurso, Long> usuarioRegistro;
	public static volatile SingularAttribute<RelAreaRecurso, CatRecursosInfraestructura> catRecursosInfraestructura;
	public static volatile SingularAttribute<RelAreaRecurso, TblConfiguracionArea> tblConfiguracionArea;
}
