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

/**
 *
 * @author U0180463
 */
@Entity
@Table(name = "TR_CLASSIFICACAO")
@XmlRootElement
public class TrClassificacao implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Basic(optional = false)
    @Column(name = "ID_CLASSIFICACAO")
    private Integer idClassificacao;
    @Basic(optional = false)
    @Column(name = "DESCRICAO")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idClassificacao")
    private List<TrArquivo> trArquivoList;

    public TrClassificacao() {
    }

    public TrClassificacao(Integer idClassificacao) {
        this.idClassificacao = idClassificacao;
    }

    public TrClassificacao(Integer idClassificacao, String descricao) {
        this.idClassificacao = idClassificacao;
        this.descricao = descricao;
    }

    public Integer getIdClassificacao() {
        return idClassificacao;
    }

    public void setIdClassificacao(Integer idClassificacao) {
        this.idClassificacao = idClassificacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        hash += (idClassificacao != null ? idClassificacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrClassificacao)) {
            return false;
        }
        TrClassificacao other = (TrClassificacao) object;
        if ((this.idClassificacao == null && other.idClassificacao != null) || (this.idClassificacao != null && !this.idClassificacao.equals(other.idClassificacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.tr.com.Modal.TrClassificacao[ idClassificacao=" + idClassificacao + " ]";
    }
    
}
