package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-19T13:30:53.234-0600")
@StaticMetamodel(RelProgramaResponsable.class)
public class RelProgramaResponsable_ {
	public static volatile SingularAttribute<RelProgramaResponsable, Integer> idResponsable;
	public static volatile SingularAttribute<RelProgramaResponsable, Date> fechaActualizacion;
	public static volatile SingularAttribute<RelProgramaResponsable, Date> fechaRegistro;
	public static volatile SingularAttribute<RelProgramaResponsable, String> responsables;
	public static volatile SingularAttribute<RelProgramaResponsable, Long> usuarioModifico;
	public static volatile SingularAttribute<RelProgramaResponsable, TblFichaDescriptivaPrograma> tblFichaDescriptivaPrograma;
}
