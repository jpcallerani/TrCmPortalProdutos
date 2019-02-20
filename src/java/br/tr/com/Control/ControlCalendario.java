package br.tr.com.Control;

import br.tr.com.DAO.SysDao;
import br.tr.com.Modal.TrCalendarioVersao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ControlCalendario {

    private List<TrCalendarioVersao> _trCalendario;
    private List<Criterion> _argumentos;
    private Order _order;
    private String _erro = "";

    public ControlCalendario() {
        this._trCalendario = new ArrayList<>();
        this._argumentos = new ArrayList<>();
    }

    /**
     *
     * @param p_calendario
     * @return
     * @throws IOException
     */
    public TrCalendarioVersao insereCalendario(TrCalendarioVersao p_calendario) throws IOException {
        p_calendario = (TrCalendarioVersao) new SysDao().saveReturnObj(p_calendario);
        return p_calendario;
    }

    /**
     *
     * @param p_calendario
     * @return
     */
    public String deletaCalendario(TrCalendarioVersao p_calendario) {
        this._erro = new SysDao().delete(p_calendario);
        return this._erro;
    }

    /**
     *
     * @param p_calendario
     * @return
     * @throws IOException
     */
    public String atualizaCalendario(TrCalendarioVersao p_calendario) throws IOException {
        this._erro = new SysDao().update(p_calendario);
        return this._erro;
    }

    /**
     *
     * @return @throws IOException
     */
    public List<TrCalendarioVersao> listaCalendarioPrincipal() throws IOException {
        this._order = Order.asc("data");
        _argumentos.add(Restrictions.gt("data", new Date()));
        this._trCalendario = (List<TrCalendarioVersao>) new SysDao().listagem(TrCalendarioVersao.class, _argumentos, _order, 5);
        return this._trCalendario;
    }
    
        /**
     *
     * @return @throws IOException
     */
    public List<TrCalendarioVersao> listaCalendario() throws IOException {
        this._order = Order.asc("data");
        this._trCalendario = (List<TrCalendarioVersao>) new SysDao().listagem(TrCalendarioVersao.class, _argumentos, _order, 0);
        return this._trCalendario;
    }
}
