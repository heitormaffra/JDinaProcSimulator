/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backing.bean;

import br.cesjf.util.MetaModel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author heitor.filho
 */
@ViewScoped
@ManagedBean(name = "indexBean")
public class IndexBean {

    private static final Logger logger = Logger.getLogger(IndexBean.class.getName());
    private String nomeProjeto;
    private List<String> atividades;
    private List<MetaModel> desenvolvedores;
    private DataModel desenvModel;
    private String nomeDesenv;
    private Double artefatoDesenv;

    public IndexBean() {
        desenvolvedores = new ArrayList<MetaModel>();
    }

    public String getNomeDesenv() {
        return nomeDesenv;
    }

    public void setNomeDesenv(String nomeDesenv) {
        this.nomeDesenv = nomeDesenv;
    }

    public Double getArtefatoDesenv() {
        return artefatoDesenv;
    }

    public void setArtefatoDesenv(Double artefatoDesenv) {
        this.artefatoDesenv = artefatoDesenv;
    }
    
    public DataModel getDesenvModel() {
        return desenvModel;
    }

    public void setDesenvModel(DataModel desenvModel) {
        this.desenvModel = desenvModel;
    }

    public List<MetaModel> getDesenvolvedores() {
        return desenvolvedores;
    }

    public void setDesenvolvedores(List<MetaModel> desenvolvedores) {
        this.desenvolvedores = desenvolvedores;
    }

    public List<String> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<String> atividades) {
        this.atividades = atividades;
    }

    public String getNomeProjeto() {
        return nomeProjeto;
    }

    public void setNomeProjeto(String nomeProjeto) {
        this.nomeProjeto = nomeProjeto;
    }

    public void save(ActionEvent actionEvent) {
        //Persist user  

        FacesMessage msg = new FacesMessage("Successful", "Welcome :");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String onFlowProcess(FlowEvent event) {
        logger.log(Level.INFO, "Current wizard step:{0}", event.getOldStep());
        logger.log(Level.INFO, "Next step:{0}", event.getNewStep());
        boolean skip = false;

        if (skip) {
            skip = false;   //reset in case user goes back  
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }
    
    public final void createNewDesenvRow(){
        desenvModel = new ListDataModel(desenvolvedores);
    }
    
    DataModel model;
    
    public final void addNewDesenvRow(){
        MetaModel meta = new MetaModel(nomeDesenv, new Double(artefatoDesenv));
        desenvolvedores.add(meta);
       model = new ListDataModel(desenvolvedores);
    }
    
    public DataModel getDesenvolvedoresList() {
        return model;
    }
}
