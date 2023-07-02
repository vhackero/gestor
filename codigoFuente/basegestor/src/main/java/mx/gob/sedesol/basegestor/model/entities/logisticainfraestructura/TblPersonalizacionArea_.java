package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.math.BigInteger;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@Generated(value="Dali", date="2017-02-20T12:37:40.153-0600")
@StaticMetamodel(TblPersonalizacionArea.class)
public class TblPersonalizacionArea_ {
	public static volatile SingularAttribute<TblPersonalizacionArea, Integer> idPersonalizacion;
	public static volatile SingularAttribute<TblPersonalizacionArea, String> emailSolicitante;
	public static volatile SingularAttribute<TblPersonalizacionArea, Integer> eventoPrivado;
	public static volatile SingularAttribute<TblPersonalizacionArea, Date> fechaActualizacion;
	public static volatile SingularAttribute<TblPersonalizacionArea, Date> fechaRegistro;
	public static volatile SingularAttribute<TblPersonalizacionArea, Integer> idArea;
	public static volatile SingularAttribute<TblPersonalizacionArea, Integer> idDistribucion;
	public static volatile SingularAttribute<TblPersonalizacionArea, String> observacionesAcademicas;
	public static volatile SingularAttribute<TblPersonalizacionArea, Byte> servicioCafeteria;
	public static volatile SingularAttribute<TblPersonalizacionArea, String> solicitante;
	public static volatile SingularAttribute<TblPersonalizacionArea, BigInteger> usuarioModifico;
}
