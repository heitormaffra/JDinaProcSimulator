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
import backing.bean.TransferBean;
import br.cesjf.model.dao.AtividadeDao;
import br.cesjf.model.dao.DesenvolvedorDao;
import br.cesjf.model.dao.ProjetoDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author heitormaffra
 */
@ViewScoped
@ManagedBean(name = "atividade")
public class AtividadeBean {

    public AtividadeBean() {
        ativdsSelecteds = new ArrayList<Atividade>();
        atividades = new ArrayList<Atividade>();
        atividades.add(new Atividade(1, "Coding", 20.0, new Desenvolvedor(1, "Heitor")));
    }
    private String nomeAtividade;
    private Double duracao;
    private Desenvolvedor desenv;
    private Projeto projeto;
    private List<Atividade> atividades;
    private Integer idAtividade;
    private Integer idDesenvolvedor;
    private Integer idProjeto;
    private Atividade atividade;
    private List<Atividade> atividadesPredecessora;
    private Atividade atividadePredecessora;
    private Atividade atividadeSelecionada;
    private List<Atividade> ativdsSelecteds;
    TransferBean transfer;

    public List<Atividade> getAtivdsSelecteds() {
        return ativdsSelecteds;
    }

    public void setAtivdsSelecteds(List<Atividade> AtivdsSelecteds) {
        this.ativdsSelecteds = AtivdsSelecteds;
    }

    public Atividade getAtividadeSelecionada() {
        return atividadeSelecionada;
    }

    public void setAtividadeSelecionada(Atividade atividadeSelecionada) {
        this.atividadeSelecionada = atividadeSelecionada;
    }

    public Atividade getAtividadePredecessora() {
        return atividadePredecessora;
    }

    public void setAtividadePredecessora(Atividade atividadePredecessora) {
        this.atividadePredecessora = atividadePredecessora;
    }

    public List<Atividade> getAtividadesPredecessora() {
        AtividadeDao atvDao = new AtividadeDao();
        atividadesPredecessora = atvDao.findAtividadeEntities();
        return atividadesPredecessora;
    }

    public void setAtividadesPredecessora(List<Atividade> atividadesPredecessora) {
        this.atividadesPredecessora = atividadesPredecessora;
    }

    public Atividade getAtividade() {
        return atividade;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }

    public Integer getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(Integer idProjeto) {
        this.idProjeto = idProjeto;
    }

    public Integer getIdDesenvolvedor() {
        return idDesenvolvedor;
    }

    public void setIdDesenvolvedor(Integer idDesenvolvedor) {
        this.idDesenvolvedor = idDesenvolvedor;
    }

    public List<Atividade> getAtividades() {
        //AtividadeDao atividadeDao = new AtividadeDao();
        //atividades = atividadeDao.findAtividadeEntities();
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
    public void criarAtividade() {

        DesenvolvedorDao desenvDao = new DesenvolvedorDao();
        desenv = desenvDao.findDesenvolvedor(idDesenvolvedor);


        Atividade atividad = new Atividade();
        atividad.setNmAtivd(nomeAtividade);
        atividad.setIdDesenv(desenv);
        atividad.setDuracaoAtivid(duracao);
        atividad.setIdProjeto(null);
        atividad.setAtividadePrecedente(atividadePredecessora);

        AtividadeDao ativdDao = new AtividadeDao();

        boolean status = ativdDao.create(atividad);
        if (status == false) {
            FacesContext.getCurrentInstance().addMessage("erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro ao salvar registro."));
        } else {
            FacesContext.getCurrentInstance().addMessage("erro", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro salvo com sucesso!"));
        }
    }

    public String findAtividadefindById(ValueChangeEvent event) {

        Integer idAtividade = event != null ? (Integer) event.getNewValue() : null;
        AtividadeDao atividadeDao = new AtividadeDao();
        if (idAtividade != null) {
            setAtividade(atividadeDao.findAtividade(idAtividade));
        } else {
            setAtividade(null);
        }
        return null;
    }

    public void verifica() {
        getAtivdsSelecteds().add(atividade);
    }

    public void nextStep() {
        transfer = new TransferBean();
        transfer.setAtividades(getAtivdsSelecteds());
        try {
            FacesContext.getCurrentInstance().
                    getExternalContext().
                    redirect("equipe.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(AtividadeBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean renderedAtividadesSelec() {
        if (getAtivdsSelecteds().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void editar() {
        int i = atividades.indexOf(atividadeSelecionada);

        atividades.get(i).setIdDesenv(desenv);
        atividades.get(i).setAtividadePrecedente(atividadePredecessora);
    }
}
