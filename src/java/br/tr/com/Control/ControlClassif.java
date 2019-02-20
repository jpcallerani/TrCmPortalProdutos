package br.tr.com.Control;

import br.tr.com.DAO.SysDao;
import br.tr.com.Modal.TrClassificacao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public class ControlClassif {

    private List<TrClassificacao> _trClassif;
    private final List<Criterion> _argumentos;
    private Order _order;
    private String _erro = "";

    public ControlClassif() {
        this._trClassif = new ArrayList<>();
        this._argumentos = new ArrayList<>();
    }

     /**
      * 
      * @return
      * @throws IOException 
      */
    public List<TrClassificacao> listaClassif() throws IOException {
        this._order = Order.asc("descricao");
        this._trClassif = (List<TrClassificacao>) new SysDao().listagem(TrClassificacao.class, _argumentos, this._order, 0);
        return this._trClassif;
    }
    
    /**
     *
     * @param p_classif
     * @return
     * @throws IOException
     */
    public String insereClassif(TrClassificacao p_classif) throws IOException {
        this._erro = new SysDao().save(p_classif);
        return this._erro;
    }

    /**
     *
     * @param p_classif
     * @return
     * @throws IOException
     */
    public String alteraClassif(TrClassificacao p_classif) throws IOException {
        this._erro = new SysDao().update(p_classif);
        return this._erro;
    }

    /**
     *
     * @param p_classif
     * @return
     * @throws IOException
     */
    public String deletaClassif(TrClassificacao p_classif) throws IOException {
        this._erro = new SysDao().delete(p_classif);
        return this._erro;
    }

}
