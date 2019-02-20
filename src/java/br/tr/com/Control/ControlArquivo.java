package br.tr.com.Control;

import br.tr.com.DAO.SysDao;
import br.tr.com.Modal.TrArquivo;
import br.tr.com.Modal.TrArquivoDownload;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ControlArquivo {

    private List<TrArquivo> _trArquivo;
    private List<Criterion> _argumentos;
    private Order _order;
    private String _erro = "";
    private TrArquivoDownload _downloadArquivo;

    public ControlArquivo() {
        this._trArquivo = new ArrayList<>();
        this._argumentos = new ArrayList<>();
        _downloadArquivo = new TrArquivoDownload();
    }

    /**
     *
     * @param p_arquivo
     * @return
     * @throws IOException
     */
    public String insereArquivo(TrArquivo p_arquivo) throws IOException {
        this._erro = new SysDao().save(p_arquivo);
        return this._erro;
    }

    /**
     *
     * @param p_arquivo
     * @return
     * @throws IOException
     */
    public String atualizaArquivo(TrArquivo p_arquivo) throws IOException {
        this._erro = new SysDao().update(p_arquivo);
        return this._erro;
    }

    /**
     *
     * @param p_arquivo
     * @return
     * @throws IOException
     */
    public String deletaArquivo(TrArquivo p_arquivo) throws IOException {
        this._erro = new SysDao().delete(p_arquivo);
        return this._erro;
    }

    /**
     *
     * @param p_arquivoDownload
     * @param in
     * @return
     * @throws java.io.IOException
     */
    public String insereArquivoDownload(TrArquivoDownload p_arquivoDownload) throws IOException {
        SysDao v_conexao = new SysDao();
        v_conexao.openSession();
        this._erro = v_conexao.saveSemCommit(p_arquivoDownload.getIdArquivo());
        if (this._erro.equals("")) {
            this._erro = v_conexao.saveSemCommit(p_arquivoDownload);
            if (this._erro.equals("")) {
                v_conexao.commit();
            } else {
                v_conexao.rollback();
            }
        } else {
            v_conexao.rollback();
        }
        v_conexao.closeSession();
        return this._erro;
    }

    /**
     *
     * @param p_arquivo
     * @return
     * @throws IOException
     */
    public TrArquivo retornaIdAfterInsert(TrArquivo p_arquivo) {
        TrArquivo v_arquivo = null;
        try {
            Criterion id_arquivo = Restrictions.eq("idArquivo", p_arquivo.getIdArquivo());
            this._argumentos.add(id_arquivo);
            v_arquivo = ((ArrayList<TrArquivo>) new SysDao().listagem(TrArquivo.class, _argumentos, _order, 0)).get(0);
        } catch (Exception e) {
            e.printStackTrace();
            v_arquivo = null;
        }

        return v_arquivo;
    }

    /**
     *
     * @return @throws IOException
     */
    public List<TrArquivo> listaArquivo() throws IOException {
        this._order = Order.desc("idSessao");
        this._trArquivo = (List<TrArquivo>) new SysDao().listagem(TrArquivo.class, _argumentos, _order, 0);
        return this._trArquivo;
    }

    /**
     *
     * @param p_arquivo
     * @return
     * @throws IOException
     */
    public List<TrArquivo> buscaArquivo(TrArquivo p_arquivo) throws IOException {
        this._order = Order.desc("idClassificacao");
        this._argumentos.add(Restrictions.eq("idSessao", p_arquivo.getIdSessao()));
        this._trArquivo = (List<TrArquivo>) new SysDao().listagem(TrArquivo.class, _argumentos, _order, 0);
        return this._trArquivo;
    }

    /**
     *
     * @param p_arquivo
     * @return
     */
    public TrArquivoDownload buscaArquivoDownload(TrArquivo p_arquivo) {
        this._argumentos.add(Restrictions.eq("idArquivo", p_arquivo));
        _downloadArquivo = (TrArquivoDownload) new SysDao().listagem(TrArquivoDownload.class, _argumentos, null, 0).get(0);
        return _downloadArquivo;
    }
}
