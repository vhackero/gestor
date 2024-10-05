package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaNivelEducativo;
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
	public List<Convocatoria> consultarConvocatoriasFiltros() {

		List<Convocatoria> lista = new ArrayList<Convocatoria>();

		String consulta = "SELECT tb.convocatoria_id,tb.nombre,tb.nombre_corto,tb.descripcion,tb.fecha_apertura,tb.fecha_cierre,\r\n"
				+ "tb.semestre,tb.tipo,tb.url_convocatoria,tb.activo,tb.fecha_alta,tb.fecha_modificacion,tb.cupo_limite FROM tbl_convocatoria tb WHERE tb.activo = 1";

		Query query = entityManager.createNativeQuery(consulta);

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				Convocatoria convocatoria = mapeo2(obj);
				lista.add(convocatoria);

			}
		}

		return lista;

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

}
