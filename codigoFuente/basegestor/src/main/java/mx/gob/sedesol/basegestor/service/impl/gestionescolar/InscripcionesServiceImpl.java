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
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaParamConsulta;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaTableroResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoProceso;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.IConvocatoriaRepository;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.IinscripcionesRepository;
import mx.gob.sedesol.basegestor.service.gestionescolar.ConvocatoriaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionesService;

/**
 * 
 * 
 * @author ITTIVA
 * 
 */
@Service("inscripcionesService")
public class InscripcionesServiceImpl implements InscripcionesService {

	private static final Logger logger = Logger.getLogger(InscripcionesServiceImpl.class);
	
	@Autowired
	private IConvocatoriaRepository iConvocatoriaRepository;
	
	@Autowired
	private IinscripcionesRepository iinscripcionesRepository;
	
	
	
	
	@Override
	public void altaConvocatorias() {
		
		iConvocatoriaRepository.altaConvocatorias();		
		
	}

	
	
	@Override
	public List<TipoProceso> consultarTipoProceso() {
		
		List<TipoProceso> lista = iinscripcionesRepository.consultarTipoProceso();
		
		if (lista.isEmpty()) {
			return new ArrayList<TipoProceso>();
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
