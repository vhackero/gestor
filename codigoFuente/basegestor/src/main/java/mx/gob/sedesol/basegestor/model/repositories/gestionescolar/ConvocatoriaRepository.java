package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.math.BigInteger;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.inscripcion.ConvocatoriaDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaNivelEducativo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaNivelEducativoCompl;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaParamConsulta;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaParamNueva;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaTableroResumen;

@Repository
public class ConvocatoriaRepository implements IConvocatoriaRepository {

	@Autowired
	public EntityManager entityManager;

	private static final Logger logger = Logger.getLogger(ConvocatoriaRepository.class);

	String query1 = "INSERT INTO tbl_convocatoria\r\n" + "(\r\n"
			+ "nombre,nombre_corto,descripcion,fecha_apertura,fecha_cierre,semestre,tipo,url_convocatoria,activo,fecha_alta,fecha_modificacion,cupo_limite)\r\n"
			+ "VALUES\r\n";

	String query2 = "SELECT MAX(convocatoria_id) FROM des_sisi_gestor.tbl_convocatoria";

	String query3 = "INSERT INTO rel_convocatoria_planesyprogramas\r\n"
			+ "(id_convocatoria,id_nivel_ensenanza,id_plan,id_programa,fecha_modificacion)\r\n" + "VALUES ";

	String query4 = "UPDATE tbl_convocatoria c " + "SET c.nombre = :nombre, " + "    c.nombre_corto = :nombreCorto, "
			+ "    c.descripcion = :descripcion, " + "    c.fecha_apertura = :fechaApertura, "
			+ "    c.fecha_cierre = :fechaCierre, " + "    c.url_convocatoria = :urlConvocatoria, "
			+ "    c.activo = :activo, " +
			// " c.fecha_alta = :fechaAlta, " +
			"    c.cupo_limite = :cupoLimite " + "WHERE c.convocatoria_id = :id";

	String query5 = "SELECT pr.convocatoria_id, pr.nombre FROM tbl_convocatoria pr "
			+ "  WHERE pr.convocatoria_id = :idConv ";
//			"  AND pr.id_plan = :idPlan " +
//			"  AND pr.id_programa = :idPrograma ";

	String query6 = "SELECT c.id, c.id_plan, c.id_programa, c.fecha_modificacion FROM rel_convocatoria_planesyprogramas c "
			+ "	 WHERE c.id_convocatoria = :idConvocatoria "
			+ "	 AND c.id_nivel_ensenanza = :idNivelEnsenanzaP "
			+ "	 AND c.id_plan = :idPlanP "
			+ "	 AND c.id_programa = :idProgramaP";

	String query7 = "UPDATE rel_convocatoria_planesyprogramas c " + "SET c.id_plan = :idPlan, "
			+ "    c.id_programa = :idPrograma, " + "    c.fecha_modificacion = :fchModificacion "
			+ "	 WHERE c.id = :idUp";

