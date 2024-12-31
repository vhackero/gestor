package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.math.BigInteger;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaNivelEducativo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaParamConsulta;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaTableroResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionParamNueva;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionesConsultaResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionesTableroResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoProceso;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;

@Repository
public class InscripcionesRepository implements IinscripcionesRepository {

	@Autowired
	public EntityManager entityManager;

	private static final Logger logger = Logger.getLogger(InscripcionesRepository.class);

	@Override
	public List<TipoProceso> consultarTipoProceso() {

		List<TipoProceso> lista = new ArrayList<TipoProceso>();

		String consulta = "SELECT cp.id_proceso, cp.nombre FROM cat_procesos_inscripcion cp WHERE cp.activo = 1";

		Query query = entityManager.createNativeQuery(consulta);

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				TipoProceso convocatoria = mapeo(obj);
				lista.add(convocatoria);

			}
		}

		return lista;

	}

	@Override
	public List<TblPlan> consultarPlanConvocatoria(InscripcionParamNueva inscripcionParamNueva) {

		return null;
	}

	@Override
	public List<TblFichaDescriptivaPrograma> consultarPrograma(InscripcionParamNueva inscripcionParamNueva) {

		List<TblFichaDescriptivaPrograma> lista = new ArrayList<TblFichaDescriptivaPrograma>();

		String consulta = "SELECT tp.id_plan, tp.nombre plan, tfd.id_programa, tfd.nombre_tentativo programa\r\n"
				+ "FROM tbl_convocatoria tc\r\n"
				+ "         INNER JOIN rel_convocatoria_planesyprogramas rcpp ON rcpp.id_convocatoria = tc.convocatoria_id\r\n"
				+ "         INNER JOIN tbl_ficha_descriptiva_programa tfd ON tfd.id_plan = rcpp.id_plan ANd tfd.id_programa = rcpp.id_programa\r\n"
				+ "         INNER JOIN tbl_planes tp ON tp.id_plan = tfd.id_plan\r\n"
				+ "         INNER JOIN tbl_malla_curricular tmc ON tmc.id_plan = tp.id_plan AND tmc.activo = 1\r\n"
				+ "WHERE tc.convocatoria_id = :id_convocatoria_seleccionada AND tp.id_plan = :id_plan_seleccionado";

		if (inscripcionParamNueva.getConvocatoriaSeleccionada() != null
				&& inscripcionParamNueva.getListaPlanesValor() != null) {
			
			
			String valorSeleccionado = inscripcionParamNueva.getConvocatoriaSeleccionada();
			
			for (String plan : inscripcionParamNueva.getListaPlanesValor()) {

				System.out.print("VALOR" +plan);
				Integer idConvocatoria = Integer.parseInt(valorSeleccionado);
				
				String valorSeleccionado2 = plan;
				Integer idPlan = Integer.parseInt(valorSeleccionado2);

				Query query = entityManager.createNativeQuery(consulta);
				query.setParameter("id_convocatoria_seleccionada", idConvocatoria);
				query.setParameter("id_plan_seleccionado", idPlan);
				
				List<Object[]> listaQuery = query.getResultList();

				if (!listaQuery.isEmpty()) {
					for (Object[] obj : listaQuery) {

						TblFichaDescriptivaPrograma tblPlan = mapeoTblPrograma(obj);
						lista.add(tblPlan);

					}
				}

			}

		} else {
			lista = new ArrayList<TblFichaDescriptivaPrograma>();
		}

		return lista;
	}

	@Override
	public List<TblPlan> consultarPlan(InscripcionParamNueva inscripcionParamNueva) {

		List<TblPlan> lista = new ArrayList<TblPlan>();

		if (inscripcionParamNueva.getConvocatoriaSeleccionada() != null) {
			String consulta = "SELECT DISTINCT (rcpp.id_plan), tp.nombre plan\r\n" + "FROM tbl_convocatoria tc\r\n"
					+ "         INNER JOIN rel_convocatoria_planesyprogramas rcpp ON rcpp.id_convocatoria = tc.convocatoria_id\r\n"
					+ "         INNER JOIN tbl_planes tp ON tp.id_plan = rcpp.id_plan\r\n"
					+ "         INNER JOIN tbl_malla_curricular tmc ON tmc.id_plan = tp.id_plan AND tmc.activo = 1\r\n"
					+ "WHERE tc.convocatoria_id = :idConvocatoria";

			String valorSeleccionado = inscripcionParamNueva.getConvocatoriaSeleccionada();
			Integer idConvocatoria = Integer.parseInt(valorSeleccionado);
			Query query = entityManager.createNativeQuery(consulta);
			query.setParameter("idConvocatoria", idConvocatoria);

			List<Object[]> listaQuery = query.getResultList();

			if (!listaQuery.isEmpty()) {
				for (Object[] obj : listaQuery) {

					TblPlan tblPlan = mapeoTblPlan(obj);
					lista.add(tblPlan);

				}
			}
		} else {

			String consultaP = "select tp.id_plan, tp.nombre from tbl_planes tp where tp.id_estatus_plan = 1";

			Query query = entityManager.createNativeQuery(consultaP);

			List<Object[]> listaQuery = query.getResultList();

			if (!listaQuery.isEmpty()) {
				for (Object[] obj : listaQuery) {

					TblPlan tblPlan = mapeoTblPlan(obj);
					lista.add(tblPlan);

				}
			}

		}
		return lista;

	}

	@Override
	public List<TipoProceso> consultarNombre(ConvocatoriaParamConsulta tableroParamConsulta) {

		List<TipoProceso> lista = new ArrayList<TipoProceso>();

		String consulta = "SELECT t.*\r\n" + "FROM tbl_procesos_inscripcion t\r\n"
				+ "WHERE convocatoria_id = :id_convocatoria_selecionada AND id_tipo_proceso = :id_del_tipo_proceso AND id_categoria_proceso = 1";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_convocatoria_selecionada", tableroParamConsulta.getValueConvocatoriaEstatus());
		query.setParameter("id_del_tipo_proceso", tableroParamConsulta.getConsulNivelEducativo());

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				TipoProceso convocatoria = mapeo(obj);
				lista.add(convocatoria);

			}
		}

		return lista;

	}

	@Override
	public List<InscripcionesTableroResumen> consultarTableroResumen(ConvocatoriaParamConsulta tableroParamConsulta) {

		List<InscripcionesTableroResumen> lista = new ArrayList<InscripcionesTableroResumen>();

		String consulta = "SELECT tp.nombre plan,fd.id_programa, fd.nombre_tentativo programa, fd.identificador_final clave, (SELECT tmc2.nombre FROM tbl_malla_curricular tmc2 WHERE tmc2.id = tmc.id_padre) semestre, tmc.nombre bloque,  count(ti.id) no_estudiantes_inscritos FROM tbl_inscripciones ti\r\n"
				+ "                                                              INNER JOIN rel_proceso_inscipcion_planesyprogramas rpi ON rpi.id_programa = ti.idprograma AND rpi.id_plan = ti.idplan\r\n"
				+ "                                                              INNER JOIN tbl_procesos_inscripcion tpi ON tpi.proceso_inscripcion_id =  rpi.id_proceso_inscripcion\r\n"
				+ "                                                              INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = ti.idprograma and ti.idplan = fd.id_plan\r\n"
				+ "                                                              INNER JOIN tbl_planes tp ON tp.id_plan = ti.idplan\r\n"
				+ "                                                              INNER JOIN tbl_malla_curricular tmc ON tmc.id = fd.id_eje_capacitacion\r\n"
				+ "WHERE tpi.convocatoria_id = :id_convocatoria_selecionada AND tpi.id_tipo_proceso = :id_del_tipo_proceso  AND (ti.fecha_registro >= tpi.fecha_inicio AND ti.fecha_registro <= tpi.fecha_fin) AND (tpi.proceso_inscripcion_id = :id_del_nombre_selecionado AND tpi.id_categoria_proceso = 1 )\r\n"
				+ "group by rpi.id_programa";

		Query query = entityManager.createNativeQuery(consulta);

		query.setParameter("id_convocatoria_selecionada", tableroParamConsulta.getValueConvocatoriaEstatus());
		query.setParameter("id_del_tipo_proceso", tableroParamConsulta.getConsulNivelEducativo());
		query.setParameter("id_del_nombre_selecionado", tableroParamConsulta.getConsulNombreCorto());

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				InscripcionesTableroResumen tblPlan = tableroInscrip(obj);
				lista.add(tblPlan);

			}
		}

		return lista;

	}

	@Override
	public List<InscripcionesConsultaResumen> consultarFiltros(ConvocatoriaParamConsulta tableroParamConsulta) {

		List<InscripcionesConsultaResumen> lista = new ArrayList<InscripcionesConsultaResumen>();

		String consulta = "select\r\n" + "	tp.proceso_inscripcion_id,\r\n" + "	tp.convocatoria_id,\r\n"
				+ "	tc.nombre convocatoria,\r\n" + "	tp.nombre nombre,\r\n" + "	tp.fecha_inicio,\r\n"
				+ "	tp.fecha_fin,\r\n" + "	cp.nombre tipo_proceso,\r\n" + "	if(tp.estatus = 0,\r\n"
				+ "	'inactivo',\r\n" + "	'activo') estatus\r\n" + "from\r\n" + "	tbl_procesos_inscripcion tp\r\n"
				+ "inner join tbl_convocatoria tc on\r\n" + "	tc.convocatoria_id = tp.convocatoria_id\r\n"
				+ "inner join cat_procesos_inscripcion cp on\r\n" + "	cp.id_proceso = tp.id_tipo_proceso\r\n"
				+ "where\r\n" + "	tp.id_categoria_proceso = 1";

		Query query = entityManager.createNativeQuery(consulta);

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				InscripcionesConsultaResumen tblPlan = mapFiltrosInscrip(obj);
				lista.add(tblPlan);

			}
		}

		return lista;

	}

	private InscripcionesConsultaResumen mapFiltrosInscrip(Object[] obj) {

		InscripcionesConsultaResumen regresa = new InscripcionesConsultaResumen();

		regresa.setProcesoInscripcionId(obj[0].toString());
		regresa.setIdConvocatoria(obj[1].toString());
		regresa.setNombreConvocatoria(obj[2].toString());
		regresa.setNombre(obj[3].toString());
		regresa.setFecIni(obj[4].toString());
		regresa.setFecFin(obj[5].toString());
		regresa.setTipoProceso(obj[6].toString());
		regresa.setEstatus(obj[7].toString());

		return regresa;
	}

	private InscripcionesTableroResumen tableroInscrip(Object[] obj) {

		InscripcionesTableroResumen regresa = new InscripcionesTableroResumen();

		regresa.setPlan(obj[0].toString());
		regresa.setIdPrograma(obj[1].toString());
		regresa.setPrograma(obj[2].toString());
		regresa.setClave(obj[3].toString());
		regresa.setSemestre(obj[4].toString());
		regresa.setBloque(obj[5].toString());
		regresa.setNoEstudiantesInscritos(obj[6].toString());

		return regresa;
	}

	private TblPlan mapeoTblPlan(Object[] obj) {

		TblPlan regresa = new TblPlan();

		regresa.setIdPlan((Integer) obj[0]);
		regresa.setNombre(obj[1].toString());

		return regresa;
	}

	private TblFichaDescriptivaPrograma mapeoTblPrograma(Object[] obj) {

		TblFichaDescriptivaPrograma regresa = new TblFichaDescriptivaPrograma();

		regresa.setIdPrograma((Integer) obj[2]);
		regresa.setNombreTentativo(obj[3].toString());

		return regresa;
	}

	private TipoProceso mapeo(Object[] obj) {

		TipoProceso regresa = new TipoProceso();

		regresa.setIdProceso((Integer) obj[0]);
		regresa.setNombre(obj[1].toString());

		return regresa;
	}
