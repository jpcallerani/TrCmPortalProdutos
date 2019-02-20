package br.tr.com.View;

import br.tr.com.Control.ControlSessao;
import br.tr.com.Modal.TrSessao;
import br.tr.com.Utils.ControlaVariaveisSecao;
import br.tr.com.Utils.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "mbSessao")
@ViewScoped
public class ManageBeanSessao implements Serializable {

    private List<TrSessao> _sessao;
    private List<TrSessao> _filtroSessao;
    private TrSessao _novaSecao;
    private TrSessao _SelectedSecao;
    private TrSessao _DelectedSecao;
    private boolean _SelectedValido;
    private boolean _valido;
    private String _erro = "";
    private Usuario _trUsuario;

    public ManageBeanSessao() throws IOException {

        this._trUsuario = (Usuario) ControlaVariaveisSecao.getSessionVariable("user");

        if (this._trUsuario == null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("trLogin.xhtml");
        }

        if (!this._trUsuario.getNivel_acesso().equalsIgnoreCase("AD")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("trPrincipal.xhtml?faces-redirect=true");
        }

        this._novaSecao = new TrSessao();
        this._SelectedSecao = new TrSessao();
        this._DelectedSecao = new TrSessao();
        // Carrega as seções.
        this._sessao = (List<TrSessao>) ControlaVariaveisSecao.getSessionVariable("seção");
        if (this._sessao == null) {
            this._sessao = new ControlSessao().listaSecao();
            ControlaVariaveisSecao.setSessionVariable("seção", this._sessao);
        }
    }

    public void limpaTela() {
        this._novaSecao = new TrSessao();
    }

    /**
     *
     * @param p_delectedSecao
     * @throws IOException
     */
    public void deletaSecao(TrSessao p_delectedSecao) throws IOException {

        FacesMessage msg = null;

        try {
            this._erro = new ControlSessao().deletaSecao(p_delectedSecao);

            if (this._erro.equals("")) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro deletado com sucesso!");
                this._sessao.remove(p_delectedSecao);
                ControlaVariaveisSecao.setSessionVariable("seção", this._sessao);
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     *
     */
    public void alteraSecao() throws IOException {

        FacesMessage msg = null;

        if (this._SelectedValido == true) {
            this._SelectedSecao.setValido("S");
        } else {
            this._SelectedSecao.setValido("N");
        }

        this._erro = new ControlSessao().alteraSecao(this._SelectedSecao);

        if (this._erro.equals("")) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro alterado com sucesso!");
            this._sessao.set(this._sessao.indexOf(_SelectedSecao), _SelectedSecao);
            ControlaVariaveisSecao.setSessionVariable("seção", this._sessao);
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     *
     */
    public void incluiSecao() throws IOException {

        FacesMessage msg = null;

        if (this._valido == true) {
            this._novaSecao.setValido("S");
        } else {
            this._novaSecao.setValido("N");
        }

        this._erro = new ControlSessao().insereSecao(this._novaSecao);

        if (this._erro.equals("")) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro inserido com sucesso!");
            this._sessao.add(this._novaSecao);
            ControlaVariaveisSecao.setSessionVariable("seção", this._sessao);
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        this._novaSecao = new TrSessao();

    }

    /**
     *
     * @return
     */
    public List<TrSessao> getSessao() {
        return _sessao;
    }

    /**
     *
     * @param _sessao
     */
    public void setSessao(List<TrSessao> _sessao) {
        this._sessao = _sessao;
    }

    /**
     *
     * @return
     */
    public List<TrSessao> getFiltroSessao() {
        return _filtroSessao;
    }

    /**
     *
     * @param _filtroSessao
     */
    public void setFiltroSessao(List<TrSessao> _filtroSessao) {
        this._filtroSessao = _filtroSessao;
    }

    /**
     *
     * @return
     */
    public TrSessao getNovaSessao() {
        return _novaSecao;
    }

    /**
     *
     * @param _novaSecao
     */
    public void setNovaSessao(TrSessao _novaSecao) {
        this._novaSecao = _novaSecao;
    }

    /**
     *
     * @return
     */
    public boolean isValido() {
        return _valido;
    }

    /**
     *
     * @param _valido
     */
    public void setValido(boolean _valido) {
        this._valido = _valido;
    }

    /**
     *
     * @return
     */
    public TrSessao getSelectedSecao() {
        if (this._SelectedSecao != null && this._SelectedSecao.getValido() != null) {
            if (this._SelectedSecao.getValido().equals("S")) {
                this._SelectedValido = true;
            } else {
                this._SelectedValido = false;
            }
        }
        return _SelectedSecao;
    }

    /**
     *
     * @param _SelectedSecao
     */
    public void setSelectedSecao(TrSessao _SelectedSecao) {
        this._SelectedSecao = _SelectedSecao;
    }

    /**
     *
     * @return
     */
    public boolean isSelectedValido() {
        return _SelectedValido;
    }

    /**
     *
     * @param _SelectedValido
     */
    public void setSelectedValido(boolean _SelectedValido) {
        this._SelectedValido = _SelectedValido;
    }

    /**
     *
     * @return
     */
    public TrSessao getDelectedSecao() {
        return _DelectedSecao;
    }

    /**
     *
     * @param _DelectedSecao
     */
    public void setDelectedSecao(TrSessao _DelectedSecao) {
        this._DelectedSecao = _DelectedSecao;
    }
}
