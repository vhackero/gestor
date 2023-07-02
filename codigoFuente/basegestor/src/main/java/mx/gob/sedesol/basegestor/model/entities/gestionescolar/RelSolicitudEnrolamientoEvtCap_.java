package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona;

@Generated(value="Dali", date="2017-06-02T18:17:43.446-0500")
@StaticMetamodel(RelSolicitudEnrolamientoEvtCap.class)
public class RelSolicitudEnrolamientoEvtCap_ {
	public static volatile SingularAttribute<RelSolicitudEnrolamientoEvtCap, RelSolicitudEnrolamientoEvtCapPK> id;
	public static volatile SingularAttribute<RelSolicitudEnrolamientoEvtCap, Byte> activo;
	public static volatile SingularAttribute<RelSolicitudEnrolamientoEvtCap, Date> fechaActualizacion;
	public static volatile SingularAttribute<RelSolicitudEnrolamientoEvtCap, Date> fechaRegistro;
	public static volatile SingularAttribute<RelSolicitudEnrolamientoEvtCap, BigInteger> usuarioModifico;
	public static volatile SingularAttribute<RelSolicitudEnrolamientoEvtCap, TblEvento> tblEvento;
	public static volatile SingularAttribute<RelSolicitudEnrolamientoEvtCap, TblPersona> tblPersona;
}
