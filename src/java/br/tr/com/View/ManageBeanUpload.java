package br.tr.com.View;

import br.tr.com.Control.ControlArquivo;
import br.tr.com.Control.ControlClassif;
import br.tr.com.Control.ControlSessao;
import br.tr.com.Modal.TrArquivo;
import br.tr.com.Modal.TrArquivoDownload;
import br.tr.com.Modal.TrClassificacao;
import br.tr.com.Modal.TrSessao;
import br.tr.com.Utils.ControlaVariaveisSecao;
import br.tr.com.Utils.Usuario;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "mbUpload")
@ViewScoped
public class ManageBeanUpload implements Serializable {

    private List<TrArquivoDownload> _trArquivos;
    private List<TrSessao> _trSecoes;
    private List<TrClassificacao> _trClassifs;
    private TrClassificacao _trSelectedClassif;
    private TrSessao _trSelectedSecao;
    private TrArquivo _trArquivo;
    private TrArquivo _trListaArquivo;
    private TrArquivo _selectedArquivo;
    private List<TrArquivo> _trListaArquivos;
    private String _erro = "";
    private boolean _visivel;
    private final Usuario _trUsuario;

    public ManageBeanUpload() throws IOException {

        this._trUsuario = (Usuario) ControlaVariaveisSecao.getSessionVariable("user");

        if (this._trUsuario == null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("trLogin.xhtml");
        }

        if (!this._trUsuario.getNivel_acesso().equalsIgnoreCase("AD")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("trPrincipal.xhtml?faces-redirect=true");
        }

        this._trArquivo = new TrArquivo();
        this._trArquivos = new ArrayList<>();
        this._trListaArquivo = new TrArquivo();

        // Carrega as seções.
        this._trSecoes = (List<TrSessao>) ControlaVariaveisSecao.getSessionVariable("seção");
        if (this._trSecoes == null) {
            this._trSecoes = new ControlSessao().listaSecao();
            ControlaVariaveisSecao.setSessionVariable("seção", this._trSecoes);
        }

        // Carrega as classificações.
        this._trClassifs = (List<TrClassificacao>) ControlaVariaveisSecao.getSessionVariable("classificação");
        if (this._trClassifs == null) {
            this._trClassifs = new ControlClassif().listaClassif();
            ControlaVariaveisSecao.setSessionVariable("classificação", this._trClassifs);
        }

        _selectedArquivo = new TrArquivo();
    }

