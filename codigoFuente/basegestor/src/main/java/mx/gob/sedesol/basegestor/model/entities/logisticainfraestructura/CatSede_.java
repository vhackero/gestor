package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import mx.gob.sedesol.basegestor.model.entities.admin.TblOrganismoGubernamental;

@Generated(value="Dali", date="2017-02-17T17:25:11.564-0600")
@StaticMetamodel(CatSede.class)
public class CatSede_ {
	public static volatile SingularAttribute<CatSede, Integer> idSede;
	public static volatile SingularAttribute<CatSede, String> activo;
	public static volatile SingularAttribute<CatSede, String> codigoPostal;
	public static volatile SingularAttribute<CatSede, String> colonia;
	public static volatile SingularAttribute<CatSede, String> direccion;
	public static volatile SingularAttribute<CatSede, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatSede, Date> fechaRegistro;
	public static volatile SingularAttribute<CatSede, String> nombre;
	public static volatile SingularAttribute<CatSede, Integer> orden;
	public static volatile SingularAttribute<CatSede, Long> usuarioModifico;
	public static volatile ListAttribute<CatSede, CatAreasSede> catAreasSedes;
	public static volatile SingularAttribute<CatSede, CatUbicacionTerritorial> catUbicacionTerritorial;
	public static volatile SingularAttribute<CatSede, TblOrganismoGubernamental>organismoGubernamental;
}
