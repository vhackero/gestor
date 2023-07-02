package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;

@Generated(value="Dali", date="2016-12-27T10:25:01.775-0600")
@StaticMetamodel(TblOrganismoGubernamental.class)
public class TblOrganismoGubernamental_ {
	public static volatile SingularAttribute<TblOrganismoGubernamental, Integer> id;
	public static volatile SingularAttribute<TblOrganismoGubernamental, Integer> activo;
	public static volatile SingularAttribute<TblOrganismoGubernamental, String> descripcion;
	public static volatile SingularAttribute<TblOrganismoGubernamental, Date> fechaActualizacion;
	public static volatile SingularAttribute<TblOrganismoGubernamental, Date> fechaRegistro;
	public static volatile SingularAttribute<TblOrganismoGubernamental, Integer> idPadre;
	public static volatile SingularAttribute<TblOrganismoGubernamental, Integer> idTipoOrganismo;
	public static volatile SingularAttribute<TblOrganismoGubernamental, String> nombre;
	public static volatile SingularAttribute<TblOrganismoGubernamental, Integer> orden;
	public static volatile SingularAttribute<TblOrganismoGubernamental, Long> usuarioModifico;
	public static volatile ListAttribute<TblOrganismoGubernamental, TblPlan> tblPlanes;
}
