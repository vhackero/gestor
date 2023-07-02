package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-17T17:58:24.989-0600")
@StaticMetamodel(CatTipoRecurso.class)
public class CatTipoRecurso_ {
	public static volatile SingularAttribute<CatTipoRecurso, Integer> id;
	public static volatile SingularAttribute<CatTipoRecurso, Integer> activo;
	public static volatile SingularAttribute<CatTipoRecurso, String> decripcion;
	public static volatile SingularAttribute<CatTipoRecurso, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatTipoRecurso, Date> fechaRegistro;
	public static volatile SingularAttribute<CatTipoRecurso, String> nombre;
	public static volatile SingularAttribute<CatTipoRecurso, Integer> orden;
	public static volatile SingularAttribute<CatTipoRecurso, Long> usuarioModifico;
	public static volatile ListAttribute<CatTipoRecurso, CatRecursosInfraestructura> catRecursosInfraestructuras;
}
