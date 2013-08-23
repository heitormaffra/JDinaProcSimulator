/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backing.bean.desenvolvedor;

//import br.cesjf.model.dao.DesenvolvedorDao;
//import br.cesjf.model.entites.Desenvolvedor;
import br.cesjf.model.dao.DesenvolvedorDao;
import br.cesjf.model.entites.Desenvolvedor;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author heitor.filho
 */
@ViewScoped
@ManagedBean(name = "desenvolvedor")
public class DesenvolvedorBean {

    private String nomeDesenv;
    private Float expeDesenv;
    private List<Desenvolvedor> desenv;
    
    private List<Desenvolvedor> desenvs;

    /**
     *
     * @return
     */
    public List<Desenvolvedor> getDesenvolvedores() {
        DesenvolvedorDao desenvDao = new DesenvolvedorDao();
        desenvs = desenvDao.findDesenvolvedorEntities();
        return desenvs;
    }

    /**
     *
     * @param desenvs
     */
    public void setDesenvs(List<Desenvolvedor> desenvs) {
        this.desenvs = desenvs;
    }

    /**
     *
     * @return
     */
    public List<Desenvolvedor> getDesenv() {
        DesenvolvedorDao desevDao = new DesenvolvedorDao();
        desenv = desevDao.findDesenvolvedorEntities();
        return desenv;
    }

    /**
     *
     * @param desenv
     */
    public void setDesenv(List<Desenvolvedor> desenv) {
        this.desenv = desenv;
    }

    /**
     * Returna o nome do desenvolvedor
     *
     * @return nomeDesenv
     */
    public String getNomeDesenv() {
        return nomeDesenv;
    }

    /**
     * Seta o nome do desenvolvedor.
     *
     * @param nomeDesenv
     */
    public void setNomeDesenv(String nomeDesenv) {
        this.nomeDesenv = nomeDesenv;
    }

    /**
     * Retorna o valor da experiência do desenvolvedor
     *
     * @return expeDesenv
     */
    public Float getExpeDesenv() {
        return expeDesenv;
    }

    /**
     * Setar valor para experiência do desenvolvedor.
     *
     * @param expeDesenv
     */
    public void setExpeDesenv(Float expeDesenv) {
        this.expeDesenv = expeDesenv;
    }

    /**
     * Método para criar um novo desenvolvedor.
     */
    public void criaDesenv() {
        Desenvolvedor desenv = new Desenvolvedor();
        desenv.setNmDsenv(nomeDesenv);
        desenv.setExpDesenv(expeDesenv);

        DesenvolvedorDao desenvDao = new DesenvolvedorDao();
        boolean create = desenvDao.create(desenv);

        if (create == false) {
            FacesContext.getCurrentInstance().addMessage("erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro ao salvar registro."));
        } else {
            FacesContext.getCurrentInstance().addMessage("erro", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Desenvolvedor " + nomeDesenv + " salvo com sucesso!"));
        }
    }
    
    
}
