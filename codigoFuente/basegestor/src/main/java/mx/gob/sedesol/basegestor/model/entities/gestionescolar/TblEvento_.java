package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatModalidadPlanPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;

@Generated(value="Dali", date="2017-02-09T13:35:23.973-0600")
@StaticMetamodel(TblEvento.class)
public class TblEvento_ {
	public static volatile SingularAttribute<TblEvento, Integer> idEvento;
	public static volatile SingularAttribute<TblEvento, Integer> idPrograma;	
	public static volatile SingularAttribute<TblEvento, String> alcanceEc;
	
	public static volatile SingularAttribute<TblEvento, String> cveEventoCap;
	public static volatile SingularAttribute<TblEvento, Date> fechaActualizacion;
	public static volatile SingularAttribute<TblEvento, Date> fechaRegistro;
	public static volatile SingularAttribute<TblEvento, Date> fechaFinal;
	public static volatile SingularAttribute<TblEvento, Date> fechaInicial;			
	public static volatile SingularAttribute<TblEvento, Integer> modalidad;
	public static volatile SingularAttribute<TblEvento, String> nivelEnsenanza;
	public static volatile SingularAttribute<TblEvento, String> noRegistroEc;
	public static volatile SingularAttribute<TblEvento, String> nombreEc;
	public static volatile SingularAttribute<TblEvento, String> objetivoGeneralEc;
	public static volatile SingularAttribute<TblEvento, String> perfilEc;
	public static volatile SingularAttribute<TblEvento, String> requisitosEc;
	public static volatile SingularAttribute<TblEvento, Long> usuarioModifico;
	public static volatile SingularAttribute<TblEvento, CatEstadoEventoCapacitacion> catEstadoEventoCapacitacion;
	public static volatile SingularAttribute<TblEvento, TblFichaDescriptivaPrograma> fichaDescriptivaPrograma;
	public static volatile SingularAttribute<TblEvento, CatModalidadPlanPrograma> catModalidadPlanPrograma;
	
	public static volatile SingularAttribute<TblEvento, Boolean> constancia;

	public static volatile SingularAttribute<TblEvento, String> calificacionMinAprobatoria;
	public static volatile SingularAttribute<TblEvento, Integer> idCategoria;
	public static volatile SingularAttribute<TblEvento, Integer> idDestinatario;
	public static volatile SingularAttribute<TblEvento, Integer> idDirigido;
	public static volatile SingularAttribute<TblEvento, Integer> idEntidadFederativa;
	public static volatile SingularAttribute<TblEvento, String> idMunicipio;
	public static volatile SingularAttribute<TblEvento, Integer> idProgramaSocial;
	public static volatile SingularAttribute<TblEvento, Integer> oportunidadesEvaluacion;
	/*Se agregó porque la entidad no tenia este atributo*/
	public static volatile SingularAttribute<TblEvento, Boolean> privado;
	
}
