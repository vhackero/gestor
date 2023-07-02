package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-22T15:53:45.265-0600")
@StaticMetamodel(RelGrupoParticipante.class)
public class RelGrupoParticipante_ {
	public static volatile SingularAttribute<RelGrupoParticipante, Integer> id;
	public static volatile SingularAttribute<RelGrupoParticipante, String> clave;
	public static volatile SingularAttribute<RelGrupoParticipante, Date> fechaActualizacion;
	public static volatile SingularAttribute<RelGrupoParticipante, Date> fechaRegistro;
	public static volatile SingularAttribute<RelGrupoParticipante, Integer> idGrupo;
	public static volatile SingularAttribute<RelGrupoParticipante, BigInteger> idPersonaParticipante;
	public static volatile SingularAttribute<RelGrupoParticipante, BigInteger> usuarioModifico;
}
