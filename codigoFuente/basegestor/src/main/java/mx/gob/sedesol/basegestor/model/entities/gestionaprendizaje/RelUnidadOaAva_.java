package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@Generated(value="Dali", date="2017-03-21T12:10:49.868-0600")
@StaticMetamodel(RelUnidadOaAva.class)
public class RelUnidadOaAva_ {
	public static volatile SingularAttribute<RelUnidadOaAva, Integer> id;
	public static volatile SingularAttribute<RelUnidadOaAva, Date> fechaActualizacion;
	public static volatile SingularAttribute<RelUnidadOaAva, Date> fechaRegistro;
	public static volatile SingularAttribute<RelUnidadOaAva, Byte> funcionalidad;
	public static volatile SingularAttribute<RelUnidadOaAva, Integer> idFoa;
	public static volatile SingularAttribute<RelUnidadOaAva, Integer> idUnidadDidactica;
	public static volatile SingularAttribute<RelUnidadOaAva, Byte> porcentajeAvanceOa;
	public static volatile SingularAttribute<RelUnidadOaAva, BigInteger> usuarioModifico;
	public static volatile SingularAttribute<RelUnidadOaAva, Byte> validacionDesarrolloOa;
	public static volatile SingularAttribute<RelUnidadOaAva, Byte> validacionGuionInst;
	public static volatile SingularAttribute<RelUnidadOaAva, Byte> validacionScorm;
	public static volatile SingularAttribute<RelUnidadOaAva, TblAmbienteVirtualAprendizaje> tblAmbienteVirtualAprendizaje;
}
