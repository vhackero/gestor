package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-17T17:23:39.252-0600")
@StaticMetamodel(CatRecursosInfraestructura.class)
public class CatRecursosInfraestructura_ {
	public static volatile SingularAttribute<CatRecursosInfraestructura, Integer> idRecurso;
	public static volatile SingularAttribute<CatRecursosInfraestructura, Integer> activo;
	public static volatile SingularAttribute<CatRecursosInfraestructura, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatRecursosInfraestructura, Date> fechaRegistro;
	public static volatile SingularAttribute<CatRecursosInfraestructura, String> nombre;
	public static volatile SingularAttribute<CatRecursosInfraestructura, Integer> orden;
	public static volatile SingularAttribute<CatRecursosInfraestructura, Long> usuarioModifico;
	public static volatile SingularAttribute<CatRecursosInfraestructura, CatTipoRecurso> catTipoRecurso;
	public static volatile ListAttribute<CatRecursosInfraestructura, RelAreaRecurso> relAreaRecursos;
}
