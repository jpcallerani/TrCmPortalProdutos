package br.tr.com.Control;

import br.tr.com.DAO.SysDao;
import br.tr.com.Modal.TrNoticia;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public class ControlNoticia {

    private List<TrNoticia> _noticia;
    private List<Criterion> _argumentos;
    private Order _order;
    private String _erro = "";

    public ControlNoticia() {
        this._noticia = new ArrayList<>();
        this._argumentos = new ArrayList<>();
    }

    /**
     *
     * @param p_noticia
     * @return
     * @throws IOException
     */
    public String insereNoticia(TrNoticia p_noticia) throws IOException {
        this._erro = new SysDao().save(p_noticia);
        return this._erro;
    }

    /**
     *
     * @param p_noticia
     * @return
     * @throws IOException
     */
    public String alteraNoticia(TrNoticia p_noticia) throws IOException {
        this._erro = new SysDao().update(p_noticia);
        return this._erro;
    }

    /**
     *
     * @param p_noticia
     * @return
     * @throws IOException
     */
    public String deletaNoticia(TrNoticia p_noticia) throws IOException {
        this._erro = new SysDao().delete(p_noticia);
        return this._erro;
    }

    /**
     * 
     * @return
     * @throws IOException 
     */
    public List<TrNoticia> listaNoticia() throws IOException {
        this._order = Order.desc("data");
        this._noticia = (List<TrNoticia>) new SysDao().listagem(TrNoticia.class, _argumentos, this._order, 0);
        return this._noticia;
    }
}
