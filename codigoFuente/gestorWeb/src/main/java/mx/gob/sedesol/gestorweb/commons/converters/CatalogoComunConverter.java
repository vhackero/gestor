package mx.gob.sedesol.gestorweb.commons.converters;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

@FacesConverter(value="catalogoComunConverter")
//@Service(value="catalogoComunConverter")
public class CatalogoComunConverter implements Converter {
	
	private final String seleccione ="--Seleccione--";
	private final String seleccionar ="Seleccionar";

	@SuppressWarnings("unchecked")
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if(!value.equalsIgnoreCase(seleccione) && !value.equalsIgnoreCase(seleccionar) ){
			
			List<UIComponent> children = component.getChildren();
			for(UIComponent childItems : children){
				if(childItems instanceof UISelectItems){
					UISelectItems items = (UISelectItems) childItems;
					//List<UIComponent> itemsData = items.getChildren();
					List<CatalogoComunDTO> values = (List<CatalogoComunDTO>)items.getValue();
					if(ObjectUtils.isNotNull(values)){
						for(CatalogoComunDTO item : values){
							if(item.getId().equals(Integer.parseInt(value)))
								return item;
						}
					}
				}
			}
			
		}
		return null;
	}
	
	
  	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		CatalogoComunDTO cat = null;
		
		if(value != null){
			cat = (CatalogoComunDTO)value;
			if(cat != null && cat.getId() != null)
				return cat.getId().toString();
		}
		
		return null;
	}
	
  

}
