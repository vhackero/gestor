package mx.gob.sedesol.gestorweb.commons.utils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatObjetoCurricular;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;

@Service("objCurricularConverter")
public class ObjCurricularConverter implements Converter {
	
	@Autowired
	private CatalogoComunService<CatObjetoCurricular, Integer> objetoCurricularService;


	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String valor) {
		if(!ObjectUtils.isNullOrEmpty(valor)){
			return objetoCurricularService.buscarPorId(Integer.parseInt(valor), CatObjetoCurricular.class);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object valor) {
		
		if(!ObjectUtils.isNullOrEmpty(valor)){
			return valor.toString();
		}
		return null;
	}
}
