/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.model.datamodel;

import br.cesjf.model.entities.Atividade;
import java.io.Serializable;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author heitormaffra
 */
public class AtividadeDataModel extends ListDataModel<Atividade> implements SelectableDataModel<Atividade>, Serializable {

    public AtividadeDataModel(List<Atividade> atividades) {
        super(atividades);
    }
    
    

    @Override
    public Atividade getRowData(String rowKey) {
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data
        
        int i = rowKey.indexOf("=");
        
        String id = rowKey.substring(i+1, rowKey.length() - 1);
        
        List<Atividade> atividades = (List<Atividade>) getWrappedData();
        
        for(Atividade atv : atividades) {
            if(atv.getIdAtivd().equals(Integer.valueOf(id)))
                return atv;
        }
        
        return null;
    }

    @Override
    public Object getRowKey(Atividade atv) {
        return atv;
    }
}
