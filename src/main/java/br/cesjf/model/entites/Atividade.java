/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.model.entites;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @NamedQuery(name = "Atividade.findByNmAtivd", query = "SELECT a FROM Atividade a WHERE a.nmAtivd = :nmAtivd"),
    @NamedQuery(name = "Atividade.findByIdDesenv", query = "SELECT a FROM Atividade a WHERE a.idDesenv = :idDesenv")})
public class Atividade implements Serializable {
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
    @Size(max = 45)
    @Column(name = "ID_DESENV")
    private String idDesenv;

    public Atividade() {
    }

    public Atividade(Integer idAtivd) {
        this.idAtivd = idAtivd;
    }

    public Atividade(Integer idAtivd, String nmAtivd) {
        this.idAtivd = idAtivd;
        this.nmAtivd = nmAtivd;
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

    public String getIdDesenv() {
        return idDesenv;
    }

    public void setIdDesenv(String idDesenv) {
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
        return "br.cesjf.model.entites.Atividade[ idAtivd=" + idAtivd + " ]";
    }
    
}
