package br.cesjf.view;

import br.cesjf.model.dao.DesenvolvedorDao;
import br.cesjf.model.entities.Desenvolvedor;
import java.util.ArrayList;
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
public class DesenvolvedorConverter implements Converter {

    public static List<Desenvolvedor> desenvolvedores;

//    static {
//        desenvolvedores = new ArrayList<Desenvolvedor>();
//        DesenvolvedorDao atvdDao = new DesenvolvedorDao();
//        desenvolvedores = atvdDao.findDesenvolvedorEntities();
//    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                int number = Integer.parseInt(submittedValue);

                for (Desenvolvedor dsv : desenvolvedores) {
                    if (dsv.getIdDesenv() == number) {
                        return dsv;
                    }
                }

            } catch (NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Não é um Desenvolvedor válido"));
            }
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Desenvolvedor) value).getIdDesenv());
        }
    }
}
