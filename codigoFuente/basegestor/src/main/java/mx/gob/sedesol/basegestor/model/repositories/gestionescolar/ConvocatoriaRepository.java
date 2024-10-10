package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.math.BigInteger;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	
	
	String query1 = "INSERT INTO tbl_convocatoria\r\n"
			+ "(\r\n"
			+ "nombre,nombre_corto,descripcion,fecha_apertura,fecha_cierre,semestre,tipo,url_convocatoria,activo,fecha_alta,fecha_modificacion,cupo_limite)\r\n"
			+ "VALUES\r\n";
	
	String query2 = "SELECT MAX(convocatoria_id) FROM des_sisi_gestor.tbl_convocatoria";
	
	String query3 = "INSERT INTO rel_convocatoria_planesyprogramas\r\n"
			+ "(id_convocatoria,id_nivel_ensenanza,id_plan,id_programa,fecha_modificacion)\r\n"
			+ "VALUES ";

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
				+ "WHERE tpa.id_convocatoria = :convocatoriaId \r\n"
				+ "GROUP BY tpa.id_plan";

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
		
		
		if (!("").equals(convocatoriaParamConsulta.getConsulNombreConvocatoria())) {
			filtro.append(validaOperador(isPrimerFiltro)+"tb.nombre = '").append(convocatoriaParamConsulta.getConsulNombreConvocatoria()).append("'").append(" ");
			isPrimerFiltro = true;
		}
		
		if (!("").equals(convocatoriaParamConsulta.getConsulNombreCorto())) {
			filtro.append(validaOperador(isPrimerFiltro)+"tb.nombre_corto = '").append(convocatoriaParamConsulta.getConsulNombreCorto()).append("'").append(" ");
			isPrimerFiltro = true;
		}
		
		if (!("").equals(convocatoriaParamConsulta.getValueConvocatoriaEstatus())) {
			filtro.append(validaOperador(isPrimerFiltro)+"tb.activo = ").append(convocatoriaParamConsulta.getValueConvocatoriaEstatus()).append(" ");
			isPrimerFiltro = true;
		}else {
			filtro.append(validaOperador(isPrimerFiltro)+"tb.activo = ").append("1").append(" ");
			isPrimerFiltro = true;
		}
		
		if (!("0").equals(convocatoriaParamConsulta.getConsulNivelEducativo())) {
			filtro.append(validaOperador(isPrimerFiltro)+"rcpp.id_nivel_ensenanza = ").append(convocatoriaParamConsulta.getConsulNivelEducativo()).append(" ");
			isPrimerFiltro = true;
		}
				
		obtieneFiltroFechas(filtro, convocatoriaParamConsulta, isPrimerFiltro);
		
		return filtro.toString();
		
	}
	
	private StringBuilder obtieneFiltroFechas(StringBuilder filtro, ConvocatoriaParamConsulta filter, boolean isPrimerFiltro) {
		
		if (filter.getConsulFechaApertura() != null && filter.getConsulFechaCierre() != null) {
			
			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
			ZonedDateTime zonedDateTime = ZonedDateTime.parse(filter.getConsulFechaApertura().toString(), inputFormatter);
			DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formattedDate = zonedDateTime.format(outputFormatter);
			
			filtro.append(validaOperador(isPrimerFiltro)+"tb.fecha_apertura >= '").append( formattedDate ).append("' ");
			
//			filtro.append(validaOperador(isPrimerFiltro)+"tb.fecha_apertura BETWEEN '")
//			.append(formattedDate).append("' AND '").append(formattedDate).append("'");
		}
		
		if (filter.getConsulFechaApertura()!= null && filter.getConsulFechaCierre() != null) {
			
			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
			ZonedDateTime zonedDateTime = ZonedDateTime.parse(filter.getConsulFechaCierre().toString(), inputFormatter);
			DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formattedDate = zonedDateTime.format(outputFormatter);
			
			filtro.append(validaOperador(isPrimerFiltro)+"tb.fecha_cierre >= '").append( formattedDate ).append("' ");
			
//			filtro.append(validaOperador(isPrimerFiltro)+"tb.fecha_cierre BETWEEN '")
//			.append(formattedDate).append("' AND '").append(formattedDate).append("'");
		}

		
		return filtro;
		
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
		regresa.setTipo( obj[7].toString());
		regresa.setUrlConvocatoria(obj[8].toString());
		
		if( "1".equals(obj[9].toString()) ) {
			regresa.setActivo( "ACTIVO ");
		} else if( "0".equals(obj[9].toString()) ) {
			regresa.setActivo( "INACTIVO" );
		}		
		
		regresa.setFechaAlta((java.util.Date) obj[10]);
		regresa.setFechaModificacion((java.util.Date) obj[11]);
		regresa.setCupoLimite((Integer) obj[12]);
		regresa.setNombreNivel( obj[13].toString());

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

		String consulta = " SELECT cnp.id id_nivel_ensenanza, cnp.nombre nivel_ensenaza, tp.id_plan id_plan, tp.nombre plan, fdp.id_programa id_programa, fdp.nombre_tentativo programa\r\n"
				+ "FROM tbl_planes tp\r\n"
				+ "         INNER JOIN tbl_ficha_descriptiva_programa fdp ON fdp.id_plan = tp.id_plan AND fdp.identificador_final IS not NULL AND fdp.identificador_final != ''\r\n"
				+ "         INNER JOIN cat_nivel_ensenanza_programa cnp ON fdp.id_nivel_programa = cnp.id\r\n"
				+ "         INNER JOIN tbl_malla_curricular mc ON mc.id_plan = tp.id_plan AND mc.activo =1";

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
	public void altaConvocatorias(ConvocatoriaParamNueva convocatoriaParamNueva) {

		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		
		ZonedDateTime zonedDateTime = ZonedDateTime.parse(convocatoriaParamNueva.getAltaFechaApertura().toString(), inputFormatter);
		ZonedDateTime zonedDateTime2 = ZonedDateTime.parse(convocatoriaParamNueva.getAltaFechaCierre().toString(), inputFormatter);
		ZonedDateTime zonedDateTime3 = ZonedDateTime.parse(convocatoriaParamNueva.getAltaFechaAlta().toString(), inputFormatter);
		
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		String fecha1 = zonedDateTime.format(outputFormatter);
		String fecha2 = zonedDateTime2.format(outputFormatter);
		String fecha3 = zonedDateTime3.format(outputFormatter);
		
		String sql2 = " ( "
				+ " '"+ convocatoriaParamNueva.getAltaNombreConvocatoria() + "' ,"
				+ " '"+ convocatoriaParamNueva.getAltaNombreCorto() + "' ,"
				+ " '"+ convocatoriaParamNueva.getAltaDescripcion() + "' ,"
				+ " '"+ fecha1 + "' ,"
				+ " '"+ fecha2 + "' ,"
				+ " "+ 1 + " ,"
				+ " "+ 0 + " ,"
				+ " '"+ convocatoriaParamNueva.getAltaUrl() + "' ,"
				+ " "+ convocatoriaParamNueva.getAltaEstatus() + " ,"
				+ " '"+ fecha3 + "' ,"
				+ " '"+ fecha3 + "' ,"
				+ " "+ convocatoriaParamNueva.getAltaCupoLimite() + " "				
				+ ")"; 
		
		Query query = entityManager.createNativeQuery(query1.concat(sql2));

		int filasAfectadas = query.executeUpdate();
		
		if(filasAfectadas >= 0) {
			
			Query query02 = entityManager.createNativeQuery(query2);
			
			int id = (Integer) query02.getSingleResult();
			
			if(id >= 0) {
				
				Pattern pattern = Pattern.compile("id\\w+=(\\d+)");
		        Matcher matcher = pattern.matcher(convocatoriaParamNueva.getAltaNivelEducativo());
		        
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
		        
		        String query32 = "("+id+"," + idNivelEnsenanza +"," + idPlan +"," + idPrograma +",'"+ fecha3 +"')";
				
				Query query03 = entityManager.createNativeQuery(query3.concat(query32));
			
				int filasAfectadas2 = query03.executeUpdate();
				
				
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

}
