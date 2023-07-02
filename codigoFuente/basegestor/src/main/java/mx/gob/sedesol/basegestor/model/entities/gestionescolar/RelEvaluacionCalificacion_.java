package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-26T18:49:30.854-0500")
@StaticMetamodel(RelEvaluacionCalificacion.class)
public class RelEvaluacionCalificacion_ {
	public static volatile SingularAttribute<RelEvaluacionCalificacion, Integer> idEvalCalificacion;
	public static volatile SingularAttribute<RelEvaluacionCalificacion, Double> calificacion;
	public static volatile SingularAttribute<RelEvaluacionCalificacion, Date> fechaRegistro;
	public static volatile SingularAttribute<RelEvaluacionCalificacion, Long> usuarioModifico;
	public static volatile SingularAttribute<RelEvaluacionCalificacion, RelGrupoEvaluacion> relGrupoEvaluacion;
	public static volatile SingularAttribute<RelEvaluacionCalificacion, RelGrupoParticipante> relGrupoParticipante;
}
