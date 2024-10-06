package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaNivelEducativo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaParamConsulta;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaTableroResumen;

@Repository
public class ConvocatoriaRepository implements IConvocatoriaRepository {

	@Autowired
	public EntityManager entityManager;

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
		
		String consulta = "SELECT DISTINCT(tb.convocatoria_id),tb.nombre,tb.nombre_corto,tb.descripcion,tb.fecha_apertura,tb.fecha_cierre, tb.semestre,tb.tipo,tb.url_convocatoria,tb.activo,tb.fecha_alta,tb.fecha_modificacion,tb.cupo_limite \r\n"
				+ "FROM tbl_convocatoria tb\r\n"
				+ "         INNER JOIN rel_convocatoria_planesyprogramas rcpp ON rcpp.id_convocatoria = tb.convocatoria_id\r\n"
				+ "         INNER JOIN cat_nivel_ensenanza_programa cne ON cne.id = rcpp.id_nivel_ensenanza ";
		
		String queryFiltro = obtieneFiltro(convocatoriaParamConsulta);
		
		String es = " AND cne.activo = 1";
		
		Query query = entityManager.createNativeQuery(consulta.concat(queryFiltro).concat(es));

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
		
		if (!("0").equals(convocatoriaParamConsulta.getValueConvocatoriaEstatus())) {
			filtro.append(validaOperador(isPrimerFiltro)+"tb.activo = ").append(convocatoriaParamConsulta.getValueConvocatoriaEstatus()).append(" ");
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
		
		if (!filter.getConsulFechaApertura().trim().equals("") && !filter.getConsulFechaCierre().trim().equals("")) {
			filtro.append(validaOperador(isPrimerFiltro)+"tb.fecha_apertura BETWEEN '")
			.append(filter.getConsulFechaApertura()).append("' AND '").append(filter.getConsulFechaApertura()).append("'");
		}
		
		if (!filter.getConsulFechaApertura().trim().equals("") && !filter.getConsulFechaCierre().trim().equals("")) {
			filtro.append(validaOperador(isPrimerFiltro)+"tb.fecha_cierre BETWEEN '")
			.append(filter.getConsulFechaCierre()).append("' AND '").append(filter.getConsulFechaCierre()).append("'");
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
		regresa.setActivo( obj[9].toString());
		regresa.setFechaAlta((java.util.Date) obj[10]);
		regresa.setFechaModificacion((java.util.Date) obj[11]);
		regresa.setCupoLimite((Integer) obj[12]);

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
	
	private ConvocatoriaNivelEducativo mapeoNivel(Object[] obj) {

		ConvocatoriaNivelEducativo regresa = new ConvocatoriaNivelEducativo();

		regresa.setId((Integer) obj[0]);
		regresa.setNombre(obj[1].toString());

		return regresa;
	}
	
	
	@Override
	public void altaConvocatorias() {

		List<ConvocatoriaNivelEducativo> lista = new ArrayList<ConvocatoriaNivelEducativo>();

		String consulta = "SELECT cne.id, cne.nombre FROM cat_nivel_ensenanza_programa cne WHERE cne.activo = 1";

		Query query = entityManager.createNativeQuery(consulta);

		 query.getResultList();

		
		

	}
	
	@Transactional
	@Override
	public void eliminarConvocatorias(Convocatoria elminarConvo) {

		List<ConvocatoriaNivelEducativo> lista = new ArrayList<ConvocatoriaNivelEducativo>();

		String consulta = "DELETE FROM tbl_convocatoria WHERE convocatoria_id = :id";
		String consulta2 = "DELETE FROM rel_convocatoria_planesyprogramas WHERE id_convocatoria = :id";

		Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("id", elminarConvo.getConvocatoriaId());
        query.executeUpdate();
        
        Query query2 = entityManager.createNativeQuery(consulta);
        query2.setParameter("id", elminarConvo.getConvocatoriaId());
        query2.executeUpdate();

        
		query.getResultList();

		
		

	}

}
