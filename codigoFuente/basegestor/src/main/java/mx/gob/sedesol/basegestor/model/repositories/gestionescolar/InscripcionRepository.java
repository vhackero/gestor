package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionBajasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionInsertDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasInsDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasPasadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMaxMinDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.IntentosAsignaturasDTO;

@Repository
public class InscripcionRepository implements IinscripcionRepository {

	@Autowired
	public EntityManager entityManager;

	@Override
	public List<InscripcionDTO> consultarTipoProceso(String idPersona) {

		List<InscripcionDTO> lista = new ArrayList<InscripcionDTO>();

		String consulta = "SELECT\r\n"
				+ "    tp.id_persona id_persona,\r\n"
				+ "    tp.sso_idUsuario nombre_usuario,\r\n"
				+ "    tp.sso_nombre nombre,\r\n"
				+ "    tp.sso_apellidoMaterno primer_apellido,\r\n"
				+ "    tp.sso_apellidoPaterno segundo_apellido,\r\n"
				+ "    rpc.sso_correoElectronico correo,\r\n"
				+ "    tpa.id_plan id_plan,\r\n"
				+ "    tpl.nombre plan,\r\n"
				+ "    tfd.nombre_tentativo programa,\r\n"
				+ "    tp.sso_idUsuario,\r\n"
				+ "    tpa.id_convocatoria\r\n"
				+ "FROM tbl_persona  tp\r\n"
				+ "         INNER JOIN rel_persona_correo rpc ON rpc.id_persona = tp.id_persona\r\n"
				+ "         INNER JOIN tbl_persona_aspirante tpa ON tpa.id_persona = rpc.id_persona\r\n"
				+ "         INNER JOIN tbl_planes tpl ON tpl.id_plan = tpa.id_plan\r\n"
				+ "         INNER JOIN tbl_ficha_descriptiva_programa tfd ON tfd.id_plan = tpl.id_plan\r\n"
				+ "        INNER JOIN tbl_convocatoria tc ON tc.convocatoria_id = tpa.id_convocatoria AND  tc.activo=1\r\n"
				+ "WHERE tp.id_persona = :id_persona";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_persona", idPersona);


		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				InscripcionDTO convocatoria = mapeo(obj);
				lista.add(convocatoria);

			}
		}

		return lista;

	}
	
	@Override
	public List<InscripcionMateriasDTO> consultarMaterias(String id_plan) {

		List<InscripcionMateriasDTO> lista = new ArrayList<InscripcionMateriasDTO>();

		String consulta = "SELECT tp.identificador clave_plan, tfd.identificador_final clave_progrma, tp.id_plan id_plan, tp.nombre nombre_plan, tfd.id_programa id_programa ,tfd.nombre_tentativo programa,\r\n"
				+ "       tmc.nombre subestructura,\r\n"
				+ "       (SELECT tmc2.nombre FROM tbl_malla_curricular tmc2 WHERE tmc2.id = tmc.id_padre ) estructura,\r\n"
				+ "       cne.nombre nivel_ensenanza,\r\n"
				+ "       cdp.nombre division,\r\n"
				+ "       tfd.tipo tipo_programa,\r\n"
				+ "       tfd.id_programa_antecedente seriada\r\n"
				+ "FROM tbl_planes tp\r\n"
				+ "         INNER JOIN tbl_ficha_descriptiva_programa tfd ON tfd.id_plan = tp.id_plan\r\n"
				+ "         INNER JOIN tbl_malla_curricular tmc ON tmc.id = tfd.id_eje_capacitacion\r\n"
				+ "         INNER JOIN cat_nivel_ensenanza_programa cne ON cne.id = tp.id_nivel_ensenanza\r\n"
				+ "         INNER JOIN cat_divisiones_plan cdp ON cdp.id = tp.id_divisiones_plan\r\n"
				+ "WHERE tp.id_plan = :id_plan AND tfd.identificador_final is not null AND tfd.identificador_final != ''";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_plan", id_plan);


		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				InscripcionMateriasDTO convocatoria = mapeoMaterias(obj);
				lista.add(convocatoria);

			}
		}

		return lista;

	}
	
	@Override
	public List<InscripcionMateriasDTO> consultarMateriasPorConvocatoria(String id_plan, String id_convocatoria, String id_estructura) {

		List<InscripcionMateriasDTO> lista = new ArrayList<InscripcionMateriasDTO>();

		String consulta = "SELECT tp.identificador clave_plan, tfd.identificador_final clave_progrma, tp.id_plan id_plan, tp.nombre nombre_plan, tfd.id_programa id_programa ,tfd.nombre_tentativo programa,\r\n"
				+ "       tmc.nombre subestructura,\r\n"
				+ "       (SELECT tmc2.nombre FROM tbl_malla_curricular tmc2 WHERE tmc2.id = tmc.id_padre ) estructura,\r\n"
				+ "       cne.nombre nivel_ensenanza,\r\n"
				+ "       cdp.nombre division,\r\n"
				+ "       tfd.tipo tipo_programa,\r\n"
				+ "       tfd.id_programa_antecedente seriada\r\n"
				+ "FROM tbl_planes tp\r\n"
				+ "         INNER JOIN tbl_ficha_descriptiva_programa tfd ON tfd.id_plan = tp.id_plan\r\n"
				+ "         INNER JOIN tbl_malla_curricular tmc ON tmc.id = tfd.id_eje_capacitacion\r\n"
				+ "         INNER JOIN cat_nivel_ensenanza_programa cne ON cne.id = tp.id_nivel_ensenanza\r\n"
				+ "         INNER JOIN cat_divisiones_plan cdp ON cdp.id = tp.id_divisiones_plan\r\n"
				+ "         INNER JOIN rel_convocatoria_planesyprogramas rcp ON tp.id_plan = rcp.id_plan AND tfd.id_programa = rcp.id_programa\r\n"
				+ "WHERE tp.id_plan = :id_plan AND rcp.id_convocatoria = :id_convocatoria AND EXISTS\r\n"
				+ "    (SELECT tmc3.nombre FROM tbl_malla_curricular tmc3 WHERE tmc3.id = tmc.id_padre AND SUBSTRING(tmc3.nombre, -1) IN (:id_estructura)) AND\r\n"
				+ "      tfd.identificador_final is not null AND tfd.identificador_final != ''";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_plan", id_plan);
		query.setParameter("id_convocatoria", id_convocatoria);
		List<String> estructuras = Arrays.asList(id_estructura.split(","));
		query.setParameter("id_estructura", estructuras);
		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				InscripcionMateriasDTO convocatoria = mapeoMaterias(obj);
				lista.add(convocatoria);

			}
		}

		return lista;

	}
	
	@Override
	public Long consultarNumeroEstructura(String id_plan_infopersona) {

		String consulta = "SELECT rmp.id_plan plan,\r\n"
				+ "       rmp.elementos_estructuras num_estructuras\r\n"
				+ "    From rel_malla_plan rmp\r\n"
				+ "WHERE rmp.id_plan=:id_plan_infopersona and rmp.activo=1;";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_plan_infopersona", id_plan_infopersona);


		List<Object[]> listaQuery = query.getResultList();
		
		Long numeroEstructura = 0l;
		
		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {
                     
				numeroEstructura = Long.valueOf(obj[1].toString());     

			}
		}

		return numeroEstructura;

	}

	@Override
	public InscripcionMaxMinDTO consultarMaxMin(String id_plan) {

		InscripcionMaxMinDTO lista = new InscripcionMaxMinDTO();

		String consulta = "SELECT * FROM tbl_max_min_planes tm WHERE tm.id_plan = :id_plan";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_plan", id_plan);


		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				InscripcionMaxMinDTO convocatoria = mapeoMaxMin(obj);
				lista = convocatoria;

			}
		}

		return lista;

	}
	
	@Override
	public List<InscripcionMateriasPasadasDTO> consultarMateriasCursadas(String id_persona) {

		List<InscripcionMateriasPasadasDTO> lista = new ArrayList<InscripcionMateriasPasadasDTO>();

		String consulta = "SELECT\r\n"
				+ "    tpl.nombre plan,\r\n"
				+ "    tpl.id_plan id_plan,\r\n"
				+ "    fd.nombre_tentativo programa,\r\n"
				+ "    fd.id_programa id_programa,\r\n"
				+ "    tmc.nombre subestructura,\r\n"
				+ "    (SELECT tmc2.nombre FROM tbl_malla_curricular tmc2 WHERE tmc2.id = tmc.id_padre ) estructura,\r\n"
				+ "    rgp.calificacion_final calificacion_final,\r\n"
				+ "    if(rgp.calificacion_final >= 60, 1,0) estatus_aprobacion\r\n"
				+ "FROM tbl_persona tp\r\n"
				+ "         INNER JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = tp.id_persona AND calificacion_final IS NOT NULL\r\n"
				+ "         INNER JOIN tbl_grupos tg ON tg.id = rgp.id_grupo\r\n"
				+ "         INNER JOIN tbl_eventos te ON te.id_evento = tg.id_evento\r\n"
				+ "         INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = te.id_programa\r\n"
				+ "         INNER JOIN tbl_planes tpl ON tpl.id_plan = fd.id_plan\r\n"
				+ "         INNER JOIN tbl_malla_curricular tmc ON tmc.id = fd.id_eje_capacitacion\r\n"
				+ "WHERE tp.id_persona = :id_persona";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_persona", id_persona);

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				InscripcionMateriasPasadasDTO convocatoria = mapeoMateriasPasadas(obj);
				lista.add(convocatoria);

			}
		}

		return lista;

	}
	
	@Override
	public List<InscripcionMateriasInsDTO> consultarMateriasInscritas(String idpersona,String plan) {

		List<InscripcionMateriasInsDTO> lista = new ArrayList<InscripcionMateriasInsDTO>();

		
		String consulta = "SELECT ti.* FROM tbl_inscripciones ti\r\n"
				+ "             INNER JOIN tbl_procesos_inscripcion tpi ON ti.fecha_registro >= tpi.fecha_inicio AND ti.fecha_registro <= tpi.fecha_fin\r\n"
				+ "             INNER JOIN rel_proceso_inscipcion_planesyprogramas rgp ON rgp.id_programa = ti.idprograma ANd rgp.id_plan = ti.idplan AND tpi.proceso_inscripcion_id =  rgp.id_proceso_inscripcion\r\n"
				+ "             WHERE ti.Idpersona = :idpersona  AND ti.idplan = :plan AND ti.semestre = tpi.semestre";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("idpersona", idpersona);
		query.setParameter("plan", plan);


		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				InscripcionMateriasInsDTO convocatoria = mapeoInscripcionMateria(obj);
				lista.add(convocatoria);

			}
		}

		return lista;

	}
	
	@Override
	public List<IntentosAsignaturasDTO> consultarIntentosAsignaturas(String id_persona) {

		List<IntentosAsignaturasDTO> lista = new ArrayList<IntentosAsignaturasDTO>();

		
		String consulta = "SELECT COUNT(if(rgp.calificacion_final < 60, 1,0)) as intentos_reprobados,\r\n"
				+ "    tpl.id_plan id_plan,\r\n"
				+ "    fd.id_programa id_programa\r\n"
				+ "FROM tbl_persona tp\r\n"
				+ "         INNER JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = tp.id_persona AND calificacion_final IS NOT NULL\r\n"
				+ "         INNER JOIN tbl_grupos tg ON tg.id = rgp.id_grupo\r\n"
				+ "         INNER JOIN tbl_eventos te ON te.id_evento = tg.id_evento\r\n"
				+ "         INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = te.id_programa\r\n"
				+ "         INNER JOIN tbl_planes tpl ON tpl.id_plan = fd.id_plan\r\n"
				+ "         INNER JOIN tbl_malla_curricular tmc ON tmc.id = fd.id_eje_capacitacion\r\n"
				+ "WHERE tp.id_persona = :id_persona and rgp.calificacion_final < 60 group by te.id_programa";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_persona", id_persona);


		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				IntentosAsignaturasDTO convocatoria = mapeoIntentosAsignaturas(obj);
				lista.add(convocatoria);

			}
		}

		return lista;

	}
	
	@Override
	public Boolean consultarNuevoIngreso(String id_persona) {

		String consulta = "SELECT * from tbl_persona tp\r\n"
				+ "WHERE NOT EXISTS(SELECT * FROM tbl_inscripciones ti WHERE ti.semestre=1 AND ti.Idpersona=tp.id_persona) AND id_persona=:id_persona\r\n"
				+ "  AND NOT EXISTS(SELECT * from rel_persona_bajas rpb WHERE rpb.id_persona = tp.id_persona)";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_persona", id_persona);


		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			return true;
		}

		return false;

	}
	
	@Override
	public List<InscripcionBajasDTO> consultarBajas(String id_persona) {

		List<InscripcionBajasDTO> lista = new ArrayList<InscripcionBajasDTO>();

		
		String consulta = "SELECT rpb.id_plan, rpb.id_programa, ctb.id_tipo_baja, ctb.nombre tipo_baja, ctb.fecha_modificacion FROM rel_persona_bajas rpb\r\n"
				+ "         INNER JOIN rel_motivo_baja rmb ON rmb.id_motivo_baja = rpb.motivo_baja_id\r\n"
				+ "         INNER JOIN cat_tipo_bajas ctb ON ctb.id_tipo_baja = rmb.tipo_baja_id\r\n"
				+ "         WHERE rpb.id_persona = :id_persona";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_persona", id_persona);


		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				InscripcionBajasDTO convocatoria = mapeoPersonaBaja(obj);
				lista.add(convocatoria);

			}
		}

		return lista;

	}
	
	@Override
	@Transactional
	public void insertarRegistro(InscripcionInsertDTO dto) {
	    String sql = "INSERT INTO tbl_inscripciones (Idpersona, programa, asignatura, groupbase, idplan, idprograma, " +
	                 "idevento, nivel, division, profile_field_perfil, bloque, clave_asig, nuevoingreso, " +
	                 "recursamiento, alta, semestre, fecha_registro) " +
	                 "VALUES ( :Idpersona, :programa, :asignatura, :groupbase, :idplan, :idprograma, :idevento, " +
	                 ":nivel, :division, :profileFieldPerfil, :bloque, :claveAsig, :nuevoIngreso, :recursamiento, " +
	                 ":alta, :semestre, :fechaRegistro)";

	    entityManager.createNativeQuery(sql)
	            .setParameter("Idpersona", dto.getIdPersona())
	            .setParameter("programa", dto.getPrograma())
	            .setParameter("asignatura", dto.getAsignatura())
	            .setParameter("groupbase", dto.getGroupBase())
	            .setParameter("idplan", dto.getIdPlan())
	            .setParameter("idprograma", dto.getIdPrograma())
	            .setParameter("idevento", dto.getIdEvento())
	            .setParameter("nivel", dto.getNivel())
	            .setParameter("division", dto.getDivision())
	            .setParameter("profileFieldPerfil", dto.getProfileFieldPerfil())
	            .setParameter("bloque", dto.getBloque())
	            .setParameter("claveAsig", dto.getClaveAsig())
	            .setParameter("nuevoIngreso", dto.getNuevoIngreso())
	            .setParameter("recursamiento", dto.getRecursamiento())
	            .setParameter("alta", dto.getAlta())
	            .setParameter("semestre", dto.getSemestre())
	            .setParameter("fechaRegistro", dto.getFechaRegistro())
	            .executeUpdate();
	}
	
	private InscripcionMateriasInsDTO mapeoInscripcionMateria(Object[] obj) {
		InscripcionMateriasInsDTO materia = new InscripcionMateriasInsDTO();

	    materia.setId(Integer.parseInt(obj[0].toString()));        
	    materia.setIdPersona(obj[1] != null ? Long.valueOf(obj[1].toString()) : null);
	    materia.setPrograma(obj[2] != null ? obj[2].toString() : null);  
	    materia.setAsignatura(obj[3].toString());                          
	    materia.setGroupBase(obj[4].toString());                          
	    materia.setIdPlan(Integer.parseInt(obj[5].toString()));        
	    materia.setIdPrograma(Integer.parseInt(obj[6].toString()));        
	    materia.setIdEvento(Integer.parseInt(obj[7].toString()));        
	    materia.setNivel(obj[8].toString());                          
	    materia.setDivision(obj[9].toString());                          
	    materia.setPerfil(obj[10].toString());                              
	    materia.setBloque(obj[11].toString());                             
	    materia.setClaveAsignatura(obj[12].toString());                   
	    materia.setNuevoIngreso(Integer.parseInt(obj[13].toString()));    
	    materia.setRecursamiento(Integer.parseInt(obj[14].toString()));     
	    materia.setAlta(Integer.parseInt(obj[15].toString()));              
	    materia.setSemestre(Integer.parseInt(obj[16].toString()));        
	    materia.setFechaRegistro(obj[17] != null ? obj[17].toString() : null);  

	    return materia;
	}

	
	private InscripcionMateriasPasadasDTO mapeoMateriasPasadas(Object[] obj) {
		InscripcionMateriasPasadasDTO programa = new InscripcionMateriasPasadasDTO();

	    programa.setPlan(obj[0].toString());                         
	    programa.setIdPlan(Long.valueOf(obj[1].toString()));        
	    programa.setPrograma(obj[2].toString());                     
	    programa.setIdPrograma(Long.valueOf(obj[3].toString()));    
	    programa.setSubestructura(obj[4].toString());                
	    programa.setEstructura(obj[5].toString());                   
	    programa.setCalificacionFinal(Double.valueOf(obj[6].toString()));
	    programa.setEstatusAprobacion(Integer.valueOf(obj[7].toString())); 

	    return programa;
	}
	
	private InscripcionMaxMinDTO mapeoMaxMin(Object[] obj) {
		InscripcionMaxMinDTO regresa = new InscripcionMaxMinDTO();

	    regresa.setIdMaxMin(Long.valueOf(obj[0].toString()));          
	    regresa.setPlan(obj[1].toString());                              
	    regresa.setIdPlan(Long.valueOf(obj[2].toString()));             
	    regresa.setMinimo(obj[3].toString());                            
	    regresa.setMaximoRegular(obj[4].toString());                     
	    regresa.setMaximoIrregular(obj[5].toString());                  

	    return regresa;
	}
	
	private InscripcionDTO mapeo(Object[] obj) {

	    InscripcionDTO regresa = new InscripcionDTO();

	    regresa.setIdPersona(Long.valueOf(obj[0].toString()));
	    regresa.setNombreUsuario(obj[1].toString());
	    regresa.setNombre(obj[2].toString());
	    regresa.setPrimerApellido(obj[3].toString());
	    regresa.setSegundoApellido(obj[4].toString());
	    regresa.setCorreo(obj[5].toString());
	    regresa.setIdPlan(Long.valueOf(obj[6].toString()));
	    regresa.setPlan(obj[7].toString());
	    regresa.setPrograma(obj[8].toString());
	    regresa.setIdConvocatoria(Long.valueOf(obj[10].toString()));

	    return regresa;
	}

	
	private InscripcionMateriasDTO mapeoMaterias(Object[] obj) {

		InscripcionMateriasDTO programa = new InscripcionMateriasDTO();

	    programa.setClavePlan(obj[0].toString());                   
	    programa.setClavePrograma(obj[1].toString());               
	    programa.setIdPlan(Long.valueOf(obj[2].toString()));         
	    programa.setNombrePlan(obj[3].toString());                  
	    programa.setIdPrograma(Long.valueOf(obj[4].toString()));     
	    programa.setNombreTentativoPrograma(obj[5].toString());      
	    programa.setSubestructura(obj[6].toString());                
	    programa.setEstructura(obj[7].toString());                   
	    programa.setNivelEnsenanza(obj[8].toString());               
	    programa.setDivision(obj[9].toString());                     
	    programa.setTipoPrograma(obj[10].toString());                
	    programa.setIdProgramaAntecedente(obj[11] != null ? Long.valueOf(obj[11].toString()) : null); 
	    programa.setCheck(false);


	    return programa;
	}
	
	private IntentosAsignaturasDTO mapeoIntentosAsignaturas(Object[] obj) {

		IntentosAsignaturasDTO programa = new IntentosAsignaturasDTO();

	    programa.setIntetosReprobados(Long.valueOf(obj[0].toString()));                   
	    programa.setIdPlan(Long.valueOf(obj[1].toString()));     
	    programa.setIdPrograma(Long.valueOf(obj[2].toString()));               

	    return programa;
	}
	
	
	private InscripcionBajasDTO mapeoPersonaBaja(Object[] obj) {

		InscripcionBajasDTO personaBaja = new InscripcionBajasDTO();

	    personaBaja.setIdPlan(Long.valueOf(obj[0].toString()));
	    personaBaja.setIdPrograma(Long.valueOf(obj[1].toString()));
	    personaBaja.setIdTipoBaja(Long.valueOf(obj[2].toString()));
	    personaBaja.setTipoBaja(obj[3].toString());
	    personaBaja.setFechaModificacion(LocalDateTime.parse(obj[4].toString()));

	    return personaBaja;
	}
	
	
	


}
