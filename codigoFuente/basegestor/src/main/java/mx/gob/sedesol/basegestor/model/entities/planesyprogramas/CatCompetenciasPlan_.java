package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-27T10:09:26.686-0600")
@StaticMetamodel(CatCompetenciasPlan.class)
public class CatCompetenciasPlan_ {
	public static volatile SingularAttribute<CatCompetenciasPlan, Integer> id;
	public static volatile SingularAttribute<CatCompetenciasPlan, Integer> activo;
	public static volatile SingularAttribute<CatCompetenciasPlan, String> descripcion;
	public static volatile SingularAttribute<CatCompetenciasPlan, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatCompetenciasPlan, Date> fechaRegistro;
	public static volatile SingularAttribute<CatCompetenciasPlan, String> nombre;
	public static volatile SingularAttribute<CatCompetenciasPlan, Integer> orden;
	public static volatile SingularAttribute<CatCompetenciasPlan, Long> usuarioModifico;
	public static volatile ListAttribute<CatCompetenciasPlan, TblPlan> tblPlans;
}
