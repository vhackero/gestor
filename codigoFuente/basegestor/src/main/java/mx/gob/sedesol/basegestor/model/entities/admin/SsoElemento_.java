package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "Dali", date = "2016-12-19T13:30:53.097-0600")
@StaticMetamodel(SsoElemento.class)
public class SsoElemento_ {
	public static volatile SingularAttribute<SsoElemento, Integer> id;
	public static volatile SingularAttribute<SsoElemento, String> idPadreElemento;
	public static volatile SingularAttribute<SsoElemento, String> idElemento;
	public static volatile SingularAttribute<SsoElemento, String> nombreElemento;
	public static volatile SingularAttribute<SsoElemento, String> tipoInformacion;
	public static volatile SingularAttribute<SsoElemento, String> iDFuente;
	public static volatile SingularAttribute<SsoElemento, String> fuente;
	public static volatile SingularAttribute<SsoElemento, String> tipoFuente;
	public static volatile SingularAttribute<SsoElemento, Date> fechaRegistro;
	public static volatile SingularAttribute<SsoElemento, Date> fechaActualizacion;
	public static volatile SingularAttribute<SsoElemento, Long> usuarioModifico;
	public static volatile SingularAttribute<SsoElemento, TblPersona> persona;
}
