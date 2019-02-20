package br.tr.com.Converter;

import br.tr.com.Modal.TrSessao;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "secaoConverter") 
public class SecaoConverter implements Converter {  
  
  @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (TrSessao) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof TrSessao) {
            TrSessao trSessao = (TrSessao) value;
            if (trSessao != null && trSessao instanceof TrSessao && trSessao.getNomeSessao()!= null) {
                uiComponent.getAttributes().put(trSessao.getNomeSessao(), trSessao);
                return trSessao.getNomeSessao();
            }
        }
        return "";
    }
}
