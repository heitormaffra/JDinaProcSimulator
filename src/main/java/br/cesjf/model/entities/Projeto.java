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
@Table(name = "projeto", catalog = "dinaprocsimu", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Projeto.findAll", query = "SELECT p FROM Projeto p"),
    @NamedQuery(name = "Projeto.findByIdProjeto", query = "SELECT p FROM Projeto p WHERE p.idProjeto = :idProjeto"),
    @NamedQuery(name = "Projeto.findByNmProjeto", query = "SELECT p FROM Projeto p WHERE p.nmProjeto = :nmProjeto")})
public class Projeto implements Serializable {
    @OneToMany(mappedBy = "idProjeto")
    private Collection<Atividade> atividadeCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PROJETO", nullable = false)
    private Integer idProjeto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "NM_PROJETO", nullable = false, length = 45)
    private String nmProjeto;

    public Projeto() {
    }

    public Projeto(Integer idProjeto) {
        this.idProjeto = idProjeto;
    }

    public Projeto(Integer idProjeto, String nmProjeto) {
        this.idProjeto = idProjeto;
        this.nmProjeto = nmProjeto;
    }

    public Integer getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(Integer idProjeto) {
        this.idProjeto = idProjeto;
    }

    public String getNmProjeto() {
        return nmProjeto;
    }

    public void setNmProjeto(String nmProjeto) {
        this.nmProjeto = nmProjeto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProjeto != null ? idProjeto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Projeto)) {
            return false;
        }
        Projeto other = (Projeto) object;
        if ((this.idProjeto == null && other.idProjeto != null) || (this.idProjeto != null && !this.idProjeto.equals(other.idProjeto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.cesjf.model.entities.Projeto[ idProjeto=" + idProjeto + " ]";
    }

    @XmlTransient
    public Collection<Atividade> getAtividadeCollection() {
        return atividadeCollection;
    }

    public void setAtividadeCollection(Collection<Atividade> atividadeCollection) {
        this.atividadeCollection = atividadeCollection;
    }
    
}
