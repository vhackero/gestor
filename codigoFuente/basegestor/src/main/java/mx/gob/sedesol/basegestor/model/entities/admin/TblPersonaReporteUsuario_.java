package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-19T13:30:53.097-0600")
@StaticMetamodel(TblPersonaReporteUsuario.class)
public class TblPersonaReporteUsuario_ {
	public static volatile SingularAttribute<TblPersonaReporteUsuario, Long> idPersona;
	public static volatile SingularAttribute<TblPersonaReporteUsuario, Boolean> activo;
	public static volatile SingularAttribute<TblPersonaReporteUsuario, String> apellidoMaterno;
	public static volatile SingularAttribute<TblPersonaReporteUsuario, String> apellidoPaterno;
	public static volatile SingularAttribute<TblPersonaReporteUsuario, String> curp;
	public static volatile SingularAttribute<TblPersonaReporteUsuario, Date> fechaNacimiento;
	public static volatile SingularAttribute<TblPersonaReporteUsuario, Date> fechaRegistro;
	public static volatile SingularAttribute<TblPersonaReporteUsuario, String> genero;
	public static volatile SingularAttribute<TblPersonaReporteUsuario, String> nombre;
	public static volatile SingularAttribute<TblPersonaReporteUsuario, Integer> tipoUsuario;
	public static volatile ListAttribute<TblPersonaReporteUsuario, RelPersonaRolReporteUsuario> relPersonaRoles;
	public static volatile ListAttribute<TblPersonaReporteUsuario, RelUsuarioDatosLaboralesReporteUsuario> datosLaborales;
	public static volatile SingularAttribute<TblPersonaReporteUsuario, String> idEntidadFederativa;
	public static volatile SingularAttribute<TblPersonaReporteUsuario, String> entidadFederativa;
	public static volatile SingularAttribute<TblPersonaReporteUsuario, String> idMunicipio;
	public static volatile SingularAttribute<TblPersonaReporteUsuario, String> municipio;
	public static volatile SingularAttribute<TblPersonaReporteUsuario, String> idDependencia;
	public static volatile SingularAttribute<TblPersonaReporteUsuario, String> claveDependencia;
	public static volatile SingularAttribute<TblPersonaReporteUsuario, String> dependencia;
	public static volatile SingularAttribute<TblPersonaReporteUsuario, String> idUnidadAdministrativa;
	public static volatile SingularAttribute<TblPersonaReporteUsuario, String> claveUnidadAdministrativa;
	public static volatile SingularAttribute<TblPersonaReporteUsuario, String> unidadAdministrativa;
}
