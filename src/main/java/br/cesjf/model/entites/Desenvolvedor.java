/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.model.entites;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "desenvolvedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Desenvolvedor.findAll", query = "SELECT d FROM Desenvolvedor d"),
    @NamedQuery(name = "Desenvolvedor.findByIdDesenv", query = "SELECT d FROM Desenvolvedor d WHERE d.idDesenv = :idDesenv"),
    @NamedQuery(name = "Desenvolvedor.findByNmDsenv", query = "SELECT d FROM Desenvolvedor d WHERE d.nmDsenv = :nmDsenv"),
    @NamedQuery(name = "Desenvolvedor.findByExpDesenv", query = "SELECT d FROM Desenvolvedor d WHERE d.expDesenv = :expDesenv")})
public class Desenvolvedor implements Serializable {
    @OneToMany(mappedBy = "idDesenv")
    private Collection<Equipe> equipeCollection;
    @OneToMany(mappedBy = "idDesenv", fetch = FetchType.LAZY)
    private List<Atividade> atividadeList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DESENV")
    private Integer idDesenv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "NM_DSENV")
    private String nmDsenv;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "EXP_DESENV")
    private Float expDesenv;

    /**
     *
     */
    public Desenvolvedor() {
    }

    /**
     *
     * @param idDesenv
     */
    public Desenvolvedor(Integer idDesenv) {
        this.idDesenv = idDesenv;
    }

    /**
     *
     * @param idDesenv
     * @param nmDsenv
     */
    public Desenvolvedor(Integer idDesenv, String nmDsenv) {
        this.idDesenv = idDesenv;
        this.nmDsenv = nmDsenv;
    }

    /**
     *
     * @return
     */
    public Integer getIdDesenv() {
        return idDesenv;
    }

    /**
     *
     * @param idDesenv
     */
    public void setIdDesenv(Integer idDesenv) {
        this.idDesenv = idDesenv;
    }

    /**
     *
     * @return
     */
    public String getNmDsenv() {
        return nmDsenv;
    }

    /**
     *
     * @param nmDsenv
     */
    public void setNmDsenv(String nmDsenv) {
        this.nmDsenv = nmDsenv;
    }

    /**
     *
     * @return
     */
    public Float getExpDesenv() {
        return expDesenv;
    }

    /**
     *
     * @param expDesenv
     */
    public void setExpDesenv(Float expDesenv) {
        this.expDesenv = expDesenv;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDesenv != null ? idDesenv.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Desenvolvedor)) {
            return false;
        }
        Desenvolvedor other = (Desenvolvedor) object;
        if ((this.idDesenv == null && other.idDesenv != null) || (this.idDesenv != null && !this.idDesenv.equals(other.idDesenv))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.cesjf.model.entites.Desenvolvedor[ idDesenv=" + idDesenv + " ]";
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

    @XmlTransient
    public Collection<Equipe> getEquipeCollection() {
        return equipeCollection;
    }

    public void setEquipeCollection(Collection<Equipe> equipeCollection) {
        this.equipeCollection = equipeCollection;
    }
    
}
