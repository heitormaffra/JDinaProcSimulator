/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package action.control;

import backing.bean.TransferBean;
import java.io.IOException;
import javax.faces.context.FacesContext;

/**
 * Classe responsável por fazer os redirecionamentos nas páginas durante o wizard
 * de construção de cenário para simulação de projetos.
 * @author heitormaffra
 */
public class FlowPagesControl {
    private TransferBean transfer;

    /**
     * Construtor que recebe a sessão para carregar os dados.
     * @see backing.bean.TransferBean
     * @param transfer 
     */
    public FlowPagesControl(TransferBean transfer) {
        this.transfer = transfer;
    }
    
    /**
     *  Retorna view para construção de Wizard de simulação.
     * @return projeto
     */
    public void startWizard() throws IOException{
        
    }
    
    /**
     *  Retorna view para construção de Wizard de simulação.
     * @return equipe
     */
    public String projetoToNext(){
        return "equipe";
    }
    
    /**
     *  Retorna view para construção de Wizard de simulação.
     * @return atividade
     */
    public String equipeToNext(){
        return "atividade";
    }
    
    /**
     *  Retorna view para construção de Wizard de simulação.
     * @return producao
     */
    public String atividadeToNext(){
        return "producao";
    }
    
    /**
     * Retorna view para construção de Wizard de simulação.
     * @return runSimulation
     */
    public String precedenteToNext(){
        return "runSimulation";
    }
    
}
