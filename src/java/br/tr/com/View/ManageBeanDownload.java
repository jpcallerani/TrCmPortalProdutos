package br.tr.com.View;

import br.tr.com.Control.ControlArquivo;
import br.tr.com.Control.ControlSessao;
import br.tr.com.Modal.TrArquivo;
import br.tr.com.Modal.TrArquivoDownload;
import br.tr.com.Modal.TrSessao;
import br.tr.com.Utils.ControlaVariaveisSecao;
import br.tr.com.Utils.Usuario;
import br.tr.com.Utils.Utils;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimaps;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "mbDownload")
@ViewScoped
public class ManageBeanDownload implements Serializable{

    private StreamedContent _trDownload;
    private Usuario _trUsuario;
    private List<TrSessao> _trSessao;
    private HashMap<TrSessao, ListMultimap> _trHashSessao;
    private ListMultimap<String, TrArquivo> _trMultiMapCalendario;

    public ManageBeanDownload() throws IOException {

        this._trUsuario = (Usuario) ControlaVariaveisSecao.getSessionVariable("user");
        if (this._trUsuario == null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("trLogin.xhtml");
        }

        //
        this._trSessao = new ControlSessao().listaSecaoValida();
        _trHashSessao = new HashMap<>();

        //
        _trSessao.stream().map((trSessao) -> {
            _trMultiMapCalendario = Multimaps.newListMultimap(
                    new TreeMap<String, Collection<TrArquivo>>(), Lists::newArrayList);
            return trSessao;
        }).map((trSessao) -> {
            trSessao.getTrArquivoList().stream().forEach((trArquivo) -> {
                _trMultiMapCalendario.put(trArquivo.getIdClassificacao().getDescricao(), trArquivo);
            });
            return trSessao;
        }).forEach((trSessao) -> {
            _trHashSessao.put(trSessao, _trMultiMapCalendario);
        });
    }

    /**
     *
     * @param arquivo
     */
    public void Download(TrArquivo arquivo) {
        TrArquivoDownload arquivo_baixado;
        FacesMessage msg = null;
        try {
            arquivo_baixado = new ControlArquivo().buscaArquivoDownload(arquivo);
            _trDownload = this.convertFichier(arquivo_baixado);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Download Iniciado!");
        } catch (Exception e) {
            e.printStackTrace();
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     *
     * @param arquivo_baixado
     * @return
     */
    public StreamedContent convertFichier(TrArquivoDownload arquivo_baixado) throws FileNotFoundException, IOException {
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
        File path = new File(extContext.getRealPath("//WEB-INF//files//" + arquivo_baixado.getArquivo()));
        FileChannel channel = new RandomAccessFile(path, "r").getChannel();
        //
        InputStream is = Channels.newInputStream(channel);
        //
        StreamedContent file = new DefaultStreamedContent(is, arquivo_baixado.getContentType(), arquivo_baixado.getNomeArquivo());

        return file;
    }

    /**
     * 
     * @param data
     * @return 
     */
    public String formataData(Date data) {
        return Utils.formataData(data);
    }

    /**
     *
     * @return
     */
    public List<TrSessao> getTrSessao() {
        return _trSessao;
    }

    /**
     *
     * @param _trSessao
     */
    public void setTrSessao(List<TrSessao> _trSessao) {
        this._trSessao = _trSessao;
    }

    /**
     *
     * @return
     */
    public HashMap<TrSessao, ListMultimap> getTrHashSessao() {
        return _trHashSessao;
    }

    /**
     *
     * @param _trHashSessao
     */
    public void setTrHashSessao(HashMap<TrSessao, ListMultimap> _trHashSessao) {
        this._trHashSessao = _trHashSessao;
    }

    /**
     *
     * @return
     */
    public StreamedContent getTrDownload() {
        return _trDownload;
    }

    /**
     *
     * @param _trDownload
     */
    public void setTrDownload(StreamedContent _trDownload) {
        this._trDownload = _trDownload;
    }
}
