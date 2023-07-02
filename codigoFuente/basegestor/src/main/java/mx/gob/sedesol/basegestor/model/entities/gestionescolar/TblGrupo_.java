package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-16T16:44:27.707-0600")
@StaticMetamodel(TblGrupo.class)
public class TblGrupo_ {
	public static volatile SingularAttribute<TblGrupo, Integer> id;
	public static volatile SingularAttribute<TblGrupo, String> clave;
	public static volatile SingularAttribute<TblGrupo, String> descripcion;
	public static volatile SingularAttribute<TblGrupo, Date> fachaActualizacion;
	public static volatile SingularAttribute<TblGrupo, Date> fechaRegistro;
	public static volatile SingularAttribute<TblGrupo, Integer> idEvento;
	public static volatile SingularAttribute<TblGrupo, String> nombre;
	public static volatile SingularAttribute<TblGrupo, BigInteger> usuarioModifico;
}
