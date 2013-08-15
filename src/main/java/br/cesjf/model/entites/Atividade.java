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
import javax.validation.constraints.NotNull;

/**
 *
 * @author heitor.filho
 */
@Entity
public class Atividade implements Serializable {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ATIV")
    private Integer idAtiv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NM_ATIV")
    private double nmAtiv;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "nm_ativi")
    private String nomeAtiv;

    public String getNomeAtiv() {
        return nomeAtiv;
    }

    public void setNomeAtiv(String nomeAtiv) {
        this.nomeAtiv = nomeAtiv;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Atividade)) {
            return false;
        }
        Atividade other = (Atividade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.cesjf.model.entites.Atividade[ id=" + id + " ]";
    }

    public Atividade() {
    }

    public Atividade(Integer idAtiv) {
        this.idAtiv = idAtiv;
    }

    public Atividade(Integer idAtiv, double nmAtiv) {
        this.idAtiv = idAtiv;
        this.nmAtiv = nmAtiv;
    }

    public Integer getIdAtiv() {
        return idAtiv;
    }

    public void setIdAtiv(Integer idAtiv) {
        this.idAtiv = idAtiv;
    }

    public double getNmAtiv() {
        return nmAtiv;
    }

    public void setNmAtiv(double nmAtiv) {
        this.nmAtiv = nmAtiv;
    }

    
}
