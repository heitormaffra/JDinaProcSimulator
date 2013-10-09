/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.util;

import backing.bean.desenvolvedor.DesenvolvedorBean;
import br.cesjf.model.dao.DesenvolvedorDao;
import br.cesjf.model.entities.Desenvolvedor;
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

        Desenvolvedor desenvolvedor = new Desenvolvedor();
        desenvolvedor.setNmDsenv(nomeDesenv);
        desenvolvedor.setExpDesenv(artefato.floatValue());

        DesenvolvedorDao desenvDao = new DesenvolvedorDao();
        try {
            desenvDao.create(desenvolvedor);

        } catch (Exception ex) {
            Logger.getLogger(DesenvolvedorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