//	
//	@Override
//	public List<ConvocatoriaTableroResumen> consultarTableroResumen(Integer convocatoriaId) {
//
//		List<ConvocatoriaTableroResumen> lista = new ArrayList<ConvocatoriaTableroResumen>();
//
//		String consulta = "SELECT tc.nombre nombre_convocatoria, tp.nombre nombre_plan,  COUNT(tpa.id_persona_aspirante) total FROM tbl_persona_aspirante tpa\r\n"
//				+ "INNER JOIN tbl_planes tp ON tp.id_plan = tpa.id_plan\r\n"
//				+ "INNER JOIN tbl_convocatoria tc ON tc.convocatoria_id = tpa.id_convocatoria\r\n"
//				+ "WHERE tpa.id_convocatoria = :convocatoriaId \r\n"
//				+ "GROUP BY tpa.id_plan";
//
//		Query query = entityManager.createNativeQuery(consulta);
//		query.setParameter("convocatoriaId", convocatoriaId);
//		
//		List<Object[]> listaQuery = query.getResultList();
//
//		if (!listaQuery.isEmpty()) {
//			for (Object[] obj : listaQuery) {
//
//				ConvocatoriaTableroResumen convocatoria = mapeoTablero(obj);
//				lista.add(convocatoria);
//
//			}
//		}
//
//		return lista;
//
//	}
//	
//	
//	
//	
//	private ConvocatoriaTableroResumen mapeoTablero(Object[] obj) {
//
//		ConvocatoriaTableroResumen regresa = new ConvocatoriaTableroResumen();
//		
//		regresa.setNombreConvocatoria(obj[0].toString());
//		regresa.setNombrePlan(obj[1].toString());
//		BigInteger total = (BigInteger) obj[2];
//		regresa.setTotal(total);
//
//		return regresa;
//	}
//	
//	
//	@Override
//	public List<Convocatoria> consultarConvocatoriasFiltros(ConvocatoriaParamConsulta convocatoriaParamConsulta) {
//
//		List<Convocatoria> lista = new ArrayList<Convocatoria>();
//
////		String consulta = "SELECT tb.convocatoria_id,tb.nombre,tb.nombre_corto,tb.descripcion,tb.fecha_apertura,tb.fecha_cierre,\r\n"
////				+ "tb.semestre,tb.tipo,tb.url_convocatoria,tb.activo,tb.fecha_alta,tb.fecha_modificacion,tb.cupo_limite FROM tbl_convocatoria tb WHERE tb.activo = 1";
//		
//		String consulta = "SELECT DISTINCT(tb.convocatoria_id),tb.nombre,tb.nombre_corto,tb.descripcion,tb.fecha_apertura,tb.fecha_cierre, tb.semestre,tb.tipo,tb.url_convocatoria,tb.activo,tb.fecha_alta,tb.fecha_modificacion,tb.cupo_limite \r\n"
//				+ "FROM tbl_convocatoria tb\r\n"
//				+ "         INNER JOIN rel_convocatoria_planesyprogramas rcpp ON rcpp.id_convocatoria = tb.convocatoria_id\r\n"
//				+ "         INNER JOIN cat_nivel_ensenanza_programa cne ON cne.id = rcpp.id_nivel_ensenanza ";
//		
//		String queryFiltro = obtieneFiltro(convocatoriaParamConsulta);
//		
//		String es = " AND cne.activo = 1";
//		
//		Query query = entityManager.createNativeQuery(consulta.concat(queryFiltro).concat(es));
//
//		List<Object[]> listaQuery = query.getResultList();
//
//		if (!listaQuery.isEmpty()) {
//			for (Object[] obj : listaQuery) {
//
//				Convocatoria convocatoria = mapeo2(obj);
//				lista.add(convocatoria);
//
//			}
//		}
//
//		return lista;
//
//	}
//	
//	private String obtieneFiltro(ConvocatoriaParamConsulta convocatoriaParamConsulta) {
//		
//		StringBuilder filtro = new StringBuilder("");
//		boolean isPrimerFiltro = false;
//		
//		
//		if (!("").equals(convocatoriaParamConsulta.getConsulNombreConvocatoria())) {
//			filtro.append(validaOperador(isPrimerFiltro)+"tb.nombre = '").append(convocatoriaParamConsulta.getConsulNombreConvocatoria()).append("'").append(" ");
//			isPrimerFiltro = true;
//		}
//		
//		if (!("").equals(convocatoriaParamConsulta.getConsulNombreCorto())) {
//			filtro.append(validaOperador(isPrimerFiltro)+"tb.nombre_corto = '").append(convocatoriaParamConsulta.getConsulNombreCorto()).append("'").append(" ");
//			isPrimerFiltro = true;
//		}
//		
//		if (!("").equals(convocatoriaParamConsulta.getValueConvocatoriaEstatus())) {
//			filtro.append(validaOperador(isPrimerFiltro)+"tb.activo = ").append(convocatoriaParamConsulta.getValueConvocatoriaEstatus()).append(" ");
//			isPrimerFiltro = true;
//		}
//		
//		if (!("0").equals(convocatoriaParamConsulta.getConsulNivelEducativo())) {
//			filtro.append(validaOperador(isPrimerFiltro)+"rcpp.id_nivel_ensenanza = ").append(convocatoriaParamConsulta.getConsulNivelEducativo()).append(" ");
//			isPrimerFiltro = true;
//		}
//		
//		
//		obtieneFiltroFechas(filtro, convocatoriaParamConsulta, isPrimerFiltro);
//		
//		return filtro.toString();
//		
//	}
//	
//	private StringBuilder obtieneFiltroFechas(StringBuilder filtro, ConvocatoriaParamConsulta filter, boolean isPrimerFiltro) {
//		
//		if (!filter.getConsulFechaApertura().trim().equals("") && !filter.getConsulFechaCierre().trim().equals("")) {
//			filtro.append(validaOperador(isPrimerFiltro)+"tb.fecha_apertura BETWEEN '")
//			.append(filter.getConsulFechaApertura()).append("' AND '").append(filter.getConsulFechaApertura()).append("'");
//		}
//		
//		if (!filter.getConsulFechaApertura().trim().equals("") && !filter.getConsulFechaCierre().trim().equals("")) {
//			filtro.append(validaOperador(isPrimerFiltro)+"tb.fecha_cierre BETWEEN '")
//			.append(filter.getConsulFechaCierre()).append("' AND '").append(filter.getConsulFechaCierre()).append("'");
//		}
//		
//		return filtro;
//		
//	}
//	
//	 public static String validaOperador(boolean filtro) {
//			String operador = "";
//			if (!filtro) {
//				operador = " WHERE ";
//			} else {
//				operador = " AND ";
//			}			
//			return operador;
//		}
//	
//	private Convocatoria mapeo2(Object[] obj) {
//
//		Convocatoria regresa = new Convocatoria();
//
//		regresa.setConvocatoriaId((Integer) obj[0]);
//		regresa.setNombre(obj[1].toString());
//		regresa.setNombreCorto(obj[2].toString());
//		regresa.setDescripcion(obj[3].toString());
//		regresa.setFecha_Apertura((java.util.Date) obj[4]);
//		regresa.setFechaCierre((java.util.Date) obj[5]);
//		regresa.setSemestre((Integer) obj[6]);
//		regresa.setTipo( obj[7].toString());
//		regresa.setUrlConvocatoria(obj[8].toString());
//		regresa.setActivo( obj[9].toString());
//		regresa.setFechaAlta((java.util.Date) obj[10]);
//		regresa.setFechaModificacion((java.util.Date) obj[11]);
//		regresa.setCupoLimite((Integer) obj[12]);
//
//		return regresa;
//	}
//	
//	
//	@Override
//	public List<ConvocatoriaNivelEducativo> consultarNivelEducativo() {
//
//		List<ConvocatoriaNivelEducativo> lista = new ArrayList<ConvocatoriaNivelEducativo>();
//
//		String consulta = "SELECT cne.id, cne.nombre FROM cat_nivel_ensenanza_programa cne WHERE cne.activo = 1";
//
//		Query query = entityManager.createNativeQuery(consulta);
//
//		List<Object[]> listaQuery = query.getResultList();
//
//		if (!listaQuery.isEmpty()) {
//			for (Object[] obj : listaQuery) {
//
//				ConvocatoriaNivelEducativo convocatoria = mapeoNivel(obj);
//				lista.add(convocatoria);
//
//			}
//		}
//
//		return lista;
//
//	}
//	
//	private ConvocatoriaNivelEducativo mapeoNivel(Object[] obj) {
//
//		ConvocatoriaNivelEducativo regresa = new ConvocatoriaNivelEducativo();
//
//		regresa.setId((Integer) obj[0]);
//		regresa.setNombre(obj[1].toString());
//
//		return regresa;
//	}
//	
//	
//	@Override
//	public void altaConvocatorias() {
//
//		List<ConvocatoriaNivelEducativo> lista = new ArrayList<ConvocatoriaNivelEducativo>();
//
//		String consulta = "SELECT cne.id, cne.nombre FROM cat_nivel_ensenanza_programa cne WHERE cne.activo = 1";
//
//		Query query = entityManager.createNativeQuery(consulta);
//
//		 query.getResultList();
//
//		
//		
//
//	}
//	
//	@Transactional
//	@Override
//	public void eliminarConvocatorias(Convocatoria elminarConvo) {
//
//		String consulta = "DELETE FROM tbl_convocatoria WHERE convocatoria_id = :id";
//		String consulta2 = "DELETE FROM rel_convocatoria_planesyprogramas WHERE id_convocatoria = :id";
//
//		Query query2 = entityManager.createNativeQuery(consulta2);
//        query2.setParameter("id", elminarConvo.getConvocatoriaId());
//        query2.executeUpdate();
//        
//		Query query = entityManager.createNativeQuery(consulta);
//        query.setParameter("id", elminarConvo.getConvocatoriaId());
//        query.executeUpdate();
// 
//	}

	@Override
	@Transactional
	public void altaInscripcionExtra(InscripcionParamNueva inscripcionParamNueva) {

		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

		ZonedDateTime zonedDateTime = ZonedDateTime.parse(inscripcionParamNueva.getFechaInicio().toString(),
				inputFormatter);
		ZonedDateTime zonedDateTime2 = ZonedDateTime.parse(inscripcionParamNueva.getFechaFin().toString(),
				inputFormatter);

		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		ZonedDateTime currentDateTime = ZonedDateTime.now();

		String fecha1 = zonedDateTime.format(outputFormatter);
		String fecha2 = zonedDateTime2.format(outputFormatter);
		String fecha3 = currentDateTime.format(outputFormatter);

		String valorSeleccionado = inscripcionParamNueva.getConvocatoriaSeleccionada();

		String valorSeleccionado2 = inscripcionParamNueva.getProcesoSeleccionada();
		
		String semestreValor = inscripcionParamNueva.getSemestre();

		Integer idConvocatoria = Integer.parseInt(valorSeleccionado);

		Integer idProceso = Integer.parseInt(valorSeleccionado2);
		
		Integer semestreNum = Integer.parseInt(semestreValor);

		String consulta = "INSERT INTO tbl_procesos_inscripcion "
				+ "(nombre, clave_proceso, descripcion, fecha_inicio, fecha_fin, id_tipo_proceso, estatus, semestre, perfil, convocatoria_id, id_categoria_proceso) "
				+ "VALUES "
				+ "(:nombre, :claveProceso, :descripcion, :fechaInicio, :fechaFin, :idTipoProceso, :estatus, :semestre, :perfil, :convocatoriaId, :idCategoriaProceso)";

		String consulta2 = "SELECT convocatoria_id, fecha_inicio, fecha_fin \r\n"
				+ "FROM des_sisi_gestor.tbl_procesos_inscripcion \r\n" + "WHERE nombre = :nombre\r\n"
				+ "  AND clave_proceso = :claveProceso\r\n" + "  AND descripcion = :descripcion\r\n"
//				+ "  AND id_tipo_proceso = :idTipoProceso\r\n"
				+ "  AND estatus = :estatus\r\n" + "  AND semestre = :semestre\r\n" + "  AND perfil = :perfil\r\n"
				+ "  AND convocatoria_id = :convocatoriaId\r\n" + "  AND id_categoria_proceso = :idCategoriaProceso";
		
		
		
		String consulta3 = "INSERT INTO rel_proceso_inscipcion_planesyprogramas "
				+ "(id_proceso_inscripcion, id_plan, id_programa, fecha_modificacion) " + "VALUES "
				+ "(:idProceso, :idPlan, :idPrograma, :fchModificacion)";
		
		
		Query query2 = entityManager.createNativeQuery(consulta2);

		query2.setParameter("nombre", inscripcionParamNueva.getNombre());
		query2.setParameter("claveProceso", inscripcionParamNueva.getCalveProceso());
		query2.setParameter("descripcion", inscripcionParamNueva.getDescripcion());
		query2.setParameter("estatus", inscripcionParamNueva.getAltaEstatus());
		query2.setParameter("semestre", semestreNum);
		query2.setParameter("perfil", inscripcionParamNueva.getPerfil());
		query2.setParameter("convocatoriaId", idConvocatoria);
		query2.setParameter("idCategoriaProceso", 1);

		List<Object[]> listaQuery = query2.getResultList();

		if (!listaQuery.isEmpty()) {
			Query query = entityManager.createNativeQuery(consulta);

			// Asignar los parámetros
			query.setParameter("nombre", inscripcionParamNueva.getNombre());
			query.setParameter("claveProceso", inscripcionParamNueva.getCalveProceso());
			query.setParameter("descripcion", inscripcionParamNueva.getDescripcion());
			query.setParameter("fechaInicio", fecha1);
			query.setParameter("fechaFin", fecha2);
			query.setParameter("idTipoProceso", idProceso);
			query.setParameter("estatus", inscripcionParamNueva.getAltaEstatus());
			query.setParameter("semestre", 1);
			query.setParameter("perfil", inscripcionParamNueva.getPerfil());
			query.setParameter("convocatoriaId", idConvocatoria);
			query.setParameter("idCategoriaProceso", 1);

			inscripcionParamNueva.setInscripcionOrdinaria(false);
			
			// Ejecutar la consulta
			query.executeUpdate();
			

			Query query3 = entityManager.createNativeQuery(consulta3);
			
			
			for (String  programas : inscripcionParamNueva.getListaPlanesValor()) {
				for (String  planes : inscripcionParamNueva.getListaProgramaValor()) {
					Object idPlan = planes;
					Object idPrograma = programas;
					
					query3.setParameter("idProceso", 2);
					query3.setParameter("idPlan", idPlan);
					query3.setParameter("fchModificacion", fecha3);
					query3.setParameter("idPrograma", idPrograma);
					
					query3.executeUpdate();
				}
			}


		} else {
			inscripcionParamNueva.setInscripcionOrdinaria(true);
		}

	}

	@Override
	@Transactional
	public void altaInscripcion(InscripcionParamNueva inscripcionParamNueva) {
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

		ZonedDateTime zonedDateTime = ZonedDateTime.parse(inscripcionParamNueva.getFechaInicio().toString(),
				inputFormatter);
		ZonedDateTime zonedDateTime2 = ZonedDateTime.parse(inscripcionParamNueva.getFechaFin().toString(),
				inputFormatter);

		ZonedDateTime currentDateTime = ZonedDateTime.now();

		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		String fecha1 = zonedDateTime.format(outputFormatter);
		String fecha2 = zonedDateTime2.format(outputFormatter);
		String fecha3 = currentDateTime.format(outputFormatter);

		String valorSeleccionado = inscripcionParamNueva.getConvocatoriaSeleccionada();

		String valorSeleccionado2 = inscripcionParamNueva.getProcesoSeleccionada();
		
		String semestreValor = inscripcionParamNueva.getSemestre();
		
		Integer semestreNum = Integer.parseInt(semestreValor);

		Integer idConvocatoria = Integer.parseInt(valorSeleccionado);

		Integer idProceso = Integer.parseInt(valorSeleccionado2);

		String consulta = "INSERT INTO tbl_procesos_inscripcion "
				+ "(nombre, clave_proceso, descripcion, fecha_inicio, fecha_fin, id_tipo_proceso, estatus, semestre, perfil, convocatoria_id, id_categoria_proceso) "
				+ "VALUES "
				+ "(:nombre, :claveProceso, :descripcion, :fechaInicio, :fechaFin, :idTipoProceso, :estatus, :semestre, :perfil, :convocatoriaId, :idCategoriaProceso)";

		String consulta2 = "SELECT convocatoria_id \r\n" + "FROM des_sisi_gestor.tbl_procesos_inscripcion \r\n"
//				+ "WHERE nombre = :nombre\r\n" + "  AND clave_proceso = :claveProceso\r\n"
//				+ "  AND descripcion = :descripcion\r\n" 
				+ "  WHERE :fechaInicio BETWEEN fecha_inicio AND fecha_fin\r\n"
				+ "  AND :fechaFin BETWEEN fecha_inicio AND fecha_fin \r\n"
				+ "  AND id_tipo_proceso = :idTipoProceso\r\n"
//				+ "  AND estatus = :estatus\r\n" 
//				+ "  AND semestre = :semestre\r\n" + "  AND perfil = :perfil\r\n" :fecha BETWEEN fecha_inicio AND fecha_final
				+ "  AND convocatoria_id = :convocatoriaId\r\n";
//              + "  AND id_categoria_proceso = :idCategoriaProceso";

		String consulta3 = "select id, id_convocatoria, id_plan, id_programa from rel_convocatoria_planesyprogramas \r\n"
				+ "WHERE id_convocatoria = :idConvocatoria";

		String consulta4 = "INSERT INTO rel_proceso_inscipcion_planesyprogramas "
				+ "(id_proceso_inscripcion, id_plan, id_programa, fecha_modificacion) " + "VALUES "
				+ "(:idProceso, :idPlan, :idPrograma, :fchModificacion)";
		
		String consulta5 = "SELECT\r\n"
				+ "    rcp.id_convocatoria,\r\n"
				+ "    tp.id_plan,\r\n"
				+ "    tfdp.id_programa,\r\n"
				+ "    CONCAT(substring_index(tp.identificador,'-',1),'-',tfdp.identificador_final,substr(tmc.nombre,1,1),substring_index(tmc.nombre,' ',-1),substr(tmc2.nombre,1,1),substring_index(tmc2.nombre,' ',-1),'-',CONCAT(DATE_FORMAT(tc.fecha_apertura, '%y'), LPAD(MONTH(tc.fecha_apertura), 2, '0')),'-',substr(tmc.nombre,1,1),substring_index(tmc.nombre,' ',-1),'-','000') grupo,\r\n"
				+ "    tp.nombre programa_educativa,\r\n"
				+ "    tfdp.nombre_tentativo asignatura,\r\n"
				+ "    tfdp.identificador_final clave_asignatura,\r\n"
				+ "    substring_index(tmc2.nombre,' ',-1) semestre,\r\n"
				+ "    CONCAT(substring_index(tmc.nombre,' ',-1)) bloque,\r\n"
				+ "    0 no_estudiantes,\r\n"
				+ "    0 no_grupos,\r\n"
				+ "    0 estudiantes_x_grupo,\r\n"
				+ "    0 grupo_resto,\r\n"
				+ "    0 estudiantes_resto\r\n"
				+ "FROM rel_convocatoria_planesyprogramas rcp\r\n"
				+ "JOIN tbl_convocatoria tc ON rcp.id_convocatoria = tc.convocatoria_id\r\n"
				+ "JOIN tbl_planes tp ON  rcp.id_plan = tp.id_plan\r\n"
				+ "JOIN tbl_ficha_descriptiva_programa tfdp ON rcp.id_programa = tfdp.id_programa\r\n"
				+ "JOIN tbl_malla_curricular tmc ON tmc.id = tfdp.id_eje_capacitacion\r\n"
				+ "JOIN tbl_malla_curricular tmc2 ON tmc2.id = tmc.id_padre\r\n"
				+ "WHERE rcp.id_convocatoria = :idConv";
		
		String consulta6 = "INSERT INTO tbl_inscripcion_resumen "
				+ "(grupo, programa_educativo, asignatura, clave_asignatura, semestre, bloque, no_estudiantes,no_grupos,estudiantes_x_grupo, grupo_resto, estudiantes_resto) " + "VALUES "
				+ "(:grupo, :programaEducativo, :asignatura, :claveAsignatura, :semestre, :bloque, :numeroEstudiantes, :numeroGrupos, :estudiantesPorGrupo, :grupoResto, :estudiantesResto)\r\n";
			

		Query query2 = entityManager.createNativeQuery(consulta2);

//		query2.setParameter("nombre", inscripcionParamNueva.getNombre());
//		query2.setParameter("claveProceso", inscripcionParamNueva.getCalveProceso());
//		query2.setParameter("descripcion", inscripcionParamNueva.getDescripcion());
		query2.setParameter("fechaInicio", fecha1);
		query2.setParameter("fechaFin", fecha2);
		query2.setParameter("idTipoProceso", idProceso);
//		query2.setParameter("estatus", inscripcionParamNueva.getAltaEstatus());
//		query2.setParameter("semestre", semestreNum);
//		query2.setParameter("perfil", inscripcionParamNueva.getPerfil());
		query2.setParameter("convocatoriaId", idConvocatoria);
//		query2.setParameter("idCategoriaProceso", 1);

		List<Object[]> listaQuery = query2.getResultList();

		if (listaQuery.isEmpty()) {
			Query query = entityManager.createNativeQuery(consulta);

			// Asignar los parámetros
			query.setParameter("nombre", inscripcionParamNueva.getNombre());
			query.setParameter("claveProceso", inscripcionParamNueva.getCalveProceso());
			query.setParameter("descripcion", inscripcionParamNueva.getDescripcion());
			query.setParameter("fechaInicio", fecha1);
			query.setParameter("fechaFin", fecha2);
			query.setParameter("idTipoProceso", idProceso);
			query.setParameter("estatus", inscripcionParamNueva.getAltaEstatus());
			query.setParameter("semestre", 1);
			query.setParameter("perfil", inscripcionParamNueva.getPerfil());
			query.setParameter("convocatoriaId", idConvocatoria);
			query.setParameter("idCategoriaProceso", 1);

			inscripcionParamNueva.setInscripcionExistente(false);
			// Ejecutar la consulta
			query.executeUpdate();
			
			Query query3 = entityManager.createNativeQuery(consulta3);
			
			query3.setParameter("idConvocatoria", idConvocatoria);
			
			List<Object[]> listaConvocatoria = query3.getResultList();

			for (Object[] row : listaConvocatoria) {
				Query query4 = entityManager.createNativeQuery(consulta4);

				Object id = row[0];
				Object idPlan = row[2];
				Object idPrograma = row[3];

				query4.setParameter("idProceso", 1);
				query4.setParameter("idPlan", idPlan);
				query4.setParameter("idPrograma", idPrograma);
				query4.setParameter("fchModificacion", fecha3);
				
				query4.executeUpdate();
			}
			
			Query query5 = entityManager.createNativeQuery(consulta5);
			
			query5.setParameter("idConv", idConvocatoria);
			
			List<Object[]> listaResumen = query5.getResultList();
			
			for (Object[] row : listaResumen) {
				Query query6 = entityManager.createNativeQuery(consulta6);
				
				query6.setParameter("grupo", row[3]);
				query6.setParameter("programaEducativo", row[4]);
				query6.setParameter("asignatura", row[5]);
				query6.setParameter("claveAsignatura", row[6]);
				query6.setParameter("semestre", row[7]);
				query6.setParameter("bloque", row[8]);
				query6.setParameter("numeroEstudiantes", row[9]);
				query6.setParameter("numeroGrupos", row[10]);
				query6.setParameter("estudiantesPorGrupo", row[11]);
				query6.setParameter("grupoResto", row[12]);
				query6.setParameter("estudiantesResto", row[13]);
				query6.executeUpdate();
			}

		} else {
			inscripcionParamNueva.setInscripcionExistente(true);
		}

	}

	public List<Convocatoria> planesProgramasConvocatoria() {

		return null;
	}

}
