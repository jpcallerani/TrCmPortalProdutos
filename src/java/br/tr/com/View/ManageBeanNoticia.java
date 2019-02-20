package br.tr.com.View;

import br.tr.com.Control.ControlNoticia;
import br.tr.com.Modal.TrNoticia;
import br.tr.com.Utils.ControlaVariaveisSecao;
import br.tr.com.Utils.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "mbNoticia")
@ViewScoped
public class ManageBeanNoticia implements Serializable {

    private List<TrNoticia> _noticia;
    private List<TrNoticia> _filtroNoticia;
    private TrNoticia _novaNoticia;
    private TrNoticia _SelectedNoticia;
    private TrNoticia _DelectedNoticia;
    private String _erro = "";
    private final Usuario _trUsuario;

    public ManageBeanNoticia() throws IOException {

        this._trUsuario = (Usuario) ControlaVariaveisSecao.getSessionVariable("user");

        if (this._trUsuario == null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("trLogin.xhtml");
        }

        if (!this._trUsuario.getNivel_acesso().equalsIgnoreCase("AD")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("trPrincipal.xhtml?faces-redirect=true");
        }

        this._noticia = new ControlNoticia().listaNoticia();
        
        this._novaNoticia = new TrNoticia();
        this._SelectedNoticia = new TrNoticia();
        this._DelectedNoticia = new TrNoticia();
    }

    public void incluiNoticia() throws IOException {

        FacesMessage msg = null;

        this._novaNoticia.setData(new Date());
        
        this._erro = new ControlNoticia().insereNoticia(this._novaNoticia);

        if (this._erro.equals("")) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro inserido com sucesso!");
            this._noticia.add(this._novaNoticia);
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        this._novaNoticia = new TrNoticia();

    }

    public void deletaNoticia(TrNoticia p_delectedNoticia) throws IOException {

        FacesMessage msg = null;

        try {
            this._erro = new ControlNoticia().deletaNoticia(p_delectedNoticia);

            if (this._erro.equals("")) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro deletado com sucesso!");
                this._noticia.remove(p_delectedNoticia);
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
            }

            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    /**
     * 
     * @throws IOException 
     */
    public void alteraSecao() throws IOException {

        FacesMessage msg = null;

        this._erro = new ControlNoticia().alteraNoticia(this._SelectedNoticia);

        if (this._erro.equals("")) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro alterado com sucesso!");
            this._noticia.set(this._noticia.indexOf(_SelectedNoticia), _SelectedNoticia);
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
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
    public List<TrNoticia> getFiltroNoticia() {
        return _filtroNoticia;
    }

    /**
     *
     * @param _filtroNoticia
     */
    public void setFiltroNoticia(List<TrNoticia> _filtroNoticia) {
        this._filtroNoticia = _filtroNoticia;
    }

    /**
     *
     * @return
     */
    public TrNoticia getNovaNoticia() {
        return _novaNoticia;
    }

    /**
     *
     * @param _novaNoticia
     */
    public void setNovaNoticia(TrNoticia _novaNoticia) {
        this._novaNoticia = _novaNoticia;
    }

    /**
     *
     * @return
     */
    public TrNoticia getSelectedNoticia() {
        return _SelectedNoticia;
    }

    /**
     *
     * @param _SelectedNoticia
     */
    public void setSelectedNoticia(TrNoticia _SelectedNoticia) {
        this._SelectedNoticia = _SelectedNoticia;
    }

    /**
     *
     * @return
     */
    public TrNoticia getDelectedNoticia() {
        return _DelectedNoticia;
    }

    /**
     *
     * @param _DelectedNoticia
     */
    public void setDelectedNoticia(TrNoticia _DelectedNoticia) {
        this._DelectedNoticia = _DelectedNoticia;
    }
}
