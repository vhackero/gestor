package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaNivelEducativo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaNivelEducativoCompl;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaParamConsulta;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaTableroResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionParamNueva;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionPlanesProgramas;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionesConsultaResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionesTableroResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.PlanesProgramas;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoProceso;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;

@Repository
public class InscripcionesRepository implements IinscripcionesRepository {

	private static final int TIPO_PROCESO_ORDINARIO = 1;
	private static final int TIPO_PROCESO_EXTRAORDINARIO = 2;
	private static final int LONGITUD_DEFAULT_PERFIL = 10;
	private static final Pattern PLAN_PROGRAMA_PATTERN = Pattern.compile("id\\w+=(\\d+)");

	@Autowired
	public EntityManager entityManager;

	private Integer longitudMaximaPerfilCache;

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
	public List<TipoProceso> consultarTipoProcesoDisponibles(Integer convocatoriaId, LocalDateTime fechaActual) {
		List<TipoProceso> tipos = consultarTipoProceso();
		if (convocatoriaId == null) {
			return tipos;
		}

		int totalOrdinarios = contarProcesosPorTipo(convocatoriaId, TIPO_PROCESO_ORDINARIO);
		LocalDateTime fechaReferencia = fechaActual != null ? fechaActual : LocalDateTime.now();

		if (totalOrdinarios <= 0) {
			return filtrarTiposPorId(tipos, TIPO_PROCESO_ORDINARIO);
		}

		LocalDateTime fechaFinOrdinario = obtenerFechaFinProcesoOrdinario(convocatoriaId);
		if (fechaFinOrdinario != null && fechaFinOrdinario.isAfter(fechaReferencia)) {
			return new ArrayList<>();
		}

		return filtrarTiposPorId(tipos, TIPO_PROCESO_EXTRAORDINARIO);
	}

	private List<TipoProceso> filtrarTiposPorId(List<TipoProceso> tipos, int tipoId) {
		List<TipoProceso> filtrados = new ArrayList<>();
		if (tipos == null) {
			return filtrados;
		}
		for (TipoProceso tipo : tipos) {
			if (tipo != null && tipo.getIdProceso() != null && tipo.getIdProceso().intValue() == tipoId) {
				filtrados.add(tipo);
			}
		}
		return filtrados;
	}

	@Override
	public int contarProcesosPorTipo(Integer convocatoriaId, int tipoProcesoId) {
		if (convocatoriaId == null) {
			return 0;
		}

		String sql = "SELECT COUNT(*) FROM tbl_procesos_inscripcion WHERE convocatoria_id = :convocatoriaId AND id_tipo_proceso = :tipoProceso";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("convocatoriaId", convocatoriaId);
		query.setParameter("tipoProceso", tipoProcesoId);

		Object resultado = query.getSingleResult();
		return resultado != null ? ((Number) resultado).intValue() : 0;
	}

	@Override
	public LocalDateTime obtenerFechaFinProcesoOrdinario(Integer convocatoriaId) {
		if (convocatoriaId == null) {
			return null;
		}

		String sql = "SELECT MAX(fecha_fin) FROM tbl_procesos_inscripcion WHERE convocatoria_id = :convocatoriaId AND id_tipo_proceso = :tipoProceso";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("convocatoriaId", convocatoriaId);
		query.setParameter("tipoProceso", TIPO_PROCESO_ORDINARIO);
		Object resultado = query.getSingleResult();

		if (resultado == null) {
			return null;
		}

		if (resultado instanceof Timestamp) {
			return ((Timestamp) resultado).toLocalDateTime();
		} else if (resultado instanceof Date) {
			return ((Date) resultado).toLocalDate().atStartOfDay();
		} else if (resultado instanceof String) {
			return Timestamp.valueOf(((String) resultado).replace("T", " ")).toLocalDateTime();
		}

		return null;
	}

	@Override
	public Integer obtenerSiguienteConsecutivoProceso() {
		String sql = "SELECT IFNULL(MAX(proceso_inscripcion_id),0) + 1 FROM tbl_procesos_inscripcion";
		Query query = entityManager.createNativeQuery(sql);
		Object resultado = query.getSingleResult();
		return resultado != null ? ((Number) resultado).intValue() : 1;
	}

