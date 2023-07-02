package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-17T17:20:46.957-0600")
@StaticMetamodel(CatAreasSede.class)
public class CatAreasSede_ {
	public static volatile SingularAttribute<CatAreasSede, Integer> idAreaSede;
	public static volatile SingularAttribute<CatAreasSede, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatAreasSede, Date> fechaRegistro;
	public static volatile SingularAttribute<CatAreasSede, String> nombre;
	public static volatile SingularAttribute<CatAreasSede, Integer> orden;
	public static volatile SingularAttribute<CatAreasSede, Integer> piso;
	public static volatile SingularAttribute<CatAreasSede, Long> usuarioModifico;
	public static volatile SingularAttribute<CatAreasSede, CatSede> catSede;
	public static volatile SingularAttribute<CatAreasSede, CatEstatusArea> catEstatusArea;
	public static volatile ListAttribute<CatAreasSede, TblConfiguracionArea> tblConfiguracionAreas;
	public static volatile SingularAttribute<CatAreasSede, CatAreaInfraestructura> catArea;
}
