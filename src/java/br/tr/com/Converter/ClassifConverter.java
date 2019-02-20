package br.tr.com.Converter;

import br.tr.com.Modal.TrClassificacao;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "classifConverter") 
public class ClassifConverter implements Converter {  
  
   @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (TrClassificacao) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof TrClassificacao) {
            TrClassificacao trClassif = (TrClassificacao) value;
            if (trClassif != null && trClassif instanceof TrClassificacao && trClassif.getDescricao()!= null) {
                uiComponent.getAttributes().put(trClassif.getDescricao(), trClassif);
                return trClassif.getDescricao();
            }
        }
        return "";
    }
    
}
