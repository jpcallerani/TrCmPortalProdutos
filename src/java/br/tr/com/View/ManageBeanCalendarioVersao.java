package br.tr.com.View;

import br.tr.com.Control.ControlCalendario;
import br.tr.com.Modal.TrCalendarioVersao;
import br.tr.com.Utils.ControlaVariaveisSecao;
import br.tr.com.Utils.DefaultScheduleModel;
import br.tr.com.Utils.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javafx.event.ActionEvent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@ManagedBean(name = "mbCalendario")
@ViewScoped
public class ManageBeanCalendarioVersao implements Serializable {

    private DefaultScheduleModel _trEventModel;
    private ScheduleEvent _trEvent;
    private TrCalendarioVersao _trCalendario;
    private String _erro = "";
    private List<TrCalendarioVersao> _trCalendarios;
    private Usuario _trUsuario;

    public ManageBeanCalendarioVersao() throws IOException {

        this._trUsuario = (Usuario) ControlaVariaveisSecao.getSessionVariable("user");

        if (this._trUsuario == null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("trLogin.xhtml");
        }

        if (!this._trUsuario.getNivel_acesso().equalsIgnoreCase("AD")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("trPrincipal.xhtml?faces-redirect=true");
        }

        this._trEvent = new DefaultScheduleEvent();
        this._trEventModel = new DefaultScheduleModel();
        this._trCalendario = new TrCalendarioVersao();
        this._trCalendarios = new ControlCalendario().listaCalendario();

        _trCalendarios.stream().map((trCalendarioVersao) -> {
            DefaultScheduleEvent v_novo_evento = new DefaultScheduleEvent();
            v_novo_evento.setId(String.valueOf(trCalendarioVersao.getIdCalendario()));
            v_novo_evento.setTitle(trCalendarioVersao.getNome());
            v_novo_evento.setDescription(trCalendarioVersao.getObservacao());
            v_novo_evento.setStartDate(trCalendarioVersao.getData());
            v_novo_evento.setEndDate(trCalendarioVersao.getData());
            return v_novo_evento;
        }).forEach((v_novo_evento) -> {
            _trEventModel.addEvent(v_novo_evento);
        });
    }

    /**
     *
     * @param selectEvent
     */
    public void onDateSelect(SelectEvent selectEvent) {
        this._trEvent = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }

    /**
     *
     * @param actionEvent
     * @throws IOException
     */
    public void addEvent(ActionEvent actionEvent) throws IOException {

        FacesMessage msg = null;

        try {
            this._trCalendario.setNome(this._trEvent.getTitle());
            this._trCalendario.setObservacao(this._trEvent.getDescription());
            this._trCalendario.setData(this._trEvent.getStartDate());

            if (_trEvent.getId() == null) {
                _trCalendario = new ControlCalendario().insereCalendario(_trCalendario);
                if (_trCalendario != null) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro inserido com sucesso!");
                    this._trEvent.setId(String.valueOf(_trCalendario.getIdCalendario()));
                    this._trEventModel.addEvent(this._trEvent);
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
                }
            } else {
                this._trCalendario.setIdCalendario(Integer.parseInt(_trEvent.getId()));
                this._erro = new ControlCalendario().atualizaCalendario(_trCalendario);
                if (this._erro.equals("")) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro atualizado com sucesso!");
                    this._trEventModel.updateEvent(this._trEvent);
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
                }
            }
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    /**
     *
     */
    public void deleteEvent() {

        FacesMessage msg = null;

        try {
            _trCalendario.setIdCalendario(Integer.parseInt(_trEvent.getId()));
            this._trCalendario.setNome(this._trEvent.getTitle());
            this._trCalendario.setObservacao(this._trEvent.getDescription());
            this._trCalendario.setData(this._trEvent.getStartDate());
            this._erro = new ControlCalendario().deletaCalendario(_trCalendario);
            if (this._erro.equals("")) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro deletado com sucesso!");
                this._trEventModel.deleteEvent(this._trEvent);
                this._trEvent = new DefaultScheduleEvent();
                //RequestContext.getCurrentInstance().execute(":frm_adiciona_calendario:eventDialog.hide()");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onEventSelect(SelectEvent selectEvent) {
        _trEvent = (DefaultScheduleEvent) selectEvent.getObject();
    }

    public ScheduleModel getTrEventModel() {
        return _trEventModel;
    }

    public void setTrEventModel(DefaultScheduleModel _trEventModel) {
        this._trEventModel = _trEventModel;
    }

    public ScheduleEvent getTrEvent() {
        return _trEvent;
    }

    public void setTrEvent(ScheduleEvent _trEvent) {
        this._trEvent = _trEvent;
    }

    public TrCalendarioVersao getTrCalendario() {
        return _trCalendario;
    }

    public void setTrCalendario(TrCalendarioVersao _trCalendario) {
        this._trCalendario = _trCalendario;
    }

    public Usuario getTrUsuario() {
        return _trUsuario;
    }

    public void setTrUsuario(Usuario _trUsuario) {
        this._trUsuario = _trUsuario;
    }
}
