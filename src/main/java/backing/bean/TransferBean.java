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
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author heitormaffra
 */
public class TransferBean implements Serializable {
    private List<Atividade> atividades;
    private Projeto projeto;

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public List<Atividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }
    
}