	@Override
	public boolean existePlanProgramaExtraordinario(Integer convocatoriaId, int idPlan, int idPrograma,
			LocalDateTime fechaActual) {
		if (convocatoriaId == null) {
			return false;
		}

		String sql = "SELECT COUNT(*) "
				+ "FROM rel_proceso_inscipcion_planesyprogramas r "
				+ "INNER JOIN tbl_procesos_inscripcion p ON p.proceso_inscripcion_id = r.id_proceso_inscripcion "
				+ "WHERE p.convocatoria_id = :convocatoriaId "
				+ "AND p.id_tipo_proceso = :tipoProceso "
				+ "AND r.id_plan = :idPlan "
				+ "AND r.id_programa = :idPrograma "
				+ "AND p.fecha_fin >= :fechaActual";

		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("convocatoriaId", convocatoriaId);
		query.setParameter("tipoProceso", TIPO_PROCESO_EXTRAORDINARIO);
		query.setParameter("idPlan", idPlan);
		query.setParameter("idPrograma", idPrograma);
		LocalDateTime referencia = fechaActual != null ? fechaActual : LocalDateTime.now();
		query.setParameter("fechaActual", Timestamp.valueOf(referencia));

		Object resultado = query.getSingleResult();
		return resultado != null && ((Number) resultado).intValue() > 0;
	}

	private String prepararClaveProceso(InscripcionParamNueva inscripcionParamNueva, int consecutivo) {
		String clave = inscripcionParamNueva.getCalveProceso();
		if (clave == null || clave.trim().isEmpty()) {
			clave = generarClaveProcesoInterno(inscripcionParamNueva.getNombre(), consecutivo);
		}
		return clave.trim().toUpperCase();
	}

	private String generarClaveProcesoInterno(String nombre, int consecutivo) {
		String base = nombre != null ? nombre.trim().toUpperCase() : "";
		String[] partes = base.isEmpty() ? new String[0] : base.split("\\s+");

		String primera = partes.length > 0 ? abreviarPalabra(partes[0]) : "PRC";
		String segunda = partes.length > 1 ? abreviarPalabra(partes[1]) : "GEN";
		String anio = String.valueOf(LocalDate.now().getYear());

		return String.format("%s-%s-%s-%d", primera, segunda, anio, consecutivo);
	}

	private String abreviarPalabra(String palabra) {
		if (palabra == null || palabra.isEmpty()) {
			return "XXX";
		}
		String normalizada = palabra.replaceAll("[^A-Z0-9]", "").toUpperCase();
		if (normalizada.isEmpty()) {
			normalizada = palabra.toUpperCase();
		}
		return normalizada.length() <= 3 ? normalizada : normalizada.substring(0, 3);
	}

	private String prepararPerfil(String perfil) {
		if (perfil == null) {
			return null;
		}
		String limpio = perfil.trim();
		if (limpio.isEmpty()) {
			return null;
		}
		int longitudMaxima = obtenerLongitudMaximaPerfil();
		if (limpio.length() <= longitudMaxima) {
			return limpio;
		}
		return limpio.substring(0, longitudMaxima);
	}

	private int obtenerLongitudMaximaPerfil() {
		if (longitudMaximaPerfilCache != null && longitudMaximaPerfilCache > 0) {
			return longitudMaximaPerfilCache;
		}
		try {
			Query query = entityManager.createNativeQuery(
					"SELECT CHARACTER_MAXIMUM_LENGTH FROM information_schema.columns "
							+ "WHERE table_schema = DATABASE() AND table_name = 'tbl_procesos_inscripcion' "
							+ "AND column_name = 'perfil'");
			Object resultado = query.getSingleResult();
				if (resultado instanceof Number) {
					longitudMaximaPerfilCache = ((Number) resultado).intValue();
				} else if (resultado != null) {
					longitudMaximaPerfilCache = Integer.parseInt(resultado.toString());
				}
		} catch (Exception e) {
			System.out.println(
					"[InscripcionesRepository] No fue posible obtener la longitud del campo perfil, se usará el valor por defecto "
							+ LONGITUD_DEFAULT_PERFIL + ". Detalle: " + e.getMessage());
			longitudMaximaPerfilCache = LONGITUD_DEFAULT_PERFIL;
		}
			if (longitudMaximaPerfilCache == null || longitudMaximaPerfilCache <= 0) {
				longitudMaximaPerfilCache = LONGITUD_DEFAULT_PERFIL;
			} else if (longitudMaximaPerfilCache > 0) {
				longitudMaximaPerfilCache = Math.max(1, longitudMaximaPerfilCache);
			}
			return longitudMaximaPerfilCache;
		}

