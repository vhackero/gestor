package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoGeneralDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.DatoValorCatGeneralDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.RelCatalogoGeneralValor;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblCatalogoGeneral;
import mx.gob.sedesol.basegestor.model.repositories.admin.CatalogosGeneralesRepo;
import mx.gob.sedesol.basegestor.service.admin.CatalogosGeneralesService;

@Service("catalogosGeneralesService")
public class CatalogosGeneralesServiceImpl implements CatalogosGeneralesService {
	
	private static final Logger logger = Logger.getLogger(CatalogosGeneralesServiceImpl.class);
	
	@Autowired
	private CatalogosGeneralesRepo catalogosGeneralesRepo;
	private ModelMapper catGralMapper = new ModelMapper();
	
	@Override
	public List<CatalogoGeneralDTO> findAll() {
		Type lstAux = new TypeToken<List<CatalogoGeneralDTO>>(){}.getType();
		return catGralMapper.map(catalogosGeneralesRepo.findAll(), lstAux);
	}

	@Override
	public CatalogoGeneralDTO buscarPorId(Integer id) {
		return catGralMapper.map(catalogosGeneralesRepo.findOne(id), CatalogoGeneralDTO.class);
	}
	
	/**
	 * 
	 * @param cveCat
	 * @return
	 */
	@Override
	public CatalogoGeneralDTO obtenerCatalogoPorCveCat(String cveCat){
		
		logger.debug("Busqueda por clave de catalogo");
		
		if(ObjectUtils.isNull(cveCat))
			return null;
		
		TblCatalogoGeneral aux = catalogosGeneralesRepo.buscarPorClaveCatalogo(cveCat);
		CatalogoGeneralDTO catGral = new CatalogoGeneralDTO();
		catGralMapper.map(aux,catGral);
		return catGral;
		
	}
	
	@Override
	public List<DatoValorCatGeneralDTO> obtenerValoresPorCveCatalogo(String cveCat){
		
		logger.debug("Busqueda por clave de catalogo");
		
		if(ObjectUtils.isNull(cveCat))
			return new ArrayList<>();
		
		List<RelCatalogoGeneralValor> aux = catalogosGeneralesRepo.obtenerValoresPorCveCatalogo(cveCat);
		Type lstAux = new TypeToken<List<DatoValorCatGeneralDTO>>(){}.getType();
		
		return catGralMapper.map(aux,lstAux);
	}

}
