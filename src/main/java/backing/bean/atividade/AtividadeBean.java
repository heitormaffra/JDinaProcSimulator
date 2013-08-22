/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backing.bean.atividade;

import br.cesjf.model.entites.Atividade;
import br.cesjf.model.entites.Desenvolvedor;
import br.cesjf.model.entites.Projeto;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import backing.bean.TransferBean;
import br.cesjf.model.dao.AtividadeDao;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author heitormaffra
 */
@ViewScoped
@ManagedBean(name = "atividade")
public class AtividadeBean {
     private String nomeAtividade;
     private Double duracao;
     private Desenvolvedor desenv;
     private Projeto projeto;
     private TransferBean transferBean;

    /**
     *
     * @return
     */
    public String getNomeAtividade() {
        return nomeAtividade;
    }

    /**
     *
     * @param nomeAtividade
     */
    public void setNomeAtividade(String nomeAtividade) {
        this.nomeAtividade = nomeAtividade;
    }

    /**
     *
     * @return
     */
    public Double getDuracao() {
        return duracao;
    }

    /**
     *
     * @param duracao
     */
    public void setDuracao(Double duracao) {
        this.duracao = duracao;
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
     */
    public void criarAtividade(){
        Atividade atividade = new Atividade();
        atividade.setNmAtivd(nomeAtividade);
        atividade.setIdDesenv(desenv);
        atividade.setIdProjeto(projeto);
        transferBean.setAtividade(atividade);
        AtividadeDao ativdDao = new AtividadeDao();
        boolean status = ativdDao.create(atividade);
        if(status == false){
            FacesContext.getCurrentInstance().addMessage("erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro ao salvar registro."));
        } else{
            FacesContext.getCurrentInstance().addMessage("erro", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro salvo com sucesso!"));
        }
    }
     
}
