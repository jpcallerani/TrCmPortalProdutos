package br.tr.com.View;

import br.tr.com.Control.ControlClassif;
import br.tr.com.Modal.TrClassificacao;
import br.tr.com.Utils.ControlaVariaveisSecao;
import br.tr.com.Utils.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "mbClassif")
@ViewScoped
public class ManageBeanClassif implements Serializable {

    private List<TrClassificacao> _trClassif;
    private List<TrClassificacao> _filtroClassif;
    private TrClassificacao _novaClassif;
    private TrClassificacao _selectedClassif;
    private TrClassificacao _delectClassif;
    private String _erro = "";
    private Usuario _trUsuario;

    public ManageBeanClassif() throws IOException {

        this._trUsuario = (Usuario) ControlaVariaveisSecao.getSessionVariable("user");

        if (this._trUsuario == null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("trLogin.xhtml");
        }

        if (!this._trUsuario.getNivel_acesso().equalsIgnoreCase("AD")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("trPrincipal.xhtml?faces-redirect=true");
        }

        this._novaClassif = new TrClassificacao();
        this._selectedClassif = new TrClassificacao();
        this._delectClassif = new TrClassificacao();
        // Carrega as classificações.
        this._trClassif = (List<TrClassificacao>) ControlaVariaveisSecao.getSessionVariable("classificação");
        if (this._trClassif == null) {
            this._trClassif = new ControlClassif().listaClassif();
            ControlaVariaveisSecao.setSessionVariable("classificação", this._trClassif);
        }
    }

    /**
     *
     * @param p_delectedClassif
     * @throws IOException
     */
    public void deletaClassif(TrClassificacao p_delectedClassif) throws IOException {

        FacesMessage msg = null;

        this._erro = new ControlClassif().deletaClassif(p_delectedClassif);

        if (this._erro.equals("")) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro deletado com sucesso!");
            this._trClassif.remove(p_delectedClassif);
            ControlaVariaveisSecao.setSessionVariable("classificação", this._trClassif);
            //RequestContext.getCurrentInstance().update("frm_dlg_altera_secao:growl_altera_secao");
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     *
     */
    public void alteraClassif() throws IOException {

        FacesMessage msg = null;

        this._erro = new ControlClassif().alteraClassif(this._selectedClassif);

        if (this._erro.equals("")) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro alterado com sucesso!");
            this._trClassif.set(this._trClassif.indexOf(_selectedClassif), _selectedClassif);
            ControlaVariaveisSecao.setSessionVariable("classificação", this._trClassif);
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     *
     */
    public void incluiClassif() throws IOException {

        FacesMessage msg = null;

        this._erro = new ControlClassif().insereClassif(this._novaClassif);

        if (this._erro.equals("")) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro inserido com sucesso!");
            this._trClassif.add(this._novaClassif);
            ControlaVariaveisSecao.setSessionVariable("classificação", this._trClassif);
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        this._novaClassif = new TrClassificacao();

    }

    /**
     *
     * @return
     */
    public List<TrClassificacao> getTrClassif() {
        return _trClassif;
    }

    /**
     *
     * @param _trClassif
     */
    public void setTrClassif(List<TrClassificacao> _trClassif) {
        this._trClassif = _trClassif;
    }

    /**
     *
     * @return
     */
    public TrClassificacao getNovaClassif() {
        return _novaClassif;
    }

    /**
     *
     * @param _novaClassif
     */
    public void setNovaClassif(TrClassificacao _novaClassif) {
        this._novaClassif = _novaClassif;
    }

    /**
     *
     * @return
     */
    public List<TrClassificacao> getFiltroClassif() {
        return _filtroClassif;
    }

    /**
     *
     * @param _filtroClassif
     */
    public void setFiltroClassif(List<TrClassificacao> _filtroClassif) {
        this._filtroClassif = _filtroClassif;
    }

    /**
     *
     * @return
     */
    public TrClassificacao getSelectedClassif() {
        return _selectedClassif;
    }

    /**
     *
     * @param _selectedClassif
     */
    public void setSelectedClassif(TrClassificacao _selectedClassif) {
        this._selectedClassif = _selectedClassif;
    }

    /**
     *
     * @return
     */
    public TrClassificacao getDelectClassif() {
        return _delectClassif;
    }

    /**
     *
     * @param _delectClassif
     */
    public void setDelectClassif(TrClassificacao _delectClassif) {
        this._delectClassif = _delectClassif;
    }
}