	@Override
	public List<Convocatoria> consultarConvocatorias() {

		List<Convocatoria> lista = new ArrayList<Convocatoria>();

		String consulta = "SELECT tb.convocatoria_id, tb.nombre FROM tbl_convocatoria tb WHERE tb.activo = 1";

		Query query = entityManager.createNativeQuery(consulta);

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				Convocatoria convocatoria = mapeo(obj);
				lista.add(convocatoria);

			}
		}

		return lista;

	}

	private Convocatoria mapeo(Object[] obj) {

		Convocatoria regresa = new Convocatoria();

		regresa.setConvocatoriaId((Integer) obj[0]);
		regresa.setNombre(obj[1].toString());

		return regresa;
	}

	@Override
	public List<ConvocatoriaTableroResumen> consultarTableroResumen(Integer convocatoriaId) {

		List<ConvocatoriaTableroResumen> lista = new ArrayList<ConvocatoriaTableroResumen>();

		String consulta = "SELECT tc.nombre nombre_convocatoria, tp.nombre nombre_plan,  COUNT(tpa.id_persona_aspirante) total FROM tbl_persona_aspirante tpa\r\n"
				+ "INNER JOIN tbl_planes tp ON tp.id_plan = tpa.id_plan\r\n"
				+ "INNER JOIN tbl_convocatoria tc ON tc.convocatoria_id = tpa.id_convocatoria\r\n"
				+ "WHERE tpa.id_convocatoria = :convocatoriaId \r\n" + "GROUP BY tpa.id_plan";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("convocatoriaId", convocatoriaId);

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				ConvocatoriaTableroResumen convocatoria = mapeoTablero(obj);
				lista.add(convocatoria);

			}
		}

		return lista;

	}

	private ConvocatoriaTableroResumen mapeoTablero(Object[] obj) {

		ConvocatoriaTableroResumen regresa = new ConvocatoriaTableroResumen();

		regresa.setNombreConvocatoria(obj[0].toString());
		regresa.setNombrePlan(obj[1].toString());
		BigInteger total = (BigInteger) obj[2];
		regresa.setTotal(total);

		return regresa;
	}

	@Override
	public List<ConvocatoriaNivelEducativoCompl> consultarPlanesProgramasId(Convocatoria convocatoriaParamConsulta) {

		List<ConvocatoriaNivelEducativoCompl> lista = new ArrayList<ConvocatoriaNivelEducativoCompl>();
		String consulta = "select id_plan, id_programa from des_sisi_gestor.rel_convocatoria_planesyprogramas\r\n"
				+ "WHERE id_convocatoria = :idConvocatoria";

		String consulta2 = " SELECT cnp.id id_nivel_ensenanza, cnp.nombre nivel_ensenaza, tp.id_plan id_plan, tp.nombre plan, fdp.id_programa id_programa, fdp.nombre_tentativo programa,\r\n"
				+ " mcr.nombre bloque,\r\n"
				+ "(SELECT mcrs.nombre FROM tbl_malla_curricular mcrs WHERE mcrs.id = mcr.id_padre)"
				+ "FROM tbl_planes tp\r\n"
				+ "         INNER JOIN tbl_ficha_descriptiva_programa fdp ON fdp.id_plan = tp.id_plan AND fdp.identificador_final IS not NULL AND fdp.identificador_final != ''\r\n"
				+ "         INNER JOIN cat_nivel_ensenanza_programa cnp ON fdp.id_nivel_programa = cnp.id\r\n"
				+ "         INNER JOIN tbl_malla_curricular mc ON mc.id_plan = tp.id_plan AND mc.activo =1 \r\n"
				+ "			INNER JOIN tbl_malla_curricular mcr ON mcr.id = fdp.id_eje_capacitacion \r\n"
				+ "			WHERE tp.id_plan = :idPlan AND fdp.id_programa = :idPrograma \r\n"
				+ " order by tp.id_plan, (SELECT mcrs.nombre FROM tbl_malla_curricular mcrs WHERE mcrs.id = mcr.id_padre), mcr.nombre";

		Query query = entityManager.createNativeQuery(consulta);

		query.setParameter("idConvocatoria", convocatoriaParamConsulta.getConvocatoriaId());

		List<Object[]> listaPlanPrograma = query.getResultList();

		if (!listaPlanPrograma.isEmpty()) {

			for (Object[] planPrograma : listaPlanPrograma) {

				Query query2 = entityManager.createNativeQuery(consulta2);

				query2.setParameter("idPlan", planPrograma[0]);
				query2.setParameter("idPrograma", planPrograma[1]);

				List<Object[]> listaPlanProgramaCompl = query2.getResultList();

				for (Object[] planProgramaCompl : listaPlanProgramaCompl) {
					ConvocatoriaNivelEducativoCompl convocatoria = mapeoNivelComp(planProgramaCompl);
					lista.add(convocatoria);
				}

			}
		}

		return lista;
	}

	@Override
	public List<Convocatoria> consultarConvocatoriasId(Convocatoria convocatoriaParamConsulta) {
		List<Convocatoria> lista = new ArrayList<Convocatoria>();

//		String consulta = "SELECT tb.convocatoria_id,tb.nombre,tb.nombre_corto,tb.descripcion,tb.fecha_apertura,tb.fecha_cierre,\r\n" 
//				+ "tb.semestre,tb.tipo,tb.url_convocatoria,tb.activo,tb.fecha_alta,tb.fecha_modificacion,tb.cupo_limite FROM tbl_convocatoria tb WHERE tb.activo = 1";

		String consulta = "SELECT DISTINCT(tb.convocatoria_id),tb.nombre,tb.nombre_corto,tb.descripcion,tb.fecha_apertura,tb.fecha_cierre, tb.semestre,tb.tipo,tb.url_convocatoria,tb.activo,tb.fecha_alta,tb.fecha_modificacion,tb.cupo_limite, cne.nombre as nomNivel \r\n"
				+ "FROM tbl_convocatoria tb\r\n"
				+ "         INNER JOIN rel_convocatoria_planesyprogramas rcpp ON rcpp.id_convocatoria = tb.convocatoria_id\r\n"
				+ "         INNER JOIN cat_nivel_ensenanza_programa cne ON cne.id = rcpp.id_nivel_ensenanza AND cne.activo = 1\r\n"
				+ "			WHERE tb.convocatoria_id = :idConvocatoria";

		Query query = entityManager.createNativeQuery(consulta);

		query.setParameter("idConvocatoria", convocatoriaParamConsulta.getConvocatoriaId());

		// logger.info(consulta);

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				Convocatoria convocatoria = mapeo3(obj);
				lista.add(convocatoria);

			}
		}

		return lista;
	}

	@Override
	public List<Convocatoria> consultarConvocatoriasFiltros(ConvocatoriaParamConsulta convocatoriaParamConsulta) {

		List<Convocatoria> lista = new ArrayList<Convocatoria>();

//		String consulta = "SELECT tb.convocatoria_id,tb.nombre,tb.nombre_corto,tb.descripcion,tb.fecha_apertura,tb.fecha_cierre,\r\n"
//				+ "tb.semestre,tb.tipo,tb.url_convocatoria,tb.activo,tb.fecha_alta,tb.fecha_modificacion,tb.cupo_limite FROM tbl_convocatoria tb WHERE tb.activo = 1";

		String consulta = "SELECT DISTINCT(tb.convocatoria_id),tb.nombre,tb.nombre_corto,tb.descripcion,tb.fecha_apertura,tb.fecha_cierre, tb.semestre,tb.tipo,tb.url_convocatoria,tb.activo,tb.fecha_alta,tb.fecha_modificacion,tb.cupo_limite, cne.nombre as nomNivel \r\n"
				+ "FROM tbl_convocatoria tb\r\n"
				+ "         INNER JOIN rel_convocatoria_planesyprogramas rcpp ON rcpp.id_convocatoria = tb.convocatoria_id\r\n"
				+ "         INNER JOIN cat_nivel_ensenanza_programa cne ON cne.id = rcpp.id_nivel_ensenanza AND cne.activo = 1 ";

		String queryFiltro = obtieneFiltro(convocatoriaParamConsulta);

		Query query = entityManager.createNativeQuery(consulta.concat(queryFiltro));
		asignarParametrosFiltros(query, convocatoriaParamConsulta);

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				Convocatoria convocatoria = mapeo2(obj);
				lista.add(convocatoria);

			}
		}

		return lista;

	}

	private String obtieneFiltro(ConvocatoriaParamConsulta convocatoriaParamConsulta) {

		StringBuilder filtro = new StringBuilder("");
		boolean isPrimerFiltro = false;

		if (tieneTexto(convocatoriaParamConsulta.getConsulNombreConvocatoria())) {
			filtro.append(validaOperador(isPrimerFiltro)
					+ "UPPER(tb.nombre) LIKE CONCAT('%', UPPER(:nombreConvocatoria), '%') ");
			isPrimerFiltro = true;
		}

		if (tieneTexto(convocatoriaParamConsulta.getConsulNombreCorto())) {
			filtro.append(validaOperador(isPrimerFiltro)
					+ "UPPER(tb.nombre_corto) LIKE CONCAT('%', UPPER(:nombreCorto), '%') ");
			isPrimerFiltro = true;
		}

		if (tieneTexto(convocatoriaParamConsulta.getValueConvocatoriaEstatus())) {
			filtro.append(validaOperador(isPrimerFiltro) + "tb.activo = :estatus ");
			isPrimerFiltro = true;
		} else {
			filtro.append(validaOperador(isPrimerFiltro) + "tb.activo = ").append("1").append(" ");
			isPrimerFiltro = true;
		}

		if (esNivelSeleccionado(convocatoriaParamConsulta.getConsulNivelEducativo())) {
			filtro.append(validaOperador(isPrimerFiltro) + "rcpp.id_nivel_ensenanza = :nivelEducativo ");
			isPrimerFiltro = true;
		}

		obtieneFiltroFechas(filtro, convocatoriaParamConsulta, isPrimerFiltro);

		return filtro.toString();

	}

	private StringBuilder obtieneFiltroFechas(StringBuilder filtro, ConvocatoriaParamConsulta filter,
			boolean isPrimerFiltro) {

		if (filter.getConsulFechaApertura() != null && filter.getConsulFechaCierre() != null) {
			filtro.append(validaOperador(isPrimerFiltro)
					+ "tb.fecha_apertura <= :fechaFin AND tb.fecha_cierre >= :fechaInicio ");
		}

		return filtro;

	}

	private void asignarParametrosFiltros(Query query, ConvocatoriaParamConsulta filtro) {
		if (tieneTexto(filtro.getConsulNombreConvocatoria())) {
			query.setParameter("nombreConvocatoria", filtro.getConsulNombreConvocatoria().trim());
		}
		if (tieneTexto(filtro.getConsulNombreCorto())) {
			query.setParameter("nombreCorto", filtro.getConsulNombreCorto().trim());
		}
		if (tieneTexto(filtro.getValueConvocatoriaEstatus())) {
			query.setParameter("estatus", filtro.getValueConvocatoriaEstatus());
		}
		if (esNivelSeleccionado(filtro.getConsulNivelEducativo())) {
			query.setParameter("nivelEducativo", filtro.getConsulNivelEducativo());
		}
		if (filtro.getConsulFechaApertura() != null && filtro.getConsulFechaCierre() != null) {
			query.setParameter("fechaInicio", inicioDia(filtro.getConsulFechaApertura()));
			query.setParameter("fechaFin", finDia(filtro.getConsulFechaCierre()));
		}
	}

	private boolean tieneTexto(String valor) {
		return valor != null && !valor.trim().isEmpty();
	}

	private boolean esNivelSeleccionado(String nivelEducativo) {
		return tieneTexto(nivelEducativo) && !"0".equals(nivelEducativo);
	}

	private java.util.Date inicioDia(java.util.Date fecha) {
		java.util.Calendar calendario = java.util.Calendar.getInstance();
		calendario.setTime(fecha);
		calendario.set(java.util.Calendar.HOUR_OF_DAY, 0);
		calendario.set(java.util.Calendar.MINUTE, 0);
		calendario.set(java.util.Calendar.SECOND, 0);
		calendario.set(java.util.Calendar.MILLISECOND, 0);
		return calendario.getTime();
	}

	private java.util.Date finDia(java.util.Date fecha) {
		java.util.Calendar calendario = java.util.Calendar.getInstance();
		calendario.setTime(fecha);
		calendario.set(java.util.Calendar.HOUR_OF_DAY, 23);
		calendario.set(java.util.Calendar.MINUTE, 59);
		calendario.set(java.util.Calendar.SECOND, 59);
		calendario.set(java.util.Calendar.MILLISECOND, 999);
		return calendario.getTime();
	}

	public static String validaOperador(boolean filtro) {
		String operador = "";
		if (!filtro) {
			operador = " WHERE ";
		} else {
			operador = " AND ";
		}
		return operador;
	}

	private Convocatoria mapeo2(Object[] obj) {

		Convocatoria regresa = new Convocatoria();

		regresa.setConvocatoriaId((Integer) obj[0]);
		regresa.setNombre(obj[1].toString());
		regresa.setNombreCorto(obj[2].toString());
		regresa.setDescripcion(obj[3].toString());
		regresa.setFecha_Apertura((java.util.Date) obj[4]);
		regresa.setFechaCierre((java.util.Date) obj[5]);
		regresa.setSemestre((Integer) obj[6]);
		regresa.setTipo(obj[7].toString());
		regresa.setUrlConvocatoria(obj[8].toString());

		if ("1".equals(obj[9].toString())) {
			regresa.setActivo("ACTIVO ");
		} else if ("0".equals(obj[9].toString())) {
			regresa.setActivo("INACTIVO");
		}

		regresa.setFechaAlta((java.util.Date) obj[10]);
		regresa.setFechaModificacion((java.util.Date) obj[11]);
		regresa.setCupoLimite((Integer) obj[12]);
		regresa.setNombreNivel(obj[13].toString());

		return regresa;
	}

	private Convocatoria mapeo3(Object[] obj) {

		Convocatoria regresa = new Convocatoria();

		regresa.setConvocatoriaId((Integer) obj[0]);
		regresa.setNombre(obj[1].toString());
		regresa.setNombreCorto(obj[2].toString());
		regresa.setDescripcion(obj[3].toString());
		regresa.setFecha_Apertura((java.util.Date) obj[4]);
		regresa.setFechaCierre((java.util.Date) obj[5]);
		regresa.setSemestre((Integer) obj[6]);
		regresa.setTipo(obj[7].toString());
		regresa.setUrlConvocatoria(obj[8].toString());

		regresa.setActivo(obj[9].toString());
//		if( "1".equals(obj[9].toString()) ) {
//			regresa.setActivo( "ACTIVO ");
//		} else if( "0".equals(obj[9].toString()) ) {
//			regresa.setActivo( "INACTIVO" );
//		}		
//		
		regresa.setFechaAlta((java.util.Date) obj[10]);
		regresa.setFechaModificacion((java.util.Date) obj[11]);
		regresa.setCupoLimite((Integer) obj[12]);
		regresa.setNombreNivel(obj[13].toString());

		return regresa;
	}

	@Override
	public List<ConvocatoriaNivelEducativo> consultarNivelEducativo() {

		List<ConvocatoriaNivelEducativo> lista = new ArrayList<ConvocatoriaNivelEducativo>();

		String consulta = "SELECT cne.id, cne.nombre FROM cat_nivel_ensenanza_programa cne WHERE cne.activo = 1";

		Query query = entityManager.createNativeQuery(consulta);

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				ConvocatoriaNivelEducativo convocatoria = mapeoNivel(obj);
				lista.add(convocatoria);

			}
		}

		return lista;

	}

	@Override
	public List<ConvocatoriaNivelEducativoCompl> consultarNivelEducativoCompleto() {

		List<ConvocatoriaNivelEducativoCompl> lista = new ArrayList<ConvocatoriaNivelEducativoCompl>();

		String consulta = " SELECT cnp.id id_nivel_ensenanza, cnp.nombre nivel_ensenaza, tp.id_plan id_plan, tp.nombre plan, fdp.id_programa id_programa, fdp.nombre_tentativo programa,\r\n"
				+ " mcr.nombre bloque,\r\n"
				+ "(SELECT mcrs.nombre FROM tbl_malla_curricular mcrs WHERE mcrs.id = mcr.id_padre)"
				+ "FROM tbl_planes tp\r\n"
				+ "         INNER JOIN tbl_ficha_descriptiva_programa fdp ON fdp.id_plan = tp.id_plan AND fdp.identificador_final IS not NULL AND fdp.identificador_final != ''\r\n"
				+ "         INNER JOIN cat_nivel_ensenanza_programa cnp ON fdp.id_nivel_programa = cnp.id\r\n"
				+ "         INNER JOIN tbl_malla_curricular mc ON mc.id_plan = tp.id_plan AND mc.activo =1"
				+ " 		INNER JOIN tbl_malla_curricular mcr WHERE mcr.id = fdp.id_eje_capacitacion"
				+ " order by tp.id_plan, (SELECT mcrs.nombre FROM tbl_malla_curricular mcrs WHERE mcrs.id = mcr.id_padre), mcr.nombre";

		Query query = entityManager.createNativeQuery(consulta);

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				ConvocatoriaNivelEducativoCompl convocatoria = mapeoNivelComp(obj);
				lista.add(convocatoria);

			}
		}

		return lista;

	}

	private ConvocatoriaNivelEducativoCompl mapeoNivelComp(Object[] obj) {

		ConvocatoriaNivelEducativoCompl regresa = new ConvocatoriaNivelEducativoCompl();

		regresa.setIdNivelEnsenanza((Integer) obj[0]);
		regresa.setNombreivelEnsenanza(obj[1].toString());
		regresa.setIdPlan((Integer) obj[2]);
		regresa.setNombrePlan(obj[3].toString());
		regresa.setIdPrograma((Integer) obj[4]);
		regresa.setNombrePrograma(obj[5].toString());
		regresa.setNombreBloque(obj[6] != null ? obj[6].toString() : null);
		regresa.setNombreSemestre(obj[7] != null ? obj[7].toString() : null);

		return regresa;

	}

	private ConvocatoriaNivelEducativo mapeoNivel(Object[] obj) {

		ConvocatoriaNivelEducativo regresa = new ConvocatoriaNivelEducativo();

		regresa.setId((Integer) obj[0]);
		regresa.setNombre(obj[1].toString());

		return regresa;
	}

	@Transactional
	@Override
	public void actualizarConvocatorias(ConvocatoriaParamNueva convocatoriaParamNueva, int idConvocatoria) {
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

		ZonedDateTime zonedDateTime = ZonedDateTime.parse(convocatoriaParamNueva.getAltaFechaApertura().toString(),
				inputFormatter);
		ZonedDateTime zonedDateTime2 = ZonedDateTime.parse(convocatoriaParamNueva.getAltaFechaCierre().toString(),
				inputFormatter);
		// ZonedDateTime zonedDateTime3 =
		// ZonedDateTime.parse(convocatoriaParamNueva.getAltaFechaAlta().toString(),
		// inputFormatter);

		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		String fecha1 = zonedDateTime.format(outputFormatter);
		String fecha2 = zonedDateTime2.format(outputFormatter);
		// String fecha3 = zonedDateTime3.format(outputFormatter);

		Query query = entityManager.createNativeQuery(query4);

		query.setParameter("nombre", convocatoriaParamNueva.getAltaNombreConvocatoria());
		query.setParameter("nombreCorto", convocatoriaParamNueva.getAltaNombreCorto());
		query.setParameter("descripcion", convocatoriaParamNueva.getAltaDescripcion());
		query.setParameter("fechaApertura", fecha1);
		query.setParameter("fechaCierre", fecha2);
		query.setParameter("activo", convocatoriaParamNueva.getAltaEstatus());
		query.setParameter("urlConvocatoria", convocatoriaParamNueva.getAltaUrl());
		// query.setParameter("fechaAlta", fecha3);
		query.setParameter("cupoLimite", convocatoriaParamNueva.getAltaCupoLimite());
		// query.setParameter("fechaModificacion", convocatoriaParamNueva.get);
		query.setParameter("id", idConvocatoria);

		int filasAfectadas = query.executeUpdate();// duda

		if (filasAfectadas < 0) {
			throw new IllegalStateException("No fue posible actualizar la convocatoria " + idConvocatoria);
		}
		actualizarPlanesProgramasSeleccionados(convocatoriaParamNueva.getListaPlanProgramaNivel(), idConvocatoria, fecha2);

		if (false) {

			Query query02 = entityManager.createNativeQuery(query2);

			int id = (Integer) query02.getSingleResult();

			if (id >= 0) {

				Pattern pattern = Pattern.compile("id\\w+=(\\d+)");

				Matcher matcher = null;

				List<?> lista = convocatoriaParamNueva.getListaPlanProgramaNivel();

				for (int i = 0; i < lista.size(); i++) {
					matcher = pattern.matcher(lista.get(i).toString());
					int idNivelEnsenanza = -1;
					int idPlan = -1;
					int idPrograma = -1;

					int index = 0;
					while (matcher.find()) {
						int value = Integer.parseInt(matcher.group(1));
						// Asignar los valores a diferentes variables basadas en el orden de aparición
						if (index == 0) {
							idNivelEnsenanza = value;
						} else if (index == 1) {
							idPlan = value;
						} else if (index == 2) {
							idPrograma = value;
						}
						index++;
					}
					Query query05 = entityManager.createNativeQuery(query5);// duda
					query05.setParameter("idConv", idConvocatoria);
					// query05.setParameter("idPrograma", idPrograma);
					// query05.setParameter("idPlan", idPlan);

					List<Object[]> listaProgramaPlan = query05.getResultList();

					for (Object[] object : listaProgramaPlan) {

//				        	object[0] = idConvocatoria;
//				        	object[1] = idPlan;
//				        	object[2] = idPrograma;

						String idConv = object[0].toString();
						Integer valorConv = Integer.parseInt(idConv.toString());

						Query query06 = entityManager.createNativeQuery(query6);
						// Buscar
						query06.setParameter("idConvocatoria", valorConv);
						query06.setParameter("idNivelEnsenanzaP", idNivelEnsenanza);
						query06.setParameter("idPlanP", idPlan);
						query06.setParameter("idProgramaP", idPrograma);

						List<Object[]> listaConvPlanPrograma = query06.getResultList();

						if (!listaConvPlanPrograma.isEmpty()) {

							for (Object[] object2 : listaConvPlanPrograma) {

								Query query07 = entityManager.createNativeQuery(query7);
								// Actualizar
								query07.setParameter("idPlan", idPlan);
								query07.setParameter("idPrograma", idPrograma);
								query07.setParameter("fchModificacion", fecha2);
								String idPyP = object2[0].toString();
								Integer valorId = Integer.parseInt(idPyP.toString());
								// Buscar
								query07.setParameter("idUp", valorId);

								query07.executeUpdate();

							}

						} else {
							String query32 = "(" + valorConv + "," + idNivelEnsenanza + "," + idPlan + "," + idPrograma
									+ ",'" + fecha2 + "')";
							Query query03 = entityManager.createNativeQuery(query3.concat(query32));
							int filasAfectadas2 = query03.executeUpdate();
						}

					}

					// String query32 = "("+id+"," + idNivelEnsenanza +"," + idPlan +"," +
					// idPrograma +",'"+ fecha3 +"')";
					// Query query03 = entityManager.createNativeQuery(query3.concat(query32));
					// int filasAfectadas2 = query03.executeUpdate();
				}
			}

		}

	}

	private void actualizarPlanesProgramasSeleccionados(List<?> seleccionados, int idConvocatoria,
			String fechaModificacion) {
		Query consultarRelaciones = entityManager.createNativeQuery(
				"SELECT id, id_nivel_ensenanza, id_plan, id_programa "
						+ "FROM rel_convocatoria_planesyprogramas WHERE id_convocatoria = :idConvocatoria");
		consultarRelaciones.setParameter("idConvocatoria", idConvocatoria);
		List<Object[]> relacionesActuales = consultarRelaciones.getResultList();
		Map<String, Object> idsRelacionesActuales = new HashMap<String, Object>();
		for (Object[] relacionActual : relacionesActuales) {
			String clave = relacionActual[1] + "-" + relacionActual[2] + "-" + relacionActual[3];
			idsRelacionesActuales.put(clave, relacionActual[0]);
		}

		if (seleccionados == null) {
			seleccionados = new ArrayList<Object>();
		}

		Pattern pattern = Pattern.compile("id\\w+=(\\d+)");
		Set<String> relacionesInsertadas = new HashSet<String>();
		for (Object seleccionado : seleccionados) {
			Integer idNivelEnsenanza = null;
			Integer idPlan = null;
			Integer idPrograma = null;

			if (seleccionado instanceof ConvocatoriaNivelEducativoCompl) {
				ConvocatoriaNivelEducativoCompl relacion = (ConvocatoriaNivelEducativoCompl) seleccionado;
				idNivelEnsenanza = relacion.getIdNivelEnsenanza();
				idPlan = relacion.getIdPlan();
				idPrograma = relacion.getIdPrograma();
			} else if (seleccionado != null) {
				Matcher matcher = pattern.matcher(seleccionado.toString());
				Integer[] ids = new Integer[3];
				int index = 0;
				while (matcher.find() && index < ids.length) {
					ids[index++] = Integer.valueOf(matcher.group(1));
				}
				if (index == ids.length) {
					idNivelEnsenanza = ids[0];
					idPlan = ids[1];
					idPrograma = ids[2];
				}
			}

			if (idNivelEnsenanza == null || idPlan == null || idPrograma == null) {
				throw new IllegalArgumentException("La seleccion de nivel educativo, plan y programa es invalida");
			}

			String claveRelacion = idNivelEnsenanza + "-" + idPlan + "-" + idPrograma;
			if (relacionesInsertadas.add(claveRelacion) && !idsRelacionesActuales.containsKey(claveRelacion)) {
				Query insertarRelacion = entityManager.createNativeQuery(
						"INSERT INTO rel_convocatoria_planesyprogramas "
								+ "(id_convocatoria, id_nivel_ensenanza, id_plan, id_programa, fecha_modificacion) "
								+ "VALUES (:idConvocatoria, :idNivelEnsenanza, :idPlan, :idPrograma, :fechaModificacion)");
				insertarRelacion.setParameter("idConvocatoria", idConvocatoria);
				insertarRelacion.setParameter("idNivelEnsenanza", idNivelEnsenanza);
				insertarRelacion.setParameter("idPlan", idPlan);
				insertarRelacion.setParameter("idPrograma", idPrograma);
				insertarRelacion.setParameter("fechaModificacion", fechaModificacion);
				insertarRelacion.executeUpdate();
			}
		}

		for (Map.Entry<String, Object> relacionActual : idsRelacionesActuales.entrySet()) {
			if (!relacionesInsertadas.contains(relacionActual.getKey())) {
				Query eliminarRelacion = entityManager.createNativeQuery(
						"DELETE FROM rel_convocatoria_planesyprogramas WHERE id = :idRelacion");
				eliminarRelacion.setParameter("idRelacion", relacionActual.getValue());
				eliminarRelacion.executeUpdate();
			}
		}
	}

	@Transactional
	@Override
	public void altaConvocatorias(ConvocatoriaParamNueva convocatoriaParamNueva) {

		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

		ZonedDateTime zonedDateTime = ZonedDateTime.parse(convocatoriaParamNueva.getAltaFechaApertura().toString(),
				inputFormatter);
		ZonedDateTime zonedDateTime2 = ZonedDateTime.parse(convocatoriaParamNueva.getAltaFechaCierre().toString(),
				inputFormatter);
		ZonedDateTime zonedDateTime3 = ZonedDateTime.parse(convocatoriaParamNueva.getAltaFechaAlta().toString(),
				inputFormatter);

		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		String fecha1 = zonedDateTime.format(outputFormatter);
		String fecha2 = zonedDateTime2.format(outputFormatter);
		String fecha3 = zonedDateTime3.format(outputFormatter);
		// String fecha3 = "2024-12-12 17:52:52";

		String sql2 = " ( " + " '" + convocatoriaParamNueva.getAltaNombreConvocatoria() + "' ," + " '"
				+ convocatoriaParamNueva.getAltaNombreCorto() + "' ," + " '"
				+ convocatoriaParamNueva.getAltaDescripcion() + "' ," + " '" + fecha1 + "' ," + " '" + fecha2 + "' ,"
				+ " " + 1 + " ," + " " + 0 + " ," + " '" + convocatoriaParamNueva.getAltaUrl() + "' ," + " "
				+ convocatoriaParamNueva.getAltaEstatus() + " ," + " '" + fecha3 + "' ," + " '" + fecha3 + "' ," + " "
				+ convocatoriaParamNueva.getAltaCupoLimite() + " " + ")";

		Query query = entityManager.createNativeQuery(query1.concat(sql2));

		int filasAfectadas = query.executeUpdate();

		if (filasAfectadas >= 0) {

			Query query02 = entityManager.createNativeQuery(query2);

			int id = (Integer) query02.getSingleResult();

			if (id >= 0) {

				Pattern pattern = Pattern.compile("id\\w+=(\\d+)");

				Matcher matcher = null;

				List<?> lista = convocatoriaParamNueva.getListaNivelEducativoCompl();

				for (int i = 0; i < lista.size(); i++) {
					matcher = pattern.matcher(lista.get(i).toString());
					int idNivelEnsenanza = -1;
					int idPlan = -1;
					int idPrograma = -1;

					int index = 0;
					while (matcher.find()) {
						int value = Integer.parseInt(matcher.group(1));
						// Asignar los valores a diferentes variables basadas en el orden de aparición
						if (index == 0) {
							idNivelEnsenanza = value;
						} else if (index == 1) {
							idPlan = value;
						} else if (index == 2) {
							idPrograma = value;
						}
						index++;
					}

					String query32 = "(" + id + "," + idNivelEnsenanza + "," + idPlan + "," + idPrograma + ",'" + fecha3
							+ "')";

					Query query03 = entityManager.createNativeQuery(query3.concat(query32));

					int filasAfectadas2 = query03.executeUpdate();
				}

			}

		}

	}

	@Transactional
	@Override
	public void eliminarConvocatorias(Convocatoria elminarConvo) {

		String consulta = "DELETE FROM tbl_convocatoria WHERE convocatoria_id = :id";
		String consulta2 = "DELETE FROM rel_convocatoria_planesyprogramas WHERE id_convocatoria = :id";

		Query query2 = entityManager.createNativeQuery(consulta2);
		query2.setParameter("id", elminarConvo.getConvocatoriaId());
		query2.executeUpdate();

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id", elminarConvo.getConvocatoriaId());
		query.executeUpdate();

	}

	@Transactional
	@Override
	public void eliminarPlanesProgramas(ConvocatoriaParamNueva convocatoriaParamNueva, int idConvocatoria) {

		String consulta2 = " DELETE FROM rel_convocatoria_planesyprogramas WHERE id_convocatoria = :idConvocatoria "
				+ "AND id_nivel_ensenanza = :idNivelEnsenanza AND id_plan = :idPlan AND id_programa = :idPrograma";

		for (ConvocatoriaNivelEducativoCompl planProgramas : convocatoriaParamNueva.getListaPlanProgramaEliminar()) {

			Query query2 = entityManager.createNativeQuery(consulta2);
			query2.setParameter("idConvocatoria", idConvocatoria);
			query2.setParameter("idNivelEnsenanza", planProgramas.getIdNivelEnsenanza());
			query2.setParameter("idPlan", planProgramas.getIdPlan());
			query2.setParameter("idPrograma", planProgramas.getIdPrograma());
			query2.executeUpdate();

		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConvocatoriaDTO> obtenerConvocatoriasActivas() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("    tb.convocatoria_id, ");
		sql.append("    tb.nombre ");
		sql.append("FROM tbl_convocatoria tb ");
		sql.append("WHERE tb.activo = 1");

		List<Object[]> resultados = entityManager.createNativeQuery(sql.toString()).getResultList();

		return resultados.stream().map(this::mapearConvocatoria).collect(Collectors.toList());
	}

	private ConvocatoriaDTO mapearConvocatoria(Object[] row) {
		ConvocatoriaDTO dto = new ConvocatoriaDTO();
		dto.setId(getLongValue(row[0]));
		dto.setNombre((String) row[1]);
		return dto;
	}

	private Long getLongValue(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Number) {
			return ((Number) value).longValue();
		}
		return null;
	}

}
