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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author heitor.filho
 */
@Entity
@Table(name = "DESENVOLVEDOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Desenvolvedor.findAll", query = "SELECT d FROM Desenvolvedor d"),
    @NamedQuery(name = "Desenvolvedor.findByNmDesenv", query = "SELECT d FROM Desenvolvedor d WHERE d.nmDesenv = :nmDesenv"),
    @NamedQuery(name = "Desenvolvedor.findByIdDesenv", query = "SELECT d FROM Desenvolvedor d WHERE d.idDesenv = :idDesenv"),
    @NamedQuery(name = "Desenvolvedor.findByExpDesenv", query = "SELECT d FROM Desenvolvedor d WHERE d.expDesenv = :expDesenv")})
public class Desenvolvedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DESENV")
    private Integer idDesenv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "NM_DESENV")
    private String nomeDesenv;
    @Column(name = "EXP_DESENV")
    private Double expDesenv;

    public String getNomeDesenv() {
        return nomeDesenv;
    }

    public void setNomeDesenv(String nomeDesenv) {
        this.nomeDesenv = nomeDesenv;
    }

    public Integer getIdDesenv() {
        return idDesenv;
    }

    public void setIdDesenv(Integer idDesenv) {
        this.idDesenv = idDesenv;
    }

    public Double getExpDesenv() {
        return expDesenv;
    }

    public void setExpDesenv(Double expDesenv) {
        this.expDesenv = expDesenv;
    }
}
