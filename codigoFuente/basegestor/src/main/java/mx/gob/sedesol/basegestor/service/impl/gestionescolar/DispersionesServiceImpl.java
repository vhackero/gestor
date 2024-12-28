package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Dispersiones;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ProcesosInscripcion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoMatriculacion;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.IDispersionesRepository;
import mx.gob.sedesol.basegestor.service.gestionescolar.DispersionesService;

@Service("dispersionesService")
public class DispersionesServiceImpl implements DispersionesService{
	
	
	@Autowired
	private IDispersionesRepository iDispersionesRepository;
	
	
	
	@Override
	public List<ProcesosInscripcion> consultarProcesoInscripcion() {
		
		List<ProcesosInscripcion> lista = iDispersionesRepository.consultarProcesoInscripcion();
		
		if (lista.isEmpty()) {
			return new ArrayList<ProcesosInscripcion>();
		}
		return lista;
	}

	
	
	@Override
	public List<TipoMatriculacion> consultarTipoMatriculacion() {
		
		List<TipoMatriculacion> lista = iDispersionesRepository.consultarTipoMatriculacion();
		
		if (lista.isEmpty()) {
			return new ArrayList<TipoMatriculacion>();
		}
		return lista;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public List<Dispersiones> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dispersiones buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultadoDTO<Dispersiones> guardar(Dispersiones dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultadoDTO<Dispersiones> actualizar(Dispersiones dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultadoDTO<Dispersiones> eliminar(Dispersiones dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultadoDTO<Dispersiones> sonDatosRequeridosValidos(TipoAccion accion, Dispersiones dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
