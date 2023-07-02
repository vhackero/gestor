package mx.gob.sedesol.basegestor.model.entities.encuestas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelGrupoParticipante;

@Generated(value="Dali", date="2017-05-18T09:52:06.417-0500")
@StaticMetamodel(RelEncuestaUsuario.class)
public class RelEncuestaUsuario_ {
	public static volatile SingularAttribute<RelEncuestaUsuario, RelEncuestaUsuarioPK> id;
	public static volatile SingularAttribute<RelEncuestaUsuario, Boolean> activo;
	public static volatile SingularAttribute<RelEncuestaUsuario, Date> fechaActualizacion;
	public static volatile SingularAttribute<RelEncuestaUsuario, Date> fechaRegistro;
	public static volatile SingularAttribute<RelEncuestaUsuario, Date> fechaApertura;
	public static volatile SingularAttribute<RelEncuestaUsuario, Long> usuarioModifico;
	public static volatile SingularAttribute<RelEncuestaUsuario, RelGrupoParticipante> relGrupoParticipante;
	public static volatile SingularAttribute<RelEncuestaUsuario, TblEncuesta> tblEncuesta;
}
