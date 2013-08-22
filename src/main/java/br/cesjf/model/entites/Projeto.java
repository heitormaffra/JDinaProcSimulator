/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.model.entites;

import br.cesjf.model.entites.Atividade;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "projeto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Projeto.findAll", query = "SELECT p FROM Projeto p"),
    @NamedQuery(name = "Projeto.findByIdProjeto", query = "SELECT p FROM Projeto p WHERE p.idProjeto = :idProjeto"),
    @NamedQuery(name = "Projeto.findByNmProjeto", query = "SELECT p FROM Projeto p WHERE p.nmProjeto = :nmProjeto")})
public class Projeto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROJETO")
    private Integer idProjeto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NM_PROJETO")
    private String nmProjeto;
    @OneToMany(mappedBy = "idProjeto")
    private List<Atividade> atividadeList;

    /**
     *
     */
    public Projeto() {
    }

    /**
     *
     * @param idProjeto
     */
    public Projeto(Integer idProjeto) {
        this.idProjeto = idProjeto;
    }

    /**
     *
     * @param idProjeto
     * @param nmProjeto
     */
    public Projeto(Integer idProjeto, String nmProjeto) {
        this.idProjeto = idProjeto;
        this.nmProjeto = nmProjeto;
    }

    /**
     *
     * @return
     */
    public Integer getIdProjeto() {
        return idProjeto;
    }

    /**
     *
     * @param idProjeto
     */
    public void setIdProjeto(Integer idProjeto) {
        this.idProjeto = idProjeto;
    }

    /**
     *
     * @return
     */
    public String getNmProjeto() {
        return nmProjeto;
    }

    /**
     *
     * @param nmProjeto
     */
    public void setNmProjeto(String nmProjeto) {
        this.nmProjeto = nmProjeto;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<Atividade> getAtividadeList() {
        return atividadeList;
    }

    /**
     *
     * @param atividadeList
     */
    public void setAtividadeList(List<Atividade> atividadeList) {
        this.atividadeList = atividadeList;
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
        return "backing.bean.Projeto[ idProjeto=" + idProjeto + " ]";
    }
    
}
