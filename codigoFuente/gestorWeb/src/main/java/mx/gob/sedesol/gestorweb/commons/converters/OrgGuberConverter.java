package mx.gob.sedesol.gestorweb.commons.converters;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import mx.gob.sedesol.basegestor.commons.dto.admin.OrgGubernamentalDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

@FacesConverter(value="orgGuberConverter")
public class OrgGuberConverter implements Converter {
	
	private final String seleccione ="--Seleccione--";

	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if(!value.equalsIgnoreCase(seleccione)){
			
			List<UIComponent> children = component.getChildren();
			for(UIComponent childItems : children){
				if(childItems instanceof UISelectItems){
					UISelectItems items = (UISelectItems) childItems;
					List<SelectItemGroup> groups = (List<SelectItemGroup>)items.getValue();
					
					if(ObjectUtils.isNotNull(groups)){
						for(SelectItemGroup group : groups){
							for(SelectItem item : group.getSelectItems()){
								OrgGubernamentalDTO orgGub = (OrgGubernamentalDTO)item.getValue();
								if(orgGub.getId().toString().equalsIgnoreCase(value)){
									return orgGub;
								}
							}
						}
					}
					//List<UIComponent> itemsData = items.getChildren();
//					List<OrgGubernamentalDTO> values = (List<OrgGubernamentalDTO>)items.getValue();
//					if(ObjectUtils.isNotNull(values)){
//						for(OrgGubernamentalDTO item : values){
//							if(item.getId().equals(Integer.parseInt(value)))
//								return item;
//						}
//					}
				}
			}
			
		}
		return null;
	}
	
	
  	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		OrgGubernamentalDTO cat = null;
		
		if(value != null){
			cat = (OrgGubernamentalDTO)value;
			return cat.getId().toString();
		}
		
		return null;
	}

}
