package mx.gob.sedesol.gestorweb.commons.validator;

import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.validation.MessageInterpolator;
import javax.validation.Validation;

import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

public class GestorWebMessageInterpolator implements MessageInterpolator {

	private MessageInterpolator defaultInterpolator = Validation.byDefaultProvider().configure()
			.getDefaultMessageInterpolator();

	@Override
	public String interpolate(String message, Context context) {
		SistemaBean sistema = getSistemaInstance();
		String textoSistema = sistema.obtenerTexto(message);
		return defaultInterpolator.interpolate(textoSistema, context);
	}

	@Override
	public String interpolate(String message, Context context, Locale locale) {
		SistemaBean sistema = getSistemaInstance();
		String textoSistema = sistema.obtenerTexto(message);
		return defaultInterpolator.interpolate(textoSistema, context, locale);
	}
	
	private SistemaBean getSistemaInstance(){
		FacesContext fcontext = FacesContext.getCurrentInstance();
		SistemaBean sistema = fcontext.getApplication().evaluateExpressionGet(fcontext, "#{sistema}",
				SistemaBean.class);
		return sistema;
	}

}
