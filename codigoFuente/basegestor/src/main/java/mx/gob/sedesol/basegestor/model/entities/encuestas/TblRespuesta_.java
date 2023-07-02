package mx.gob.sedesol.basegestor.model.entities.encuestas;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-16T13:00:14.927-0600")
@StaticMetamodel(TblRespuesta.class)
public class TblRespuesta_ {
	public static volatile SingularAttribute<TblRespuesta, Integer> idRespuesta;
	public static volatile SingularAttribute<TblRespuesta, Integer> idPregunta;
	public static volatile SingularAttribute<TblRespuesta, Integer> ponderacion;
	public static volatile SingularAttribute<TblRespuesta, RelRespuestaUsuario> relRespuestaUsuario;
}
