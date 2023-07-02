package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-17T18:09:57.108-0600")
@StaticMetamodel(TblConfiguracionArea.class)
public class TblConfiguracionArea_ {
	public static volatile SingularAttribute<TblConfiguracionArea, Integer> idConfigArea;
	public static volatile SingularAttribute<TblConfiguracionArea, String> emailCc;
	public static volatile SingularAttribute<TblConfiguracionArea, String> emailResponsable;
	public static volatile SingularAttribute<TblConfiguracionArea, String> emailSolicitante;
	public static volatile SingularAttribute<TblConfiguracionArea, Date> fechaActualizacion;
	public static volatile SingularAttribute<TblConfiguracionArea, Date> fechaRegistro;
	public static volatile SingularAttribute<TblConfiguracionArea, Integer> idEstatus;
	public static volatile SingularAttribute<TblConfiguracionArea, String> rutaImgDos;
	public static volatile SingularAttribute<TblConfiguracionArea, String> rutaImgTres;
	public static volatile SingularAttribute<TblConfiguracionArea, String> rutaImgUno;
	public static volatile SingularAttribute<TblConfiguracionArea, String> solicitante;
	public static volatile SingularAttribute<TblConfiguracionArea, Long> usuarioModifico;
	public static volatile ListAttribute<TblConfiguracionArea, RelAreaDistribucion> relAreaDistribucion;
	public static volatile ListAttribute<TblConfiguracionArea, RelAreaRecurso> relAreaRecursos;
	public static volatile SingularAttribute<TblConfiguracionArea, CatAreasSede> catAreasSede;
}
