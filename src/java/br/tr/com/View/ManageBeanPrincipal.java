package br.tr.com.View;

import br.tr.com.Control.ControlCalendario;
import br.tr.com.Control.ControlNoticia;
import br.tr.com.Modal.TrCalendarioVersao;
import br.tr.com.Modal.TrNoticia;
import br.tr.com.Utils.ControlaVariaveisSecao;
import br.tr.com.Utils.Usuario;
import br.tr.com.Utils.Utils;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimaps;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "mbPrincipal")
@ViewScoped
public class ManageBeanPrincipal implements Serializable {
    
    private static final long serialVersionUID = 165135041090675743L;

    private Usuario _trUsuario;
    private ListMultimap<Date, TrCalendarioVersao> _trHashCalendario;
    private List<TrNoticia> _noticia;

    public ManageBeanPrincipal() throws IOException {

        this._trUsuario = (Usuario) ControlaVariaveisSecao.getSessionVariable("user");
        if (this._trUsuario == null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("trLogin.xhtml");
        }

        this._noticia = new ControlNoticia().listaNoticia();
        
        List<TrCalendarioVersao> trListCalendario = new ControlCalendario().listaCalendarioPrincipal();

        _trHashCalendario = Multimaps.newListMultimap(
                new TreeMap<Date, Collection<TrCalendarioVersao>>(), Lists::newArrayList);
        trListCalendario.stream().forEach((trCalendarioVersao) -> {
            _trHashCalendario.put(trCalendarioVersao.getData(), trCalendarioVersao);
        });

    }

    /**
     * 
     * @return 
     */
    public List<TrNoticia> getNoticia() {
        return _noticia;
    }

    /**
     * 
     * @param _noticia 
     */
    public void setNoticia(List<TrNoticia> _noticia) {
        this._noticia = _noticia;
    }
    
    /**
     * 
     * @return 
     */
    public Usuario getTrUsuario() {
        return _trUsuario;
    }

    /**
     * 
     * @param _trUsuario 
     */
    public void setTrUsuario(Usuario _trUsuario) {
        this._trUsuario = _trUsuario;
    }

    /**
     * 
     * @return 
     */
    public Map<Date, Collection<TrCalendarioVersao>> getTrHashCalendario() {
        return _trHashCalendario.asMap();
    }

    /**
     * 
     * @param _trHashCalendario 
     */
    public void setTrHashCalendario(ListMultimap<Date, TrCalendarioVersao> _trHashCalendario) {
        this._trHashCalendario = _trHashCalendario;
    }

    /**
     *
     * @param data
     * @return
     */
    public String formataData(Date data) {
        return Utils.formataData(data);
    }

    /**
     *
     * @return @throws IOException
     */
    public String logoff() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        ControlaVariaveisSecao.setSessionVariable("user", null);
        FacesContext.getCurrentInstance().getExternalContext().redirect("trLogin.xhtml?faces-redirect=true");
        return "trLogin.xhtml";
    }

    /**
     *
     * @throws IOException
     */
    public void invalidar() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
}
