/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.model.entites;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author heitormaffra
 */
@Entity
@Table(name = "atividade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Atividade.findAll", query = "SELECT a FROM Atividade a"),
    @NamedQuery(name = "Atividade.findByIdAtivd", query = "SELECT a FROM Atividade a WHERE a.idAtivd = :idAtivd"),
    @NamedQuery(name = "Atividade.findByNmAtivd", query = "SELECT a FROM Atividade a WHERE a.nmAtivd = :nmAtivd")})
public class Atividade implements Serializable {
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DURACAO_ATIVID")
    private Double duracaoAtivid;
    @JoinColumn(name = "ID_PROJETO", referencedColumnName = "ID_PROJETO")
    @ManyToOne
    private Projeto idProjeto;
    @JoinColumn(name = "ID_DESENV", referencedColumnName = "ID_DESENV")
    @ManyToOne
    private Desenvolvedor idDesenv;
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

    /**
     *
     */
    public Atividade() {
    }

    /**
     *
     * @param idAtivd
     */
    public Atividade(Integer idAtivd) {
        this.idAtivd = idAtivd;
    }

    /**
     *
     * @param idAtivd
     * @param nmAtivd
     */
    public Atividade(Integer idAtivd, String nmAtivd) {
        this.idAtivd = idAtivd;
        this.nmAtivd = nmAtivd;
    }

    /**
     *
     * @return
     */
    public Integer getIdAtivd() {
        return idAtivd;
    }

    /**
     *
     * @param idAtivd
     */
    public void setIdAtivd(Integer idAtivd) {
        this.idAtivd = idAtivd;
    }

    /**
     *
     * @return
     */
    public String getNmAtivd() {
        return nmAtivd;
    }

    /**
     *
     * @param nmAtivd
     */
    public void setNmAtivd(String nmAtivd) {
        this.nmAtivd = nmAtivd;
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
        return "br.cesjf.model.entites.Atividade[ idAtivd=" + idAtivd + " ]";
    }

    /**
     *
     * @return
     */
    public Double getDuracaoAtivid() {
        return duracaoAtivid;
    }

    /**
     *
     * @param duracaoAtivid
     */
    public void setDuracaoAtivid(Double duracaoAtivid) {
        this.duracaoAtivid = duracaoAtivid;
    }

    /**
     *
     * @return
     */
    public Projeto getIdProjeto() {
        return idProjeto;
    }

    /**
     *
     * @param idProjeto
     */
    public void setIdProjeto(Projeto idProjeto) {
        this.idProjeto = idProjeto;
    }

    /**
     *
     * @return
     */
    public Desenvolvedor getIdDesenv() {
        return idDesenv;
    }

    /**
     *
     * @param idDesenv
     */
    public void setIdDesenv(Desenvolvedor idDesenv) {
        this.idDesenv = idDesenv;
    }
    
}