	private int[] obtenerIdsPlanPrograma(Object elemento) {
		int[] valores = new int[] { -1, -1 };

		if (elemento instanceof InscripcionPlanesProgramas) {
			InscripcionPlanesProgramas ipp = (InscripcionPlanesProgramas) elemento;
			if (ipp.getIdPlan() != null) {
				valores[0] = ipp.getIdPlan();
			}
			if (ipp.getIdPrograma() != null) {
				valores[1] = ipp.getIdPrograma();
			}
			return valores;
		}

		String texto = elemento != null ? elemento.toString() : "";
		Matcher matcher = PLAN_PROGRAMA_PATTERN.matcher(texto);
		int index = 0;
		while (matcher.find() && index < valores.length) {
			valores[index] = Integer.parseInt(matcher.group(1));
			index++;
		}
		return valores;
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

		String consulta = "SELECT tc.nombre convocatoria ,tp.nombre plan,fd.id_programa, fd.nombre_tentativo programa, fd.identificador_final clave, (SELECT tmc2.nombre FROM tbl_malla_curricular tmc2 WHERE tmc2.id = tmc.id_padre) semestre, tmc.nombre bloque,  count(ti.id) no_estudiantes_inscritos FROM tbl_inscripciones ti\r\n"
				+ "                                                                                                                                                                                                                                                                INNER JOIN rel_proceso_inscipcion_planesyprogramas rpi ON rpi.id_programa = ti.idprograma AND rpi.id_plan = ti.idplan\r\n"
				+ "                                                                                                                                                                                                                                                                INNER JOIN tbl_procesos_inscripcion tpi ON tpi.proceso_inscripcion_id =  rpi.id_proceso_inscripcion\r\n"
				+ "                                                                                                                                                                                                                                                                INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = ti.idprograma and ti.idplan = fd.id_plan\r\n"
				+ "                                                                                                                                                                                                                                                                INNER JOIN tbl_planes tp ON tp.id_plan = ti.idplan\r\n"
				+ "                                                                                                                                                                                                                                                                INNER JOIN tbl_malla_curricular tmc ON tmc.id = fd.id_eje_capacitacion\r\n"
				+ "                                                                                                                                                                                                                                                                INNER JOIN tbl_convocatoria tc ON tc.convocatoria_id = tpi.convocatoria_id\r\n"
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

		List<InscripcionesConsultaResumen> lista = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		// Inicializar consulta base
		StringBuilder consulta = new StringBuilder("SELECT tp.proceso_inscripcion_id, \n" + "tp.convocatoria_id, \n"
				+ "tc.nombre convocatoria, \n" + "tp.nombre nombre, \n" + "tp.fecha_inicio, \n" + "tp.fecha_fin, \n"
				+ "cp.nombre tipo_proceso, \n" + "IF(tp.estatus = 0, 'Inactivo', 'Activo') estatus,\n"
				+ "tp.id_tipo_proceso\n" + "FROM tbl_procesos_inscripcion tp\n"
				+ "INNER JOIN tbl_convocatoria tc ON tc.convocatoria_id = tp.convocatoria_id\n"
				+ "INNER JOIN cat_procesos_inscripcion cp ON cp.id_proceso = tp.id_tipo_proceso\n"
				+ "WHERE tp.id_categoria_proceso = 1\n" + "AND tp.convocatoria_id = :id_convocatoria_selecionada ");

		// Variables auxiliares para agregar filtros opcionales
		boolean hasNombre = tableroParamConsulta.getConsulNombreCorto() != null
				&& !tableroParamConsulta.getConsulNombreCorto().isEmpty();
		boolean hasTipoProceso = tableroParamConsulta.getConsulNivelEducativo() != null;
		boolean hasFechaInicio = tableroParamConsulta.getConsulFechaApertura() != null;
		boolean hasFechaFin = tableroParamConsulta.getConsulFechaCierre() != null;

		// Añadir filtros opcionales
		if (hasNombre) {
			consulta.append("AND tp.nombre LIKE :nombre ");
		}
		if (hasTipoProceso) {
			consulta.append("AND tp.id_tipo_proceso = :id_tipo_proceso ");
		}
		if (hasFechaInicio && hasFechaFin) {
			consulta.append("AND tp.fecha_inicio >= :fecha_inicio AND tp.fecha_fin <= :fecha_fin ");
		}

		Query query = entityManager.createNativeQuery(consulta.toString());

		// Parámetro obligatorio
		query.setParameter("id_convocatoria_selecionada", tableroParamConsulta.getValueConvocatoriaEstatus());

		// Parámetros opcionales
		if (hasNombre) {
			query.setParameter("nombre", "%" + tableroParamConsulta.getConsulNombreCorto() + "%");
		}
		if (hasTipoProceso) {
			query.setParameter("id_tipo_proceso", tableroParamConsulta.getConsulNivelEducativo());
		}
		if (hasFechaInicio && hasFechaFin) {
			String fechaInicioStr = sdf.format(tableroParamConsulta.getConsulFechaApertura());
			String fechaFinStr = sdf.format(tableroParamConsulta.getConsulFechaCierre());
			query.setParameter("fecha_inicio", fechaInicioStr);
			query.setParameter("fecha_fin", fechaFinStr);
		}

		// Ejecutar consulta
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
		regresa.setIdTipoProceso(obj[8].toString());

		return regresa;
	}

	private InscripcionesTableroResumen tableroInscrip(Object[] obj) {

		InscripcionesTableroResumen regresa = new InscripcionesTableroResumen();

		regresa.setConvocatoria(obj[0].toString());
		regresa.setPlan(obj[1].toString());
		regresa.setIdPrograma(obj[2].toString());
		regresa.setPrograma(obj[3].toString());
		regresa.setClave(obj[4].toString());
		regresa.setSemestre(obj[5].toString());
		regresa.setBloque(obj[6].toString());
		regresa.setNoEstudiantesInscritos(obj[7].toString());

		return regresa;
	}

