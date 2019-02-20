package br.tr.com.Utils;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class ControlaVariaveisSecao {

    /**
     *
     * @param p_variable
     * @return
     */
    public static Object getSessionVariable(String p_variable) {
        Object v_object_variavel = null;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            v_object_variavel = session.getAttribute(p_variable);
        }
        return v_object_variavel;
    }

    /**
     * 
     * @param p_variable
     * @param p_value 
     */
    public static void setSessionVariable(String p_variable, Object p_value) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.setAttribute(p_variable, p_value);
        }
    }
}
