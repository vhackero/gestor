package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-01-27T09:59:13.052-0600")
@StaticMetamodel(RelProgramaAutore.class)
public class RelProgramaAutore_ {
	public static volatile SingularAttribute<RelProgramaAutore, Integer> idAutor;
	public static volatile SingularAttribute<RelProgramaAutore, String> autores;
	public static volatile SingularAttribute<RelProgramaAutore, Date> fechaActualizacion;
	public static volatile SingularAttribute<RelProgramaAutore, Date> fechaRegistro;
	public static volatile SingularAttribute<RelProgramaAutore, Long> usuarioModifico;
	public static volatile SingularAttribute<RelProgramaAutore, TblFichaDescriptivaPrograma> tblFichaDescriptivaPrograma;
}
