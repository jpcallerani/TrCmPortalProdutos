package br.tr.com.Modal;

import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TR_ARQUIVO_DOWNLOAD")
@XmlRootElement
public class TrArquivoDownload implements Serializable {

    @Transient
    @Basic(optional = true)
    private InputStream dados;
    @Basic(optional = false)
    @Column(name = "ARQUIVO")
    private String arquivo;
    @Basic(optional = false)
    @Column(name = "TAMANHO_ARQUIVO")
    private long tamanhoArquivo;
    @Basic(optional = false)
    @Column(name = "NOME_ARQUIVO")
    private String nomeArquivo;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Basic(optional = false)
    @Column(name = "ID_ARQUIVO_DOWNLOAD")
    private BigDecimal idArquivoDownload;
    @Column(name = "CONTENT_TYPE")
    private String contentType;
    @JoinColumn(name = "ID_ARQUIVO", referencedColumnName = "ID_ARQUIVO")
    @ManyToOne(optional = false)
    private TrArquivo idArquivo;

    public TrArquivoDownload() {
    }

    public TrArquivoDownload(BigDecimal idArquivoDownload) {
        this.idArquivoDownload = idArquivoDownload;
    }

    public TrArquivoDownload(BigDecimal idArquivoDownload, String arquivo) {
        this.idArquivoDownload = idArquivoDownload;
        this.arquivo = arquivo;
    }

    public BigDecimal getIdArquivoDownload() {
        return idArquivoDownload;
    }

    public void setIdArquivoDownload(BigDecimal idArquivoDownload) {
        this.idArquivoDownload = idArquivoDownload;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public TrArquivo getIdArquivo() {
        return idArquivo;
    }

    public void setIdArquivo(TrArquivo idArquivo) {
        this.idArquivo = idArquivo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArquivoDownload != null ? idArquivoDownload.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrArquivoDownload)) {
            return false;
        }
        TrArquivoDownload other = (TrArquivoDownload) object;
        if ((this.idArquivoDownload == null && other.idArquivoDownload != null) || (this.idArquivoDownload != null && !this.idArquivoDownload.equals(other.idArquivoDownload))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.tr.com.Modal.TrArquivoDownload[ idArquivoDownload=" + idArquivoDownload + " ]";
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public long getTamanhoArquivo() {
        return tamanhoArquivo;
    }

    public void setTamanhoArquivo(long tamanhoArquivo) {
        this.tamanhoArquivo = tamanhoArquivo;
    }

    public InputStream getDados() {
        return dados;
    }

    public void setDados(InputStream dados) {
        this.dados = dados;
    }
}
