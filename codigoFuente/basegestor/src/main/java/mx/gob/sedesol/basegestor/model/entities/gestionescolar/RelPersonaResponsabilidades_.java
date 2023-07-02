package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.math.BigInteger;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona;

@Generated(value="Dali", date="2017-02-24T17:13:26.475-0600")
@StaticMetamodel(RelPersonaResponsabilidades.class)
public class RelPersonaResponsabilidades_ {
	public static volatile SingularAttribute<RelPersonaResponsabilidades, Integer> id;
	public static volatile SingularAttribute<RelPersonaResponsabilidades, Date> fechaActualizacion;
	public static volatile SingularAttribute<RelPersonaResponsabilidades, Date> fechaRegistro;
	public static volatile SingularAttribute<RelPersonaResponsabilidades, BigInteger> usuarioModifico;
	public static volatile SingularAttribute<RelPersonaResponsabilidades, TblPersona> tblPersona;
	public static volatile SingularAttribute<RelPersonaResponsabilidades, CatTipoResponsabilidadEc> catTipoResponsabilidadEc;
}
