/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backing.bean.desenvolvedor;

import br.cesjf.model.dao.DesenvolvedorDao;
import br.cesjf.model.dao.exceptions.NonexistentEntityException;
import br.cesjf.model.entities.Desenvolvedor;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author heitormaffra
 */
@ViewScoped
@ManagedBean(name = "desenvolvedorBean")
public class DesenvolvedorBean {

    private String nomeDesenv;
    private Double artefato;
    private List<Desenvolvedor> listDesenv;
    private Desenvolvedor desenvSelcionado;
    private List<Desenvolvedor> desenvFiltrado;
    private DataModel rowsData;

    public DataModel getRowData() {
        return rowsData;
    }

    public void setRowData(DataModel rowData) {
        this.rowsData = rowData;
    }

    public List<Desenvolvedor> getDesenvFiltrado() {
        return desenvFiltrado;
    }

    public void setDesenvFiltrado(List<Desenvolvedor> desenvFiltrado) {
        this.desenvFiltrado = desenvFiltrado;
    }

    public Desenvolvedor getDesenvSelcionado() {
        return desenvSelcionado;
    }

    public void setDesenvSelcionado(Desenvolvedor desenvSelcionado) {
        this.desenvSelcionado = desenvSelcionado;
    }

    public List<Desenvolvedor> getListDesenv() {
        DesenvolvedorDao desenvDao = new DesenvolvedorDao();
        listDesenv = desenvDao.findDesenvolvedorEntities();
        return listDesenv;
    }

    public void setListDesenv(List<Desenvolvedor> listDesenv) {
        this.listDesenv = listDesenv;
    }

    public String getNomeDesenv() {
        return nomeDesenv;
    }

    public void setNomeDesenv(String nomeDesenv) {
        this.nomeDesenv = nomeDesenv;
    }

    public Double getArtefato() {
        return artefato;
    }

    public void setArtefato(Double artefato) {
        this.artefato = artefato;
    }

    public void criaDesenv() {
        Desenvolvedor desenvolvedor = new Desenvolvedor();
        desenvolvedor.setNmDsenv(nomeDesenv);
        desenvolvedor.setExpDesenv(artefato.floatValue());

        DesenvolvedorDao desenvDao = new DesenvolvedorDao();
        try {
            desenvDao.create(desenvolvedor);
            setNomeDesenv("");
            setArtefato(null);
            FacesContext.getCurrentInstance().addMessage("sucesso", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Desenvolvedor " + nomeDesenv + " salvo com sucesso!"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage("erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro ao salvar registro." + ex.getMessage()));
        }
    }

    public void deletaDesenv() {

        try {
            //resultFluxos = new ListDataModel(recebimentoNotaDao.findResultNotaSearch(atributosPesquisa));
            desenvSelcionado.setNmDsenv(nomeDesenv);
            desenvSelcionado.setExpDesenv(artefato.floatValue());
            DesenvolvedorDao desenvDao = new DesenvolvedorDao();
            desenvDao.destroy(desenvSelcionado.getIdDesenv());
            FacesContext.getCurrentInstance().addMessage("sucesso", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Desenvolvedor " + desenvSelcionado.getNmDsenv() + " deletado com sucesso!"));
        } catch (NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage("erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro ao deletar registro." + ex.getMessage()));
            Logger.getLogger(DesenvolvedorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editaDesenv() {

        try {
            //resultFluxos = new ListDataModel(recebimentoNotaDao.findResultNotaSearch(atributosPesquisa));
            desenvSelcionado.setNmDsenv(nomeDesenv);
            desenvSelcionado.setExpDesenv(artefato.floatValue());
            rowsData = new ListDataModel(getListDesenv());
            DesenvolvedorDao desenvDao = new DesenvolvedorDao();
            desenvDao.edit(desenvSelcionado);
            FacesContext.getCurrentInstance().addMessage("sucesso", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Desenvolvedor " + desenvSelcionado.getNmDsenv() + " editado com sucesso!"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage("erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro ao editar registro." + ex.getMessage()));
            Logger.getLogger(DesenvolvedorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
