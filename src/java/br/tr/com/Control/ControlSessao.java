package br.tr.com.Control;

import br.tr.com.DAO.SysDao;
import br.tr.com.Modal.TrArquivo;
import br.tr.com.Modal.TrSessao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

public class ControlSessao {

    private List<TrSessao> _sessao;
    private List<Criterion> _argumentos;
    private Order _order;
    private String _erro = "";

    public ControlSessao() {
        this._sessao = new ArrayList<>();
        this._argumentos = new ArrayList<>();
    }

    /**
     * Lista todas as sessoes.
     *
     * @return
     */
    public List<TrSessao> listaSecao() throws IOException {
        this._order = Order.asc("nomeSessao");
        this._sessao = (List<TrSessao>) new SysDao().listagem(TrSessao.class, _argumentos, this._order, 0);
        return this._sessao;
    }

    /**
     * Lista todas as sessoes.
     *
     * @return
     */
    public List<TrSessao> listaSecaoValida() throws IOException {
        this._order = Order.asc("nomeSessao");
        this._argumentos.add(Restrictions.eq("valido", "S"));
        this._argumentos.add(Restrictions.sqlRestriction("{alias}.id_sessao in (SELECT id_sessao FROM tr_arquivo)"));
        this._sessao = (List<TrSessao>) new SysDao().listagem(TrSessao.class, _argumentos, this._order, 0);
        return this._sessao;
    }

    /**
     *
     * @param p_secao
     * @return
     * @throws IOException
     */
    public String insereSecao(TrSessao p_secao) throws IOException {
        this._erro = new SysDao().save(p_secao);
        return this._erro;
    }

    /**
     *
     * @param p_secao
     * @return
     * @throws IOException
     */
    public String alteraSecao(TrSessao p_secao) throws IOException {
        this._erro = new SysDao().update(p_secao);
        return this._erro;
    }

    /**
     *
     * @param p_secao
     * @return
     * @throws IOException
     */
    public String deletaSecao(TrSessao p_secao) throws IOException {
        this._erro = new SysDao().delete(p_secao);
        return this._erro;
    }
}
