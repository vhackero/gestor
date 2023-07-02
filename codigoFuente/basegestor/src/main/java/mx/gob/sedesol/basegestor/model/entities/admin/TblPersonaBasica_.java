package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-19T13:30:53.097-0600")
@StaticMetamodel(TblPersonaBasica.class)
public class TblPersonaBasica_ {
	public static volatile SingularAttribute<TblPersonaBasica, Long> idPersona;
	public static volatile SingularAttribute<TblPersonaBasica, Boolean> activo;
	public static volatile SingularAttribute<TblPersonaBasica, String> apellidoMaterno;
	public static volatile SingularAttribute<TblPersonaBasica, String> apellidoPaterno;
	public static volatile SingularAttribute<TblPersonaBasica, String> curp;
	public static volatile SingularAttribute<TblPersonaBasica, Date> fechaNacimiento;
	public static volatile SingularAttribute<TblPersonaBasica, String> genero;
	public static volatile SingularAttribute<TblPersonaBasica, String> nombre;
	public static volatile SingularAttribute<TblPersonaBasica, String> usuario;
	public static volatile SingularAttribute<TblPersonaBasica, Integer> tipoUsuario;
}