    /**
     *
     * @param arquivo
     */
    public void alteraArquivo(TrArquivo arquivo) {

        FacesMessage msg = null;

        try {
            this._erro = new ControlArquivo().atualizaArquivo(arquivo);

            if (this._erro.equals("")) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro atualizado com sucesso!");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
            }

            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     *
     * @param arquivo
     */
    public void deletaArquivo(TrArquivo arquivo) {

        FacesMessage msg = null;

        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();

        try {
            //
            TrArquivoDownload delete_arquivo = new ControlArquivo().buscaArquivoDownload(arquivo);
            //
            File path = new File(extContext.getRealPath("//WEB-INF//files//" + delete_arquivo.getArquivo()));
            if (path.exists()) {
                path.delete();
            }
            //
            this._erro = new ControlArquivo().deletaArquivo(arquivo);
            //
            if (this._erro.equals("")) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro deletado com sucesso!");
                _trListaArquivos.remove(arquivo);
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
            }
            //
            FacesContext.getCurrentInstance().addMessage(null, msg);
            //
        } catch (Exception e) {
            e.printStackTrace();
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     *
     */
    public void eventSecao() {
        try {
            _trListaArquivos = new ControlArquivo().buscaArquivo(_trListaArquivo);
        } catch (IOException ex) {
            Logger.getLogger(ManageBeanUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param event
     * @throws java.io.IOException
     */
    public void insereArquivos(FileUploadEvent event) {

        FacesMessage msg = null;

        try {
            TrArquivo v_novo_arquivo = new TrArquivo();
            UploadedFile file = event.getFile();
            v_novo_arquivo.setIdClassificacao(null);
            v_novo_arquivo.setIdSessao(null);
            v_novo_arquivo.setNomeArquivo(file.getFileName().substring(0, file.getFileName().lastIndexOf(".")));
            v_novo_arquivo.setData(new Date());
            TrArquivoDownload v_novo_arquivo_download = new TrArquivoDownload();
            v_novo_arquivo_download.setNomeArquivo(file.getFileName());
            v_novo_arquivo_download.setContentType(file.getContentType());
            v_novo_arquivo_download.setDados(file.getInputstream());
            v_novo_arquivo_download.setTamanhoArquivo(file.getSize());
            v_novo_arquivo_download.setIdArquivo(v_novo_arquivo);
            this._trArquivos.add(v_novo_arquivo_download);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "ok!");
        } catch (IOException e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     *
     * @param p_delectedArquivo
     * @throws IOException
     */
    public void deletaArquivo(TrArquivoDownload p_delectedArquivo) throws IOException {

        this._trArquivos.remove(p_delectedArquivo);

    }

    /**
     *
     */
    public void atualizaArquivo() {

        FacesMessage msg = null;

        List<TrArquivoDownload> trArquivoTemp = new ArrayList<>();
        List<String> trStringErros = new ArrayList<>();

        try {

            if (_trArquivos.size() > 0) {

                for (TrArquivoDownload trArquivo : _trArquivos) {

                    ReadableByteChannel readableChannel = Channels.newChannel(trArquivo.getDados());

                    ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
                    //
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    trArquivo.setArquivo(sdf.format(new Date()) + trArquivo.getNomeArquivo());
                    //
                    File result = new File(extContext.getRealPath("//WEB-INF//files//" + trArquivo.getArquivo()));
                    //
                    FileOutputStream outputStream = new FileOutputStream(result);
                    FileChannel outputChannel = outputStream.getChannel();
                    FileLock lock = outputChannel.lock();

                    outputChannel.transferFrom(readableChannel, outputChannel.position(), Integer.MAX_VALUE);
                    lock.release();
                    outputChannel.close();
                    outputStream.close();

                    this._erro = new ControlArquivo().insereArquivoDownload(trArquivo);

                    if (!this._erro.equals("")) {
                        trStringErros.add(this._erro);
                        trArquivoTemp.add(trArquivo);
                        if (result.exists()) {
                            result.delete();
                        }
                    }
                }
                if (trStringErros.isEmpty()) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro(s) \n atualizado(s) com sucesso!", "");
                } else {
                    String erro = "";
                    for (String erro_insert : trStringErros) {
                        erro += "-> " + erro_insert + "\n";
                    }
                    msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Atenção", erro);
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nenhum arquivo selecionado!", "");
            }
        } catch (IOException e) {
            e.printStackTrace();
            msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Atenção", "Erro grave! \n Consulte o administrador do sistema.");
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        _trArquivos = trArquivoTemp;
    }

    /**
     *
     * @return
     */
    public TrArquivo getTrArquivo() {
        return _trArquivo;
    }

    /**
     *
     * @param _trArquivo
     */
    public void setTrArquivo(TrArquivo _trArquivo) {
        this._trArquivo = _trArquivo;
    }

    /**
     *
     * @return
     */
    public List<TrArquivoDownload> getTrArquivos() {
        return _trArquivos;
    }

    /**
     *
     * @param _trArquivos
     */
    public void setTrArquivos(List<TrArquivoDownload> _trArquivos) {
        this._trArquivos = _trArquivos;
    }

    /**
     *
     * @return
     */
    public List<TrSessao> getTrSecoes() {
        return _trSecoes;
    }

    /**
     *
     * @param _trSecoes
     */
    public void setTrSecoes(List<TrSessao> _trSecoes) {
        this._trSecoes = _trSecoes;
    }

    /**
     *
     * @return
     */
    public List<TrClassificacao> getTrClassifs() {
        return _trClassifs;
    }

    /**
     *
     * @param _trClassifs
     */
    public void setTrClassifs(List<TrClassificacao> _trClassifs) {
        this._trClassifs = _trClassifs;
    }

    /**
     *
     * @return
     */
    public TrClassificacao getTrSelectedClassif() {
        return _trSelectedClassif;
    }

    /**
     *
     * @param _trSelectedClassif
     */
    public void setTrSelectedClassif(TrClassificacao _trSelectedClassif) {
        this._trSelectedClassif = _trSelectedClassif;
    }

    /**
     *
     * @return
     */
    public TrSessao getTrSelectedSecao() {
        return _trSelectedSecao;
    }

    /**
     *
     * @param _trSelectedSecao
     */
    public void setTrSelectedSecao(TrSessao _trSelectedSecao) {
        this._trSelectedSecao = _trSelectedSecao;
    }

    /**
     *
     * @return
     */
    public boolean isVisivel() {
        return _visivel;
    }

    /**
     *
     * @param _visivel
     */
    public void setVisivel(boolean _visivel) {
        this._visivel = _visivel;
    }

    /**
     *
     * @return
     */
    public TrArquivo getTrListaArquivo() {
        return _trListaArquivo;
    }

    /**
     *
     * @param _trListaArquivo
     */
    public void setTrListaArquivo(TrArquivo _trListaArquivo) {
        this._trListaArquivo = _trListaArquivo;
    }

    /**
     *
     * @return
     */
    public List<TrArquivo> getTrListaArquivos() {
        return _trListaArquivos;
    }

    /**
     *
     * @param _trListaArquivos
     */
    public void setTrListaArquivos(List<TrArquivo> _trListaArquivos) {
        this._trListaArquivos = _trListaArquivos;
    }

    /**
     *
     * @return
     */
    public TrArquivo getSelectedArquivo() {
        return _selectedArquivo;
    }

    /**
     *
     * @param _selectedArquivo
     */
    public void setSelectedArquivo(TrArquivo _selectedArquivo) {
        this._selectedArquivo = _selectedArquivo;
    }
}
