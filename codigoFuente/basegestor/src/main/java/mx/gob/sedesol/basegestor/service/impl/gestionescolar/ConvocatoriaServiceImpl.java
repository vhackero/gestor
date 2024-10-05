package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaNivelEducativo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaTableroResumen;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.IConvocatoriaRepository;
import mx.gob.sedesol.basegestor.service.gestionescolar.ConvocatoriaService;

/**
 * 
 * 
 * @author ITTIVA
 * 
 */
@Service("convocatoriaService")
public class ConvocatoriaServiceImpl implements ConvocatoriaService {

	private static final Logger logger = Logger.getLogger(ConvocatoriaServiceImpl.class);
	
	@Autowired
	private IConvocatoriaRepository iConvocatoriaRepository;
	
	
	@Override
	public void altaConvocatorias() {
		
		iConvocatoriaRepository.altaConvocatorias();		
		
	}
	
	
	@Override
	public List<Convocatoria> consultarConvocatorias() {
		
		List<Convocatoria> lista = iConvocatoriaRepository.consultarConvocatorias();
		
		if (lista.isEmpty()) {
			return new ArrayList<Convocatoria>();
		}
		return lista;
	}
	
	@Override
	public List<Convocatoria> consultarConvocatoriasFiltros() {
		
		List<Convocatoria> lista = iConvocatoriaRepository.consultarConvocatoriasFiltros();
		
		if (lista.isEmpty()) {
			return new ArrayList<Convocatoria>();
		}
		return lista;
	}
	
	@Override
	public List<ConvocatoriaTableroResumen> consultarTableroResumen(Integer convocatoriaId) {
		
		List<ConvocatoriaTableroResumen> lista = iConvocatoriaRepository.consultarTableroResumen(convocatoriaId);
		
		if (lista.isEmpty()) {
			return new ArrayList<ConvocatoriaTableroResumen>();
		}
		return lista;
	}
	
	@Override
	public List<ConvocatoriaNivelEducativo> consultarNivelEducativo() {
		
		List<ConvocatoriaNivelEducativo> lista = iConvocatoriaRepository.consultarNivelEducativo();
		
		if (lista.isEmpty()) {
			return new ArrayList<ConvocatoriaNivelEducativo>();
		}
		return lista;
	}


	@Override
	public List<Convocatoria> findAll() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Convocatoria buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResultadoDTO<Convocatoria> guardar(Convocatoria dto) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResultadoDTO<Convocatoria> actualizar(Convocatoria dto) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResultadoDTO<Convocatoria> eliminar(Convocatoria dto) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResultadoDTO<Convocatoria> sonDatosRequeridosValidos(TipoAccion accion, Convocatoria dto) {
		// TODO Auto-generated method stub
		return null;
	}



}
