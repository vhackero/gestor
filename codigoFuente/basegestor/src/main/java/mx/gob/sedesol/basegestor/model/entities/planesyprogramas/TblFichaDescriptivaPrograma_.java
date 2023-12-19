package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;


import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;


@Generated(value="Dali", date="2016-12-19T13:30:53.265-0600")
@StaticMetamodel(TblFichaDescriptivaPrograma.class)
public class TblFichaDescriptivaPrograma_ {
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, Integer> idPrograma;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, Integer> idPlan;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, String> conocimietosPrevios;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, String> cvePrograma;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, String> descripcion;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, Date> fechaArranque;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, Date> fechaProduccion;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, Date> fechaSolicitud;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, Integer> idAlcancePrograma;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, Integer> idModalidadPrograma;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, Integer> idNivelDominioPrograma;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, TblFichaDescriptivaPrograma> idProgramaAntecedente;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, String> identificadorFinal;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, String> instrumentoAprendizaje;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, String> justificacionAcademica;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, String> metodologia;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, String> nombreTentativo;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, String> objetivosGenerales;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, String> presentacion;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, String> propositos;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, String> requerimientoEquipo;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, String> requisitosIngreso;
	//public static volatile ListAttribute<TblFichaDescriptivaPrograma, RelProgramaActividadesAprendizaje> relProgramaActividadesAprendizajes;
	//public static volatile ListAttribute<TblFichaDescriptivaPrograma, RelProgramaAreasConocimiento> relProgramaAreasConocimientos;
	//public static volatile ListAttribute<TblFichaDescriptivaPrograma, RelProgramaCreadorPrograma> relProgramaCreadorProgramas;
	public static volatile ListAttribute<TblFichaDescriptivaPrograma, RelProgramaAutore> relProgramaAutores;
	public static volatile ListAttribute<TblFichaDescriptivaPrograma, RelProgramaCargaHoraria> relProgramaDuracion;
	//public static volatile ListAttribute<TblFichaDescriptivaPrograma, RelProgramaEntidadesAcademica> relProgramaEntidadesAcademicas;
	//public static volatile ListAttribute<TblFichaDescriptivaPrograma, RelProgramaPilotaje> relProgramaPilotajes;
	//public static volatile ListAttribute<TblFichaDescriptivaPrograma, RelProgramaResolucionImagen> relProgramaResolucionImagen;
	public static volatile ListAttribute<TblFichaDescriptivaPrograma, RelProgramaResponsable> relProgramaResponsables;
	//public static volatile ListAttribute<TblFichaDescriptivaPrograma, RelProgramaTecnicasDidactica> relProgramaTecnicasDidacticas;
	public static volatile ListAttribute<TblFichaDescriptivaPrograma, TblEvento> tblEventos;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, TblMallaCurricular> tblMallaCurricular;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, CatAmbientesAprendizajeEc> catAmbientesAprendizajeEc;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, CatStatusPrograma> catStatusPrograma;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, CatNivelEnsenanzaPrograma> catNivelEnsenanzaPrograma;
	//public static volatile SingularAttribute<TblFichaDescriptivaPrograma, CatPuestosSedesolEc> catPuestosSedesolEc;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, CatTipoEventoEc> catTipoEventoEc;
	//public static volatile SingularAttribute<TblFichaDescriptivaPrograma, TblEstructuraTematica> tblEstructuraTematica;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, Date> fechaActualizacion;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, Date> fechaRegistro;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, Long> usuarioModifico;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, Integer> tipoCompetencia;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, Integer> ejeCapacitacion;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, CatModalidadPlanPrograma> catModalidad;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, Date> fechaVigInical;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, Date> fechaVigFinal;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, CatNivelConocimiento>catNivelConocimiento;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, Boolean>encuestaKp;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, CatInstitucionesCertificadora> catInstitucionesCertificadora;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, String> calificacionMinAprobatoria;
	
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, String> tipo;
	public static volatile SingularAttribute<TblFichaDescriptivaPrograma, Integer> creditos;
}