	private TblPlan mapeoTblPlan(Object[] obj) {

		TblPlan regresa = new TblPlan();

		regresa.setIdPlan((Integer) obj[0]);
		regresa.setNombre(obj[1].toString());

		return regresa;
	}

	private TipoProceso mapeo(Object[] obj) {

		TipoProceso regresa = new TipoProceso();

		regresa.setIdProceso((Integer) obj[0]);
		regresa.setNombre(obj[1].toString());

		return regresa;
	}

	@Override
	public Object getProcesoInscripcionById(Long procesoInscripcionId) {
		String query = "SELECT * FROM tbl_procesos_inscripcion WHERE proceso_inscripcion_id = :procesoInscripcionId";
		try {
			return entityManager.createNativeQuery(query).setParameter("procesoInscripcionId", procesoInscripcionId)
					.getSingleResult();
		} catch (NoResultException e) {
			return null; // No se encontró el registro
		}
	}

	// Método para actualizar los campos permitidos
	@Transactional
	@Override
	public void updateProcesoInscripcion(Long procesoInscripcionId, String nombre, LocalDateTime fechaInicio,
			LocalDateTime fechaFin, int estatus, Long idTipoProceso, Long convocatoriaId) {

		String query = "UPDATE tbl_procesos_inscripcion " + "SET nombre = ?, " + "fecha_inicio = ?, "
				+ "fecha_fin = ?, " + "estatus = ?, " + "id_tipo_proceso = ?, " + "convocatoria_id = ? "
				+ "WHERE proceso_inscripcion_id = ?";

		entityManager.createNativeQuery(query).setParameter(1, nombre).setParameter(2, fechaInicio)
				.setParameter(3, fechaFin).setParameter(4, estatus).setParameter(5, idTipoProceso)
				.setParameter(6, convocatoriaId).setParameter(7, procesoInscripcionId).executeUpdate();
	}

	private InscripcionPlanesProgramas mapeoNivelComp(Object[] obj) {

		InscripcionPlanesProgramas regresa = new InscripcionPlanesProgramas();

		Integer idPlan = Integer.parseInt(obj[1].toString());

		regresa.setIdPlan(idPlan);
		regresa.setNombrePlan(obj[2].toString());
		regresa.setIdPrograma((Integer) obj[3]);
		regresa.setNombrePrograma(obj[4].toString());

		return regresa;

	}

	@Override
	public List<InscripcionPlanesProgramas> consultarPlanPrograma(InscripcionParamNueva inscripcionParamNueva) {

		List<InscripcionPlanesProgramas> lista = new ArrayList<InscripcionPlanesProgramas>();

		String consulta = "SELECT DISTINCT tc.convocatoria_id, tp.id_plan pll, tp.nombre plan, tfd.id_programa, tfd.nombre_tentativo programa, (rcpp.id_plan), tp.nombre FROM des_sisi_gestor.tbl_convocatoria tc\r\n"
				+ "INNER JOIN des_sisi_gestor.rel_convocatoria_planesyprogramas rcpp ON rcpp.id_convocatoria = tc.convocatoria_id\r\n"
				+ "INNER JOIN des_sisi_gestor.tbl_ficha_descriptiva_programa tfd ON tfd.id_plan = rcpp.id_plan ANd tfd.id_programa = rcpp.id_programa\r\n"
				+ "INNER JOIN des_sisi_gestor.tbl_planes tp ON tp.id_plan = tfd.id_plan\r\n"
				+ "INNER JOIN des_sisi_gestor.tbl_malla_curricular tmc ON tmc.id_plan = tp.id_plan AND tmc.activo = 1\r\n"
				+ "WHERE tc.convocatoria_id = :idConvocatoria AND tp.id_plan = :idPlan";

		String consulta2 = "SELECT DISTINCT (rcpp.id_plan), tp.nombre plan\r\n"
				+ "FROM des_sisi_gestor.tbl_convocatoria tc\r\n"
				+ "         INNER JOIN des_sisi_gestor.rel_convocatoria_planesyprogramas rcpp ON rcpp.id_convocatoria = tc.convocatoria_id\r\n"
				+ "         INNER JOIN des_sisi_gestor.tbl_planes tp ON tp.id_plan = rcpp.id_plan\r\n"
				+ "         INNER JOIN des_sisi_gestor.tbl_malla_curricular tmc ON tmc.id_plan = tp.id_plan AND tmc.activo = 1\r\n"
				+ "WHERE des_sisi_gestor.tc.convocatoria_id = :convocatoriaId";

		if (inscripcionParamNueva.getConvocatoriaSeleccionada() != null) {
			Query query2 = entityManager.createNativeQuery(consulta2);
			query2.setParameter("convocatoriaId", inscripcionParamNueva.getConvocatoriaSeleccionada());

			Query query = entityManager.createNativeQuery(consulta);

			// query.setParameter("idPlan", inscripcionParamNueva.get);

			List<Object[]> listaQuery2 = query2.getResultList();

			if (!listaQuery2.isEmpty()) {
				for (Object[] obj : listaQuery2) {

					query.setParameter("idConvocatoria", inscripcionParamNueva.getConvocatoriaSeleccionada());
					query.setParameter("idPlan", obj[0]);
					List<Object[]> listaQuery = query.getResultList();
					if (!listaQuery.isEmpty()) {
						for (Object[] obj2 : listaQuery) {

							InscripcionPlanesProgramas convocatoria = mapeoNivelComp(obj2);
							lista.add(convocatoria);

						}
					}

				}
			}
		}

		return lista;
	}

