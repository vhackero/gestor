package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-22T15:53:45.119-0600")
@StaticMetamodel(RelAsistencia.class)
public class RelAsistencia_ {
	public static volatile SingularAttribute<RelAsistencia, Date> fechaActualizacion;
	public static volatile SingularAttribute<RelAsistencia, Date> fechaRegistro;
	public static volatile SingularAttribute<RelAsistencia, Integer> idDiasEventoCap;
	public static volatile SingularAttribute<RelAsistencia, Integer> idTpoAsistencia;
	public static volatile SingularAttribute<RelAsistencia, BigInteger> usuarioModifico;
	public static volatile SingularAttribute<RelAsistencia, RelGrupoParticipante> relGrupoParticipante;
}  
