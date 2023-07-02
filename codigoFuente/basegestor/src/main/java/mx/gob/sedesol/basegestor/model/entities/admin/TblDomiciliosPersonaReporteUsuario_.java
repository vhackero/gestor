package mx.gob.sedesol.basegestor.model.entities.admin;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-07-13T12:40:10.436-0500")
@StaticMetamodel(TblDomiciliosPersonaReporteUsuario.class)
public class TblDomiciliosPersonaReporteUsuario_ {
	public static volatile SingularAttribute<TblDomiciliosPersonaReporteUsuario, Long> idDomicilioPersona;
	public static volatile SingularAttribute<TblDomiciliosPersonaReporteUsuario, TblPersonaReporteUsuario> persona;
	public static volatile SingularAttribute<TblDomiciliosPersonaReporteUsuario, CatAsentamientoReporteUsuario> asentamiento;
}
