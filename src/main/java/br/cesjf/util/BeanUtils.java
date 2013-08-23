package br.cesjf.util;

import javax.el.ELContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author heitormaffra
 */
public class BeanUtils {

    /**
     * Método que recupera um bean de página (Referencia a JSF2.1)
     *
     * @param managedBean Nome do managed bean do contexto
     * @return Retorna o objeto contendo o ManagedBean
     * @author heitormaffra
     */
    public static Object getManagedBean(String managedBean) {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Object object = FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, managedBean);
        return object;
    }
}
