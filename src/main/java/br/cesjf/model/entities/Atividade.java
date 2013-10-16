/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.model.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author heitormaffra
 */
@Entity
@Table(name = "atividade", catalog = "dinaprocsimu", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Atividade.findAll", query = "SELECT a FROM Atividade a"),
    @NamedQuery(name = "Atividade.findByIdAtivd", query = "SELECT a FROM Atividade a WHERE a.idAtivd = :idAtivd"),
    @NamedQuery(name = "Atividade.findByNmAtivd", query = "SELECT a FROM Atividade a WHERE a.nmAtivd = :nmAtivd"),
    @NamedQuery(name = "Atividade.findByDuracaoAtivid", query = "SELECT a FROM Atividade a WHERE a.duracaoAtivid = :duracaoAtivid")})
public class Atividade implements Serializable {
    @OneToMany(mappedBy = "atividadePrecedente")
    private Collection<Atividade> atividadeCollection;
    @JoinColumn(name = "ATIVIDADE_PRECEDENTE", referencedColumnName = "ID_ATIVD")
    @ManyToOne
    private Atividade atividadePrecedente;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ATIVD")
    private Integer idAtivd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "NM_ATIVD")
    private String nmAtivd;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DURACAO_ATIVID")
    private Double duracaoAtivid;
    @JoinColumn(name = "ID_PROJETO", referencedColumnName = "ID_PROJETO")
    @ManyToOne
    private Projeto idProjeto;
    @JoinColumn(name = "ID_DESENV", referencedColumnName = "ID_DESENV")
    @ManyToOne
    private Desenvolvedor idDesenv;

    public Atividade() {
    }

    public Atividade(Integer idAtivd) {
        this.idAtivd = idAtivd;
    }

    public Atividade(Integer idAtivd, String nmAtivd) {
        this.idAtivd = idAtivd;
        this.nmAtivd = nmAtivd;
    }
    
    public Atividade(Integer idAtivd, String nmAtivd, Double duracaoAtv, Desenvolvedor idDesenv) {
        this.idAtivd = idAtivd;
        this.nmAtivd = nmAtivd;
        this.duracaoAtivid = duracaoAtv;
        this.idDesenv = idDesenv;
    }

    public Integer getIdAtivd() {
        return idAtivd;
    }

    public void setIdAtivd(Integer idAtivd) {
        this.idAtivd = idAtivd;
    }

    public String getNmAtivd() {
        return nmAtivd;
    }

    public void setNmAtivd(String nmAtivd) {
        this.nmAtivd = nmAtivd;
    }

    public Double getDuracaoAtivid() {
        return duracaoAtivid;
    }

    public void setDuracaoAtivid(Double duracaoAtivid) {
        this.duracaoAtivid = duracaoAtivid;
    }

    public Projeto getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(Projeto idProjeto) {
        this.idProjeto = idProjeto;
    }

    public Desenvolvedor getIdDesenv() {
        return idDesenv;
    }

    public void setIdDesenv(Desenvolvedor idDesenv) {
        this.idDesenv = idDesenv;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAtivd != null ? idAtivd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Atividade)) {
            return false;
        }
        Atividade other = (Atividade) object;
        if ((this.idAtivd == null && other.idAtivd != null) || (this.idAtivd != null && !this.idAtivd.equals(other.idAtivd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.cesjf.model.entities.Atividade[ idAtivd=" + idAtivd + " ]";
    }

    @XmlTransient
    public Collection<Atividade> getAtividadeCollection() {
        return atividadeCollection;
    }

    public void setAtividadeCollection(Collection<Atividade> atividadeCollection) {
        this.atividadeCollection = atividadeCollection;
    }

    public Atividade getAtividadePrecedente() {
        return atividadePrecedente;
    }

    public void setAtividadePrecedente(Atividade atividadePrecedente) {
        this.atividadePrecedente = atividadePrecedente;
    }
    
}
