package br.tr.com.View;

import br.tr.com.Control.ControlUsuarioAdm;
import br.tr.com.Utils.ControlaVariaveisSecao;
import br.tr.com.Utils.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "mbUsuarioAdm")
@ViewScoped
public class ManageBeanUsuarioAdm implements Serializable {

    private List<Usuario> _trUsuarios;
    private List<Usuario> _filtroUsuario;
    private final Usuario _trUsuario;
    private Usuario _selectUsuario;

    public ManageBeanUsuarioAdm() throws IOException {

        this._trUsuario = (Usuario) ControlaVariaveisSecao.getSessionVariable("user");

        if (this._trUsuario == null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("trLogin.xhtml");
        }

        if (!this._trUsuario.getNivel_acesso().equalsIgnoreCase("AD")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("trPrincipal.xhtml?faces-redirect=true");
        }

        this._trUsuarios = new ControlUsuarioAdm().listaFuncionarios();
        this._selectUsuario = new Usuario();
    }

    /**
     *
     * @return
     */
    public Usuario getSelectedUsuario() {
        return _selectUsuario;
    }

    /**
     *
     * @param _selectedUsuarios
     */
    public void setSelectedUsuario(Usuario _selectedUsuarios) {
        this._selectUsuario = _selectedUsuarios;
    }

    /**
     *
     * @return
     */
    public List<Usuario> getTrUsuarios() {
        return _trUsuarios;
    }

    /**
     *
     * @param _trUsuarios
     */
    public void setTrUsuarios(List<Usuario> _trUsuarios) {
        this._trUsuarios = _trUsuarios;
    }

    /**
     *
     * @return
     */
    public List<Usuario> getFiltroUsuario() {
        return _filtroUsuario;
    }

    /**
     *
     * @param _filtroUsuario
     */
    public void setFiltroUsuario(List<Usuario> _filtroUsuario) {
        this._filtroUsuario = _filtroUsuario;
    }

}
