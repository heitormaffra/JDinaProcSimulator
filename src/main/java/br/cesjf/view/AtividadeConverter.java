/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.view;

import br.cesjf.model.dao.AtividadeDao;
import br.cesjf.model.entities.Atividade;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 *
 * @author heitormaffra
 */
public class AtividadeConverter implements Converter {

    public AtividadeConverter() {
    }
    public static List<Atividade> atividades;

//    static {
//        AtividadeDao atvdDao = new AtividadeDao();
//        atividades = atvdDao.findAtividadeEntities();
//    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                int number = Integer.parseInt(submittedValue);

                for (Atividade atv : atividades) {
                    if (atv.getIdAtivd() == number) {
                        return atv;
                    }
                }

            } catch (NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid player"));
            }
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Atividade) value).getIdAtivd());
        }
    }
}
