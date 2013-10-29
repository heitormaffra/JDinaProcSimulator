/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backing.bean.atividade;

import br.cesjf.model.entities.Atividade;
import br.cesjf.model.entities.Desenvolvedor;
import br.cesjf.model.entities.Projeto;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.cesjf.model.dao.AtividadeDao;
import br.cesjf.view.AtividadeConverter;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author heitormaffra
 */
public class AtividadeBean {

    public AtividadeBean() {
        ativdsSelecteds = new ArrayList<Atividade>();
        atividades = new ArrayList<Atividade>();
        atividades.add(new Atividade(1, "Coding", 50d, new Desenvolvedor(1, "Heitor", 1.5f)));
        atividades.add(new Atividade(2, "QA", 10d, new Desenvolvedor(1, "Lucas", 1.5f)));
        atividades.add(new Atividade(3, "Homolog", 30d, new Desenvolvedor(1, "Thiago", 1.5f)));
        atividades.get(1).setAtividadePrecedente(atividades.get(0));
        atividades.get(2).setAtividadePrecedente(atividades.get(1));
        
//        atividades = AtividadeConverter.atividades;
    }
    private String nomeAtividade;
    private Double duracao;
    private Desenvolvedor desenv;
    private Projeto projeto;
    private List<Atividade> atividades;
    private Integer idAtividade;
    private Atividade atividade;
    private Atividade atividadePredecessora;
    private Atividade atividadeSelecionada;
    private List<Atividade> ativdsSelecteds;

    public String getNomeAtividade() {
        return nomeAtividade;
    }

    public void setNomeAtividade(String nomeAtividade) {
        this.nomeAtividade = nomeAtividade;
    }

    public Double getDuracao() {
        return duracao;
    }

    public void setDuracao(Double duracao) {
        this.duracao = duracao;
    }

    public Desenvolvedor getDesenv() {
        return desenv;
    }

    public void setDesenv(Desenvolvedor desenv) {
        this.desenv = desenv;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public List<Atividade> getAtividades() {
//        atividades = new ArrayList<Atividade>();
//        AtividadeDao atvdDao = new AtividadeDao();
//        atividades = atvdDao.findAtividadeEntities();
        return atividades;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }

    public Integer getIdAtividade() {
        return idAtividade;
    }

    public void setIdAtividade(Integer idAtividade) {
        this.idAtividade = idAtividade;
    }

    public Atividade getAtividade() {
        return atividade;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }

    public Atividade getAtividadePredecessora() {
        return atividadePredecessora;
    }

    public void setAtividadePredecessora(Atividade atividadePredecessora) {
        this.atividadePredecessora = atividadePredecessora;
    }

    public Atividade getAtividadeSelecionada() {
        return atividadeSelecionada;
    }

    public void setAtividadeSelecionada(Atividade atividadeSelecionada) {
        this.atividadeSelecionada = atividadeSelecionada;
    }

    public List<Atividade> getAtivdsSelecteds() {
        return ativdsSelecteds;
    }

    public void setAtivdsSelecteds(List<Atividade> ativdsSelecteds) {
        this.ativdsSelecteds = ativdsSelecteds;
    }

    public void criarAtividade() {

        Atividade atividad = new Atividade();
        atividad.setNmAtivd(nomeAtividade);
        atividad.setIdDesenv(desenv);
        atividad.setDuracaoAtivid(duracao);
        atividad.setIdProjeto(projeto);
        atividad.setAtividadePrecedente(atividadePredecessora);

        AtividadeDao ativdDao = new AtividadeDao();

        boolean status = ativdDao.create(atividad);
        if (status == false) {
            FacesContext.getCurrentInstance().addMessage("erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro ao salvar registro."));
        } else {
            FacesContext.getCurrentInstance().addMessage("erro", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro salvo com sucesso!"));
        }
    }

    public void verifica() {
        getAtivdsSelecteds().add(atividade);
        //getAtividades().remove(atividade);
    }
}
