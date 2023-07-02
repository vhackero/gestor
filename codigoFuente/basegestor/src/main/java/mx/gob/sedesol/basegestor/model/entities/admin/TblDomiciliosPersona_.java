package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-07-13T12:40:10.436-0500")
@StaticMetamodel(TblDomiciliosPersona.class)
public class TblDomiciliosPersona_ {
	public static volatile SingularAttribute<TblDomiciliosPersona, Long> idDomicilioPersona;
	public static volatile SingularAttribute<TblDomiciliosPersona, String> calle;
	public static volatile SingularAttribute<TblDomiciliosPersona, Date> fechaActualizacion;
	public static volatile SingularAttribute<TblDomiciliosPersona, Date> fechaRegistro;
	public static volatile SingularAttribute<TblDomiciliosPersona, String> numero;
	public static volatile SingularAttribute<TblDomiciliosPersona, Long> usuarioModifico;
	public static volatile SingularAttribute<TblDomiciliosPersona, TblPersona> persona;
	public static volatile SingularAttribute<TblDomiciliosPersona, CatAsentamiento> asentamiento;
}
