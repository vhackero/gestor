package mx.gob.sedesol.gestorweb.commons.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.service.admin.TiposCorreoService;

//@FacesConverter("tipoCorreoConverter")
@Service("tipoCorreoConverter")
public class TipoCorreoConverter implements Converter {
	
	@Autowired
	private TiposCorreoService tipoCorreoService;
	
//	public TipoCorreoConverter() {
//		ApplicationContext context = new ClassPathXmlApplicationContext(ConstantesGestorWeb.CONFIG_PATH);
//		tipoCorreoService = context.getBean(TiposCorreoService.class);
//	}
	
	
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String valor) {
		if(!ObjectUtils.isNullOrEmpty(valor)){
			return tipoCorreoService.buscarPorId(Integer.parseInt(valor));
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object valor) {
		
//		if(!ObjectUtils.isNullOrEmpty(valor)){
//			TipoCorreoDTO tpoCorreo = (TipoCorreoDTO) valor;
//			
//			return tpoCorreo.getIdTipoCorreo().toString(); 
//		}
		
		if(!ObjectUtils.isNullOrEmpty(valor)){
			return valor.toString();
		}
		
		return null;
	}

	/**
	 * @return the tipoCorreoService
	 */
	public TiposCorreoService getTipoCorreoService() {
		return tipoCorreoService;
	}

	/**
	 * @param tipoCorreoService the tipoCorreoService to set
	 */
	public void setTipoCorreoService(TiposCorreoService tipoCorreoService) {
		this.tipoCorreoService = tipoCorreoService;
	}

}
