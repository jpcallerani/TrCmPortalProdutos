package br.tr.com.Modal;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TR_ARQUIVO")
@XmlRootElement
public class TrArquivo implements Serializable {

    @Basic(optional = false)
    @Transient
    private String arquivo;
    @Basic(optional = false)
    @Column(name = "DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idArquivo")
    private Collection<TrArquivoDownload> trArquivoDownloadCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Basic(optional = false)
    @Column(name = "ID_ARQUIVO")
    private Integer idArquivo;
    @Basic(optional = false)
    @Column(name = "NOME_ARQUIVO")
    private String nomeArquivo;
    @JoinColumn(name = "ID_CLASSIFICACAO", referencedColumnName = "ID_CLASSIFICACAO")
    @ManyToOne
    private TrClassificacao idClassificacao;
    @JoinColumn(name = "ID_SESSAO", referencedColumnName = "ID_SESSAO")
    @ManyToOne
    private TrSessao idSessao;

    public TrArquivo() {
    }

    public TrArquivo(Integer idArquivo) {
        this.idArquivo = idArquivo;
    }

    public TrArquivo(Integer idArquivo, String nomeArquivo) {
        this.idArquivo = idArquivo;
        this.nomeArquivo = nomeArquivo;
    }

    public Integer getIdArquivo() {
        return idArquivo;
    }

    public void setIdArquivo(Integer idArquivo) {
        this.idArquivo = idArquivo;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public TrClassificacao getIdClassificacao() {
        return idClassificacao;
    }

    public void setIdClassificacao(TrClassificacao idClassificacao) {
        this.idClassificacao = idClassificacao;
    }

    public TrSessao getIdSessao() {
        return idSessao;
    }

    public void setIdSessao(TrSessao idSessao) {
        this.idSessao = idSessao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArquivo != null ? idArquivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrArquivo)) {
            return false;
        }
        TrArquivo other = (TrArquivo) object;
        if ((this.idArquivo == null && other.idArquivo != null) || (this.idArquivo != null && !this.idArquivo.equals(other.idArquivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.tr.com.Modal.TrArquivo[ idArquivo=" + idArquivo + " ]";
    }

    @XmlTransient
    public Collection<TrArquivoDownload> getTrArquivoDownloadCollection() {
        return trArquivoDownloadCollection;
    }

    public void setTrArquivoDownloadCollection(Collection<TrArquivoDownload> trArquivoDownloadCollection) {
        this.trArquivoDownloadCollection = trArquivoDownloadCollection;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }
}
