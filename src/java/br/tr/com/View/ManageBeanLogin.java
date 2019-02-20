package br.tr.com.View;

import br.tr.com.Control.ControlUsuario;
import br.tr.com.Utils.*;
import javafx.event.ActionEvent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "mbUsuario")
@RequestScoped
public class ManageBeanLogin {

    private Usuario _trUsuario;

    public ManageBeanLogin() {
        _trUsuario = new Usuario();
        _trUsuario.setEmail("Username@email.com");
        _trUsuario.setSenha("Password");
    }

    public void login(ActionEvent actionEvent) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;

        _trUsuario = new ControlUsuario().validaUsuario(_trUsuario);
        
        if (_trUsuario != null) {
            loggedIn = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", _trUsuario.getNome_func());
            ControlaVariaveisSecao.setSessionVariable("user", _trUsuario);
        } else {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha no login.", "Username/password invalid");
            ControlaVariaveisSecao.setSessionVariable("user", null);
        }

        _trUsuario = new Usuario();
        _trUsuario.setEmail("Username@email.com");
        _trUsuario.setSenha("Password");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public Usuario getTrUsuario() {
        return _trUsuario;
    }

    public void setTrUsuario(Usuario _trUsuario) {
        this._trUsuario = _trUsuario;
    }
}
