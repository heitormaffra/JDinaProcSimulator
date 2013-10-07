/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backing.bean.projeto;

import br.cesjf.model.dao.AtividadeDao;
import br.cesjf.model.entities.Projeto;
import br.cesjf.model.dao.ProjetoDao;
import br.cesjf.model.entities.Atividade;
import br.cesjf.model.entities.exceptions.PreexistingEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author heitormaffra
 */
@ViewScoped
@ManagedBean(name = "projetoBean")
public class ProjetoBean {

    /**
     * Creates a new instance of ProjetoBean
     */
    public ProjetoBean() {
    }
    private Projeto projeto;
    private List<Projeto> projetos;
    private String nomeProjeto;
    private Integer idAtividade;
    private List<Atividade> atividades;

    public List<Atividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<Atividade> atividade) {
        this.atividades = atividade;
    }

    public Integer getIdAtividade() {
        return idAtividade;
    }

    public void setIdAtividade(Integer idAtividade) {
        this.idAtividade = idAtividade;
    }

    //private List<String> atividades;
    public String getNomeProjeto() {
        return nomeProjeto;
    }

    public void setNomeProjeto(String nomeProjeto) {
        this.nomeProjeto = nomeProjeto;
    }
    
    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public List<Projeto> getProjetos() {
        ProjetoDao projetoDao = new ProjetoDao();
        projetos = projetoDao.findProjetoEntities();
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }

    public List<Projeto> preencheProjetos() {

        ProjetoDao projetoDao = new ProjetoDao();
        projetos = projetoDao.findProjetoEntities();
        return projetos;
    }

    public String findProjetosfindById(ValueChangeEvent event) {
        
        Integer idProjeto = event != null ? (Integer) event.getNewValue() : null;
        ProjetoDao projetoDao = new ProjetoDao();
        if (idProjeto != null) {
            setProjeto(projetoDao.findProjeto(idProjeto));
        } else {
            setProjeto(null);
        }
        return null;
    }
    
    public void criarProjeto(){

        projeto = new Projeto();
        projeto.setNmProjeto(nomeProjeto);
        
        ProjetoDao projetoDao = new ProjetoDao();

        try {
            projetoDao.create(projeto);
            FacesContext.getCurrentInstance().addMessage("Sucesso", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Projeto " + nomeProjeto + " salvo com sucesso!"));
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(ProjetoBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage("erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro ao salvar registro."));
        } catch (Exception ex) {
            Logger.getLogger(ProjetoBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage("erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro ao salvar registro."));
        }
        
    }
}
