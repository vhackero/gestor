package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-19T13:30:53.097-0600")
@StaticMetamodel(TblPersona.class)
public class TblPersona_ {
	public static volatile SingularAttribute<TblPersona, Long> idPersona;
	public static volatile SingularAttribute<TblPersona, Boolean> activo;
	public static volatile SingularAttribute<TblPersona, String> apellidoMaterno;
	public static volatile SingularAttribute<TblPersona, String> apellidoPaterno;
	public static volatile SingularAttribute<TblPersona, String> contrasenia;
	public static volatile SingularAttribute<TblPersona, String> curp;
	public static volatile SingularAttribute<TblPersona, Date> fechaActualizacion;
	public static volatile SingularAttribute<TblPersona, Date> fechaNacimiento;
	public static volatile SingularAttribute<TblPersona, Date> fechaRegistro;
	public static volatile SingularAttribute<TblPersona, String> genero;
	public static volatile SingularAttribute<TblPersona, String> nombre;
	public static volatile SingularAttribute<TblPersona, String> rfc;
	public static volatile SingularAttribute<TblPersona, String> rutaFirma;
	public static volatile SingularAttribute<TblPersona, String> rutaFoto;
	public static volatile SingularAttribute<TblPersona, String> usuario;
	public static volatile SingularAttribute<TblPersona, String> token;
	public static volatile SingularAttribute<TblPersona, Long> usuarioModifico;
	public static volatile SingularAttribute<TblPersona, Integer> tipoUsuario;
	public static volatile SingularAttribute<TblPersona, CatPais> nacionalidad;
	public static volatile ListAttribute<TblPersona, RelPersonaCorreo> personaCorreos;
	public static volatile ListAttribute<TblPersona, RelPersonaRol> relPersonaRoles;
	public static volatile ListAttribute<TblPersona, RelPersonaTelefono> relPersonaTelefonos;
	public static volatile ListAttribute<TblPersona, TblDomiciliosPersona> domiciliosPersona;
	public static volatile ListAttribute<TblPersona, SsoElemento> relPersonaElementos;
	//public static volatile ListAttribute<TblPersona, RelUsuarioDatosLaborales> datosLaborales;
	public static volatile SingularAttribute<TblPersona, String> idEntidadFederativa;
	public static volatile SingularAttribute<TblPersona, String> entidadFederativa;
	public static volatile SingularAttribute<TblPersona, String> idMunicipio;
	public static volatile SingularAttribute<TblPersona, String> municipio;
	public static volatile SingularAttribute<TblPersona, String> idDependencia;
	public static volatile SingularAttribute<TblPersona, String> claveDependencia;
	public static volatile SingularAttribute<TblPersona, String> dependencia;
	public static volatile SingularAttribute<TblPersona, String> idUnidadAdministrativa;
	public static volatile SingularAttribute<TblPersona, String> claveUnidadAdministrativa;
	public static volatile SingularAttribute<TblPersona, String> unidadAdministrativa;
	public static volatile SingularAttribute<TblPersona, String> registradoPor;
}
