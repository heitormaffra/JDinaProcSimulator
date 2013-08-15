/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backing.bean.desenvolvedor;

import br.cesjf.model.dao.DesenvolvedorDao;
import br.cesjf.model.entites.Desenvolvedor;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author heitor.filho
 */
@ViewScoped
@ManagedBean(name = "desenvolvedor")
public class DesenvolvedorBean {

    private String nomeDesenv;
    private Double expeDesenv;

    /**
     * Returna o nome do desenvolvedor
     * @return nomeDesenv
     */
    public String getNomeDesenv() {
        return nomeDesenv;
    }

    /**
     * Seta o nome do desenvolvedor.
     * @param nomeDesenv 
     */
    public void setNomeDesenv(String nomeDesenv) {
        this.nomeDesenv = nomeDesenv;
    }

    /**
     * Retorna o valor da experiência do desenvolvedor
     * @return expeDesenv
     */
    public Double getExpeDesenv() {
        return expeDesenv;
    }

    /**
     * Setar valor para experiência do desenvolvedor.
     * @param expeDesenv 
     */
    public void setExpeDesenv(Double expeDesenv) {
        this.expeDesenv = expeDesenv;
    }

    /**
     * Método para criar um novo desenvolvedor.
     */
    public void criaDesenv() {
        Desenvolvedor desenv = new Desenvolvedor();
        desenv.setNomeDesenv(nomeDesenv);
        desenv.setExpDesenv(expeDesenv);

        DesenvolvedorDao desenvDao = new DesenvolvedorDao();

        desenvDao.create(desenv);
    }
    
}
