package br.cesjf.view;

import br.cesjf.model.dao.ProjetoDao;
import br.cesjf.model.entities.Projeto;
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
public class ProjetoConverter implements Converter {

    public static List<Projeto> prjetos;

//    static {
//        ProjetoDao atvdDao = new ProjetoDao();
//        prjetos = atvdDao.findProjetoEntities();
//    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                int number = Integer.parseInt(submittedValue);

                for (Projeto prj : prjetos) {
                    if (prj.getIdProjeto() == number) {
                        return prj;
                    }
                }

            } catch (NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Não é um projeto válido"));
            }
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Projeto) value).getIdProjeto());
        }
    }
}
