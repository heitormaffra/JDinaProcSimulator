/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backing.bean;

import br.cesjf.model.entities.Atividade;
import br.cesjf.model.entities.Desenvolvedor;
import br.cesjf.model.entities.Projeto;
import br.cesjf.util.Equipe;
import br.cesjf.util.Precedente;
import br.cesjf.util.Producao;
import br.cesjf.util.Trabalho;
import java.io.Serializable;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author heitormaffra
 */
@SessionScoped
public class TransferBean implements Serializable {
    Projeto projeto;
    Atividade atividade;
    Desenvolvedor desenv;
    Equipe equipe;
    Precedente precedente;
    Producao producao;
    Trabalho trabalho;
    
    public void cleanFields(){
        producao = null;
        atividade = null;
        desenv = null;
        equipe = null;
        precedente = null;
        producao = null;
        trabalho = null;
    }

    /**
     *
     * @return
     */
    public Equipe getEquipe() {
        return equipe;
    }

    /**
     *
     * @param equipe
     */
    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    /**
     *
     * @return
     */
    public Precedente getPrecedente() {
        return precedente;
    }

    /**
     *
     * @param precedente
     */
    public void setPrecedente(Precedente precedente) {
        this.precedente = precedente;
    }

    /**
     *
     * @return
     */
    public Producao getProducao() {
        return producao;
    }

    /**
     *
     * @param producao
     */
    public void setProducao(Producao producao) {
        this.producao = producao;
    }

    /**
     *
     * @return
     */
    public Trabalho getTrabalho() {
        return trabalho;
    }

    /**
     *
     * @param trabalho
     */
    public void setTrabalho(Trabalho trabalho) {
        this.trabalho = trabalho;
    }

    /**
     *
     * @return
     */
    public Projeto getProjeto() {
        return projeto;
    }

    /**
     *
     * @param projeto
     */
    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    /**
     *
     * @return
     */
    public Atividade getAtividade() {
        return atividade;
    }

    /**
     *
     * @param atividade
     */
    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }

    /**
     *
     * @return
     */
    public Desenvolvedor getDesenv() {
        return desenv;
    }

    /**
     *
     * @param desenv
     */
    public void setDesenv(Desenvolvedor desenv) {
        this.desenv = desenv;
    }
    
    
}
