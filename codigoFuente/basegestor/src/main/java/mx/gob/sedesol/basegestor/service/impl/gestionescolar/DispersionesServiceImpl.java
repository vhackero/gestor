package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Dispersiones;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParam;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParamNuevo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionPlanesProgramas;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ProcesosInscripcion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoMatriculacion;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblDispersionesBusqueda;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.IDispersionesRepository;
import mx.gob.sedesol.basegestor.service.gestionescolar.DispersionesService;

@Service("dispersionesService")
public class DispersionesServiceImpl implements DispersionesService {

	@Autowired
	private IDispersionesRepository iDispersionesRepository;

	@Override
	public void altaDisperciones(DispersionesParam dispercionParametros) {

		iDispersionesRepository.altaDisperciones(dispercionParametros);

	}

	@Override
	public List<TblDispersionesBusqueda> consultaDisperciones(DispersionesParam dispercionParametros) {

		List<TblDispersionesBusqueda> lista = iDispersionesRepository.consultaDisperciones(dispercionParametros);

		if (lista.isEmpty()) {
			return lista;
		}
		return lista;

	}

	@Override
	public void borrarDispercsion(TblDispersionesBusqueda tblBusqueda) {
		iDispersionesRepository.borrarDispercsion(tblBusqueda);
	}

	@Override
	public boolean existeRelDispersionGrupo(Integer idDispersion) {
		return iDispersionesRepository.existeRelDispersionGrupo(idDispersion);
	}

	@Override
	public List<TblDispersionesBusqueda> actualizarDispersion(DispersionesParamNuevo dispercionParametros) {

		List<TblDispersionesBusqueda> lista = iDispersionesRepository.actualizarDispersion(dispercionParametros);

		if (lista.isEmpty()) {
			return lista;
		}
		return lista;

	}
	
	@Override
	public boolean actualizarDispersionExc(DispersionesParamNuevo dispercionParametros){
		 return iDispersionesRepository.actualizarDispersionExc(dispercionParametros);

	}

	@Override
	public boolean validarDispercionExistente(DispersionesParam dispercionParametros) {
		return iDispersionesRepository.validarDispercionExistente(dispercionParametros);
	}

	@Override
	public boolean validarDispercionExistenteOrdinario(DispersionesParam dispercionParametros) {
		return iDispersionesRepository.validarDispercionExistenteOrdinario(dispercionParametros);
	}

	@Override
	public List<ProcesosInscripcion> consultarProcesoInscripcion(DispersionesParam dispercionParametros) {

		List<ProcesosInscripcion> lista = iDispersionesRepository.consultarProcesoInscripcion(dispercionParametros);

		if (lista.isEmpty()) {
			return new ArrayList<ProcesosInscripcion>();
		}
		return lista;
	}

	@Override
	public List<TblPlan> consultarPlan(DispersionesParam dispercionParametros) {

		List<TblPlan> lista = iDispersionesRepository.consultarPlan(dispercionParametros);

		if (lista.isEmpty()) {
			return new ArrayList<TblPlan>();
		}
		return lista;
	}

	@Override
	public List<TblFichaDescriptivaPrograma> consultarPrograma(DispersionesParam dispercionParametros) {
		List<TblFichaDescriptivaPrograma> lista = iDispersionesRepository.consultarPrograma(dispercionParametros);

		if (lista.isEmpty()) {
			return new ArrayList<TblFichaDescriptivaPrograma>();
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
	public List<InscripcionPlanesProgramas> consultarPlanesProgramas(DispersionesParam dispercionParametros) {
		List<InscripcionPlanesProgramas> lista = iDispersionesRepository.consultarPlanesProgramas(dispercionParametros);
		if (lista.isEmpty()) {
			return new ArrayList<InscripcionPlanesProgramas>();
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
