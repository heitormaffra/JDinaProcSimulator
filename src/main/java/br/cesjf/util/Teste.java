/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.util;

import backing.bean.desenvolvedor.DesenvolvedorBean;
import br.cesjf.model.dao.AtividadeDao;
import br.cesjf.model.dao.DesenvolvedorDao;
import br.cesjf.model.dao.ProjetoDao;
import br.cesjf.model.entities.Atividade;
import br.cesjf.model.entities.Desenvolvedor;
import br.cesjf.model.entities.Projeto;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author heitormaffra
 */
public class Teste {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String nomeDesenv = "Heitor";
        Double artefato = 1.0d;

        DesenvolvedorDao desenvDao = new DesenvolvedorDao();
        Desenvolvedor desenv = desenvDao.findDesenvolvedor(10);
        ProjetoDao projDao = new ProjetoDao();
        Projeto projetoAtv = projDao.findProjeto(4);
        
        Atividade atividadePredecessora;


        Atividade atividad = new Atividade();
        atividad.setNmAtivd("qa");
        atividad.setIdDesenv(desenv);
        atividad.setDuracaoAtivid(20.0);
        atividad.setIdProjeto(projetoAtv);
        

        AtividadeDao ativdDao = new AtividadeDao();
        
        atividadePredecessora = ativdDao.findAtividade(1);
        
        atividad.setAtividadePrecedente(atividadePredecessora);
        
        boolean status = ativdDao.create(atividad);
    }
}
