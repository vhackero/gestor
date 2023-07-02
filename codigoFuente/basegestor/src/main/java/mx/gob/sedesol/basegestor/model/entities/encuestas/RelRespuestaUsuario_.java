package mx.gob.sedesol.basegestor.model.entities.encuestas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-16T13:00:14.924-0600")
@StaticMetamodel(RelRespuestaUsuario.class)
public class RelRespuestaUsuario_ {
	public static volatile SingularAttribute<RelRespuestaUsuario, Integer> idRespuestaUsuario;
	public static volatile SingularAttribute<RelRespuestaUsuario, Date> fechaRegistro;
	public static volatile SingularAttribute<RelRespuestaUsuario, Integer> idEncuesta;
	public static volatile SingularAttribute<RelRespuestaUsuario, Integer> idUsuario;
	public static volatile ListAttribute<RelRespuestaUsuario, TblRespuesta> tblRespuestas;
}