	@Override
	@Transactional
	public void altaInscripcion(InscripcionParamNueva inscripcionParamNueva) {
		if (inscripcionParamNueva == null) {
			return;
		}

		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		ZonedDateTime zonedDateTime = ZonedDateTime.parse(inscripcionParamNueva.getFechaInicio().toString(),
				inputFormatter);
		ZonedDateTime zonedDateTime2 = ZonedDateTime.parse(inscripcionParamNueva.getFechaFin().toString(),
				inputFormatter);
		ZonedDateTime currentDateTime = ZonedDateTime.now();

		String fecha1 = zonedDateTime.format(outputFormatter);
		String fecha2 = zonedDateTime2.format(outputFormatter);
		String fecha3 = currentDateTime.format(outputFormatter);

		String valorConvocatoria = inscripcionParamNueva.getConvocatoriaSeleccionada();
		String valorTipoProceso = inscripcionParamNueva.getProcesoSeleccionada();
		String semestreValor = inscripcionParamNueva.getSemestre();

		Integer idConvocatoria = valorConvocatoria != null ? Integer.parseInt(valorConvocatoria) : null;
		Integer idTipoProceso = valorTipoProceso != null ? Integer.parseInt(valorTipoProceso) : null;
		Integer semestreNum = semestreValor != null ? Integer.parseInt(semestreValor) : 0;

		if (idConvocatoria == null || idTipoProceso == null) {
			inscripcionParamNueva.setInscripcionExistente(true);
			return;
		}

		if (idTipoProceso == TIPO_PROCESO_ORDINARIO
				&& contarProcesosPorTipo(idConvocatoria, TIPO_PROCESO_ORDINARIO) > 0) {
			inscripcionParamNueva.setInscripcionExistente(true);
			return;
		}

		int consecutivo = obtenerSiguienteConsecutivoProceso();
		String claveProceso = prepararClaveProceso(inscripcionParamNueva, consecutivo);
		inscripcionParamNueva.setCalveProceso(claveProceso);
		String perfilNormalizado = prepararPerfil(inscripcionParamNueva.getPerfil());
		inscripcionParamNueva.setPerfil(perfilNormalizado);
		if (perfilNormalizado != null) {
			System.out.println(
					"[InscripcionesRepository] Perfil normalizado (alta ordinaria) longitud=" + perfilNormalizado.length());
		} else {
			System.out.println("[InscripcionesRepository] Perfil normalizado (alta ordinaria) es null");
		}

		String consulta = "INSERT INTO tbl_procesos_inscripcion "
				+ "(nombre, clave_proceso, descripcion, fecha_inicio, fecha_fin, id_tipo_proceso, estatus, semestre, perfil, convocatoria_id, id_categoria_proceso) "
				+ "VALUES "
				+ "(:nombre, :claveProceso, :descripcion, :fechaInicio, :fechaFin, :idTipoProceso, :estatus, :semestre, :perfil, :convocatoriaId, :idCategoriaProceso)";

		String consultaRelConvocatoria = "select id, id_convocatoria, id_plan, id_programa from rel_convocatoria_planesyprogramas \r\n"
				+ "WHERE id_convocatoria = :idConvocatoria";

		String consultaRelacionProcesos = "INSERT INTO rel_proceso_inscipcion_planesyprogramas "
				+ "(id_proceso_inscripcion, id_plan, id_programa, fecha_modificacion) " + "VALUES "
				+ "(:idProceso, :idPlan, :idPrograma, :fchModificacion)";

		String consultaResumenBase = "SELECT\r\n" + "    rcp.id_convocatoria,\r\n" + "    tp.id_plan,\r\n"
				+ "    tfdp.id_programa,\r\n"
				+ "    CONCAT(substring_index(tp.identificador,'-',1),'-',tfdp.identificador_final,substr(tmc.nombre,1,1),substring_index(tmc.nombre,' ',-1),substr(tmc2.nombre,1,1),substring_index(tmc2.nombre,' ',-1),'-',CONCAT(DATE_FORMAT(tc.fecha_apertura, '%y'), LPAD(MONTH(tc.fecha_apertura), 2, '0')),'-',substr(tmc.nombre,1,1),substring_index(tmc.nombre,' ',-1),'-','000') grupo,\r\n"
				+ "    tp.nombre programa_educativa,\r\n" + "    tfdp.nombre_tentativo asignatura,\r\n"
				+ "    tfdp.identificador_final clave_asignatura,\r\n"
				+ "    substring_index(tmc2.nombre,' ',-1) semestre,\r\n"
				+ "    CONCAT(substring_index(tmc.nombre,' ',-1)) bloque,\r\n" + "    0 no_estudiantes,\r\n"
				+ "    0 no_grupos,\r\n" + "    0 estudiantes_x_grupo,\r\n" + "    0 grupo_resto,\r\n"
				+ "    0 estudiantes_resto\r\n" + "FROM rel_convocatoria_planesyprogramas rcp\r\n"
				+ "JOIN tbl_convocatoria tc ON rcp.id_convocatoria = tc.convocatoria_id\r\n"
				+ "JOIN tbl_planes tp ON  rcp.id_plan = tp.id_plan\r\n"
				+ "JOIN tbl_ficha_descriptiva_programa tfdp ON rcp.id_programa = tfdp.id_programa\r\n"
				+ "JOIN tbl_malla_curricular tmc ON tmc.id = tfdp.id_eje_capacitacion\r\n"
				+ "JOIN tbl_malla_curricular tmc2 ON tmc2.id = tmc.id_padre\r\n"
				+ "WHERE rcp.id_convocatoria = :idConv";

		String consultaInsertResumen = "INSERT INTO tbl_inscripcion_resumen "
				+ "(grupo, programa_educativo, asignatura, clave_asignatura, semestre, bloque, no_estudiantes,no_grupos,estudiantes_x_grupo, grupo_resto, estudiantes_resto, id_programa, id_plan, id_convocatoria ) "
				+ "VALUES "
				+ "(:grupo, :programaEducativo, :asignatura, :claveAsignatura, :semestre, :bloque, :numeroEstudiantes, :numeroGrupos, :estudiantesPorGrupo, :grupoResto, :estudiantesResto, :idPrograma, :idPlan, :idConvocatoria)";

		Query queryInsert = entityManager.createNativeQuery(consulta);
		queryInsert.setParameter("nombre", inscripcionParamNueva.getNombre());
		queryInsert.setParameter("claveProceso", claveProceso);
		queryInsert.setParameter("descripcion", inscripcionParamNueva.getDescripcion());
		queryInsert.setParameter("fechaInicio", fecha1);
		queryInsert.setParameter("fechaFin", fecha2);
		queryInsert.setParameter("idTipoProceso", idTipoProceso);
		queryInsert.setParameter("estatus", inscripcionParamNueva.getAltaEstatus());
		queryInsert.setParameter("semestre", semestreNum);
		queryInsert.setParameter("perfil", inscripcionParamNueva.getPerfil());
		queryInsert.setParameter("convocatoriaId", idConvocatoria);
		queryInsert.setParameter("idCategoriaProceso", 1);

		queryInsert.executeUpdate();
		inscripcionParamNueva.setInscripcionExistente(false);

		Query queryIdGenerado = entityManager.createNativeQuery("SELECT LAST_INSERT_ID()");
		Object idGenerado = queryIdGenerado.getSingleResult();

		Query queryRelConvocatoria = entityManager.createNativeQuery(consultaRelConvocatoria);
		queryRelConvocatoria.setParameter("idConvocatoria", idConvocatoria);

		List<Object[]> listaConvocatoria = queryRelConvocatoria.getResultList();
		for (Object[] row : listaConvocatoria) {
			Object idPlan = row[2];
			Object idPrograma = row[3];

			Query queryRelacion = entityManager.createNativeQuery(consultaRelacionProcesos);
			queryRelacion.setParameter("idProceso", idGenerado);
			queryRelacion.setParameter("idPlan", idPlan);
			queryRelacion.setParameter("idPrograma", idPrograma);
			queryRelacion.setParameter("fchModificacion", fecha3);
			queryRelacion.executeUpdate();
		}

		Query queryResumen = entityManager.createNativeQuery(consultaResumenBase);
		queryResumen.setParameter("idConv", idConvocatoria);

		List<Object[]> listaResumen = queryResumen.getResultList();
		for (Object[] row : listaResumen) {
			Query queryInsertResumen = entityManager.createNativeQuery(consultaInsertResumen);

			queryInsertResumen.setParameter("idPrograma", row[2]);
			queryInsertResumen.setParameter("idPlan", row[1]);
			queryInsertResumen.setParameter("idConvocatoria", row[0]);
			queryInsertResumen.setParameter("grupo", row[3]);
			queryInsertResumen.setParameter("programaEducativo", row[4]);
			queryInsertResumen.setParameter("asignatura", row[5]);
			queryInsertResumen.setParameter("claveAsignatura", row[6]);
			queryInsertResumen.setParameter("semestre", row[7]);
			queryInsertResumen.setParameter("bloque", row[8]);
			queryInsertResumen.setParameter("numeroEstudiantes", row[9]);
			queryInsertResumen.setParameter("numeroGrupos", row[10]);
			queryInsertResumen.setParameter("estudiantesPorGrupo", row[11]);
			queryInsertResumen.setParameter("grupoResto", row[12]);
			queryInsertResumen.setParameter("estudiantesResto", row[13]);
			queryInsertResumen.executeUpdate();
		}
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
	@Transactional
	public void altaInscripcionExtra(InscripcionParamNueva inscripcionParamNueva) {

		if (inscripcionParamNueva == null) {
			return;
		}

		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		ZonedDateTime zonedInicio = ZonedDateTime.parse(inscripcionParamNueva.getFechaInicio().toString(),
				inputFormatter);
		ZonedDateTime zonedFin = ZonedDateTime.parse(inscripcionParamNueva.getFechaFin().toString(), inputFormatter);
		ZonedDateTime zonedActual = ZonedDateTime.now();
		LocalDateTime fechaActual = zonedActual.toLocalDateTime();

		String fechaInicio = zonedInicio.format(outputFormatter);
		String fechaFin = zonedFin.format(outputFormatter);
		String fechaModificacion = zonedActual.format(outputFormatter);

		Integer idConvocatoria = inscripcionParamNueva.getConvocatoriaSeleccionada() != null
				? Integer.parseInt(inscripcionParamNueva.getConvocatoriaSeleccionada())
				: null;
		Integer semestreNum = inscripcionParamNueva.getSemestre() != null
				? Integer.parseInt(inscripcionParamNueva.getSemestre())
				: 0;

		if (idConvocatoria == null) {
			inscripcionParamNueva.setInscripcionOrdinaria(true);
			return;
		}

		if (contarProcesosPorTipo(idConvocatoria, TIPO_PROCESO_ORDINARIO) <= 0) {
			inscripcionParamNueva.setInscripcionOrdinaria(true);
			return;
		}

		LocalDateTime fechaFinOrdinario = obtenerFechaFinProcesoOrdinario(idConvocatoria);
		if (fechaFinOrdinario != null && fechaFinOrdinario.isAfter(fechaActual)) {
			inscripcionParamNueva.setFechaMayor(true);
			inscripcionParamNueva.setInscripcionOrdinaria(true);
			return;
		}

		List<?> planesProgramasSeleccionados = inscripcionParamNueva.getPlanesProgramas();
		if (planesProgramasSeleccionados == null || planesProgramasSeleccionados.isEmpty()) {
			inscripcionParamNueva.setPlanProgramaBoolean(true);
			return;
		}

		for (Object elemento : planesProgramasSeleccionados) {
			int[] ids = obtenerIdsPlanPrograma(elemento);
			if (ids[0] > 0 && ids[1] > 0
					&& existePlanProgramaExtraordinario(idConvocatoria, ids[0], ids[1], fechaActual)) {
				inscripcionParamNueva.setPlanProgramaBoolean(true);
				return;
			}
		}

		int consecutivo = obtenerSiguienteConsecutivoProceso();
		String claveProceso = prepararClaveProceso(inscripcionParamNueva, consecutivo);
		inscripcionParamNueva.setCalveProceso(claveProceso);
		String perfilNormalizado = prepararPerfil(inscripcionParamNueva.getPerfil());
		inscripcionParamNueva.setPerfil(perfilNormalizado);
		if (perfilNormalizado != null) {
			System.out.println(
					"[InscripcionesRepository] Perfil normalizado (alta extraordinaria) longitud=" + perfilNormalizado.length());
		} else {
			System.out.println("[InscripcionesRepository] Perfil normalizado (alta extraordinaria) es null");
		}

		String consultaInsertProceso = "INSERT INTO tbl_procesos_inscripcion "
				+ "(nombre, clave_proceso, descripcion, fecha_inicio, fecha_fin, id_tipo_proceso, estatus, semestre, perfil, convocatoria_id, id_categoria_proceso) "
				+ "VALUES "
				+ "(:nombre, :claveProceso, :descripcion, :fechaInicio, :fechaFin, :idTipoProceso, :estatus, :semestre, :perfil, :convocatoriaId, :idCategoriaProceso)";

		String consultaRelacion = "INSERT INTO rel_proceso_inscipcion_planesyprogramas "
				+ "(id_proceso_inscripcion, id_plan, id_programa, fecha_modificacion) "
				+ "VALUES (:idProceso, :idPlan, :idPrograma, :fchModificacion)";

		Query insertProceso = entityManager.createNativeQuery(consultaInsertProceso);
		insertProceso.setParameter("nombre", inscripcionParamNueva.getNombre());
		insertProceso.setParameter("claveProceso", claveProceso);
		insertProceso.setParameter("descripcion", inscripcionParamNueva.getDescripcion());
		insertProceso.setParameter("fechaInicio", fechaInicio);
		insertProceso.setParameter("fechaFin", fechaFin);
		insertProceso.setParameter("idTipoProceso", TIPO_PROCESO_EXTRAORDINARIO);
		insertProceso.setParameter("estatus", inscripcionParamNueva.getAltaEstatus());
		insertProceso.setParameter("semestre", semestreNum);
		insertProceso.setParameter("perfil", inscripcionParamNueva.getPerfil());
		insertProceso.setParameter("convocatoriaId", idConvocatoria);
		insertProceso.setParameter("idCategoriaProceso", 1);
		insertProceso.executeUpdate();

		Query queryIdGenerado = entityManager.createNativeQuery("SELECT LAST_INSERT_ID()");
		Object idGenerado = queryIdGenerado.getSingleResult();

		for (Object elemento : planesProgramasSeleccionados) {
			int[] ids = obtenerIdsPlanPrograma(elemento);
			if (ids[0] <= 0 || ids[1] <= 0) {
				continue;
			}
			Query insertRelacion = entityManager.createNativeQuery(consultaRelacion);
			insertRelacion.setParameter("idProceso", idGenerado);
			insertRelacion.setParameter("idPlan", ids[0]);
			insertRelacion.setParameter("idPrograma", ids[1]);
			insertRelacion.setParameter("fchModificacion", fechaModificacion);
			insertRelacion.executeUpdate();
		}

		inscripcionParamNueva.setInscripcionOrdinaria(false);
		inscripcionParamNueva.setPlanProgramaBoolean(false);
		inscripcionParamNueva.setFechaMayor(false);
	}
	
	@Transactional
	@Override
	public void deleteProcesoInscripcion(Long procesoInscripcionId, Long convocatoriaId, String tipoProceso) {
	    try {
	        // 1. Eliminar registros relacionados en tbl_terminosycondiciones
	        String deleteTerminosQuery = "DELETE FROM tbl_terminosycondiciones WHERE id_proceso_inscripcion = ?";
	        entityManager.createNativeQuery(deleteTerminosQuery)
	                     .setParameter(1, procesoInscripcionId)
	                     .executeUpdate();

	        // 2. Eliminar registros relacionados en tbl_encuestas_contestadas
	        String deleteEncuestasQuery = "DELETE FROM tbl_encuestas_contestadas WHERE id_proceso_inscripcion = ?";
	        entityManager.createNativeQuery(deleteEncuestasQuery)
	                     .setParameter(1, procesoInscripcionId)
	                     .executeUpdate();

	        // 3. Eliminar registros relacionados en tbl_dispersiones
	        String deleteDispersionesQuery = "DELETE FROM tbl_dispersiones WHERE id_proceso_inscripcion = ?";
	        entityManager.createNativeQuery(deleteDispersionesQuery)
	                     .setParameter(1, procesoInscripcionId)
	                     .executeUpdate();    
            //4. Eliminar registros en rel_proceso_inscipcion_planesyprogramas
            String deleteRelacionesQuery = "DELETE FROM rel_proceso_inscipcion_planesyprogramas WHERE id_proceso_inscripcion = ?";
            entityManager.createNativeQuery(deleteRelacionesQuery)
                                                 .setParameter(1, procesoInscripcionId)
                                                 .executeUpdate();
	        
	        if (tipoProceso.equalsIgnoreCase("Ordinario")) {
	        	
	            // 5. Eliminar registros en tbl_inscripcion_resumen
		        String deleteResumenQuery = "DELETE FROM tbl_inscripcion_resumen WHERE id_convocatoria = ?";
		        entityManager.createNativeQuery(deleteResumenQuery)
		                     .setParameter(1, convocatoriaId)
		                     .executeUpdate();

	        }
	        
	        // 6. Eliminar el registro principal en tbl_procesos_inscripcion
	        String deletePrincipalQuery = "DELETE FROM tbl_procesos_inscripcion WHERE proceso_inscripcion_id = ?";
	        entityManager.createNativeQuery(deletePrincipalQuery)
	                     .setParameter(1, procesoInscripcionId)
	                     .executeUpdate();
	    } catch (Exception e) {
	        throw new RuntimeException("Error al eliminar el proceso de inscripción y sus relaciones", e);
	    }
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
	public List<InscripcionesTableroResumen> altaInscripciones(InscripcionParamNueva inscripcionParamNueva) {
		// TODO Auto-generated method stub
		return null;
	}

}
