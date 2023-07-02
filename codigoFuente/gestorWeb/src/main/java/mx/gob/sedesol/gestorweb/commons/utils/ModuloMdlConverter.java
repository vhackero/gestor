package mx.gob.sedesol.gestorweb.commons.utils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.service.planesyprogramas.ModuloMoodleService;

@Service("moduloMdlConverter")
public class ModuloMdlConverter implements Converter {

	private ModuloMoodleService moduloMoodleService;
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(!ObjectUtils.isNullOrEmpty(value)){
			return moduloMoodleService.buscarPorId(Integer.parseInt(value));
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if(!ObjectUtils.isNullOrEmpty(value)){
			return value.toString();
		}
		return null;
	}

}
