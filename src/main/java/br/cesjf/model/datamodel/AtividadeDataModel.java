/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.model.datamodel;

import br.cesjf.model.entities.Atividade;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author heitor.filho
 */
public class AtividadeDataModel extends ListDataModel<Atividade> implements SelectableDataModel<Atividade>{

    public AtividadeDataModel(List<Atividade> data) {
        super(data);
    }
    
    

    @Override
    public Object getRowKey(Atividade t) {
        return t.getIdAtivd();
    }

    @Override
    public Atividade getRowData(String rowKey) {
        List<Atividade> atividades = (List<Atividade>) getWrappedData();
        for(Atividade atv : atividades){
            if(atv.getIdAtivd().equals(Integer.parseInt(rowKey))){
                return atv;
            }
        }
        return null;
    }
    
}
