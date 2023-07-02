package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-17T18:03:04.075-0600")
@StaticMetamodel(CatUbicacionTerritorial.class)
public class CatUbicacionTerritorial_ {
	public static volatile SingularAttribute<CatUbicacionTerritorial, Integer> id;
	public static volatile SingularAttribute<CatUbicacionTerritorial, Integer> activo;
	public static volatile SingularAttribute<CatUbicacionTerritorial, String> decripcion;
	public static volatile SingularAttribute<CatUbicacionTerritorial, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatUbicacionTerritorial, Date> fechaRegistro;
	public static volatile SingularAttribute<CatUbicacionTerritorial, String> nombre;
	public static volatile SingularAttribute<CatUbicacionTerritorial, Integer> orden;
	public static volatile SingularAttribute<CatUbicacionTerritorial, Long> usuarioModifico;
	public static volatile ListAttribute<CatUbicacionTerritorial, CatSede> catSedes;
}
