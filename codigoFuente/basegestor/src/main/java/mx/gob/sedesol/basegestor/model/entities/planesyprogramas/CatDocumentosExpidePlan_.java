package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-08-16T12:18:14.666-0500")
@StaticMetamodel(CatDocumentosExpidePlan.class)
public class CatDocumentosExpidePlan_ {
	public static volatile SingularAttribute<CatDocumentosExpidePlan, Long> id;
	public static volatile SingularAttribute<CatDocumentosExpidePlan, Integer> activo;
	public static volatile SingularAttribute<CatDocumentosExpidePlan, String> descripcion;
	public static volatile SingularAttribute<CatDocumentosExpidePlan, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatDocumentosExpidePlan, Date> fechaRegistro;
	public static volatile SingularAttribute<CatDocumentosExpidePlan, String> nombre;
	public static volatile SingularAttribute<CatDocumentosExpidePlan, Integer> orden;
	public static volatile SingularAttribute<CatDocumentosExpidePlan, Long> usuarioModifico;
}
