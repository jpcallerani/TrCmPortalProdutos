package br.tr.com.Modal;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TR_SESSAO")
@XmlRootElement
public class TrSessao implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Basic(optional = false)
    @Column(name = "ID_SESSAO")
    private Integer idSessao;
    @Basic(optional = false)
    @Column(name = "NOME_SESSAO")
    private String nomeSessao;
    @Column(name = "OBSERVACAO")
    private String observacao;
    @Column(name = "VALIDO")
    private String valido;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSessao", fetch = FetchType.EAGER)
    private List<TrArquivo> trArquivoList;
    
    public TrSessao() {
    }

    public TrSessao(Integer idSessao) {
        this.idSessao = idSessao;
    }

    public TrSessao(Integer idSessao, String nomeSessao) {
        this.idSessao = idSessao;
        this.nomeSessao = nomeSessao;
    }

    public Integer getIdSessao() {
        return idSessao;
    }

    public void setIdSessao(Integer idSessao) {
        this.idSessao = idSessao;
    }

    public String getNomeSessao() {
        return nomeSessao;
    }

    public void setNomeSessao(String nomeSessao) {
        this.nomeSessao = nomeSessao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getValido() {
        return valido;
    }

    public void setValido(String valido) {
        this.valido = valido;
    }

    @XmlTransient
    public List<TrArquivo> getTrArquivoList() {
        return trArquivoList;
    }

    public void setTrArquivoList(List<TrArquivo> trArquivoList) {
        this.trArquivoList = trArquivoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSessao != null ? idSessao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrSessao)) {
            return false;
        }
        TrSessao other = (TrSessao) object;
        if ((this.idSessao == null && other.idSessao != null) || (this.idSessao != null && !this.idSessao.equals(other.idSessao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.tr.com.Modal.TrSessao[ idSessao=" + idSessao + " ]";
    }

}
