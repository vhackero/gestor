package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-03-06T18:26:06.346-0600")
@StaticMetamodel(TblEventoGeneral.class)
public class TblEventoGeneral_ {
	public static volatile SingularAttribute<TblEventoGeneral, Integer> idEventoGeneral;
	public static volatile SingularAttribute<TblEventoGeneral, String> correoSolicitante;
	public static volatile SingularAttribute<TblEventoGeneral, Byte> eventoPrivado;
	public static volatile SingularAttribute<TblEventoGeneral, Date> fechaRegistro;
	public static volatile SingularAttribute<TblEventoGeneral, String> nombre;
	public static volatile SingularAttribute<TblEventoGeneral, String> solicitante;
	public static volatile SingularAttribute<TblEventoGeneral, BigInteger> usuarioModifico;
	public static volatile SingularAttribute<TblEventoGeneral, CatAreasSede> catAreasSede;
}
